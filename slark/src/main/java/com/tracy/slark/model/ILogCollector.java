package com.tracy.slark.model;

/**
 * Created by shijiecui on 2018/2/6.
 */

public interface ILogCollector {
    void trackClickEvent(String event);

    void trackPageEvent(String event);
}
