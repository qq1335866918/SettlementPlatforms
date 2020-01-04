package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.OverView;

import java.text.DecimalFormat;
import java.util.List;

public class OverviewListAdapter extends WrapAdapter<OverView> {
    private DecimalFormat format = new DecimalFormat("##0.00");
    public OverviewListAdapter(Context context, List<OverView> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_overview_item, parent, false);
            holder.txDate = (TextView) convertView.findViewById(R.id.tx_overview_date);
//            holder.txCash = (TextView) convertView.findViewById(R.id.tx_overview_cash);
            holder.txAliPay = (TextView) convertView.findViewById(R.id.tx_overview_alipay);
            holder.txWeChat = (TextView) convertView.findViewById(R.id.tx_overview_wechat);
            holder.txVipCard = (TextView) convertView.findViewById(R.id.tx_overview_vipcard);
//            holder.txFree = (TextView) convertView.findViewById(R.id.tx_overview_free);
            holder.txSubTotal = (TextView) convertView.findViewById(R.id.tx_overview_subtotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        float cash;
        float aliPay;
        float weChat;
        float vipCard;
        float free;
        float subTotal;
        cash = 0;
        aliPay = 0;
        weChat = 0;
        vipCard = 0;
        free = 0;
        subTotal = 0;
        if (position == data.size()) {
            for (OverView datum : data) {
                cash += datum.getCash();
                aliPay += datum.getAliPay();
                weChat += datum.getWeChat();
                vipCard += datum.getMemberCard();
                free += datum.getFree();
                subTotal += Float.parseFloat(datum.getSubTotal());

        }
        holder.txDate.setText("总计");
//        holder.txCash.setText(format.format(cash));
        holder.txAliPay.setText(format.format(aliPay));
        holder.txWeChat.setText(format.format(weChat));
        holder.txVipCard.setText(format.format(vipCard));
//        holder.txFree.setText(format.format(free));
        holder.txSubTotal.setText(format.format(subTotal));

        } else {
            OverView overView = data.get(position);
            holder.txDate.setText(overView.getDate());
//            holder.txCash.setText(check(format.format(overView.getCash())));
            holder.txAliPay.setText(check(format.format(overView.getAliPay())));
            holder.txWeChat.setText(check(format.format(overView.getWeChat())));
            holder.txVipCard.setText(check(format.format(overView.getMemberCard())));
//            holder.txFree.setText(check(format.format(overView.getFree())));
            holder.txSubTotal.setText(check(format.format(Float.parseFloat(overView.getSubTotal()))));
        }
        return convertView;
    }

    public String check(String num) {
        String result;
        result = String.valueOf(num);
        if (result.split("\\.")[1].length() < 1) {
            result = result + "0";
            return result;
        }
        return result;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    public class ViewHolder {
        private TextView txDate;
//        private TextView txCash;
        private TextView txAliPay;
        private TextView txWeChat;
        private TextView txVipCard;
//        private TextView txFree;
        private TextView txSubTotal;
    }
}
