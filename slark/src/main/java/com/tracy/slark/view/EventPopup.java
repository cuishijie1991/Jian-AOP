package com.tracy.slark.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tracy.slark.R;
import com.tracy.slark.Slark;
import com.tracy.slark.network.Network;


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
        showAtLocation(mAnchorView, Gravity.CENTER, 0, 0);
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
            if (TextUtils.isEmpty(getEventId())) {
                Toast.makeText(mContext, "ID不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (listener != null) {
                listener.onConfirm(EventPopup.this);
            }
            dismiss();
        });
        Button buttonCancel = contentView.findViewById(R.id.btn_cancel);
        Button upload = contentView.findViewById(R.id.btn_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(Slark.saveConfig())) {
                    Network.getInstance().sendRequestWithHttpURLConnection(mContext, Network.POST, Slark.saveConfig());
                    Toast.makeText(mContext, "Upload Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "config is null", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        buttonCancel.setOnClickListener(v -> dismiss());
        Button buttonIgnore = contentView.findViewById(R.id.btn_ignore);
        buttonIgnore.setOnClickListener(v -> {
            listener.onIgnore(EventPopup.this);
            dismiss();
        });
        return contentView;
    }

}
