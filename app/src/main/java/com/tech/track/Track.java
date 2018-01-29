package com.tech.track;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class Track {
    public static void trackClick(View view) {
        Log.e("Slark########", "view " + view.toString() + " is clicked");
    }

    public static void trackPageStart(String page) {
        Log.e("Slark########", page + " ====> onPageStart " + System.currentTimeMillis());
    }

    public static void trackPageStop(String page) {
        Log.e("Slark########", page + " ====> onPageStop " + System.currentTimeMillis());
    }


    public static String getRClassName(Context context) {
        return context.getPackageName() + ".R";
    }
}
