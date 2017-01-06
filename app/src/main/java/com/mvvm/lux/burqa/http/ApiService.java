package com.mvvm.lux.burqa.http;

import com.mvvm.lux.burqa.model.response.RecommendResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/6 10:56
 * @Version
 */
public interface ApiService {

    @GET("recommend")
    Observable<RecommendResponse> getRecommend();

}
