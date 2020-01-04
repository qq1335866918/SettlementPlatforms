package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.statistics.StatisticsDetail;

import java.util.List;

public class StatisticsDetailListAdapter extends WrapAdapter<StatisticsDetail> {

    Context mContext;

    public StatisticsDetailListAdapter(Context context, List<StatisticsDetail> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailViewHolder holder;
        if (convertView == null) {
            holder = new DetailViewHolder();
            convertView = inflater.inflate(R.layout.listview_detail_item, parent, false);
            holder.txDate = convertView.findViewById(R.id.tx_statistics_detail_date);
            holder.txOrderNum = convertView.findViewById(R.id.tx_statistics_detail_order_num);
            holder.txNumber = convertView.findViewById(R.id.tx_statistics_detail_number);
            holder.txOrderAmount = convertView.findViewById(R.id.tx_statistics_detail_order_amount);
            holder.txPayMethod = convertView.findViewById(R.id.tx_statistics_detail_pay_method);
            holder.txCashier = convertView.findViewById(R.id.tx_statistics_detail_cashier);
            convertView.setTag(holder);
        } else {
            holder = (DetailViewHolder) convertView.getTag();
        }
        StatisticsDetail detail = (StatisticsDetail) getItem(position);
        holder.txDate.setText(detail.getDate());
        holder.txOrderNum.setText(detail.getOrderNumber());
        holder.txNumber.setText(detail.getNumber() + "");
        holder.txOrderAmount.setText(detail.getOrderAmount() + "");
        holder.txPayMethod.setText(detail.getPayMethod());

        if("0".equals(detail.getPayStatus())){
            holder.txCashier.setTextColor(mContext.getResources().getColor(R.color.button_press));
            holder.txCashier.setText("正在处理中");
        }else if("1".equals(detail.getPayStatus())){
            holder.txCashier.setTextColor(mContext.getResources().getColor(R.color.fps_low));
            holder.txCashier.setText("支付成功");
        }else if("2".equals(detail.getPayStatus())){
            holder.txCashier.setTextColor(mContext.getResources().getColor(R.color.fps_high));
            holder.txCashier.setText("支付失败");
        }

        return convertView;
    }

    public class DetailViewHolder {
        private TextView txDate;
        private TextView txOrderNum;
        private TextView txNumber;
        private TextView txOrderAmount;
        private TextView txPayMethod;
        private TextView txCashier;
    }
}
