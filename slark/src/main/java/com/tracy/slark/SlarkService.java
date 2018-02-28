package com.tracy.slark;

import android.os.Handler;
import android.os.HandlerThread;

import com.tracy.slark.model.ILogCollector;
import com.tracy.slark.model.task.AddActionTask;
import com.tracy.slark.model.task.DebugTask;
import com.tracy.slark.model.action.IAction;
import com.tracy.slark.model.Constant;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class SlarkService {
    private SlarkThread mThread;
    private Handler H;
    private ILogCollector mCollector;

    private SlarkService() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new SlarkThread();
            mThread.start();
            H = new Handler(mThread.getLooper());
        }
    }

    private static SlarkService instance = new SlarkService();

    public static SlarkService getInstance() {
        return instance;
    }

    public void addAction(IAction action) {
        H.post(new AddActionTask(mCollector, action));
        if (Constant.isDebug) {
            H.post(new DebugTask(action));
        }
    }

    public void setLogCollector(ILogCollector collector) {
        this.mCollector = collector;
    }

    public ILogCollector getLogCollector() {
        return mCollector;
    }

    private class SlarkThread extends HandlerThread {

        public SlarkThread() {
            super("Slark");
        }
    }
}
