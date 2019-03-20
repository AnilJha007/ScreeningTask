package com.wipro.screeningtask.exercise.ui;

import com.wipro.screeningtask.MyApplication;
import com.wipro.screeningtask.network.ApiClient;
import com.wipro.screeningtask.network.ApiInterface;

public class MyApplicationTest extends MyApplication {

    public static final String BASE_URL = "http://localhost:8080/";
    private static MyApplicationTest myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public ApiInterface getApiInterface() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }

    public static MyApplicationTest getMyApplication() {
        if (myApplication == null) {
            myApplication = new MyApplicationTest();
        }
        return myApplication;
    }
}
