package com.mvvm.lux.burqa.http;

import com.mvvm.lux.burqa.model.response.CategoryResponse;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.model.response.RecommendResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/6 10:56
 * @Version
 */
public interface ApiService {

    @GET("recommend.json")
    Observable<List<RecommendResponse>> getRecommend();

    @GET("{url}")
    Observable<ComicResponse> getComic(@Path("url") String url);

    @GET("{url}")
    Observable<ComicPageResponse> getChapter(@Path("url") String url);

    @GET("0/category.json")
    Observable<List<CategoryResponse>> getCategory();
}
