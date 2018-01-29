package com.tracy.slark.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhaohaiyang on 2018/1/24.
 */

public class TraceUtils {

    private static int KEY_GENERATION = 0xff000001;
    private static int KEY_PATH = 0xff000002;

    public static String getPath(View view) {
        if (view.getTag(KEY_PATH) == null) {
            setAllTag(getParentView(view));
        }
        return (String) view.getTag(KEY_PATH);
    }

    public static String getCurrentPath(View view) {
        StringBuilder sb = new StringBuilder();
        do {
            //1. 构造ViewPath中于view对应的节点:ViewType[index]
            String viewType = view.getClass().getSimpleName();
            if (sb.length() == 0) {
                sb.append(viewType);
            } else {
                sb.insert(0, viewType + "/");
            }
            if (view.getParent() instanceof View) {
                view = (View) view.getParent();
            } else {
                return sb.toString();
            }
        } while (view instanceof View);//2. 将view指向上一级的节点
        return sb.toString();
    }

    public static void setAllTag(ViewGroup root) {
        String path = "";
        if (root.getTag(KEY_PATH) == null) {
            //如果根结点也没有设置tag
            if (root.getParent() instanceof ViewGroup) {
                setAllTag(getParentView(root));
                return;
            } else {
                path = getCurrentPath(root);
            }
        } else {
            //如果根结点有tag，就是根结点tag
            path = (String) root.getTag(KEY_PATH);
        }
        int g = (int) root.getTag(KEY_GENERATION);
        int sum = root.getChildCount();
        for (int i = 0; i < sum; i++) {
            String tag = path + "/" + root.getChildAt(i).getClass().getSimpleName() + i;
            if (g != 1) {
                if (root.getChildAt(i).getTag(KEY_PATH) == null) {
                    for (int j = 1; j < g; j++) {
                        tag = tag + "-0";
                    }
                } else {
                    tag = tag + root.getChildAt(i).getTag(KEY_PATH);
                }
            }
            root.getChildAt(i).setTag(KEY_PATH, tag);
            if (root.getChildAt(i) instanceof ViewGroup) {
                if (root.getChildAt(i).getTag(KEY_GENERATION) == null) {
                    root.getChildAt(i).setTag(KEY_GENERATION, 1);
                }
                setAllTag((ViewGroup) root.getChildAt(i));
            }
        }
    }

    public static ViewGroup getParentView(View view) {
        if (view.getParent() instanceof ViewGroup) {
            view = (View) view.getParent();
        } else {
            return null;
        }
        return setGTag((ViewGroup) view);
    }

    private static ViewGroup setGTag(ViewGroup view) {
        if (view.getTag(KEY_GENERATION) == null) {
            view.setTag(KEY_GENERATION, 1);
        } else {
            view.setTag(KEY_GENERATION, (int) view.getTag(KEY_GENERATION) + 1);
        }
        return view;
    }
}
