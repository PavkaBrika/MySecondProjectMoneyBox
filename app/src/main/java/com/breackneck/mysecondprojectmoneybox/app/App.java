package com.breackneck.mysecondprojectmoneybox.app;

import android.app.Application;
import android.util.Log;

import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.e("TAG", "Yandex Ads initiliazed");
            }
        });
        MobileAds.setUserConsent(false);
    }
}
