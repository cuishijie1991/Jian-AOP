package com.tracy.slark;

import android.content.Context;
import android.view.View;

import com.tracy.slark.model.ClickAction;
import com.tracy.slark.model.PageAction;

/**
 * Created by cuishijie on 2018/1/28.
 */

public final class Slark {

    public final static void trackClickEvent(View view) {
        if (view != null) {
            SlarkAgent.addAction(new ClickAction(view));
        }
    }

    public final static void trackPageEvent(Context context, boolean pageStart) {
        SlarkAgent.addAction(new PageAction(context, pageStart));
    }

}
