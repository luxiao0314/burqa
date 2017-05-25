package com.mvvm.lux.framework.http.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description 拦截所有url并打印,这里可以添加公共参数和请求头(post/get都可以)
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/30 22:58
 * @Version
 */
public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
            request = addGetParams(request);
        } else if (request.method().equals("POST")) {
            request = addPostParams(request);
        }
        return chain.proceed(request);
    }



    //get请求 添加公共参数 签名
    private static Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
//                .addQueryParameter("version","" )
//                .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
                .build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append("&").append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
        }
//        Log.e("get-拦截所有请求参数",buffer.toString());
        httpUrl = httpUrl.newBuilder()
//                .addQueryParameter("sign", MD5.toMD5(buffer.toString()))
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 添加签名和公共参数
    private Request addPostParams(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            formBody = bodyBuilder
//                    .addEncoded("clienttype", String.valueOf(NetConstants.CLIENT_TYPE_ANDROID))
//                    .addEncoded("version","" )
//                    .addEncoded("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();

            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();

            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            }

            Collections.sort(nameList);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameList.size(); i++) {
                builder.append("&").append(nameList.get(i)).append("=")
                        .append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
            }

//            Log.e("post-拦截所有请求参数",builder.toString());

            formBody = bodyBuilder
//                    .addEncoded("sign", MD5.toMD5(builder.toString()))
                    .build();
            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }
}
