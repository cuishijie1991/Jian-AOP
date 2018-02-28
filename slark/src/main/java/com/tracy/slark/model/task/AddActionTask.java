package com.tracy.slark.model.task;

import com.tracy.slark.model.ILogCollector;
import com.tracy.slark.model.action.ActionType;
import com.tracy.slark.model.action.IAction;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class AddActionTask extends AbsTask {
    ILogCollector mCollector;

    public AddActionTask(ILogCollector collector, IAction action) {
        super(action);
        mCollector = collector;
    }

    @Override
    public void run() {
        if (mCollector != null) {
            switch (mAction.getActType()) {
                case ActionType.CLICK:
                    mCollector.trackClickEvent(mAction.toActString());
                    break;
                case ActionType.PAGE:
                    mCollector.trackPageEvent(mAction.toActString());
                    break;
            }
        }
    }
}
