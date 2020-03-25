package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.paydevice.smartpos.sdk.SmartPosException;
import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.GeneralTypeAdapter;
import com.tt1000.settlementplatform.adapter.OnAdapterItemClickListener;
import com.tt1000.settlementplatform.adapter.OperationDishRecyclerAdapter;
import com.tt1000.settlementplatform.adapter.OperationMenuListAdapter;
import com.tt1000.settlementplatform.adapter.WeiCaiOperationWaterAdapter;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityRecordDao;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecord;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecordDao;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.TfCardInfo;
import com.tt1000.settlementplatform.bean.member.TfCardInfoDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecord;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMealTimesDao;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecord;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.TfMemberInfoDao;
import com.tt1000.settlementplatform.bean.operation.OperationMenu;
import com.tt1000.settlementplatform.bean.operation.WeiCaiWaterInfo;
import com.tt1000.settlementplatform.bean.result_info.QueryCardResultInfo;
import com.tt1000.settlementplatform.bean.result_info.ResultBaseBean;
import com.tt1000.settlementplatform.feature.BuiltInPrinter;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.utils.SSLSocketClient;

import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;
import static com.tt1000.settlementplatform.utils.MyUtil.getPrimaryValue;
import static com.tt1000.settlementplatform.utils.MyUtil.uploadingError;

