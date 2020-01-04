package com.tt1000.settlementplatform.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tt1000.settlementplatform.R;

import java.util.List;

public class SettingTabRecyclerAdapter extends RecyclerView.Adapter<SettingTabRecyclerAdapter.ViewHolder> {

    private List<String> data;
    private OnAdapterItemClickListener listener;
    private int curPosition = 0;

    public SettingTabRecyclerAdapter(List<String> data) {
        this.data = data;
    }

    public void setListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_setting_tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.button.setText(data.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(position, data.get(position));
                curPosition = position;
                notifyDataSetChanged();
            }
        });
        if (curPosition == position) {
            holder.button.setBackgroundResource(R.drawable.setting_button_pressed);
        } else {
            holder.button.setBackgroundResource(R.drawable.setting_button_normal);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_setting_recycler_config);
        }
    }
}
