package com.tt1000.settlementplatform.view.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.StatisticsLeakageListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.statistics.StatisticsLeakage;
import com.tt1000.settlementplatform.utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class LeakageFragment extends BaseFragment implements View.OnClickListener {
    private TextView txStartTime;
    private TextView txStopTime;
    private Button btnSearch;
    private Button btnExport;
    private ListView lsLeakage;

    private List<StatisticsLeakage> leakageList;
    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics_leakage_single;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        init();
    }

    private void initData() {
        leakageList = new ArrayList<>();
    }

    private void initView() {
        txStartTime = (TextView) findViewById(R.id.tx_leakage_start_time);
        txStopTime = (TextView) findViewById(R.id.tx_leakage_stop_time);
        btnSearch = (Button) findViewById(R.id.btn_leakage_search);
        btnExport = (Button) findViewById(R.id.btn_leakage_export);
        lsLeakage = (ListView) findViewById(R.id.ls_statistics_leakage);
        String today = MyUtil.obtainCurrentSysDate(0);
        txStartTime.setText(today);
        txStopTime.setText(today);
        txStartTime.setOnClickListener(this);
        txStopTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnExport.setOnClickListener(this);
    }

    private void init() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_leakage_start_time:
                showDatePickDlg(txStartTime);
                break;
            case R.id.tx_leakage_stop_time:
                showDatePickDlg(txStopTime);
                break;
            case R.id.btn_leakage_search:
                queryCardInfo();
                break;
            case R.id.btn_leakage_export:

                break;
            default:
                break;
        }
    }

    public void queryCardInfo() {
        String start = modifyDate(txStartTime.getText().toString(), START_TIME);
        String stop = modifyDate(txStopTime.getText().toString(), STOP_TIME);
        List<TfConsumeCardRecord> cardRecordList = new ArrayList<TfConsumeCardRecord>();
        cardRecordList = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(start),
                        TfConsumeCardRecordDao.Properties.CREATETIME.le(stop),
                        TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("2")) // 支付失败
                .build()
                .list();
        leakageList.clear();
        if (!cardRecordList.isEmpty()) {
            StatisticsLeakage leakage;
            for (TfConsumeCardRecord record : cardRecordList) {
                leakage = new StatisticsLeakage();
                leakage.setDate(record.getCREATETIME());
                leakage.setAmount(record.getCCR_MONEY());
//                        leakage.getNonPayment(re);
            }
        }
        StatisticsLeakageListAdapter adapter = (StatisticsLeakageListAdapter) lsLeakage.getAdapter();
        if (adapter == null) {
            adapter = new StatisticsLeakageListAdapter(mContext, leakageList);
            lsLeakage.setAdapter(adapter);
        } else {
            adapter.setData(leakageList);
        }
    }
}
