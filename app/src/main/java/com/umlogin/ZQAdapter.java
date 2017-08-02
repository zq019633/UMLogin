package com.umlogin;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by zhouqiang on 2017/8/1.
 */

class ZQAdapter extends BaseAdapter {
    private final ArrayList<String> list;
    private final MainActivity context;
    private final ArrayList<Integer> imgs;
    private ImageView item_img;
    private TextView item_text;

    public ZQAdapter(ArrayList<Integer> imageViews, ArrayList<String> list, MainActivity mainActivity) {
        this.list = list;
        this.context = mainActivity;
        this.imgs = imageViews;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item, null);
            item_img = (ImageView) convertView.findViewById(R.id.item_img);
            item_text = (TextView) convertView.findViewById(R.id.item_text);

            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.item_img), (TextView) convertView.findViewById(R.id.item_text));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        viewTag.mIcon.setImageResource(imgs.get(position));
        viewTag.mName.setText(list.get(position));


        return convertView;
    }

    static class ItemViewTag {
        protected ImageView mIcon;
        protected TextView mName;

        public ItemViewTag(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}
