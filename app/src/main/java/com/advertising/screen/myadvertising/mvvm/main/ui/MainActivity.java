package com.advertising.screen.myadvertising.mvvm.main.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.databinding.ActivityMainBinding;
import com.advertising.screen.myadvertising.func.service.WorkService;
import com.advertising.screen.myadvertising.mvvm.ViewModelFactory;
import com.advertising.screen.myadvertising.mvvm.main.vm.UIViewModel;
import com.bumptech.glide.Glide;
import com.xuanyuan.library.adapter.AdvertiseLinearLayoutManager;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;
import com.xuanyuan.library.utils.system.SystemInfoUtils;
import com.xuanyuan.library.utils.view.StatusBarUtil;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/7/20.
 */
public class MainActivity extends AppCompatActivity implements IConstants, View.OnClickListener {
    private String TAG = getClass().getName();
    private UIViewModel viewModel;
    private ActivityMainBinding binding;

    private Context context;

    /*  使用  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImageTranslucent(this, Color.RED, 0);
        SystemInfoUtils.hideState(this);
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModelFactory factory = new ViewModelFactory(getApplication(), this);
        viewModel = ViewModelProviders.of(this, factory).get(UIViewModel.class);

        binding.setViewModel(viewModel);
        binding.setUiBean(viewModel.uiBean);
        binding.setOnClick(this);
        initAdapter();

        initHandler();
        initBanner();

        initSubscribe(this);

        viewModel.notifyData();
        handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);
    }

    /**
     * 初始化Banner 设置功能
     */
    private void initBanner() {
        MyImageLoader mMyImageLoader = new MyImageLoader();
        //设置样式，里面有很多种样式可以自己都看看效果
        binding.mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        binding.mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        binding.mBanner.setBannerAnimation(Transformer.Default);
        //轮播图片的文字
//        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        binding.mBanner.setDelayTime(9000);
        //设置是否为自动轮播，默认是true
        binding.mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        binding.mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
//        mBanner.setImages(imagePath);// 这是资源id集合

        //开始调用的方法，启动轮播图。//轮播图的监听
//        mBanner.setOnBannerListener(this).start();
    }

    /**
     * 数据 handler，
     */
    private Handler handler;

    /**
     * 初始化 Handler 内容数据
     */
    private void initHandler() {
        handler = new Handler(msg -> {
            switch (msg.what) {
                case NOTIFY_DATA_MOVE:
                    int priceIndex = viewModel.getSmollPriceIndex();
                    if (priceIndex >= 0) {
                        binding.rvPrice.smoothScrollToPosition(priceIndex);
                    }
                    handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);
                    break;
                case NOTIFY_JUMP:
                    break;
            }
            return false;
        });
    }

    /**
     * VM  初始化Adapter
     */
    private void initAdapter() {
        AdvertiseLinearLayoutManager linearLayoutManager = new AdvertiseLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridDividerDecoration viewDivider=   new GridDividerDecoration(context, LinearLayoutManager.HORIZONTAL);
        binding.rvPrice.setLayoutManager(linearLayoutManager);
//        binding.rvPrice.addItemDecoration(viewDivider);
        binding.rvPrice.setAdapter(viewModel.adapter);
    }

    /**
     * 初始化订阅,有变化一般是数据有改变了
     */
    private void initSubscribe(@NonNull LifecycleOwner owner) {
        viewModel.weatherLiveData.observe(owner, weather -> {
            binding.setWeather(weather);
        });

        viewModel.mainUILiveData.observe(owner, uiBean -> {
            binding.setUiBean(uiBean);
            if (uiBean.getBitmapZS() != null) {
                binding.ivZS.setImageBitmap(uiBean.getBitmapZS());
            }
        });


        viewModel.userInfoLiveData.observe(owner, userInfoEntity -> {
            binding.setUserInfoEntity(userInfoEntity);
            if (userInfoEntity.getWxQR() != null) {
                binding.ivWX.setImageBitmap(userInfoEntity.getWxQR());
            }
            if (userInfoEntity.getZfbQR() != null) {
                binding.ivZFB.setImageBitmap(userInfoEntity.getZfbQR());
            }

            String photoString = userInfoEntity.getHeadUrl();
            if (TextUtils.isEmpty(photoString)) {
                Glide.with(context).load(photoString).error(R.mipmap.head).into(binding.ivHead);
            } else {
                binding.ivHead.setImageResource(R.mipmap.head);
            }

            binding.mBanner.setImages(userInfoEntity.getUrls());
            binding.mBanner.setDelayTime(5000);
            binding.mBanner.start();

        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(workIntent);
//        LiveBus.removeObserver(NOTIFY_LIVEBUS_KEY, String.class, viewModel.observer);
    }

    /**
     * VM 弹出设置框
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//       MyToast.toastShort(context,"按键 +=="+keyCode);
        if (keyCode == 139 || keyCode == 140 || keyCode == 141) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * VM 初始化数据
     */
    private void startService() {
        /**
         * 工作Intent
         */
        Intent workIntent = new Intent(this, WorkService.class);
        startService(workIntent);
    }


    /**
     * VM  此处用于Banner的图片加载
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load(path).into(imageView);
//            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
        }
    }


}
//https://data.axebao.com/smartsz/ups/uploads/file/20191206/%E4%B8%8B%E8%BD%BD%20(3).jpg
//https://data.axebao.com/smartsz/ups/uploads/file/20191206/%E4%B8%8B%E8%BD%BD(3).jpg