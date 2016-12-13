package com.example.coolweather.app;

import org.litepal.LitePalApplication;

/**
 * Created by li-pc on 2016/12/12.
 */

public class MyApp extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}