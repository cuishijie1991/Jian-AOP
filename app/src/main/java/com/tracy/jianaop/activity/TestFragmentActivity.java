package com.tracy.jianaop.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tracy.jianaop.R;
import com.tracy.jianaop.fragment.MyFragmentTabHost;
import com.tracy.jianaop.fragment.NoMethodFragment;
import com.tracy.jianaop.fragment.PPFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiecui on 2018/1/31.
 */

public class TestFragmentActivity extends FragmentActivity {

    private MyFragmentTabHost mTabHost;

    private List<Class> mFragmentArray = new ArrayList<>();

    private List<String> mTextArray = new ArrayList<>();
    private List<Integer> mImageArray = new ArrayList<>();
    public static final String CONSOLE_TAB = "工作台";
    public static final String CAR_SOURCE_TAB = "车源";
    public static final String MY_INFO_TAB = "我的";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        initView();
    }

    private void initView() {
        mFragmentArray.clear();
        mTextArray.clear();
        mImageArray.clear();
        mFragmentArray.add(PPFragment.class);
        mFragmentArray.add(NoMethodFragment.class);
        mTextArray.add(CONSOLE_TAB);
        mTextArray.add(CAR_SOURCE_TAB);
        mImageArray.add(R.drawable.console_tab);
        mImageArray.add(R.drawable.car_source_tab);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        mTabHost.clearAllTabs();
        if (mFragmentArray != null && mFragmentArray.size() > 0) {
            int count = mFragmentArray.size();
            for (int i = 0; i < count; i++) {
                TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray.get(i)).setIndicator(getTabItemView(i));
                mTabHost.addTab(tabSpec, mFragmentArray.get(i), null);
                mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.white);
            }
        }
    }

    private View getTabItemView(int index) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tab_item_view, null);
        TextView textView = view.findViewById(R.id.tv_tab_name);
        textView.setText(mTextArray.get(index));
        ImageView imageView = view.findViewById(R.id.iv_tab_content);
        imageView.setImageResource(mImageArray.get(index));
        return view;
    }
}
