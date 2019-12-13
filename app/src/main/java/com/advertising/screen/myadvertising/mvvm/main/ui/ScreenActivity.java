package com.advertising.screen.myadvertising.mvvm.main.ui;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.ImageView;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.databinding.ScreenMainBinding;
import com.advertising.screen.myadvertising.entity.AdUserBean;
import com.advertising.screen.myadvertising.mvvm.main.vm.ScreenViewModel;
import com.bumptech.glide.Glide;
import com.xuanyuan.library.MyToast;
import com.xuanyuan.library.adapter.AdvertiseLinearLayoutManager;
import com.xuanyuan.library.utils.system.SystemInfoUtils;
import com.xuanyuan.library.utils.view.StatusBarUtil;
import com.xuanyuan.library.view.MarqueeTextviewNofocus;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


/**
 * Created by Administrator on 2018/7/20.
 */
public class ScreenActivity extends AppCompatActivity implements IConstants, View.OnClickListener {
    /**
     * 上下文内容
     */
    private Context context;
    /**
     * 工作Intent
     */
    private Intent workIntent;
    private Banner mBanner;
    private ScreenMainBinding binding;
    private ScreenViewModel viewModel;

    /*  使用  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImageTranslucent(this, Color.RED, 0);
        SystemInfoUtils.hideState(this);
        binding = DataBindingUtil.setContentView(this, R.layout.screen_main);

//        setListener();
//        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ScreenViewModel.class);
//        binding.setScreenState(viewModel.getScreenState());
        setObserve();
//        viewModel.init();
//
//        LiveBus.observe(NOTIFY_LIVEBUS_KEY, String.class, this, viewModel.observer);
//        context = this;
//        initHandler();
        initAdapter();
//
//        startService();
        initBanner();
//
//        handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);
//        handler.sendEmptyMessageDelayed(NOTIFY_PATCH_UPDATE, 4000);

    }

    @Override
    public void onClick(View v) {

    }

//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        binding.setOnClickListener(this);
//    }
//
    /**
     * VM  设置监听
     */
    private void setObserve() {
        // 登陆表单字段信息已经变化 ,
        viewModel.getScreenStateLiveData().observe(this, screenState -> {
            if (screenState == null) {
                return;
            }

            if (screenState.getPriceBeans().size() == 0) {
                binding.ilData.llPrice.setVisibility(View.GONE);
            } else {
                binding.ilData.llPrice.setVisibility(View.VISIBLE);
            }

            if (screenState.getIsWifi().get()) {
                binding.ivWifi.setImageResource(R.drawable.ic_net);
            } else {
                binding.ivWifi.setImageResource(R.drawable.ic_net2);
            }
        });

        //监听AdUser的基础信息
        viewModel.getAdInfoLiveData().observe(this, adInfoLiveBean -> {
            if (adInfoLiveBean == null) {
                return;
            }

            AdUserBean adUserBean = adInfoLiveBean.getAdUserBean();
            String adString = adInfoLiveBean.getReallyAdString();
            if(adUserBean!=null){
                binding.setAdUserBean(adUserBean);
            }

            binding.marquee.setText(adString);
            // 初始化
            binding.marquee.init(getWindowManager());
            // 设置滚动方向
            binding.marquee.setScrollDirection(MarqueeTextviewNofocus.RIGHT_TO_LEFT);
            // 设置滚动速度
            binding.marquee.setScrollMode(MarqueeTextviewNofocus.SCROLL_SLOW);

            String photoString = adInfoLiveBean.getPhotoString();
            if (photoString != null) {
//            RequestOptions requestOptions = new RequestOptions();
//            //忽略 警告 ，不使用内存 ,这样就失去了意义了
//            requestOptions.skipMemoryCache(true) // 不使用内存缓存
//                    .diskCacheStrategy(DiskCacheStrategy.NONE); // 不使用磁盘缓存

                Glide.with(context).load(photoString).error(R.mipmap.head).into(binding.layoutMain.ivPhoto);
//            Glide.with(context).load(photo).apply(requestOptions).into(ivPhoto);
            } else {
                binding.layoutMain.ivPhoto.setImageResource(R.mipmap.head);
            }

            List<Object> imagePaths = adInfoLiveBean.getImagePaths();
            mBanner.setImages(imagePaths);
            mBanner.setDelayTime(5000);
            mBanner.start();
        });

    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopService(workIntent);
//        LiveBus.removeObserver(NOTIFY_LIVEBUS_KEY, String.class, viewModel.observer);
//    }
//
//    /**
//     * VM 弹出设置框
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////       MyToast.toastShort(context,"按键 +=="+keyCode);
//        if (keyCode == 139 || keyCode == 140 || keyCode == 141) {
//            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
//            showSellerIdDialog(sellerId);
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /**
//     * VM  显示设置商户id
//     */
//    private void showSellerIdDialog(String hint) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = View.inflate(context, R.layout.layout_dialog_sellerid, null);   // 账号、密码的布局文件，自定义
//        builder.setIcon(R.mipmap.log);//设置对话框icon
//        builder.setTitle("SellerId设置");
//        final EditText etSellerId = view.findViewById(R.id.etSellerId);
//        if (hint != null) {
//            etSellerId.setText(hint);
//        }
//        AlertDialog dialog = builder.create();
//        dialog.setView(view);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog1, which) -> {
//            String sellerId = etSellerId.getText().toString();
//            if (TextUtils.isEmpty(sellerId)) {
//                MyToast.showError(context, "商户id不可为空");
//            } else {
//                MyPreferenceUtils.getSp().edit().putString(SELLER_ID, sellerId).apply();
//                MyToast.toastShort(context, "设置成功！");
//                dialog1.dismiss();//关闭对话框
//
//                onDestroy();
//                System.exit(0);
//            }
//        });
//        dialog.show();
//    }


    /**
     * 初始化Banner 设置功能
     */
    private void initBanner() {
        MyImageLoader mMyImageLoader = new MyImageLoader();
        mBanner = binding.banner;
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.Default);
        //轮播图片的文字
//        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(9000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
//        mBanner.setImages(imagePath);// 这是资源id集合

        //开始调用的方法，启动轮播图。//轮播图的监听
//        mBanner.setOnBannerListener(this).start();
    }

