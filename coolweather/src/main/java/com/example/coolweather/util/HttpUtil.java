package com.example.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by li-pc on 2016/12/12.
 * 用于网络加载的相关类
 */

public class HttpUtil {

    /**
     * 根据网址进行加载，并回传给回调函数
     * @param url       目标url
     * @param callback  需要回传的回调函数
     */
    public static void sendOkHttpRequest(String url, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
