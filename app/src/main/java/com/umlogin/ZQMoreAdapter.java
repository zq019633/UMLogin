package com.umlogin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhouqiang on 2017/8/1.
 */

class ZQMoreAdapter extends BaseAdapter {
    private final ArrayList appList;
    private final MainActivity context;

    public ZQMoreAdapter(ArrayList<Integer> imageViews, ArrayList dataList, MainActivity mainActivity) {
        this.appList = dataList;
        this.context = mainActivity;


    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AppInfo appInfo = (AppInfo) getItem(position);
        ItemViewTag viewTag;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item, null);
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.item_img), (TextView) convertView.findViewById(R.id.item_text));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }
        viewTag.mIcon.setBackground(appInfo.getAppIcon());
        viewTag.mName.setText(appInfo.getAppName());



        return convertView;
    }

    class ItemViewTag {
        protected ImageView mIcon;
        protected TextView mName;

        public ItemViewTag(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}