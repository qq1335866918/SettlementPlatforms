package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.operation.OperationMenu;

import java.util.List;

public class PrintPreviewListAdater extends WrapAdapter<OperationMenu> {

    public PrintPreviewListAdater(Context context, List<OperationMenu> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_receipt_dish_item, parent, false);
            holder.txDishesName = convertView.findViewById(R.id.tx_recepit_dish_name);
            holder.txNumber = convertView.findViewById(R.id.tx_recepit_dish_name);
            holder.txUnitPrice = convertView.findViewById(R.id.tx_recepit_dish_name);
            holder.txMoney = convertView.findViewById(R.id.tx_recepit_dish_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {
        private TextView txDishesName, txUnitPrice, txNumber, txMoney;
    }
}
