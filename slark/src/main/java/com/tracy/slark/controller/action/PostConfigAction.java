package com.tracy.slark.controller.action;

/**
 * Created by shijiecui on 2018/5/3.
 */

public class PostConfigAction implements IAction {

    private String mConfig;

    public PostConfigAction(String config) {
        mConfig = config;
    }

    @Override
    public String toActString() {
        return mConfig;
    }

    @Override
    public int getActType() {
        return ActionType.POST_CONFIG;
    }
}
