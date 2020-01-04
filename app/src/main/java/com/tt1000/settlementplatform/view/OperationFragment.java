package com.tt1000.settlementplatform.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.paydevice.smartpos.sdk.SmartPosException;
import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.OnAdapterItemClickListener;
import com.tt1000.settlementplatform.adapter.OperationCashListAdapter;
import com.tt1000.settlementplatform.adapter.OperationDishRecyclerAdapter;
import com.tt1000.settlementplatform.adapter.OperationMenuListAdapter;
import com.tt1000.settlementplatform.adapter.OperationWaterAdapter;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.base.WrapAdapter;
import com.tt1000.settlementplatform.bean.BaseBean;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityRecordDao;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecord;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecordDao;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.TfCardInfo;
import com.tt1000.settlementplatform.bean.member.TfCardInfoDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecordDao;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecord;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMealTimesDao;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecord;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.TfMemberInfoDao;
import com.tt1000.settlementplatform.bean.operation.OperationMenu;
import com.tt1000.settlementplatform.bean.operation.WaterInfo;
import com.tt1000.settlementplatform.bean.pay.InlineUpdateInfo;
import com.tt1000.settlementplatform.bean.result_info.OnlineUpdateResultInfo;
import com.tt1000.settlementplatform.bean.result_info.QueryCardResultInfo;
import com.tt1000.settlementplatform.bean.result_info.ResultBaseBean;
import com.tt1000.settlementplatform.bean.result_info.UpdateResultInfo;
import com.tt1000.settlementplatform.feature.BuiltInPrinter;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.zxing.camera.CameraManager;
import com.tt1000.settlementplatform.zxing.decoding.CaptureActivityHandler;
import com.tt1000.settlementplatform.zxing.view.ViewfinderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static com.tt1000.settlementplatform.utils.MyConstant.TAG;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;
import static com.tt1000.settlementplatform.utils.MyUtil.getPrimaryValue;

@SuppressLint("ValidFragment")
public class OperationFragment extends BaseFragment implements View.OnClickListener, SurfaceHolder.Callback {
    //清除扫描的支付宝/微信帐号
    public static final int CLEAR_SCANNER_NUM = 0x111;
    public static final int MODIFY_PAY_INFO = 111;
    public static final int JUDGE_SETUP_SCAN = 222;
    public static final int NOTIFY_MSG_CHANGE = 333;
    public static final int INLINE_UPDATE_ORDER_INFO = 444;
    public static final int REFRESH = 555;
    public static final int NOTIFY_FIXED_PRICE_ADD = 666;
    public static final int INSERT_TO_WATER = 777;
    public static final int UPDATE_PERSON_NUMBER = 888;

    private static final int PAY_CASH = 0;//
    private static final int PAY_MEMBER_CARD = 1;
    private static final int PAY_WECHAT = 2;
    private static final int PAY_ALIPAY = 3;
    private static final int PAY_BANK = 4;
    // 已点菜单
    private ListView lsMenu;

    // 流水
    private ListView lsWater;
    // 菜单选项
    private ListView lsCash;
    // 挂单列表
    private List<OperationMenu> entryOrdersList;
    private RecyclerView recyclerCommodityRecord;
    // 数量
    private TextView txPayCount;
    // 总价
    private TextView txPayTotalPrice;
    private TextView txPaidIn;
    private TextView txChange;
    private TextView txShowPaidIn;
    private TextView txShowChange;
    private TextView txMemberName;
    private TextView txMemberPhone;
    private TextView txMemberBalance;
    // 人数区
    private TextView txCurMealPerson;
    private TextView txTodayPerson;
    // 支付提示
    private TextView txMsgNotify;
    // 固价
    private TextView txFixedPrice;

    // 切换收钱布局
    private LinearLayout llCash, llMemberCard;
    private ImageButton btnSwitch;
    private ImageButton btnImputedPrices;
    private ImageButton btnRefresh;
    private ImageButton btnPrint;
    private Button btnPgUp;
    private Button btnPgDown;
    //    private Button btnFixed;//固价模式
    private View inclueKeyBoard;
    private EditText etInputDishesName;

    private List<OperationMenu> menuList;
    private List<CommodityRecord> dishesList;
    private List<String> cashList;
    /*
    记录当前显示的菜单列表位置
    没按一次下一页，则显示的数据改变
    position + 9
     */
    private int curListPosition;
    // nfc 识别会导致 调用onResume函数
    private boolean hasResume = false;
    private boolean isFirst = true;
    // 当前小票编号，在生成订单后被赋值
    public static String curOrderNo = "";
    public boolean isduringPayment = false;
    public static String isduringOrderNo = "";
    private int pay_result_select = 1000;
    // 是否已经打印
    private boolean isPrint = false;
    private ConcurrentLinkedQueue<org.json.JSONObject> listPrint = new ConcurrentLinkedQueue<>();
    // 是否已支付
    private boolean isPayment = false;
    // 在线更新是否启动
    private boolean onlineSync = false;
    // 扫描是否开启
    private boolean isOpenScan = false;
    // 支付方式
    private int payWay;
    // 支付宝还是微信
    private String payName;
    // 实体卡号，不是会员卡号
    private String cardId;
    // 账户余额
    private float accoutBalance;
    // 固价值
    public static float mFixedPrice = 0;

    public long start_time = 0;
    public long scan_start_time = 0;

    public String barcode = "";
    public static boolean syncing = false;

    private MediaPlayer mPlayer;
    private OperationMenuListAdapter menuListAdapter;
    private OperationDishRecyclerAdapter dishesRecyclerAdapter;
    private OperationWaterAdapter waterAdapter;

    // 人数区
    private int mTotalPersonCount;
    private int mCurMealPersonCount;
    private String mCurMealType = "";

