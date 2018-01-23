package com.tech.track;

import android.util.Log;
import android.view.View;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class Track {
    public static void trackClick(View view) {
        Log.e("Track########", "view " + view.toString() + " is clicked");
    }

    public static void trackPageStart(String page) {
        Log.e("Track########", page + " ====> onPageStart " + System.currentTimeMillis());
    }

    public static void trackPageStop(String page) {
        Log.e("Track########", page + " ====> onPageStop " + System.currentTimeMillis());
    }
}
