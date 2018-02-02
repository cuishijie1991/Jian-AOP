package com.tracy.jianaop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    Item[] items = {
            new Item("兰博基尼", "http://img.mp.itc.cn/upload/20160823/ffa1e8ea679b41d3abf2ace3d412c083_th.jpeg", false),
            new Item("迈凯轮", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=969431638,686521512&fm=27&gp=0.jpg", true),
            new Item("法拉利", "http://img.mp.itc.cn/upload/20160823/17dc0cf00323406eaebcbaab8f894808_th.jpeg", true),
            new Item("玛莎拉蒂", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1991134558,3663849984&fm=27&gp=0.jpg", false),
            new Item("布加迪威龙", "http://img.mp.itc.cn/upload/20160823/b6b14dcb664d42279f022861d8cfb413.jpeg", true),
            new Item("迈巴赫", "http://img.mp.itc.cn/upload/20160823/34cdcea742484fc49d45b049ef1b922c_th.jpeg", false)
    };
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        itemList = Arrays.asList(items);
        lv = findViewById(R.id.listView);
        gv = findViewById(R.id.gridView);
        lv.setAdapter(new LVAdapter());
        gv.setAdapter(new GVAdapter());

        gv.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(mContext, "GridView " + items[position].name + " is Clicked!", Toast.LENGTH_SHORT).show();
        });
    }

    class Item {
        public Item(String name, String image, boolean selected) {
            this.name = name;
            this.image = image;
            this.selected = selected;
        }

        public String name;
        public String image;
        public boolean selected;
    }

    class LVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Item getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = getItem(position);
            convertView = mInflater.inflate(R.layout.list_item, null);
            ImageView imageView = convertView.findViewById(R.id.image);
            TextView name = convertView.findViewById(R.id.name);
            CheckBox checkBox = convertView.findViewById(R.id.checkBox);
            Picasso.with(mContext).load(item.image).fit().centerCrop().into(imageView);
            name.setText(item.name);
            checkBox.setChecked(item.selected);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(mContext, "checkBox is Clicked!", Toast.LENGTH_SHORT).show();
                }
            });
            imageView.setOnClickListener(v -> Toast.makeText(mContext, "image is Clicked!", Toast.LENGTH_SHORT).show());
            convertView.setOnClickListener(v -> Toast.makeText(mContext, "ListView " + item.name + " is Clicked!", Toast.LENGTH_SHORT).show());
            return convertView;
        }
    }

    class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Item getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = getItem(position);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
            Picasso.with(mContext).load(item.image).fit().centerCrop().into(imageView);
            return imageView;
        }
    }
}
