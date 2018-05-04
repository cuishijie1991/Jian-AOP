package com.tracy.slark.controller.task;

import android.util.Log;

import com.tracy.slark.controller.ILogCollector;
import com.tracy.slark.controller.action.ActionType;
import com.tracy.slark.controller.action.IAction;
import com.tracy.slark.controller.model.EventConfig;
import com.tracy.slark.network.SlarkRequest;
import com.tracy.slark.utils.DBUtil;
import com.tracy.slark.utils.PreferenceUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import tech.guazi.component.network.fastjson.BaseResponse;
import tech.guazi.component.network.fastjson.ResponseCallback;

/**
 * Created by cuishijie on 2018/1/28.
 * 执行所有类型的Action
 */

public class ActionTask extends AbsTask {
    public static final String Slark = ActionTask.class.getSimpleName();
    private List<ILogCollector> mCollectors = new ArrayList<>();

    public ActionTask(List<ILogCollector> collectors, IAction action) {
        super(action);
        mCollectors.addAll(collectors);
    }

    @Override
    public void run() {

        switch (mAction.getActType()) {
            case ActionType.CLICK:
                if (!mCollectors.isEmpty()) {
                    for (ILogCollector mCollector : mCollectors) {
                        mCollector.trackClickEvent(mAction.toActString());
                    }
                }
                break;
            case ActionType.PAGE:
                if (!mCollectors.isEmpty()) {
                    for (ILogCollector mCollector : mCollectors) {
                        mCollector.trackPageEvent(mAction.toActString());
                    }
                }
                break;
            case ActionType.POST_CONFIG:
                SlarkRequest.getInstance().postConfig(mAction.toActString(), new ResponseCallback<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {
                        Log.d(Slark, "config upload is succeed!");
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        Log.d(Slark, "config upload is failed!");
                    }
                });
                break;
            case ActionType.GET_CONFIG:
                SlarkRequest.getInstance().getConfig(new ResponseCallback<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {
                        Log.d(Slark, "config getting is succeed!");
                        if (baseResponse != null && baseResponse.data != null) {
                            if (baseResponse.data instanceof JSONArray) {
                                String data = baseResponse.data.toString();
                                List<EventConfig> configs = null;
                                try {
                                    configs = com.alibaba.fastjson.JSONArray.parseArray(data, EventConfig.class);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (configs != null && !configs.isEmpty()) {
                                    PreferenceUtil.getInstance().putString(PreferenceUtil.DefaultKeys.KEY_LOCAL_CONFIG, data);
                                }
                            }
                        }
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        Log.d(Slark, "config getting is failed!");
                    }
                });
                break;
            case ActionType.POST_LOG:
                SlarkRequest.getInstance().postConfig(mAction.toActString(), new ResponseCallback<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {
                        Log.d(Slark, "log upload is succeed!");
                        DBUtil.getInstance().deleteAll();
                    }

                    @Override
                    protected void onFail(int i, String s) {
                        Log.d(Slark, "log upload is succeed!");
                    }
                });
                break;
        }
    }
}

