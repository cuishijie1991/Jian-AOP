package com.tracy.plugin.java.adviceAdapter;

import com.tracy.plugin.java.InjectMethod;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class ClickAdviceAdapter extends AdviceAdapter {
    private String className;
    private InjectMethod method;

    public ClickAdviceAdapter(String className, InjectMethod method, MethodVisitor mv, int access, String name, String desc) {
        super(ASM5, mv, access, name, desc);
        this.className = className;
        this.method = method;
    }


    @Override
    protected void onMethodEnter() {
        switch (method) {
            case CLICK:
                visitVarInsn(ALOAD, 1);
                visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackClickEvent", "(Landroid/view/View;)V", false);
                break;
            case ITEM_CLICK:
                visitVarInsn(ALOAD, 2);
                visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackClickEvent", "(Landroid/view/View;)V", false);
                break;
        }

        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
    }

}
