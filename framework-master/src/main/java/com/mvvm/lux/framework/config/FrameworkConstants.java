package com.mvvm.lux.framework.config;

/**
 * Created by iceman on 16/1/25.
 */
public class FrameworkConstants {
    //intent中取值所用的标记
    public static String MESSAGE_CODE = "message_code";
    public static String MESSAGE_CONTENT = "message_content";
    public static String MESSAGE_DATA = "message_data";
    //事件结果的状态类型
    public static int MESSAGE_SUCCESS = 0;
    public static int MESSAGE_FAIL = 1;
    public static int MESSAGE_CANCEL = 2;
    //页面数据跳转赋值
    public static String PAGE_BASE_EXCHANGEMODEL = "BaseExchangeModel";

    //业务接口的code定义
    public static int CODE_BUSINESS_SUCCESS = 10000;
    public static int CODE_BUSINESS_FAIL = 20000;
    public static int CODE_CMS_SUCCESS = 200;

    public interface ActionKeys {
        /**
         * 登录
         */
        String LOGIN = "login";
        /**
         * 登出
         */
        String LOG_OUT = "log_out";
    }

    public interface SaveKeys {
        String SESSION_KEY = "session_key";
        String LAST_LOGIN_NAME = "last_login_name";
        //新加
        String USER_TOKEN = "user_token";
    }


}