    public ThreadPoolExecutor mTHREAD_POOL = new ThreadPoolExecutor(6,
            14,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    public ScheduledExecutorService mExecutor = new ScheduledThreadPoolExecutor(4);

    public static OperationHandler gOperationHandler;

    public class OperationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MODIFY_PAY_INFO:
                    modifyPayInfo();
                    break;
                case JUDGE_SETUP_SCAN:
//                    Log.e("look","JUDGE_SETUP_SCAN");
                    // Boolean stopScan = (System.currentTimeMillis() - scan_start_time < 30 * 1000);
                    //debug dels
                    float totalPrice = Float.parseFloat(txPayTotalPrice.getText().toString());
                    if (totalPrice > 0 && !isPayment) {
                        setupScan();
                    } else {
                        stopScan();
                    }
                    break;
                case NOTIFY_MSG_CHANGE:
                    String notify = msg.obj == null ? " 空" : msg.obj.toString();
                    txMsgNotify.setText(notify);
                    break;

                case MyConstant.OPERATION_UPDATE_PERSON_INFO:
                    try {
                        String curMealtimes = MyUtil.getCurMealTimes();
                        if (mCurMealType == null || !mCurMealType.equals(curMealtimes)) {
                            Log.e("pay", "curMealtimes != mCurMealType curMealtimes:" + curMealtimes + " mCurMealType:" + mCurMealType);
                            gOperationHandler.obtainMessage(UPDATE_PERSON_NUMBER).sendToTarget();
                        }
                        // 当天账单总数
                        txTodayPerson.setText("" + mTotalPersonCount);
                        txCurMealPerson.setText("" + mCurMealPersonCount);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case REFRESH:
                    refresh();
                    break;
                case NOTIFY_FIXED_PRICE_ADD:
                    showMessage("", "请支付");
                    Log.e("url", "SP_MEDIA_PLEASE_SET_CARD1:" + isPayment);
                    playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);

                    float totalPrices = 0;
                    for (OperationMenu menu2 : menuListAdapter.getData()) {
                        totalPrices += menu2.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                    }
                    //mainActivity.showPay("wait","待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)));
                    mainActivity.showPay(mainActivity, "card", "待支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
                    break;
                case INSERT_TO_WATER:
                    WaterInfo info = (WaterInfo) msg.obj;
                    waterAdapter.insertDataToEnd(info);
                    break;
                case UPDATE_PERSON_NUMBER:
                    try {
                        List<TfConsumeCardRecord> list = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
                                        TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"))
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
                        txTodayPerson.setText("" + mTotalPersonCount);
                        txCurMealPerson.setText("" + mCurMealPersonCount);
                        Log.e("pay", "UPDATE_PERSON_NUMBER mTotalPersonCount:" + mTotalPersonCount + " mCurMealPersonCount:" + mCurMealPersonCount);
                    } catch (Exception e) {
                        Log.e("CrashHandler", "UPDATE_PERSON_NUMBER:" + e.getMessage());
                    }
                    break;
                case CLEAR_SCANNER_NUM:
                    etInputDishesName.setText("");
                    break;
            }
        }
    }

    public OperationFragment() {
        super();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_operation;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mainActivity.pPrinter != null) {
            mainActivity.pPrinter.powerOff();
        }
    }

    @Override
    public void onAttach(final Context context) {
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
                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    Log.d("frost", "tag-----" + tag);
                    // 能获取到卡号，且未支付 我尼玛以前吃鸡还开过直播，不过没啥人就没播了
                    if (tag != null && tag.getId() != null) {
                        String smartCardId = MyUtil.ByteArrayToHexString(tag.getId());
                        if (TextUtils.isEmpty(smartCardId)) {
                            showMessage("会员卡", "非法卡");
                            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                        } else {
                            long convertId = computeMemberId(smartCardId);
                            if (convertId == 0) {
                                showMessage("提示", "非法卡");
                                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                return;
                            }
                            int diff = 10 - String.valueOf(convertId).length();
                            cardId = String.valueOf(convertId);
                            // 不足10位补0
                            if (diff >= 0) {
                                for (int i = 0; i < diff; i++) {
                                    cardId = ("0" + cardId);
                                }
                                /**
                                 * 测试的时候使用
                                 * 正常卡：1852084081
                                 * 挂失卡：1852084082
                                 * 注销卡：1852084083
                                 * 会员注销卡：1852084084
                                 */
//                                getMemberCardBalance("1852084081");//正常卡
//                                getMemberCardBalance("1852084082");//挂失卡
//                                getMemberCardBalance("1852084083");//注销卡
//                                getMemberCardBalance("1852084084");//会员注销卡
                                // 最终得到去数据库查询的IC_ID
                                getMemberCardBalance(String.valueOf(cardId));
                            } else if (diff < 0) {
                                showMessage("会员卡", "非法卡");
                                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                return;
                            }
                        }
                    }
                } catch (Exception ex) {
                    Log.e("CrashHandler", "onNewIntent..." + ex.getMessage());
                }

            }
        });

        //TODO: add by hansen,need test for barcoder scanner
        ((MainActivity) context).setBarcodeScannerCallback(new MainActivity.OnBarcodeScannerCallback() {
            public void onKey(KeyEvent event) {
                barcode += (char) event.getUnicodeChar();
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.d(TAG, "barcode:" + barcode.trim());
                    handleBarcodeScanner(barcode.trim());
                    barcode = "";
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gOperationHandler = new OperationHandler();
        checkPermission();
        CameraManager.init(getContext());
        initData();
        initView();

        init();

        //openScan();
        initTimer();

        Log.e("url", "onActivityCreated");
        curOrderNo = MyUtil.getPrimaryValue();
        Log.e("url", "curOrderNo:" + curOrderNo);
    }

    private void initTimer() {
        mExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    syncDataToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    payResultSelect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    OperationFragment.gOperationHandler.obtainMessage(OperationFragment.JUDGE_SETUP_SCAN).sendToTarget();
                    OperationFragment.gOperationHandler.obtainMessage(OperationFragment.MODIFY_PAY_INFO).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);


        mExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Log.e("url", "dishesRecyclerAdapter.notifyDataSetChanged()");
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            readData();
                            dishesRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10, TimeUnit.SECONDS);


//        mTHREAD_POOL.execute(new Runnable() {
//            @Override
//            public void run() {
//                while (!mainActivity.isDestory) {
//                    if (start_time != 0 && System.currentTimeMillis() - start_time >= 20000) {
//                        start_time = 0;
//                        if(playCardError){
//                            return;
//                        }
//                        if(isduringOrderNo!=null && isduringOrderNo.length()>0 && isduringOrderNo.equals(curOrderNo))
//                        {
//                            showMessage("","订单正在支付中");
//                            return;
//                        }
//                        if (isduringPayment) {
//                            showMessage("", "订单正在支付中");
//                            return;
//                        }
//                        mainActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.e("url","准备下单1");
//                                if (mFixedPrice <= 0) {
//                                    if(isAdded()){
//                                       showMessage("", getString(R.string.default_notify));
//                                    }
//                                    mainActivity.showPay(mainActivity,"card","待支付 0.0","","","");
//                                }
//                                txShowPaidIn.setVisibility(View.INVISIBLE);
//                                txShowChange.setVisibility(View.INVISIBLE);
//                                txPaidIn.setText("");
//                                txChange.setText("");
//                                if (llCash.getVisibility() == View.GONE) {
//                                    llCash.setVisibility(View.VISIBLE);
//                                    hiddenMemberCardInfo();
//                                }
//                                if(isPayment){
//                                    isPayment = false;
//                                    menuListAdapter.clearData();
//                                    txPayCount.setText("0");
//                                    txPayTotalPrice.setText("0");
//                                    etInputDishesName.setText("");
//                                }
//                            }
//                        });
//
//                    }
//                    SystemClock.sleep(500);
//                }
//            }
//        });

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
    }

    /**
     * 隐藏会员卡的结算内容部分
     */
    private void hiddenMemberCardInfo() {
        llMemberCard.setVisibility(View.GONE);
        txMemberName.setText("");
        txMemberPhone.setText("");
        txMemberBalance.setText("");
    }

    private void initData() {
        entryOrdersList = new ArrayList<>();
        menuList = new ArrayList<>();
        dishesList = new ArrayList<>();
        cashList = new ArrayList<>();
        readData();
        //<editor-fold desc="money">
        cashList.add("50");
        cashList.add("100");
        //</editor-fold>
        Log.e("pay", "initData...UPDATE_PERSON_NUMBER");
        gOperationHandler.obtainMessage(UPDATE_PERSON_NUMBER).sendToTarget();
    }

    /**
     * USE_STATUS：0上架；1：已下架
     */
    private void readData() {
        dishesList.clear();
        List<CommodityRecord> commodityRecordList = MyApplication.getInstance().queryBuilder(CommodityRecord.class)
                .where(CommodityRecordDao.Properties.USE_STATUS.eq("0"))
                .build()
                .list();
//        // 商品表
//        for (CommodityRecord record : commodityRecordList) {
//            Log.e("url","dishesList:" + record.getCI_NAME());
//        }
        dishesList.addAll(commodityRecordList);
    }

    private void initView() {
        lsMenu = (ListView) findViewById(R.id.ls_operation_menu);
        lsCash = (ListView) findViewById(R.id.ls_operation_add_cash);
        lsWater = (ListView) findViewById(R.id.ls_operation_water);
        recyclerCommodityRecord = (RecyclerView) findViewById(R.id.recycler_operation_order_dishes);
        txPaidIn = (TextView) findViewById(R.id.tx_operation_paid_in);
        txChange = (TextView) findViewById(R.id.tx_operation_change);
        txShowPaidIn = (TextView) findViewById(R.id.tx_operation_show_paidin);
        txShowChange = (TextView) findViewById(R.id.tx_operation_show_change);
        txTodayPerson = (TextView) findViewById(R.id.tx_operation_person_today);
        txCurMealPerson = (TextView) findViewById(R.id.tx_operation_person_mealtime);
//        txEntryCount = (TextView) findViewById(R.id.tx_entry_count);
//        txEntryTotalPrice = (TextView) findViewById(R.id.tx_entry_total_price);
        txPayCount = (TextView) findViewById(R.id.tx_operation_pay_count);
        txPayTotalPrice = (TextView) findViewById(R.id.tx_operation_pay_total_price);
        txMemberName = (TextView) findViewById(R.id.tx_member_name);
        txMemberPhone = (TextView) findViewById(R.id.tx_member_id);
        txMemberBalance = (TextView) findViewById(R.id.tx_member_balance);
        txMsgNotify = (TextView) findViewById(R.id.tx_message_notify);
        txFixedPrice = (TextView) findViewById(R.id.tx_operation_fixed_price);
        btnSwitch = (ImageButton) findViewById(R.id.btn_operation_switch_page);
        btnImputedPrices = (ImageButton) findViewById(R.id.btn_operatin_listview_pay_cash);
        btnRefresh = (ImageButton) findViewById(R.id.btn_opreation_refresh);
        btnPgUp = (Button) findViewById(R.id.btn_operation_page_up);
        btnPgDown = (Button) findViewById(R.id.btn_operation_page_down);
//        btnFixed = (Button) findViewById(R.id.btn_operation_fixed_price);
        findViewById(R.id.btn_operation_fixed_price).setOnClickListener(this);
        inclueKeyBoard = findViewById(R.id.inclued_operation_number_keyboard_layout);
        etInputDishesName = (EditText) findViewById(R.id.et_operation_input_dishes_name);
//        btnPrint = (ImageButton) findViewById(R.id.btn_opreation_print);
//        opEntryOrder = (OperationButton) findViewById(R.id.op_entry_orders);
//        opCombinedOrder = (OperationButton) findViewById(R.id.op_combined_bill);
//        opSingleElimination = (OperationButton) findViewById(R.id.op_single_elimination);
        llCash = (LinearLayout) findViewById(R.id.ll_cash);
        llMemberCard = (LinearLayout) findViewById(R.id.ll_membercard);

        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        initKeyBoard();
        btnPgUp.setOnClickListener(this);
        btnPgDown.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnImputedPrices.setOnClickListener(this);
//        btnFixed.setOnClickListener(this);
//        btnPrint.setOnClickListener(this);
//        opEntryOrder.setOnClickListener(this);
//        opCombinedOrder.setOnClickListener(this);
//        opSingleElimination.setOnClickListener(this);
        //厂家给的代码中是有的，2019-07-13注销，chenxiaoyan
//        etInputDishesName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        etInputDishesName.setInputType(0);
        txPaidIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 自动计算找零,且是折扣后的价格
                if (txPaidIn.getText().toString().isEmpty()) {
                    return;
                }
                String actualCash = getActualCash(txPayTotalPrice.getText().toString());
                float total = Float.parseFloat(actualCash);
//                float total = Float.parseFloat((txPayTotalPrice.getText().toString()));
                float paidIn = cashListNum;
                /*
                if (payWay == PAY_MEMBER_CARD) {
                    total = Float.parseFloat(calculateDisCountPrice(String.valueOf(total), 0));
                    total = Float.parseFloat(calculateDisCountPrice(String.valueOf(total), 1));
                } else {
                    total = Float.parseFloat(calculateDisCountPrice(String.valueOf(total), 0));
                }
                */

//                float totalPrices = 0;
//                for (OperationMenu menu : menuListAdapter.getData()) {
//                    totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
//                }
//                txPaidIn.setText(MyConstant.gFormat.format(totalPrices) + "");

                txChange.setText(paidIn - total < 0 ? "余额不足" : MyConstant.gFormat.format(paidIn - total));
            }
        });

        gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();
    }

    private void initKeyBoard() {
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
        Button btnBackSpace = inclueKeyBoard.findViewById(R.id.btn_operation_key_backspace);
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
        btnBackSpace.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
    }

    public float cashListNum = 0f;

    private void init() {
        waterAdapter = new OperationWaterAdapter(mContext, new ArrayList<WaterInfo>());
        menuListAdapter = new OperationMenuListAdapter(mContext, menuList);
        lsWater.setAdapter(waterAdapter);
        lsMenu.setAdapter(menuListAdapter);
        recyclerCommodityRecord.setLayoutManager(new GridLayoutManager(mContext, 3));
        final OperationCashListAdapter cashListAdapter = new OperationCashListAdapter(mContext, cashList);
        final LinearLayout dishLayout = (LinearLayout) findViewById(R.id.ll_operation_dish);
        cashListAdapter.setListener(new WrapAdapter.onAdapterItemClickListener() {
            @Override
            public void onClick(int position, List<?> data) {
                // that cannot pay again if the payment is success

                if (playWxAliError) {
                    showMessage("", "请重新刷新");
                    mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
                    return;
                }

                if (isPayMsg) {
                    showMessage("", "该单已支付");
                    mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
                    return;
                }

                if (isPayment) {
                    showMessage("", "该单已支付");
                    //mainActivity.showPay("wait","该单已支付");
                    mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
                    return;
                }

                if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                    showMessage("", "正在支付中");
                    return;
                }

                if (isPayment) {
                    return;
                }

                float cash = Float.parseFloat(cashListAdapter.getData().get(position));
                String actualCash = getActualCash(txPayTotalPrice.getText().toString());
                float total = Float.parseFloat(actualCash);
                Log.i(TAG, "onClick: cash->" + cash + "   total->" + total);
                if (total > cash || total == 0f || menuListAdapter.getData().isEmpty()) {
                    showMessage("提示：", "请选择相符的金额！");
                } else {
                    showMessage("", "现金支付中");
                    txChange.setVisibility(View.VISIBLE);
                    txShowPaidIn.setVisibility(View.VISIBLE);

                    Log.e("url", "现金支付中:");
                    Log.e("url", "cash:" + cash);
                    Log.e("url", "actualCash:" + actualCash);

                    txShowChange.setVisibility(View.VISIBLE);


                    cashListNum = cash;


                    // txPaidIn.setText(cash + "");

                    payByCash();
                    showCashInfo();
                    showMessage("提示", "支付成功");
                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                    float totalPrices = 0;
                    for (OperationMenu menu : menuListAdapter.getData()) {
                        totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                    }
                    txPaidIn.setText(MyConstant.gFormat.format(totalPrices) + "");
                    printerReceipt(null);
                }

            }
        });

        dishLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = dishLayout.getWidth();
                int height = dishLayout.getHeight();
                dishesRecyclerAdapter = new OperationDishRecyclerAdapter(dishesList, width, height);
                dishesRecyclerAdapter.setListener(new OnAdapterItemClickListener() {
                    @Override
                    public void onClick(int position, Object data) {
                        Log.e("pay", "dishesRecyclerAdapter.setListener...");

                        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                            showMessage("", "正在支付中");
                        } else {
                            addDishInternal((CommodityRecord) data);
                            float totalPrices = 0;
                            for (OperationMenu menu2 : menuListAdapter.getData()) {
                                totalPrices += menu2.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                            }
                            //mainActivity.showPay("wait","待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)));
                            mainActivity.showPay(mainActivity, "card", "待支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
                        }
                    }
                });
                recyclerCommodityRecord.setAdapter(dishesRecyclerAdapter);
                cashListAdapter.setHeight(lsCash.getHeight() / 2);
                lsCash.setAdapter(cashListAdapter);
            }
        });
        //mainActivity.showPay("wait","待支付 " + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))));
        mainActivity.showPay(mainActivity, "card", "待支付 " + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), "", "", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_operation_switch_page:
                if (inclueKeyBoard.getVisibility() == View.VISIBLE) {
                    btnSwitch.setImageResource(R.drawable.goods);
                    inclueKeyBoard.setVisibility(View.GONE);
                    readData();
                    dishesRecyclerAdapter.notifyDataSetChanged();
                    recyclerCommodityRecord.setVisibility(View.VISIBLE);
                } else {
                    btnSwitch.setImageResource(R.drawable.keyboard);
                    inclueKeyBoard.setVisibility(View.VISIBLE);
                    recyclerCommodityRecord.setVisibility(View.GONE);
                }
                break;
            // 现金
            case R.id.btn_operatin_listview_pay_cash:
                // 已支付、0元订单，空菜单

                if (playWxAliError) {
                    showMessage("", "请重新刷新");
                    mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
                    return;
                }

                if (isPayMsg) {
                    showMessage("", "该单已支付");
                    mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
                    return;
                }

                if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                    showMessage("", "正在支付中");
                    return;
                }

                if (isPayment) {
                    showMessage("", "该单已支付");
                    //mainActivity.showPay("wait","该单已支付");
                    mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
                    return;
                }

                if (isPayment
                        || Float.parseFloat(txPayTotalPrice.getText().toString()) <= 0
                        || menuListAdapter.getData().isEmpty()) {
                    return;
                }
                showMessage("", "现金支付中");
                payByCash();
                txChange.setVisibility(View.INVISIBLE);
                txShowPaidIn.setVisibility(View.VISIBLE);
                //txPaidIn.setText(calculateDisCountPrice(txPayTotalPrice.getText().toString(), 0));

                float totalPrices2 = 0;
                for (OperationMenu menu : menuListAdapter.getData()) {
                    totalPrices2 += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                }
                txPaidIn.setText(MyConstant.gFormat.format(totalPrices2) + "");
                //txPaidIn.setText(txPayTotalPrice.getText().toString());
                showMessage("", "支付成功");
                playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                printerReceipt(null);
                //gOperationHandler.sendEmptyMessageDelayed(REFRESH,600);
                break;
            case R.id.btn_opreation_refresh:
                // 刷新
                refreshOn();
                MainActivity mainActivity = (MainActivity) getActivity();
                //mainActivity.showPay("wait","待支付 0.0");
                mainActivity.showPay(mainActivity, "card", "待支付 0.0", "", "", "");

                break;
