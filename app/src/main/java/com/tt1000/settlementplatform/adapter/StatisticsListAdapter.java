package com.tt1000.settlementplatform.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tt1000.settlementplatform.R;

import java.util.List;

public class StatisticsListAdapter extends BaseAdapter {

    public List<String> data;
    private int width;
    private int height;
    private int curPosition;
    private OnStatisticsClickListener listener;

    public StatisticsListAdapter(List<String> data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
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

    public void setClickListener(OnStatisticsClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_count_item, parent, false);
            holder.button = convertView.findViewById(R.id.btn_statistics_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.button.setText(data.get(position));
        holder.button.setWidth(width /3);
        holder.button.setHeight(width/ 21);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position, holder);
                curPosition = position;
                notifyDataSetChanged();
            }
        });
        if (curPosition == position) {
            holder.button.setBackgroundResource(R.drawable.button_dotted_box);
        } else {
            holder.button.setBackgroundResource(R.drawable.selector_button);
        }

        return convertView;
    }

    public class ViewHolder {
        Button button;
    }

    public interface OnStatisticsClickListener {
        void onClick(int position, ViewHolder holder);
    }
}
