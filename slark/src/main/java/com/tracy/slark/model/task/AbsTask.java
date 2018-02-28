package com.tracy.slark.model.task;

import com.tracy.slark.model.action.IAction;

/**
 * Created by shijiecui on 2018/2/6.
 */

public abstract class AbsTask implements Runnable {
    protected IAction mAction;

    public AbsTask(IAction action) {
        mAction = action;
    }
}
