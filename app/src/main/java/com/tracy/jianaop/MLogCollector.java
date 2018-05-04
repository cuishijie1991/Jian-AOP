package com.tracy.jianaop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.tracy.slark.controller.ILogCollector;

/**
 * Created by shijiecui on 2018/2/6.
 */

public class MLogCollector implements ILogCollector {
    @NonNull
    @Override
    public String getName() {
        return "MLogCollector";
    }

    @Override
    public void trackClickEvent(String event) {
        Log.e("AOP", event);
    }

    @Override
    public void trackPageEvent(String event) {
        Log.e("AOP", event);
    }
}
