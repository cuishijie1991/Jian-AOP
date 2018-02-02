package com.tracy.slark;

import android.view.View;

import com.tracy.slark.model.ClickAction;
import com.tracy.slark.model.PageAction;

/**
 * Created by cuishijie on 2018/1/28.
 */

public final class Slark {

    /**
     * track click events
     *
     * @param view
     */
    public final static void trackClickEvent(View view) {
        if (view != null) {
            SlarkAgent.addAction(new ClickAction(view));
        }
    }


    /**
     * @param pageRef   this (Activity or Fragment)
     * @param pageStart
     */
    public final static void trackPageEvent(Object pageRef, boolean pageStart) {
        SlarkAgent.addAction(new PageAction(pageRef, pageStart));
    }

}