    /**
     * VM  初始化Adapter
     */
    private void initAdapter() {
        AdvertiseLinearLayoutManager linearLayoutManager = new AdvertiseLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        AdvertiseLinearLayoutManager linearLayoutManager2 = new AdvertiseLinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        binding.ilData.rvPrice.setLayoutManager(linearLayoutManager);
        binding.ilData.rvCheck.setLayoutManager(linearLayoutManager2);
        binding.ilData.rvPrice.setAdapter(viewModel.getPriceAdapter());
        binding.ilData.rvCheck.setAdapter(viewModel.getInspectAdapter());
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
                        binding.ilData.rvPrice.smoothScrollToPosition(priceIndex);
                    }

                    int inspectIndex = viewModel.getSmollInspectIndex();
                    if (inspectIndex >= 0) {
                        binding.ilData.rvCheck.smoothScrollToPosition(inspectIndex);
                    }
                    handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);
                    break;
                case NOTIFY_JUMP:
                    break;
                case FAILED://每隔几分钟循环访问一次，无内容
                    MyToast.showError(context, "Data信息为null，请检查后台配置");
                    break;
                case NOTIFY_PATCH_UPDATE:
                    // 自动加载补丁包
                    //  Toast.makeText(this, "点击下载补丁", Toast.LENGTH_SHORT).show();
//                        SophixManager.getInstance().queryAndLoadNewPatch();
                    break;
            }
            return false;
        });
    }
//
//    /**
//     * VM 初始化数据
//     */
//    private void startService() {
//        workIntent = new Intent(this, WorkService.class);
//        startService(workIntent);
//    }
//
//    /**
//     * VM  点击事件
//     */
//    @Override
//    public void onClick(View v) {
//        String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
//        showSellerIdDialog(sellerId);
//    }
//
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
