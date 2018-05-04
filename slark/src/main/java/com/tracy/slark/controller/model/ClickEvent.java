package com.tracy.slark.controller.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by shijiecui on 2018/5/4.
 */

public class ClickEvent extends EventConfig {

    @JSONField(name = "time")
    public long actTime;

    public ClickEvent(String page, String path, String id) {
        super(page, path, id);
        actTime = System.currentTimeMillis();
    }

    public void setId(String id) {
        this.id = id;
    }
}
