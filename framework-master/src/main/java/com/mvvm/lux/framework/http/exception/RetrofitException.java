package com.mvvm.lux.framework.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.mvvm.lux.framework.config.FrameWorkConfig;
import com.mvvm.lux.framework.utils.Logger;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Tamic on 2016-08-12.
 */
public class RetrofitException {

    private static final int UNAUTHORIZED = 401;    //未认证
    private static final int FORBIDDEN = 403;   //被禁止
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int ACCESS_DENIED = 302;

    public static Throwable handleException(java.lang.Throwable e) {
        Logger.e("RetrofitException", e.getMessage());
        Throwable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new Throwable(e, ERROR.HTTP_ERROR);    //协议出错
            switch (httpException.code()) {
                case UNAUTHORIZED:  //一般是token过期,在这里处理可以,抛一个token过期的异常也可以,retry可以解决
                    ex.setMessage("token过期");
                    FrameWorkConfig.frameworkSupport.onSessionInvaild();
                    break;
                case FORBIDDEN:
                    ex.setMessage("请求是被禁止的");
                    break;
                case NOT_FOUND:
                    ex.setMessage("HTTP NOT FOUND");
                    break;
                case REQUEST_TIMEOUT:
                    ex.setMessage("请求超时");
                    break;
                case GATEWAY_TIMEOUT:
                    ex.setMessage("网关超时");
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.setMessage("内部服务器错误");
                    break;
                case BAD_GATEWAY:
                    ex.setMessage("无效网关");
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.setMessage("找不到服务器");
                    break;
                case ACCESS_DENIED:
                    ex.setMessage("拒绝访问");
                    break;
                default:
                    ex.setMessage("网络错误");
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {  //服务器错误,直接返回服务器错误信息
            ServerException resultException = (ServerException) e;
            ex = new Throwable(resultException, resultException.code);
            ex.setMessage(resultException.message);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new Throwable(e, ERROR.PARSE_ERROR);
            ex.setMessage("解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new Throwable(e, ERROR.NETWORD_ERROR);
            ex.setMessage("连接失败");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new Throwable(e, ERROR.SSL_ERROR);
            ex.setMessage("证书验证失败");
            return ex;
        } else if (e instanceof java.security.cert.CertPathValidatorException) {
            ex = new Throwable(e, ERROR.SSL_NOT_FOUND);
            ex.setMessage("证书路径没找到");
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new Throwable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new Throwable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new Throwable(e, ERROR.FORMAT_ERROR);
            ex.setMessage("类型转换出错");
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new Throwable(e, ERROR.NULL);
            ex.setMessage("数据有空");
            return ex;
        } else if (e instanceof FormatException) {
            FormatException resultException = (FormatException) e;
            ex = new Throwable(resultException, resultException.code);
            ex.setMessage(resultException.message);
            return ex;
        }else if (e instanceof CusException) {
            CusException resultException = (CusException) e;
            ex = new Throwable(resultException, resultException.code);
            ex.setMessage(resultException.message);
            return ex;
        } else {
            ex = new Throwable(e, ERROR.UNKNOWN);
            ex.setMessage(e.getMessage());
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * token不存在
         */
        public static final int TOKEN_NOT_EXIST = 1000;
        /**
         * token无效
         */
        public static final int TOKEN_INVALID = 1001;
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 证书未找到
         */
        public static final int SSL_NOT_FOUND = 1007;

        /**
         * 出现空值
         */
        public static final int NULL = -100;

        /**
         * 格式错误
         */
        public static final int FORMAT_ERROR = 1008;
    }

}

