package com.tracy.slark;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tracy.slark.controller.ILogCollector;
import com.tracy.slark.controller.action.ClickAction;
import com.tracy.slark.controller.action.GetConfigAction;
import com.tracy.slark.controller.action.PageAction;
import com.tracy.slark.controller.model.EventConfig;
import com.tracy.slark.utils.DBUtil;
import com.tracy.slark.utils.PreferenceUtil;
import com.tracy.slark.utils.TraceUtils;
import com.tracy.slark.view.EventPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tech.guazi.component.network.PhoneInfoHelper;

/**
 * Created by cuishijie on 2018/1/28.
 */

public final class Slark {

    public static final int VIEW_TAG = 0xff000001;
    public static final HashMap<String, List<EventConfig>> configMap = new HashMap<>();
    public static final List<String> ignoreList = new ArrayList<>();

    /**
     * 设置日志接收器，灵活起见，暂未集成，需要自定义,并在应用初始化时调用
     *
     * @param collector
     */
    public final static void setLogCollector(ILogCollector collector) {
        SlarkService.getInstance().addLogCollector(collector);
    }

    public final static void init(Application context) {
        PreferenceUtil.getInstance().init(context);
        DBUtil.getInstance().init(context);
        PhoneInfoHelper.init(context);
        SlarkService.getInstance().addAction(new GetConfigAction());
    }

    /**
     * track click events
     *
     * @param view
     * @pluginInject
     */
    public final static void trackClickEvent(View view) {
        if (view != null) {
            SlarkService.getInstance().addAction(new ClickAction(view));
        }
    }


    /**
     * @param pageRef   this (Activity or Fragment)
     * @param pageStart 进入/退出页面
     * @pluginInject
     */
    public final static void trackPageEvent(Object pageRef, boolean pageStart) {
        SlarkService.getInstance().addAction(new PageAction(pageRef, pageStart));
    }

    public final static boolean hasEventConfig(View view) {
        if (view.getContext() != null) {
            String pageKey = view.getContext().getClass().getSimpleName();
            if (view.getTag(VIEW_TAG) == null) {
                view.setTag(VIEW_TAG, TraceUtils.generateViewTree(view));
            }
            String eventKey = (String) view.getTag(VIEW_TAG);
            if (configMap.containsKey(pageKey)) {
                List<EventConfig> pageEvents = configMap.get(pageKey);
                for (EventConfig event : pageEvents) {
                    if (eventKey.equals(event.path)) {
                        return true;
                    }
                }
            }
            if (ignoreList.contains(TraceUtils.generateIgnoreViewId(pageKey, eventKey))) {
                Toast.makeText(view.getContext(), "已被设置【忽略】", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public final static void showEventDialog(View view) {
        if (view.getContext() != null) {
            EventPopup popup = new EventPopup(view, new EventPopup.IEventPopupListener() {
                @Override
                public void onConfirm(EventPopup pop) {
                    String eventValue = pop.getEventId();
                    if (TextUtils.isEmpty(eventValue)) {
                        Toast.makeText(view.getContext(), "请先编辑事件ID后再保存！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String pageKey = view.getContext().getClass().getSimpleName();
                    List<EventConfig> pageEvents;
                    if (configMap.containsKey(pageKey)) {
                        pageEvents = configMap.get(pageKey);
                    } else {
                        pageEvents = new ArrayList<>();
                        configMap.put(pageKey, pageEvents);
                    }
                    if (view.getTag(VIEW_TAG) == null) {
                        view.setTag(VIEW_TAG, TraceUtils.generateViewTree(view));
                    }
                    String eventKey = (String) view.getTag(VIEW_TAG);
                    EventConfig event = new EventConfig(pageKey, eventKey, eventValue);
                    pageEvents.add(event);
                    Log.e("SlarkEventConfig", "Save => " + event.toString());
                }

                @Override
                public void onIgnore(EventPopup pop) {
                    String pageKey = view.getContext().getClass().getSimpleName();
                    if (view.getTag(VIEW_TAG) == null) {
                        view.setTag(VIEW_TAG, TraceUtils.generateViewTree(view));
                    }
                    String eventKey = (String) view.getTag(VIEW_TAG);
                    ignoreList.add(TraceUtils.generateIgnoreViewId(pageKey, eventKey));
                    Log.e("SlarkEventConfig", "Ignore => pageKey = " + pageKey + " | eventKey = " + eventKey);
                }
            });
            popup.show();
        }
    }
}