//            case R.id.btn_opreation_print:
//                // 打印
//                setupPrinter();
//                break;
            case R.id.btn_operation_page_up:
                if (curListPosition == 0) {
                    return;
                }
                curListPosition -= 1;
                dishesRecyclerAdapter.setListData(reflushDishList(curListPosition));
                break;
            case R.id.btn_operation_page_down:
                int size = dishesList.size() / 9;
                if (dishesList.size() % 9 > 0) {
                    size += 1;
                }
                if (curListPosition < size - 1) {
                    curListPosition += 1;
                    dishesRecyclerAdapter.setListData(reflushDishList(curListPosition));
                }
                break;
            //<editor-fold desc="数字键">
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
            case R.id.btn_operation_key_backspace:
                try {
                    if (isInptEditEmpty()) {
                        // 退格
                        int index = etInputDishesName.getSelectionStart();
                        int lenght = etInputDishesName.getText().length();
                        if (lenght > 0) {
                            String oldContent;
                            String newContent;
                            if (index == lenght) {
                                oldContent = "";
                                newContent = etInputDishesName.getText().toString().substring(0, index - 1);
                            } else {
                                oldContent = etInputDishesName.getText().toString().substring(index, lenght);
                                newContent = etInputDishesName.getText().toString().substring(0, index == 0 ? 0 : index - 1);
                            }
                            etInputDishesName.setText(newContent + oldContent);
                            etInputDishesName.setSelection(index == 0 ? 0 : index - 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_operation_key_clear:
                if (isInptEditEmpty()) {
                    etInputDishesName.setText("");
                }
                break;
            case R.id.btn_operation_key_point:
                inputEdit(".");
                break;

            case R.id.btn_operation_key_enter:
                if (!judgeInputPrice()) {
                    return;
                }

                String payMsg = etInputDishesName.getText().toString();

                if (playWxAliError) {
                    showMessage("", "请重新刷新");
                    return;
                }

                if (isPayMsg) {
                    Log.d("frost-","isPayMsg");
                    refresh();
                    etInputDishesName.setText(payMsg);
                }

                if (isPayment) {
                    refresh();
                    etInputDishesName.setText(payMsg);
                }

                if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                    showMessage("", "正在支付中");
                    return;
                }

                addCustomDish(MyConstant.CUSTOM_PRICING_GOODS, "");
                etInputDishesName.setText("");
                MainActivity mainActivity2 = (MainActivity) getActivity();
                float totalPrices = 0;
                for (OperationMenu menu : menuListAdapter.getData()) {
                    totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                }
                //mainActivity2.showPay("wait","待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)));
                mainActivity2.showPay(mainActivity2, "card", "待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)), "", "", "");
                break;
            case R.id.btn_operation_fixed_price://固价模式
                btnOperationFixedPrice();
                break;
//            case R.id.op_entry_orders:
//                // 挂单
//                stopScan();
//                entryOrders();
//                break;
//            case R.id.op_combined_bill:
//                // 并单
//                setupScan();
//                combinedBills();
//                break;
//            case R.id.op_single_elimination:
//                // 消单
//                txEntryCount.setText("0");
//                txEntryTotalPrice.setText("0.0");
//                entryOrdersList.clear();
//                break;
            default:
                break;
        }
    }

    /**
     * 设置固价模式
     */
    private void btnOperationFixedPrice() {
        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
            showMessage("", "正在支付中");
            return;
        }

        String price = etInputDishesName.getText().toString();
        etInputDishesName.setText("");
        String curContent[] = price.split("\\.");
        if (price.isEmpty()) {
            showMessage("", "请输入正确金额！");
            return;
        }
        float curPrice = 0;
        try {
            curPrice = Float.parseFloat(price);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("提示", "请输入正确金额！");
            return;
        }
        if (curContent.length >= 2) {
            if (curContent[1].length() >= 2) {
                showMessage("提示", "请输入正确金额！");
                return;
            }
        }
        if (price.equals("0")) {
            // 取消固价
            Log.e("frost", "取消固价");
            if (txFixedPrice.getVisibility() == View.VISIBLE) {
                txFixedPrice.setVisibility(View.INVISIBLE);
                mFixedPrice = 0f;
            }
            menuListAdapter.deleteFixedPrice();
            modifyPayInfo();
            txFixedPrice.setText("");
            Log.e("frost", "mFixedPrice:" + mFixedPrice);
        } else if (curPrice > 0) {
            if (playWxAliError) {
                showMessage("", "请重新刷新");
                return;
            }
            if (isduringPayment) {
                showMessage("", "订单正在支付中");
                return;
            }
            oldCode = "";
            pay_result_select = 1000;
            Log.e("frost", "准备下单2");
            if (mFixedPrice <= 0) {
                if (isAdded()) {
                    showMessage("", getString(R.string.default_notify));
                }
                mainActivity.showPay(mainActivity, "card", "待支付 0.0", "", "", "");
            }
            isPayment = false;
            menuListAdapter.clearData();
            //modifyPayInfo();
            txPayCount.setText("0");
            txPayTotalPrice.setText("0");
            //现金支付部分界面内容 2019-07-13 chenxiaoyan
            txShowPaidIn.setVisibility(View.INVISIBLE);
            txShowChange.setVisibility(View.INVISIBLE);
            txPaidIn.setText("");
            txChange.setText("");
            //隐藏会员卡信息的内容  add by chenxiaoyan  2019-07-13
            hiddenMemberCardInfo();
            //清空输入金额部分的内容  add by chenxiaoyan  2019-07-13
            etInputDishesName.setText("");

            // 更改价格后，才变化
            mFixedPrice = curPrice;
            txFixedPrice.setText("固价模式：" + MyConstant.gFormat.format(mFixedPrice) + "元");
            if (txFixedPrice.getVisibility() == View.INVISIBLE) {
                txFixedPrice.setVisibility(View.VISIBLE);
            }
            // 删除原来的添加一个新的
            menuListAdapter.deleteFixedPrice();
            addCustomDish(MyConstant.CUSTOM_FIXED_PRICE_GOODS, mFixedPrice + "");

            float totalPrices = 0;
            for (OperationMenu menu2 : menuListAdapter.getData()) {
                totalPrices += menu2.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
            }
            //mainActivity.showPay("wait","待支付 " + Float.parseFloat(MyConstant.gFormat.format(totalPrices)));
            mainActivity.showPay(mainActivity, "card", "待支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
        }
    }

    private void refreshOn() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                    showMessage("", "订单正在支付中");
                    return;
                }
                if (isduringPayment) {
                    showMessage("", "订单正在支付中");
                    return;
                }
                playCardError = false;
                playWxAliError = false;
                isPayMsg = false;
                requestCardTime = false;
                Log.e("url", "refresh");
                curOrderNo = MyUtil.getPrimaryValue();
                Log.e("url", "curOrderNo:" + curOrderNo);
                oldCode = "";
                pay_result_select = 1000;
                Log.e("url", "准备下单2");
                if (mFixedPrice <= 0) {
                    if (isAdded()) {
                        showMessage("", getString(R.string.default_notify));
                    }
                    mainActivity.showPay(mainActivity, "card", "待支付 0.0", "", "", "");
                }
                isPayment = false;
                menuListAdapter.clearData();
                //modifyPayInfo();
                txPayCount.setText("0");
                txPayTotalPrice.setText("0");
                //现金支付部分界面内容 2019-07-13 chenxiaoyan
                txShowPaidIn.setVisibility(View.INVISIBLE);
                txShowChange.setVisibility(View.INVISIBLE);
                txPaidIn.setText("");
                txChange.setText("");
                //隐藏会员卡信息的内容  add by chenxiaoyan  2019-07-13
                hiddenMemberCardInfo();
                //清空输入金额部分的内容  add by chenxiaoyan  2019-07-13
                etInputDishesName.setText("");

            }
        });


    }

    private void refresh() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playCardError) {
                    return;
                }
                if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
                    showMessage("", "订单正在支付中");
                    return;
                }
                if (isduringPayment) {
                    showMessage("", "订单正在支付中");
                    return;
                }
                if ("在支付".equals(txMsgNotify.getText().toString())) {
                    return;
                }
                playCardError = false;
                playWxAliError = false;
                isPayMsg = false;
                requestCardTime = false;
                Log.e("url", "refresh");
                curOrderNo = MyUtil.getPrimaryValue();
                Log.e("url", "curOrderNo:" + curOrderNo);
                oldCode = "";
                pay_result_select = 1000;
                Log.e("url", "准备下单2");
                if (mFixedPrice <= 0) {
                    if (isAdded()) {
                        showMessage("", getString(R.string.default_notify));
                    }
                    mainActivity.showPay(mainActivity, "card", "待支付 0.0", "", "", "");
                }
                isPayment = false;
                menuListAdapter.clearData();
                //modifyPayInfo();
                txPayCount.setText("0");
                txPayTotalPrice.setText("0");
                //现金支付部分界面内容 2019-07-13 chenxiaoyan
                txShowPaidIn.setVisibility(View.INVISIBLE);
                txShowChange.setVisibility(View.INVISIBLE);
                txPaidIn.setText("");
                txChange.setText("");
                //隐藏会员卡信息的内容  add by chenxiaoyan  2019-07-13
                hiddenMemberCardInfo();
                //清空输入金额部分的内容  add by chenxiaoyan  2019-07-13
                etInputDishesName.setText("");

            }
        });

    }

    /**
     * 支付完成后清理数据
     * 以及判断是否需要添加固价商品
     */
    private void clearCurData() {
        if (isPayment) {
            Log.e("frost", "clearCurData");
            curOrderNo = MyUtil.getPrimaryValue();
            Log.e("frost", "curOrderNo:" + curOrderNo);
            isPayment = false;
            menuListAdapter.clearData();
            modifyPayInfo();
            txShowPaidIn.setVisibility(View.INVISIBLE);
            txShowChange.setVisibility(View.INVISIBLE);
            txPaidIn.setText("");
            txChange.setText("");
        }
    }

    /**
     * 打印小票
     */
    private void printerReceipt(TfMemberInfo memberInfo) {
        String printStatus = gSharedPre.getString(MyConstant.SP_AUTO_PRINT, "");
        Log.d(TAG, "printerReceipt: " + printStatus);
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

                /*
                checkName(menu.getDishNmae(), result);
                checkName(String.valueOf(menu.getCount()), result);
                checkName(MyConstant.gFormat.format(menu.getUnitPrice()), result);
                checkName(MyConstant.gFormat.format(menu.getTotalPrice()), result);
                */

                Log.e("printing", "menu.getDishNmae().length():" + strLength(menu.getDishNmae()));
                //菜单名字的处理
                PrintDishName printDishName = new PrintDishName(menu.getDishNmae(), result);
//                result.append("\r\n"+menu.getDishNmae());
                //数量的拼接
//                if(printDishName.getChangeLineCount() > 0){
//                    for(int temp = 0;temp<printDishName.getChangeLineCount();temp++){
//                        result.append("\n");
//                    }
//                }
                int kong_count = 11;
                int len_name = strLength(printDishName.getLastLineStr());
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
                dis_count = getDisCountForMember(memberInfo.getMI_TYPE());
                Log.e("pay", "mi_type:" + memberInfo.getMI_TYPE() + " dis_count:" + dis_count);
            }
            if (dis_count == 100) {
                dis_count = getDisCountForAll();
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


    /**
     * 支付成功后，或手动
     * 打印信息
     */
    private void setupPrinter() {
        Log.d(TAG, "setupPrinter: ");
        if (!isPayment || isPrint) {
            Log.e("print", "isPayment:" + isPayment + " isPrint:" + isPrint);
            return;
        }
        isPrint = true;
        // 打印预览
        if (mainActivity.pPrinter == null) {
            mainActivity.pPrinter = new BuiltInPrinter(mainActivity);
        }
        mainActivity.pPrinter.setPrintCallback(new BuiltInPrinter.PrintCallback() {
            @Override
            public void print(BuiltInPrinter printer) {
                // 判断是余额还是找零，在打印中判断会失效
                String word = judgePrintinfo();
                String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
//                String machineName = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NAME, "");
                String storeName = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_NAME, "");
                String storeAddr = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_ADDR, "");
                try {
                    //<editor-fold desc="小票开头">
                    printer.sendData("\n小票编号：");
                    printer.sendData(("\n" + curOrderNo));
                    //mainActivity.pPrinter.sendCommand(0x0a);
                    printer.sendData(("\n\t\t\t" + storeName + "\n"));
                    //mainActivity.pPrinter.sendCommand(0x1b, 0x23, 0x44);
                    printer.sendData(("\n设备号：" + machineNo));
                    printer.sendData(("\n操作员：" + mainActivity.pUserInfo.getUNAME()));
                    //mainActivity.pPrinter.sendCommand(0x0a);
                    printer.sendData(("\n日期：" + MyUtil.obtainCurrentSysDate(2)));
                    //mainActivity.pPrinter.sendCommand(0x0a);
                    printer.sendData("\n品名\t\t数量\t单价\t金额");
                    printer.sendData("\n--------------------------------");
                    checkProductMsg(printer, word, storeAddr);
                    printer.sendData("\n--------------------------------");
                    printer.sendData("\n\t谢谢惠顾，欢迎下次光临！");
                    printer.sendData(("\n\t此小票仅作消费依据"));
                    mainActivity.pPrinter.lineFeed(4);
                    //try {
                    //	Thread.sleep(2000);
                    //} catch(InterruptedException ignored) {
                    //}
                } catch (SmartPosException e) {
                    e.printStackTrace();
                }
                isPrint = false;
                if (payWay != PAY_CASH) {
                    gOperationHandler.obtainMessage(REFRESH).sendToTarget();
                }
            }
        });
        mTHREAD_POOL.execute(mainActivity.pPrinter.new WriteThread(0));
    }

    /**
     * 添加自定义价格的定价商品
     *
     * @param ci_id 菜品id
     */
    private void addCustomDish(long ci_id, String price) {
        if (price.isEmpty()) {
            price = etInputDishesName.getText().toString();
        }
        Log.d("frost", price + "---");
        CommodityRecord record = new CommodityRecord();
        record.setCI_PRICE(price);
        record.setCI_ID(ci_id);
        record.setCI_NAME("定价商品");
        addDishInternal(record);
    }

    /**
     * 把菜单中选中的菜品添加到左侧计算菜单价格中
     */
    private void addDishInternal(CommodityRecord dishes) {
        showMessage("", "请支付");
        showCashInfo();
        clearCurData();
        OperationMenu menu = new OperationMenu();
        menu.setUnitPrice(Float.parseFloat(dishes.getCI_PRICE().isEmpty() ? "0" : dishes.getCI_PRICE()));
        menu.setId(dishes.getCI_ID());
        menu.setDishNmae(dishes.getCI_NAME());

        OperationMenu oldMenu = isContainId(dishes.getCI_ID());
        //int dis_count = getDisCountForAll();

        // 如果所点菜名已存在，且不是固价/定价商品， +1
        if (oldMenu != null && oldMenu.getId() != MyConstant.CUSTOM_PRICING_GOODS && oldMenu.getId() != MyConstant.CUSTOM_FIXED_PRICE_GOODS) {

            oldMenu.setCount(oldMenu.getCount() + 1);
            oldMenu.setTotalPrice(oldMenu.getCount() * oldMenu.getUnitDisPrice());
            menuListAdapter.notifyDataSetChanged();
            // menuListAdapter.calDisCount(dis_count);
            return;
        } else {
            menu.setCount(1);
        }
        menuListAdapter.insertDataToEnd(menu);
        //menuListAdapter.calDisCount(dis_count);
        menuListAdapter.calDisCount(100);
//        if(mFixedPrice <= 0){
        Log.e("url", "SP_MEDIA_PLEASE_SET_CARD2");
        playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
//        }

    }

    /**
     * 现金支付
     */
    private void payByCash() {

        Log.e("url", "payByCash1");
        payWay = PAY_CASH;
        isPayment = true;

        // 更新人数区记录
        mTotalPersonCount += 1;
        mCurMealPersonCount += 1;
        gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();
        Log.e("url", "payByCash2");
        // 点击现金快捷按钮，即表示买单，创建对应的账单信息
        String count = txPayTotalPrice.getText().toString();
        int dis_count = getDisCountForAll();//全员折扣
        Log.i(TAG, "dis_count: " + dis_count);
        if (dis_count < 100) {//
            menuListAdapter.calDisCount(dis_count);
            count = calculateDisCountPrice(count, dis_count);
            Log.i("pay", "payByCash->count   折扣后的值为->" + count);
        }
        Log.e("url", "payByCash3");
        createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(count))), 0, "0", "0", "0", menuListAdapter.getData().size() + "", 1);
        // mainActivity.showPay("cash","已支付 " + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))));
        Log.e("url", "payByCash4");
        float totalPrices = 0;
        for (OperationMenu menu : menuListAdapter.getData()) {
            totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
        }
        Log.e("url", "payByCash5");
        mainActivity.showPay(mainActivity, "card", "已支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
        Log.e("url", "payByCash6");
        stopScan();
        Log.e("url", "payByCash7");
    }

    /**
     * 获得真正需要 支付的现金
     *
     * @param price
     * @return
     */
    private String getActualCash(String price) {
        int dis_count = getDisCountForAll();//全员折扣
        Log.i(TAG, "getActualCash ->dis_count: " + dis_count);
        if (dis_count < 100) {//有全员折扣
            price = calculateDisCountPrice(price, dis_count);
            Log.i("pay", "getActualCash->price   折扣后的值为->" + price);
        }
        return price;
    }

    /**
     * 规定选择菜肴的9宫格需要填满，未满9个就添加空的item
     * 这里根据给定页数，显示对应页数的菜肴信息
     *
     * @param position
     * @return
     */
    public List<CommodityRecord> reflushDishList(int position) {
        int start = position * 9;
//        int stop = start + 9 - 1;
        List<CommodityRecord> list = new ArrayList<>();
        for (int i = start; i < dishesList.size(); i++) {
            list.add(dishesList.get(i));
        }
        if (list.size() < 9) {
            while (list.size() == 9) {
                list.add(new CommodityRecord());
            }
        }
        return list;
    }

    public boolean isInptEditEmpty() {
        if (etInputDishesName != null && /*etInputDishesName.isFocused() && */!etInputDishesName.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void inputEdit(String content) {
        Log.i("temp", "inputEdit->" + content);
        if (etInputDishesName != null) {
            etInputDishesName.requestFocus();
            MyUtil.hideInputMethodManager(mContext, etInputDishesName);
            int index = etInputDishesName.getSelectionStart();
            Editable editable = etInputDishesName.getText();
            editable.insert(index, content);
            String curContent[] = editable.toString().split("\\.");
            if (curContent.length >= 2) {
                if (curContent[1].length() >= 2) {
                    showMessage("提示", "请输入正确金额！");
                    return;
                }
            }
            etInputDishesName.setText(editable.toString());
            etInputDishesName.setSelection(index + content.length());
        }
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
     * 修改支付信息
     * 数量和总价
     */
    public void modifyPayInfo() {
        float totalPrices = 0;
        int sum = 0;
        for (OperationMenu menu : menuListAdapter.getData()) {
            sum += menu.getCount();
            totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
        }
        txPayCount.setText(sum + "");
        Log.e("totalPrices", "totalPrices:" + MyConstant.gFormat.format(totalPrices));
        txPayTotalPrice.setText(MyConstant.gFormat.format(totalPrices));
        if (sum == 0) {
            stopScan();
        }
        oldCode = "";
    }

    //    /**
//     * 计算折扣价格
//     * 折扣（全民折扣）
//     * DISCOUNT_STATUS: 0 关闭 1开启
//     *
//     * @param price
//     * @param discountType 1 全民折扣 2 会员折扣 3 自定义折扣
//     * @return final discount Price
//     */
    /*
    public String calculateDisCountPrice(String price, int discountType) {
        price = String.valueOf(getDiscountRate(discountType) * Float.parseFloat(price));
        return price;
    }
    */
    public String calculateDisCountPrice(String price, int dis_count) {
        float result = Float.parseFloat(price);
        result = result * dis_count / 100f;
        price = MyConstant.gFormat.format(result);
        return price;
    }

    /**
     * 会员折扣
     *
     * @param mi_type
     * @return
     */
    public int getDisCountForMember(String mi_type) {
        int disCount = 100;
        if (mi_type != null) {
            List<TfDiscountRecord> discountRecordList = pDaoSession.queryBuilder(TfDiscountRecord.class)
                    .where(TfDiscountRecordDao.Properties.DISCOUNT_STATUS.eq(1),
                            TfDiscountRecordDao.Properties.DISCOUNT_TYPE.eq(1))
                    .build()
                    .list();
            if (discountRecordList != null && discountRecordList.size() > 0) {
                TfDiscountRecord tfDiscountRecord = discountRecordList.get(0);
                if (tfDiscountRecord != null) {
                    List<MemberTypeRecord> memberTypeRecordList = pDaoSession.queryBuilder(MemberTypeRecord.class)
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

    /**
     * 返回折扣率
     * DISCOUNT_STATUS: 0 关闭 1开启
     *
     * @param discountType 0 全民折扣 1 会员折扣 2 自定义折扣
     * @return
     */
    private float getDiscountRate(int discountType) {
        float rate = 1;
        List<TfDiscountRecord> discountRecordList = pDaoSession.queryBuilder(TfDiscountRecord.class)
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

    public void saveOrder(final TfConsumeCardRecord cardRecord,
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

    public static String cardMemberId = "0";

    /**
     * 创建一条消费记录
     */
    public void createConsumeInfo(final float totalMoney, final float balance, final String memberId,
                                  final String accountId, final String icid, final String personCount, final int CCR_STATUS) {
        Log.d("frost", "createConsumeInfo......:" + totalMoney);
        onlineSync = true;
        mTHREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!"0".equals(memberId)) {
                        cardMemberId = memberId;
                    }
                    DaoSession session = MyApplication.getInstance();
                    String clientCode = mainActivity.pUserInfo.getCLIENT_CODE();
                    String storeCode = mainActivity.pUserInfo.getSTORE_CODE();
                    String u_id = mainActivity.pUserInfo.getU_ID();
                    String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
                    String createTime = MyUtil.dateConversion(System.currentTimeMillis());
//                    String pricingNo = "";
//                    try {
//                        pricingNo = pDaoSession.queryBuilder(StoreConfig.class)
//                                .build()
//                                .list()
//                                .get(0).getPRICING();
//                    } catch (Exception e) {
//                        showMessage("", "定价编号获取失败");
//                        return;
//                    }
                    if (curOrderNo == null && curOrderNo.length() == 0) {
                        showMessage("", "订单号不能为空");
                    }
                    List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                            .where(TfConsumeCardRecordDao.Properties.COR_ID.eq(curOrderNo))
                            .limit(1)
                            .build()
                            .list();
                    if (cardRecords != null && cardRecords.size() > 0) {
                        showMessage("", "订单号已存在");
                        Log.e("url", "订单号已存在");
                        Log.e("url", "curOrderNo:" + curOrderNo);
                        return;
                    }

                    //curOrderNo = MyUtil.getPrimaryValue();
                    String orderNo = curOrderNo;
                    // 获取餐次
                    String currentTime = MyUtil.obtainCurrentSysDate(1);

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

                            //personCount:商品size
                            Log.d("frost", "personCount:" + personCount);
                            //流水数据添加到界面
                            waterAdapter.insertDataToEnd(new WaterInfo(personCount, "" + s));

                        }
                    });

                    List<TfMealTimes> mealTimeList = session.queryBuilder(TfMealTimes.class)
                            .where(TfMealTimesDao.Properties.STARTTIME.le(currentTime),
                                    TfMealTimesDao.Properties.ENDTIME.ge(currentTime))
                            .build()
                            .list();
                    /*
                    // 更新人数区记录
                    mTotalPersonCount +=1;
                    mCurMealPersonCount +=1;

                    gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();
                    */

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
                    Log.e("pay", "payWay:" + payWay);
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

                    cardRecord.setCCR_UPLOAD_STATUS("1");
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
                    cardRecord.setCCR_UPLOAD_TIME(createTime);

                    // 消费详情
                    List<TfConsumeDetailsRecord> detailsRecordList = new ArrayList<>();
                    TfConsumeDetailsRecord detailsRecord;
                    for (OperationMenu menu : menuListAdapter.getData()) {
                        detailsRecord = new TfConsumeDetailsRecord();
                        detailsRecord.setCDR_ID(getPrimaryValue());

                        //detailsRecord.setCDR_MONEY(MyConstant.gFormat.format(menu.getCount() * menu.getUnitPrice()));
                        detailsRecord.setCDR_MONEY(MyConstant.gFormat.format(menu.getTotalPrice()));

                        if (menu.getId() == MyConstant.CUSTOM_FIXED_PRICE_GOODS || menu.getId() == MyConstant.CUSTOM_PRICING_GOODS) {
                            //detailsRecord.setCDR_NO(String.valueOf(menu.getId()));
                            detailsRecord.setCDR_NO(String.valueOf(mainActivity.pStoreConfig.getPRICING()));
                            Log.e("pay", "---1--detailsRecord.getCDR_ID():" + detailsRecord.getCDR_NO());
                        } else {
                            detailsRecord.setCDR_NO(String.valueOf(menu.getId()));
                            Log.e("pay", "---2--detailsRecord.getCDR_ID():" + detailsRecord.getCDR_NO());
                        }

                        detailsRecord.setCDR_NUMBER(String.valueOf(menu.getCount()));
                        detailsRecord.setCDR_TYPE("2");
                        //detailsRecord.setCDR_UNIT_PRICE(String.valueOf(menu.getUnitPrice()));

//                        Log.e("url","menu.getUnitDisPrice():" + menu.getUnitDisPrice());
//                        Log.e("url","menu.getUnitPrice():" + menu.getUnitPrice());
                        //111
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
//
//                    // 消费订单
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
                        onlineSync = false;
                        Log.d("url", "saveOrder: saveOrder");
                        saveOrder(cardRecord, detailsRecordList, orderRecord);
                    } else {
                        // 更新
                        try {
                            Log.d("pay", "updateToServerOnline: ");
                            Log.d("url", "updateToServerOnline: updateToServerOnline");
                            updateToServerOnline(cardRecord, detailsRecordList, orderRecord);
                        } catch (Exception e) {
                            Log.d("url", "updateToServerOnline: Exception");
                            Log.d("url", "updateToServerOnline: e:" + e.getMessage());
                            onlineSync = false;
                            saveOrder(cardRecord, detailsRecordList, orderRecord);
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    onlineSync = false;
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 去数据库查找更新到服务器
     * 启动收银界面时，在线状态下，自动的去查找上次没有更新的数据
     * 即离线状态下的更新
     */
    public void syncDataToServer() {
        try {
            if (!MyUtil.networkState) {
                // 无网络，不同步
                return;
            }
            if (syncing) {
                return;
            }
            List<TfConsumeOrderRecord> orderRecords = new ArrayList<>();
            List<TfConsumeDetailsRecord> detailsRecords = new ArrayList<>();
            List<TfConsumeCardRecord> cardRecords = new ArrayList<>();
            cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                    .where(
                            //TfConsumeCardRecordDao.Properties.MI_ID.isNotNull(),
                            TfConsumeCardRecordDao.Properties.CCR_STATUS.eq(1),
                            TfConsumeCardRecordDao.Properties.ISM_STATUS.eq(0))
                    .orderAsc(TfConsumeCardRecordDao.Properties.CCR_ID)
                    .limit(2)
                    .build()
                    .list();
            for (TfConsumeCardRecord cardRecord : cardRecords) {
                List<TfConsumeOrderRecord> orderList = new ArrayList<>();
                List<TfConsumeDetailsRecord> detailList = new ArrayList<>();

                orderList = pDaoSession.queryBuilder(TfConsumeOrderRecord.class)
                        .where(TfConsumeOrderRecordDao.Properties.ISM_STATUS.eq(0),
                                TfConsumeOrderRecordDao.Properties.COR_ID.eq(cardRecord.getCOR_ID()))
                        .build()
                        .list();

                detailList = pDaoSession.queryBuilder(TfConsumeDetailsRecord.class)
                        .where(TfConsumeDetailsRecordDao.Properties.ISM_STATUS.eq(0),
                                TfConsumeDetailsRecordDao.Properties.COR_ID.eq(cardRecord.getCOR_ID()))
                        .build()
                        .list();

                if (!orderList.isEmpty()) {
                    for (TfConsumeOrderRecord orderRecord : orderList) {
                        orderRecords.add(orderRecord);
                    }
                }
                if (!detailList.isEmpty()) {
                    for (TfConsumeDetailsRecord detailsRecord : detailList) {
                        detailsRecords.add(detailsRecord);
                    }
                }
            }

            if (cardRecords.isEmpty()) {
                // empty data does not need to sync
                return;
            }
            if (!syncing && !onlineSync) {
                syncing = true;
                updateToServerInline(cardRecords, detailsRecords, orderRecords);
            }
        } catch (Exception e) {
            syncing = false;
            e.printStackTrace();
        }
    }

    /**
     * 将本地记录的消费信息发送到服务器（更新）
     * 离线模式   
     *
     * @throws JSONException
     */
    public void updateToServerOnline(final TfConsumeCardRecord cardRecord,
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
        //
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
        Log.d("pay", "updateToServerOnline: " + data);

        LocalRetrofit.createService().postUpdateOnline(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UpdateResultInfo>() {
                    @Override
                    public void onCompleted() {
                        onlineSync = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("url", "updateToServerOnline:777 onError");
                        onlineSync = false;
                        saveOrder(cardRecord, detailsRecordList, orderRecord);

                        Log.d(TAG, "在线状态更新 onError: " + e.getMessage());
                        //cardRecord.setCCR_UPLOAD_STATUS("2");
                        //pDaoSession.update(cardRecord);
                    }

                    @Override
                    public void onNext(UpdateResultInfo syncResultInfo) {
                        Log.e("url", "updateToServerOnline:777 onNext");
                        onlineSync = false;

                        if (syncResultInfo != null && syncResultInfo.isResult()) {
                            cardRecord.setISM_STATUS("1");
                        }
                        saveOrder(cardRecord, detailsRecordList, orderRecord);

                        Log.d(TAG, "Online  getCode: " + syncResultInfo.getCode());
                        Log.d(TAG, "Online  getData: " + syncResultInfo.getData());
                        Log.d(TAG, "Online  getMsg: " + syncResultInfo.getMsg());
                        Log.d(TAG, "Online  isResult: " + syncResultInfo.isResult());
                        Log.d(TAG, "Online  getStatus: " + syncResultInfo.getStatus());
                        Log.d(TAG, "Online  getType: " + syncResultInfo.getType());
                        Log.d(TAG, "Online  money: " + cardRecord.getCCR_MONEY());

                    }
                });

    }

    /**
     * 离线更新
     * 断网重连后调用
     */
    private void updateToServerInline(final List<TfConsumeCardRecord> cardRecordList,
                                      final List<TfConsumeDetailsRecord> detailsRecordList,
                                      final List<TfConsumeOrderRecord> orderRecordList) {
        Gson gson = new Gson();
        final InlineUpdateInfo syncInfo = new InlineUpdateInfo();
        List<InlineUpdateInfo.DetailsBean> details = new ArrayList<>();
        List<InlineUpdateInfo.OrderBean> orderBeans = new ArrayList<>();
        List<InlineUpdateInfo.CardBean> cardBeans = new ArrayList<>();
        InlineUpdateInfo.OrderBean orderBean = null;
        InlineUpdateInfo.DetailsBean detailsBean;
        InlineUpdateInfo.CardBean cardBean = null;

        for (TfConsumeOrderRecord orderRecord : orderRecordList) {
            orderBean = new InlineUpdateInfo.OrderBean();
            orderBean.setCOR_ID(orderRecord.getCOR_ID());
            orderBean.setCOR_MONERY(orderRecord.getCOR_AMOUNT());
            orderBean.setCOR_AMOUNT(orderRecord.getCOR_AMOUNT());
            orderBean.setMACHINE_NO(orderRecord.getMACHINE_NO() == null ? "0" : orderRecord.getMACHINE_NO());
            orderBean.setISM_STATUS(orderRecord.getISM_STATUS());
            orderBean.setSTORE_CODE(orderRecord.getSTORE_CODE());
            orderBean.setCREATETIME(orderRecord.getCREATETIME());
            orderBean.setCOR_TYPE(orderRecord.getCOR_TYPE());
            orderBean.setADDR_ID(String.valueOf(orderRecord.getADDR_ID()));
            orderBean.setCLIENT_CODE(orderRecord.getCLIENT_CODE());
            orderBean.setUPDATETIME(orderRecord.getUPDATETIME());
            orderBean.setCOR_TYPE(orderRecord.getCOR_TYPE());
            // 添加到列表就表示能同步了，修改同步状态
            orderBeans.add(orderBean);
        }

        for (TfConsumeDetailsRecord detailsRecord : detailsRecordList) {
            detailsBean = new InlineUpdateInfo.DetailsBean();
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

        for (TfConsumeCardRecord cardRecord : cardRecordList) {
            if ("1".equals(cardRecord.getCCR_PAY_TYPE())) {
                cardBean = new InlineUpdateInfo.CardBean();
                cardBean.setCOR_ID(cardRecord.getCOR_ID());
                cardBean.setCCR_MONEY(String.valueOf(cardRecord.getCCR_MONEY()));
                cardBean.setCCR_PAY_TYPE(cardRecord.getCCR_PAY_TYPE());
                cardBean.setCCR_ID(cardRecord.getCCR_ID());
                cardBean.setCCR_STATUS(cardRecord.getCCR_STATUS());
                cardBean.setIC_ID(cardRecord.getIC_ID());

                if (cardRecord.getMI_ID() == null) {
                    cardBean.setMI_ID(cardMemberId);
                } else {
                    cardBean.setMI_ID(cardRecord.getMI_ID());
                }

                cardBean.setMT_ID(cardRecord.getMT_ID());
                cardBean.setMACHINE_NO(cardRecord.getMACHINE_NO() == null ? "0" : cardRecord.getMACHINE_NO());
                cardBean.setISM_STATUS(cardRecord.getISM_STATUS());
                cardBean.setSTORE_CODE(cardRecord.getSTORE_CODE());
                cardBean.setCREATETIME(cardRecord.getCREATETIME());
                cardBean.setCLIENT_CODE(cardRecord.getCLIENT_CODE());
                if (cardRecord.getMI_ID() != null && cardRecord.getMI_ID().length() > 0) {
                    cardBean.setCCR_ORIGINALAMOUNT(cardRecord.getCCR_ORIGINALAMOUNT());
                }
                cardBean.setU_ID(cardRecord.getU_ID());
                String uploadTime = MyUtil.dateConversion(System.currentTimeMillis());
                cardBean.setCCR_UPLOAD_TIME(uploadTime);
                cardRecord.setCCR_UPLOAD_TIME(uploadTime);
                cardBeans.add(cardBean);


            } else {
                cardBean = new InlineUpdateInfo.CardBean();
                cardBean.setCOR_ID(cardRecord.getCOR_ID());
                cardBean.setCCR_MONEY(String.valueOf(cardRecord.getCCR_MONEY()));
                cardBean.setCCR_PAY_TYPE(cardRecord.getCCR_PAY_TYPE());
                cardBean.setCCR_ID(cardRecord.getCCR_ID());
                cardBean.setCCR_STATUS(cardRecord.getCCR_STATUS());
                cardBean.setIC_ID(cardRecord.getIC_ID());
                cardBean.setMI_ID(cardRecord.getMI_ID() == null ? "0" : cardRecord.getMI_ID());
                cardBean.setMT_ID(cardRecord.getMT_ID());
                cardBean.setMACHINE_NO(cardRecord.getMACHINE_NO() == null ? "0" : cardRecord.getMACHINE_NO());
                cardBean.setISM_STATUS(cardRecord.getISM_STATUS());
                cardBean.setSTORE_CODE(cardRecord.getSTORE_CODE());
                cardBean.setCREATETIME(cardRecord.getCREATETIME());
                cardBean.setCLIENT_CODE(cardRecord.getCLIENT_CODE());

                if (cardRecord.getMI_ID() != null && cardRecord.getMI_ID().length() > 0) {
                    cardBean.setCCR_ORIGINALAMOUNT(cardRecord.getCCR_ORIGINALAMOUNT());
                }
                cardBean.setU_ID(cardRecord.getU_ID());
                String uploadTime = MyUtil.dateConversion(System.currentTimeMillis());
                cardBean.setCCR_UPLOAD_TIME(uploadTime);
                cardRecord.setCCR_UPLOAD_TIME(uploadTime);
                cardBeans.add(cardBean);
            }

        }

        syncInfo.setCard(cardBeans);
        syncInfo.setDetails(details);
        syncInfo.setOrder(orderBeans);
        String data = gson.toJson(syncInfo, InlineUpdateInfo.class);
        Log.d(TAG, "updateToServerInline: " + data);
        if (MyUtil.isConfigServer()) {
            //
            LocalRetrofit.createService().postUpdateInline(data)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BaseBean<?>>() {
                        @Override
                        public void onCompleted() {
                            syncing = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("url", "updateToServerInline:555");
                            syncing = false;
                            Log.d(TAG, "离线状态更新 onError: " + e.getMessage());
                        }

                        @Override
                        public void onNext(BaseBean<?> syncResultInfo) {
                            Log.e("url", "updateToServerInline:5552");
                            syncing = false;
                            Log.d(TAG, "inline  getCode: " + syncResultInfo.getCode());
                            Log.d(TAG, "inline  getData: " + syncResultInfo.getData());
                            Log.d(TAG, "inline  getMsg: " + syncResultInfo.getMsg());
                            Log.d(TAG, "inline  isResult: " + syncResultInfo.isResult());
                            Log.d(TAG, "inline  getStatus: " + syncResultInfo.getStatus());
                            Log.d(TAG, "inline  getType: " + syncResultInfo.getType());

                            try {
                                if (syncResultInfo != null && syncResultInfo.getData() != null) {
                                    for (int i = 0; i < syncResultInfo.getData().size(); i++) {
                                        String ccr_id = (String) syncResultInfo.getData().get(i);
                                        Log.d(TAG, "inline  ccr_id: " + ccr_id);

                                        if (ccr_id != null) {
                                            for (TfConsumeCardRecord cardRecord : cardRecordList) {
                                                if (ccr_id.equals(cardRecord.getCCR_ID())) {
                                                    cardRecord.setISM_STATUS("1");
                                                    pDaoSession.update(cardRecord);
                                                    Log.d(TAG, "inline  update ccr_id: " + ccr_id);
                                                }
                                            }
                                        }

                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
        }
    }

    private boolean requestCard = false;
    private boolean requestCardTime = false;
    private boolean playCardError = false;

    /**
     * 根据给定的卡号，去查找对应的U_ID，再通过他去查找对应账户余额
     *
     * @param icId
     */
    private void getMemberCardBalance(final String icId) {
        Log.e("url", "isPayment:" + isPayment);

        if (playWxAliError) {
            showMessage("", "请重新刷新");
            mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
            return;
        }

        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
            showMessage("", "正在支付中");
            return;
        }

        if ("0".equals(txPayCount.getText().toString())) {

        } else {
            if (!isPayment) {
                showMessage("提示", "会员卡支付中...");
            } else {
                refresh();
            }
        }


        Log.e("pay", "getMemberCardBalance....1 icId:" + icId);
        try {
            //curOrderNo = MyUtil.getPrimaryValue();
            Log.e("frost", "会员卡支付 curOrderNo:" + curOrderNo);
            isduringOrderNo = "" + curOrderNo;
            Log.e("frost", "会员卡支付icId:" + icId);
            if (MyUtil.networkState) {
                if (requestCard) {
                    return;
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("url", "线下超时支付判断");
                        if (requestCard && isduringOrderNo.equals(curOrderNo) && !isPayment) {
                            Log.e("url", "线下超时支付判断 in");
                            requestCard = false;
                            requestCardTime = true;
                            TfCardInfo info = null;
                            TfMemberAccountRecord cashRecord = null;
                            TfMemberAccountRecord subsidyRecord = null;
                            TfMemberInfo memberInfo = null;
                            List<TfCardInfo> cardInfoList = pDaoSession.queryBuilder(TfCardInfo.class)
                                    .where(TfCardInfoDao.Properties.IC_ID.eq(icId))
                                    .build()
                                    .list();

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
                                            showMessage("", "当前卡已挂失");
                                            playVoice(MyConstant.SP_LOST_CARD);
                                            break;
                                        case "2":
                                            showMessage("", "当前卡已注销");
                                            playVoice(MyConstant.SP_CANCEL_CARD);
                                            break;
                                    }
                                    isduringOrderNo = "";
                                    requestCard = false;
                                    playCardError = true;
                                    return;
                                }
                                // memberinfo
                                // account status : 0 normal 1 freeze
                                Log.e("look", "getMemberCardBalance info.getMI_ID():" + info.getMI_ID());
                                List<TfMemberInfo> memberInfoList = pDaoSession.queryBuilder(TfMemberInfo.class)
                                        .where(TfMemberInfoDao.Properties.MI_ID.eq(info.getMI_ID()))
                                        .build()
                                        .list();
                                if (memberInfoList == null || memberInfoList.isEmpty()) {
                                    showMessage("", "会员不存在");
                                    requestCard = false;
                                    isduringOrderNo = "";
                                    playCardError = true;
                                    return;
                                }
                                memberInfo = memberInfoList.get(0);
                                List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
                                        //TODO
                                        .where(TfMemberAccountRecordDao.Properties.MI_ID.eq(info.getMI_ID()))
                                        .build()
                                        .list();

                                if (accountRecordList.isEmpty() || accountRecordList == null) {
                                    cashRecord = null;
                                } else {
                                    Log.e("frost", "getMemberCardBalance accountRecordList.size():" + accountRecordList.size());
                                    for (TfMemberAccountRecord record : accountRecordList) {
                                        Log.e("frost", "getMemberCardBalance record.getACCOUNT_TYPE():" + record.getACCOUNT_TYPE());
                                        if (record.getACCOUNT_TYPE().equals("0")) {
                                            cashRecord = record;
                                        } else if ((record.getACCOUNT_TYPE().equals("1"))) {
                                            subsidyRecord = record;
                                        }
                                    }
                                    if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                                        try {
                                            /**
                                             * frost
                                             */
                                            calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            cashRecord = null;
                                            subsidyRecord = null;
                                            return;
                                        }
                                    }
                                }
                            }
                            showMessage("", "非法卡");
                            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                            isduringOrderNo = "";
                            playCardError = true;
                        }
                    }
                }, 10000);

                Log.e("pay", "getMemberCardBalance....2");
                //   Log.e("url","isduringPayment2:" + isduringPayment);
                String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                String clienCode = "", storeCode = "";
                try {
                    clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
                    storeCode = mainActivity.pUserInfo.getSTORE_CODE();
                } catch (Exception e) {
                    clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                    storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
                }
                String url = "http://" + ip + ":" + port + "/k-occ/member/info/" + clienCode + "/" + storeCode + "/icId/" + icId;

                requestCard = true;
                requestCardTime = false;

//                Handler handler2 = new Handler();
//                handler2.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {

                OkHttpClient client = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder()
                        .url(url)
                        .build();
                //Log.e("url","isduringPayment3:" + isduringPayment);
                Log.e("pay", "getMemberCardBalance....2.1");
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("frost", "会员卡支付 onFailure:" + isduringOrderNo);
                        if (requestCardTime) {
                            Log.e("url", "超时支付，停止url支付 onFailure");
                            requestCardTime = false;
                            return;
                        }
                        requestCard = false;
                        TfCardInfo info = null;
                        TfMemberAccountRecord cashRecord = null;
                        TfMemberAccountRecord subsidyRecord = null;
                        TfMemberInfo memberInfo = null;
                        List<TfCardInfo> cardInfoList = pDaoSession.queryBuilder(TfCardInfo.class)
                                .where(TfCardInfoDao.Properties.IC_ID.eq(icId))
                                .build()
                                .list();

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
                                        showMessage("", "当前卡已挂失");
                                        playVoice(MyConstant.SP_LOST_CARD);
                                        break;
                                    case "2":
                                        showMessage("", "当前卡已注销");
                                        playVoice(MyConstant.SP_CANCEL_CARD);
                                        break;
                                }
                                isduringOrderNo = "";
                                requestCard = false;
                                playCardError = true;
                                return;
                            }
                            // memberinfo
                            // account status : 0 normal 1 freeze
                            Log.e("look", "getMemberCardBalance info.getMI_ID():" + info.getMI_ID());
                            List<TfMemberInfo> memberInfoList = pDaoSession.queryBuilder(TfMemberInfo.class)
                                    .where(TfMemberInfoDao.Properties.MI_ID.eq(info.getMI_ID()))
                                    .build()
                                    .list();
                            if (memberInfoList == null || memberInfoList.isEmpty()) {
                                showMessage("", "会员不存在");
                                isduringOrderNo = "";
                                requestCard = false;
                                isPayment = true;
                                playCardError = true;
                                return;
                            }
                            memberInfo = memberInfoList.get(0);
                            List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
                                    //TODO
                                    .where(TfMemberAccountRecordDao.Properties.MI_ID.eq(info.getMI_ID()))
                                    .build()
                                    .list();
                            if (accountRecordList.isEmpty() || accountRecordList == null) {
                                isduringOrderNo = "";
                                requestCard = false;
                                cashRecord = null;
                            } else {
                                Log.e("look", "getMemberCardBalance accountRecordList.size():" + accountRecordList.size());
                                for (TfMemberAccountRecord record : accountRecordList) {
                                    Log.e("look", "getMemberCardBalance record.getACCOUNT_TYPE():" + record.getACCOUNT_TYPE());
                                    if (record.getACCOUNT_TYPE().equals("0")) {
                                        cashRecord = record;
                                    } else if ((record.getACCOUNT_TYPE().equals("1"))) {
                                        subsidyRecord = record;
                                    }
                                }
                                if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                                    try {
                                        TfCardInfo finalInfo = info;
                                        TfMemberInfo finalMemberInfo = memberInfo;
                                        TfMemberAccountRecord finalCashRecord = cashRecord;
                                        TfMemberAccountRecord finalSubsidyRecord = subsidyRecord;
                                        mainActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                calcurlateMemberBalance(icId, finalInfo, finalMemberInfo, finalCashRecord, finalSubsidyRecord);
                                            }
                                        });
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    } finally {
                                        cashRecord = null;
                                        subsidyRecord = null;
                                        isduringOrderNo = "";
                                        requestCard = false;
                                        return;
                                    }
                                }
                            }
                        }
                        showMessage("", "非法卡");
                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                        isduringOrderNo = "";
                        requestCard = false;
                        playCardError = true;
                        //  isduringPayment = false;
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            if (requestCardTime) {
                                Log.e("url", "超时支付，停止url支付");
                                requestCardTime = false;
                                return;
                            }
                            //    Log.e("url","isduringPayment4:" + isduringPayment);
                            TfCardInfo info = null;
                            TfMemberAccountRecord cashRecord = null;
                            TfMemberAccountRecord subsidyRecord = null;
                            TfMemberInfo memberInfo = null;
                            float cashBalance = 0, subsidyBalance = 0;
                            requestCard = false;

                            Log.e("pay", "getMemberCardBalance....3...requestCard:" + requestCard);

                            Gson gson = new Gson();
                            String body = response.body().string();
                            Log.e("pay", "getMemberCardBalance....4...body:" + body);
                            //验证卡的状态
                            ResultBaseBean resultBaseBean = gson.fromJson(body, new TypeToken<ResultBaseBean>() {
                            }.getType());
                            if (!resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_NORMAL)) {
                                if (resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_LOST)) {
                                    showMessage("", resultBaseBean.getMsg());
                                    playVoice(MyConstant.SP_LOST_CARD);
                                } else if (resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_CANCELED)) {
                                    showMessage("", resultBaseBean.getMsg());
                                    playVoice(MyConstant.SP_CANCEL_CARD);
                                } else {
                                    showMessage("", "非法卡");
                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                }
                                playCardError = true;
                                isduringOrderNo = "";
                                return;
                            }

                            QueryCardResultInfo queryCardResultInfo = gson.fromJson(body, new TypeToken<QueryCardResultInfo>() {
                            }.getType());
                            if (queryCardResultInfo != null) {
                                if (queryCardResultInfo.isResult()) {
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
                                                            showMessage("", "当前卡已挂失");
                                                            playVoice(MyConstant.SP_LOST_CARD);
                                                            break;
                                                        case "2":
                                                            showMessage("", "当前卡已注销");
                                                            playVoice(MyConstant.SP_CANCEL_CARD);
                                                            break;
                                                    }
                                                    playCardError = true;
                                                    isduringOrderNo = "";
                                                    return;
                                                }
                                                info = new TfCardInfo();
                                                info.setMI_ID(cardBean.getMI_ID());
                                                QueryCardResultInfo.DataBean.MemberBean bean = queryCardResultInfo.getData().getMember();
                                                if (bean != null) {
                                                    memberInfo = new TfMemberInfo();
                                                    memberInfo.setMI_NAME(bean.getMI_NAME());
                                                    memberInfo.setMI_PHONE(bean.getMI_NO());
                                                    memberInfo.setMI_TYPE(bean.getMI_TYPE());
                                                }
                                                if (queryCardResultInfo.getData().getAccount() != null && queryCardResultInfo.getData().getAccount().size() > 0) {
                                                    for (QueryCardResultInfo.DataBean.AccountBean record : queryCardResultInfo.getData().getAccount()) {
                                                        //<editor-fold desc="cash">
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
                                                            //</editor-fold>
                                                        }
                                                    }
                                                }
                                                if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                                                    // Log.e("url","isduringPayment5:" + isduringPayment);
                                                    onMainThreadRunCalaulater(icId, info, memberInfo, cashRecord, subsidyRecord);
                                                } else {
                                                    isduringOrderNo = "";
                                                    playCardError = true;
                                                    //  isduringPayment = false;
                                                    showMessage("", "非法卡");
                                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                                }
                                            }
                                        }

                                    } else {
                                        Log.d("look", "queryCardResultInfo data is null");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            isduringOrderNo = "";
                            //   isduringPayment = false;
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            requestCard = false;
                            playCardError = true;
                            showMessage("", "非法卡");
                            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                            e.printStackTrace();
                        }
                    }
                });
