package com.tracy.slark.controller;

import android.support.annotation.NonNull;

/**
 * Created by shijiecui on 2018/5/4.
 */

public class DefaultLogCollector implements ILogCollector {
    private static DefaultLogCollector collector;

    public static synchronized DefaultLogCollector getInstance() {
        if (collector == null) {
            collector = new DefaultLogCollector();
        }
        return collector;
    }

    private DefaultLogCollector() {
    }

    @NonNull
    @Override
    public String getName() {
        return "DefaultLogCollector";
    }

    @Override
    public void trackClickEvent(String event) {
        LogController.getInstance().onClickEvent(event);
    }

    @Override
    public void trackPageEvent(String event) {
        //TODO handle page event
    }
}
