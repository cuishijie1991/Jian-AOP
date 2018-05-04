package com.tracy.slark;

import android.os.Handler;
import android.os.HandlerThread;

import com.tracy.slark.controller.DefaultLogCollector;
import com.tracy.slark.controller.ILogCollector;
import com.tracy.slark.controller.task.ActionTask;
import com.tracy.slark.controller.action.IAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishijie on 2018/1/28.
 * 负责调度所有的Action
 */

public class SlarkService {
    private SlarkThread mThread;
    private Handler H;
    private List<ILogCollector> mLogCollectors = new ArrayList<>();
    private List<String> mLogCollectorNames = new ArrayList<>();

    private SlarkService() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new SlarkThread();
            mThread.start();
            H = new Handler(mThread.getLooper());
            mLogCollectors.clear();
            mLogCollectorNames.clear();
            addLogCollector(DefaultLogCollector.getInstance());
        }
    }

    private static SlarkService instance = new SlarkService();

    public static SlarkService getInstance() {
        return instance;
    }

    public void addAction(IAction action) {
        H.post(new ActionTask(mLogCollectors, action));
    }

    public void addLogCollector(ILogCollector collector) {
        if (collector != null && collector.getName() != null && !mLogCollectorNames.contains(collector.getName())) {
            mLogCollectors.add(collector);
            mLogCollectorNames.add(collector.getName());
        }
    }

    private final class SlarkThread extends HandlerThread {

        public SlarkThread() {
            super("Slark");
        }
    }
}
