package com.mvvm.lux.burqa.http;

import com.mvvm.lux.burqa.model.response.AuthorDesResponse;
import com.mvvm.lux.burqa.model.response.CategoryResponse1;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.model.response.HotResponse;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.model.response.SearchResponse;
import com.mvvm.lux.burqa.model.response.SubjectDesResponse;
import com.mvvm.lux.burqa.model.response.SubjectResopnse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Observable<CategoryResponse1> getCategory();

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<ClassifyResponse>> getClassify(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("search/show/0/{keyword}/0.json")
    Observable<List<SearchResponse>> getSearch(@Path(value="keyword", encoded=true) String keyword);    //encoded 解决传入参数乱码问题

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<HotResponse>> getHot(@Path("url") String url);

    @GET("/unableape/url/{name}/get")
    Observable getUnableApeName(@Header("Authorization") String authorization, @Path("name") String name);

    //http://v2.api.dmzj.com/UCenter/comics/100085704.json
    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<AuthorDesResponse> getAuthorDes(@Path("url") String url);

    @Headers("Referer:http://images.dmzj.com/")
    @GET("{url}")
    Observable<List<SubjectResopnse>> getSubject(@Path("url") String ur);

    //http://v2.api.dmzj.com/subject/172.json
    @GET("{url}")
    Observable<SubjectDesResponse> getSubjectDes(@Path("url") String url);
}
