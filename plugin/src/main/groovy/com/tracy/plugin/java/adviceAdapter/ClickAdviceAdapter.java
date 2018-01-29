package com.tracy.plugin.java.adviceAdapter;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class ClickAdviceAdapter extends AdviceAdapter {
    private String className;

    public ClickAdviceAdapter(String className, MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
        this.className = className;
    }


    @Override
    protected void onMethodEnter() {
        visitVarInsn(Opcodes.ALOAD, 1);
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/tracy/slark/Slark", "trackClickEvent", "(Landroid/view/View;)V", false);
        super.onMethodEnter();
    }


}
