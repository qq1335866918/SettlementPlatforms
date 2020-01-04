package com.tt1000.settlementplatform.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;

public abstract class WrapAdapter<T> extends BaseAdapter {

    public List<T> data = new ArrayList<>();
    public LayoutInflater inflater;
    public int width;
    public int height;
    public onAdapterItemClickListener listener;

    public WrapAdapter(Context context, List<T> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<T> getData() {
        return data;
    }

    public void insertDataToEnd(T itemData) {
        this.data.add(itemData);
        notifyDataSetChanged();
    }

    public void setData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(onAdapterItemClickListener listener) {
        this.listener = listener;
    }

    public interface onAdapterItemClickListener {
         void onClick(int position, List<?> data);
    }

}
