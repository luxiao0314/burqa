package com.mvvm.lux.burqa.http;

import com.mvvm.lux.burqa.model.response.CategoryResponse;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.model.response.HotResponse;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.model.response.SearchResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @Headers("Referer:http://images.dmzj.com/")
    @GET("recommend.json")
    Observable<List<RecommendResponse>> getRecommend();

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<ComicResponse> getComic(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<ComicPageResponse> getChapter(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("0/category.json")
    Observable<List<CategoryResponse>> getCategory();

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<ClassifyResponse>> getClassify(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<SearchResponse>> getSearch(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<HotResponse>> getHot(@Path("url") String url);
}