public class GeneralFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recycler_general;
    private LinearLayout general_ll;
    private LinearLayout general_type_ll;
    private EditText et_put;
    private List<CommodityRecord> dishesList;
    private ListView lv_general;
    private List<OperationMenu> menuList;
    private List<CommodityTypeRecord> typeList;
    private OperationMenuListAdapter menuListAdapter;
    private View inclueKeyBoard;
    private Button handover_general;
    private MediaPlayer mPlayer;
    private TextView general_status;
    private TextView general_totalNum;
    private TextView general_totalPrice;
    private ImageButton general_refresh, general_print, general_cash;
    private TextView general_name, general_vip_no, general_balance;
    private TextView general_num_people, general_total_people;
    private ListView general_list_water;
    private RecyclerView general_type;
    private String mCurMealType = "";
    // 是否已经打印
    private boolean isPrint = false;
    private boolean isFix;
    private String max_num;
    // 人数区
    private int mTotalPersonCount;
    private int mCurMealPersonCount;
    public static final int SETTLEMENT = 777;
    public static final int PAYMENTING = 1111;
    public static final int UPDATE_PERSON_NUMBER = 888;
    public static final int COUNT_PERSON_NUMBER = 999;
    public static final int PAYMENTFAILED = 2222;
    public static final int JUDGE_SETUP_SCAN = 222;
    public static final int VISIBILITY_PROGRESS = 520;
    public static final int GONE_PROGRESS = 521;
    public static final int CARD_STATUS_CANCELEDS = 369;
    public static final int CARD_STATUS_LOSTS = 258;
    public static final int CARD_ILLEGALITY = 147;
    public static final int REFRESH_CODE = 419;
    public static final int WEIXIN_CODE = 659;
    private static final int PAY_CASH = 0;
    private static final int PAY_MEMBER_CARD = 1;
    private static final int PAY_WECHAT = 2;
    private static final int PAY_ALIPAY = 3;
    private static final int PAY_BANK = 4;
    //清除扫描的支付宝/微信帐号
    public static final int CLEAR_SCANNER_NUM = 0x111;
    // 支付方式
    private int payWay;
    private MifareClassic mifareClassic;
    private byte[] key = {(byte) 0xA0, (byte) 0xB7, (byte) 0xA5, (byte) 0xC5, (byte) 0x80, (byte) 0x88};
    private String barcode = "";
    private boolean isPaying = false;
    private String payMsg;
    // 当前小票编号，在生成订单后被赋值
    public static String curOrderNo = "";
    // 账户余额
    private float accoutBalance;
    public static String cardMemberId = "0";
    private WeiCaiOperationWaterAdapter weiCaiOperationWaterAdapter;
    private ConcurrentLinkedQueue<JSONObject> listPrint = new ConcurrentLinkedQueue<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_general;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        menuList = new ArrayList<>();
        setData();
        initTime();
    }

    public ScheduledExecutorService mExecutor = new ScheduledThreadPoolExecutor(4);

    private void initTime() {
        mExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    payResultSelect();
                } catch (Exception e) {
                    Log.d("frost", "payResultSelect is error");
                    e.printStackTrace();
                }
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /**
         * ActivityResult回调
         */
        ((MainActivity) context).setScanCallback(new MainActivity.OnScanResultCallback() {
            @Override
            public void onresult(int requestCode, int resultCode, Intent data) {
            }

            /**
             * 刷卡回调
             * @param intent
             */
            @Override
            public void onNewIntent(Intent intent) {
                try {
                    String mTagText = null;
                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    //加密IC卡

                    mifareClassic = MifareClassic.get(tag);
                    mifareClassic.connect();
                    //获取扇区数量
                    int count = mifareClassic.getSectorCount();
                    Log.e("onNewIntent:", "扇区数量 ===" + count);
                    MyUtil.appendFile("开始刷卡");
                    boolean isOpen = mifareClassic.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT);
                    if (isOpen) {
                        MyUtil.appendFile("检测到此卡为默认密码");
                        int bCount = mifareClassic.getBlockCountInSector(1);
                        int bIndex = mifareClassic.sectorToBlock(1);
                        for (int j = 0; j < bCount; j++) {
                            Log.e("onNewIntent:", "存储器的位置 ===" + bIndex + "当前块 === " + (bIndex + j));
                            //修改KeyA和KeyB
                            if ((bIndex + j) == 7) {
                                MyUtil.appendFile("开始加密第" + (bIndex + j) + "块");
                                mifareClassic.writeBlock(bIndex + j, new byte[]{(byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88, (byte) 0xff, 0x07, (byte) 0x80, (byte) 0x69, (byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88});
                                Log.e("onNewIntent:", (bIndex + j) + "块加密成功");
                                MyUtil.appendFile((bIndex + j) + "块加密成功");
                                MyUtil.appendFile("-----------------------");
                            }
                        }
                    } else {
                        //安装这个apk，使用新卡用手机复制卡ID，再用手机
                        boolean isPassword = mifareClassic.authenticateSectorWithKeyA(1, key);
                        MyUtil.appendFile("检测到此卡非默认密码");
                        MyUtil.appendFile("准备用密码解密");
                        if (isPassword) {
                            MyUtil.appendFile("密码正确，解密成功，允许使用");
                            Log.e("onNewIntent:", "密码正确");
                            if (tag != null && tag.getId() != null) {
                                String smartCardId = MyUtil.ByteArrayToHexString(tag.getId());
                                if (TextUtils.isEmpty(smartCardId)) {
                                    showStatusMessage("非法卡");
                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                } else {
                                    long convertId = MyUtil.computeMemberId(smartCardId);
                                    if (convertId == 0) {
                                        showStatusMessage("非法卡");
                                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                        return;
                                    }
                                    int diff = 10 - String.valueOf(convertId).length();
                                    String cardId = String.valueOf(convertId);
                                    // 不足10位补0
                                    if (diff >= 0) {
                                        for (int k = 0; k < diff; k++) {
                                            cardId = ("0" + cardId);
                                        }
//                                        if (!("请刷卡").equals(general_status.getText().toString())) {
//                                        } else {
                                        // 最终得到去数据库查询的IC_ID
                                        getMemberCardBalance(String.valueOf(cardId));
//                                            return;
//                                        }
                                        return;
                                    } else if (diff < 0) {
                                        showStatusMessage("非法卡");
                                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                        return;
                                    }
                                }
                            }
                        } else {
                            Log.e("onNewIntent:", "密码错误");
                            MyUtil.appendFile("密码错误");
                        }
                    }
                } catch (Exception ex) {
                    Log.e("CrashHandler", "onNewIntent..." + ex.getMessage());
                }
            }
        });

        ((MainActivity) context).setBarcodeScannerCallback(new MainActivity.OnBarcodeScannerCallback() {
            public void onKey(KeyEvent event) {
                barcode += (char) event.getUnicodeChar();
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.d("frost", "barcode:" + barcode.trim());
                    if (isPaying) {
                        return;
                    }
                    if (barcode.trim().length() > 25) {
                        showMessage("提示", "请刷新后再操作");
                        barcode = "";
                        return;
                    } else {
                        handleBarcodeScanner(barcode.trim());
                    }
                    barcode = "";
                }
            }
        });
    }

    String result = "";
    private String oldCode = "";
    // 支付宝还是微信
    private String payName;
    private int pay_result_select = 1000;

    public void handleBarcodeScanner(String resultString) {
        if (!MyUtil.obtainNetworkStatus(mContext)) {
            showMessage("", "离线无法支付");
            isPaying = false;
            et_put.setText("");
            return;
        }
        isPaying = true;
        try {
            if (result.equals(resultString)) {
                isPaying = false;
                handler.sendEmptyMessage(REFRESH_CODE);
                return;
            }
        } catch (Exception e) {
            Log.e("frost_handleBarcode:", e.getMessage());
        }

        result = resultString;
        List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                .where(TfConsumeCardRecordDao.Properties.COR_ID.eq(curOrderNo))
                .limit(1)
                .build()
                .list();
        try {
            if (cardRecords.get(0).getCOR_ID().equals(curOrderNo)) {
                Log.e("frost", "订单号已存在");
                Log.e("frost", "curOrderNo:" + curOrderNo);
                isPaying = false;
                generalRefresh();
                return;
            }
        } catch (Exception e) {
            Log.d("frost", e.toString());
        }
        if (cardRecords != null && cardRecords.size() > 0) {
            Log.e("frost", "订单号已存在");
            Log.e("frost", "curOrderNo:" + curOrderNo);
            showMessage("提示", "该订单已支付");
            generalRefresh();
            return;
        }

        if (resultString == null) {
            return;
        }
        et_put.requestFocus();
        et_put.setText("");

        if (resultString.substring(0, 2).equals("28")) {
            if (!"".equals(payMsg) && payMsg != null) {
                handler.sendEmptyMessage(PAYMENTING);
            }
        } else if (resultString.substring(0, 2).equals("13")) {
            if (!"".equals(payMsg) && payMsg != null) {
                handler.sendEmptyMessage(PAYMENTING);
            }
        } else if (resultString.substring(0, 2).equals("18") && resultString.length() == 17) {
            getMemberCardBalance(resultString);
        } else {
            List<CommodityRecord> commodityRecordList = pDaoSession.queryBuilder(CommodityRecord.class)
                    .where(CommodityRecordDao.Properties.CI_NO.eq(resultString))
                    .build()
                    .list();
            if (commodityRecordList.size() > 0) {
                CommodityRecord record = new CommodityRecord();
                record.setCI_PRICE(commodityRecordList.get(0).getCI_PRICE());
                record.setCI_ID(commodityRecordList.get(0).getCI_ID());
                record.setCI_NAME(commodityRecordList.get(0).getCI_NAME());
                addDishInternal(record);

                MainActivity mainActivity = (MainActivity) getActivity();
                float totalPrices = 0;
                for (OperationMenu menu : menuListAdapter.getData()) {
                    totalPrices += menu.getTotalPrice();
                }
                mainActivity.showPay(mainActivity, "card", "待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)), "", "", "");

            } else {
                showMessage("", "无此商品");
            }
            et_put.setText("");
            isPaying = false;
            return;
        }

        Log.d("frost", "handleBarcodeScanner: " + resultString);
//        handler.sendEmptyMessage(CLEAR_SCANNER_NUM);
        if (oldCode.isEmpty() || oldCode == null || !resultString.equals(oldCode)) {
            oldCode = resultString;
        } else {
            Log.d("frost", "can't repeat payment with same code!!!");
            return;
        }

        String payPrice = payMsg;
        int dis_count = MyUtil.getDisCountForAll();//全员折扣
        Log.i("frost", "handleBarcodeScanner-dis_count: " + dis_count);
        if (dis_count < 100) {
            payPrice = MyUtil.calculateDisCountPrice(payPrice, dis_count);
            Log.i("frost", "handleBarcodeScanner- scan->payPrice   折扣后的值为->" + payPrice);
        }
        float payTotalPrice;
        try {
            payTotalPrice = Float.parseFloat(payMsg);
            if (payTotalPrice == 0) {
                isPaying = false;
                et_put.setText("");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (resultString != null && resultString.trim().length() > 0) {
            switch (resultString.substring(0, 2)) {
                case "28":
                    payWay = PAY_ALIPAY;
                    payName = "支付宝";
                    break;
                case "13":
                    payWay = PAY_WECHAT;
                    payName = "微信";
                    break;
            }

            if ("".equals(payMsg) || payMsg == null) {
                isPaying = false;
                return;
            }
            long star = System.currentTimeMillis();
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String url = ip + ":" + port + "/k-occ/pay/reverse/scan";
            String clienCode, storeCode;
            clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
            storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            if ("".equals(curOrderNo) || curOrderNo == null || curOrderNo.isEmpty()) {
                generalRefresh();
                return;
            }
            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("authCode", resultString);
            jsonData.addProperty("requestNum", curOrderNo);
            jsonData.addProperty("transamt", payPrice);

            Log.e("frost", "handleBarcodeScanner jsonData:   " + jsonData.toString());
            String price = payPrice;
            String count = menuListAdapter.getData().size() + "";
            RequestBody requestBody = new FormBody.Builder()
                    .add("data", jsonData.toString())
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    long end = System.currentTimeMillis();
                    try {
                        MyUtil.appendFile("#####################外接扫码start 订单号" + curOrderNo + "#####################" + "\n");
                        MyUtil.appendFile("请求时间：" + star);
                        MyUtil.appendFile("请求地址：" + url);
                        MyUtil.appendFile("请求参数：" + jsonData.toString());
                        MyUtil.appendFile("响应参数：" + e.getMessage());
                        MyUtil.appendFile("耗时：" + (end - star) + "");
                        MyUtil.appendFile("结束时间：" + end);
                        MyUtil.appendFile("##################### end #####################" + "\n");
                        Log.e("frost", "handleBarcodeScanner:onFailure:" + e.getMessage());
                        isPaying = false;
                        showStatusMessage("支付超时");
                        mainActivity.showPay(mainActivity, "card", "支付超时", "", "", "");
                        pay_result_select = 1000;
                        createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", count, 0);
                    } catch (Exception msg) {
                        uploadingError(jsonData.toString(), msg.getMessage(), "微信支付onFailure", mContext);
                    }
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    long end = System.currentTimeMillis();
                    try {
                        Gson gson = new Gson();
                        String body = response.body().string();
                        Log.e("frost", "handleBarcodeScanner....3...body:" + body);

                        MyUtil.appendFile("#####################外接扫码start 订单号" + curOrderNo + "#####################" + "\n");
                        MyUtil.appendFile("请求时间：" + star);
                        MyUtil.appendFile("请求地址：" + url);
                        MyUtil.appendFile("请求参数：" + jsonData.toString());
                        MyUtil.appendFile("响应参数：" + body);
                        MyUtil.appendFile("耗时：" + (end - star) + "");
                        MyUtil.appendFile("结束时间：" + end);
                        MyUtil.appendFile("##################### end #####################" + "\n");

                        JSONObject jsonRes = new JSONObject(body);
                        if (jsonRes != null) {
                            String code = jsonRes.getString("code");
                            String orderId = jsonRes.optString("data");
                            String msg1 = jsonRes.optString("msg");
                            Log.e("frost", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);
                            if ("0000".equals(code)) {
                                // true 支付成功
                                Log.d("frost", "非估价coming");
                                isPaying = false;
                                payMsg = "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", count, 1);
                                Log.e("frost_test", "price:" + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))));
                                Log.e("frost", "success payWay:" + payWay + " payName...." + payName);
                                playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                                mTotalPersonCount += 1;
                                handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showStatusMessage("支付成功");
                                        mainActivity.showPay(mainActivity, "card", "支付成功 ", "", "", "");
                                    }
                                });
                            } else if ("0001".equals(code) || "0002".equals(code) || "0005".equals(code)) {
                                isPaying = false;
                                payMsg = "";
//                                generalRefresh();
                                showMessage("提示", msg1);
                            } else if ("0004".equals(code)) {
                                isPaying = false;
                                payMsg = "";
                                showStatusMessage(msg1);
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", count, 0);
                            } else {
                                if (msg1.equals("缓存中没有第三方支付配置")) {
                                    showMessage("警告", msg1);
//                                    generalRefresh();
                                    return;
                                }
                                showStatusMessage("支付超时");
                                isPaying = false;
                                payMsg = "";
                                pay_result_select = 0;
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", count, 0);
                            }
                        } else {
                            isPaying = false;
                            payMsg = "";
                        }
                    } catch (Exception e) {
                        isPaying = false;
                        handler.sendEmptyMessage(WEIXIN_CODE);
                        Log.e("frost", "handleBarcode  exception" + e.getMessage());
                        uploadingError(jsonData.toString(), e.getMessage(), "微信支付onResponse", mContext);
                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }

    }

    /**
     * 支付结果验证
     */
    private void payResultSelect() {
        if (!MyUtil.networkState) {
            return;
        }
        String temp_order_no = "";
        pay_result_select++;
        if (pay_result_select <= 180) {
            if (pay_result_select % 30 != 0) {
                return;
            }
            temp_order_no = curOrderNo;
        } else {
            if (pay_result_select % 5 != 0) {
                return;
            }
            List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                    .where(
                            TfConsumeCardRecordDao.Properties.CCR_STATUS.eq(0),
                            TfConsumeCardRecordDao.Properties.CCR_PAY_TYPE.in(PAY_WECHAT, PAY_ALIPAY))
                    .limit(1)
                    .build()
                    .list();

            if (cardRecords != null && cardRecords.size() > 0) {
                TfConsumeCardRecord cardRecord = cardRecords.get(0);
                if (cardRecord != null) {
                    temp_order_no = cardRecord.getCOR_ID();
                }
            }
        }

        Log.e(TAG, "payResultSelect....1 pay_result_select:" + pay_result_select + " temp_order_no:" + temp_order_no);
        if (temp_order_no != null && temp_order_no.length() > 0) {
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String clienCode = "", storeCode = "";
            storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            long star = System.currentTimeMillis();
            String url = ip + ":" + port + "/k-occ/pay/result/select";

            final String req_order_no = temp_order_no;

            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("payType", payWay);
            jsonData.addProperty("corId", req_order_no);

            Log.e("frost", "payResultSelect jsonData:" + jsonData.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("data", jsonData.toString())
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            String finalTemp_order_no = temp_order_no;
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    main_progress.setVisibility(View.GONE);
                    Log.d("frost", "失敗");
                    Log.e("url", "result/select:onFailure");
                    Log.e("url", "result/select:onFailure:" + e.getMessage());
                    Log.d("pay", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    long end = System.currentTimeMillis();
                    try {
                        String body = response.body().string();
                        Log.d("frost", body);
                        MyUtil.appendFile("##################### 验证start 订单号:" + finalTemp_order_no + " #####################" + "\n");
                        MyUtil.appendFile("请求时间：" + star);
                        MyUtil.appendFile("请求地址：" + url);
                        MyUtil.appendFile("请求参数：" + jsonData.toString());
                        MyUtil.appendFile("响应参数：" + body);
                        MyUtil.appendFile("耗时：" + (end - star) + "");
                        MyUtil.appendFile("结束时间：" + end);
                        MyUtil.appendFile("##################### end #####################" + "\n");

                        Log.e("frost", "payResultSelect....3...body:" + body);

                        JSONObject jsonRes = new JSONObject(body);
                        if (jsonRes != null) {
                            String code = jsonRes.getString("code");
                            String orderId = jsonRes.optString("data");
                            Log.e("frost", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);

                            int CCR_STATUS = 0;
                            if ("0000".equals(code)) {
                                CCR_STATUS = 1;
                                pay_result_select = 1000;

                                if (orderId != null && orderId.equals(curOrderNo)) {
//                                    // 更新人数区记录
                                    mTotalPersonCount += 1;
                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);

                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                                }
                            } else if ("0003".equals(code)) {

                            } else {
                                pay_result_select = 1000;
                                CCR_STATUS = 2;
                                Log.e("frost", "come here");
                            }
                            if (CCR_STATUS > 0) {
                                List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                                        .where(TfConsumeCardRecordDao.Properties.COR_ID.eq(req_order_no))
                                        .limit(1)
                                        .build()
                                        .list();
                                if (cardRecords != null && cardRecords.size() > 0) {
                                    TfConsumeCardRecord cardRecord = cardRecords.get(0);
                                    if (cardRecord != null) {
                                        cardRecord.setCCR_STATUS("" + CCR_STATUS);
                                        DaoSession session = MyApplication.getInstance();
                                        session.update(cardRecord);
                                        pay_result_select = 1000;
                                        Log.e("pay", "session.insert(cardRecord) order_no:" + cardRecord.getCOR_ID() + " CCR_STATUS:" + CCR_STATUS);
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        uploadingError(jsonData.toString(), e.getMessage(), "支付验证onResponse", mContext);
                    }
                }
            });
        }
    }

    private String judgePrintinfo() {
        String word = "";
        switch (payWay) {
            case PAY_CASH:
                //word = "找零：" + txChange.getText().toString();
                word = "找零：0.0";
                break;
            case PAY_WECHAT:
            case PAY_ALIPAY:
            case PAY_MEMBER_CARD:
                word = "余额：" + MyConstant.gFormat.format(accoutBalance);
                break;
        }
        return word;
    }

    /**
     * 打印小票
     */
    private void printerReceipt(TfMemberInfo memberInfo) {
        if (payWay == 5) {
            return;
        }
        String printStatus = gSharedPre.getString(MyConstant.SP_AUTO_PRINT, "");
        Log.d("frost", "printerReceipt: " + printStatus);
        boolean autoPrint = printStatus.equals("1");
        // 为空是因为没有保存设置，默认为自动打印
        if (autoPrint || printStatus.isEmpty()) {
            //setupPrinter();
            addPrintContent(memberInfo);
        } else {
//            if (payWay != PAY_CASH) {
//                gOperationHandler.obtainMessage(REFRESH).sendToTarget();
//            }
            return;
        }
    }

    private void addPrintContent(TfMemberInfo memberInfo) {
        try {
            String word = judgePrintinfo();
            String pay_way_name = "";
            switch (payWay) {
                case PAY_CASH:
                    pay_way_name = "现金";
                    word = "找零：0.0";
                    // word = "找零：" + txChange.getText().toString();
                    break;
                case PAY_WECHAT:
                    pay_way_name = "微信";
                    break;
                case PAY_ALIPAY:
                    pay_way_name = "支付宝";
                    break;
                case PAY_MEMBER_CARD:
                    pay_way_name = "会员卡";
                    word = "余额：" + MyConstant.gFormat.format(accoutBalance);
                    break;
            }


            String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
            String storeName = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_NAME, "");
            String storeAddr = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_ADDR, "");

            org.json.JSONObject objPrint = new org.json.JSONObject();
            objPrint.put("order_no", curOrderNo);
            objPrint.put("word", word);
            objPrint.put("machineNo", machineNo);
            objPrint.put("storeName", storeName);
            objPrint.put("storeAddr", storeAddr);
            objPrint.put("timestr", MyUtil.obtainCurrentSysDate(2));
            objPrint.put("uname", mainActivity.pUserInfo.getUNAME());

            String str_details = "";
            StringBuilder result = new StringBuilder();
            int count = 0;
            float totalPrice = 0;
            int discountType = 0;
            List<OperationMenu> operationMenuList = menuListAdapter.getData();
            for (int i = 0; i < operationMenuList.size(); i++) {
                OperationMenu menu = operationMenuList.get(i);
                if (payWay == PAY_MEMBER_CARD) {
                    discountType = 1;
                }
                //菜单名字的处理
                PrintDishName printDishName = new PrintDishName(menu.getDishNmae(), result);
                int kong_count = 11;
                int len_name = MyUtil.strLength(printDishName.getLastLineStr());
                kong_count -= len_name;
                for (int n = 0; n < kong_count; n++) {
                    result.append(" ");
                }
                result.append(String.valueOf(menu.getCount()));
                //打印纸上单价
                kong_count = 11;
//                if(printDishName.getChangeLineCount() > 0){
//                    for(int temp = 0;temp<printDishName.getChangeLineCount();temp++){
//                        result.append("\n");
//                    }
//                }
                String str_count = String.valueOf(menu.getCount());
                String str_unit_price = MyConstant.gFormat.format(menu.getUnitPrice());
                kong_count = kong_count - str_count.length() - str_unit_price.length();
                for (int n = 0; n < kong_count; n++) {
                    result.append(" ");
                }
                result.append(MyConstant.gFormat.format(menu.getUnitPrice()));
                //打印纸上 金额的拼接
                kong_count = 8;
//                if(printDishName.getChangeLineCount() > 0){
//                    for(int temp = 0;temp<printDishName.getChangeLineCount();temp++){
//                        result.append("\n");
//                    }
//                }
                //String str_total_price = MyConstant.gFormat.format(menu.getTotalPrice());
                float unit_total_price = menu.getUnitDisPrice() * menu.getCount();
                String str_total_price = MyConstant.gFormat.format(unit_total_price);
//                Log.e("url","str_total_price:"+str_total_price);

                kong_count -= str_total_price.length();
                for (int n = 0; n < kong_count; n++) {
                    result.append(" ");
                }
                result.append(str_total_price);

                str_details += result.toString();
                result.setLength(0);
                count += menu.getCount();
                totalPrice += menu.getTotalPrice();
//                Log.e("url","menu.getTotalPrice():"+menu.getTotalPrice());
//                Log.e("url","totalPrice:"+totalPrice);
            }
            Log.e("url", "word:" + word);
            int dis_count = 100;
            if (discountType == 1 && memberInfo != null && memberInfo.getMI_TYPE() != null) {
                dis_count = MyUtil.getDisCountForMember(memberInfo.getMI_TYPE());
                Log.e("pay", "mi_type:" + memberInfo.getMI_TYPE() + " dis_count:" + dis_count);
            }
            if (dis_count == 100) {
                dis_count = MyUtil.getDisCountForAll();
                Log.e("pay", "for all dis_count:" + dis_count);
            }
            float rate = Float.parseFloat("" + dis_count) / 100f;

            str_details += "\n--------------------------------";
            str_details += "\n总数量：" + count;
            str_details += "\n总金额：" + MyConstant.gFormat.format(totalPrice);
            str_details += "\n支付方式：" + pay_way_name;
            str_details += "\n" + word;
            str_details += "\n折扣：" + rate;
            str_details += "\n地址：" + storeAddr;
            objPrint.put("str_details", str_details);

            Log.e("print", "objPrint.toString():" + objPrint.toString());
            listPrint.offer(objPrint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PrintDishName {
        //换行的行数
        private int changeLineCount = 0;
        //最后一行的文字
        private String lastLineStr = "";
        private String source;
        private StringBuilder result;

        public PrintDishName(String source, StringBuilder result) {
            this.result = result;
            this.source = source;
            processData();
        }

        private void processData() {
            //对于文字的处理
            String chinese = "[\u0391-\uFFE5]";
            int lineCount = 0;
            /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
            for (int i = 0; i < source.length(); i++) {
                if (lineCount == 0) {
                    changeLineCount++;//换行书 +1
                    result.append("\r\n");//换行符
                    lastLineStr = "";//更新最后一行的数据
                }
                /* 获取一个字符 */
                String temp = source.substring(i, i + 1);
                result.append(temp);
                lastLineStr += temp;
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    /* 中文字符长度为2 */
                    lineCount += 2;
                } else {
                    /* 其他字符长度为1 */
                    lineCount += 1;
                }
                if (lineCount >= 10) {
                    lineCount = 0;
                }
            }
            //减一行，打印文字
            changeLineCount--;
        }

        public int getChangeLineCount() {
            return changeLineCount;
        }

        public String getLastLineStr() {
            return lastLineStr;
        }
    }

    private TfMemberInfo memberInfo;

    private void getMemberCardBalance(String icId) {
        Log.e("frost", "icId-----" + icId);
        memberInfo = null;
        if (isPaying) {
            return;
        }
        curOrderNo = MyUtil.getPrimaryValue();
        isPaying = true;
        if ("".equals(general_totalPrice.getText().toString())) {
            showStatusMessage("员工卡查询中");
        }
        String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
        String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
        String clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
        String storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
        String url = ip + ":" + port + "/k-occ/member/info/" + clienCode + "/" + storeCode + "/icId/" + icId;
        if (MyUtil.obtainNetworkStatus(mContext)) {
            if (!"".equals(payMsg)) {
                handler.sendEmptyMessage(PAYMENTING);
            }
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(5, TimeUnit.SECONDS)//设置读取超时时间
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            long star = System.currentTimeMillis();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    offLine(icId, url);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    long end = System.currentTimeMillis();
                    try {
                        isPaying = false;
                        TfCardInfo info = null;
                        TfMemberAccountRecord cashRecord = null;
                        TfMemberAccountRecord subsidyRecord = null;
                        float cashBalance = 0, subsidyBalance = 0;

                        Gson gson = new Gson();
                        String body = response.body().string();
                        Log.e("frost_onResponse1", body);
                        MyUtil.appendFile("#####################会员卡线上 onResponse start 订单号" + curOrderNo + "#####################" + "\n");
                        MyUtil.appendFile("请求时间：" + star);
                        MyUtil.appendFile("请求地址：" + url);
                        MyUtil.appendFile("请求参数：" + url);
                        MyUtil.appendFile("响应参数：" + body);
                        MyUtil.appendFile("耗时：" + (end - star) + "");
                        MyUtil.appendFile("结束时间：" + end);
                        MyUtil.appendFile("##################### end #####################" + "\n");
                        //验证卡的状态
                        ResultBaseBean resultBaseBean = gson.fromJson(body, new TypeToken<ResultBaseBean>() {
                        }.getType());
                        if (!resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_NORMAL)) {
                            if (resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_LOST)) {
                                showStatusMessage("挂失卡");
                                playVoice(MyConstant.SP_LOST_CARD);
                            } else if (resultBaseBean.getCode().equals("0003") || resultBaseBean.getCode().equals("0006")) {
                                handler.sendEmptyMessage(CARD_ILLEGALITY);
                                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                            } else if (resultBaseBean.getCode().equals("0005")) {
                                handler.sendEmptyMessage(CARD_STATUS_CANCELEDS);
                                playVoice(MyConstant.SP_CANCEL_CARD);
                            } else {
                                showMessage("", resultBaseBean.getMsg());
                            }
                            return;
                        }
                        QueryCardResultInfo queryCardResultInfo = gson.fromJson(body, new TypeToken<QueryCardResultInfo>() {
                        }.getType());
                        if (queryCardResultInfo != null) {
                            if (queryCardResultInfo.isResult()) {
                                try {
                                    if (queryCardResultInfo.getData() != null && queryCardResultInfo.getData().getCard() != null && queryCardResultInfo.getData().getCard().size() > 0) {
                                        QueryCardResultInfo.DataBean.CardBean cardBean = null;
                                        for (QueryCardResultInfo.DataBean.CardBean cardBeans : queryCardResultInfo.getData().getCard()) {
                                            if (cardBeans.getIC_ID().equals(icId)) {
                                                cardBean = cardBeans;
                                            }
                                        }
                                        if (cardBean != null) {
                                            // 提示卡状态
                                            // '0':'正常'；'1':'挂失';'2':'注销'
                                            if (cardBean.getIC_STATUS() != null) {
                                                if (!cardBean.getIC_STATUS().equals("0")) {
                                                    switch (cardBean.getIC_STATUS()) {
                                                        case "1":
                                                            handler.sendEmptyMessage(CARD_STATUS_LOSTS);
                                                            playVoice(MyConstant.SP_LOST_CARD);
                                                            break;
                                                        case "2":
                                                            handler.sendEmptyMessage(CARD_STATUS_CANCELEDS);
                                                            playVoice(MyConstant.SP_CANCEL_CARD);
                                                            break;
                                                    }
                                                    return;
                                                }
                                                info = new TfCardInfo();
                                                info.setMI_ID(cardBean.getMI_ID());
                                                QueryCardResultInfo.DataBean.MemberBean bean = queryCardResultInfo.getData().getMember();
                                                if (bean != null) {
                                                    memberInfo = new TfMemberInfo();
                                                    memberInfo.setMI_NAME(bean.getMI_NAME());
                                                    memberInfo.setMI_PHONE(bean.getMI_PHONE());
                                                    memberInfo.setMI_TYPE(bean.getMI_TYPE());
                                                }
                                                if (queryCardResultInfo.getData().getAccount() != null && queryCardResultInfo.getData().getAccount().size() > 0) {
                                                    for (QueryCardResultInfo.DataBean.AccountBean record : queryCardResultInfo.getData().getAccount()) {
                                                        if (record.getMI_ID().equals(cardBean.getMI_ID())) {
                                                            if (record.getACCOUNT_TYPE().equals("0")) {//现金账户
                                                                cashRecord = new TfMemberAccountRecord();
                                                                cashRecord.setBALANCE(record.getBALANCE());
                                                                cashRecord.setACCOUNT_STATUS(record.getACCOUNT_STATUS());
                                                                cashRecord.setACCOUNT_TYPE(record.getACCOUNT_TYPE());
                                                                cashRecord.setACCOUNT_ID(record.getACCOUNT_ID());
                                                                cashRecord.setCLIENT_CODE(record.getCLIENT_CODE());
                                                                cashRecord.setCREATETIME(record.getCREATETIME());
                                                                cashRecord.setISM_STATUS(record.getISM_STATUS());
                                                                cashRecord.setMI_ID(record.getMI_ID());
                                                                cashRecord.setREFUND_STATUS(record.getREFUND_STATUS());
                                                                cashRecord.setSTORE_CODE(record.getSTORE_CODE());
                                                                cashRecord.setTOTAL_RECHARGE_MONEY(record.getTOTAL_RECHARGE_MONEY());
                                                                cashRecord.setU_ID("0");
                                                                cashRecord.setUPDATETIME(record.getUPDATETIME());
                                                            } else if ((record.getACCOUNT_TYPE().equals("1"))) {//补贴账户
                                                                subsidyRecord = new TfMemberAccountRecord();
                                                                subsidyRecord.setBALANCE(record.getBALANCE());
                                                                subsidyRecord.setACCOUNT_STATUS(record.getACCOUNT_STATUS());
                                                                subsidyRecord.setACCOUNT_TYPE(record.getACCOUNT_TYPE());
                                                                subsidyRecord.setACCOUNT_ID(record.getACCOUNT_ID());
                                                                subsidyRecord.setCLIENT_CODE(record.getCLIENT_CODE());
                                                                subsidyRecord.setCREATETIME(record.getCREATETIME());
                                                                subsidyRecord.setISM_STATUS(record.getISM_STATUS());
                                                                subsidyRecord.setMI_ID(record.getMI_ID());
                                                                subsidyRecord.setREFUND_STATUS(record.getREFUND_STATUS());
                                                                subsidyRecord.setSTORE_CODE(record.getSTORE_CODE());
                                                                subsidyRecord.setTOTAL_RECHARGE_MONEY(record.getTOTAL_RECHARGE_MONEY());
                                                                subsidyRecord.setU_ID("0");
                                                                subsidyRecord.setUPDATETIME(record.getUPDATETIME());
                                                            }
                                                        }
                                                    }
                                                }
                                                if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                                                    Float cashBalance1 = Float.parseFloat(cashRecord.getBALANCE());
                                                    Float subsidyBalance1 = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                                                    String balance = MyConstant.gFormat.format(cashBalance1 + subsidyBalance1);
                                                    if ("".equals(payMsg)) {
                                                        showMemberInfo(memberInfo, balance, "准备下单");
                                                        return;
                                                    }
                                                    calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                                                } else {
                                                    handler.sendEmptyMessage(CARD_ILLEGALITY);
                                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                                }
                                            }
                                        }
                                    } else {
                                        Log.d("look", "queryCardResultInfo data is null");
                                    }
                                } catch (Exception e) {
                                    uploadingError(url, e.getMessage(), "会员卡在线onResponse", mContext);
                                    Log.e("frost", e.getMessage());
                                }
                            }
                        }
                    } catch (Exception e) {
                        uploadingError(url, e.getMessage(), "会员卡支付onResponse", mContext);
                        Log.d("frost", "onResponse: " + e.getMessage());
                        isPaying = false;
                        showMessage("提示", "请求错误，请稍后再试");
                        generalRefresh();
                        return;
                    }
                }
            });
        } else {
            offLine(icId, url);
        }

    }

    private void showMemberInfo(TfMemberInfo memberInfo, String balance, String str) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isPaying = false;
                menuListAdapter.clearData();
                general_totalPrice.setText("");
                general_totalNum.setText("");
                general_balance.setText(balance);
                general_name.setText(memberInfo.getMI_NAME());
                general_vip_no.setText(memberInfo.getMI_PHONE());
                showStatusMessage("准备下单");
            }
        });
    }

    private void offLine(String icId, String url) {
        long star = System.currentTimeMillis();
        try {
            isPaying = false;
            Log.e("url", "线下支付");
            TfCardInfo info = null;
            TfMemberAccountRecord cashRecord = null;
            TfMemberAccountRecord subsidyRecord = null;
            List<TfCardInfo> cardInfoList = pDaoSession.queryBuilder(TfCardInfo.class)
                    .where(TfCardInfoDao.Properties.IC_ID.eq(icId))
                    .build()
                    .list();
            Log.d("frost", "cardInfoList" + cardInfoList);
            if (cardInfoList.isEmpty() || cardInfoList == null) {
                info = null;
            } else {
                info = cardInfoList.get(0);
            }
            if (info != null) {
                // 提示卡状态
                // '0':'正常'；'1':'挂失';'2':'注销'
                if (!info.getIC_STATUS().equals("0")) {
                    switch (info.getIC_STATUS()) {
                        case "1":
                            handler.sendEmptyMessage(CARD_STATUS_LOSTS);
                            playVoice(MyConstant.SP_LOST_CARD);
                            break;
                        case "2":
                            handler.sendEmptyMessage(CARD_STATUS_CANCELEDS);
                            playVoice(MyConstant.SP_CANCEL_CARD);
                            break;
                    }
                    return;
                }
                // account status : 0 normal 1 freeze
                Log.e("look", "getMemberCardBalance info.getMI_ID():" + info.getMI_ID());
                List<TfMemberInfo> memberInfoList = pDaoSession.queryBuilder(TfMemberInfo.class)
                        .where(TfMemberInfoDao.Properties.MI_ID.eq(info.getMI_ID()))
                        .build()
                        .list();
                Log.e("android", "getMemberCardBalance info.getMI_ID():" + memberInfoList.toString());
                if (memberInfoList == null || memberInfoList.isEmpty()) {
                    return;
                }
                memberInfo = memberInfoList.get(0);
                List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
                        .where(TfMemberAccountRecordDao.Properties.MI_ID.eq(info.getMI_ID()))
                        .build()
                        .list();
                if (accountRecordList.isEmpty() || accountRecordList == null) {
                    cashRecord = null;
                } else {
                    for (TfMemberAccountRecord record : accountRecordList) {
                        if (record.getACCOUNT_TYPE().equals("0")) {
                            cashRecord = record;
                        } else if ((record.getACCOUNT_TYPE().equals("1"))) {
                            subsidyRecord = record;
                        }
                    }
                    if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                        try {
                            long end = System.currentTimeMillis();
                            Float cashBalance1 = Float.parseFloat(cashRecord.getBALANCE());
                            Float subsidyBalance1 = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                            String balance = MyConstant.gFormat.format(subsidyBalance1 + cashBalance1);
                            Log.d("frost", "calcurlateMemberBalance----3");
                            MyUtil.appendFile("#####################会员卡离线 start 订单号" + curOrderNo + "#####################" + "\n");
                            MyUtil.appendFile("请求时间：" + star);
                            MyUtil.appendFile("请求地址：" + url);
                            MyUtil.appendFile("请求参数：" + url);
                            MyUtil.appendFile("响应参数：" + "");
                            MyUtil.appendFile("耗时：" + (end - star) + "");
                            MyUtil.appendFile("结束时间：" + end);
                            MyUtil.appendFile("##################### end #####################" + "\n");
                            if ("".equals(payMsg)) {
                                showMemberInfo(memberInfo, balance, "准备下单");
                                return;
                            }
                            calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
//                            if ("".equals(business_price.getText().toString()) || business_price.getText().toString() == null) {
//                                showMemberInfo(memberInfo, balance, "准备下单");
//                            }
                            pDaoSession.clear();
                        } catch (Exception e1) {
                            isPaying = false;
                        } finally {
                            cashRecord = null;
                            subsidyRecord = null;
                            return;
                        }
                    }
                }
            }
            showStatusMessage("非法卡");
            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
        } catch (Exception e12) {
            long end = System.currentTimeMillis();
            uploadingError(url, e12.getMessage(), "会员卡支付失败onFailure", mContext);
            MyUtil.appendFile("#####################会员卡线上请求失败start 订单号" + curOrderNo + "#####################" + "\n");
            MyUtil.appendFile("请求时间：" + star);
            MyUtil.appendFile("请求地址：" + url);
            MyUtil.appendFile("请求参数：" + url);
            MyUtil.appendFile("响应参数：" + e12.getMessage());
            MyUtil.appendFile("耗时：" + (end - star) + "");
            MyUtil.appendFile("结束时间：" + end);
            MyUtil.appendFile("##################### end #####################" + "\n");
            handler.sendEmptyMessage(8);
            isPaying = false;
        }

    }

    private void calcurlateMemberBalance(String icId, TfCardInfo info, TfMemberInfo memberInfo, TfMemberAccountRecord cashRecord, TfMemberAccountRecord subsidyRecord) {
        try {
            float cashBalance = 0, subsidyBalance = 0;
            if (cashRecord != null) {
                if (subsidyRecord == null) {
                    showMessage("", "该卡无补贴账户");
                    mainActivity.showPay(mainActivity, "card", "该卡无补贴账户", "", "", "");
                    return;
                }
                if (cashRecord.getACCOUNT_STATUS().equals("1")
                        || subsidyRecord != null
                        && subsidyRecord.getACCOUNT_STATUS().equals("1")) {
                    showMessage("", "当前账户已冻结");
                    handler.sendEmptyMessage(PAYMENTFAILED);
                    mainActivity.showPay(mainActivity, "card", "当前账户已冻结", "", "", "");
                    return;
                }
                cashBalance = Float.parseFloat(cashRecord.getBALANCE());
                subsidyBalance = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                float balance = cashBalance + subsidyBalance;
                long start_time = System.currentTimeMillis();
                // 全民以及会员折扣
                String mi_type = memberInfo.getMI_TYPE();//会员类型
                int dis_count = MyUtil.getDisCountForMember(mi_type);//会员折扣
                Log.e("frost", "mi_type:" + mi_type + "  会员折扣 dis_count:" + dis_count);
                if (dis_count == 100) {
                    dis_count = MyUtil.getDisCountForAll();//全员折扣
                }
                float payNum = Float.parseFloat(payMsg) * dis_count / 100f;
                // 0 社会餐饮 先扣现金再扣补贴，1 团餐 先扣补贴再扣现金
                if (mainActivity.pStoreConfig == null) {
                    List<StoreConfig> storeConfigList = pDaoSession.queryBuilder(StoreConfig.class)
                            .build()
                            .list();
                    if (!storeConfigList.isEmpty() && storeConfigList != null) {
                        mainActivity.pStoreConfig = storeConfigList.get(0);
                    } else {
                        showMessage("提示", "餐饮类型未知");
                        mainActivity.showPay(mainActivity, "card", "餐饮类型未知", "", "", "");
                        return;
                    }
                }

                switch (mainActivity.pStoreConfig.getSTORE_TYPE()) {
                    case "0"://社会快餐
                        cashBalance = cashBalance - payNum;
                        cashRecord.setBALANCE(MyConstant.gFormat.format(cashBalance));
                        // 现金扣完，扣补贴
                        if (cashBalance < 0) {
                            subsidyBalance = subsidyBalance - Math.abs(cashBalance);
                            cashBalance = subsidyBalance;
                            cashRecord.setBALANCE("0");
                            subsidyRecord.setBALANCE(MyConstant.gFormat.format(subsidyBalance));
                        } else {
                            // 没扣完，加上补贴金额
                            cashBalance += subsidyBalance;
                        }
                        accoutBalance = cashBalance;
                        break;
                    case "1"://团膳
                        subsidyBalance = subsidyBalance - payNum;
                        subsidyRecord.setBALANCE(MyConstant.gFormat.format(subsidyBalance));
                        if (subsidyBalance < 0) {
                            cashBalance = cashBalance - Math.abs(subsidyBalance);
                            subsidyBalance = cashBalance;
                            subsidyRecord.setBALANCE("0");
                            cashRecord.setBALANCE(MyConstant.gFormat.format(cashBalance));
                        } else {
                            subsidyBalance += cashBalance;
                        }
                        accoutBalance = subsidyBalance;
                        break;
                }
                if (accoutBalance < 0) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            general_balance.setText(balance + "");
                            general_name.setText(memberInfo.getMI_NAME());
                            general_vip_no.setText(memberInfo.getMI_PHONE());
                            showStatusMessage("余额不足");
                            mainActivity.showPay(mainActivity, "card", "余额不足", memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance + "");
//                            payMsg = "";
//                            curOrderNo = "";
                        }
                    });
                    // 余额不足
                    playVoice(MyConstant.SP_MEDIA_MONEY_NOT_ENOUGH);
                    return;
                } else {
                    // 支付成功
                    payWay = PAY_MEMBER_CARD;
//                    curOrderNo = MyUtil.getPrimaryValue();
                    Log.e("frost", "支付成功");
                    //折扣后金额
                    float temp_f = Float.parseFloat(MyConstant.gFormat.format(payNum));
                    // 余额
                    float temp_balance = Float.parseFloat(MyConstant.gFormat.format(accoutBalance));
                    Log.d("frost", "temp_f:" + temp_f);
                    Log.d("frost", "temp_balance:" + temp_balance);
                    // TODO: 2019/11/7 创建一条消费信息

                    // 更新余额
//                    if (memberInfo.getMI_NAME() != null) {
                    TfMemberAccountRecord finalCashRecord = cashRecord;
                    TfMemberAccountRecord finalSubsidyRecord = subsidyRecord;
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createConsumeInfo(temp_f, temp_balance, info.getMI_ID(), finalCashRecord.getACCOUNT_ID(), icId, 1 + "", 1);
                            general_name.setText(memberInfo.getMI_NAME());
                            general_vip_no.setText(memberInfo.getMI_PHONE());
                            String balance1 = MyConstant.gFormat.format(accoutBalance);
                            general_balance.setText(balance1);
                            showStatusMessage("支付成功");
                            payMsg = "";
                            mainActivity.showPay(mainActivity, "card", "支付成功", memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance1);
                            playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                        }
                    });
                    // 修改数据库中的会员余额
                    pDaoSession.update(cashRecord);
                    pDaoSession.update(subsidyRecord);
                    mTotalPersonCount += 1;
                    mCurMealPersonCount += 1;
                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);
                    return;
                }
            } else {
                handler.sendEmptyMessage(CARD_ILLEGALITY);
                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
            }
        } catch (Exception e) {
            Log.e("CrashHandler", "calcurlateMemberBalance.." + e.getMessage());
        } finally {
            cashRecord = null;
            subsidyRecord = null;
        }
    }

    private ThreadPoolExecutor mTHREAD_POOL = new ThreadPoolExecutor(6,
            14,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    /**
     * 创建一条消费记录
     */
    public synchronized void createConsumeInfo(final float totalMoney, final float balance, final String memberId,
                                               final String accountId, final String icid, final String personCount, final int CCR_STATUS) {

        Log.d("frost", "createConsumeInfo:" + balance);
        mTHREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    payMsg = "";
                    if (!"0".equals(memberId)) {
                        cardMemberId = memberId;
                    }
                    DaoSession session = MyApplication.getInstance();
                    String clientCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                    String storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
                    String u_id = MyConstant.gSharedPre.getString(MyConstant.U_ID, "");

                    String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
                    String createTime = MyUtil.dateConversion(System.currentTimeMillis());
                    if (curOrderNo == null && curOrderNo.length() == 0) {
                        showMessage("", "订单号不能为空");
                    }
                    List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                            .where(TfConsumeCardRecordDao.Properties.COR_ID.eq(curOrderNo))
                            .limit(1)
                            .build()
                            .list();
                    if (cardRecords != null && cardRecords.size() > 0) {
                        Log.e("frost", "订单号已存在");
                        Log.e("frost", "curOrderNo:" + curOrderNo);
                        return;
                    }

                    //curOrderNo = MyUtil.getPrimaryValue();
                    String orderNo = curOrderNo;
                    // 获取餐次
                    String currentTime = MyUtil.obtainCurrentSysDate(1);

                    List<TfMealTimes> mealTimeList = session.queryBuilder(TfMealTimes.class)
                            .where(TfMealTimesDao.Properties.STARTTIME.le(currentTime),
                                    TfMealTimesDao.Properties.ENDTIME.ge(currentTime))
                            .build()
                            .list();

                    TfMealTimes mealTimes;
                    if (mealTimeList.isEmpty()) {
                        mealTimes = null;
                    } else {
                        mealTimes = mealTimeList.get(0);
                    }

//                    // 会员卡消费记录
                    TfConsumeCardRecord cardRecord = new TfConsumeCardRecord();
                    cardRecord.setCCR_ID(MyUtil.getPrimaryValue());
                    cardRecord.setIC_ID(icid);
                    cardRecord.setCCR_MONEY(totalMoney);
                    Log.e("frost", "payWay:" + payWay);
                    switch (payWay) {
                        case PAY_CASH:
                            cardRecord.setCCR_PAY_TYPE("0");
                            break;
                        case PAY_MEMBER_CARD:
                            cardRecord.setCCR_PAY_TYPE("1");
                            break;
                        case PAY_WECHAT:
                            cardRecord.setCCR_PAY_TYPE("2");
                            break;
                        case PAY_ALIPAY:
                            cardRecord.setCCR_PAY_TYPE("3");
                            break;
                        case PAY_BANK:
                            cardRecord.setCCR_PAY_TYPE("4");
                            break;
                    }
                    //     20/1/2
                    cardRecord.setCCR_UPLOAD_STATUS("0");
                    cardRecord.setCCR_STATUS("" + CCR_STATUS);
                    cardRecord.setACCOUNT_ID(accountId);
                    cardRecord.setCLIENT_CODE(clientCode);
                    cardRecord.setCOR_ID(orderNo);
                    cardRecord.setCREATETIME(createTime);
                    cardRecord.setISM_STATUS("0");
                    cardRecord.setMI_ID(memberId);
                    cardRecord.setMT_ID(mealTimes == null ? "" : mealTimes.getMT_ID()); // 餐次
                    cardRecord.setSTORE_CODE(storeCode);
                    cardRecord.setU_ID(u_id);
                    cardRecord.setMACHINE_NO(machineNo);
                    if (cardRecord.getMI_ID() != null && cardRecord.getMI_ID().length() > 0) {
                        cardRecord.setCCR_ORIGINALAMOUNT("" + balance);
                    }
                    cardRecord.setCCR_UPLOAD_TIME("0");
                    // 添加到流水记录中
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NumberFormat ddf1 = NumberFormat.getNumberInstance();

                            //设置最多保留小数位数，不足不补0。
                            ddf1.setMaximumFractionDigits(1);

                            String s = ddf1.format(totalMoney);
                            if (s.indexOf(".") == -1) {
                                s += ".0";
                            }
                            String payWay = null;
                            if (cardRecord.getCCR_PAY_TYPE().equals("1")) {
                                payWay = "会员卡";
                            } else if (cardRecord.getCCR_PAY_TYPE().equals("2")) {
                                payWay = "微信";
                            } else if (cardRecord.getCCR_PAY_TYPE().equals("3")) {
                                payWay = "支付宝";
                            } else if (cardRecord.getCCR_PAY_TYPE().equals("0")) {
                                payWay = "现金";
                            }
                            weiCaiOperationWaterAdapter.insertDataToEnd(new WeiCaiWaterInfo(payWay, s));
                        }
                    });

                    // 消费详情
                    List<TfConsumeDetailsRecord> detailsRecordList = new ArrayList<>();
                    TfConsumeDetailsRecord detailsRecord;
                    for (OperationMenu menu : menuListAdapter.getData()) {
                        detailsRecord = new TfConsumeDetailsRecord();
                        detailsRecord.setCDR_ID(getPrimaryValue());

                        detailsRecord.setCDR_MONEY(MyConstant.gFormat.format(menu.getTotalPrice()));

                        if (menu.getId() == MyConstant.CUSTOM_FIXED_PRICE_GOODS || menu.getId() == MyConstant.CUSTOM_PRICING_GOODS) {
                            detailsRecord.setCDR_NO(String.valueOf(mainActivity.pStoreConfig.getPRICING()));
                        } else {
                            detailsRecord.setCDR_NO(String.valueOf(menu.getId()));
                        }

                        detailsRecord.setCDR_NUMBER(String.valueOf(menu.getCount()));
                        detailsRecord.setCDR_TYPE("2");
                        if ("1".equals(cardRecord.getCCR_PAY_TYPE())) {
                            detailsRecord.setCDR_UNIT_PRICE(String.valueOf(menu.getUnitPrice()));
                        } else {
                            detailsRecord.setCDR_UNIT_PRICE(String.valueOf(menu.getUnitDisPrice()));
                        }

                        detailsRecord.setCLIENT_CODE(clientCode);
                        detailsRecord.setCOR_ID(orderNo);
                        detailsRecord.setCREATETIME(createTime);
                        detailsRecord.setISM_STATUS("0");
                        detailsRecord.setSTORE_CODE(storeCode);
                        detailsRecordList.add(detailsRecord);
                    }
                    // 消费订单
                    TfConsumeOrderRecord orderRecord = new TfConsumeOrderRecord();
                    orderRecord.setCLIENT_CODE(clientCode);
                    orderRecord.setCOR_AMOUNT(String.valueOf(totalMoney));
                    orderRecord.setCOR_ID(orderNo);
                    orderRecord.setCOR_TYPE("0");
                    orderRecord.setCREATETIME(createTime);
                    orderRecord.setISM_STATUS("0");
                    orderRecord.setSTORE_CODE(storeCode);
                    orderRecord.setMACHINE_NO(machineNo);
                    orderRecord.setUPDATETIME(MyUtil.dateConversion(System.currentTimeMillis()));

                    if (!MyUtil.networkState || payWay != PAY_MEMBER_CARD) {
                        payMsg = "";
                        Log.d("url", "saveOrder: saveOrder");
                        MyUtil.saveOrder(cardRecord, detailsRecordList, orderRecord);
                    } else {
                        // 更新
                        try {
                            payMsg = "";
                            Log.d("pay", "updateToServerOnline: ");
                            Log.d("url", "updateToServerOnline: updateToServerOnline");
                            MyUtil.updateToServerOnline(cardRecord, detailsRecordList, orderRecord);
                        } catch (Exception e) {
                            Log.d("url", "updateToServerOnline: Exception");
                            Log.d("url", "updateToServerOnline: e:" + e.getMessage());
                            MyUtil.saveOrder(cardRecord, detailsRecordList, orderRecord);
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("frost", e.toString());
                }
            }
        });
    }

    int width_dishes, height_dishes;

    /**
     * 打印
     */
    private void doPrinting() {
        try {
            org.json.JSONObject objPrint = listPrint.poll();
            if (objPrint != null) {
                Log.e("printing", "printing.....");
                if (mainActivity.pPrinter == null) {
                    mainActivity.pPrinter = new BuiltInPrinter(mainActivity);
                }
                mainActivity.pPrinter.powerOn();
                if (!mainActivity.pPrinter.havePaper()) {
                    mainActivity.pPrinter.powerOff();
                    return;
                }
                mainActivity.pPrinter.lock.acquire();

                try {
                    mainActivity.pPrinter.sendData("\n小票编号：");
                    mainActivity.pPrinter.sendData("\n" + objPrint.opt("order_no"));
                    mainActivity.pPrinter.sendData("\n\t\t\t" + objPrint.opt("storeName") + "\n");
                    mainActivity.pPrinter.sendData("\n设备号：" + objPrint.opt("machineNo"));
                    mainActivity.pPrinter.sendData("\n操作员：" + objPrint.opt("uname"));
                    mainActivity.pPrinter.sendData("\n日期：" + objPrint.opt("timestr"));
                    mainActivity.pPrinter.sendData("\r\n\r\n品名\t     数量     单价    金额");
                    mainActivity.pPrinter.sendData("\n--------------------------------");
                    mainActivity.pPrinter.sendData("" + objPrint.opt("str_details"));
                    mainActivity.pPrinter.sendData("\n--------------------------------");
                    mainActivity.pPrinter.sendData("\n\t谢谢惠顾，欢迎下次光临！");
                    mainActivity.pPrinter.sendData("\n\t此小票仅作消费依据");
                    mainActivity.pPrinter.lineFeed(4);
                } catch (SmartPosException e) {
                    e.printStackTrace();
                } finally {
                    //SystemClock.sleep(2000);
                    //mainActivity.pPrinter.powerOff();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        mTHREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                while (!mainActivity.isDestory) {
                    //打印
                    doPrinting();
                    SystemClock.sleep(300);
                }
            }
        });
        payMsg = "";
        payWay = 5;
        max_num = MyConstant.gSharedPre.getString(MyConstant.SP_MAX_SUM, "");
        isFix = MyConstant.gSharedPre.getBoolean(MyConstant.FIXED_PRICE_STATUS, false);
        menuListAdapter = new OperationMenuListAdapter(mContext, menuList);
        weiCaiOperationWaterAdapter = new WeiCaiOperationWaterAdapter(mContext, new ArrayList<WeiCaiWaterInfo>());
        general_list_water.setAdapter(weiCaiOperationWaterAdapter);
        lv_general.setAdapter(menuListAdapter);
        dishesList = new ArrayList<>();
        handler.sendEmptyMessage(COUNT_PERSON_NUMBER);
        general_type_ll.post(new Runnable() {
            @Override
            public void run() {
                typeList = new ArrayList<>();
                List<CommodityTypeRecord> commodityRecordList = MyApplication.getInstance().queryBuilder(CommodityTypeRecord.class)
                        .where(CommodityTypeRecordDao.Properties.SEQNO.notEq("0"))
                        .build()
                        .list();
                int width = general_type_ll.getWidth();
                int height = general_type_ll.getHeight();
                general_type.setLayoutManager(new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.HORIZONTAL));
//                general_type.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                GeneralTypeAdapter generalTypeAdapter = new GeneralTypeAdapter(width, height, commodityRecordList);
                generalTypeAdapter.setOnItemClickLitener(new GeneralTypeAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(int position, Object data) {
                        CommodityTypeRecord commodityTypeRecord = (CommodityTypeRecord) data;
                        String seqno = commodityTypeRecord.getSEQNO();
                        List<CommodityRecord> commodityRecordList;
                        dishesList.clear();
                        if (commodityTypeRecord.getP_ID().equals("0")) {
                            commodityRecordList = MyApplication.getInstance().queryBuilder(CommodityRecord.class)
                                    .where(CommodityRecordDao.Properties.USE_STATUS.eq("0"))
                                    .build()
                                    .list();
                        } else {
                            commodityRecordList = MyApplication.getInstance().queryBuilder(CommodityRecord.class)
                                    .where(CommodityRecordDao.Properties.USE_STATUS.eq("0"),
                                            CommodityRecordDao.Properties.CT_ID.eq(seqno))
                                    .build()
                                    .list();
                        }
                        dishesList.addAll(commodityRecordList);
                        OperationDishRecyclerAdapter operationDishRecyclerAdapter = new OperationDishRecyclerAdapter(dishesList, width_dishes, height_dishes);
                        operationDishRecyclerAdapter.setListener(new OnAdapterItemClickListener() {
                            @Override
                            public void onClick(int position, Object data) {
                                if (isPaying){
                                    return;
                                }
                                addDishInternal((CommodityRecord) data);
                                setNuMAndTotal();
                            }
                        });
                        recycler_general.setAdapter(operationDishRecyclerAdapter);
                    }
                });
                general_type.setAdapter(generalTypeAdapter);

            }
        });
        general_ll.post(new Runnable() {
            @Override
            public void run() {
                width_dishes = general_ll.getWidth();
                height_dishes = general_ll.getHeight();
                recycler_general.setLayoutManager(new GridLayoutManager(mContext, 4));
                List<CommodityRecord> commodityRecordList = MyApplication.getInstance().queryBuilder(CommodityRecord.class)
                        .where(CommodityRecordDao.Properties.USE_STATUS.eq("0"))
                        .build()
                        .list();
                dishesList.addAll(commodityRecordList);
                OperationDishRecyclerAdapter operationDishRecyclerAdapter = new OperationDishRecyclerAdapter(dishesList, width_dishes, height_dishes);
                operationDishRecyclerAdapter.setListener(new OnAdapterItemClickListener() {
                    @Override
                    public void onClick(int position, Object data) {
                        if (isPaying){
                            return;
                        }
                        addDishInternal((CommodityRecord) data);
                        setNuMAndTotal();
                    }
                });
                recycler_general.setAdapter(operationDishRecyclerAdapter);
            }
        });
    }

    private void setNuMAndTotal() {
        double totalPrices = 0;
        int totalNum = 0;
        for (OperationMenu menu2 : menuListAdapter.getData()) {
            totalPrices += menu2.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
            totalNum += menu2.getCount();
        }
        payMsg = MyConstant.gFormat.format(totalPrices);
        general_totalPrice.setText(payMsg);
        general_totalNum.setText(String.valueOf(totalNum));
        mainActivity.showPay(mainActivity, "card", "待支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
    }

    private void initKeyBoard() {
        inclueKeyBoard = findViewById(R.id.inclued_operation_number_keyboard_layout);
        Button btn0 = inclueKeyBoard.findViewById(R.id.btn_operation_key_0);
        Button btn1 = inclueKeyBoard.findViewById(R.id.btn_operation_key_1);
        Button btn2 = inclueKeyBoard.findViewById(R.id.btn_operation_key_2);
        Button btn3 = inclueKeyBoard.findViewById(R.id.btn_operation_key_3);
        Button btn4 = inclueKeyBoard.findViewById(R.id.btn_operation_key_4);
        Button btn5 = inclueKeyBoard.findViewById(R.id.btn_operation_key_5);
        Button btn6 = inclueKeyBoard.findViewById(R.id.btn_operation_key_6);
        Button btn7 = inclueKeyBoard.findViewById(R.id.btn_operation_key_7);
        Button btn8 = inclueKeyBoard.findViewById(R.id.btn_operation_key_8);
        Button btn9 = inclueKeyBoard.findViewById(R.id.btn_operation_key_9);
        Button btnClear = inclueKeyBoard.findViewById(R.id.btn_operation_key_clear);
        Button btnPoint = inclueKeyBoard.findViewById(R.id.btn_operation_key_point);
        inclueKeyBoard.findViewById(R.id.btn_operation_key_enter).setOnClickListener(this);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
    }

    private void initView() {
        general_refresh = (ImageButton) findViewById(R.id.general_refresh);
        general_type = (RecyclerView) findViewById(R.id.general_type);
        general_cash = (ImageButton) findViewById(R.id.general_cash);
        general_print = (ImageButton) findViewById(R.id.general_print);
        general_name = (TextView) findViewById(R.id.general_name);
        general_vip_no = (TextView) findViewById(R.id.general_vip_no);
        general_balance = (TextView) findViewById(R.id.general_balance);
        general_list_water = (ListView) findViewById(R.id.general_list_water);
        general_refresh.setOnClickListener(this);
        general_print.setOnClickListener(this);
        general_cash.setOnClickListener(this);
        general_totalNum = (TextView) findViewById(R.id.general_totalNum);
        general_totalPrice = (TextView) findViewById(R.id.general_totalPrice);
        handover_general = (Button) findViewById(R.id.handover_general);
        general_status = (TextView) findViewById(R.id.general_status);
        handover_general.setOnClickListener(this);
        lv_general = (ListView) findViewById(R.id.lv_general);
        recycler_general = (RecyclerView) findViewById(R.id.recycler_general);
        general_ll = (LinearLayout) findViewById(R.id.general_ll);
        general_num_people = (TextView) findViewById(R.id.general_num_people);
//        general_total_people = (TextView) findViewById(R.id.general_total_people);
        general_type_ll = (LinearLayout) findViewById(R.id.general_type_ll);
        et_put = (EditText) findViewById(R.id.et_put);
        et_put.requestFocus();
        et_put.setInputType(InputType.TYPE_NULL);
        initKeyBoard();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.handover_general:
                if (recycler_general.getVisibility() == View.VISIBLE) {
                    recycler_general.setVisibility(View.GONE);
                    inclueKeyBoard.setVisibility(View.VISIBLE);
                    general_type.setVisibility(View.GONE);
                } else {
                    recycler_general.setVisibility(View.VISIBLE);
                    inclueKeyBoard.setVisibility(View.GONE);
                    general_type.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_operation_key_point:
                inputEdit(".");
                break;
            case R.id.btn_operation_key_0:
                inputEdit("0");
                break;
            case R.id.btn_operation_key_1:
                inputEdit("1");
                break;
            case R.id.btn_operation_key_2:
                inputEdit("2");
                break;
            case R.id.btn_operation_key_3:
                inputEdit("3");
                break;
            case R.id.btn_operation_key_4:
                inputEdit("4");
                break;
            case R.id.btn_operation_key_5:
                inputEdit("5");
                break;
            case R.id.btn_operation_key_6:
                inputEdit("6");
                break;
            case R.id.btn_operation_key_7:
                inputEdit("7");
                break;
            case R.id.btn_operation_key_8:
                inputEdit("8");
                break;
            case R.id.btn_operation_key_9:
                inputEdit("9");
                break;
            case R.id.btn_operation_key_clear:
                if (isInptEditEmpty()) {
                    et_put.setText("");
                }
                break;
            case R.id.btn_operation_key_enter:
                handler.sendEmptyMessage(SETTLEMENT);
                break;
            case R.id.general_refresh:
                generalRefresh();
                break;
            case R.id.general_cash:
                payByCash();
                break;
            case R.id.general_print:
                if (!"".equals(general_name.getText().toString())) {
                    printerReceipt(memberInfo);
                } else {
                    printerReceipt(null);
                }
//                setupPrinter();
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WEIXIN_CODE:
                    showStatusMessage("服务异常");
                    break;
                case REFRESH_CODE:
                    et_put.setText("");
                    showStatusMessage("请刷新二维码");
                case CARD_STATUS_CANCELEDS:
                    showStatusMessage("注销卡");
                    break;
                case CARD_STATUS_LOSTS:
                    showStatusMessage("挂失卡");
                    break;
                case SETTLEMENT:
//                    if (!"请刷卡".equals(general_status.getText().toString()) && !"准备下单".equals(general_status.getText().toString())) {
//                        generalRefresh();
//                    }
                    curOrderNo = MyUtil.getPrimaryValue();
                    if (isNumber(et_put.getText().toString().trim())) {
                        addCustomDish(MyConstant.CUSTOM_PRICING_GOODS, "");
                        et_put.setText("");
                        setNuMAndTotal();
                    } else {
                        et_put.setText("");
                    }
                    break;
                case PAYMENTING:
                    showStatusMessage("支付中");
                    break;
                case UPDATE_PERSON_NUMBER:
                    // 当天账单总数
//                    general_total_people.setText(String.valueOf(mTotalPersonCount) );
                    general_num_people.setText(String.valueOf(mTotalPersonCount));
                    break;
                case COUNT_PERSON_NUMBER:
                    try {
                        List<TfConsumeCardRecord> list = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
                                        TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"),
                                        TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"))
                                .build()
                                .list();
                        if (!list.isEmpty() && list != null) {
                            mTotalPersonCount = list.size();
                            // 当餐人数
                            int mealcount = 0;
                            String curMealtimes = MyUtil.getCurMealTimes();
                            mCurMealType = curMealtimes;
                            for (TfConsumeCardRecord record : list) {
                                if (record.getMT_ID() == null) {
                                    continue;
                                }
                                if (record.getMT_ID().equals(curMealtimes)) {
                                    mealcount += 1;
                                }
                                mCurMealPersonCount = mealcount;
                            }
                        }
                        // 当天账单总数
//                        general_total_people.setText(String.valueOf(mTotalPersonCount) );
                        general_num_people.setText(String.valueOf(mTotalPersonCount));
                        Log.e("pay", "UPDATE_PERSON_NUMBER mTotalPersonCount:" + mTotalPersonCount + " mCurMealPersonCount:" + mCurMealPersonCount);
                    } catch (Exception e) {
                        Log.e("CrashHandler", "UPDATE_PERSON_NUMBER:" + e.getMessage());
                    }
                    break;
            }
        }
    };

    private void isFixStatus() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                curOrderNo = MyUtil.getPrimaryValue();
                general_name.setText("");
                general_vip_no.setText("");
                general_balance.setText("");
                showStatusMessage("请刷卡");
                playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
                setNuMAndTotal();
            }
        });

    }

    public boolean isNumber(String str) {
        try {
            Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,1})?$"); // 判断小数点后1位的数字的正则表达式
            Matcher match = pattern.matcher(str);
            if (Double.parseDouble(str) == 0.00 || !match.matches()) {
                return false;
            } else {
                if (!TextUtils.isEmpty(max_num) && Double.parseDouble(str) > Double.parseDouble(max_num)) {
                    return false;
                } else {
                    if (Double.parseDouble(str) > 100000.00) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void generalRefresh() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isPaying) {
                    return;
                } else {
                    payMsg = "";
                    payWay = 5;
                    memberInfo = null;
                    menuListAdapter.clearData();
                    general_totalNum.setText("");
                    general_totalPrice.setText("");
                    general_name.setText("");
                    general_balance.setText("");
                    et_put.setText("");
                    general_vip_no.setText("");
                    showStatusMessage("准备下单");
                }
            }
        });
    }

    /**
     * 添加自定义价格的定价商品
     *
     * @param ci_id 菜品id
     */
    private void addCustomDish(long ci_id, String price) {
        if (price.isEmpty()) {
            price = et_put.getText().toString();
        }
        CommodityRecord record = new CommodityRecord();
        record.setCI_PRICE(price);
        record.setCI_ID(ci_id);
        record.setCI_NAME("定价商品");
        addDishInternal(record);
    }

    public boolean isInptEditEmpty() {
        if (et_put != null && !et_put.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void inputEdit(String content) {
        if (et_put != null) {
            et_put.requestFocus();
            int index = et_put.getSelectionStart();
            Editable editable = et_put.getText();
            editable.insert(index, content);
            String curContent[] = editable.toString().split("\\.");
            if (curContent.length >= 2) {
                if (curContent[1].length() >= 2) {
                    Log.d("frost", "length error");
//                    showMessage("提示", "请输入正确金额！");
                    return;
                }
            }
            et_put.setText(editable.toString());
            et_put.setSelection(index + content.length());
        }
    }

    /**
     * 现金支付
     */
    private void payByCash() {
        if (isPaying || "".equals(payMsg)) {
            return;
        }
        memberInfo = null;
        payWay = PAY_CASH;
        // 更新人数区记录
        mTotalPersonCount += 1;
        mCurMealPersonCount += 1;
        curOrderNo = MyUtil.getPrimaryValue();
        handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);
        // 点击现金快捷按钮，即表示买单，创建对应的账单信息
        String count = general_totalPrice.getText().toString();
        int dis_count = MyUtil.getDisCountForAll();//全员折扣
        if (dis_count < 100) {//
            menuListAdapter.calDisCount(dis_count);
            count = MyUtil.calculateDisCountPrice(count, dis_count);
            Log.i("pay", "payByCash->count   折扣后的值为->" + count);
        }
        createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(count))), 0, "0", "0", "0", menuListAdapter.getData().size() + "", 1);
        String s = general_totalPrice.getText().toString();
        mainActivity.showPay(mainActivity, "card", "支付成功 " + s, "", "", "");
        playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
        showStatusMessage("支付成功");
    }

    /**
     * 把菜单中选中的菜品添加到左侧计算菜单价格中
     */
    private void addDishInternal(CommodityRecord dishes) {
        if (!"请刷卡".equals(general_status.getText().toString())) {
            generalRefresh();
        }
        showStatusMessage("请刷卡");
        OperationMenu menu = new OperationMenu();
        menu.setUnitPrice(Float.parseFloat(dishes.getCI_PRICE().isEmpty() ? "0" : dishes.getCI_PRICE()));
        menu.setId(dishes.getCI_ID());
        menu.setDishNmae(dishes.getCI_NAME());

        OperationMenu oldMenu = isContainId(dishes.getCI_ID());

        // 如果所点菜名已存在，且不是固价/定价商品， +1
        if (oldMenu != null
                && oldMenu.getId() != MyConstant.CUSTOM_PRICING_GOODS
                && oldMenu.getId() != MyConstant.CUSTOM_FIXED_PRICE_GOODS) {
            oldMenu.setCount(oldMenu.getCount() + 1);
            oldMenu.setTotalPrice(oldMenu.getCount() * oldMenu.getUnitDisPrice());
            menuListAdapter.notifyDataSetChanged();
            playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
            return;
        } else {
            menu.setCount(1);
        }
        menuListAdapter.insertDataToEnd(menu);
        menuListAdapter.calDisCount(100);
        Log.e("url", "SP_MEDIA_PLEASE_SET_CARD2");
        playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
    }

    private void showStatusMessage(String str) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                general_status.setText(str);
            }
        });
    }

    public OperationMenu isContainId(long id) {
        for (OperationMenu menu : menuListAdapter.getData()) {
            if (menu.getId() == (id)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * 音频播放
     *
     * @param type
     */
    public void playVoice(int type) {
        int resId = 0;
        switch (type) {
            case MyConstant.SP_MEDIA_CONSUME_SUCCESS:
                resId = R.raw.consumesuccess;
                break;
            case MyConstant.SP_MEDIA_MONEY_NOT_ENOUGH:
                resId = R.raw.moneynotenough;
                break;
            case MyConstant.SP_MEDIA_PLEASE_SET_CARD:
                resId = R.raw.pleasesetcard;
                break;
            case MyConstant.SP_MEDIA_NO_EFFECT_CARD:
                resId = R.raw.noeffectcard;
                break;
            case MyConstant.SP_MEDIA_ILLEGAL_CARD:
                resId = R.raw.illegalcard;
                break;
            case MyConstant.SP_CANCEL_CARD:
                // 注销卡
                resId = R.raw.canclecard;
                break;
            case MyConstant.SP_LOST_CARD:
                // 挂失
                resId = R.raw.lostcard;
                break;

        }
        try {

            mPlayer = MediaPlayer.create(mContext, resId);
            //FIXME: mPlayer is null if wav is mono(only support stereo)
            if (mPlayer != null) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        mp.release();
                        mp = null;
//                        //支付成功-----发送 refresh 命令
                        if (type == MyConstant.SP_MEDIA_CONSUME_SUCCESS && !isPaying) {
                            if (isFix) {
                                isFixStatus();
                            }
                        }
                    }
                });
                mPlayer.start();
            }
        } catch (Exception e) {
            Log.e("frost", "playVoice --" + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutor.shutdown();
        mTHREAD_POOL.shutdown();
    }
}
