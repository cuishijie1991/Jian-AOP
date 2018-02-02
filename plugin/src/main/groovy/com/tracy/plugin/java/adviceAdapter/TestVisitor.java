package com.tracy.plugin.java.adviceAdapter;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/2/2.
 */

public class TestVisitor extends AdviceAdapter {
    public String name;
    public String className;

    public TestVisitor(String className, MethodVisitor mv, int access, String name, String desc) {
        super(ASM5, mv, access, name, desc);
        this.name = name;
        this.className = className;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
//        mv.visitInsn(ICONST_3);
//        mv.visitIntInsn(NEWARRAY, T_INT);
//        mv.visitVarInsn(ASTORE, 1);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (name.equals("test")) {
            visitVarInsn(ALOAD, 0);
            visitMethodInsn(INVOKEVIRTUAL, className, "getContext", "()Ljava/lang/String", false);
            mv.visitVarInsn(ASTORE, 1);
//            visitLocalVariable("_context","Ljava/lang/String,"null,);
        }
    }
}
