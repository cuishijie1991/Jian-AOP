package com.tracy.plugin.java;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/1/22.
 */

public class SourceMethodClassVisitor extends ClassVisitor {
    private String className;

    public SourceMethodClassVisitor(String className, ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("onClick")) {
            return new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {


                @Override
                protected void onMethodEnter() {
                    System.out.println("onMethodEnter ==>z " + className + "_____" + name + "@OnClick");
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "com.tech.track.Track", "track", "Ljava/io/PrintStream;");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/io/PrintStream", "println", "(J)V", false);
                    super.onMethodEnter();
                }

                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                }
            };
        }
        return methodVisitor;
    }

}

