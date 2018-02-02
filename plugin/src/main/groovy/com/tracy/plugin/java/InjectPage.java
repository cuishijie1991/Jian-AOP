package com.tracy.plugin.java;

/**
 * Created by shijiecui on 2018/2/1.
 */

public enum InjectPage {
    OTHER(""),

    ACTIVITY("Activity"),

    FRAGMENT("Fragment");

    public String type;

    InjectPage(String type) {
        this.type = type;
    }
}
