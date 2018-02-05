package com.tracy.jianaop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tracy.jianaop.R;
import com.tracy.jianaop.data.DataHelper;
import com.tracy.jianaop.data.ListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shijiecui on 2018/1/31.
 */

public class PagerRecyclerViewActivity extends Activity {
    RecyclerView recyclerView;
    ViewPager viewPager;
    Context mContext = this;
    List<ListItem> listItemList = new ArrayList<>();
    LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_recycler);
        mInflater = LayoutInflater.from(mContext);
        recyclerView = findViewById(R.id.recyclerView);
        viewPager = findViewById(R.id.viewPager);
        recyclerView.setAdapter(new RecyclerViewAdapter());
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(manager);
        listItemList.addAll(Arrays.asList(DataHelper.listItems));

        viewPager.setAdapter(new PagerAdapter());
    }

    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return listItemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View convertView = mInflater.inflate(R.layout.list_item, container, false);
            ImageView imageView = convertView.findViewById(R.id.image);
            TextView name = convertView.findViewById(R.id.name);
            CheckBox checkBox = convertView.findViewById(R.id.checkBox);
            ListItem listItem = listItemList.get(position);
            Picasso.with(mContext).load(listItem.image).fit().centerCrop().into(imageView);
            name.setText(listItem.name);
            checkBox.setChecked(listItem.selected);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> Toast.makeText(mContext, "checkBox is Clicked!", Toast.LENGTH_SHORT).show());
            imageView.setOnClickListener(v -> Toast.makeText(mContext, "image is Clicked!", Toast.LENGTH_SHORT).show());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            container.addView(convertView);
            return convertView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.85f;
        }
    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(mInflater.inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            ListItem listItem = listItemList.get(position);
            Picasso.with(mContext).load(listItem.image).fit().centerCrop().into(holder.imageView);
            holder.name.setText(listItem.name);
            holder.checkBox.setChecked(listItem.selected);
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> Toast.makeText(mContext, "checkBox is Clicked!", Toast.LENGTH_SHORT).show());
            holder.imageView.setOnClickListener(v -> Toast.makeText(mContext, "image is Clicked!", Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return listItemList.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView name;
            public CheckBox checkBox;

            public Holder(View convertView) {
                super(convertView);
                imageView = convertView.findViewById(R.id.image);
                name = convertView.findViewById(R.id.name);
                checkBox = convertView.findViewById(R.id.checkBox);
            }
        }
    }
}
