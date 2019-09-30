package com.advertising.screen.myadvertising.ui.screen;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Patterns;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.adapter.InspectAdapter;
import com.advertising.screen.myadvertising.adapter.PriceAdapter;
import com.advertising.screen.myadvertising.data.ScreenRepository;
import com.advertising.screen.myadvertising.data.ScreenState;
import com.advertising.screen.myadvertising.entity.AdImageInfo;
import com.advertising.screen.myadvertising.entity.AdInfoLiveBean;
import com.advertising.screen.myadvertising.entity.AdUserBean;
import com.advertising.screen.myadvertising.entity.dao.AdUserDao;
import com.advertising.screen.myadvertising.entity.dao.ImageDao;
import com.advertising.screen.myadvertising.entity.dao.InspectBeanDao;
import com.advertising.screen.myadvertising.entity.dao.PriceBeanDao;
import com.xuanyuan.library.help.QRCodeUtil;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

import java.util.List;

import static com.advertising.screen.myadvertising.config.IConstants.DATA_BOOTH_NUMBER;
import static com.advertising.screen.myadvertising.config.IConstants.DATA_MARK_ID;
import static com.advertising.screen.myadvertising.config.IConstants.DATA_MARK_NAME;
import static com.advertising.screen.myadvertising.config.IConstants.DEFAULT_AD_CONTENT;
import static com.advertising.screen.myadvertising.config.IConstants.DEFAULT_ID;
import static com.advertising.screen.myadvertising.config.IConstants.SELLER_ID;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_INFO;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_INSPECT;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_PRICE;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_NET_CHANGE;

/**
 * 屏幕控件模型
 */
public class ScreenViewModel extends ViewModel {
    //      登录状态 实体
    private MutableLiveData<ScreenState> screenStateLiveData = new MutableLiveData<>();
    private MutableLiveData<AdInfoLiveBean> AdInfoLiveData = new MutableLiveData<>();
    private ScreenState screenState = new ScreenState();
    private AdInfoLiveBean adInfoLiveBean = new AdInfoLiveBean();

    MutableLiveData<ScreenState> getScreenStateLiveData() {
        return screenStateLiveData;
    }

    public MutableLiveData<AdInfoLiveBean> getAdInfoLiveData() {
        return AdInfoLiveData;
    }

    ScreenState getScreenState() {
        return screenState;
    }



    private PriceAdapter priceAdapter;
    private int priceIndex;
    private InspectAdapter inspectAdapter;
    private int inspectIndex;

    public PriceAdapter getPriceAdapter() {
        if (priceAdapter == null) {
            priceAdapter = new PriceAdapter(getScreenState().getPriceBeans());
        }
        return priceAdapter;
    }

    /**
     * 获取要滑动的索引
     */
    public int getSmollPriceIndex() {
        int priceCount = getPriceAdapter().getItemCount();
        if (priceCount > 0) {
            if (priceIndex == priceCount - 1) {
                priceIndex = 0;
            } else {
                priceIndex += 6;
            }

            if (priceIndex >= priceCount - 1) {
                priceIndex = priceCount - 1;
            }
            return priceIndex;
        } else {
            return -1;
        }
    }

    /**
     *
     * @return
     */
    public InspectAdapter getInspectAdapter() {
        if (inspectAdapter == null) {
            inspectAdapter=   new InspectAdapter(getScreenState().getInspectBeans());
        }
        return inspectAdapter;
    }

    /**
     * 获取要滑动的索引
     */
    public int getSmollInspectIndex() {
        int inspectCount = getInspectAdapter().getItemCount();
        if (inspectCount > 0) {
            if (inspectIndex == inspectCount - 1) {
                inspectIndex = 0;
            } else {
                inspectIndex += 6;
            }

            if (inspectIndex >= inspectCount - 1) {
                inspectIndex = inspectCount - 1;
            }
            return inspectIndex;
        }
        return -1;
    }


    private AdInfoLiveBean getAdInfoLiveBean() {
        return adInfoLiveBean;
    }

    //  登陆数据库控制器
    private ScreenRepository repository;

    public ScreenViewModel(ScreenRepository repository) {
        this.repository = repository;
        screenStateLiveData.setValue(screenState);
        AdInfoLiveData.setValue(adInfoLiveBean);
    }

    public void init() {
        discoverNet();
        initQR();
        updateBasePrice();
        updateBaseInspect();
        updateBaseInfo();
    }

    /**
     * 观察者
     */
    public Observer<String> observer = s -> {
        if (s == null) {
            return;
        }
        switch (s) {
            case NOTIFY_BASE_INFO:
                updateBaseInfo();
                MyLog.logTest("************   更新基础信息");
                break;
            case NOTIFY_BASE_PRICE:
                MyLog.logTest("====   更新价格信息");
                updateBasePrice();
                break;
            case NOTIFY_BASE_INSPECT:
                MyLog.logTest("====   更新检测信息");
                updateBaseInspect();
                break;
            case NOTIFY_NET_CHANGE://网络变化了
                discoverNet();
                break;
            default:
                break;
        }
    };

