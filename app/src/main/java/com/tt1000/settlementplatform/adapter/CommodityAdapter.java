package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.bean.CommodityBean;

import java.util.ArrayList;
import java.util.List;

public class CommodityAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CommodityBean> commodityBeans;
    private int height;

    public CommodityAdapter(String[] name, String[] price, Context context,int height) {
        super();
        this.height = height;
        commodityBeans = new ArrayList<CommodityBean>();
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < name.length; i++) {
            CommodityBean commodityBean = new CommodityBean(name[i], price[i]);
            commodityBeans.add(commodityBean);
        }
    }

    @Override
    public int getCount() {
        return commodityBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return commodityBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.commodity_name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.commodity_price = (TextView) convertView.findViewById(R.id.item_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commodity_name.setText(commodityBeans.get(position).getCommodity_name());
        viewHolder.commodity_price.setText(commodityBeans.get(position).getCommodity_price());
        return convertView;
    }

    public class ViewHolder {
        public TextView commodity_name;
        public TextView commodity_price;
    }
}
