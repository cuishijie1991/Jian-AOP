package com.tracy.plugin.java.adviceAdapter;

import com.tracy.plugin.java.InjectPage;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class PageAdviceAdapter extends AdviceAdapter {
    private String className;
    private String methodName;
    private InjectPage pageType;

    public PageAdviceAdapter(String className, InjectPage pageType, MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
        this.className = className;
        this.pageType = pageType;
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        if (pageType == InjectPage.ACTIVITY) {
            insertActivity();
        } else if (pageType == InjectPage.FRAGMENT) {
            insertFragment();
        }
        super.onMethodEnter();
    }

    private void insertActivity() {
        if (methodName.equals("onResume")) {
            insertPageStart();
        }
        if (methodName.equals("onPause")) {
            insertPageEnd();
        }
    }

    private void insertFragment() {
        if (methodName.equals("onResume")) {
            insertPageStart();
        }
        if (methodName.equals("onPause")) {
            insertPageEnd();
        }
        if (methodName.equals("setUserVisibleHint")) {
            insertPageStart();
        }
        if (methodName.equals("onHiddenChanged")) {
            insertPageEnd();
        }
    }

    private void insertPageStart() {
        visitVarInsn(ALOAD, 0);
        visitLdcInsn(true);
        visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Ljava/lang/Object;Z)V", false);
    }

    private void insertPageEnd() {
        visitVarInsn(ALOAD, 0);
        visitLdcInsn(false);
        visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Ljava/lang/Object;Z)V", false);
    }
}
