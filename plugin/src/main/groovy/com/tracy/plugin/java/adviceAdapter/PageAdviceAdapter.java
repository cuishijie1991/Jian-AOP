package com.tracy.plugin.java.adviceAdapter;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class PageAdviceAdapter extends AdviceAdapter {
    private String className;
    private String methodName;

    public PageAdviceAdapter(String className, MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
        this.className = className;
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        if (methodName.equals("onResume")) {
            System.out.println(className + "==> track Page start");
            visitVarInsn(Opcodes.ALOAD, 0);
            visitLdcInsn(true);
            visitMethodInsn(Opcodes.INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Landroid/content/Context;Z)V", false);
        }
        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (methodName.equals("onPause")) {
            System.out.println(className + "==> track Page end");
            visitVarInsn(Opcodes.ALOAD, 0);
            visitLdcInsn(false);
            visitMethodInsn(Opcodes.INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Landroid/content/Context;Z)V", false);
        }
        super.onMethodExit(opcode);
    }
}
