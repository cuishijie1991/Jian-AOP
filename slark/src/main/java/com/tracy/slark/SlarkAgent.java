package com.tracy.slark;

import android.util.Log;

import com.tracy.slark.model.IAction;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class SlarkAgent {

    public static void addAction(IAction action) {
        Log.e("Slark", action.toActionString());
    }

}
