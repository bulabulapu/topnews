package com.fzo.znwork;

import android.app.Application;
import android.content.Context;

public class NewsApplication extends Application {
    public static Context newsContext; // 全局context

    @Override
    public void onCreate() {
        super.onCreate();
        newsContext = getApplicationContext();
    }
}
