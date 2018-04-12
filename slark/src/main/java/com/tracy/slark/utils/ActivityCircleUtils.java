package com.tracy.slark.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.tracy.slark.network.Network;

import java.util.UUID;

public final class ActivityCircleUtils {
    private static int activityCount = 0;
    private static UUID uuidForeground;
    private static long tsForegroundEntering;
    private static long tsOnActivityResumed;
    private static final String TAG = "slark";


    public static final void registerMonitor(Application app) {
        Network.getInstance().sendRequestWithHttpURLConnection(app.getBaseContext(), Network.GET, "");
        Log.e(TAG, "action=register");
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityCount == 0) {
                    uuidForeground = UUID.randomUUID();
                    tsForegroundEntering = System.currentTimeMillis();
                }

                ++activityCount;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activityCount > 0) {
                    tsOnActivityResumed = System.currentTimeMillis();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (activityCount > 0) {
                    long current = System.currentTimeMillis();
                    long delta = current - tsOnActivityResumed;

                    //过滤亮屏导致的生命周期快速切换事件，100ms以上才上报
                    if (delta > 100) {
                        Log.e(TAG, "action=stay&activity=" + activity.getClass().getSimpleName() + "&delta=" + delta);
                    }
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //ensure foreground entry is recorded
                if (activityCount > 0) {
                    --activityCount;
                    //just enter background
                    if (activityCount == 0) {
                        long current = System.currentTimeMillis();
                        long delta = current - tsForegroundEntering;

                        //过滤亮屏导致的生命周期快速切换事件，大于400ms以上才上报
                        if (delta > 400) {
                            Log.e(TAG, "action=quit&activity=" + activity.getClass().getSimpleName() + "&tsForegroundEntering=" + tsForegroundEntering);
                        }
                    }
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
