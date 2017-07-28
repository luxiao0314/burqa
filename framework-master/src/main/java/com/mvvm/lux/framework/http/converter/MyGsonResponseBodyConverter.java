package com.mvvm.lux.framework.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.mvvm.lux.framework.http.base.BaseResponse;
import com.mvvm.lux.framework.http.exception.RetrofitException;
import com.mvvm.lux.framework.http.exception.ServerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 本来是想将[]数组格式的json用Gson进行转换了数组,结果是多余的,备以后用
 * @param <T>
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            if (response.startsWith("{")) { //如果是JsonObject就直接返回Gson对象
                return jsonResponse(value, response);
            } else if (response.startsWith("[")) {  //如果是json数组,就转换成数组
                return jsonResponse(value, response);
            } else {    //如果data的值是一个字符串,而不是标准json,那么直接返回
                return (T) response;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private T jsonResponse(ResponseBody value, String response) {
        BaseResponse baseResponse = mGson.fromJson(response, BaseResponse.class);
        //关注的重点，自定义响应码中非0的情况，一律抛出ApiException异常。
        //这样，我们就成功的将该异常交给onError()去处理了。
        if (!baseResponse.isOk()) {
            value.close();
            ServerException serverException = new ServerException(baseResponse.getCode(), baseResponse.getMessage());
            throw new RuntimeException(RetrofitException.handleException(serverException));
        }

        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(bis, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}