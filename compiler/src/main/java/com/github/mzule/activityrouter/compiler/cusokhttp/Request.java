package com.github.mzule.activityrouter.compiler.cusokhttp;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/22 10:10
 * @Version
 */
public class Request {
    String url;
    String method;
    Headers headers;
    Body requestBody;

    public Request() {
        this.url = url;
    }

    public Request(String url, String method, Headers headers, Body requestBody) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public Request url(String url) {
        this.url = url;
        return this;
    }
}
