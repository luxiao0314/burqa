package com.mvvm.lux.framework.http.exception;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/2/9 11:45
 * @Version
 */
public class CusException extends RuntimeException {
    public int code = -1200;
    public String message = "自定义异常";

    public CusException(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
