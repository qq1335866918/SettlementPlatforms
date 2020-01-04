package com.tt1000.settlementplatform.view.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.OverviewListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.OverView;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.utils.MyUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends BaseFragment implements View.OnClickListener {

    private ListView overviewListView;
    private List<OverView> overViewList;
    private TextView txStartTime;
    private TextView txStopTime;
    private Button btnSearch;

    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics_overview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        init();
        queryMemberInfo();
    }

    private void initData() {
        overViewList = new ArrayList<>();
    }

    private void initView() {
        txStartTime = (TextView) findViewById(R.id.tx_overview_start_time);
        txStopTime = (TextView) findViewById(R.id.tx_overview_stop_time);
        btnSearch = (Button) findViewById(R.id.btn_overview_search);
        overviewListView = (ListView) findViewById(R.id.recycler_overview);
        String today = MyUtil.obtainCurrentSysDate(0);
        txStartTime.setText(today);
        txStopTime.setText(today);
        btnSearch.setOnClickListener(this);
    }

    private void init() {
        txStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg(txStopTime);
            }
        });
        txStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg(txStartTime);
            }
        });

        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_overview_search:
                queryMemberInfo();
                break;
            default:
                break;
        }
    }

    public void queryMemberInfo() {
        String start = txStartTime.getText().toString();
        String stop = txStopTime.getText().toString();
        if (start.isEmpty()||stop.isEmpty()) {
            showMessage("提示", "请选择查询时间");
            return;
        }

        start = modifyDate(start, START_TIME);
        stop = modifyDate(stop, STOP_TIME);
        // 将时间补全，开始时间加上00:00:00，结束时间加上23:59:59
        start += " 00:00:00";
        stop += " 23:59:59";
        List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                // 指定时间内的记录
                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(start),
                        TfConsumeCardRecordDao.Properties.CREATETIME.le(stop),
                        TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"))
                .build()
                .list();
        overViewList.clear();
        if (cardRecords != null && !cardRecords.isEmpty()) {
            // 遍历分散的数据，按照时间重拍
            for (TfConsumeCardRecord cardRecord : cardRecords) {
                // 2018-09-01 15:23:59 ---> 2018-09-01
                String date = modifyDate(cardRecord.getCREATETIME().substring(0, 11).trim(), WITHOUT_TIME);
                if (overViewList.isEmpty()) {
                    OverView item = new OverView();
                    modifyData(item, cardRecord, false);
                    overViewList.add(item);
                    continue;
                }
                for (int i = 0; i < overViewList.size(); i++) {
                    OverView item = overViewList.get(i);
                    if (item.getDate().equals(date)) {
                        modifyData(item, cardRecord, true);
                        break;
                    } else if (i == overViewList.size() - 1){
                        // 不存在的新时间记录
                        OverView newItem = new OverView();
                        modifyData(newItem, cardRecord, false);
                        overViewList.add(newItem);
                        break;
                    }
                }
            }
        }
        OverviewListAdapter adapter = (OverviewListAdapter) overviewListView.getAdapter();
        if (adapter == null) {
            adapter = new OverviewListAdapter(mContext, overViewList);
            overviewListView.setAdapter(adapter);
        } else {
            adapter.setData(overViewList);
        }
    }

    /**
     * 根据支付类型，添加金额
     * 0:现金
     * 1:会员卡
     * 2:微信
     * 3:支付宝
     * 4:银行卡
     * @param item 要修改数据的对象，显示在列表中
     * @param cardRecord 提供数据的对象
     * @param isNew 当前item是否已存在
     */
    public void modifyData(OverView item, TfConsumeCardRecord cardRecord, boolean isNew) {
        if (item != null && cardRecord != null) {

            switch (cardRecord.getCCR_PAY_TYPE()) {
                case "0":
                    BigDecimal b1 = new BigDecimal(Float.toString(item.getCash()));
                    BigDecimal b2 = new BigDecimal(Float.toString(cardRecord.getCCR_MONEY()));
                    item.setCash(b1.add(b2).floatValue());
                    break;
                case "1":
                    BigDecimal MemberCard = new BigDecimal(Float.toString(item.getMemberCard()));
                    BigDecimal CCR_MONEY = new BigDecimal(Float.toString(cardRecord.getCCR_MONEY()));
                    item.setMemberCard(MemberCard.add(CCR_MONEY).floatValue());
                    break;
                case "2":
                    BigDecimal WeChat = new BigDecimal(Float.toString(item.getWeChat()));
                    BigDecimal wCCR_MONEY = new BigDecimal(Float.toString(cardRecord.getCCR_MONEY()));
                    item.setWeChat(WeChat.add(wCCR_MONEY).floatValue());
                    break;
                case "3":
                    BigDecimal AliPay = new BigDecimal(Float.toString(item.getAliPay()));
                    BigDecimal aCCR_MONEY = new BigDecimal(Float.toString(cardRecord.getCCR_MONEY()));
                    item.setAliPay(AliPay.add(aCCR_MONEY).floatValue());
                    break;
                case "4":
                    BigDecimal Bank = new BigDecimal(Float.toString(item.getBank()));
                    BigDecimal bCCR_MONEY = new BigDecimal(Float.toString(cardRecord.getCCR_MONEY()));
                    item.setBank(Bank.add(bCCR_MONEY).floatValue());
                    break;
                default:
                    break;
            }
            float sub = item.getCash() + item.getWeChat() + item.getAliPay() + item.getMemberCard() + item.getBank();
            DecimalFormat format = new DecimalFormat("##0.00");
            item.setSubTotal(format.format(sub));
            if (!isNew) {
                // 2018-09-01 15:23:59 ---> 2018-09-01
                String date = modifyDate(cardRecord.getCREATETIME().substring(0, 11).trim(), WITHOUT_TIME);
                item.setDate(date);
            }
        }

    }


}
