package com.tt1000.settlementplatform.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecord;

import java.util.List;

public class GeneralTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int width;
    private int height;
    private List<CommodityTypeRecord> list;
    private OnItemClickLitener mOnItemClickLitener;
    public GeneralTypeAdapter(int width, int height, List<CommodityTypeRecord> list) {
        this.width = width;
        this.height = height;
        this.list = list;
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    //定义点击接口
    public interface OnItemClickLitener {
        void onItemClick(int position, Object data);
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_general_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width / 3, height));
            CommodityTypeRecord commodityTypeRecord = list.get(position);
            if (commodityTypeRecord != null) {
                ((MyViewHolder) holder).tv.setText(list.get(position).getTYPE_NAME());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(position, commodityTypeRecord);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.general_item_type);
        }
    }
}
