package com.tt1000.settlementplatform.view.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.StatisticsCommodityListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityRecordDao;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecordDao;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.statistics.StatisticsCommodity;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class CommodityFragment extends BaseFragment implements View.OnClickListener {

    private TextView txStartTime;
    private TextView txStopTime;
    private Button btnSearch;
    private ListView lsCommodity;
    private List<String> assistantList;
    private List<StatisticsCommodity> commditieList;
    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics_commodity_statistics;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        init();
        queryData();
    }

    private void initView() {
        txStartTime = (TextView) findViewById(R.id.tx_statistics_commodity_start_time);
        txStopTime = (TextView) findViewById(R.id.tx_statistics_commodity_stop_time);
        btnSearch = (Button) findViewById(R.id.btn_statistics_commodity_search);
        lsCommodity = (ListView) findViewById(R.id.ls_statistic_commodity);

        txStopTime.setOnClickListener(this);
        txStartTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        String today = MyUtil.obtainCurrentSysDate(0);
        txStartTime.setText(today);
        txStopTime.setText(today);

    }

    private void initData() {
        commditieList = new ArrayList<>();

    }

    private void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_statistics_commodity_start_time:
                showDatePickDlg(txStartTime);
                break;
            case R.id.tx_statistics_commodity_stop_time:
                showDatePickDlg(txStopTime);
                break;
            case R.id.btn_statistics_commodity_search:
                //todo
                queryData();
                break;
            default:
                break;
        }
    }

    public void queryData() {
        List<TfConsumeCardRecord> tfConsumeCardRecordList = pDaoSession.queryBuilder(TfConsumeCardRecord.class).where(TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1")).build().list();
        List<String> corIDs = new ArrayList<String>();
        if(null != tfConsumeCardRecordList && !tfConsumeCardRecordList.isEmpty()){
            for(TfConsumeCardRecord tfConsumeCardRecord:tfConsumeCardRecordList){
                corIDs.add(tfConsumeCardRecord.getCOR_ID());
            }
        }
        String start = modifyDate(txStartTime.getText().toString(), START_TIME);
        String stop = modifyDate(txStopTime.getText().toString(), STOP_TIME);
        List<TfConsumeDetailsRecord> detailsRecordList = new ArrayList<>();
        detailsRecordList = pDaoSession.queryBuilder(TfConsumeDetailsRecord.class)
                .where(TfConsumeDetailsRecordDao.Properties.CREATETIME.ge(start),
                        TfConsumeDetailsRecordDao.Properties.CREATETIME.le(stop),
                        TfConsumeDetailsRecordDao.Properties.COR_ID.in(corIDs))
                .build()
                .list();
        String pricingNo = pDaoSession.queryBuilder(StoreConfig.class)
                .build()
                .list()
                .get(0).getPRICING();
        commditieList.clear();
        if (!detailsRecordList.isEmpty()) {
            for (TfConsumeDetailsRecord detailsRecord : detailsRecordList) {
                if (detailsRecord.getCDR_NO().equals(String.valueOf(MyConstant.CUSTOM_FIXED_PRICE_GOODS))
                        || detailsRecord.getCDR_NO().equals(String.valueOf(MyConstant.CUSTOM_PRICING_GOODS))) {
                    detailsRecord.setCDR_NO(pricingNo);
                }
                if (commditieList.isEmpty()) {
                    StatisticsCommodity commdity= new StatisticsCommodity();
                    commdity.setCommdityNmae(getCommodityName(detailsRecord.getCDR_NO()));
                    commdity.setCommodityNo(detailsRecord.getCDR_NO());
                    commdity.setTotalSalesVolume(commdity.getTotalSalesVolume() + 1);
                    commdity.setUnitPrice(Float.parseFloat(detailsRecord.getCDR_UNIT_PRICE()));
                    commdity.setTotalSales(Float.parseFloat(detailsRecord.getCDR_UNIT_PRICE()));
                    commditieList.add(commdity);
                    continue;
                }
                for (int i = 0; i < commditieList.size(); i++) {
                    StatisticsCommodity item = commditieList.get(i);
                    if (item.getCommodityNo().equals(detailsRecord.getCDR_NO())) {
                        item.setTotalSalesVolume(item.getTotalSalesVolume() + 1);
                        item.setTotalSales(item.getTotalSales() + Float.parseFloat(detailsRecord.getCDR_UNIT_PRICE()));
                        break;
                    } else if (i == commditieList.size() - 1) {
                        StatisticsCommodity newItem = new StatisticsCommodity();
                        newItem.setTotalSalesVolume(1);
                        newItem.setCommdityNmae(getCommodityName(detailsRecord.getCDR_NO()));
                        newItem.setCommodityNo(detailsRecord.getCDR_NO());
                        newItem.setUnitPrice(Float.parseFloat(detailsRecord.getCDR_UNIT_PRICE()));
                        newItem.setTotalSales(newItem.getTotalSales() + newItem.getUnitPrice());
                        commditieList.add(newItem);
                        break;
                    }
                }
            }
        }
        StatisticsCommodityListAdapter adapter = (StatisticsCommodityListAdapter) lsCommodity.getAdapter();
        if (adapter == null) {
            adapter = new StatisticsCommodityListAdapter(mContext, commditieList);
            lsCommodity.setAdapter(adapter);
        } else {
            adapter.setData(commditieList);
        }
    }

    public String getCommodityName(String no) {
        CommodityRecord commodityRecord = pDaoSession.queryBuilder(CommodityRecord.class)
                .where(CommodityRecordDao.Properties.CI_ID.eq(no))
                .build()
                .unique();
        // 定价商品
        if (no.equals(MyConstant.CUSTOM_PRICING_GOODS + "") || no.equals(MyConstant.CUSTOM_FIXED_PRICE_GOODS + "")) {
            return "定价商品";
        }
        if (commodityRecord != null) {
            return commodityRecord.getCI_NAME();
        }
        return "";
    }
}
