package com.tracy.plugin.visitor.adapter;

import com.tracy.plugin.visitor.InjectMethod;
import com.tracy.plugin.visitor.InjectPage;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class PageAdapter extends BaseMethodAdapter {
    public PageAdapter(MethodVisitor mv, InjectPage page, InjectMethod method) {
        super(mv, page, method);
    }

    @Override
    protected void onMethodEnter() {
        if (mPage == InjectPage.ACTIVITY) {
            insertActivity();
        } else if (mPage == InjectPage.FRAGMENT) {
            insertFragment();
        }
        super.onMethodEnter();
    }

    private void insertActivity() {
        if (mMethod == InjectMethod.ON_RESUME) {
            insertPageStart();
        }
        if (mMethod == InjectMethod.ON_PAUSE) {
            insertPageEnd();
        }
    }

    private void insertFragment() {
        switch (mMethod) {
            case ON_RESUME:
            case SET_USER_VISIBLE_HINT:
                insertPageStart();
                break;
            case ON_PAUSE:
            case ON_HIDDEN_CHANGED:
                insertPageEnd();
                break;
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
