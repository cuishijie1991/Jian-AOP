package com.tracy.slark;

import android.os.Handler;
import android.os.HandlerThread;

import com.tracy.slark.action.FileAction;
import com.tracy.slark.model.IAction;
import com.tracy.slark.utils.Constant;
import com.tracy.slark.utils.LogUtils;

/**
 * Created by cuishijie on 2018/1/28.
 */

public class SlarkAgent {
    public static final String TAG = SlarkAgent.class.getSimpleName();
    private SlarkThread mThread = null;
    private Handler H;

    private SlarkAgent() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new SlarkThread();
            mThread.start();
            H = new Handler(mThread.getLooper());
        }
    }

    private static SlarkAgent instance = new SlarkAgent();

    public static SlarkAgent getInstance() {
        return instance;
    }

    public void addAction(IAction action) {
        LogUtils.e(action.toActionString());
        if (Constant.isDebug) {
            H.post(new FileAction(action.toActionString()));
        }
    }

    private class SlarkThread extends HandlerThread {

        public SlarkThread() {
            super("Slark");
        }
    }
}
