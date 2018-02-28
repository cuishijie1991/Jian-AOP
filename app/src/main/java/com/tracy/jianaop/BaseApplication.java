package com.tracy.jianaop;

import android.app.Application;
import android.util.Log;

import com.tracy.slark.Slark;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("###", "application init!");
        Slark.setLogCollector(new MLogCollector());
    }
}
