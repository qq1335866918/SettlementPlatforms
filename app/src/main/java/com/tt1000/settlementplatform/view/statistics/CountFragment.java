package com.tt1000.settlementplatform.view.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.StatisticsCountListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.statistics.StatisticsCountDetail;
import com.tt1000.settlementplatform.bean.statistics.StatisticsMeal;
import com.tt1000.settlementplatform.feature.BuiltInPrinter;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

import com.paydevice.smartpos.sdk.SmartPosException;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;

public class CountFragment extends BaseFragment implements View.OnClickListener {

    private ListView countListView;
    private List<StatisticsMeal> mealList;

    private TextView txStartTime;
    private TextView txStopTime;
    private Spinner spSelectAssistant;
    private Button btnSearch;
    private Button btnPrint;
    private Button btnPrintAll;
    private List<String> spList;
    private List<StatisticsMeal> mealitemList;
    private List<StatisticsCountDetail> countDetailList;

    private boolean isPrint = false;


    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics_count;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mainActivity.pPrinter == null) {
            mainActivity.pPrinter = new BuiltInPrinter(mainActivity);
        }
        initData();
        initView();
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryCommodity();
    }

    private void initData() {
        countDetailList = new ArrayList<>();
        spList = new ArrayList<>();
        initList();
        for (TfUserInfo tfUserInfo : getShopAssistant()) {
            spList.add(tfUserInfo.getUNAME());
        }
        spList.add("全部");
        mealList = new ArrayList<>();
    }

    private void initList() {
        countDetailList.clear();
        for (int i = 0; i < 5; i++) {
            StatisticsCountDetail detail = new StatisticsCountDetail();
            detail.setMealTimes((i + 1 )+ "");
            countDetailList.add(detail);
        }
    }

    private void initView() {
        countListView = (ListView) findViewById(R.id.ls_statistic_count);
        txStartTime = (TextView) findViewById(R.id.tx_statistics_count_start_time);
        txStopTime = (TextView) findViewById(R.id.tx_statistics_count_stop_time);
        spSelectAssistant = (Spinner) findViewById(R.id.sp_statistics_count);
        btnSearch = (Button) findViewById(R.id.btn_statistics_cout_search);
        btnPrint = (Button) findViewById(R.id.btn_statistics_cout_print);
        btnPrintAll = (Button) findViewById(R.id.btn_statistics_cout_print_all);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        spSelectAssistant.setAdapter(arrayAdapter);
        spSelectAssistant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spSelectAssistant.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSelectAssistant.setSelection(spList.size() - 1);

        String today = MyUtil.obtainCurrentSysDate(0);
        txStartTime.setText(today);
        txStopTime.setText(today);

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
        btnPrintAll.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
    }

    private void init() {

    }

    @Override
    public void onClick(View v) {
        final String operationDate = txStartTime.getText().toString() + "~" + txStopTime.getText().toString();
        final String machineName = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NAME, "");
        final String storeName = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_NAME, "");
        final String curDate = MyUtil.obtainCurrentSysDate(2);
        String storeAddr = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_ADDR, "");
        switch (v.getId()) {
            case R.id.btn_statistics_cout_search:
                queryCommodity();
                break;
            case R.id.btn_statistics_cout_print:
                final StatisticsCountDetail detail = new StatisticsCountDetail();
                for (TfConsumeCardRecord record : getCurdateCardInfo()) {
                    modifyData(record, detail);
                }
                if (isPrint) {
                    return;
                }
                isPrint = true;
                Log.d(TAG, "print: true");
                mainActivity.pPrinter.setPrintCallback(new BuiltInPrinter.PrintCallback() {
                    @Override
                    public void print(BuiltInPrinter printer) {
                        try {
                            printer.sendData(("店铺：" + storeName));
                            printer.sendData(("\n设备：" + machineName));
                            printer.sendData(("\n营业日期：" + operationDate));
                            printer.sendData("\n--------------------------------");
                            printer.sendData("\n结算\t\t笔数\t金额(RMB)");
                            printer.sendData(("\n微信\t\t" + detail.getNumWechat() + "\t\t" + MyConstant.gFormat.format(detail.getPriceWechat())));
                            printer.sendData(("\n支付宝\t\t" + detail.getNumAlipay() + "\t\t" + MyConstant.gFormat.format(detail.getPriceAlipay())));
                            printer.sendData(("\n会员卡\t\t" + detail.getNumMember() + "\t\t" + MyConstant.gFormat.format(detail.getPriceMember())));
                            printer.sendData(("\n现金\t\t" + detail.getNumCash() + "\t\t" + MyConstant.gFormat.format(detail.getPriceCash())));
                            printer.sendData("\n--------------------------------");
                            printer.sendData(("\n打印时间：" + curDate));
							mainActivity.pPrinter.lineFeed(4);
                        } catch (SmartPosException e) {
                            e.printStackTrace();
                        }
						//try {
						//	Thread.sleep(2000);
						//} catch(InterruptedException ignored) {
						//}
                        isPrint = false;
                        Log.d(TAG, "print: false");
                    }
                });
                // 开启打印线程
                mainActivity.gTHREAD_POOL.execute(mainActivity.pPrinter.new WriteThread(0));
                break;
            case R.id.btn_statistics_cout_print_all:
                if (isPrint) {
                    return;
                }
                isPrint = true;
                Log.d(TAG, "print: true");
                getCountDetail();
                mainActivity.pPrinter.setPrintCallback(new BuiltInPrinter.PrintCallback() {
                    @Override
                    public void print(BuiltInPrinter printer) {
                        try {
                            printer.sendData(("店铺：" + storeName));
                            printer.sendData(("\n设备：" + machineName));
                            printer.sendData(("\n营业日期：" + operationDate));
                            for (StatisticsCountDetail statisticsCountDetail : countDetailList) {
                                String mealName = statisticsCountDetail.getMealTimesName();
                                if (mealName == null) continue;
                                printer.sendData(("\n-------------"+mealName+"---------------"));
                                printDishesDetail(printer, statisticsCountDetail);
                            }
                            printer.sendData("\n--------------------------------");
                            printer.sendData(("\n打印时间：" + curDate));
							mainActivity.pPrinter.lineFeed(4);
                        } catch (SmartPosException e) {
                            e.printStackTrace();
                        }
						//try {
						//	Thread.sleep(1000);
						//} catch(InterruptedException ignored) {
						//}
                        isPrint = false;
                        Log.d(TAG, "print: false");
                    }
                });
                // 开启打印线程
                mainActivity.gTHREAD_POOL.execute(mainActivity.pPrinter.new WriteThread(0));
                break;
            default:
                break;
        }
    }

    public void printDishesDetail(BuiltInPrinter printer , StatisticsCountDetail detail) {
        try {
            printer.sendData("\n结算\t\t笔数\t金额(RMB)");
            printer.sendData(("\n微信\t\t" + detail.getNumWechat() + "\t\t" + MyConstant.gFormat.format(detail.getPriceWechat())));
            printer.sendData(("\n支付宝\t\t" + detail.getNumAlipay() + "\t\t" + MyConstant.gFormat.format(detail.getPriceAlipay())));
            printer.sendData(("\n会员卡\t\t" + detail.getNumMember() + "\t\t" + MyConstant.gFormat.format(detail.getPriceMember())));
            printer.sendData(("\n现金\t\t" + detail.getNumCash() + "\t\t" + MyConstant.gFormat.format(detail.getPriceCash())));
        } catch (SmartPosException e) {
            e.printStackTrace();
        }
		//try {
		//	Thread.sleep(100);
		//} catch(InterruptedException ignored) {
		//}
    }

    /**
     * 根据数据修改对应支付方式数据
     * 统计各个支付方式的使用次数
     * @param record
     * @param detail
     * @return
     */
    private StatisticsCountDetail modifyData(TfConsumeCardRecord record, StatisticsCountDetail detail) {
        switch (record.getCCR_PAY_TYPE()) {
            case "0":
                // cash
                detail.setNumCash(detail.getNumCash() + 1);
                detail.setPriceCash(detail.getPriceCash() + record.getCCR_MONEY());
                break;
            case "1":
                // member
                detail.setNumMember(detail.getNumMember() + 1);
                detail.setPriceMember(detail.getPriceMember() + record.getCCR_MONEY());
                break;
            case "2":
                //wechat
                detail.setNumWechat(detail.getNumWechat() + 1);
                detail.setPriceWechat(detail.getPriceWechat() + record.getCCR_MONEY());
                break;
            case "3":
                // alipay
                detail.setNumAlipay(detail.getNumAlipay() + 1);
                detail.setPriceAlipay(detail.getPriceAlipay() + record.getCCR_MONEY());
                break;
        }
        return detail;
    }

    public void getCountDetail() {
        initList();
        List<TfConsumeCardRecord> cardRecordList = getCurdateCardInfo();
        StatisticsCountDetail detail;
        for (TfConsumeCardRecord record : cardRecordList) {
            switch (record.getMT_ID()) {
                case "1":
                    detail = countDetailList.get(0);
                    detail.setMealTimesName("早餐");
                    detail = modifyData(record, detail);
                    break;
                case "2":
                    detail = countDetailList.get(1);
                    detail.setMealTimesName("中餐");
                    detail = modifyData(record, detail);
                    break;
                case "3":
                    detail = countDetailList.get(2);
                    detail.setMealTimesName("晚餐");
                    detail = modifyData(record, detail);
                    break;
                case "4":
                    detail = countDetailList.get(3);
                    detail.setMealTimesName("夜宵");
                    detail = modifyData(record, detail);
                    break;
                case "5":
                    detail = countDetailList.get(4);
                    detail.setMealTimesName("拓展");
                    detail = modifyData(record, detail);
                    break;
            }
        }
    }

    public void queryCommodity() {
        List<TfConsumeCardRecord> cardRecordList = getCurdateCardInfo();
        mealList.clear();
        mealList.clear();
        mealitemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mealitemList.add(new StatisticsMeal());
        }
        if (!cardRecordList.isEmpty()) {
            for (TfConsumeCardRecord record : cardRecordList) {
               try {
                   StatisticsMeal meal = mealitemList.get(Integer.parseInt(record.getMT_ID()) - 1);///crash--------
                   if (meal.getMeal() == null) {
                       switch (record.getMT_ID()) {
                           case "1":
                               meal.setMeal("早餐");
                               break;
                           case "2":
                               meal.setMeal("中餐");
                               break;
                           case "3":
                               meal.setMeal("晚餐");
                               break;
                           case "4":
                               meal.setMeal("夜宵");
                               break;
                           case "5":
                               meal.setMeal("拓展");
                               break;
                       }
                   }
                   calculateMeal(meal, record);
               }catch (Exception e){e.printStackTrace();}
            }
            for (StatisticsMeal meal : mealitemList) {
                if (meal.getMeal() != null) {
                    mealList.add(meal);
                }
            }
        }
        StatisticsCountListAdapter adapter = (StatisticsCountListAdapter) countListView.getAdapter();
        if (adapter == null) {
            adapter = new StatisticsCountListAdapter(mContext, mealList);
            countListView.setAdapter(adapter);
        } else {
            adapter.setData(mealList);
        }
    }

    /**
     * 当前时间段的卡消费数据
     * @return
     */
    public List<TfConsumeCardRecord> getCurdateCardInfo() {
        String start = modifyDate(txStartTime.getText().toString(), START_TIME);
        String stop = modifyDate(txStopTime.getText().toString(), STOP_TIME);
        List<TfConsumeCardRecord> cardRecordList = new ArrayList<TfConsumeCardRecord>();
        QueryBuilder<TfConsumeCardRecord> builder = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(start),
                        TfConsumeCardRecordDao.Properties.CREATETIME.le(stop),
                        TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"));
        int assistor = spSelectAssistant.getSelectedItemPosition();
        if (assistor!= spList.size() - 1) {
            String u_id = getShopAssistant().get(assistor).getU_ID();
            builder.where(TfConsumeCardRecordDao.Properties.U_ID.eq(u_id));
        }
        cardRecordList = builder.build().list();
        return cardRecordList;
    }

    public void calculateMeal(StatisticsMeal meal, TfConsumeCardRecord record) {
        meal.setTotal(meal.getTotal() + record.getCCR_MONEY());
        meal.setOrders(meal.getOrders() + 1);
        meal.setHeadcount(meal.getHeadcount() + 1);
        meal.setAverage(meal.getTotal() / meal.getHeadcount());
        switch (record.getCCR_PAY_TYPE()) {
            case "0":
                meal.setCash(meal.getCash() + record.getCCR_MONEY());
                meal.setCashTime(meal.getCashTime() + 1);
                break;
            case "1":
                meal.setSwipingCard(meal.getCash() + record.getCCR_MONEY());
                meal.setSwipingCardTime(meal.getSwipingCardTime() + 1);
                break;
            default:
                break;
        }
    }
}
