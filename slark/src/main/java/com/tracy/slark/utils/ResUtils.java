package com.tracy.slark.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class ResUtils {
    public static final String TAG = "ResUtils";
    private static Object idResource = null;

    public static String getResourceNameById(Class<?> rClass, int id) {
        if (rClass != null) {
            try {
                Class<?> aClass = getIdResource(rClass).getClass();
                Field[] fields = aClass.getFields();
                for (int i = 0; i < fields.length; i++) {
                    String name = fields[i].getName();
                    if (id == fields[i].getInt(aClass)) {
                        return name;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return Constant.UNKNOWN;
    }

    private static Object getIdResource(Class<?> resource) {
        if (idResource == null) {
            try {
                Class<?>[] classes = resource.getClasses();
                for (Class<?> c : classes) {
                    int i = c.getModifiers();
                    String className = c.getName();
                    String s = Modifier.toString(i);
                    if (s.contains("static") && className.contains("id")) {
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
