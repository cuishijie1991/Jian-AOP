package com.tracy.slark.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.tracy.slark.R;


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

        void onIgnore(EventPopup pop);
    }

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
        Button buttonCancel = contentView.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(v -> dismiss());
        Button buttonIgnore = contentView.findViewById(R.id.btn_ignore);
        buttonIgnore.setOnClickListener(v -> {
            listener.onIgnore(EventPopup.this);
            dismiss();
        });
        return contentView;
    }

}
