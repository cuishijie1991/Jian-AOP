package com.tracy.plugin.visitor.adapter;

import com.tracy.plugin.visitor.InjectMethod;
import com.tracy.plugin.visitor.InjectPage;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by shijiecui on 2018/1/23.
 */

public class ClickAdapter extends BaseMethodAdapter {

    public ClickAdapter(MethodVisitor mv, InjectPage page, InjectMethod method) {
        super(mv, page, method);
    }

    @Override
    protected void onMethodEnter() {
        switch (mMethod) {
            case ON_CLICK:
                visitVarInsn(ALOAD, 1);
                visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackClickEvent", "(Landroid/view/View;)V", false);
                break;
            case ON_ITEM_CLICK:
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
