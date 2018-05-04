package com.tracy.slark.utils;

import com.tracy.slark.controller.model.EventConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shijiecui on 2018/5/3.
 */

public class SlarkUtil {

    public final static String toJsonString(HashMap<String, List<EventConfig>> map) {
        JSONObject object = new JSONObject();
        if (map != null && !map.isEmpty()) {
            try {
                Set<Map.Entry<String, List<EventConfig>>> entries = map.entrySet();
                for (Map.Entry<String, List<EventConfig>> entry : entries) {
                    String key = entry.getKey();
                    HashMap<String, String> _map = (HashMap<String, String>) entry.getValue();
                    JSONObject objectPage = new JSONObject();
                    for (Map.Entry<String, String> entry1 : _map.entrySet()) {
                        String pageKey = entry1.getKey();
                        String pageValue = entry1.getValue();
                        objectPage.put(pageKey, pageValue);
                    }
                    object.put(key, objectPage);
                }
                return object.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map.toString();
    }
}
