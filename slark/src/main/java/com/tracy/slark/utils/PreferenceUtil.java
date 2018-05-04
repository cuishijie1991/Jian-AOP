package com.tracy.slark.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jiayuan on 15/10/26.
 */
public class PreferenceUtil {

    /**
     * the app's preference file name.
     */
    private static final String sDefaultPrefFileName = "hostler_pref";

    private static final Object sLock = new Object();
    private volatile static PreferenceUtil sInstance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mReader;

    /**
     * when you want to add your key value pairs to the preference file, add the
     * enum here first.
     */
    public enum DefaultKeys {
        KEY_LOCAL_CONFIG("KEY_LOCAL_CONFIG");

        private String mKey;

        DefaultKeys(String key) {
            mKey = key;
        }

        public String getKey() {
            return mKey;
        }
    }

    /**
     * get the single instance
     *
     * @return
     */
    public static PreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new PreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mReader = context.getApplicationContext().getSharedPreferences(sDefaultPrefFileName,
                Context.MODE_PRIVATE);
        mEditor = mReader.edit();
    }

    private PreferenceUtil() {
    }

    public void putBoolean(DefaultKeys key, boolean value) {
        synchronized (sLock) {
            mEditor.putBoolean(key.getKey(), value).commit();
        }
    }

    public void putFloat(DefaultKeys key, float value) {
        synchronized (sLock) {
            mEditor.putFloat(key.getKey(), value).commit();
        }
    }

    public void putInt(DefaultKeys key, int value) {
        synchronized (sLock) {
            mEditor.putInt(key.getKey(), value).commit();
        }
    }

    public void putLong(DefaultKeys key, long value) {
        synchronized (sLock) {
            mEditor.putLong(key.getKey(), value).commit();
        }
    }

    public void putString(DefaultKeys key, String value) {
        synchronized (sLock) {
            mEditor.putString(key.getKey(), value).commit();
        }
    }

    public void remove(DefaultKeys key) {
        synchronized (sLock) {
            mEditor.remove(key.getKey()).commit();
        }
    }

    public void clear() {
        synchronized (sLock) {
            mEditor.clear().commit();
        }
    }

    public boolean getBoolean(DefaultKeys key) {
        synchronized (sLock) {
            return mReader.getBoolean(key.getKey(), false);
        }
    }

    public float getFloat(DefaultKeys key) {
        synchronized (sLock) {
            return mReader.getFloat(key.getKey(), 0f);
        }
    }

    public int getInt(DefaultKeys key) {
        synchronized (sLock) {
            return mReader.getInt(key.getKey(), 0);
        }
    }

    public long getLong(DefaultKeys key) {
        synchronized (sLock) {
            return mReader.getLong(key.getKey(), 0l);
        }
    }

    public String getString(DefaultKeys key) {
        synchronized (sLock) {
            return mReader.getString(key.getKey(), "");
        }
    }


}
