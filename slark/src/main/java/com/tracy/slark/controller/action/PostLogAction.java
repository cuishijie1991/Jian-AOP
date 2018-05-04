package com.tracy.slark.controller.action;

import com.alibaba.fastjson.JSONArray;
import com.tracy.slark.dao.SlarkLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiecui on 2018/5/4.
 */

public class PostLogAction implements IAction {
    private List<SlarkLog> mLogs = new ArrayList<>();

    public PostLogAction(List<SlarkLog> logs) {
        if (logs != null) {
            mLogs.addAll(logs);
        }
    }

    @Override
    public String toActString() {
        JSONArray logArray = new JSONArray();
        for (SlarkLog log : mLogs) {
            logArray.add(log.getText());
        }
        return logArray.toJSONString();
    }

    @Override
    public int getActType() {
        return ActionType.POST_LOG;
    }
}
