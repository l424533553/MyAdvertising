//package com.axecom.smartweight.base;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.DisplayMetrics;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.*;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import com.advertising.SysApplication;
//import com.advertising.screen.myadvertising.R;
//import com.axecom.smartweight.bean.SubOrderBean;
//import com.axecom.smartweight.bean.SubOrderReqBean;
//import com.axecom.smartweight.manager.AccountManager;
//import com.axecom.smartweight.net.RetrofitFactory;
//import com.axecom.smartweight.ui.uiutils.UIUtils;
//import com.axecom.smartweight.ui.uiutils.ViewUtils;
//import com.axecom.smartweight.utils.LogUtils;
//import com.axecom.smartweight.utils.SPUtils;
//import com.bigkoo.convenientbanner.holder.Holder;
//import com.bumptech.glide.Glide;
//import com.luofx.newclass.ActivityController;
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.lang.reflect.Method;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Longer on 2016/10/26.
// */
//public abstract class BaseActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
//    public ViewUtils mViewUtils = null;
//    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
//    DisplayMetrics dm;
//    public int mWidthPixels;
//    public int mHeightPixels;
//    //    private SweetAlertDialog mSweetAlertDialog;
//    private View mMenuRoot;
//    private SweetAlertDialog mSweetAlertDialog;
//    protected SysApplication sysApplication;
//    private Context context;
//
//    // 测试功能
//    @SuppressLint("InlinedApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
//        sysApplication = (SysApplication) getApplication();
//        context = this;
//
//        EventBus.getDefault().register(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
////        Intent bannerIntent = new Intent(this, BannerService.class);
////        startService(bannerIntent);
//        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 19) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
//        }
//        //
//        View view = setInitView();
//        setContentView(view);
//        //这里这一段会影响弹出的dialog型的Activity，故暂时注释掉
//        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//
//        mViewUtils = new ViewUtils(this);
//        //获取屏幕的宽高的像素
//        dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
////        SysApplication.mWidthPixels = dm.widthPixels;
////        SysApplication.mHeightPixels = dm.heightPixels;
//        ActivityController.addActivity(this);
//
//    }
//
//   public  abstract void initView();
//
//    /**
//     * 判断 是否在线
//     */
//    public void isOnline(Context context) {
//        RetrofitFactory.getInstance().API()
//                .isOnline(AccountManager.getInstance(context).getToken(), AccountManager.getInstance(context).getScalesId())
//                .compose(this.<BaseEntity>setThread())
//                .subscribe(new Observer<BaseEntity>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(BaseEntity baseEntity) {
//                        if (baseEntity.isSuccess()) {
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }
//
//
//    /**
//     * 网络  图片  holderView
//     */
//    public class NetworkImageHolderView implements Holder<String> {
//        private ImageView imageView;
//
//        @Override
//        public View createView(Context context) {
//            imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            return imageView;
//        }
//
//        @Override
//        public void UpdateUI(Context context, int position, String data) {
//            imageView.setImageResource(R.drawable.logo);
////            ImageLoader imageLoader = ImageLoader.getInstance();
////            imageLoader.displayImage(data, imageView);
//            Glide.with(context).load(data).into(imageView);
//        }
//    }
//
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == event.KEYCODE_HOME) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    /**
//     * @param et 隐藏软键盘
//     * @nama yl
//     */
//    public void Hidekeyboard(EditText et) {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
//        if (isOpen) {
//            ((InputMethodManager) et.getContext().getSystemService(
//                    INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                    getCurrentFocus().getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    public void showSoftInput(EditText editText) {
//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();
//        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
//    }
//
//    public void hideSoftInput() {
//        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (m.isActive()) {
//            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    /**
//     * @param et 显示软键盘
//     * @nama yl
//     */
//    public void Showkeyboard(EditText et) {
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                .showSoftInput(et, 0);
//
//    }
//
//
//    public abstract View setInitView();
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    protected View getAnchorView() {
//        return null;
//    }
//
//    PopupWindow mPopupWindow;
//
//    protected void switchMenu() {
//        if (mPopupWindow == null) {
//            mPopupWindow = new PopupWindow(this);
//            //mPopupWindow.setOutsideTouchable(true);
//            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//            mPopupWindow.setContentView(mMenuRoot);
//            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            mMenuRoot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switchMenu();
//                }
//            });
//
//        }
//        if (mPopupWindow.isShowing()) {
//            mPopupWindow.dismiss();
//        } else {
//            mPopupWindow.showAsDropDown(getAnchorView());
//        }
//    }
//
//    public void showLoading(String titleText, int type) {
//        if (mSweetAlertDialog != null) {
//            mSweetAlertDialog.dismiss();
//        }
//        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void showLoading() {
//        if (mSweetAlertDialog != null) {
//            mSweetAlertDialog.dismiss();
//        }
//        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void showLoading(String titleText, String confirmText) {
//        if (mSweetAlertDialog != null) {
//            mSweetAlertDialog.dismiss();
//        }
//        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//        mSweetAlertDialog.setConfirmText(confirmText);
//
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void showLoading(String titleText, String confirmText, long times) {
//
//        showLoading(titleText, confirmText);
//        UIUtils.getMainThreadHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                closeLoading();
//            }
//        }, times);
//    }
//
//    public void showLoading(String titleText) {
//        SweetAlertDialog mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//
//
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void closeLoading() {
//        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
//            mSweetAlertDialog.dismissWithAnimation();
//        }
//    }
//
//
//
//    public void disableShowInput(final EditText editText) {
//        if (android.os.Build.VERSION.SDK_INT <= 10) {
//            editText.setInputType(InputType.TYPE_NULL);
//        } else {
//            Class<EditText> cls = EditText.class;
//            Method method;
//            try {
//                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {//TODO: handle exception
//            }
//            try {
//                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {//TODO: handle exception
//            }
//        }
//
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                editText.setSelection(editText.length());
//            }
//        });
//    }
//
//    public void setEditText(EditText editText, int position, String text) {
//        if (position == 9) {
//            if (!TextUtils.isEmpty(editText.getText()))
//                editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
//        } else {
//            editText.setText(editText.getText() + text);
//        }
//    }
//
//    public void setEditText(EditText editText, int position, String text, int type) {
//        if (position == 9) {
//            if (!TextUtils.isEmpty(editText.getText()))
//                editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
//        } else {
//            editText.setText(editText.getText() + text);
//        }
//    }
//
//
//    public String getMonthTime(String specifiedDay, String format, int type) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
//        Calendar c = Calendar.getInstance();
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yy-MM").parse(specifiedDay);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        c.setTime(date);
//        if (type == 1)
//            c.add(Calendar.MONTH, -1);
//        if (type == 2)
//            c.add(Calendar.MONTH, +1);
//        if (type == 3)
//            c.add(Calendar.DAY_OF_MONTH, -1);
//        if (type == 4)
//            c.add(Calendar.DAY_OF_MONTH, +1);
//        Date m = c.getTime();
//        return simpleDateFormat.format(m);
//    }
//
//    public void scrollTo(final ListView listView, final int position) {
//        listView.post(new Runnable() {
//            @Override
//            public void run() {
//                listView.smoothScrollToPosition(position);
//            }
//        });
//    }
//
//    @Subscribe
//    public void onEventMainThread(BusEvent event) {
//
//        if (event.getType() == BusEvent.NET_WORK_AVAILABLE) {
//            if (event.getBooleanParam()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<SubOrderReqBean> orders = (List<SubOrderReqBean>) SPUtils.readObject(BaseActivity.this, "local_order");
//                        submitOrders(orders);
////                        for (int i = 0; i < orders.size(); i++) {
////                            submitOrder(orders.get(i));
////                        }
//                    }
//                }).start();
//            }
//        }
//        if (event.getType() == BusEvent.REPORT_DEVICE_IS_ONLINE) {
//            isOnline(context);
//        }
//    }
//
//    /**
//     * 提交订单集合
//     * @param list
//     */
//    public void submitOrders(List<SubOrderReqBean> list) {
//        RetrofitFactory.getInstance().API()
//                .submitOrders(list)
//                .compose(this.<BaseEntity>setThread())
//                .subscribe(new Observer<BaseEntity>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(BaseEntity baseEntity) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }
//
//    /**
//     * 提交订单
//     *
//     * @param subOrderReqBean 提交订单
//     */
//    public void submitOrder(SubOrderReqBean subOrderReqBean) {
//        RetrofitFactory.getInstance().API()
//                .submitOrder(subOrderReqBean)
//                .compose(this.<BaseEntity<SubOrderBean>>setThread())
//                .subscribe(new Observer<BaseEntity<SubOrderBean>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(final BaseEntity<SubOrderBean> subOrderBeanBaseEntity) {
//                        if (subOrderBeanBaseEntity.isSuccess()) {
//                            LogUtils.d("提交成功");
//                        } else {
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }
//
//    public <T> ObservableTransformer<T, T> setThread() {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//
//}
