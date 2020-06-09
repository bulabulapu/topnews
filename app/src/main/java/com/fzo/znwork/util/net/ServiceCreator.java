package com.fzo.znwork.util.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator { // 构造一个用于请求NewsService的客户端
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://webaddress/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static NewsService newsService = retrofit.create(NewsService.class);
}
