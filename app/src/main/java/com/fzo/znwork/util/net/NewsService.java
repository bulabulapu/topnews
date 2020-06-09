package com.fzo.znwork.util.net;

import com.fzo.znwork.util.model.News;
import com.fzo.znwork.util.model.PastNews;
import com.fzo.znwork.util.model.PushNews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsService { // retrofit的请求接口

    @GET("tophub/hotnews") // 请求热点新闻
    Call<List<News>> getNews(@Query("currentPage") int page, @Query("pageSize") int count, @Query("plantform") String platform);

    @GET("tophub/today/{month}/{day}") // 请求历史今日
    Call<List<PastNews>> getPastNews(@Path("month") String month, @Path("day") String date);

    @GET("tophub/typenews?") // 分类新闻
    Call<List<PushNews>> getPushNews(@Query("type") String type);
}
