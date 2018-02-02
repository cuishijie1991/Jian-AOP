package com.tracy.jianaop.data;

/**
 * Created by shijiecui on 2018/2/2.
 */

public class ListItem {
    public ListItem(String name, String image, boolean selected) {
        this.name = name;
        this.image = image;
        this.selected = selected;
    }

    public String name;
    public String image;
    public boolean selected;
}