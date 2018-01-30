package com.tracy.plugin.java;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class CostMethodClassVisitor extends ClassVisitor {

    private String className;

    public CostMethodClassVisitor(String className, ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature,
                                     String[] exceptions) {

        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {

            boolean inject = false;

            private boolean isInject() {
               /* if(name.equals("setStartTime") || name.equals("setEndTime") || name.equals("getCostTime")){
                   return false;
                }
                return true;*/
                return inject;
            }

            @Override
            public void visitCode() {
                super.visitCode();

            }

            @Override
            public org.objectweb.asm.AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//                if (Type.getDescriptor(Cost.class).equals(desc)) {
//                    inject = true;
//                }

                return super.visitAnnotation(desc, visible);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                super.visitFieldInsn(opcode, owner, name, desc);
            }


            @Override
            protected void onMethodEnter() {
                //super.onMethodEnter();
                if (isInject()) {

                    //mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    //mv.visitLdcInsn("========start========="+name+"==>des:"+desc);
                    //mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    //      "(Ljava/lang/String;)V", false);

//                    mv.visitLdcInsn(className + ":" + name + desc);
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/meiyou/meetyoucost/CostLog", "setStartTime",
//                            "(Ljava/lang/String;J)V", false);
                }
            }

            @Override
            protected void onMethodExit(int i) {
                //super.onMethodExit(i);
                if (isInject()) {
//                    mv.visitLdcInsn(className + ":" + name + desc);
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/meiyou/meetyoucost/CostLog", "setEndTime",
//                            "(Ljava/lang/String;J)V", false);

                    /*mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn(className+":"+name+desc);
                    mv.visitMethodInsn(INVOKESTATIC, "com/meiyou/meetyoucost/CostLog", "getCostTime",
                            "(Ljava/lang/String;)Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);*/

                    //mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    //mv.visitLdcInsn("========end=========");
                    //mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    //      "(Ljava/lang/String;)V", false);
                }
            }
        };
        return methodVisitor;
        //return super.visitMethod(i, s, s1, s2, strings);

    }
}