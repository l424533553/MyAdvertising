package com.advertising.screen.myadvertising.mvvm.main.ui;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.common.MyImageLoader;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.common.iinterface.IEventBus;
import com.advertising.screen.myadvertising.databinding.ActivityMainBinding;
import com.advertising.screen.myadvertising.func.service.DownloadApkService;
import com.advertising.screen.myadvertising.func.service.MyJobService;
import com.advertising.screen.myadvertising.mvvm.ViewModelFactory;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.advertising.screen.myadvertising.mvvm.main.vm.UIViewModel;
import com.bumptech.glide.Glide;
import com.xuanyuan.library.MyToast;
import com.xuanyuan.library.adapter.AdvertiseLinearLayoutManager;
import com.xuanyuan.library.apk_update.download.DownloadIntentService;
import com.xuanyuan.library.utils.LiveBus;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;
import com.xuanyuan.library.utils.system.SystemInfoUtils;
import com.xuanyuan.library.utils.view.StatusBarUtil;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

/**
 * Created by Administrator on 2018/7/20.
 */
public class MainActivity extends AppCompatActivity implements IEventBus, IConstants, View.OnClickListener {
    //    private String TAG = getClass().getName();
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

        // 设置网络变化的
        LiveBus.observe(NOTIFY_LIVEBUS_KEY, String.class, this, observer);

        initAdapter();
        initHandler();
        initBanner();

        initSubscribe(this);
        if (!MyNetWorkUtils.isNetworkAvailable()) {
            viewModel.notifyData();
        }
        viewModel.checkVersion();
        initJobScheduler();

        handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);
        String localVersion = SystemInfoUtils.getVersionName(context) + "." + SystemInfoUtils.getVersionCode(context);
        binding.tvVersion.setText(localVersion);
    }

    private void initJobScheduler() {

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler == null) {
            return;
        }
        ComponentName jobService = new ComponentName(this, MyJobService.class);

        JobInfo jobInfo = new JobInfo.Builder(100012, jobService) //任务Id等于100012
//                .setMinimumLatency(1000)// 任务最少延迟时间为5s
//                .setOverrideDeadline(60000)// 任务deadline，当到期没达到指定条件也会开始执行
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)// 需要满足网络条件，默认值NETWORK_TYPE_NONE
                .setPeriodic(15 * 60 * 1000) //最小为15分钟
//                .setPeriodic(AlarmManager.INTERVAL_FIFTEEN_MINUTES) //最小为15分钟
                .setRequiresCharging(true)// 需要满足充电状态
//                .setRequiresDeviceIdle(false)// 设备处于Idle(Doze)
//                .setPersisted(true) //设备重启后是否继续执行
                .setBackoffCriteria(3000, JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略
                .build();
        scheduler.schedule(jobInfo);
    }


    /**
     * 观察者
     */
    public Observer<String> observer = s -> {
        if (s == null) {
            return;
        }
        if (NOTIFY_NET_CHANGE.equals(s)) {//网络变化了
            if (MyNetWorkUtils.isNetworkAvailable()) {
                viewModel.uiBean.getIsWifi().set(true);
            } else {
                viewModel.uiBean.getIsWifi().set(false);
            }
        } else if (NOTIFY_WHILE_DATA.equals(s)) {
            MyLog.sysLog("MyJobService", "收到数据更新的通知");
            viewModel.notifyData();
        }
    };

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
        viewModel.weatherLiveData.observe(owner, (Weather weather) -> binding.setWeather(weather));
        viewModel.mainUILiveData.observe(owner, uiBean -> {
            binding.setUiBean(uiBean);
            if (uiBean.getBitmapZS() != null) {
                binding.ivZS.setImageBitmap(uiBean.getBitmapZS());
            }
        });
        viewModel.versionInfoLiveData.observe(owner, versionInfo -> {
            Intent intent = new Intent(context, DownloadApkService.class);
            intent.putExtra("download_url", versionInfo);
            startService(intent);
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
            if (!TextUtils.isEmpty(photoString)) {
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
        if (v.getId() == R.id.ivZS||v.getId() == R.id.tvMarketName) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            showSellerIdDialog(sellerId);
        }
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
//       F9,F10，F11,
        if (keyCode == 139 || keyCode == 140 || keyCode == 141) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            showSellerIdDialog(sellerId);

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * VM  显示设置商户id
     */
    private void showSellerIdDialog(String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_dialog_sellerid, null);   // 账号、密码的布局文件，自定义
        builder.setIcon(R.mipmap.log);//设置对话框icon
        builder.setTitle("SellerId设置");
        final EditText etSellerId = view.findViewById(R.id.etSellerId);
        if (hint != null) {
            etSellerId.setText(hint);
        }
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog1, which) -> {
            String sellerId = etSellerId.getText().toString();
            if (TextUtils.isEmpty(sellerId)) {
                MyToast.showError(context, "商户id不可为空");
            } else {
                MyPreferenceUtils.getSp().edit().putString(SELLER_ID, sellerId).apply();
                MyToast.toastShort(context, "设置成功！");
                dialog1.dismiss();//关闭对话框
                viewModel.notifyData();
            }
        });
        dialog.show();
    }

    private void test() {

    }
}
