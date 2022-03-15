package com.js.test;

import android.app.Application;
import android.content.Intent;

import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.snart.sdk.api.INeedLoginListener;

public class TuyaSmartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TuyaHomeSdk.setDebugMode(true);
        TuyaHomeSdk.init(this);

        TuyaHomeSdk.setOnNeedLoginListner(new INeedLoginListener() {
            @Override
            public void onNeedLogin(Context context) {
                startActivity(new Intent(TuyaHomeApp.this, MainActivity.class)):
            }
        });
    }
}
