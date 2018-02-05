package com.tracy.slark;

import android.view.View;

import com.tracy.slark.model.ClickAction;
import com.tracy.slark.model.PageAction;
import com.tracy.slark.utils.Constant;

/**
 * Created by cuishijie on 2018/1/28.
 */

public final class Slark {

    public final static void initConfig(boolean debug) {
        Constant.isDebug = debug;
    }

    /**
     * track click events
     *
     * @param view
     */
    public final static void trackClickEvent(View view) {
        if (view != null) {
            SlarkAgent.getInstance().addAction(new ClickAction(view));
        }
    }


    /**
     * @param pageRef   this (Activity or Fragment)
     * @param pageStart
     */
    public final static void trackPageEvent(Object pageRef, boolean pageStart) {
        SlarkAgent.getInstance().addAction(new PageAction(pageRef, pageStart));
    }

}
