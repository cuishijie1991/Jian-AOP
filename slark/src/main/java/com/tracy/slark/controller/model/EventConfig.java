package com.tracy.slark.controller.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by shijiecui on 2018/5/3.
 */

public class EventConfig implements Serializable {
    @JSONField(name = "page")
    public String page;
    @JSONField(name = "path")
    public String path;
    @JSONField(name = "id")
    public String id;

    public EventConfig(String page, String path, String id) {
        this.page = page;
        this.path = path;
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSON(this).toString();
    }
}
