package com.tracy.plugin.visitor;

/**
 * Created by shijiecui on 2018/2/1.
 */

public enum InjectPage {
    OTHER("", ""),

    APPLICATION("Application", ""),

    ACTIVITY("Activity", ""),

    FRAGMENT("Fragment", "");

    public String name;
    public String type;

    InjectPage(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean match(String superClass) {
        return superClass != null && superClass.endsWith(type);
    }
}
