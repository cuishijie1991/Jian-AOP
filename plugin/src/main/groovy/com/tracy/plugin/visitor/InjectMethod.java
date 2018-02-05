package com.tracy.plugin.visitor;

import org.objectweb.asm.Opcodes;

/**
 * Created by shijiecui on 2018/1/31.
 */

public enum InjectMethod implements Opcodes {
    //生命周期
    ON_CREATE("onCreate", ACC_PUBLIC, "()V"),

    ON_RESUME("onResume", ACC_PUBLIC, "()V"),

    ON_PAUSE("onPause", ACC_PUBLIC, "()V"),

    SET_USER_VISIBLE_HINT("setUserVisibleHint", ACC_PUBLIC, "(Z)V"),

    ON_HIDDEN_CHANGED("onHiddenChanged", ACC_PUBLIC, "(Z)V"),

    //点击
    ON_CLICK("onClick", ACC_PUBLIC, "(Landroid/view/View;)V"),

    ON_ITEM_CLICK("onItemClick", ACC_PUBLIC, "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V");

    public String name;
    public int access;
    public String desc;

    InjectMethod(String name, int access, String desc) {
        this.name = name;
        this.access = access;
        this.desc = desc;
    }

    public boolean match(String name, int access, String desc) {
        return this.name.equals(name) && this.access == access && this.desc.equals(desc);
    }

}
