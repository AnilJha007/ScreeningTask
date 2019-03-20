package com.wipro.screeningtask;

import android.app.Application;

import com.wipro.screeningtask.network.ApiClient;
import com.wipro.screeningtask.network.ApiInterface;

public class MyApplication extends Application {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/";
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApiInterface getApiInterface() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }

    public static MyApplication getMyApplication() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
}
