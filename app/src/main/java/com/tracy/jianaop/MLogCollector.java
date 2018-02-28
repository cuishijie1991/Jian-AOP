package com.tracy.jianaop;

import android.util.Log;

import com.tracy.slark.model.ILogCollector;

/**
 * Created by shijiecui on 2018/2/6.
 */

public class MLogCollector implements ILogCollector {
    @Override
    public void trackClickEvent(String event) {
        Log.e("AOP", event);
    }

    @Override
    public void trackPageEvent(String event) {
        Log.e("AOP", event);
    }
}
