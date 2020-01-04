package com.tt1000.settlementplatform.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.bean.TaskBean;
import com.tt1000.settlementplatform.bean.TotalNum;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityRecordDao;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecord;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecordDao;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecord;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecordDao;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.StoreConfigDao;
import com.tt1000.settlementplatform.bean.member.SyncResultInfo;
import com.tt1000.settlementplatform.bean.member.SyncTableRecord;
import com.tt1000.settlementplatform.bean.member.SyncTableRecordDao;
import com.tt1000.settlementplatform.bean.member.TfCardInfo;
import com.tt1000.settlementplatform.bean.member.TfCardInfoDao;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecord;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMealTimesDao;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecord;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.TfMemberInfoDao;
import com.tt1000.settlementplatform.bean.member.TfPrintTask;
import com.tt1000.settlementplatform.bean.member.TfPrintTaskDao;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.member.TfUserInfoDao;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.schedulers.Schedulers;

public class MainService extends Service {
    private Context mContext;
    public ThreadPoolExecutor mTHREAD_POOL = new ThreadPoolExecutor(6,
            6,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    public ScheduledExecutorService mExecutor = new ScheduledThreadPoolExecutor(4);
    public List<TaskBean> taskList;
    public DaoSession session;
    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        session = MyApplication.getInstance();
        taskList = new ArrayList<>();
        mContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        regularlyUpdateTable();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void regularlyUpdateTable() {
        getTaskType();
        taskTable();
    }

    /**
     * 判断本地数据是否和服务器数据数量相等
     */
    public void verifyDataNum(final int type) {
        String updateTime = "1000-01-0100:00:00";
        String clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
        String storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("updatetime", updateTime);
        map.put("client_code", clienCode);
        map.put("store_code", storeCode);
        map.put("api", "1");
        map.put("pageNo", "0");


        Object obj = null;
        String primaryKey = "";
        String tableName = "";
        switch (type) {
            case 0:
                primaryKey = "CI_ID";
                tableName = "commodity_record";
                obj = new CommodityRecord();
                break;
            case 1:
                primaryKey = "SQENO";
                tableName = "commodity_type_record";
                obj = new CommodityTypeRecord();
                break;
            case 2:
                primaryKey = "SQENO";
                tableName = "member_type_record";
                obj = new MemberTypeRecord();
                break;
            case 3:
                primaryKey = "SQENO";
                tableName = "store_config";
                obj = new StoreConfig();
                break;
            case 4:
                primaryKey = "IC_ID";
                tableName = "tf_cardinfo";
                obj = new TfCardInfo();
                break;
            case 5:
                primaryKey = "SQENO";
                tableName = "tf_discount_record";
                obj = new TfDiscountRecord();
                break;
            case 6:
                primaryKey = "MT_ID";
                tableName = "tf_mealtimes";
                obj = new TfMealTimes();
                break;
            case 7:
                primaryKey = "ACCOUNT_ID";
                tableName = "tf_member_account_record";
                obj = new TfMemberAccountRecord();
                break;
            case 8:
                primaryKey = "MI_ID";
                tableName = "tf_memberinfo";
                obj = new TfMemberInfo();
                break;
            case 9:
                primaryKey = "SQENO";
                tableName = "tf_print_task";
                obj = new TfPrintTask();
                break;
            case 10:
                primaryKey = "U_ID";
                tableName = "tf_userinfo";
                obj = new TfUserInfo();
                break;
//            case 11:
//                primaryKey = "SQENO";
//                tableName = "sync_table";
//                obj = SyncTableRecord.class;
//                break;
        }

        if (obj == null || primaryKey.isEmpty() || tableName.isEmpty()) {
            return;
        }
        map.put("tableName", tableName);
        map.put("primaryKey", primaryKey);


        final List<?> dbList = session.queryBuilder(obj.getClass())
                .build()
                .list();
        final SyncTableRecord record = session.queryBuilder(SyncTableRecord.class)
                .where(SyncTableRecordDao.Properties.TABLENAME.eq(tableName))
                .build().unique();

        final String finalTableName = tableName;
        LocalRetrofit.createService()
                .getTableTotalNum(map)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TotalNum>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "123 onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(TotalNum totalNum) {
                        if (totalNum !=null && totalNum.getData() >= 0) {
                            if (dbList.size() != totalNum.getData()) {
                                if (record != null) {
                                    record.setUPDATETIME("0");
                                    session.update(record);
                                }
                            }
                        }
                    }
                });
    }

    public void getTaskType() {
        DaoSession session = MyApplication.getInstance();
        List<SyncTableRecord> tableRecordList = session.queryBuilder(SyncTableRecord.class)
                .build()
                .list();
        taskList.clear();
        if (tableRecordList != null && tableRecordList.size() > 0) {
            if (tableRecordList != null) {
                for (SyncTableRecord tableRecord : tableRecordList) {
                    int type = -1;
                    // region 根据名称判断要更新的表
                    switch (tableRecord.getTABLENAME()) {
                        case "tf_chipinfo":
                            break;
                        case "tf_mealtimes":
                            type = 6;
                            break;
                        case "tf_member_subsidy_config":
                            break;
                        case "tf_member_account_record":
                            type = 7;
                            break;
                        case "commodity_record":
                            type = 0;
                            break;
                        case "tf_userinfo":
                            type = 10;
                            break;
                        case "commodity_type_record":
                            type = 1;
                            break;
                        case "tf_mealtimes_chipcategory_relation":
                            break;
                        case "member_type_record":
                            type = 2;
                            break;
                        case "commodity_img_record":
                            break;
                        case "tf_cardinfo":
                            type = 4;
                            break;
                        case "store_config":
                            type = 3;
                            break;
                        case "tf_chip_category":
                            break;
                        case "tf_memberinfo":
                            type = 8;
                            break;
                        case "tf_store_discount_record":
                            break;
                        case "tf_print_task":
                            type = 9;
                            break;
                        case "tf_discount_record":
                            type = 5;
                            break;
                    }
                    //endregion
                    if (type != -1) {
                        TaskBean bean = new TaskBean();
                        bean.setTableName(tableRecord.getTABLENAME());
                        bean.setMs(tableRecord.getQUESTTIME());
                        try{
                            bean.setUpdatetime(Long.parseLong(tableRecord.getUPDATETIME()));

                            //Log.e("look","tableRecord.getTABLENAME():"+tableRecord.getTABLENAME()+" tableRecord.getUPDATETIME()："+tableRecord.getUPDATETIME()+" bean.getUpdatetime():"+bean.getUpdatetime());
                        }catch (Exception e){}
                        bean.setType(type);
                        taskList.add(bean);
                    }
                }
            }
        }
    }

    private  int task_page_no = 0;
    private  int task_table_index = 0;
    // 是否在同步
    public boolean isSync = false;
    private SyncTableRecord syncTableRecord = null;

    public void syncTable(final int type, String tableName,long l_updatetime,int page_no) {
//        Log.d(TAG, "syncTable: " + type);
        if (!MyUtil.obtainNetworkStatus(mContext) &&!MyUtil.isConfigServer()) {
            return;
        }
        final DaoSession session = MyApplication.getInstance();
        String updateTime = "";
        String t_record_name ="";;


        if (!TextUtils.isEmpty(tableName)) {
            List<SyncTableRecord> syncTableRecordList = session.queryBuilder(SyncTableRecord.class)
                    .where(SyncTableRecordDao.Properties.TABLENAME.eq(tableName))
                    .build()
                    .list();
            if (syncTableRecordList != null && syncTableRecordList.size() > 0) {
                syncTableRecord = syncTableRecordList.get(0);
                /*
                updateTime = syncTableRecord.getUPDATETIME();
                t_record_name = syncTableRecord.getTABLENAME();
                try {
                    updateTime = MyUtil.dateConversion(Long.parseLong(updateTime));
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                    updateTime = syncTableRecord.getUPDATETIME();
                }
                */
            }
        }

        try {
            updateTime = MyUtil.dateConversion(l_updatetime);
        }catch (NumberFormatException e) {

        }

        Log.d("look","table_type:"+type+" t_record_name:"+t_record_name+" l_updatetime"+l_updatetime+" updateTime:"+updateTime);
        String clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
        String storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("updatetime", updateTime);
        map.put("client_code", clienCode);
        map.put("store_code", storeCode);
        map.put("api", "1");
        map.put("pageNo", ""+page_no);

        switch (type) {
            case 0:
                LocalRetrofit.createService().syncCommodityRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<?>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(SyncResultInfo<?> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time=0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    CommodityRecord record = (CommodityRecord) objectSyncResultInfo.getData().get(i);

                                    if (max_time < record.getUPDATETIME()) {
                                        max_time = record.getUPDATETIME();
                                    }

                                    List<CommodityRecord> typeRecordList = session.queryBuilder(CommodityRecord.class)
                                            .where(CommodityRecordDao.Properties.CI_ID.eq(record.getCI_ID())).build().list();

                                    CommodityRecord typeRecord;
                                    if (typeRecordList.isEmpty()) {
                                        typeRecord = null;
                                    } else {
                                        typeRecord = typeRecordList.get(0);
                                    }
                                    if (typeRecord != null) {
                                        // 已存在这条，则更新数据
                                        session.getCommodityRecordDao().update(record);
                                        continue;
                                    } else {
                                        session.insert(record);
                                    }

                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 1:
                LocalRetrofit.createService().syncCommodityTypeRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<CommodityTypeRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<CommodityTypeRecord> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }

                                long max_time = 0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    CommodityTypeRecord commodityTypeRecord = (CommodityTypeRecord) objectSyncResultInfo.getData().get(i);

                                    if (max_time < commodityTypeRecord.getUPDATETIME()) {
                                        max_time = commodityTypeRecord.getUPDATETIME();
                                    }

                                    List<CommodityTypeRecord> recordList = session.queryBuilder(CommodityTypeRecord.class)
                                            .where(CommodityTypeRecordDao.Properties.SEQNO.eq(commodityTypeRecord.getSEQNO())).build().list();
                                    CommodityTypeRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(commodityTypeRecord);
                                        continue;
                                    }
                                    session.insert(commodityTypeRecord);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 2:
                LocalRetrofit.createService().syncMemberTypeRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<MemberTypeRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<MemberTypeRecord> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    MemberTypeRecord memberTypeRecord = (MemberTypeRecord) objectSyncResultInfo.getData().get(i);

                                    if (max_time < memberTypeRecord.getUPDATETIME()) {
                                        max_time = memberTypeRecord.getUPDATETIME();
                                    }

                                    List<MemberTypeRecord> recordList = session.queryBuilder(MemberTypeRecord.class)
                                            .where(MemberTypeRecordDao.Properties.SEQNO.eq(memberTypeRecord.getSEQNO())).build().list();
                                    MemberTypeRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(memberTypeRecord);
                                        continue;
                                    }
                                    session.insert(memberTypeRecord);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 3:
                LocalRetrofit.createService().syncStoreConfigData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<StoreConfig>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<StoreConfig> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    StoreConfig storeConfig = (StoreConfig) objectSyncResultInfo.getData().get(i);

                                    if (max_time < storeConfig.getUPDATETIME()) {
                                        max_time = storeConfig.getUPDATETIME();
                                    }

                                    if (storeConfig.getSEQNO() == null) {
                                        continue;
                                    }
                                    String seqNo = storeConfig.getSEQNO();
                                    List<StoreConfig> recordList = session.queryBuilder(StoreConfig.class)
                                            .where(StoreConfigDao.Properties.SEQNO.eq(seqNo)).build().list();
                                    StoreConfig record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(storeConfig);
                                        continue;
                                    }
                                    session.insert(storeConfig);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 4:
                LocalRetrofit.createService().syncTfCardInfoData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfCardInfo>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }

                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(SyncResultInfo<TfCardInfo> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }

                                long max_time = 0;
                                long time = 0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {

                                    TfCardInfo tfCardInfo = (TfCardInfo) objectSyncResultInfo.getData().get(i);
                                    time = tfCardInfo.getUPDATETIME();
                                    //Log.d("look","table_type:"+type+" time:"+MyUtil.dateConversion(time));
                                    if (max_time < tfCardInfo.getUPDATETIME()) {
                                        max_time = tfCardInfo.getUPDATETIME();
                                    }

                                    /*
                                    if (i == objectSyncResultInfo.getData().size() - 1) {

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }

                                    }
                                    */
                                    List<TfCardInfo> cardInfoList = session.queryBuilder(TfCardInfo.class)
                                            .where(TfCardInfoDao.Properties.IC_ID.eq(tfCardInfo.getIC_ID())).build().list();
                                    TfCardInfo cardInfo;
                                    if (cardInfoList.isEmpty()) {
                                        cardInfo = null;
                                    } else {
                                        cardInfo = cardInfoList.get(0);
                                    }
                                    if (cardInfo != null) {
                                        session.update(tfCardInfo);
                                        continue;
                                    }
                                    session.insert(tfCardInfo);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" time:"+MyUtil.dateConversion(time)+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 5:
                LocalRetrofit.createService().syncTfDiscountRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfDiscountRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfDiscountRecord> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }

                                long  max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfDiscountRecord discountRecord = (TfDiscountRecord) objectSyncResultInfo.getData().get(i);

                                    if (max_time < discountRecord.getUPDATETIME()) {
                                        max_time = discountRecord.getUPDATETIME();
                                    }

                                    List<TfDiscountRecord> recordList = session.queryBuilder(TfDiscountRecord.class)
                                            .where(TfDiscountRecordDao.Properties.SEQNO.eq(discountRecord.getSEQNO())).build().list();
                                    TfDiscountRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(discountRecord);
                                        continue;
                                    }
                                    session.insert(discountRecord);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 6:
                LocalRetrofit.createService().syncTfMealTimesData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfMealTimes>>() {
                            @Override
                            public void onCompleted() {
//                                Log.d(TAG, "onCompleted: ");
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {

//                                Log.d(TAG, "onError: " + e.getMessage());
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfMealTimes> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfMealTimes mealTimes = (TfMealTimes) objectSyncResultInfo.getData().get(i);

                                    if (max_time < mealTimes.getUPDATETIME()) {
                                        max_time = mealTimes.getUPDATETIME();
                                    }


                                    List<TfMealTimes> recordList = session.queryBuilder(TfMealTimes.class)
                                            .where(TfMealTimesDao.Properties.MT_ID.eq(mealTimes.getMT_ID())).build().list();
                                    TfMealTimes record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record != null) {
                                        session.update(mealTimes);
                                        continue;
                                    }
                                    session.insert(mealTimes);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 7:
                LocalRetrofit.createService().syncTfMemberAccountRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfMemberAccountRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {
//                                Log.d(TAG, "onError: " + e.getMessage());

                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfMemberAccountRecord> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfMemberAccountRecord memberAccountRecord = (TfMemberAccountRecord) objectSyncResultInfo.getData().get(i);

                                    if (max_time < memberAccountRecord.getUPDATETIME()) {
                                        max_time = memberAccountRecord.getUPDATETIME();
                                    }


                                    List<TfMemberAccountRecord> recordList = session.queryBuilder(TfMemberAccountRecord.class)
                                            .where(TfMemberAccountRecordDao.Properties.ACCOUNT_ID.eq(memberAccountRecord.getACCOUNT_ID())).build().list();
                                    TfMemberAccountRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(memberAccountRecord);
                                        continue;
                                    }
                                    session.insert(memberAccountRecord);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 8:
                LocalRetrofit.createService().syncTfMemberInfoData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfMemberInfo>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfMemberInfo> objectSyncResultInfo) {
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time =0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfMemberInfo memberInfo = (TfMemberInfo)objectSyncResultInfo.getData().get(i);

                                    if (max_time < memberInfo.getUPDATETIME()) {
                                        max_time = memberInfo.getUPDATETIME();
                                    }

                                    List<TfMemberInfo> recordList = session.queryBuilder(TfMemberInfo.class)
                                            .where(TfMemberInfoDao.Properties.MI_ID.eq(memberInfo.getMI_ID())).build().list();
                                    TfMemberInfo record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(memberInfo);
                                        continue;
                                    }
                                    session.insert(memberInfo);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 9:
                LocalRetrofit.createService().syncTfPrintTaskData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfPrintTask>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfPrintTask> objectSyncResultInfo) {

                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time = 0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfPrintTask printTask = (TfPrintTask)objectSyncResultInfo.getData().get(i);

                                    if (max_time < printTask.getUPDATETIME()) {
                                        max_time = printTask.getUPDATETIME();
                                    }

                                    List<TfPrintTask> recordList = session.queryBuilder(TfPrintTask.class)
                                            .where(TfPrintTaskDao.Properties.SEQNO.eq(printTask.getSEQNO())).build().list();
                                    TfPrintTask record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(printTask);
                                        continue;
                                    }
                                    session.insert(printTask);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+" objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 10:
                LocalRetrofit.createService().syncTfUserInfoData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfUserInfo>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
