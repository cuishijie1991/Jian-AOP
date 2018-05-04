package com.tracy.slark.controller.action;

/**
 * Created by shijiecui on 2018/2/6.
 */

public class ActionType {
    public static final int CLICK = 0;
    public static final int PAGE = CLICK + 1;
    public static final int POST_CONFIG = PAGE + 1;
    public static final int GET_CONFIG = POST_CONFIG + 1;
    public static final int POST_LOG = GET_CONFIG + 1;
}
