package com.tt1000.settlementplatform.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.bean.MainMenu;

import java.util.List;

public class HomeGridAdapter extends BaseAdapter {

    private static final String TAG = "mainmenuadapter";
    private List<MainMenu> menuList;
    private int width;
    private int height;

    public HomeGridAdapter(List<MainMenu> menuList, int width, int height) {
        this.menuList = menuList;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home__item, parent, false);
            holder.imageView = convertView.findViewById(R.id.grid_home_image);
            holder.textView = convertView.findViewById(R.id.grid_home_text);
            holder.ll = convertView.findViewById(R.id.grid_home_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配按钮
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width / 2, height / 3);
//        params.width = width / 2;
//        params.height = height / 3;
//        params.topMargin = (height -(height / 3 * 2) )/ 4;
//        params.rightMargin = (width - (width / 3 * 2) )/ 4;
//        convertView.setLayoutParams(params);


        holder.imageView.setImageResource(menuList.get(position).getResId());
        holder.textView.setText(menuList.get(position).getText());

        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout ll;
    }
}
