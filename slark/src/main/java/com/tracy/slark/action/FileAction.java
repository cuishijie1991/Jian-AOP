package com.tracy.slark.action;

import com.tracy.slark.utils.FileManager;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class FileAction implements Runnable {
    private String msg;

    public FileAction(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        if (msg != null) {
            FileManager.getInstance().saveToFile(msg);
        }
    }
}
