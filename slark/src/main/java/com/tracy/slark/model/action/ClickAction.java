package com.tracy.slark.model.action;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tracy.slark.utils.ConfigUtils;
import com.tracy.slark.utils.ResUtils;
import com.tracy.slark.utils.TraceUtils;

import java.util.HashMap;

import static com.tracy.slark.model.Constant.UNKNOWN;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class ClickAction implements IAction {
    //string resource id in layout file , may return null
    public String viewId;
    // viewTree path, may changed if view add or remove happened in the viewTree
    public String viewPath;
    // page name , activity or fragment
    public String actPage;
    // if view instance of textView, return the text
    public String text;

    public long actTime;

    private Context mContext;

    public ClickAction(String viewId, String viewPath, String actPage, String text, long actTime) {
        this.viewId = viewId;
        this.viewPath = viewPath;
        this.actPage = actPage;
        this.text = text;
        this.actTime = actTime;
    }

    public ClickAction(View view) {
        this.actTime = System.currentTimeMillis();
        mContext = view.getContext();
        this.actPage = mContext.getClass().getSimpleName();
        this.viewId = findViewById(mContext, view.getId());
        this.viewPath = TraceUtils.generateViewTree(view);
        if (view instanceof TextView) {
            text = ((TextView) view).getText().toString();
        } else {
            text = UNKNOWN;
        }
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
        ConfigUtils configUtils = new ConfigUtils(mContext);
        if (configUtils.getConfig().containsKey(actPage)) {
            HashMap<String, String> map = configUtils.getConfig().get(actPage);
            if (map.containsKey(viewPath)) {
                StringBuilder sb = new StringBuilder();
                sb.append("viewId=").append(this.viewId)
                        .append("&actPage=").append(this.actPage)
                        .append("&viewPath=").append(this.viewPath)
                        .append("&text=").append(this.text)
                        .append("&actTime=").append(this.actTime);
                return sb.toString();
            }
        }
        return "no trace click";
    }

    @Override
    public long getActTime() {
        return this.actTime;
    }

    @Override
    public int getActType() {
        return ActionType.CLICK;
    }
}
