package com.tracy.slark.utils;

import com.tracy.slark.controller.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class ResUtils {
    public static final String TAG = "ResUtils";
    private static Object idResource = null;
    private static Map<String, String> cachedIdMap = new HashMap<>();

    public static String getResourceNameById(Class<?> rClass, int id) {
        if (rClass == null) {
            return Constant.UNKNOWN;
        }
        String resName = null;
        String cachedId = rClass + "$" + id;
        if (cachedIdMap.containsKey(cachedId)) {
            return cachedIdMap.get(cachedId);
        }
        try {
            Class<?> aClass = getIdResource(rClass).getClass();
            Field[] fields = aClass.getFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                if (id == fields[i].getInt(aClass)) {
                    resName = name;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resName = StringUtils.replaceUnderlineToHump(resName);
        if (resName == null) {
            resName = Constant.UNKNOWN;
        } else {
            cachedIdMap.put(cachedId, resName);
        }
        return resName;
    }

    private static Object getIdResource(Class<?> resource) {
        if (idResource == null) {
            try {
                Class<?>[] classes = resource.getClasses();
                for (Class<?> c : classes) {
                    int i = c.getModifiers();
                    String className = c.getName();
                    String s = Modifier.toString(i);
                    if (s.contains("static") && className.contains("$id")) {
                        return c.getConstructor().newInstance();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idResource;
    }

}
