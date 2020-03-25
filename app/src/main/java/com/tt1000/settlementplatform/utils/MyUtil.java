package com.tt1000.settlementplatform.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecord;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecord;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecord;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMealTimesDao;
import com.tt1000.settlementplatform.bean.result_info.OnlineUpdateResultInfo;
import com.tt1000.settlementplatform.bean.result_info.UpdateResultInfo;
import com.tt1000.settlementplatform.feature.network.ApiService;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;

public class MyUtil {

    private static final String[] sSyncTableColumns = new String[]{"SEQNO", "TABLENAME", "PRIMARYKEY", "QUESTTIME", "ISM_STATUS", "INIT_STATUS",
            "CLIENT_CODE", "STORE_CODE", "CREATETIME", "UPDATETIME", "ANOTHER_NAME"};

    public static SQLiteDatabase readDatabase(String dbPath, String dbName) {
        Log.d("assets", "进来了呀");
        File path = new File(dbPath);
        File dbFile = new File(dbPath + dbName);
        SQLiteDatabase sqLiteDatabase = null;
        InputStream input;
        FileOutputStream output;
        try {
            if (!path.exists()) {
                path.mkdirs();
            }

            if (!dbFile.exists()) {
//                dbFile.mkdirs();
                Log.d("assets", "不存在呀");
                // 不存在，则加载assets中的db文件到本地
                AssetManager am = MyApplication.getContext().getAssets();
                input = am.open("local_db.db");
                output = new FileOutputStream(dbFile);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }
            sqLiteDatabase = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqLiteDatabase;
    }
    public static int strLength(String value) {
        int valueLength = 0;
        if (value != null) {
            String chinese = "[\u0391-\uFFE5]";
            /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
            for (int i = 0; i < value.length(); i++) {
                /* 获取一个字符 */
                String temp = value.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    /* 中文字符长度为2 */
                    valueLength += 2;
                } else {
                    /* 其他字符长度为1 */
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }
    /**
     * 返回折扣率
     * DISCOUNT_STATUS: 0 关闭 1开启
     *
     * @param discountType 0 全民折扣 1 会员折扣 2 自定义折扣
     * @return
     */
    public static float getDiscountRate(int discountType) {
        float rate = 1;
        List<TfDiscountRecord> discountRecordList = MyApplication.getInstance().queryBuilder(TfDiscountRecord.class)
                .where(TfDiscountRecordDao.Properties.DISCOUNT_STATUS.eq(1),
                        TfDiscountRecordDao.Properties.DISCOUNT_TYPE.eq(discountType))
                .build()
                .list();
        if (discountRecordList == null || discountRecordList.isEmpty()) {
            return rate;
        }
        TfDiscountRecord discountRecord = discountRecordList.get(0);
        if (discountRecord != null) {
            rate = Float.parseFloat(discountRecord.getDISCOUNT_RATE_C1()) / 100f;
            return rate;
        }
        return rate;
    }
    /**
     * 同步表
     * SEQNO	主键
     * TABLENAME	表名
     * PRIMARYKEY	表主键
     * QUESTTIME	间隔时间
     * ISM_STATUS	同步状态
     * INIT_STATUS	初始状态
     * CLIENT_CODE	客户代码
     * STORE_CODE	店铺代码
     * CREATETIME	创建时间
     * UPDATETIME	更新时间
     * ANOTHER_NAME	表名
     */
    public static void createOrUpdateSyncTable(String syncTableName, List<String> newData) {

        SQLiteDatabase database = readDatabase(MyConstant.DB_PATH, MyConstant.DB_NAME);
        ContentValues values = new ContentValues();

        Cursor cursor = database.query(MyConstant.SYNC_TABLE_RECORD, null, "TABLENAME=?", new String[]{syncTableName}, null, null, null);
        // 创建记录
        if (cursor.getCount() == 0) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss");
            values.put("SEQNO", "123123");
            values.put("TABLENAME", syncTableName);
            values.put("PRIMARYKEY", "123123");
            values.put("QUESTTIME", "123123");
            values.put("ISM_STATUS", "");
            values.put("INIT_STATUS", "");
            values.put("CLIENT_CODE", "");
            values.put("STORE_CODE", "");
            values.put("CREATETIME", format.format(new Date()));
            values.put("ANOTHER_NAME", "123123");
            database.insert(MyConstant.SYNC_TABLE_RECORD, null, values);
            return;
        }
        ContentValues updateValues = new ContentValues();
        // 更新数据
        for (int i = 0; i < newData.size(); i++) {
            if (sSyncTableColumns[i].equals("CREATETIME")) {
                // 更新数据的数据中不包含创建时间
                // 同时也不允许用户设置这个数据
                continue;
            }
            updateValues.put(sSyncTableColumns[i], newData.get(i));
        }
//        database.update(MyConstant.SYNC_TABLE_RECORD, updateValues, "TABLENAME=?", new String[]{syncTableName});
    }

    public static String encryptMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 0x74);
        }
        String s = new String(a);
        return s;
    }
    /**
     * 会员折扣
     *
     * @param mi_type
     * @return
     */
    public static int getDisCountForMember(String mi_type) {
        int disCount = 100;
        if (mi_type != null) {
            List<TfDiscountRecord> discountRecordList = MyApplication.getInstance().queryBuilder(TfDiscountRecord.class)
                    .where(TfDiscountRecordDao.Properties.DISCOUNT_STATUS.eq(1),
                            TfDiscountRecordDao.Properties.DISCOUNT_TYPE.eq(1))
                    .build()
                    .list();
            if (discountRecordList != null && discountRecordList.size() > 0) {
                TfDiscountRecord tfDiscountRecord = discountRecordList.get(0);
                if (tfDiscountRecord != null) {
                    List<MemberTypeRecord> memberTypeRecordList = MyApplication.getInstance().queryBuilder(MemberTypeRecord.class)
                            .where(MemberTypeRecordDao.Properties.SEQNO.eq(mi_type))
                            .build()
                            .list();
                    if (memberTypeRecordList != null && memberTypeRecordList.size() > 0) {
                        MemberTypeRecord memberTypeRecord = memberTypeRecordList.get(0);
                        if (memberTypeRecord != null) {
                            try {
                                disCount = Integer.parseInt(memberTypeRecord.getDISCOUNT_RATE());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return disCount;
    }
    public static void saveOrder(final TfConsumeCardRecord cardRecord,
                          final List<TfConsumeDetailsRecord> detailsRecordList,
                          final TfConsumeOrderRecord orderRecord) {
        if (cardRecord != null) {
            DaoSession session = MyApplication.getInstance();
            session.insert(cardRecord);

            if (detailsRecordList != null) {
                for (TfConsumeDetailsRecord detailsRecord : detailsRecordList) {
                    if (detailsRecord != null) {
                        session.insert(detailsRecord);
                    }
                }
            }
            if (orderRecord != null) {
                session.insert(orderRecord);
            }
        }
    }
    public static String calculateDisCountPrice(String price, int dis_count) {
        float result = Float.parseFloat(price);
        result = result * dis_count / 100f;
        price = MyConstant.gFormat.format(result);
        return price;
    }
    /**
     * 将本地记录的消费信息发送到服务器（更新）
     * 离线模式
     *
     * @throws
     */
    public static synchronized void updateToServerOnline(final TfConsumeCardRecord cardRecord,
                                                  final List<TfConsumeDetailsRecord> detailsRecordList,
                                                  final TfConsumeOrderRecord orderRecord) {
        Gson gson = new Gson();
        OnlineUpdateResultInfo syncInfo = new OnlineUpdateResultInfo();
        List<OnlineUpdateResultInfo.TfConsumeDetailsRecordBean> details = new ArrayList<>();
        OnlineUpdateResultInfo.TfConsumeOrderRecordBean orderBean = null;
        OnlineUpdateResultInfo.TfConsumeDetailsRecordBean detailsBean;
        OnlineUpdateResultInfo.TfConsumeCardRecordBean cardBean = null;

        orderBean = new OnlineUpdateResultInfo.TfConsumeOrderRecordBean();
        orderBean.setCOR_ID(orderRecord.getCOR_ID());
        orderBean.setCOR_MONERY(orderRecord.getCOR_AMOUNT());
        orderBean.setCOR_AMOUNT(orderRecord.getCOR_AMOUNT());
        if (orderRecord.getMACHINE_NO() == null) {
            orderBean.setMACHINE_NO("0");
        } else {
            orderBean.setMACHINE_NO(orderRecord.getMACHINE_NO());
        }
        orderBean.setISM_STATUS(orderRecord.getISM_STATUS());
        orderBean.setSTORE_CODE(orderRecord.getSTORE_CODE());
        orderBean.setCREATETIME(orderRecord.getCREATETIME());
        orderBean.setCOR_TYPE(orderRecord.getCOR_TYPE());
        orderBean.setADDR_ID(String.valueOf(orderRecord.getADDR_ID()));
        orderBean.setCLIENT_CODE(orderRecord.getCLIENT_CODE());
        orderBean.setUPDATETIME(orderRecord.getUPDATETIME());

        // 添加到列表就表示能同步了，修改同步状态
        for (TfConsumeDetailsRecord detailsRecord : detailsRecordList) {
            detailsBean = new OnlineUpdateResultInfo.TfConsumeDetailsRecordBean();
            detailsBean.setCOR_ID(detailsRecord.getCOR_ID());
            detailsBean.setCDR_UNIT_PRICE(detailsRecord.getCDR_UNIT_PRICE());
            detailsBean.setCDR_MONEY(detailsRecord.getCDR_MONEY());
            detailsBean.setCDR_NO(detailsRecord.getCDR_NO());
            detailsBean.setCDR_TYPE(detailsRecord.getCDR_TYPE());
            detailsBean.setISM_STATUS(detailsRecord.getISM_STATUS());
            detailsBean.setSTORE_CODE(detailsRecord.getSTORE_CODE());
            detailsBean.setCREATETIME(detailsRecord.getCREATETIME());
            detailsBean.setCDR_NUMBER(detailsRecord.getCDR_NUMBER());
            detailsBean.setCLIENT_CODE(detailsRecord.getCLIENT_CODE());
            detailsBean.setCDR_ID(detailsRecord.getCDR_ID());
            details.add(detailsBean);
        }
        cardBean = new OnlineUpdateResultInfo.TfConsumeCardRecordBean();
        cardBean.setCOR_ID(cardRecord.getCOR_ID());
        cardBean.setCCR_MONEY(String.valueOf(cardRecord.getCCR_MONEY()));
        if (cardRecord.getMI_ID() == null) {
            cardBean.setMI_ID("0");
        } else {
            cardBean.setMI_ID(cardRecord.getMI_ID());
        }
        cardBean.setMT_ID(cardRecord.getMT_ID());

        if (cardRecord.getMACHINE_NO() == null) {
            cardBean.setMACHINE_NO("0");
        } else {
            cardBean.setMACHINE_NO(cardRecord.getMACHINE_NO());
        }
        cardBean.setCCR_ID(cardRecord.getCCR_ID());
        cardBean.setISM_STATUS(cardRecord.getISM_STATUS());
        cardBean.setSTORE_CODE(cardRecord.getSTORE_CODE());
        cardBean.setCREATETIME(cardRecord.getCREATETIME());
        cardBean.setCLIENT_CODE(cardRecord.getCLIENT_CODE());
        cardBean.setCCR_PAY_TYPE(cardRecord.getCCR_PAY_TYPE());
        cardBean.setCCR_STATUS(cardRecord.getCCR_STATUS());
        cardBean.setIC_ID(cardRecord.getIC_ID());
        cardBean.setPAY_REMARK("");


        if (cardRecord.getMI_ID() != null && cardRecord.getMI_ID().length() > 0) {
            cardBean.setCCR_ORIGINALAMOUNT(cardRecord.getCCR_ORIGINALAMOUNT());
        }
        cardBean.setU_ID(cardRecord.getU_ID());
        String uploadTime = MyUtil.dateConversion(System.currentTimeMillis());
        cardBean.setCCR_UPLOAD_TIME(uploadTime);
        syncInfo.setTf_consume_order_record(orderBean);
        syncInfo.setTf_consume_details_record(details);
        syncInfo.setTf_consume_card_record(cardBean);
        final String data = gson.toJson(syncInfo, OnlineUpdateResultInfo.class);
        Log.d("frost", "updateToServerOnline--data: " + data);

        LocalRetrofit.createService().postUpdateOnline(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UpdateResultInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "updateToServerOnline:777 onError");
                        saveOrder(cardRecord, detailsRecordList, orderRecord);
                        Log.d(TAG, "在线状态更新 onError: " + e.getMessage());
                        //cardRecord.setCCR_UPLOAD_STATUS("2");
                        //pDaoSession.update(cardRecord);
                    }

                    @Override
                    public void onNext(UpdateResultInfo syncResultInfo) {
                        Log.e(TAG, "updateToServerOnline:777 onNext");
                        if (syncResultInfo != null && syncResultInfo.isResult()) {
                            cardRecord.setISM_STATUS("1");
                        }
                        saveOrder(cardRecord, detailsRecordList, orderRecord);

                    }
                });

    }
    /**
     * 获得全员折扣
     *
     * @return
     */
    public static int getDisCountForAll() {
        int disCount = 100;
        List<TfDiscountRecord> discountRecordList = MyApplication.getInstance().queryBuilder(TfDiscountRecord.class)
                .where(TfDiscountRecordDao.Properties.DISCOUNT_STATUS.eq(1),
                        TfDiscountRecordDao.Properties.DISCOUNT_TYPE.eq(0))
                .build()
                .list();
        if (discountRecordList != null && discountRecordList.size() > 0) {
            TfDiscountRecord tfDiscountRecord = discountRecordList.get(0);
            if (tfDiscountRecord != null) {
                try {
                    disCount = Integer.parseInt(tfDiscountRecord.getDISCOUNT_RATE_C1());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(TAG, "getDisCountForAll: " + disCount);
        return disCount;
    }
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static String getPrimaryValue() {
        try {
            JSONObject random = new JSONObject();
            random.put("length", 1000);
            JSONObject randomJson = new JSONObject();
            randomJson.put("length", 100);
            return System.currentTimeMillis()
                    + String.valueOf((int) (Math.random() * randomJson.getInt("length")
                    + randomJson.getInt("length")))
                    + String.valueOf((int) (Math.random() * random.getInt("length")
                    + random.getInt("length")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurMealTimes() {
        String curDate = MyUtil.obtainCurrentSysDate(1);
        TfMealTimes mealTimes = MyApplication.getInstance().queryBuilder(TfMealTimes.class)
                .where(TfMealTimesDao.Properties.STARTTIME.le(curDate),
                        TfMealTimesDao.Properties.ENDTIME.ge(curDate))
                .build()
                .unique();
        if (mealTimes != null) {
            return mealTimes.getMT_ID();
        }
        return "";
    }

    public static String dateConversion(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(millis));
    }

    /**
     * 隐藏烦人的输入法
     *
     * @param context
     * @param editText
     */
    public static void hideInputMethodManager(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * @param type 0:yyyy-MM-dd 1: HH:mm:ss 2 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String obtainCurrentSysDate(int type) {
        SimpleDateFormat format = new SimpleDateFormat();
        switch (type) {
            case 0:
                format.applyPattern("yyyy-MM-dd");
                break;
            case 1:
                format.applyPattern("HH:mm:ss");
                break;
            case 2:
                format.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
        }
        Date date = new Date();
        return format.format(date);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    //date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static boolean networkState = false;

    /**
     * 网络连通性
     *
     * @param context
     * @return
     */

    public static boolean obtainNetworkStatus(Context context) {
        if (null == context) {
            networkState = false;
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null == networkInfo) {
            networkState = false;
            return false;
        } else {
            if (networkInfo.isAvailable()) {
                networkState = true;
                return true;
            } else {
                if (MainActivity.gUiHandler != null) {
//                    MainActivity.gUiHandler.obtainMessage(MainActivity.WIFI_ERROR).sendToTarget();
                }
                networkState = false;
                return false;
            }
        }
    }


//    public static boolean obtainNetworkStatus(Context context) {
//        try {
//            LocalRetrofit.createService().obtainHeart()
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<Object>() {
//                        @Override
//                        public void onCompleted() {
//                            Log.d("network", "onCompleted: ");
//                            networkState = true;
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.d("network", "onError: " + e.getMessage());
//                            networkState = false;
//                        }
//
//                        @Override
//                        public void onNext(Object o) {
//                            Log.d("network", "onNext: ");
//                            networkState = true;
//                        }
//                    });
//            return networkState;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (MainActivity.gUiHandler != null) {
//                MainActivity.gUiHandler.obtainMessage(MainActivity.WIFI_ERROR).sendToTarget();
//            }
//            return false;
//        }
//
//    }

    public static String strToHexString(String toConvert) {
        String result = "";
        for (int i = 0; i < toConvert.length(); i++) {
            int c = toConvert.charAt(i);
            result += Integer.toHexString(c);
        }
        return result;
    }

    public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    public static String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public static String getMacAddress() {
        StringBuilder result = new StringBuilder(callCmd());
        String Mac = "";

        //对该行数据进行解析
        //例如：eth0      Link encap:Ethernet  HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.toString().contains("HWaddr")) {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
//            Log.i("test", "Mac:" + Mac + " Mac.length: " + Mac.length());

            if (Mac.length() > 1) {
                Mac = Mac.replaceAll(" ", "");
                result = new StringBuilder();
                String[] tmp = Mac.split(":");
                for (String aTmp : tmp) {
                    result.append(aTmp);
                }
                return result.toString();
            }
        }
        return result.toString();
    }

    public static String callCmd() {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec("busybox ifconfig");
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && !line.contains("HWaddr")) {
                //result += line;
            }
            is.close();
            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断服务器地址是否可用
     *
     * @return
     */
    public static boolean isConfigServer() {
        try {
            LocalRetrofit.getInstance().create(ApiService.class);
            return true;
        } catch (Exception e) {
            if (MainActivity.gUiHandler != null) {
                MainActivity.gUiHandler.obtainMessage(MainActivity.WIFI_ERROR).sendToTarget();
            }
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEmpty(String temp) {
        if (null == temp || temp.equals("") || temp.length() <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String temp) {
        return !isEmpty(temp);
    }


//---------------------------------for WeichaiOperationFragment------------------

    /**
     * 根据icid计算得出会员卡号
     *
     * @param smartCardId
     * @return
     */
    public static long computeMemberId(String smartCardId) {
        byte[] arr_test = MyUtil.hex2byte(smartCardId);
        long convertId = 0;
        if (arr_test.length == 4) {
            long shu_0 = arr_test[0] & 0xFF;
            long shu_1 = arr_test[1] & 0xFF;
            long shu_2 = arr_test[2] & 0xFF;
            long shu_3 = arr_test[3] & 0xFF;
            long i_test = shu_0 + shu_1 * 256L + shu_2 * 256L * 256L + shu_3 * 256L * 256L * 256L;
            convertId = i_test;
        }
        return convertId;
    }


    public static final String TAG = "frost_test";

    //移除文件，获取文件时间与当前时间对比
    public static void removeFileByTime(String dirPath) {
        //获取目录下所有文件
        List<File> allFile = getDirAllFile(new File(dirPath));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取当前时间
        Date end = new Date(System.currentTimeMillis());
        try {
            end = dateFormat.parse(dateFormat.format(new Date(System.currentTimeMillis())));
        } catch (Exception e) {
            Log.d(TAG, "dataformat exeption e " + e.toString());
        }
        Log.d(TAG, "getNeedRemoveFile  dirPath = " + dirPath);
        for (File file : allFile) {//ComDef
            try {
                //文件时间减去当前时间
                Date start = dateFormat.parse(dateFormat.format(new Date(file.lastModified())));
                long diff = end.getTime() - start.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);
                if (6 <= days) {
                    deleteFile(file);
                }

            } catch (Exception e) {
                Log.d(TAG, "dataformat exeption e " + e.toString());
            }
        }
    }

    //删除文件夹及文件夹下所有文件
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }

    //获取指定目录下一级文件
    public static List<File> getDirAllFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null)
            return fileList;
        for (File f : fileArray) {
            fileList.add(f);
        }
        fileSortByTime(fileList);
        return fileList;
    }

    //对文件进行时间排序
    public static void fileSortByTime(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File p1, File p2) {
                if (p1.lastModified() < p2.lastModified()) {
                    return -1;
                }
                return 1;
            }
        });
    }

    public static void appendFile(String json) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        long timestamp = System.currentTimeMillis();
        String time = formatter.format(new Date());
        String fileName = time + ".text";
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            String path = "/sdcard/JsonLog/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = null;
            try {
                BufferedWriter out = null;
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path + fileName, true)));
                out.write(json + "\n");
                out.close();
            } catch (FileNotFoundException e) {
                Log.e("frost", "FileNotFoundException" + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("frost", "IOException" + e.getMessage());
                e.printStackTrace();
            }


        }
    }

    /**
     * @param params 请求参数
     * @param detail 详细内容
     * @param title  标题
     */
    public static void uploadingError(String params, String detail, String title, Context context) {
        Log.e("frost_upload", "uploadingError");
        if (obtainNetworkStatus(context)) {
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");

            String url = ip + ":" + port + "/k-occ/api/kbs/device/uploadError";
            long timeMillis = System.currentTimeMillis();
            String machine_no = gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
            String client_code = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
            String store_code = gSharedPre.getString(MyConstant.STORE_CODE, "");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("storeCode", store_code);
            jsonObject.addProperty("clientCode", client_code);
            jsonObject.addProperty("machineNo", machine_no);
            jsonObject.addProperty("params", params);
            jsonObject.addProperty("detail", detail);
            jsonObject.addProperty("title", title);
            jsonObject.addProperty("excType", 0);
            jsonObject.addProperty("timestamp", timeMillis);

            Log.e("frost_upload", jsonObject.toString());

            RequestBody requestBody = new FormBody.Builder().add("data", jsonObject.toString()).build();
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("frost_upload", "onFailure:" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Gson gson = new Gson();
                    String body = response.body().string();
                    Log.e("frost_upload", "onResponse" + body);
//                    UploadErrorBean uploadErrorBean = gson.fromJson(body, UploadErrorBean.class);
//                    if (!"0000".equals(uploadErrorBean.getCode()) || !uploadErrorBean.isSuccess()) {
//
//                    }
                }
            });
        } else {
            return;
        }
    }

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }

    /**
     * 获取IP
     *
     * @param context
     * @return
     */
    public static String getIP(Context context) {
        String ip = "0.0.0.0";
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        int type = info.getType();
        if (type == ConnectivityManager.TYPE_ETHERNET) {
            ip = getEtherNetIP();
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            ip = getWifiIP(context);
        }
        return ip;
    }

    /**
     * 获取有线地址
     *
     * @return
     */
    public static String getEtherNetIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "0.0.0.0";
    }

    /**
     * 获取wifiIP地址
     *
     * @param context
     * @return
     */
    public static String getWifiIP(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                .getSystemService(android.content.Context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifi.getConnectionInfo();
        int intaddr = wifiinfo.getIpAddress();
        byte[] byteaddr = new byte[]{(byte) (intaddr & 0xff),
                (byte) (intaddr >> 8 & 0xff), (byte) (intaddr >> 16 & 0xff),
                (byte) (intaddr >> 24 & 0xff)};
        InetAddress addr = null;
        try {
            addr = InetAddress.getByAddress(byteaddr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String mobileIp = addr.getHostAddress();
        return mobileIp;
    }

    public static String getLocalIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {    //3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    Log.e(TAG, "获取3G/4G网络IP失败");
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {     // wifi
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = int2Sip(wifiInfo.getIpAddress());
                return ipAddress;
            } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {    //有线
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                Network network = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    network = mConnectivityManager.getActiveNetwork();
                    LinkProperties linkProperties = mConnectivityManager.getLinkProperties(network);
                    for (LinkAddress linkAddress : linkProperties.getLinkAddresses()) {
                        InetAddress address = linkAddress.getAddress();
                        if (address instanceof Inet4Address) {
                            return address.getHostAddress();
                        }
                    }
                }
                return "0.0.0.0";
            }
        } else {
            return "0.0.0.0";
        }
        return null;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ip
     * @return
     */
    public static String int2Sip(int ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip & 0xFF).append(".");
        sb.append((ip >> 8) & 0xFF).append(".");
        sb.append((ip >> 16) & 0xFF).append(".");
        sb.append((ip >> 24) & 0xFF);
        return sb.toString();
    }
}
