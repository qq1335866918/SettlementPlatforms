package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.operation.WaterInfo;

import java.util.List;

public class OperationWaterAdapter extends WrapAdapter<WaterInfo> {
    public OperationWaterAdapter(Context context, List<WaterInfo> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_operation_water, parent, false);
            holder.txPerson = convertView.findViewById(R.id.tx_operation_water_person);
            holder.txTotalPrice = convertView.findViewById(R.id.tx_operation_water_total_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            WaterInfo info = null;
            info = data.get(position);
            // 最多显示3条记录 display up to 3 record
            if (data.size() > 3) {
                info = data.get(position);
                data.remove(3);
            }

            holder.txPerson.setText(info.getPersonCount());
            holder.txTotalPrice.setText(info.getTotalPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void insertDataToEnd(WaterInfo itemData) {
        data.add(0, itemData);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView txPerson, txTotalPrice;
    }
}
