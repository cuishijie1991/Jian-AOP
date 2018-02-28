package com.tracy.slark.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class FileHelper {
    private static FileHelper instance;
    private final File logDir;
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mTimeFormat;

    private FileHelper() {
        File sdCardDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
        String dir = sdCardDir.getPath() + "/Slark";
        this.logDir = new File(dir);
        mDateFormat = new SimpleDateFormat("yyyyMMdd");
        mTimeFormat = new SimpleDateFormat("hh-mm-ss");
    }

    public static synchronized FileHelper getInstance() {
        if (instance == null) {
            instance = new FileHelper();
        }
        return instance;
    }

    public void saveToFile(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        try {
            String date = mDateFormat.format(new Date());
            String fileName = String.format("%s/%s.log", logDir.getAbsolutePath(), date);
            File logFile = new File(fileName);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            FileWriter writer = new FileWriter(fileName, true);
            StringBuilder sb = new StringBuilder();
            sb.append(mTimeFormat.format(new Date())).append(" => ").append(log).append("\n");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