//                                syncNum +=1;
                            }
                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<TfUserInfo> objectSyncResultInfo) {

                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);

                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                }else{
                                    task_page_no++;
                                }
                                long max_time = 0;
                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    TfUserInfo userInfo = (TfUserInfo) objectSyncResultInfo.getData().get(i);

                                    if (max_time < userInfo.getUPDATETIME()) {
                                        max_time = userInfo.getUPDATETIME();
                                    }

                                    List<TfUserInfo> recordList = session.queryBuilder(TfUserInfo.class)
                                            .where(TfUserInfoDao.Properties.U_ID.eq(userInfo.getU_ID())).build().list();
                                    TfUserInfo record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(userInfo);
                                        continue;
                                    }
                                    session.insert(userInfo);
                                }

                                if (syncTableRecord != null) {
                                    syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                    syncTableRecord.setISM_STATUS("1");
                                    session.update(syncTableRecord);
                                }
                                //Log.d("look","table_type:"+type+" tname:"+syncTableRecord.getTABLENAME()+"  objectSyncResultInfo.getData().size():"+objectSyncResultInfo.getData().size()+" max_time"+MyUtil.dateConversion(max_time));
                            }
                        });
                break;
            case 11:
                LocalRetrofit.createService().syncTableRecord(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<SyncTableRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
                            }
                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
