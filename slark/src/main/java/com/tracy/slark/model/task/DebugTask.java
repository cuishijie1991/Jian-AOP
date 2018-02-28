package com.tracy.slark.model.task;

import com.tracy.slark.model.action.IAction;
import com.tracy.slark.utils.FileHelper;
import com.tracy.slark.utils.LogUtils;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class DebugTask extends AbsTask {

    public DebugTask(IAction action) {
        super(action);
    }

    @Override
    public void run() {
        if (mAction != null) {
            LogUtils.e(mAction.toActString());
            FileHelper.getInstance().saveToFile(mAction.toActString());
        }
    }
}
