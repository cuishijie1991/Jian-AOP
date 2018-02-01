package com.tracy.jianaop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracy.jianaop.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by zhaohaiyang on 2018/1/31.
 */

public class NoMethodFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        view.findViewById(R.id.tv_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.currentTimeMillis();
            }
        });
        return view;
    }
}