//            }
//        }, 11000);
            } else {//线下支付
                Log.e("url", "线下支付");
                TfCardInfo info = null;
                TfMemberAccountRecord cashRecord = null;
                TfMemberAccountRecord subsidyRecord = null;
                TfMemberInfo memberInfo = null;
                List<TfCardInfo> cardInfoList = pDaoSession.queryBuilder(TfCardInfo.class)
                        .where(TfCardInfoDao.Properties.IC_ID.eq(icId))
                        .build()
                        .list();

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
                                showMessage("", "当前卡已挂失");
                                playVoice(MyConstant.SP_LOST_CARD);
                                break;
                            case "2":
                                showMessage("", "当前卡已注销");
                                playVoice(MyConstant.SP_CANCEL_CARD);
                                break;
                        }
                        playCardError = true;
                        isduringOrderNo = "";
                        return;
                    }
                    // memberinfo
                    // account status : 0 normal 1 freeze
                    Log.e("look", "getMemberCardBalance info.getMI_ID():" + info.getMI_ID());
                    List<TfMemberInfo> memberInfoList = pDaoSession.queryBuilder(TfMemberInfo.class)
                            .where(TfMemberInfoDao.Properties.MI_ID.eq(info.getMI_ID()))
                            .build()
                            .list();
                    if (memberInfoList == null || memberInfoList.isEmpty()) {
                        showMessage("", "会员不存在");
                        isduringOrderNo = "";
                        playCardError = true;
                        return;
                    }
                    memberInfo = memberInfoList.get(0);
                    List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
                            //TODO
                            .where(TfMemberAccountRecordDao.Properties.MI_ID.eq(info.getMI_ID()))
                            .build()
                            .list();
                    if (accountRecordList.isEmpty() || accountRecordList == null) {
                        isduringOrderNo = "";
                        cashRecord = null;
                    } else {
                        Log.e("look", "getMemberCardBalance accountRecordList.size():" + accountRecordList.size());
                        for (TfMemberAccountRecord record : accountRecordList) {
                            Log.e("look", "getMemberCardBalance record.getACCOUNT_TYPE():" + record.getACCOUNT_TYPE());
                            if (record.getACCOUNT_TYPE().equals("0")) {
                                cashRecord = record;
                            } else if ((record.getACCOUNT_TYPE().equals("1"))) {
                                subsidyRecord = record;
                            }
                        }
                        if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                            try {
                                calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                cashRecord = null;
                                subsidyRecord = null;
                                isduringOrderNo = "";
                                return;
                            }
                        }
                    }
                }
                playCardError = true;
                showMessage("", "非法卡");
                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
            }
        } catch (Exception e) {
            requestCard = false;
            e.printStackTrace();
        }
    }

    public void onMainThreadRunCalaulater(final String icId, final TfCardInfo info, final TfMemberInfo memberInfo, final TfMemberAccountRecord cashRecord, final TfMemberAccountRecord subsidyRecord) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
            }
        });

    }

    public void calcurlateMemberBalance(String icId, TfCardInfo info, TfMemberInfo memberInfo, TfMemberAccountRecord cashRecord, TfMemberAccountRecord subsidyRecord) {
        Log.d("pay", "calcurlateMemberBalance....");
        try {
            float cashBalance = 0, subsidyBalance = 0;
            if (cashRecord != null) {
                if (subsidyRecord == null) {
                    showMessage("", "该会员无补贴账户");
                    isduringOrderNo = "";
                    playCardError = true;
                    /// mainActivity.showPay("wait","该会员无补贴账户");
                    mainActivity.showPay(mainActivity, "card", "该会员无补贴账户", "", "", "");
                    //   isduringPayment = false;
                    return;
                }
                if (cashRecord.getACCOUNT_STATUS().equals("1")
                        || subsidyRecord != null
                        && subsidyRecord.getACCOUNT_STATUS().equals("1")) {
                    showMessage("", "当前账户已冻结");
                    //mainActivity.showPay("wait","当前账户已冻结");
                    isduringOrderNo = "";
                    playCardError = true;
                    mainActivity.showPay(mainActivity, "card", "当前账户已冻结", "", "", "");
                    //   isduringPayment = false;
                    return;
                }
                cashBalance = Float.parseFloat(cashRecord.getBALANCE());
                subsidyBalance = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                Log.d("frost", "cashBalance:" + cashBalance);
                Log.d("frost", "subsidyBalance:" + subsidyBalance);
                showMemberInfo(memberInfo, MyConstant.gFormat.format(subsidyBalance + cashBalance));

                start_time = System.currentTimeMillis();
                //Log.e("url","isduringPayment7:" + isduringPayment);
                if (!isPayment) {
                    /**
                     * 折扣后金额
                     */
                    float payTotalPrice = 0;

                    // 全民以及会员折扣
                    String mi_type = memberInfo.getMI_TYPE();//会员类型
                    int dis_count = getDisCountForMember(mi_type);//会员折扣
                    Log.e("pay", "mi_type:" + mi_type + "  会员折扣 dis_count:" + dis_count);
                    if (dis_count == 100) {
                        dis_count = getDisCountForAll();//全员折扣
                        Log.e("pay", "全员折扣 for all dis_count:" + dis_count);
                    }
                    menuListAdapter.calDisCount(dis_count, true);

                    List<OperationMenu> data = menuListAdapter.getData();
                    for (OperationMenu datum : data) {
                        payTotalPrice += datum.getTotalPrice();//datum.getUnitPrice() * datum.getCount();
                    }

//                    // 0 元账单
                    if (payTotalPrice == 0) {
                        //    isduringPayment = false;
                        isduringOrderNo = "";
                        mainActivity.showPay(mainActivity, "card", "", txMemberPhone.getText().toString(), txMemberName.getText().toString(), txMemberBalance.getText().toString());
                        return;
                    }
                    Log.i("pay", "card->payPrice   折扣后的值为->" + payTotalPrice);
                    // 0 社会餐饮 先扣现金再扣补贴，1 团餐 先扣补贴再扣现金
                    if (mainActivity.pStoreConfig == null) {
                        List<StoreConfig> storeConfigList = pDaoSession.queryBuilder(StoreConfig.class)
                                .build()
                                .list();
                        if (!storeConfigList.isEmpty() && storeConfigList != null) {
                            mainActivity.pStoreConfig = storeConfigList.get(0);
                        } else {
                            isduringOrderNo = "";
                            //       isduringPayment = false;
                            playCardError = true;
                            showMessage("提示", "餐饮类型未知");
                            mainActivity.showPay(mainActivity, "card", "餐饮类型未知", "", "", "");
                            return;
                        }
                    }

                    switch (mainActivity.pStoreConfig.getSTORE_TYPE()) {
                        case "0"://社会快餐
                            cashBalance = cashBalance - payTotalPrice;

                            cashRecord.setBALANCE(MyConstant.gFormat.format(cashBalance));
                            // 现金扣完，扣补贴 付款
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
                            subsidyBalance = subsidyBalance - payTotalPrice;
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
                    // Log.e("url","isduringPayment8:" + isduringPayment);
                    if (accoutBalance < 0) {
                        // 余额不足
                        isduringOrderNo = "";
                        playCardError = true;
                        showMessage("会员卡支付", "余额不足");
                        playVoice(MyConstant.SP_MEDIA_MONEY_NOT_ENOUGH);
                        // mainActivity.showPay("wait","余额不足");
                        mainActivity.showPay(mainActivity, "card", "余额不足", "", "", "");
                        return;
                    } else {
                        //  Log.e("url","isduringPayment9:" + isduringPayment);
                        //start_time = System.currentTimeMillis();
                        // 支付成功
                        isPayment = true;
//                        isduringPayment = false;
//                        Log.e("url","isduringPayment10:" + isduringPayment);
                        payWay = PAY_MEMBER_CARD;
                        Log.e("pay", "payWay = PAY_MEMBER_CARD:" + payWay);

                        Log.e("url", "PAY_MEMBER_CARD");
                        curOrderNo = MyUtil.getPrimaryValue();
                        //60 --15731941977441411391
                        //75 -- 15731942201021841179
                        Log.e("url", "curOrderNo:" + curOrderNo);

                        // 更新人数区记录
                        mTotalPersonCount += 1;
                        mCurMealPersonCount += 1;
                        gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();

                        Log.e("url", "支付成功:1");
                        //折扣后金额
                        float temp_f = Float.parseFloat(MyConstant.gFormat.format(payTotalPrice));
                        // 余额
                        float temp_balance = Float.parseFloat(MyConstant.gFormat.format(accoutBalance));
                        // TODO: 2019/11/7 创建一条消费信息
                        createConsumeInfo(temp_f, temp_balance, info.getMI_ID(), cashRecord.getACCOUNT_ID(), icId, menuListAdapter.getData().size() + "", 1);
                        txMsgNotify.setText("支付成功");
                        isduringOrderNo = "";
                        showMessage("会员卡支付", "支付成功");
                        playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                        // 更新余额
                        txMemberBalance.setText(MyConstant.gFormat.format(accoutBalance));
                        // nfc 会员卡支付

                        float totalPrices = 0;
                        for (OperationMenu menu : menuListAdapter.getData()) {
                            totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                        }
                        mainActivity.showPay(mainActivity, "card", "已支付 " + MyConstant.gFormat.format(totalPrices), txMemberPhone.getText().toString(), txMemberName.getText().toString(), txMemberBalance.getText().toString());
                        //打印
                        printerReceipt(memberInfo);
                        String count = txPayCount.getText().toString();
                        // 修改数据库中的会员余额
                        pDaoSession.update(cashRecord);
                        pDaoSession.update(subsidyRecord);
                        stopScan();
//                        if (mFixedPrice > 0) {
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    refresh();
//                                }
//                            },800);
//                        }
                        return;
                    }
                }
            } else {
                showMessage("", "非法卡");
                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
            }
            // 交易不成功，则通过这个函数重新打开灯
            if (!isPayment && menuListAdapter.getData().size() > 0) {
                setupScan();
            }
        } catch (Exception e) {
            Log.e("CrashHandler", "calcurlateMemberBalance.." + e.getMessage());
        }
    }

    /**
     * 根据icid计算得出会员卡号
     *
     * @param smartCardId
     * @return
     */
    private long computeMemberId(String smartCardId) {
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

    /**
     * 展示IC卡信息
     *
     * @param memberInfo
     * @param balance
     */
    public void showMemberInfo(final TfMemberInfo memberInfo, final String balance) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (memberInfo != null) {
//            if (llCash.getVisibility() == View.VISIBLE) {
                    llCash.setVisibility(View.GONE);
                    llMemberCard.setVisibility(View.VISIBLE);
                    txMemberName.setText(memberInfo.getMI_NAME());
                    txMemberPhone.setText(memberInfo.getMI_PHONE());
                    txMemberBalance.setText(balance);
//            }
                }
            }
        });

    }

    public void showCashInfo() {
        if (llCash.getVisibility() == View.GONE && isPayment) {
            llCash.setVisibility(View.VISIBLE);
            hiddenMemberCardInfo();
        }
    }

    /**
     * 输入的金额正确则返回true，反之false
     *
     * @return
     */
    private boolean judgeInputPrice() {
        String inputStr = etInputDishesName.getText().toString();
        String curContent[] = inputStr.split("\\.");
        float price;
        try {
            price = Float.parseFloat(inputStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showMessage("", "请输入正确金额！");
            return false;
        }
        if (inputStr.isEmpty() || price == 0) {
            showMessage("", "请输入正确金额！");
            return false;
        } else if (curContent.length >= 2) {
            if (curContent[1].length() >= 2) {
                showMessage("提示", "请输入正确金额！");
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否达到最低消费标准
     *
     * @return
     */
    public boolean judgeFixedPrice() {
        //float totalPrice = Float.parseFloat(txPayTotalPrice.getText().toString());
        if (mFixedPrice > 0f) {
            return true;
        } else if (mFixedPrice == 0f) {
            return true;
        }
        return false;
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
                        //支付成功-----发送 refresh 命令
                        if (type == MyConstant.SP_MEDIA_CONSUME_SUCCESS) {
                            gOperationHandler.removeMessages(REFRESH);
                            if (menuListAdapter.isExistFixedPrice()) {//固价
                                gOperationHandler.sendEmptyMessage(REFRESH);
                            } else {//非固价
                                gOperationHandler.sendEmptyMessageDelayed(REFRESH, 60 * 1000);
                            }
                        }
                    }
                });
                mPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * 打印产品信息小票中的重要部分
     *
     * @param printer
     * @param word
     * @param storeAddr
     */
    public void checkProductMsg(BuiltInPrinter printer, String word, String storeAddr) {
        try {
            StringBuilder result = new StringBuilder();
            int count = 0;
            float totalPrice = 0;
            int discountType = 0;
            List<OperationMenu> operationMenuList = menuListAdapter.getData();
            for (int i = 0; i < operationMenuList.size(); i++) {
                OperationMenu menu = operationMenuList.get(0);
                if (payWay == PAY_MEMBER_CARD) {
                    discountType = 1;
                }
                //String unitPrice = calculateDisCountPrice(MyConstant.gFormat.format(menu.getUnitPrice()), discountType);
                checkName(menu.getDishNmae(), result);
                checkName(String.valueOf(menu.getCount()), result);
                checkName(MyConstant.gFormat.format(menu.getUnitPrice()), result);
                //checkName(MyConstant.gFormat.format(menu.getCount() * Float.parseFloat(unitPrice)), result);
                checkName(MyConstant.gFormat.format(menu.getUnitDisPrice()), result);

                printer.sendData(result.toString());
                result.setLength(0);
                count += menu.getCount();
                totalPrice += menu.getCount() * menu.getUnitPrice();
                //try {
                //	Thread.sleep(100);
                //} catch(InterruptedException ignored) {
                //}
            }
            printer.sendData("\n--------------------------------");
            printer.sendData(("\n总数量：" + count));
            printer.sendData(("\n总金额：" + MyConstant.gFormat.format(totalPrice)));
            printer.sendData(("\n支付方式：" + getCurPayWay()));
            printer.sendData(("\n" + word));
            printer.sendData(("\n折扣：" + getDiscountRate(discountType)));
            printer.sendData((("\n地址：" + storeAddr)));
        } catch (SmartPosException e) {
            Log.e(TAG, "checkProductMsg: ", e);
            e.printStackTrace();
        }
    }


    /**
     * 根据字段长度，选择打印时文字间的空隙
     *
     * @param name
     * @param sb
     */
    public void checkName(String name, StringBuilder sb) {
        if (name.length() < 4) {
            sb.append(name + "\t\t");
        } else if (name.length() == 0) {
            sb.append("暂无商品\t");
        } else if (name.length() >= 4) {
            sb.append(name + "\t");
        }
    }

    /**
     * 判断支付方式
     *
     * @return
     */
    public String getCurPayWay() {
        String way = "";
        switch (payWay) {
            case PAY_CASH:
                way = "现金";
                break;
            case PAY_WECHAT:
                way = payName;
                break;
            case PAY_ALIPAY:
                way = payName;
                break;
            case PAY_MEMBER_CARD:
                way = "会员卡";
                break;
        }
//        payWay = -1;
        return way;
    }

    @Override
    public void showMessage(String title, final String msg) {
        gOperationHandler.obtainMessage(NOTIFY_MSG_CHANGE, msg).sendToTarget();
    }

    public void copy(String data) {
        try {
            InputStream input = null;
            FileOutputStream output;
            try {

                File downloadFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Download");
                if (!downloadFile.exists()) {
                    downloadFile.mkdirs();
                }
                File dbFile = new File(downloadFile.getPath() + "/data.txt");
                if (dbFile.exists()) {
                    dbFile.delete();
                }
//                input = new BufferedInputStream()
                output = new FileOutputStream(dbFile);
                output.write(data.getBytes());
//                byte[] buffer = new byte[8192];
//                int count = 0;
//                while ((count = input.read(buffer)) != -1) {
//                    output.write(buffer, 0, count);
//                }
                output.flush();
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*-----------------------------以下为扫码-------------------------------------*/

    /**
     * 按键启动扫描功能
     */
    public void setupScan() {
//        Log.e("pay","setupScan");
        if (!isOpenScan) {
            isOpenScan = true;
            try {
                if (isFirst) {
                    openScan();
                    isFirst = false;
                    return;
                }
                if (captureActivityHandler != null) {
                    captureActivityHandler.restartPreviewAndDecode();
                } else {
                    if (hasSurface) {
                        initCamera(surfaceHolder);
                    } else {
                        Log.e("pay", "setupScan surfaceHolder.addCallback(this);");
                        surfaceHolder.addCallback(this);
                        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                    }
                }
                scan_start_time = System.currentTimeMillis();
            } catch (Exception e) {

            }
        }
    }

    public void stopScan() {
//        Log.e("pay","stopScan");  
        if (isOpenScan == true) {
            isOpenScan = false;
            if (captureActivityHandler != null) {
                captureActivityHandler.quitSynchronously();
                captureActivityHandler = null;
            }
            CameraManager.get().closeDriver();

            scan_start_time = 0;
        }
    }

    public void reStartScan() {
        stopScan();
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupScan();
                    }
                }, 1000);
            }
        });

    }


    /**
     * 扫描初始化
     */
    private void openScan() {
        Log.e("pay", "openScan");
        // 初始化相机画布
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            Log.e("pay", "openScan  surfaceHolder.addCallback(this);");
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        isOpenScan = true;

        decodeFormats = null;
        characterSet = null;
        // 声音
        playBeep = true;
        // 初始化音频管理器
        AudioManager audioService = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // 振动
        vibrate = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("url", "onResume");
//        if (!hasResume) {
//            hasResume = true;
//            setupScan();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopScan();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopScan();
        // if (!appTitle.getText().equals(getString(R.string.operate))) {
        if (txFixedPrice.getVisibility() == View.VISIBLE) {
            txFixedPrice.setVisibility(View.INVISIBLE);
            mFixedPrice = 0f;
        }
        menuListAdapter.deleteFixedPrice();
        refresh();
        // }
    }

    public boolean isPayMsg = false;

    /**
     * 处理反扫接口数据
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        Log.e("frost", "handleDecode:11 ");
        if (result == null) {
            return;
        }

        etInputDishesName.requestFocus();

        if (result.getText().substring(0, 2).equals("18")) {

        } else if (result.getText().substring(0, 2).equals("28")) {

        } else if (result.getText().substring(0, 2).equals("13")) {

        } else {
            List<CommodityRecord> commodityRecordList = pDaoSession.queryBuilder(CommodityRecord.class)
                    .where(CommodityRecordDao.Properties.CI_NO.eq(result.getText()))
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
            etInputDishesName.setText("");
            return;
        }

        if (result.getText().substring(0, 2).equals("18")) {
            if (isPayment) {
                refresh();
            }
            getMemberCardBalance(result.getText());
            etInputDishesName.setText("");
            return;
        }

        if (!MyUtil.networkState) {
            showMessage("", "离线无法支付");
            mainActivity.showPay(mainActivity, "card", "离线无法支付", "", "", "");
            return;
        }

        if (playWxAliError) {
            showMessage("", "请重新刷新");
            mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
            return;
        }

        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
            showMessage("", "正在支付中");
            return;
        }

//        if(isPayMsg){
//            showMessage("", "该单已支付");
//            mainActivity.showPay(mainActivity,"card","该单已支付","","","");
//            return;
//        }

        if (isPayment) {
            showMessage("", "该单已支付");
            mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
            return;
        }

        if (isduringPayment) {
            showMessage("", "订单正在支付中");
            mainActivity.showPay(mainActivity, "card", "订单正在支付中", "", "", "");
            return;
        }


        playBeepSoundAndVibrate();
        final String resultString = result.getText();
        Log.e("frost", "handleDecode: " + resultString);

        if (oldCode.isEmpty() || oldCode == null || !resultString.equals(oldCode)) {
            oldCode = resultString;
        } else {
            Log.e("frost", "can't repeat payment with same code!!!");
            return;
        }

        String payPrice = txPayTotalPrice.getText().toString();
        //payPrice = calculateDisCountPrice(payPrice, 0);
//        if (MyUtil.getMacAddress().equals("EE3B8BFF5290")
//				|| Build.SERIAL.equals("123456")) {
//			Log.d(TAG,"DEBUG phone payment");
//            payPrice = "0.01";
//        }
        payPrice = "0.01";


        float payTotalPrice = 0;
        for (OperationMenu datum : menuListAdapter.getData()) {
            payTotalPrice += datum.getTotalPrice();//datum.getUnitPrice() * datum.getCount();
        }

        if (payTotalPrice == 0) {
            etInputDishesName.setText("");
            return;
        }


        if (resultString != null && resultString.trim().length() > 0) {
            Gson gson = new Gson();
            switch (resultString.substring(0, 2)) {
                case "28":
                    payWay = PAY_ALIPAY;
                    payName = "支付宝";
                    break;
                case "13":
                    payWay = PAY_WECHAT;
                    payName = "微信";
            }

            isduringPayment = true;
            isduringOrderNo = "" + curOrderNo;

            showMessage("", payName + "支付中...");

            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            // 反扫接口处理
            String url = "http://" + ip + ":" + port + "/k-occ/pay/reverse/scan";

            String clienCode = "", storeCode = "";
            try {
                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
            } catch (Exception e) {
                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            }

            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("authCode", resultString);
            jsonData.addProperty("requestNum", curOrderNo);
            jsonData.addProperty("transamt", payPrice);
            Log.e("frost", "handleDecode jsonData:" + jsonData.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("data", jsonData.toString())
                    .build();


            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();


            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Log.e("frost", "handleDecode....2");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    isPayment = false;
                    isduringPayment = false;
                    playWxAliError = true;

                    showMessage("", "支付错误");
                    mainActivity.showPay(mainActivity, "card", "支付错误", "", "", "");
                    pay_result_select = 1000;
                    isduringOrderNo = "";
                    final String count = menuListAdapter.getData().size() + "";
                    createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                    reStartScan();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Gson gson = new Gson();
                        String body = response.body().string();
                        Log.e("frost", "handleDecode....3...body:" + body);

                        JSONObject jsonRes = new JSONObject(body);
                        if (jsonRes != null) {
                            String code = jsonRes.getString("code");
                            String orderId = jsonRes.optString("data");
                            Log.e("frost", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);

                            if ("0000".equals(code)) {
                                // true 支付成功
                                isPayment = true;
                                isduringPayment = false;
                                isduringOrderNo = "";

                                // 更新人数区记录
                                mTotalPersonCount += 1;
                                mCurMealPersonCount += 1;
                                gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();

                                printerReceipt(null);
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 1);

                                Log.e("frost", "success payWay:" + payWay + " payName...." + payName);
                                playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        float totalPrices = 0;
                                        for (OperationMenu menu : menuListAdapter.getData()) {
                                            totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                                        }
                                        // mainActivity.showPay("wx&zfb","已支付 " + MyConstant.gFormat.format(totalPrices));
                                        mainActivity.showPay(mainActivity, "card", "已支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
                                    }
                                });
                                showMessage("", "支付成功");
//                                if (mFixedPrice > 0) {
//                                    mainActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            new Handler().postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    refresh();
//                                                }
//                                            },800);
//                                        }
//                                    });
//                                }

                            } else if ("0001".equals(code) || "0002".equals(code)) {
                                isPayment = false;
                                isduringPayment = false;
                                Log.e("frost", "weixin or alipay fail code:" + code);
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);

                                isduringOrderNo = "";
                                reStartScan();
                            } else if ("0004".equals(code)) {
                                isPayment = false;
                                isduringPayment = false;

                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);

                                Log.e("frost", "weixin or alipay CCR_STATUS2 code:" + code);
                                isduringOrderNo = "";
                                reStartScan();
                            } else {
                                isPayment = true;
                                isduringPayment = false;
                                isduringOrderNo = "";
                                pay_result_select = 0;
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                                Log.e("frost", "weixin or alipay other code:" + code);
                            }

                            String msg = jsonRes.optString("msg");
                            if (msg != null && msg.startsWith("交易")) {
                                msg = msg.replaceAll("交易", "支付");
                            }

                            if (msg != null && msg.length() > 0) {
                                showMessage("", msg);
                                isPayMsg = true;
                                if ("支付成功".equals(msg)) {
                                    isPayment = true;
                                } else {
                                    isPayment = false;
                                }
                                isduringPayment = false;
                            }
                            Log.e("pay", "--87-msg:" + msg);
                        } else {
                            isPayment = false;
                            isduringPayment = false;
                        }

                    } catch (Exception e) {
                        isPayment = false;
                        isduringPayment = false;
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    boolean playWxAliError = false;

    public void handleBarcodeScanner(String resultString) {
        if (resultString == null) {
            return;
        }
        etInputDishesName.requestFocus();

        if (resultString.substring(0, 2).equals("18")) {

        } else if (resultString.substring(0, 2).equals("28")) {

        } else if (resultString.substring(0, 2).equals("13")) {

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
            etInputDishesName.setText("");
            return;
        }

        if (resultString.substring(0, 2).equals("18")) {
            if (isPayment) {
                refresh();
            }
            getMemberCardBalance(resultString);
            etInputDishesName.setText("");
            return;
        }

        if (!MyUtil.networkState) {
            showMessage("", "离线无法支付");
            // mainActivity.showPay("wait","离线无法支付");
            mainActivity.showPay(mainActivity, "card", "离线无法支付", "", "", "");
            return;
        }

        if (playWxAliError) {
            showMessage("", "请重新刷新");
            mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
            return;
        }

        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
            showMessage("", "正在支付中");
            return;
        }

//        if(isPayMsg){
//            showMessage("", "该单已支付");
//            mainActivity.showPay(mainActivity,"card","该单已支付","","","");
//            return;
//        }

        if (isPayment) {
            showMessage("", "该单已支付");
            //mainActivity.showPay("wait","该单已支付");
            mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
            return;
        }

        if (isduringPayment) {
            showMessage("", "订单正在支付中");
            mainActivity.showPay(mainActivity, "card", "订单正在支付中", "", "", "");
            return;
        }


        //playBeepSoundAndVibrate();
        Log.d("pay", "handleBarcodeScanner: " + resultString);
        //清除付款码数字
        gOperationHandler.sendEmptyMessageDelayed(CLEAR_SCANNER_NUM, MyConstant.SANNER_CLEAR_TIME_);
        if (oldCode.isEmpty() || oldCode == null || !resultString.equals(oldCode)) {
            oldCode = resultString;
        } else {
            Log.d(TAG, "can't repeat payment with same code!!!");
            return;
        }

        String payPrice = txPayTotalPrice.getText().toString();
        int dis_count = getDisCountForAll();//全员折扣
        Log.i(TAG, "dis_count: " + dis_count);
        if (dis_count < 100) {//
            menuListAdapter.calDisCount(dis_count);
            payPrice = calculateDisCountPrice(payPrice, dis_count);
            Log.i("pay", "scan->payPrice   折扣后的值为->" + payPrice);
        }
//        payPrice = calculateDisCountPrice(payPrice, 0);
//        if (MyUtil.getMacAddress().equals("EE3B8BFF5290")
//				|| Build.SERIAL.equals("123456")) {
//			Log.d(TAG,"DEBUG phone payment");
//            payPrice = "0.01";
//        }
//        payPrice = "0.01";
        float payTotalPrice = 0;
        for (OperationMenu datum : menuListAdapter.getData()) {
            payTotalPrice += datum.getTotalPrice();//datum.getUnitPrice() * datum.getCount();
        }

        if (payTotalPrice == 0) {
            etInputDishesName.setText("");
            return;
        }


        if (resultString != null && resultString.trim().length() > 0) {
            Gson gson = new Gson();
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

            isduringPayment = true;
            isduringOrderNo = "" + curOrderNo;

            showMessage("", payName + "支付中...");

            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String url = "http://" + ip + ":" + port + "/k-occ/pay/reverse/scan";

            String clienCode = "", storeCode = "";
            try {
                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
            } catch (Exception e) {
                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            }

            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("authCode", resultString);
            jsonData.addProperty("requestNum", curOrderNo);
            jsonData.addProperty("transamt", payPrice);
            Log.e("pay", "handleBarcodeScanner jsonData:" + jsonData.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("data", jsonData.toString())
                    .build();


            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .build();


            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Log.e("pay", "handleBarcodeScanner....2");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    playWxAliError = true;
                    isPayment = false;
                    isduringPayment = false;
                    final String count = menuListAdapter.getData().size() + "";
                    showMessage("", "支付错误");
                    //mainActivity.showPay("wait","支付错误");
                    mainActivity.showPay(mainActivity, "card", "支付错误", "", "", "");
                    pay_result_select = 1000;
                    isduringOrderNo = "";
                    createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                    reStartScan();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Gson gson = new Gson();
                        String body = response.body().string();
                        Log.e("pay", "handleBarcodeScanner....3...body:" + body);

                        JSONObject jsonRes = new JSONObject(body);
                        if (jsonRes != null) {
                            String code = jsonRes.getString("code");
                            String orderId = jsonRes.optString("data");
                            Log.e("pay", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);

                            if ("0000".equals(code)) {
                                // true 支付成功
                                isPayment = true;
                                isduringPayment = false;
                                isduringOrderNo = "";

                                // 更新人数区记录
                                mTotalPersonCount += 1;
                                mCurMealPersonCount += 1;
                                gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();

                                printerReceipt(null);
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 1);

                                Log.e("pay", "success payWay:" + payWay + " payName...." + payName);
                                playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                                showMessage("", "支付成功");
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        float totalPrices = 0;
                                        for (OperationMenu menu : menuListAdapter.getData()) {
                                            totalPrices += menu.getTotalPrice(); //menu.getUnitPrice() * menu.getCount();
                                        }
                                        //  mainActivity.showPay("wx&zfb","已支付 " + MyConstant.gFormat.format(totalPrices));
                                        mainActivity.showPay(mainActivity, "card", "已支付 " + MyConstant.gFormat.format(totalPrices), "", "", "");
                                    }
                                });
//                                if (mFixedPrice > 0) {
//                                    mainActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            new Handler().postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    refresh();
//                                                }
//                                            },400);
//                                        }
//                                    });
//
//                                }

                            } else if ("0001".equals(code) || "0002".equals(code)) {
                                isPayment = false;
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                                isduringPayment = false;
                                Log.e("pay", "weixin or alipay fail code:" + code);
                                isduringOrderNo = "";
                                reStartScan();
                            } else if ("0004".equals(code)) {
                                isPayment = false;
                                isduringPayment = false;
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                                Log.e("pay", "weixin or alipay CCR_STATUS2 code:" + code);
                                isduringOrderNo = "";
                                reStartScan();
                            } else {
                                isPayment = true;
                                isduringPayment = false;
                                isduringOrderNo = "";
                                pay_result_select = 0;
                                final String count = menuListAdapter.getData().size() + "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(txPayTotalPrice.getText().toString()))), 0, "0", "0", "", count, 0);
                                Log.e("pay", "weixin or alipay other code:" + code);
                            }

                            String msg = jsonRes.optString("msg");
                            if (msg != null && msg.startsWith("交易")) {
                                msg = msg.replaceAll("交易", "支付");
                            }

                            if (msg != null && msg.length() > 0) {
                                showMessage("", msg);
                                isPayMsg = true;
                                if ("支付成功".equals(msg)) {
                                    isPayment = true;
                                } else {
                                    isPayment = false;
                                }
                                isduringPayment = false;
                            }
                            Log.e("pay", "--87-msg:" + msg);
                        } else {
                            isPayment = false;
                            isduringPayment = false;
                        }

                    } catch (Exception e) {
                        isPayment = false;
                        isduringPayment = false;
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    /**
     * 支付结果验证
     *
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
            // Log.e("url","payResultSelect%30");
            temp_order_no = curOrderNo;
        } else {
            // Log.e("url","payResultSelect:" + pay_result_select);
            if (pay_result_select % 5 != 0) {
                return;
            }
            //  Log.e("url","payResultSelect%5");
            List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                    .where(
                            TfConsumeCardRecordDao.Properties.CCR_STATUS.eq(0),
                            //TfConsumeCardRecordDao.Properties.ISM_STATUS.eq(0),
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

        //Log.e("url","payResultSelect01:temp_order_no:" + temp_order_no.length());

        Log.e("pay", "payResultSelect....1 pay_result_select:" + pay_result_select + " temp_order_no:" + temp_order_no);
        if (temp_order_no != null && temp_order_no.length() > 0) {

            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String clienCode = "", storeCode = "";
            try {
                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
            } catch (Exception e) {
                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            }
            String url = "http://" + ip + ":" + port + "/k-occ/pay/result/select";

            final String req_order_no = temp_order_no;

            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("payType", payWay);
            jsonData.addProperty("corId", req_order_no);

            Log.e("pay", "payResultSelect jsonData:" + jsonData.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("data", jsonData.toString())
                    .build();

            Log.e("url", "payResultSelect1");
            OkHttpClient client = new OkHttpClient.Builder().build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Log.e("url", "payResultSelect2");
            Log.e("pay", "payResultSelect....2");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("url", "result/select:onFailure");
                    Log.e("url", "result/select:onFailure:" + e.getMessage());
                    Log.d("pay", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Log.e("url", "result/select:onResponse");
                        Gson gson = new Gson();
                        String body = response.body().string();
                        Log.e("pay", "payResultSelect....3...body:" + body);

                        JSONObject jsonRes = new JSONObject(body);
                        if (jsonRes != null) {
                            String code = jsonRes.getString("code");
                            String orderId = jsonRes.optString("data");
                            Log.e("pay", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);

                            int CCR_STATUS = 0;

                            if ("0000".equals(code)) {
                                CCR_STATUS = 1;
                                pay_result_select = 1000;

                                if (orderId != null && orderId.equals(curOrderNo)) {
                                    // 更新人数区记录
                                    mTotalPersonCount += 1;
                                    mCurMealPersonCount += 1;
                                    gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();

                                    isPayment = true;
                                    printerReceipt(null);
                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);

                                    String msg = jsonRes.optString("msg");
                                    if (msg != null && msg.length() > 0) {
                                        showMessage("", msg);
                                    }
                                }
                            } else if ("0003".equals(code)) {

                            } else {
                                pay_result_select = 1000;
                                CCR_STATUS = 2;
                                if (isduringOrderNo != null && isduringOrderNo.equals(curOrderNo)) {
                                    isPayment = false;
                                    isduringOrderNo = "";
                                    reStartScan();
                                }
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
                                } else {

                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    private static final long VIBRATE_DURATION = 200L;
    public final static int REQUEST_CAMERA = 1;
    public final static int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private CaptureActivityHandler captureActivityHandler;// 消息中心
    private ViewfinderView viewfinderView;// 绘制扫描区域
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    //    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔
    private String oldCode = "";

    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
//        if (captureActivityHandler == null) {
//            captureActivityHandler = new CaptureActivityHandler(this, decodeFormats,
//                    characterSet);
//        }
        if (isFirst) {
            isFirst = false;
            stopScan();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
//        CameraManager.get().stopPreview();
        CameraManager.get().closeDriver();
        Camera camera = CameraManager.getCamera();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return captureActivityHandler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 声音设置
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
//            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * 结束后的声音
     */
    private void playBeepSoundAndVibrate() {
        Log.d(TAG, "playBeepSoundAndVibrate");
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        int permissionCheck1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

}
