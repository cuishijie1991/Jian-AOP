package com.tracy.slark.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.tracy.slark.controller.model.ClickEvent;
import com.tracy.slark.dao.DaoMaster;
import com.tracy.slark.dao.DaoSession;
import com.tracy.slark.dao.SlarkLog;

import java.util.List;

/**
 * Created by shijiecui on 2018/5/4.
 */

public class DBUtil {

    private static final String DB_NAME = "slark_db";
    private static DBUtil instance;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession mDaoSession;

    public static final synchronized DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    private DBUtil() {
    }

    public void init(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public void save(String text) {
        if (!TextUtils.isEmpty(text)) {
            mDaoSession.insertOrReplace(new SlarkLog(System.currentTimeMillis(), text));
        }
    }

    public void save(ClickEvent event) {
        if (event != null) {
            mDaoSession.insertOrReplace(new SlarkLog(event.actTime, event.toString()));
        }
    }

    public void deleteAll() {
        mDaoSession.clear();
    }

    public List<SlarkLog> getLogs() {
        return mDaoSession.loadAll(SlarkLog.class);
    }


}
