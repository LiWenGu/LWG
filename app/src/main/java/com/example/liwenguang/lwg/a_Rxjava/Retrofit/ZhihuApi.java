package com.example.liwenguang.lwg.a_Rxjava.Retrofit;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liwenguang on 2016/11/9.
 */

public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getListDaily();
}
