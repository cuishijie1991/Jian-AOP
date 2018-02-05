package com.tracy.plugin.visitor;


import com.android.ddmlib.Log;
import com.tracy.plugin.SlarkSettings;
import com.tracy.plugin.visitor.adapter.ApplicationAdapter;
import com.tracy.plugin.visitor.adapter.ClickAdapter;
import com.tracy.plugin.visitor.adapter.PageAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.tracy.plugin.visitor.InjectMethod.ON_CLICK;
import static com.tracy.plugin.visitor.InjectMethod.ON_HIDDEN_CHANGED;
import static com.tracy.plugin.visitor.InjectMethod.ON_ITEM_CLICK;
import static com.tracy.plugin.visitor.InjectMethod.ON_CREATE;
import static com.tracy.plugin.visitor.InjectMethod.ON_PAUSE;
import static com.tracy.plugin.visitor.InjectMethod.ON_RESUME;
import static com.tracy.plugin.visitor.InjectMethod.SET_USER_VISIBLE_HINT;
import static com.tracy.plugin.visitor.InjectPage.ACTIVITY;
import static com.tracy.plugin.visitor.InjectPage.APPLICATION;
import static com.tracy.plugin.visitor.InjectPage.FRAGMENT;
import static com.tracy.plugin.visitor.InjectPage.OTHER;

/**
 * Created by shijiecui on 2018/1/22.
 */

public class SourceMethodClassVisitor extends ClassVisitor implements Opcodes {
    private String className;
    private boolean trackClick = true;
    private boolean trackPage = true;
    private InjectPage pageType = OTHER;

    public SourceMethodClassVisitor(String _className, ClassVisitor cv) {
        super(ASM5, cv);
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
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        pageType = OTHER;
        pageType.setName(name);
        if (APPLICATION.match(superName)) {
            pageType = APPLICATION;
            pageType.setName(name);
        }
        if (trackPage) {
            if (ACTIVITY.match(superName)) {
                pageType = ACTIVITY;
                pageType.setName(name);
            } else if (FRAGMENT.match(superName)) {
                pageType = FRAGMENT;
                pageType.setName(name);
            }
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (pageType == APPLICATION) {
            if (ON_CREATE.match(name, access, desc)) {
                methodVisitor = new ApplicationAdapter(methodVisitor, pageType, ON_CREATE);
            }
        } else {
            if (trackClick) {
                if (ON_CLICK.match(name, access, desc)) {
                    methodVisitor = new ClickAdapter(methodVisitor, pageType, ON_CLICK);
                } else if (ON_ITEM_CLICK.match(name, access, desc)) {
                    methodVisitor = new ClickAdapter(methodVisitor, pageType, ON_ITEM_CLICK);
                }
            }
            if (trackPage) {
                InjectMethod iMethod = null;
                if (ON_RESUME.match(name, access, desc)) {
                    iMethod = ON_RESUME;
                } else if (ON_PAUSE.match(name, access, desc)) {
                    iMethod = ON_PAUSE;
                } else if (SET_USER_VISIBLE_HINT.match(name, access, desc)) {
                    iMethod = SET_USER_VISIBLE_HINT;
                } else if (ON_HIDDEN_CHANGED.match(name, access, desc)) {
                    iMethod = ON_HIDDEN_CHANGED;
                }
                if (iMethod != null) {
                    methodVisitor = new PageAdapter(methodVisitor, pageType, iMethod);
                }
            }
        }
        return methodVisitor;
    }
}

