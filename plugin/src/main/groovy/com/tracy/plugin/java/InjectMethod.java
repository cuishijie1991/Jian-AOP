package com.tracy.plugin.java;

/**
 * Created by shijiecui on 2018/1/31.
 */

public enum InjectMethod {
    CLICK("onClick"),
    ITEM_CLICK("onItemClick");

    public String method;

    InjectMethod(String method) {
        this.method = method;
    }
}
