package com.mvvm.lux.framework.http.authenticator;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/30 09:13
 * @Version
 */
public class RefreshTokenAuthenticator implements Authenticator {
    @Override public Request authenticate(Route route, Response response)
            throws IOException {
//        Your.sToken = service.refreshToken();
        return response.request().newBuilder()
//                .addHeader("Authorization", Your.sToken)
                .build();
    }
}