//                                syncNum +=1;

                                task_page_no = 0;
                                task_table_index++;
                            }
                            @Override
                            public void onNext(SyncResultInfo<SyncTableRecord> objectSyncResultInfo) {

                                task_page_no = 0;
                                task_table_index++;

                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    SyncTableRecord tableRecord = (SyncTableRecord) objectSyncResultInfo.getData().get(i);
                                    long time = 0;
                                    if (time < Long.parseLong(tableRecord.getUPDATETIME())) {
                                        time = Long.parseLong(tableRecord.getUPDATETIME());
                                    }
                                    if (i == objectSyncResultInfo.getData().size() - 1) {
                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                    List<SyncTableRecord> recordList = session.queryBuilder(SyncTableRecord.class)
                                            .where(SyncTableRecordDao.Properties.TABLENAME.eq(tableRecord.getTABLENAME())).build().list();
                                    SyncTableRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record!=null) {
                                        // 已存在这条，则更新数据
                                        session.update(tableRecord);
                                        continue;
                                    }
                                    session.insert(tableRecord);
                                }
//                                syncData();
                            }
                        });
                break;
        }


    }

    public void taskTable() {
        mExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isSync) {
                        Log.e("look","task_table_index:"+task_table_index+" task_page_no:"+task_page_no);

                        if(task_table_index>=taskList.size())
                        {
                            getTaskType();
                            task_table_index = 0;
                        }
                        TaskBean bean = taskList.get(task_table_index);

                        //taskList.remove(0);
                        //if (taskList.size() == 0) {
                        //    getTaskType();
                        //}
                        if(bean!=null) {
                            isSync = true;
//                            syncTable(bean.getType(), bean.getTableName(),bean.getUpdatetime(),task_page_no);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0,1, TimeUnit.SECONDS);
    }
}
