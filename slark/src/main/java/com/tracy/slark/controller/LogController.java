package com.tracy.slark.controller;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.tracy.slark.SlarkService;
import com.tracy.slark.controller.action.PostLogAction;
import com.tracy.slark.controller.model.ClickEvent;
import com.tracy.slark.controller.model.EventConfig;
import com.tracy.slark.dao.SlarkLog;
import com.tracy.slark.utils.DBUtil;
import com.tracy.slark.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiecui on 2018/5/4.
 */

public class LogController {
    private static LogController instance;
    private static final List<EventConfig> mConfigs = new ArrayList<>();
    private static final int MAX_LOG_COUNT = 20;

    public synchronized static final LogController getInstance() {
        if (instance == null) {
            instance = new LogController();
            instance.updateConfigs();
        }
        return instance;
    }

    private LogController() {
    }

    private boolean updateConfigs() {
        String configStr = PreferenceUtil.getInstance().getString(PreferenceUtil.DefaultKeys.KEY_LOCAL_CONFIG);
        if (!TextUtils.isEmpty(configStr)) {
            try {
                List<EventConfig> eventConfigs = JSON.parseArray(configStr, EventConfig.class);
                if (eventConfigs != null && !eventConfigs.isEmpty()) {
                    mConfigs.clear();
                    mConfigs.addAll(eventConfigs);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void onClickEvent(String eventStr) {
        if (!TextUtils.isEmpty(eventStr)) {
            ClickEvent event = JSON.parseObject(eventStr, ClickEvent.class);
            if (checkEventMatchConfig(event)) {
                saveOrPostLog(event);
            }
        }
    }

    private boolean checkEventMatchConfig(ClickEvent event) {
        if (event != null) {
            for (EventConfig config : mConfigs) {
                if (config.page.equals(event.page) && config.path.equals(event.path)) {
                    event.setId(config.id);
                    return true;
                }
            }
        }
        return false;
    }

    private void saveOrPostLog(ClickEvent event) {
        DBUtil.getInstance().save(event);
        List<SlarkLog> logs = DBUtil.getInstance().getLogs();
        if (logs != null && logs.size() >= MAX_LOG_COUNT) {
            SlarkService.getInstance().addAction(new PostLogAction(logs));
        }
    }

}
