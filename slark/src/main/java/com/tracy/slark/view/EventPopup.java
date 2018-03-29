package com.tracy.slark.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.tracy.slark.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shijiecui on 2018/3/19.
 */

public class EventPopup extends PopupWindow {
    private View mAnchorView;
    private Context mContext;
    private EditText et;

    public EventPopup(View anchor, IEventPopupListener listener) {
        super(anchor.getContext());
        mContext = anchor.getContext();
        mAnchorView = anchor;
        setFocusable(true);
        setContentView(initContentView(listener));
        setWidth(800);
        setHeight(400);
    }

    public void show() {
        showAsDropDown(mAnchorView);
    }

    public String getEventId() {
        return et.getText().toString().trim();
    }

    public interface IEventPopupListener {
        void onConfirm(EventPopup pop);
    }

//    private View initContentView(IEventPopupListener listener) {
//        RelativeLayout contentView = new RelativeLayout(mContext);
//        TextView titleView = new TextView(mContext);
//        titleView.setId(generateViewId(titleView));
//        titleView.setText("请编辑事件ID，并保存");
//        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        contentView.addView(titleView, rParams);
//        et = new EditText(mContext);
//        et.setId(generateViewId(et));
//        rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rParams.addRule(RelativeLayout.BELOW, titleView.getId());
//        contentView.addView(et, rParams);
//        Button buttonOK = new Button(mContext);
//        buttonOK.setText("确定");
//        buttonOK.setId(generateViewId(buttonOK));
//        buttonOK.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onConfirm(EventPopup.this);
//            }
//            dismiss();
//        });
//        rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rParams.addRule(RelativeLayout.BELOW, et.getId());
//        contentView.addView(buttonOK, rParams);
//
//        Button buttonCancel = new Button(mContext);
//        buttonCancel.setText("取消");
//        buttonCancel.setOnClickListener(v -> dismiss());
//        rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rParams.addRule(RelativeLayout.BELOW, et.getId());
//        rParams.addRule(RelativeLayout.RIGHT_OF, buttonOK.getId());
//        contentView.addView(buttonCancel, rParams);
//        return contentView;
//    }

    private View initContentView(IEventPopupListener listener) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog, null);
        et = contentView.findViewById(R.id.et);
        Button buttonOK = contentView.findViewById(R.id.btn_ok);
        buttonOK.setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirm(EventPopup.this);
            }
            dismiss();
        });
        Button buttonCancel = contentView.findViewById(R.id.btn_cancle);
        buttonCancel.setOnClickListener(v -> dismiss());
        return contentView;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
