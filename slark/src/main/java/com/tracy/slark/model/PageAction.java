package com.tracy.slark.model;

import android.app.Activity;
import android.content.Context;

/**
 * Created by shijiecui on 2018/1/30.
 */

public class PageAction implements IAction {

    public String actPage;

    public long actTime;

    public boolean pageStart;

    public PageAction(String actPage, boolean pageStart, long actTime) {
        this.actPage = actPage;
        this.actTime = actTime;
        this.pageStart = pageStart;
    }

    public PageAction(Context context, boolean pageStart) {
        this.actPage = context.getClass().getSimpleName();
        this.actTime = System.currentTimeMillis();
        this.pageStart = pageStart;
    }

    @Override
    public String toActionString() {
        StringBuilder sb = new StringBuilder();
        sb.append("actPage=").append(this.actPage)
                .append("&PageStart=").append(this.pageStart)
                .append("&actTime=").append(this.actTime);
        return sb.toString();
    }

    @Override
    public long getActTime() {
        return this.actTime;
    }
}
