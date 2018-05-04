package com.tracy.slark.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shijiecui on 2018/5/4.
 */

@Entity
public class SlarkLog {
    @Id
    private long id;
    private String text;

    @Generated(hash = 1593231274)
    public SlarkLog(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Generated(hash = 765441168)
    public SlarkLog() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
