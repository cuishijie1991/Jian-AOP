package com.tracy.jianaop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tracy.jianaop.R;
import com.tracy.jianaop.data.DataHelper;
import com.tracy.jianaop.data.ListItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shijiecui on 2018/1/31.
 */

public class ListViewActivity extends Activity {
    ListView lv;
    GridView gv;
    Context mContext;
    LayoutInflater mInflater;

    List<ListItem> listItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        listItemList = Arrays.asList(DataHelper.listItems);
        lv = findViewById(R.id.listView);
        gv = findViewById(R.id.gridView);
        lv.setAdapter(new LVAdapter());
        gv.setAdapter(new GVAdapter());

        gv.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(mContext, "GridView " + DataHelper.listItems[position].name + " is Clicked!", Toast.LENGTH_SHORT).show();
        });
    }


    class LVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listItemList.size();
        }

        @Override
        public ListItem getItem(int position) {
            return listItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItem listItem = getItem(position);
            convertView = mInflater.inflate(R.layout.list_item, null);
            ImageView imageView = convertView.findViewById(R.id.big_image);
            TextView name = convertView.findViewById(R.id.name);
            CheckBox checkBox = convertView.findViewById(R.id.check_box);
            Picasso.with(mContext).load(listItem.image).fit().centerCrop().into(imageView);
            name.setText(listItem.name);
            checkBox.setChecked(listItem.selected);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(mContext, "checkBox is Clicked!", Toast.LENGTH_SHORT).show();
                }
            });
            imageView.setOnClickListener(v -> Toast.makeText(mContext, "image is Clicked!", Toast.LENGTH_SHORT).show());
            convertView.setOnClickListener(v -> Toast.makeText(mContext, "ListView " + listItem.name + " is Clicked!", Toast.LENGTH_SHORT).show());
            return convertView;
        }
    }

    class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listItemList.size();
        }

        @Override
        public ListItem getItem(int position) {
            return listItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItem listItem = getItem(position);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
            Picasso.with(mContext).load(listItem.image).fit().centerCrop().into(imageView);
            return imageView;
        }
    }
}
