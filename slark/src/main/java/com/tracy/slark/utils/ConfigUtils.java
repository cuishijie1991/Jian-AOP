package com.tracy.slark.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by zhaohaiyang on 2018/4/2.
 */

public class ConfigUtils {

    public static HashMap<String, HashMap<String, String>> configMap = new HashMap<>();
    private Context mContext;

    public ConfigUtils(Context context) {
        this.mContext = context;
    }

    public HashMap<String, HashMap<String, String>> getConfig() {
        if (configMap.size() == 0) {
            initData();
        }
        return configMap;
    }

    private void initData() {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        String config = sp.getString("pointConfig", "");
        try {
            if (!TextUtils.isEmpty(config)) {
                JSONObject object = new JSONObject(config);
                Iterator<String> keys = object.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = object.optString(key);
                    JSONObject page = new JSONObject(value);
                    Iterator<String> iterator = page.keys();
                    while (iterator.hasNext()) {
                        HashMap<String, String> pageMap = new HashMap<>();
                        String pageKey = iterator.next();
                        String pageValue = page.optString(pageKey);
                        pageMap.put(pageKey, pageValue);
                        configMap.put(key, pageMap);
                    }
                }
            } else {
                Log.e("ConfigUtils", "Configuration not found Exception!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
