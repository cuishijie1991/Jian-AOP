package com.tracy.slark.controller;

import android.support.annotation.NonNull;

/**
 * Created by shijiecui on 2018/2/6.
 */

public interface ILogCollector {
    @NonNull
    String getName();

    void trackClickEvent(String event);

    void trackPageEvent(String event);
}
