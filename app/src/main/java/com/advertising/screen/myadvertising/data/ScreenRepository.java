package com.advertising.screen.myadvertising.data;



/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class ScreenRepository {

    private static volatile ScreenRepository instance;

    private ScreenDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
//    private LoggedInUser user = null;

    // private constructor : singleton access
    private ScreenRepository(ScreenDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ScreenRepository getInstance(ScreenDataSource dataSource) {
        if (instance == null) {
            instance = new ScreenRepository(dataSource);
        }
        return instance;
    }

//    public boolean isLoggedIn() {
//        return user != null;
//    }
//
//    public void logout() {
//        user = null;
//        dataSource.logout();
//    }

//    private void setLoggedInUser(LoggedInUser user) {
//        this.user = user;
//        // If user credentials will be cached in local storage, it is recommended it be encrypted
//        // @see https://developer.android.com/training/articles/keystore
//    }
//
//    Result<LoggedInUser> result = null;
//
//    public Result<LoggedInUser> login(String username, String password) {
//        // handle login
//
//
//        HttpRtHelper.getmInstants().getUserInfoExByRetrofit(username, new RetrofitCallback<UserInfo>() {
//            @Override
//            public void onNext(ResultRtInfo<UserInfo> resultInfo, int flag) {
//                UserInfo userInfo = resultInfo.getData();
//                LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), userInfo.getSeller());
//                result = new Result.Success<>(fakeUser);
//                MyLog.log22("测试登陆 result");
//            }
//
//            @Override
//            public void onError(Throwable e, int flag) {
//
//            }
//
//            @Override
//            public void onComplete(int flag) {
//
//            }
//        }, 1);
//
//
//        if (result == null) {
//            LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe");
//            Result<LoggedInUser> result = new Result.Success<>(fakeUser);
//            MyLog.log22("测试登陆 为空");
//        }
//
//
////        Result<LoggedInUser> result = dataSource.login(username, password);
////        if (result instanceof Result.Success) {
////            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
////        }
//
//
//        return result;
//    }


}
