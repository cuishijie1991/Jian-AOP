package com.tracy.plugin.java;


import com.tracy.plugin.java.adviceAdapter.ClickAdviceAdapter;
import com.tracy.plugin.java.adviceAdapter.PageAdviceAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by shijiecui on 2018/1/22.
 */

public class SourceMethodClassVisitor extends ClassVisitor {
    private String className;
    private boolean trackClick = true;
    private boolean trackPageTime = true;

    public SourceMethodClassVisitor(String _className, ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
        this.className = _className;
        if (className == null) {
            className = "SourceMethodClassVisitor#UnknownClass";
        }
        int splitIndex = className.indexOf("$");
        if (splitIndex > -1) {
            className = className.substring(0, splitIndex);
        }
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (trackClick && name.equals("onClick")) {
            methodVisitor = new ClickAdviceAdapter(className, methodVisitor, access, name, desc);
        }
        if (trackPageTime && (name.equals("onResume") || name.equals("onPause"))) {
            methodVisitor = new PageAdviceAdapter(className, methodVisitor, access, name, desc);
        }
        return methodVisitor;
    }


}

