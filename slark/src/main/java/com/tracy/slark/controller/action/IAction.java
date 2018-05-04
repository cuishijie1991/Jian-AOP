package com.tracy.slark.controller.action;

/**
 * Created by cuishijie on 2018/1/28.
 */

public interface IAction {

    String toActString();

    default long getActTime() {
        return System.currentTimeMillis();
    }

    int getActType();
}
