package com.tracy.slark.model.action;

/**
 * Created by cuishijie on 2018/1/28.
 */

public interface IAction {

    String toActString();

    long getActTime();

    int getActType();
}
