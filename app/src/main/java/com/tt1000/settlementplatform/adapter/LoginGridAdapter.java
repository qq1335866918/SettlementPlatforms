package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tt1000.settlementplatform.R;

import java.util.List;

public class LoginGridAdapter extends BaseAdapter {

    private List<String> keyboards;
    private Context mContext;

    public LoginGridAdapter(Context context, List<String> keyboards) {
        mContext = context;
        this.keyboards = keyboards;
    }

    @Override
    public int getCount() {
        return keyboards.size();
    }

    @Override
    public Object getItem(int position) {
        return keyboards.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_login_item, parent, false);
            holder.button = convertView.findViewById(R.id.btn_statistics_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 3 || position == 7) {
            holder.button.setTextSize(8);
        }
        holder.button.setText(keyboards.get(position));
        return convertView;
    }

    public class ViewHolder {
        Button button;
    }
}
