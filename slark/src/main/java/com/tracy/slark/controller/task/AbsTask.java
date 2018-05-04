package com.tracy.slark.controller.task;

import com.tracy.slark.controller.action.IAction;

/**
 * Created by shijiecui on 2018/2/6.
 */

public abstract class AbsTask implements Runnable {
    protected IAction mAction;

    public AbsTask(IAction action) {
        mAction = action;
    }
}
