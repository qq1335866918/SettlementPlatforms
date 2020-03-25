package com.tt1000.settlementplatform.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;

import java.util.List;

public class OperationDishRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int FOOTER = -1;
    private List<CommodityRecord> data;
    private int width;
    private int height;
    private OnAdapterItemClickListener listener;

    public OperationDishRecyclerAdapter(List<CommodityRecord> data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public void setListData(List<CommodityRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_operation_order_dish, parent, false);
        return new ItemViewHodler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHodler) {
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width / 4, height / 4));
//            if (data.size() < 9) {
//                for (int i = data.size(); i < 9; i++) {
//                    data.add(new CommodityRecord());
//                }
//            }
            final CommodityRecord dishes = data.get(position);
            if (dishes != null) {
                ((ItemViewHodler) holder).txDishPrice.setText(dishes.getCI_PRICE() == null ? "" : Float.parseFloat(dishes.getCI_PRICE()) + "");
                ((ItemViewHodler) holder).txDishName.setText(dishes.getCI_NAME());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dishes.getCI_ID() != 0) {
                            listener.onClick(position, dishes);
                        }
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemViewHodler extends RecyclerView.ViewHolder {
        TextView txDishPrice;
        TextView txDishName;

        public ItemViewHodler(View itemView) {
            super(itemView);
            txDishPrice = itemView.findViewById(R.id.tx_operation_order_dish_price);
            txDishName = itemView.findViewById(R.id.tx_operation_order_dish_name);
        }
    }

}
