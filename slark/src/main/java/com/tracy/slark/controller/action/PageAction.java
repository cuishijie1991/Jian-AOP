package com.tracy.slark.controller.action;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

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

    /**
     * @param pageRef   this(Activity or Fragment)
     * @param pageStart
     */
    public PageAction(Object pageRef, boolean pageStart) {
        if (pageRef instanceof Context) {
            this.actPage = pageRef.getClass().getSimpleName();
        } else if (pageRef instanceof Fragment) {
            Fragment f = (Fragment) pageRef;
            if (f.getContext() != null) {
                this.actPage = f.getContext().getClass().getSimpleName() + "$" + pageRef.getClass().getSimpleName();
            }
        } else if (pageRef instanceof android.app.Fragment) {
            android.app.Fragment f = (android.app.Fragment) pageRef;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (f.getContext() != null) {
                    this.actPage = f.getContext().getClass().getSimpleName() + "$" + pageRef.getClass().getSimpleName();
                }
            }
        }
        this.actTime = System.currentTimeMillis();
        this.pageStart = pageStart;
    }

    @Override
    public String toActString() {
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

    @Override
    public int getActType() {
        return ActionType.PAGE;
    }
}
