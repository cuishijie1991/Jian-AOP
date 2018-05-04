package com.tracy.slark.controller.action;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.tracy.slark.controller.model.ClickEvent;
import com.tracy.slark.utils.ResUtils;
import com.tracy.slark.utils.TraceUtils;

import static com.tracy.slark.controller.Constant.UNKNOWN;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class ClickAction implements IAction {

    private Context mContext;
    private ClickEvent mEvent;

    public ClickAction(String page, String path, String id) {
        mEvent = new ClickEvent(page, path, id);
    }

    public ClickAction(View view) {
        mContext = view.getContext();
        String page = mContext.getClass().getSimpleName();
        String path = TraceUtils.generateViewTree(view);
        mEvent = new ClickEvent(page, path, null);
    }

    private String findViewById(Context context, int id) {
        if (id == View.NO_ID) {
            return UNKNOWN;
        }
        Class RClass = null;
        try {
            RClass = Class.forName(context.getPackageName() + ".R");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ResUtils.getResourceNameById(RClass, id);
    }

    @Override
    public String toActString() {
        if (mEvent != null) {
            return JSON.toJSONString(mEvent);
        }
        return null;
    }

    @Override
    public long getActTime() {
        if (mEvent != null) {
            return mEvent.actTime;
        }
        return System.currentTimeMillis();
    }

    @Override
    public int getActType() {
        return ActionType.CLICK;
    }
}
