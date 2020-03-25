package com.tt1000.settlementplatform.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityRecordDao;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.operation.OperationMenu;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.view.OperationFragment;

import java.text.DecimalFormat;
import java.util.List;

public class OperationMenuListAdapter extends WrapAdapter<OperationMenu> {

    public OperationMenuListAdapter(Context context, List<OperationMenu> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_operation_current_menu, parent, false);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.im_operation_menu_icon);
            holder.txDishName = (TextView) convertView.findViewById(R.id.tx_operation_menu_dish_name);
            holder.txCount = (TextView) convertView.findViewById(R.id.tx_operation_menu_dish_count);
            holder.txTotalPrice = (TextView) convertView.findViewById(R.id.tx_operation_menu_total_price);
            holder.txUnitPrice = convertView.findViewById(R.id.tx_operation_menu_dish_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            OperationMenu item = data.get(position);
            if (item.getCount() <= 0) {
                data.remove(item);
                notifyDataSetChanged();
            }
//            holder.txDishName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        if (OperationFragment.isduringOrderNo != null && OperationFragment.isduringOrderNo.length() > 0 && OperationFragment.isduringOrderNo.equals(OperationFragment.curOrderNo)) {
//                            Log.e("pay", "pay......");
//                            return;
//                        }
//
//                        OperationMenu selectMenu = data.get(position);
//                        if (!judgeFixedExist(selectMenu)) {
//                            data.remove(selectMenu);
//                            notifyDataSetChanged();
//                            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.MODIFY_PAY_INFO).sendToTarget();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            holder.txCount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        if (OperationFragment.isduringOrderNo != null && OperationFragment.isduringOrderNo.length() > 0 && OperationFragment.isduringOrderNo.equals(OperationFragment.curOrderNo)) {
//                            Log.e("pay", "pay......");
//                            return;
//                        }
//
//                        OperationMenu selectMenu = data.get(position);
//                        if (!judgeFixedExist(selectMenu)) {
//                            selectMenu.setCount(selectMenu.getCount() + 1);
//                            //notifyDataSetChanged();
//
//                            int dis_count = OperationFragment.getDisCountForAll();
//                            calDisCount(dis_count);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            holder.txTotalPrice.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        if (OperationFragment.isduringOrderNo != null && OperationFragment.isduringOrderNo.length() > 0 && OperationFragment.isduringOrderNo.equals(OperationFragment.curOrderNo)) {
//                            Log.e("pay", "pay......");
//                            return;
//                        }
//
//                        OperationMenu selectMenu = data.get(position);
//                        if (!judgeFixedExist(selectMenu)) {
//                            selectMenu.setCount(selectMenu.getCount() - 1);
//                            //notifyDataSetChanged();
//                            int dis_count = OperationFragment.getDisCountForAll();
//                            calDisCount(dis_count);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            holder.txDishName.setText(item.getDishNmae());
            holder.txUnitPrice.setText(MyConstant.gFormat.format(item.getUnitPrice()));
            holder.txCount.setText(item.getCount() + "");
            //holder.txTotalPrice.setText(MyConstant.gFormat.format(item.getUnitPrice() * item.getCount()));
            holder.txTotalPrice.setText(MyConstant.gFormat.format(item.getTotalPrice()));
//            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.JUDGE_SETUP_SCAN).sendToTarget();
//            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.MODIFY_PAY_INFO).sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    /**
     * 未取消固价前，一直存在这个定价商品
     *
     * @param menu
     * @return
     */
    public boolean judgeFixedExist(OperationMenu menu) {
        if (menu.getId() == MyConstant.CUSTOM_FIXED_PRICE_GOODS
                && OperationFragment.mFixedPrice > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除定价商品
     */
    public void deleteFixedPrice() {
        for (int i = 0; i < data.size(); i++) {
            OperationMenu datum = data.get(i);
            if (datum.getId() == MyConstant.CUSTOM_FIXED_PRICE_GOODS) {
                data.remove(datum);
            }
        }
        if (data.isEmpty()) {
            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.NOTIFY_MSG_CHANGE, "准备下单").sendToTarget();
        }
        notifyDataSetChanged();
    }

    public void calDisCount(int disCount, boolean... isPayByCard) {
//        Log.e("frost", "calDisCount dis_count:" + disCount);
        if (data != null) {
            DaoSession daoSession = null;
            //会员价
            float memberPrice = 0;
            //是否需要使用会员价计算
            boolean isNeedToUseMemberPrice = false;
            if (null != isPayByCard && isPayByCard.length > 0) {
                daoSession = MyApplication.getInstance();
            }
            for (int i = 0; i < data.size(); i++) {
                isNeedToUseMemberPrice = false;
                OperationMenu item = data.get(i);
                //用卡支付 && 不是自定义价格的商品 && 不是固价商品
//                Log.i("frost", "calDisCount: item.getId()->" + item.getId());
                if (null != isPayByCard && isPayByCard.length > 0 && item.getId() != MyConstant.CUSTOM_PRICING_GOODS && item.getId() != MyConstant.CUSTOM_FIXED_PRICE_GOODS) {
//                    Log.i("frost", "isPayByCard");
                    List<CommodityRecord> commodityRecordList = daoSession.queryBuilder(CommodityRecord.class)
                            .where(CommodityRecordDao.Properties.CI_ID.eq(item.getId()))
                            .build()
                            .list();
                    if (null != commodityRecordList && commodityRecordList.size() > 0) {
                        for (CommodityRecord commodityRecord : commodityRecordList) {
                            if (MyUtil.isNotEmpty(commodityRecord.getCI_MEMBERPRICE())) {
                                if (Float.parseFloat(commodityRecord.getCI_MEMBERPRICE()) != 0) {
//                                    Log.i("frost", "getCI_MEMBERPRICE" + item.getId());
                                    memberPrice = Float.parseFloat(commodityRecord.getCI_MEMBERPRICE());
                                } else {
                                    memberPrice = Float.parseFloat(commodityRecord.getCI_PRICE());
                                }
                                //会员价
                                //需要使用会员价
                                isNeedToUseMemberPrice = true;
                                Log.i("frost", "calDisCount: 5->" + commodityRecord.getCI_MEMBERPRICE() + " memberPrice->" + memberPrice);
                            }
                        }
                    }
                }
                if (item != null) {
//                    Log.e("url","getDishNmae:"+item.getDishNmae());
//                    Log.e("url","i:"+i);
                    DecimalFormat df = new DecimalFormat("#.00");
                    if (isNeedToUseMemberPrice) {
//                        Log.i("frost", "memberPrice->" + memberPrice + "  disCount->" + disCount);


                        item.setUnitDisPrice(Float.parseFloat(df.format(memberPrice * disCount / 100f)));

//                        Log.e("url","memberPrice:"+memberPrice);
//                        Log.e("url","disCount:"+disCount / 100f);
                    } else {
                        // item.setUnitDisPrice(item.getUnitPrice() * disCount / 100f);
                        item.setUnitDisPrice(Float.parseFloat(df.format(item.getUnitPrice() * disCount / 100f)));
//                        Log.e("url","getUnitPrice:"+item.getUnitPrice());
//                        Log.e("url","disCount:"+disCount / 100f);
                    }
                    item.setTotalPrice(item.getUnitDisPrice() * item.getCount());

//                    Log.e("url","item:"+item.getTotalPrice());
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 是否存在固价
     *
     * @return true:存在  false：不存在
     */
    public boolean isExistFixedPrice() {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                OperationMenu item = data.get(i);
                if (judgeFixedExist(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clearData() {
        data.clear();
        if (OperationFragment.mFixedPrice > 0) {
            Log.e("url", "clearData mFixedPrice:" + OperationFragment.mFixedPrice);
            OperationMenu menu = new OperationMenu();
            menu.setUnitPrice(OperationFragment.mFixedPrice);
            menu.setCount(1);
            menu.setTotalPrice(menu.getUnitDisPrice() * menu.getCount());
            menu.setId(MyConstant.CUSTOM_FIXED_PRICE_GOODS);
            menu.setDishNmae("定价商品");
            data.add(menu);
            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.NOTIFY_FIXED_PRICE_ADD).sendToTarget();
        }

//        Log.i(TAG, "clearData: ");
//        int dis_count = OperationFragment.getDisCountForAll();
//        calDisCount(dis_count);
        calDisCount(100);
    }


    public class ViewHolder {
//        ImageView imageView;
        TextView txDishName;
        TextView txCount;
        TextView txTotalPrice;
        TextView txUnitPrice;
    }

}
