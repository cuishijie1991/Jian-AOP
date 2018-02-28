package com.tracy.plugin.java;


import com.tracy.plugin.SlarkSettings;
import com.tracy.plugin.java.adviceAdapter.ClickAdviceAdapter;
import com.tracy.plugin.java.adviceAdapter.PageAdviceAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.tracy.plugin.java.InjectMethod.CLICK;
import static com.tracy.plugin.java.InjectMethod.ITEM_CLICK;
import static com.tracy.plugin.java.InjectPage.ACTIVITY;
import static com.tracy.plugin.java.InjectPage.FRAGMENT;
import static com.tracy.plugin.java.InjectPage.OTHER;

/**
 * Created by shijiecui on 2018/1/22.
 */

public class SourceMethodClassVisitor extends ClassVisitor {
    private String className;
    private boolean trackClick = true;
    private boolean trackPage = true;
    private boolean isPageClass = false;
    private InjectPage pageType = OTHER;

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
        trackClick = SlarkSettings.isTrackClick();
        trackPage = SlarkSettings.isTrackPage();
        isPageClass = false;
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (trackPage) {
            if (superName.endsWith(ACTIVITY.type)) {
                pageType = ACTIVITY;
                isPageClass = true;
                return;
            } else if (superName.endsWith(FRAGMENT.type)) {
                pageType = FRAGMENT;
                isPageClass = true;
                return;
            }
        }
        pageType = OTHER;
        isPageClass = false;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println(className);
        if (trackClick) {
            if (name.equals(CLICK.method)) {
                methodVisitor = new ClickAdviceAdapter(className, CLICK, methodVisitor, access, name, desc);
            } else if (name.equals(ITEM_CLICK.method)) {
                methodVisitor = new ClickAdviceAdapter(className, ITEM_CLICK, methodVisitor, access, name, desc);
            }
        }
        if (trackPage && isPageClass) {
            methodVisitor = new PageAdviceAdapter(className, pageType, methodVisitor, access, name, desc);
        }

        return methodVisitor;
    }


}

