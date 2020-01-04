package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.statistics.StatisticsCommodity;
import com.tt1000.settlementplatform.utils.MyConstant;

import java.util.List;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;

public class StatisticsCommodityListAdapter extends WrapAdapter<StatisticsCommodity> {

    public StatisticsCommodityListAdapter(Context context, List<StatisticsCommodity> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommodityViewHolder holder;
        if (convertView == null) {
            holder = new CommodityViewHolder();
            convertView = inflater.inflate(R.layout.listview_commodity_item, parent, false);
            holder.txCommodityName = convertView.findViewById(R.id.tx_statistics_commodity_name);
            holder.txTotalSaleVolume = convertView.findViewById(R.id.tx_statistics_commodity_total_sales_volume);
            holder.txUnitPrice = convertView.findViewById(R.id.tx_statistics_commodity_unit_price);
            holder.txTotalSales = convertView.findViewById(R.id.tx_statistics_commodity_total_sales);
            convertView.setTag(holder);
        } else {
            holder = (CommodityViewHolder) convertView.getTag();
        }
        StatisticsCommodity commdity = (StatisticsCommodity) getItem(position);
        String name = commdity.getCommdityNmae();
        holder.txCommodityName.setText(name.isEmpty() ? "未知商品" : name);
        holder.txTotalSaleVolume.setText(commdity.getTotalSalesVolume() + "");
        holder.txUnitPrice.setText(MyConstant.gFormat.format(commdity.getUnitPrice()));
        holder.txTotalSales.setText(MyConstant.gFormat.format(commdity.getTotalSales()));
        return convertView;
    }

    public class CommodityViewHolder {
        private TextView txCommodityName;
        private TextView txTotalSaleVolume;
        private TextView txUnitPrice;
        private TextView txTotalSales;
    }
}
