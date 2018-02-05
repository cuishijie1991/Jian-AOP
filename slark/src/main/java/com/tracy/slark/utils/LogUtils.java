package com.tracy.slark.utils;

import android.util.Log;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class LogUtils {
    public static final String TAG = "Slark";

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (Constant.isDebug) {
            Log.e(tag, msg);
        }
    }
}
