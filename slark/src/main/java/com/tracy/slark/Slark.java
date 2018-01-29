package com.tracy.slark;

import android.view.View;

import com.tracy.slark.model.ClickAction;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class Slark {

    public static void trackClickEvent(View view) {
        if (view != null) {
            SlarkAgent.addAction(new ClickAction(view));
        }
    }
}
