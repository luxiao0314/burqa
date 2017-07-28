package com.mvvm.lux.framework.http;

import android.support.annotation.NonNull;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/14 18:29
 * @Version
 */
public class ContentType {
    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_HTML = MediaType.parse("application/xml;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    @NonNull
    public static RequestBody postJson(String json) {
        return RequestBody.create(MEDIA_TYPE_JSON, json);
    }

    @NonNull
    public static RequestBody postString(String content) {
        return RequestBody.create(MEDIA_TYPE_PLAIN, content);
    }

    @NonNull
    public static HashMap<String, RequestBody> getMultipart(List<String> paths) {
        HashMap<String, RequestBody> files = new HashMap<>();
        for (String path : paths) {
            File file = new File(path);
            RequestBody body = RequestBody.create(guessMimeType(file.getName()), file);
            files.put("file[]\"; filename=\"" + file.getName(), body);
        }
        return files;
    }

    @NonNull
    public static List<MultipartBody.Part> getParts(List<String> paths) {
        List<MultipartBody.Part> files = new ArrayList<>();
        for (String path : paths) {
            File file = new File(path);
            //构建requestbody
            RequestBody requestFile = RequestBody.create(guessMimeType(file.getName()), file); //Content-Type:multipart/form-data
            //将resquestbody封装为MultipartBody.Part对象,既定格式
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);   //KEY:file
            files.add(body);
        }
        return files;
    }

    @NonNull
    public static MultipartBody.Part getPart(String path) {
        File file = new File(path);
        //构建requestbody
        RequestBody requestFile = RequestBody.create(guessMimeType(file.getName()), file); //Content-Type:multipart/form-data
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file); //Content-Type:multipart/form-data
        //将resquestbody封装为MultipartBody.Part对象,既定格式
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

    /**
     * 根据路径自己去匹配对应的content-type
     *
     * @param path
     * @return
     */
    private static MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }
}
