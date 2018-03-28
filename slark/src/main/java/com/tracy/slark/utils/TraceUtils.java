package com.tracy.slark.utils;

import android.util.Log;
import android.view.View;

/**
 * Created by zhaohaiyang on 2018/1/24.
 */

public class TraceUtils {

    public static String generateViewTree(View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(view.getClass().getSimpleName()).append("/");
        while (view.getParent() != null && view.getParent() instanceof View) {
            view = (View) view.getParent();
            sb.append(view.getClass().getSimpleName()).append("/");
        }
        Log.e("TraceUtils", sb.toString());
        return sb.toString();
    }
}
