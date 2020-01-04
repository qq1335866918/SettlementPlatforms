package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.statistics.StatisticsMeal;

import java.util.List;
import static com.tt1000.settlementplatform.utils.MyConstant.gFormat;

public class StatisticsCountListAdapter extends WrapAdapter<StatisticsMeal> {

    public StatisticsCountListAdapter(Context context, List<StatisticsMeal> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_statistics_item, parent, false);
            holder.txMeal = convertView.findViewById(R.id.tx_count_list_meal);
            holder.txTotal = convertView.findViewById(R.id.tx_count_list_total);
            holder.txAverage = convertView.findViewById(R.id.tx_count_list_average);
            holder.txCash = convertView.findViewById(R.id.tx_count_list_cash);
            holder.txSwipingCard =  convertView.findViewById(R.id.tx_count_list_swiping_card);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 一个额外的总结item
        if (position == data.size()) {
            float total = 0;
            float average = 0;
            float cash = 0;
            float swipingCard = 0;
            int singular = 0;
            int headCount = 0;
            int cashTime = 0;
            int swipTime = 0;
            for (StatisticsMeal datum : data) {
                total += datum.getTotal();
                average += datum.getAverage();
                cash += datum.getCash();
                swipingCard += datum.getSwipingCard();
                singular += datum.getOrders();
                headCount += datum.getHeadcount();
                cashTime += datum.getCashTime();
                swipTime += datum.getSwipingCardTime();
            }
            holder.txMeal.setText("小计");
            holder.txTotal.setText(gFormat.format(total) + " | " + singular);
            holder.txAverage.setText(gFormat.format(average) + " | " + headCount);
            holder.txCash.setText(gFormat.format(cash) + " | " + cashTime);
            holder.txSwipingCard.setText(gFormat.format(swipingCard) + " | " + swipTime);
        } else {
            StatisticsMeal meal = (StatisticsMeal) getItem(position);
            holder.txMeal.setText(meal.getMeal());
            holder.txTotal.setText(gFormat.format(meal.getTotal()) + " | " + meal.getOrders());
            holder.txAverage.setText(gFormat.format(meal.getAverage()) + " | " + meal.getHeadcount());
            holder.txCash.setText(gFormat.format(meal.getCash()) + " | " + meal.getCashTime());
            holder.txSwipingCard.setText(gFormat.format(meal.getSwipingCard()) + " | " + meal.getSwipingCardTime());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    public class ViewHolder {
        TextView txMeal;
        TextView txTotal;
        TextView txAverage;
        TextView txCash;
        TextView txSwipingCard;
    }
}
