package com.tracy.plugin.visitor.adapter;

import com.tracy.plugin.visitor.InjectMethod;
import com.tracy.plugin.visitor.InjectPage;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class BaseMethodAdapter extends AdviceAdapter {
    protected final String TAG = getClass().getSimpleName();
    protected InjectPage mPage;
    protected InjectMethod mMethod;

    public BaseMethodAdapter(MethodVisitor mv, InjectPage page, InjectMethod method) {
        super(ASM5, mv, method.access, method.name, method.desc);
        this.mPage = page;
        this.mMethod = method;
    }
}
