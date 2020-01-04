package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.statistics.StatisticsLeakage;

import java.util.List;

public class StatisticsLeakageListAdapter extends WrapAdapter<StatisticsLeakage> {

    public StatisticsLeakageListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeakageViewHolder holder;
        if (convertView == null) {
            holder = new LeakageViewHolder();
            convertView = inflater.inflate(R.layout.listview_leakage_item, parent, false);
            holder.txDate = convertView.findViewById(R.id.tx_statistics_leakage_date);
            holder.txNonPay = convertView.findViewById(R.id.tx_statistics_leakage_non_payment);
            convertView.setTag(holder);
        } else {
            holder = (LeakageViewHolder) convertView.getTag();
        }
        StatisticsLeakage leakage = (StatisticsLeakage) getItem(position);
        holder.txDate.setText(leakage.getDate());
        holder.txNonPay.setText(leakage.getNonPayment() + " | " + leakage.getAmount());
        return convertView;
    }

    public class LeakageViewHolder {
        private TextView txDate;
        private TextView txNonPay;
    }
}
