package com.mvvm.lux.framework.config.session;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mvvm.lux.framework.config.UnProguard;
import com.mvvm.lux.framework.config.FrameWorkConfig;
import com.mvvm.lux.framework.config.FrameworkConstants;
import com.mvvm.lux.framework.config.LocalBroadcast;
import com.mvvm.lux.framework.utils.SharePreferencesUtil;


/**
 * Created by iceman on 16/3/18 14:17
 * 邮箱：xubin865@pingan.com.cn
 * 关于islogin和isRegister:
 * 所有手机号码+收到的短信验证码都可以登录.
 * 但是只有后台绑定了地区id的才代表正式注册的医生账号.
 * 对用户而言,两个流程是一体的.
 * 登录的账号如果没有绑定地区,那么最终也不能算登录状态.
 */
public class Session {

    private static User user;

    private static LoginManager loginManager = new LoginManager();

    public static User getUser() {
        if (user == null) {
            readFromSp();
        }
        return user;
    }

    public void setUser() {
            SharePreferencesUtil.saveString(FrameworkConstants.SaveKeys.USER_TOKEN, user.token);
    }

    public static String getToken() {
        if (isLogin()) {
            return user.token;
        } else {  
            return "";
        }
    }

    public static String getZoneCode() {
        User user = getUser();
        if (user.isBindCard) {
            return user.zoneCode;
        } else {
            return "";
        }
    }
    public static String getZoneName() {
        User user = getUser();
        if (user.isBindCard) {
            return user.zoneName;
        } else {
            return "";
        }
    }

    public static void startLogin(FragmentActivity activity, LoginManager.LoginCallback loginCallback) {
        loginManager.setLoginCallback(loginCallback);
        FrameWorkConfig.frameworkSupport.goToActivity(activity,10708,null);
    }

    /**
     * 用户是否登录.包含未绑卡的情况
     *
     * @return
     */
    public static boolean isLogin() {
        User user = getUser();
        return user.isLogin;
//        return user.isLogin && user.isRegister;
    }

    /**
     * 用户是否已经绑卡
     *
     * @return
     */
    public static boolean isBindCard() {
        User user = getUser();
        return user.isLogin && user.isBindCard;
    }

    public static void saveUserinfo() {
        SharePreferencesUtil.saveString(FrameworkConstants.SaveKeys.SESSION_KEY, new Gson().toJson(user));
    }

    private static void readFromSp() {
        String saveUserString = SharePreferencesUtil.getString(FrameworkConstants.SaveKeys.SESSION_KEY, "");
        if (!TextUtils.isEmpty(saveUserString)) {
            user = new Gson().fromJson(saveUserString, User.class);
        } else {
            user = new User();
        }
    }

    public static void logout() {
        user = new User();
        SharePreferencesUtil.saveString(FrameworkConstants.SaveKeys.SESSION_KEY, "");
        LocalBroadcast.sendLocalBroadcast(FrameworkConstants.ActionKeys.LOG_OUT);
    }

    public static void bindout() {
        User user = getUser();
        user.isBindCard = false;
        user.siCard = null;
        user.birthday = null;
        user.hiredate = null;
        user.retired = null;
        user.sex = null;

        saveUserinfo();
        LocalBroadcast.sendLocalBroadcast(FrameworkConstants.ActionKeys.LOGIN);

    }




    public static class User implements UnProguard {
        /**
         * 是否登录
         */
        public boolean isLogin = false;
        /**
         * 是否已绑定卡号
         */
        public boolean isBindCard = false;
        public String userName;
        public String token;
        public String systemCategory;
        public String phoneNumber;
        public String siCard;
        public String idCard;
        public String sex;
        public String zoneCode = "0086";
        public String zoneName;
        public String depart;
        public String birthday;
        public String hiredate;
        public String retired;
        public String regionCode;
        public String deviceid;
        public String pinganfuToken;
        public String secondToken;
        public String suumUserId;
        public String email;
        public String password;

        User() {
        }

        @Override
        public String toString() {
            return isLogin + "-" + userName + "-" + token + "-" + phoneNumber;
        }

    }
}
