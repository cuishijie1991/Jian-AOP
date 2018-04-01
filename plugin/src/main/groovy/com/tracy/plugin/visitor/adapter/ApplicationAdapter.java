package com.tracy.plugin.visitor.adapter;

import com.tracy.plugin.visitor.InjectMethod;
import com.tracy.plugin.visitor.InjectPage;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by shijiecui on 2018/2/5.
 */

public class ApplicationAdapter extends BaseMethodAdapter {
    public ApplicationAdapter(MethodVisitor mv, InjectPage page, InjectMethod method) {
        super(mv, page, method);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        visitVarInsn(ALOAD, 0);
        visitMethodInsn(INVOKESTATIC, "com/tracy/slark/utils/ActivityCircleUtils", "registerMonitor", "(Landroid/app/Application;)V", false);
    }
}
