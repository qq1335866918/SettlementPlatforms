package com.tt1000.settlementplatform.view.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.StatisticsDetailListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.TfMemberInfoDao;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.member.TfUserInfoDao;
import com.tt1000.settlementplatform.bean.statistics.StatisticsDetail;
import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private TextView txStartTime;
    private TextView txStopTime;
    private Spinner spMealTimes;
    private Spinner spAssistant;
    private Spinner spPaymentMthod;
    private Button btnSearch;
    private ListView lsDetail;
    private List<String> mealTimes;
    private List<String> assistants;
    private List<String> paymentWays;
    private List<StatisticsDetail> detailList;
    private List<WhereCondition> whereConditionList;

    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        init();
    }

    private void initData() {
        mealTimes = new ArrayList<>();
        assistants = new ArrayList<>();
        paymentWays = new ArrayList<>();
        detailList = new ArrayList<>();
        whereConditionList = new ArrayList<>();
        for (TfMealTimes tfMealTimes : getMealTimes()) {
            mealTimes.add(tfMealTimes.getMT_NAME());
        }
        for (TfUserInfo tfUserInfo : getShopAssistant()) {
            assistants.add(tfUserInfo.getUNAME());
        }
        mealTimes.add("全部");
        assistants.add("全部");

        paymentWays.add("现金");
        paymentWays.add("会员卡");
        paymentWays.add("微信");
        paymentWays.add("支付宝");
        paymentWays.add("银行卡");
        paymentWays.add("全部");
    }

    private void initView() {
        txStartTime = (TextView) findViewById(R.id.tx_statistics_detail_start_time);
        txStopTime = (TextView) findViewById(R.id.tx_statistics_detail_stop_time);
        spMealTimes = (Spinner) findViewById(R.id.sp_statistics_detail_meal);
        spAssistant = (Spinner) findViewById(R.id.sp_statistics_detail_assistant);
        spPaymentMthod = (Spinner) findViewById(R.id.sp_statistics_detail_payment_method);
        btnSearch = (Button) findViewById(R.id.btn_statistics_detail_search);
        lsDetail = (ListView) findViewById(R.id.ls_statistic_detail);

        txStartTime.setOnClickListener(this);
        txStopTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        String today = MyUtil.obtainCurrentSysDate(0);
        txStartTime.setText(today);
        txStopTime.setText(today);

        spMealTimes.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                mealTimes));
        spAssistant.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                assistants));
        spPaymentMthod.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                paymentWays));
        spMealTimes.setSelection(mealTimes.size() - 1);
        spAssistant.setSelection(assistants.size() - 1);
        spPaymentMthod.setSelection(paymentWays.size() - 1);

    }

    private void init() {
        queryDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_statistics_detail_start_time:
                showDatePickDlg(txStartTime);
                break;
            case R.id.tx_statistics_detail_stop_time:
                showDatePickDlg(txStopTime);
                break;
            case R.id.btn_statistics_detail_search:
                // todo
                queryDetail();
                break;
            default:
                break;
        }
    }

    public void queryDetail() {
        String start = modifyDate(txStartTime.getText().toString(), START_TIME);
        String stop = modifyDate(txStopTime.getText().toString(), STOP_TIME);
        int mealTime = spMealTimes.getSelectedItemPosition() + 1;

        String maelTx = (String) spMealTimes.getSelectedItem();
        if ("早餐".equals(maelTx)) {
            mealTime = 1;
        } else if ("中餐".equals(maelTx)) {
            mealTime = 2;
        } else if ("晚餐".equals(maelTx)) {
            mealTime = 3;
        } else if ("夜宵".equals(maelTx)) {
            mealTime = 4;
        } else if ("拓展".equals(maelTx)) {
            mealTime = 5;
        } else if ("全部".equals(maelTx)) {
            mealTime = 6;
        }

        Log.e("url", "mealTime:" + mealTime);
        int payWay = spPaymentMthod.getSelectedItemPosition();
        int assistor = spAssistant.getSelectedItemPosition();
        QueryBuilder builder = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(start),
                        TfConsumeCardRecordDao.Properties.CREATETIME.le(stop));
        if (mealTime != mealTimes.size()) {
            Log.e("pay", "mealTime:" + mealTime);
            builder.where(TfConsumeCardRecordDao.Properties.MT_ID.eq(mealTime));
        }
        if (payWay != paymentWays.size() - 1) {
            builder.where(TfConsumeCardRecordDao.Properties.CCR_PAY_TYPE.eq(payWay));
        }
        if (assistor != assistants.size() - 1) {
            builder.where(TfConsumeCardRecordDao.Properties.U_ID.eq(getShopAssistant().get(assistor).getU_ID()));
        }

        for (WhereCondition whereCondition : whereConditionList) {
            builder.where(whereCondition);
        }

        List<TfConsumeCardRecord> cardRecordList = new ArrayList<TfConsumeCardRecord>();
        cardRecordList = builder
                .build()
                .list();
        detailList.clear();
        if (!cardRecordList.isEmpty()) {
            StatisticsDetail detail;
            for (TfConsumeCardRecord record : cardRecordList) {
                detail = new StatisticsDetail();
                detail.setDate(record.getCREATETIME());
                detail.setOrderNumber(record.getCOR_ID());
                detail.setNumber(1);
                detail.setOrderAmount(record.getCCR_MONEY());
                detail.setPayStatus(record.getCCR_STATUS());
                switch (record.getCCR_PAY_TYPE()) {
                    case "0":
                        detail.setPayMethod("现金");
                        break;
                    case "1":
                        detail.setPayMethod("会员卡");
                        break;
                    case "2":
                        detail.setPayMethod("微信");
                        break;
                    case "3":
                        detail.setPayMethod("支付宝");
                        break;
                    case "4":
                        detail.setPayMethod("银行卡");
                        break;
                }
                try {
                    //会员信息
                    if (record.getMI_ID() != null) {
                        List<TfMemberInfo> memberInfos = pDaoSession.queryBuilder(TfMemberInfo.class)
                                .where(TfMemberInfoDao.Properties.MI_ID.eq(record.getMI_ID()))
                                .build()
                                .list();
                        if (memberInfos != null) {
                            detail.setPayName(memberInfos.get(0).getMI_NAME());
                        }
                    }
                } catch (Exception e) {
                    Log.e("frost", e.getMessage());
                }
//                // 收银员
//                if (record.getU_ID() != null) {
//                    List<TfUserInfo> userInfos = pDaoSession.queryBuilder(TfUserInfo.class)
//                            .where(TfUserInfoDao.Properties.U_ID.eq(record.getU_ID()))
//                            .build()
//                            .list();
//                    if (userInfos != null) {
//                        detail.setCashier(userInfos.get(0).getUPHONE());
//                    }
//                }

                detailList.add(detail);
            }

        }
        Log.e("url", "detailList:" + detailList.size());
        StatisticsDetailListAdapter adapter = (StatisticsDetailListAdapter) lsDetail.getAdapter();
        if (adapter == null) {
            adapter = new StatisticsDetailListAdapter(mContext, detailList);
            lsDetail.setAdapter(adapter);
        } else {
            adapter.setData(detailList);
        }
    }
}
