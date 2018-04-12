package com.tracy.slark.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.tracy.slark.Slark;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuishijie on 2018/4/1.
 */

public class ConfigController {
    private Context mContext;
    private static ConfigController instance;
    private static final String SP_FILE_NAME = "Slark";
    private static final String SP_CONFIG_KEY = "Slark_Config";
    private static final String SP_IGNORE_KEY = "Slark_Ignore";
    private int TIMER_DELAY = 10 * 1000;
    private int TIMER_PERIOD = 10 * 1000;

    public synchronized static ConfigController getInstance() {
        if (instance == null)
            instance = new ConfigController();
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        readFromCache();
        TimerTask task = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                writeToCache();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIMER_DELAY, TIMER_PERIOD);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private synchronized void writeToCache() {
        SharedPreferences sp = mContext.getSharedPreferences(SP_FILE_NAME, 0);
        try {
            JSONArray configJA = new JSONArray(Slark.configMap);
            JSONArray ignoreJA = new JSONArray(Slark.ignoreList);
            sp.edit().putString(SP_CONFIG_KEY, configJA.toString()).commit();
            sp.edit().putString(SP_IGNORE_KEY, ignoreJA.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private synchronized void readFromCache() {
        SharedPreferences sp = mContext.getSharedPreferences(SP_FILE_NAME, 0);
        String config = sp.getString(SP_CONFIG_KEY, null);
        String ignore = sp.getString(SP_CONFIG_KEY, null);

        try {
            if (config != null) {
                //TODO
                JSONArray configJA = new JSONArray(config);
                if (configJA != null && configJA.length() > 0) {
                    for (int i = 0; i < configJA.length(); i++) {
//                        String key = configJA.optJSONArray()
                        String ignoreId = configJA.optString(i);
                        if (ignoreId != null) {
                            Slark.ignoreList.add(ignoreId);
                        }
                    }
                }
            }
            if (ignore != null) {
                JSONArray ignoreJA = new JSONArray(ignore);
                if (ignoreJA != null && ignoreJA.length() > 0) {
                    for (int i = 0; i < ignoreJA.length(); i++) {
                        String ignoreId = ignoreJA.optString(i);
                        if (ignoreId != null) {
                            Slark.ignoreList.add(ignoreId);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray ignoreJA = new JSONArray(Slark.ignoreList);
    }
}
