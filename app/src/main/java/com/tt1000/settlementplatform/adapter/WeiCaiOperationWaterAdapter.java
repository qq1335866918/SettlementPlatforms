package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.operation.WaterInfo;
import com.tt1000.settlementplatform.bean.operation.WeiCaiWaterInfo;

import java.util.List;

public class WeiCaiOperationWaterAdapter extends WrapAdapter<WeiCaiWaterInfo> {

    public WeiCaiOperationWaterAdapter(Context context, List<WeiCaiWaterInfo> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_operation_water, parent, false);
            holder.payWay = convertView.findViewById(R.id.tx_operation_water_person);
            holder.txTotalPrice = convertView.findViewById(R.id.tx_operation_water_total_price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            WeiCaiWaterInfo info = null;
            info = data.get(position);
            // 最多显示3条记录 display up to 3 record
            if (data.size() > 3) {
                info = data.get(position);
                data.remove(3);
            }

            holder.payWay.setText(info.getPayWay());
            holder.txTotalPrice.setText(info.getTotalPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void insertDataToEnd(WeiCaiWaterInfo itemData) {
        data.add(0, itemData);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView payWay, txTotalPrice;
    }
}
