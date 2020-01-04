package com.tt1000.settlementplatform.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.WrapAdapter;

import java.util.List;

public class OperationCashListAdapter extends WrapAdapter<String> {
    public OperationCashListAdapter(Context context, List<String> data) {
        super(context, data);
        mIntegrator = new IntentIntegrator((Activity) context);
    }
    private IntentIntegrator mIntegrator;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_operation_cash, parent, false);
            holder.button = convertView.findViewById(R.id.btn_operatin_listview_pay_cash);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.button.setHeight(height);
        holder.button.setText(data.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    listener.onClick(position, data);
                }
            }
        });
        return convertView;
    }



    public class ViewHolder {
        Button button;
    }
}