    /**
     *
     * 更新  基础信息
     */
    private void updateBaseInfo() {
        List<AdUserBean> userBeans = AdUserDao.getInstance().queryAll();
        if (userBeans != null && userBeans.size() > 0) {
            AdUserBean userBean = userBeans.get(0);
            getAdInfoLiveBean().setAdUserBean(userBean);

            String adcontent = userBean.getAdcontent();
            String commcontent = userBean.getCommcontent();
            String adString;
            if (!TextUtils.isEmpty(commcontent)) {
                adString = "1." + commcontent;
                if (!TextUtils.isEmpty(adcontent)) {
                    adString = adString + "                                              2." + adcontent;
                }
            } else {
                if (!TextUtils.isEmpty(adcontent)) {
                    adString = "1." + adcontent;
                } else {
                    adString = DEFAULT_AD_CONTENT;
                }
            }
            getAdInfoLiveBean().setReallyAdString(adString);
        } else {
            getAdInfoLiveBean().setAdUserBean(null);
            getAdInfoLiveBean().setReallyAdString(DEFAULT_AD_CONTENT);
        }

        /*  头像信息   ******************************************************************/
        List<AdImageInfo> photos = ImageDao.getInstance().queryPhoto();
        if (photos != null && photos.size() > 0) {
            String photo = photos.get(0).getNetPath();
            getAdInfoLiveBean().setPhotoString(photo);
        } else {
            getAdInfoLiveBean().setPhotoString(null);
        }

        /*  更新轮播图片信息   **************************************************************************/
        List<AdImageInfo> list = ImageDao.getInstance().queryAll();
        if (list == null || list.size() == 0) {
            adInfoLiveBean.getImagePaths().clear();
            adInfoLiveBean.getImagePaths().add(R.drawable.img1);
            adInfoLiveBean.getImagePaths().add(R.drawable.img2);
            adInfoLiveBean.getImagePaths().add(R.drawable.img3);
        } else {
            adInfoLiveBean.getImagePaths().clear();
            for (AdImageInfo image : list) {
                adInfoLiveBean.getImagePaths().add(image.getNetPath());
            }
        }

        AdInfoLiveData.setValue(adInfoLiveBean);
    }

    /**
     * 更新Banner
     */
    public void updateBanner() {

    }

    /**
     * 检测网络 状态
     */
    public void discoverNet() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            screenState.getIsWifi().set(true);
//            screenStateLiveData.setValue(screenState);
//          binding.ivNet.setImageResource(R.drawable.ic_net);
        } else {
            screenState.getIsWifi().set(false);
//            screenStateLiveData.setValue(screenState);
//          binding.ivNet.setImageResource(R.drawable.ic_net2);
        }
    }

    //
    public void initQR() {
        String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
        String stringZS = "https://data.axebao.com/smartsz/trace/trace2.php?companyid=" + sellerId;
        Bitmap bitmapZS = QRCodeUtil.createBitmap(stringZS, 30, 30);

//                Bitmap logoBmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_traceback2);
//                这种方式是创建彩色的二维码
//                bitmapZS = QRCodeUtil.makeQRImage(logoBmp2, stringZS, 300, 300);

        String stringData = MyPreferenceUtils.getSp().getInt(DATA_MARK_ID, 1) + "." +
                MyPreferenceUtils.getSp().getString(DATA_MARK_NAME, "黄田智慧农贸市场") + "." +
                MyPreferenceUtils.getSp().getString(DATA_BOOTH_NUMBER, "A001");

        String stringDP = "https://data.axebao.com/html/home.php?id=" + stringData;
        Bitmap bitmapDP = QRCodeUtil.createBitmap(stringDP, 30, 30);
        screenState.setBitmapDP(bitmapDP);
        screenState.setBitmapZS(bitmapZS);
        //内容值有了变化了
        screenStateLiveData.setValue(screenState);
    }

    /**
     * 更新检测结果
     */
    public void updateBaseInspect() {
        screenState.getInspectBeans().clear();
        screenState.getInspectBeans().addAll(InspectBeanDao.getInstance().queryAll());
        screenStateLiveData.setValue(screenState);
    }

    /**
     * 更新价格
     */
    public void updateBasePrice() {
        screenState.getPriceBeans().clear();
        screenState.getPriceBeans().addAll(PriceBeanDao.getInstance().queryAll());
        screenStateLiveData.setValue(screenState);
    }


//
//    public void login(String username, String password) {
//
//        // can be launched in a separate asynchronous job
//        // 可以是一个异步线程
//        // 此处使用 Rxjava方式
//        Result<LoggedInUser> result = loginRepository.login(username, password);
//
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
//
//    }

//    // 登陆数据变化了，紧接着会进行监控处理
//    public void loginDataChanged(String username, String password) {
//        if (!isUserNameValid(username)) {
//            loginStateBean.setUsernameError(R.string.invalid_username);
//            loginFormState.setValue(loginStateBean);
////            loginFormState.setValue(  new LoginFormState(R.string.invalid_username, null));
//        } else if (!isPasswordValid(password)) {
//            loginStateBean.setPasswordError(R.string.invalid_password);
//            loginFormState.setValue(loginStateBean);
//
////            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginStateBean.setDataValid(true);
//            loginFormState.setValue(loginStateBean);
////            loginFormState.setValue(new LoginFormState(true));
//            //
//        }
//    }

    /**
     * A placeholder username validation check
     * 字段名验证，属于业务逻辑故需要放在ViewModel中
     */
    private boolean isUserNameValid(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return username.trim().isEmpty();
        }
    }

    /**
     * 业务逻辑的判断
     * A placeholder password validation check
     */
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
