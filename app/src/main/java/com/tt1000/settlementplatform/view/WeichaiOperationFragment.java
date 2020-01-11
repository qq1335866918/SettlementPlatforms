package com.tt1000.settlementplatform.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.OperationMenuListAdapter;
import com.tt1000.settlementplatform.adapter.WeiCaiOperationWaterAdapter;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.BaseBean;
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
import com.tt1000.settlementplatform.bean.operation.WeiCaiWaterInfo;
import com.tt1000.settlementplatform.bean.pay.InlineUpdateInfo;
import com.tt1000.settlementplatform.bean.result_info.OnlineUpdateResultInfo;
import com.tt1000.settlementplatform.bean.result_info.QueryCardResultInfo;
import com.tt1000.settlementplatform.bean.result_info.ResultBaseBean;
import com.tt1000.settlementplatform.bean.result_info.UpdateResultInfo;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.utils.SSLSocketClient;
import com.tt1000.settlementplatform.zxing.camera.CameraManager;
import com.tt1000.settlementplatform.zxing.decoding.CaptureActivityHandler;
import com.tt1000.settlementplatform.zxing.view.ViewfinderView;

import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static com.tt1000.settlementplatform.utils.MyUtil.uploadingError;

@SuppressLint("ValidFragment")
public class WeichaiOperationFragment extends BaseFragment implements View.OnClickListener, SurfaceHolder.Callback {
    private ListView business_list_water;
    private TextView business_num_people;
    private TextView business_state;
    private TextView business_price;
    private TextView business_vip_name;
    private TextView business_vip_no;
    private TextView business_balance;
    private EditText business_put_price;
    private Button business_num0;
    private Button business_num1;
    private Button business_num2;
    private Button business_num3;
    private Button business_num4;
    private Button business_num5;
    private Button business_num6;
    private Button business_num7;
    private Button business_num8;
    private Button business_num9;
    private Button business_num_dot;
    private ImageButton business_refresh;
    private ImageButton business_ok;
    public long start_time = 0;
    private AlertDialog backDialog;
    private TextView txAppTitle;
    private ImageButton iv_power;

    //金额
    private String payMsg;
    // 实体卡号，不是会员卡号
    private String cardId;
    private OperationMenuListAdapter menuListAdapter;

    private ViewfinderView viewfinderView;// 绘制扫描区域


    // 账户余额
    private float accoutBalance;
    // 当前小票编号，在生成订单后被赋值
    public static String curOrderNo = "";
    private String card_ID;

    private CaptureActivityHandler captureActivityHandler;// 消息中心
    // 人数区
    private int mTotalPersonCount;
    private int mCurMealPersonCount;
    private String mCurMealType = "";

    // 支付宝还是微信
    private String payName;
    MainActivity mainActivity2;

    private ImageView img_upload;
    private static final int PAY_CASH = 0;
    private static final int PAY_MEMBER_CARD = 1;
    private static final int PAY_WECHAT = 2;
    private static final int PAY_ALIPAY = 3;
    private static final int PAY_BANK = 4;

    private LinearLayout ll_upload;

    public static final int UPDATE_PERSON_NUMBER = 888;
    public static final int UPDATE_PERSON_NUMBER_WEIXIN = 1314;
    public static final int UPDATE_PERSON_NUMBER_ZERO = 123;
    public static final int COUNT_PERSON_NUMBER = 999;

    public static final int REFRESH = 666;
    public static final int SETTLEMENT = 777;

    //清除扫描的支付宝/微信帐号
    public static final int CLEAR_SCANNER_NUM = 0x111;

    // 支付方式
    private int payWay;
    public String barcode = "";
    // 是否已经打印
    private boolean isPrint = false;
    // 数量
    private TextView txPayCount;

    private ConcurrentLinkedQueue<JSONObject> listPrint = new ConcurrentLinkedQueue<>();

    public boolean isduringPayment = false;

    private boolean requestCard = false;
    private boolean requestCardTime = false;
    private boolean playCardError = false;
    // 是否已支付
    private boolean isPayment = false;
    // 在线更新是否启动
    private boolean onlineSync = false;

    private int pay_result_select = 1000;

    public static String cardMemberId = "0";
    // 扫描是否开启
    private boolean isOpenScan = false;
    public long scan_start_time = 0;

    private ProgressBar main_progress;

    private boolean fixed_price_status;

    private Boolean first = true;
    private boolean isPaying = false;

    public static final int JUDGE_SETUP_SCAN = 222;
    public static final int VISIBILITY_PROGRESS = 520;
    public static final int GONE_PROGRESS = 521;
    public static final int CARD_STATUS_CANCELEDS = 369;
    public static final int CARD_STATUS_LOSTS = 258;
    public static final int CARD_ILLEGALITY = 147;
    public static final int REFRESH_CODE = 419;
    public static final int WEIXIN_CODE = 659;


    private WeiCaiOperationWaterAdapter weiCaiOperationWaterAdapter;

    // 讯飞
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_LOCAL;
    // 默认本地发音人
    public static String voicerLocal = "xiaoyan";

    @Override
    protected int setContentView() {
        return R.layout.operation_activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gOperationHandler = new OperationHandler();

        mainActivity2 = (MainActivity) getActivity();
        fixed_price_status = MyConstant.gSharedPre.getBoolean(MyConstant.FIXED_PRICE_STATUS, false);
        initView();
//        openScan();
        handler.sendEmptyMessage(COUNT_PERSON_NUMBER);
        checkPermission();
//        CameraManager.init(getContext());
        initTimer();
        curOrderNo = MyUtil.getPrimaryValue();

        try {
            // 初始化合成对象
            mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
        } catch (Exception e) {
            Log.e("frost", "mTs为空:" + e.getMessage());
        }
        if (null != mTts) {
            setXunFeiData();
        } else {
            Log.e("frost", "mTs为空");
        }
    }

    private void setXunFeiData() {
//        // 清空参数
//        mTts.setParameter(SpeechConstant.PARAMS, null);
        //设置使用本地引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //设置发音人资源路径
        mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicerLocal);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //	mTts.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC+"");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

//        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/sound/tts.wav");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("frost", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e("frost", "初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                isPaying = false;
                if (fixed_price_status) {
                    settlement();
                }
            } else if (speechError != null) {
                Log.e("frost_onCompleted_error", "错误码" + speechError.getErrorCode());
            }

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    //获取发音人资源路径
    private String getResourcePath() {
        StringBuffer tempBuffer = new StringBuffer();
        String type = "tts";
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, type + "/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, type + "/" + voicerLocal + ".jet"));
        return tempBuffer.toString();
    }

    //更新人数handle
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WEIXIN_CODE:
                    business_state.setText("错误信息，服务异常");
                    break;
                case REFRESH_CODE:
                    business_put_price.setText("");
                    business_state.setText("请刷新二维码");
                    break;
                case CARD_STATUS_LOSTS:
                    business_state.setText("挂失卡");
                    break;
                case CARD_STATUS_CANCELEDS:
                    business_state.setText("注销卡");
                    break;
                case VISIBILITY_PROGRESS:
                    img_upload.setImageResource(R.drawable.upload_end);
                    break;
                case GONE_PROGRESS:
                    img_upload.setImageResource(R.drawable.upload_start);
                    break;
                case CARD_ILLEGALITY:
                    business_state.setText("非法卡");
                    break;
                case JUDGE_SETUP_SCAN:
                    if (payMsg != null || !"".equals(payMsg)) {
                        if (!isPaying) {
                            setupScan();
                        } else {
                            stopScan();
                        }
                    }
                    break;
                case UPDATE_PERSON_NUMBER:
//                    pDaoSession.clear();
//                    List<TfConsumeCardRecord> list = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
//                            .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
//                                    TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"),
//                                    TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"))
//                            .build()
//                            .list();
//                    if (!list.isEmpty() && list != null) {
//                        mTotalPersonCount = list.size();
//                    }
//                    if (MyUtil.obtainNetworkStatus(mContext)) {
//                        business_num_people.setText(mTotalPersonCount + 1 + "");
//                    } else {
                    business_num_people.setText(mTotalPersonCount + "");
//                    }
                    break;
//                case UPDATE_PERSON_NUMBER_WEIXIN:
//                    List<TfConsumeCardRecord> list1 = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
//                            .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
//                                    TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"),
//                                    TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"))
//                            .build()
//                            .list();
//                    if (!list1.isEmpty() && list1 != null) {
//                        mTotalPersonCount = list1.size();
//                    }
//                    business_num_people.setText(mTotalPersonCount + 1 + "");
//
//                    break;

                case UPDATE_PERSON_NUMBER_ZERO:
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    String format1 = simpleDateFormat.format(date);
                    format1 = format1.substring(0, 5);
                    if ("00:00".equals(format1)) {
                        business_num_people.setText("0");
                        weiCaiOperationWaterAdapter.clearData();
                        weiCaiOperationWaterAdapter.notifyDataSetChanged();
                    }

                    break;
                case COUNT_PERSON_NUMBER:

                    List<TfConsumeCardRecord> list12 = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
                            .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
                                    TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"),
                                    TfConsumeCardRecordDao.Properties.CCR_STATUS.eq("1"))
                            .build()
                            .list();
                    if (!list12.isEmpty() && list12 != null) {
                        mTotalPersonCount = list12.size();
                    }
                    business_num_people.setText(mTotalPersonCount + "");
                    break;

                case REFRESH:
                    Log.e("frost", "REFRESH");
                    business_put_price.setText("");
                    isPaying = false;
                    playCardError = false;
                    playWxAliError = false;
                    isPayMsg = false;
                    requestCardTime = false;
                    business_price.setText("");
                    curOrderNo = "";
                    payMsg = "";
                    business_state.setText("准备下单");
                    break;
                case SETTLEMENT:
                    double maxSum_double;
                    String maxSum = MyConstant.gSharedPre.getString(MyConstant.SP_MAX_SUM, "");
                    if (TextUtils.isEmpty(maxSum)) {
                        maxSum_double = 99999999999999999.99;
                    } else {
                        maxSum_double = Double.parseDouble(maxSum);
                    }
                    if ("".equals(business_put_price.getText().toString()) && "".equals(business_price.getText().toString())) {
                        return;
                    }
                    if (isPaying) {
//                        showMessage("警告", "请等待支付结果完成");
                    } else {
                        try {
                            curOrderNo = MyUtil.getPrimaryValue();
                            if (!fixed_price_status || first) {
                                payMsg = business_put_price.getText().toString().trim();
                                if (TextUtils.isEmpty(payMsg)) {
                                    return;
                                }
                                if ("0.00".equals(payMsg) || "0.0".equals(payMsg)) {
                                    refresh();
                                    return;
                                }
                                if (maxSum_double < Double.parseDouble(payMsg)) {
                                    showMessage("提示", "当前消费金额超过最大限制金额");
                                    payMsg = "";
                                    business_state.setText("准备下单");
                                    business_price.setText("");
                                    business_put_price.setText("");
                                    return;
                                }
                                if (!isNumber(payMsg)) {
                                    refresh();
                                    return;
                                }
                                if (!"".equals(payMsg) && "请刷卡".equals(business_state.getText().toString())) {
                                    if (maxSum_double < Double.parseDouble(payMsg)) {
                                        showMessage("提示", "当前消费金额超过最大限制金额");
                                        payMsg = "";
                                        business_state.setText("准备下单");
                                        business_price.setText("");
                                        business_put_price.setText("");
                                        return;
                                    }
                                    Log.d("frost", "payMsg" + payMsg);
                                    if (!"".equals(business_price.getText().toString())) {
                                        String price = business_price.getText().toString().trim();
                                        float v = Float.parseFloat(price);
                                        float v1 = Float.parseFloat(payMsg);
                                        payMsg = MyConstant.gFormat.format(v + v1);
                                        if (maxSum_double < Double.parseDouble(payMsg)) {
                                            showMessage("提示", "当前消费金额超过最大限制金额");
                                            payMsg = "";
                                            business_state.setText("准备下单");
                                            business_price.setText("");
                                            business_put_price.setText("");
                                            return;
                                        }
                                    }
                                    String format = MyConstant.gFormat.format(Float.parseFloat(payMsg));
                                    business_price.setText(format);
                                    if (!isNumber(payMsg)) {
                                        refresh();
                                        return;
                                    } else {
                                        if (maxSum_double < Double.parseDouble(payMsg)) {
                                            showMessage("提示", "当前消费金额超过最大限制金额");
                                            payMsg = "";
                                            business_state.setText("准备下单");
                                            business_price.setText("");
                                            business_put_price.setText("");
                                            return;
                                        }
                                        first = false;
                                        business_state.setText("请刷卡");
                                        business_put_price.setText("");
                                        business_vip_no.setText("");
                                        business_vip_name.setText("");
                                        business_balance.setText("");
                                        Log.d("frost", "payMsg = " + payMsg);
                                        mTts.stopSpeaking();
                                        playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
                                        mainActivity.showPay(mainActivity, "card", "待支付 " + format, "", "", "");
                                    }
                                } else {
                                    String format = MyConstant.gFormat.format(Float.parseFloat(payMsg));
                                    if (maxSum_double < Double.parseDouble(format)) {
                                        showMessage("提示", "当前消费金额超过最大限制金额");
                                        payMsg = "";
                                        business_state.setText("准备下单");
                                        business_price.setText("");
                                        business_put_price.setText("");
                                        return;
                                    }
                                    business_price.setText(format);
                                    first = false;
                                    business_state.setText("请刷卡");
                                    business_put_price.setText("");
                                    business_vip_no.setText("");
                                    business_vip_name.setText("");
                                    business_balance.setText("");
                                    Log.d("frost", "payMsg = " + payMsg);
                                    mTts.stopSpeaking();
                                    playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
                                    mainActivity.showPay(mainActivity, "card", "待支付 " + format, "", "", "");
                                }
                            } else if (fixed_price_status && !first) {
                                if ("".equals(business_put_price.getText().toString())) {
                                    business_state.setText("请刷卡");
                                    payMsg = business_price.getText().toString();
                                    if ("0.00".equals(payMsg) || "0.0".equals(payMsg)) {
                                        refresh();
                                        return;
                                    }
                                    if (!isNumber(payMsg)) {
                                        refresh();
                                        return;
                                    }
                                    if (maxSum_double < Double.parseDouble(payMsg)) {
                                        showMessage("提示", "当前消费金额超过最大限制金额");
                                        payMsg = "";
                                        business_state.setText("准备下单");
                                        business_price.setText("");
                                        business_put_price.setText("");
                                        return;
                                    }
                                    business_vip_name.setText("");
                                    business_vip_no.setText("");
                                    business_balance.setText("");
                                    playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
                                    mainActivity.showPay(mainActivity, "card", "待支付 " + MyConstant.gFormat.format(Float.parseFloat(payMsg)), "", "", "");
//                                    getMemberCardBalance(cardId);
                                } else {
                                    business_state.setText("请刷卡");
                                    payMsg = business_put_price.getText().toString();
                                    if (!isNumber(payMsg)) {
                                        refresh();
                                        return;
                                    }
                                    if ("0.00".equals(payMsg) || "0.0".equals(payMsg)) {
                                        refresh();
                                        return;
                                    }
                                    if (maxSum_double < Double.parseDouble(payMsg)) {
                                        showMessage("提示", "当前消费金额超过最大限制金额");
                                        payMsg = "";
                                        business_state.setText("准备下单");
                                        business_price.setText("");
                                        business_put_price.setText("");
                                        return;
                                    }
                                    if (payMsg.length() > 10) {
                                        showMessage("提示", "请刷新后再支付");
                                        return;
                                    }
                                    String format = MyConstant.gFormat.format(Float.parseFloat(payMsg));
                                    business_price.setText(format);
                                    business_put_price.setText("");
                                    business_vip_name.setText("");
                                    business_vip_no.setText("");
                                    business_balance.setText("");
                                    playVoice(MyConstant.SP_MEDIA_PLEASE_SET_CARD);
                                    mainActivity.showPay(mainActivity, "card", "待支付 " + format, "", "", "");
//                                    getMemberCardBalance(cardId);
                                }
                            }
                        } catch (Exception e) {
//                            showMessage("", e.getMessage());
                            Log.d("frost", "try -catch -----" + e.getMessage());
                        }
                    }
                    break;
                case 1:
                    business_state.setText("员工卡支付中");
                    break;
                case 2:
                    business_state.setText("支付失败");
                    break;
                case 3:
                    business_state.setText("微信支付中");
                    break;
                case 6:
                    business_state.setText("支付宝支付中");
                    break;
                case 4:
                    business_state.setText("准备下单");
                    break;
                case 5:
                    business_state.setText("微信支付超时");
                    break;
                case 7:
                    business_state.setText("网络异常");
                    break;
                case 8:
                    business_state.setText("请求错误");
                    break;
            }
        }
    };
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    private static final long VIBRATE_DURATION = 200L;
    public final static int REQUEST_CAMERA = 1;
    public final static int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    //    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔
    private String oldCode = "";
    private boolean isFirst = true;
    private String msg = "";

    private void initView() {
        img_upload = mainActivity.findViewById(R.id.img_upload);
        business_list_water = (ListView) findViewById(R.id.business_list_water);
        weiCaiOperationWaterAdapter = new WeiCaiOperationWaterAdapter(mContext, new ArrayList<WeiCaiWaterInfo>());
        business_list_water.setAdapter(weiCaiOperationWaterAdapter);

        txAppTitle = mainActivity.findViewById(R.id.app_title);
        iv_power = mainActivity.findViewById(R.id.iv_power);

        curOrderNo = MyUtil.getPrimaryValue();

        business_state = (TextView) findViewById(R.id.business_state);
        business_num_people = (TextView) findViewById(R.id.business_num_people);
        business_price = (TextView) findViewById(R.id.business_price);
        business_vip_name = (TextView) findViewById(R.id.business_vip_name);
        business_vip_no = (TextView) findViewById(R.id.business_vip_no);
        business_balance = (TextView) findViewById(R.id.business_balance);
        business_put_price = (EditText) findViewById(R.id.business_put_price);

        surfaceView = (ViewfinderView) findViewById(R.id.viewfinder_view1);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view1);
        surfaceHolder = surfaceView.getHolder();
        if (TextUtils.isEmpty(MyConstant.gSharedPre.getString(MyConstant.SP_MAX_SUM, ""))) {
            MyConstant.gEditor.putString(MyConstant.SP_MAX_SUM, "50");
            MyConstant.gEditor.commit();
        }
        //点击不弹出系统输入法
        business_put_price.setInputType(InputType.TYPE_NULL);
        business_put_price.requestFocus();

        business_put_price.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //160    0-9 = 7-16  0-9 = 144-153

                if (keyEvent.getKeyCode() == 7 || keyEvent.getKeyCode() == 144 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "0";
                } else if (keyEvent.getKeyCode() == 8 || keyEvent.getKeyCode() == 145 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "1";
                } else if (keyEvent.getKeyCode() == 9 || keyEvent.getKeyCode() == 146 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "2";
                } else if (keyEvent.getKeyCode() == 10 || keyEvent.getKeyCode() == 147 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "3";
                } else if (keyEvent.getKeyCode() == 11 || keyEvent.getKeyCode() == 148 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "4";
                } else if (keyEvent.getKeyCode() == 12 || keyEvent.getKeyCode() == 149 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "5";
                } else if (keyEvent.getKeyCode() == 13 || keyEvent.getKeyCode() == 150 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "6";
                } else if (keyEvent.getKeyCode() == 14 || keyEvent.getKeyCode() == 151 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "7";
                } else if (keyEvent.getKeyCode() == 15 || keyEvent.getKeyCode() == 152 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "8";
                } else if (keyEvent.getKeyCode() == 16 || keyEvent.getKeyCode() == 153 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + "9";
                } else if (keyEvent.getKeyCode() == 158 || keyEvent.getKeyCode() == 56 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    msg = msg + ".";
                }
                if (keyEvent.getKeyCode() == 160 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                  payMsg = business_put_price.getText().toString().trim();
                    settlement();
                }
                return false;
            }
        });
        business_num0 = (Button) findViewById(R.id.business_num0);
        business_num1 = (Button) findViewById(R.id.business_num1);
        business_num2 = (Button) findViewById(R.id.business_num2);
        business_num3 = (Button) findViewById(R.id.business_num3);
        business_num4 = (Button) findViewById(R.id.business_num4);
        business_num5 = (Button) findViewById(R.id.business_num5);
        business_num6 = (Button) findViewById(R.id.business_num6);
        business_num7 = (Button) findViewById(R.id.business_num7);
        business_num8 = (Button) findViewById(R.id.business_num8);
        business_num9 = (Button) findViewById(R.id.business_num9);
        business_num_dot = (Button) findViewById(R.id.business_num_dot);
        business_refresh = (ImageButton) findViewById(R.id.business_refresh);
        business_ok = (ImageButton) findViewById(R.id.business_ok);

        business_num0.setOnClickListener(this);
        business_num1.setOnClickListener(this);
        business_num2.setOnClickListener(this);
        business_num3.setOnClickListener(this);
        business_num4.setOnClickListener(this);
        business_num5.setOnClickListener(this);
        business_num6.setOnClickListener(this);
        business_num7.setOnClickListener(this);
        business_num8.setOnClickListener(this);
        business_num9.setOnClickListener(this);
        business_num_dot.setOnClickListener(this);
        business_refresh.setOnClickListener(this);
        business_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_num0:
                inputEdit("0");
                break;
            case R.id.business_num1:
                inputEdit("1");
                break;
            case R.id.business_num2:
                inputEdit("2");
                break;
            case R.id.business_num3:
                inputEdit("3");
                break;
            case R.id.business_num4:
                inputEdit("4");
                break;
            case R.id.business_num5:
                inputEdit("5");
                break;
            case R.id.business_num6:
                inputEdit("6");
                break;
            case R.id.business_num7:
                inputEdit("7");
                break;
            case R.id.business_num8:
                inputEdit("8");
                break;
            case R.id.business_num9:
                inputEdit("9");
                break;
            case R.id.business_num_dot:
                inputEdit(".");
                break;
            case R.id.business_refresh:
                refresh();
                break;
            case R.id.business_ok:
                settlement();
                break;
        }

    }

    @Override
    public void onResume() {
//        getFocus();
        super.onResume();
        Log.e("frost_test", "onResume");
    }

    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mainActivity.repleaseFragment(new HomeFragment());
//                    ll_down_bar.setVisibility(View.VISIBLE);
//                    ll_top_bar.setVisibility(View.VISIBLE);
//                    txAppTitle.setText("主界面");
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 展示IC卡信息
     *
     * @param memberInfo
     * @param balance
     */
    public void showMemberInfo(final TfMemberInfo memberInfo, final String balance, final String status) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (memberInfo != null) {
                    Log.d("frost", "memberInfo" + memberInfo.getMI_NAME());
                    business_vip_name.setText(memberInfo.getMI_NAME());
                    business_vip_no.setText(memberInfo.getMI_PHONE());
                    business_balance.setText(balance);
                    business_state.setText(status);
                    mainActivity.showPay(mainActivity, "card", status, memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance);
                }
            }
        });

    }


    @Override
    public void onDestroy() {
        mExecutor.shutdown();
//        getFocus();
        if (this instanceof WeichaiOperationFragment) {
            txAppTitle.setText("主界面");
        }
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
//        mExecutor.shutdown();
//        getFocus();
        Log.e("frost_test", "onPause");
//        if (null != mTts) {
//            mTts.stopSpeaking();
//            // 退出时释放连接
//            mTts.destroy();
//        }
        super.onPause();
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
                    byte[] key = {(byte) 0xA0, (byte) 0xB7, (byte) 0xA5, (byte) 0xC5, (byte) 0x80, (byte) 0x88};
                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    //加密IC卡
                    MifareClassic mifareClassic = MifareClassic.get(tag);
                    mifareClassic.connect();
                    //获取扇区数量
                    int count = mifareClassic.getSectorCount();
                    Log.e("onNewIntent:", "扇区数量 ===" + count);
                    boolean isOpen = mifareClassic.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT);
                    if (isOpen) {
                        int bCount = mifareClassic.getBlockCountInSector(1);
                        int bIndex = mifareClassic.sectorToBlock(1);
                        for (int j = 0; j < bCount; j++) {
                            Log.e("onNewIntent:", "存储器的位置 ===" + bIndex + "当前块 === " + (bIndex + j));
                            //修改KeyA和KeyB
                            if ((bIndex + j) == (4 + 3)) {
                                mifareClassic.writeBlock(bIndex + j, new byte[]{(byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88, (byte) 0xff, 0x07, (byte) 0x80, (byte) 0x69, (byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88});
                                Log.e("onNewIntent:", (bIndex + j) + "块加密成功");

                            }
                        }
                    } else {
//                        boolean isPassword = mifareClassic.authenticateSectorWithKeyA(1, "a0b7a5c58088".getBytes());
                        boolean isPassword = mifareClassic.authenticateSectorWithKeyA(1, key);
                        if (isPassword) {
                            Log.e("onNewIntent:", "密码正确");
                            if (tag != null && tag.getId() != null) {
                                String smartCardId = MyUtil.ByteArrayToHexString(tag.getId());
                                if (TextUtils.isEmpty(smartCardId)) {
                                    business_state.setText("非法卡");
                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                } else {
                                    long convertId = MyUtil.computeMemberId(smartCardId);
                                    if (convertId == 0) {
                                        business_state.setText("非法卡");
                                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                        return;
                                    }
                                    int diff = 10 - String.valueOf(convertId).length();
                                    cardId = String.valueOf(convertId);
                                    // 不足10位补0
                                    if (diff >= 0) {
                                        for (int k = 0; k < diff; k++) {
                                            cardId = ("0" + cardId);
                                        }
                                        // 最终得到去数据库查询的IC_ID
                                        if (!("").equals(business_vip_name.getText().toString())) {
                                        } else {
                                            getMemberCardBalance(String.valueOf(cardId));
                                            return;
                                        }
                                    } else if (diff < 0) {
                                        business_state.setText("非法卡");
                                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                        return;
                                    }
                                }
                            }
                        } else {
                            Log.e("onNewIntent:", "密码错误");
                        }
//                                byte[] bytes = mifareClassic.readBlock(bIndex + j);
//                                mifareClassic.writeBlock(bIndex + j, new byte[]{(byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88, (byte) 0xff, 0x07, (byte) 0x80, (byte) 0x69, (byte) 0xa0, (byte) 0xb7, (byte) 0xa5, (byte) 0xc5, (byte) 0x80, (byte) 0x88});
//                                if (isPassword) {
//                                    // 能获取到卡号，且未支付
//                                    Log.e("onNewIntent:","密码正确");
//
//                                }else {
//                                    Log.e("onNewIntent:","密码错误");
//                                }
                    }


                } catch (Exception ex) {
                    Log.e("CrashHandler", "onNewIntent..." + ex.getMessage());
                }

            }
        });

        ((MainActivity) context).setBarcodeScannerCallback(new MainActivity.OnBarcodeScannerCallback() {
            public void onKey(KeyEvent event) {
                barcode += (char) event.getUnicodeChar();
                business_put_price.requestFocus();
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
                    pay_way_name = "员工卡";
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
//            objPrint.put("uname", mainActivity.pUserInfo.getUNAME());
            objPrint.put("uname", MyConstant.gSharedPre.getString(MyConstant.SP_STORE_PRESON, ""));
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

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return captureActivityHandler;
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

    public void calcurlateMemberBalance(String icId, TfCardInfo info, TfMemberInfo memberInfo, TfMemberAccountRecord cashRecord, TfMemberAccountRecord subsidyRecord) {
        try {
            float cashBalance = 0, subsidyBalance = 0;
            if (cashRecord != null) {
                if (subsidyRecord == null) {
                    showMessage("", "该卡无补贴账户");
                    isduringOrderNo = "";
                    playCardError = true;
                    mainActivity.showPay(mainActivity, "card", "该卡无补贴账户", "", "", "");
                    //   isduringPayment = false;
                    return;
                }
                if (cashRecord.getACCOUNT_STATUS().equals("1")
                        || subsidyRecord != null
                        && subsidyRecord.getACCOUNT_STATUS().equals("1")) {
                    showMessage("", "当前账户已冻结");
                    isduringOrderNo = "";
                    playCardError = true;
//                    business_state.setText("支付失败");
                    handler.sendEmptyMessage(2);
                    mainActivity.showPay(mainActivity, "card", "当前账户已冻结", "", "", "");
                    //   isduringPayment = false;
                    return;
                }
                cashBalance = Float.parseFloat(cashRecord.getBALANCE());
                subsidyBalance = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                float balance = cashBalance + subsidyBalance;
                start_time = System.currentTimeMillis();
                // 全民以及会员折扣
                String mi_type = memberInfo.getMI_TYPE();//会员类型
                int dis_count = getDisCountForMember(mi_type);//会员折扣
                Log.e("frost", "mi_type:" + mi_type + "  会员折扣 dis_count:" + dis_count);
                if (dis_count == 100) {
                    dis_count = getDisCountForAll();//全员折扣
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
                        isduringOrderNo = "";
                        // isduringPayment = false;
                        playCardError = true;
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
                Log.e("url", "isduringPayment8:" + isduringPayment);
                if (accoutBalance < 0) {
                    if (!MyUtil.obtainNetworkStatus(mContext)) {
                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                business_balance.setText(balance + "");
                                business_vip_name.setText(memberInfo.getMI_NAME());
                                business_vip_no.setText(memberInfo.getMI_NO());
                                mainActivity.showPay(mainActivity, "card", "余额不足", memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance + "");
                                payMsg = "";
                                curOrderNo = "";
                            }
                        });
                    }
                    // 余额不足
                    isduringOrderNo = "";
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            business_state.setText("余额不足");
                        }
                    });
                    playVoice(MyConstant.SP_MEDIA_MONEY_NOT_ENOUGH);
                    mainActivity.showPay(mainActivity, "card", "余额不足", "", "", "");
                    return;
                } else {
                    // 支付成功
//                        isPayment = true;
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
                    createConsumeInfo(temp_f, temp_balance, info.getMI_ID(), cashRecord.getACCOUNT_ID(), icId, 1 + "", 1);
                    // 更新余额
//                    if (memberInfo.getMI_NAME() != null) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            business_vip_name.setText(memberInfo.getMI_NAME());
                            business_vip_no.setText(memberInfo.getMI_NO());
                            String balance1 = MyConstant.gFormat.format(accoutBalance);
                            business_balance.setText(balance1);
                            business_state.setText("支付成功");
                            payMsg = "";
                            mainActivity.showPay(mainActivity, "card", "支付成功", memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance1);
                        }
                    });
//                    }
                    isduringOrderNo = "";

                    // 修改数据库中的会员余额
                    pDaoSession.update(cashRecord);
                    pDaoSession.update(subsidyRecord);

                    try {
                        int code = mTts.startSpeaking("支付成功" + temp_f + "元", mTtsListener);
                        if (code != ErrorCode.SUCCESS) {
                            Log.e("frost_xunfei", "语音合成失败,错误码: " + code);
                        }
                        mTotalPersonCount += 1;
                        handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);
                    } catch (Exception e) {
                        Log.d("frost", e.getMessage());
                    }
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

    public ThreadPoolExecutor mTHREAD_POOL = new ThreadPoolExecutor(6,
            14,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    /**
     * 创建一条消费记录
     */
    public void createConsumeInfo(final float totalMoney, final float balance, final String memberId,
                                  final String accountId, final String icid, final String personCount, final int CCR_STATUS) {

        Log.d("frost", "createConsumeInfo:" + balance);
        onlineSync = true;
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
                            ddf1.setMaximumFractionDigits(2);

                            String s = ddf1.format(totalMoney);
                            if (s.indexOf(".") == -1) {
                                s += ".00";
                            }
                            String payWay = null;
                            if (cardRecord.getCCR_PAY_TYPE().equals("1")) {
                                payWay = "员工卡";
                            } else if (cardRecord.getCCR_PAY_TYPE().equals("2")) {
                                payWay = "微信";
                            } else if (cardRecord.getCCR_PAY_TYPE().equals("3")) {
                                payWay = "支付宝";
                            }
                            weiCaiOperationWaterAdapter.insertDataToEnd(new WeiCaiWaterInfo(payWay, s));
                        }
                    });

                    // 消费详情
                    List<TfConsumeDetailsRecord> detailsRecordList = new ArrayList<>();

                    List<StoreConfig> list = pDaoSession.queryBuilder(StoreConfig.class).build().list();
                    String pricing = list.get(0).getPRICING();

                    TfConsumeDetailsRecord detailsRecord;
//                    OperationMenu menu = new OperationMenu();
                    detailsRecord = new TfConsumeDetailsRecord();
                    detailsRecord.setCDR_ID(getPrimaryValue());
//                    float totalPrice = menu.getTotalPrice();
//                    Log.e("frost_test","pricing:"+pricing);
//                    Log.e("frost_test","totalPrice:"+totalPrice);
//                    detailsRecord.setCDR_MONEY(MyConstant.gFormat.format(menu.getTotalPrice()));totalMoney
                    detailsRecord.setCDR_MONEY(totalMoney + "");
//                    if (menu.getId() == MyConstant.CUSTOM_FIXED_PRICE_GOODS || menu.getId() == MyConstant.CUSTOM_PRICING_GOODS) {
                    detailsRecord.setCDR_NO(pricing);
//                        Log.e("frost_test", "---1--detailsRecord.getCDR_ID():" + detailsRecord.getCDR_NO());
//                    } else {
//                        detailsRecord.setCDR_NO(String.valueOf(menu.getId()));
//                        Log.e("frost", "---2--detailsRecord.getCDR_ID():" + detailsRecord.getCDR_NO());
//                    }

                    detailsRecord.setCDR_NUMBER("1");
                    detailsRecord.setCDR_TYPE("1");
                    if ("1".equals(cardRecord.getCCR_PAY_TYPE())) {
                        detailsRecord.setCDR_UNIT_PRICE(totalMoney + "");
                    } else {
                        detailsRecord.setCDR_UNIT_PRICE(totalMoney + "");
                    }

                    detailsRecord.setCLIENT_CODE(clientCode);
                    detailsRecord.setCOR_ID(orderNo);
                    detailsRecord.setCREATETIME(createTime);
                    detailsRecord.setISM_STATUS("0");
                    detailsRecord.setSTORE_CODE(storeCode);
                    detailsRecordList.add(detailsRecord);

                    // 消费订单
                    TfConsumeOrderRecord orderRecord = new TfConsumeOrderRecord();
                    orderRecord.setCLIENT_CODE(clientCode);
                    orderRecord.setCOR_AMOUNT(totalMoney + "");
                    orderRecord.setCOR_ID(orderNo);
                    orderRecord.setCOR_TYPE("0");
                    orderRecord.setCREATETIME(createTime);
                    orderRecord.setISM_STATUS("0");
                    orderRecord.setSTORE_CODE(storeCode);
                    orderRecord.setMACHINE_NO(machineNo);
                    orderRecord.setUPDATETIME(MyUtil.dateConversion(System.currentTimeMillis()));

                    if (!MyUtil.networkState || payWay != PAY_MEMBER_CARD) {
                        onlineSync = false;
                        Log.d("frost", "saveOrder: saveOrder");
                        saveOrder(cardRecord, detailsRecordList, orderRecord);
                    } else {
                        // 更新
                        try {
                            Log.d("frost", "updateToServerOnline:  ");
                            Log.d("frost", "updateToServerOnline: updateToServerOnline");
                            updateToServerOnline(cardRecord, detailsRecordList, orderRecord);
                        } catch (Exception e) {
                            Log.d("frost", "updateToServerOnline: Exception");
                            Log.d("frost", "updateToServerOnline: e:" + e.getMessage());
                            onlineSync = false;
                            saveOrder(cardRecord, detailsRecordList, orderRecord);
                        }
                    }
                } catch (Exception e) {
                    onlineSync = false;
                    e.printStackTrace();
                    Log.e("frost", e.toString());
                }
            }
        });
    }

    /**
     * 将本地记录的消费信息发送到服务器（更新）
     * 离线模式
     *
     * @throws
     */
    public synchronized void updateToServerOnline(final TfConsumeCardRecord cardRecord,
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
                        onlineSync = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "updateToServerOnline:777 onError");
                        onlineSync = false;
//                        saveOrder(cardRecord, detailsRecordList, orderRecord);
                        saveOrder(cardRecord, detailsRecordList, orderRecord);
                        Log.d(TAG, "在线状态更新 onError: " + e.getMessage());
                        //cardRecord.setCCR_UPLOAD_STATUS("2");
                        //pDaoSession.update(cardRecord);
                    }

                    @Override
                    public void onNext(UpdateResultInfo syncResultInfo) {
                        Log.e(TAG, "updateToServerOnline:777 onNext");
                        onlineSync = false;

                        if (syncResultInfo != null && syncResultInfo.isResult()) {
                            cardRecord.setISM_STATUS("1");
                            cardRecord.setCCR_UPLOAD_STATUS("1");
                            cardRecord.setCCR_UPLOAD_TIME(MyUtil.dateConversion(System.currentTimeMillis()));
                        }
                        saveOrder(cardRecord, detailsRecordList, orderRecord);
//                        pDaoSession.update(cardRecord);
//                        pDaoSession.update(detailsRecordList);
//                        pDaoSession.update(orderRecord);
//                        pDaoSession.clear();

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

    boolean playWxAliError = false;
    public static String isduringOrderNo = "";

    /**
     * 根据给定的卡号，去查找对应的U_ID，再通过他去查找对应账户余额
     *
     * @param icId
     */
    // TODO: 2019/12/3 线上支付
    private void getMemberCardBalance(final String icId) {
        Log.d("frost", "getMemberCardBalance");
        isPaying = true;
        if ("".equals(business_price.getText().toString())) {
            business_state.setText("员工卡查询中");
        }
        try {
            isduringOrderNo = "" + curOrderNo;
            Log.e("frost", "会员卡支付icId:" + icId);
            if (MyUtil.obtainNetworkStatus(mContext)) {
//                if (requestCard) {
//                    return;
//                }
                Log.e("pay", "getMemberCardBalance....2");
                //   Log.e("url","isduringPayment2:" + isduringPayment);
                long star = System.currentTimeMillis();
                String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                String clienCode = "", storeCode = "";
                try {
                    clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                    storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
                } catch (Exception e) {
                    clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                    storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
                }
                String url = ip + ":" + port + "/k-occ/member/info/" + clienCode + "/" + storeCode + "/icId/" + icId;
                Log.e("frost", url);
                requestCard = true;
                requestCardTime = false;
                if (!"".equals(business_price.getText().toString()) && !payMsg.equals("")) {
                    handler.sendEmptyMessage(1);
                }
//                OkhttpUtil.get(url, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("frost_onFailure",e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String string = response.body().string();
//                        Log.e("frost_onResponse",string);
//                    }
//                });
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                        .readTimeout(5, TimeUnit.SECONDS)//设置读取超时时间
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
                final Request request = new Request.Builder()
                        .url(url)
                        .build();
                Log.e("pay", "getMemberCardBalance....2.1");
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        try {
                            long star = System.currentTimeMillis();
                            isPaying = false;
                            Log.e("url", "线下支付");
                            TfCardInfo info = null;
                            TfMemberAccountRecord cashRecord = null;
                            TfMemberAccountRecord subsidyRecord = null;
                            TfMemberInfo memberInfo = null;
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
                                Log.e("android", "getMemberCardBalance info.getMI_ID():" + memberInfoList.toString());
                                if (memberInfoList == null || memberInfoList.isEmpty()) {
                                    isduringOrderNo = "";
                                    playCardError = true;
                                    return;
                                }
                                memberInfo = memberInfoList.get(0);
                                List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
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
                                    Log.e("frost_test", "balance" + cashRecord.getBALANCE() + "\n" + subsidyRecord.getBALANCE());
                                    Log.e("frost", "--------" + payMsg);
                                    if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                                        try {
                                            long end = System.currentTimeMillis();
                                            Float cashBalance1 = Float.parseFloat(cashRecord.getBALANCE());
                                            Float subsidyBalance1 = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                                            String balance = MyConstant.gFormat.format(subsidyBalance1 + cashBalance1);
                                            Log.d("frost", "calcurlateMemberBalance----3");
                                            MyUtil.appendFile("#####################会员卡OnFailure start 订单号" + curOrderNo + "#####################" + "\n");
                                            MyUtil.appendFile("请求时间：" + star);
                                            MyUtil.appendFile("请求地址：" + url);
                                            MyUtil.appendFile("请求参数：" + url);
                                            MyUtil.appendFile("响应参数：" + e.getMessage());
                                            MyUtil.appendFile("耗时：" + (end - star) + "");
                                            MyUtil.appendFile("结束时间：" + end);
                                            MyUtil.appendFile("##################### end #####################" + "\n");
                                            calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                                            if ("".equals(business_price.getText().toString()) || business_price.getText().toString() == null) {
                                                showMemberInfo(memberInfo, balance, "准备下单");
                                            }
                                            pDaoSession.clear();
                                        } catch (Exception e1) {
                                            isPaying = false;
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
                            business_state.setText("非法卡");
                            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
//                            Log.e("frost_onFailure1", e.getMessage());
//                            requestCardTime = false;
//                            isPaying = false;
//                            requestCard = false;
//                            TfCardInfo info = null;
//                            TfMemberAccountRecord cashRecord = null;
//                            TfMemberAccountRecord subsidyRecord = null;
//                            TfMemberInfo memberInfo = null;
//                            List<TfCardInfo> cardInfoList = pDaoSession.queryBuilder(TfCardInfo.class)
//                                    .where(TfCardInfoDao.Properties.IC_ID.eq(icId))
//                                    .build()
//                                    .list();
//
//                            if (cardInfoList.isEmpty() || cardInfoList == null) {
//                                if (icId.substring(0, 2).equals("56") && icId.length() == 20) {
//                                    info = null;
//                                    handler.sendEmptyMessage(8);
//                                    isPaying = false;
//                                    return;
//                                } else {
//                                    handler.sendEmptyMessage(CARD_ILLEGALITY);
//                                }
//                            } else {
//                                info = cardInfoList.get(0);
//                            }
//                            if (info != null) {
//                                // 提示卡状态
//                                // '0':'正常'；'1':'挂失';'2':'注销'
//                                if (!info.getIC_STATUS().equals("0")) {
//                                    switch (info.getIC_STATUS()) {
//                                        case "1":
//                                            handler.sendEmptyMessage(CARD_STATUS_LOSTS);
//                                            playVoice(MyConstant.SP_LOST_CARD);
//                                            break;
//                                        case "2":
//                                            handler.sendEmptyMessage(CARD_STATUS_CANCELEDS);
//                                            playVoice(MyConstant.SP_CANCEL_CARD);
//                                            break;
//                                    }
//                                    isduringOrderNo = "";
//                                    requestCard = false;
//                                    playCardError = true;
//                                    return;
//                                }
//                                // memberinfo
//                                // account status : 0 normal 1 freeze
//                                Log.e("look", "getMemberCardBalance info.getMI_ID():" + info.getMI_ID());
//                                List<TfMemberInfo> memberInfoList = pDaoSession.queryBuilder(TfMemberInfo.class)
//                                        .where(TfMemberInfoDao.Properties.MI_ID.eq(info.getMI_ID()))
//                                        .build()
//                                        .list();
//                                if (memberInfoList == null || memberInfoList.isEmpty()) {
//                                    isduringOrderNo = "";
//                                    requestCard = false;
//                                    playCardError = true;
//                                    return;
//                                }
//                                memberInfo = memberInfoList.get(0);
//                                List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
//                                        .where(TfMemberAccountRecordDao.Properties.MI_ID.eq(info.getMI_ID()))
//                                        .build()
//                                        .list();
//                                if (accountRecordList.isEmpty() || accountRecordList == null) {
//                                    isduringOrderNo = "";
//                                    requestCard = false;
//                                    cashRecord = null;
//                                } else {
//                                    Log.e("look", "getMemberCardBalance accountRecordList.size():" + accountRecordList.size());
//                                    for (TfMemberAccountRecord record : accountRecordList) {
//                                        Log.e("look", "getMemberCardBalance record.getACCOUNT_TYPE():" + record.getACCOUNT_TYPE());
//                                        if (record.getACCOUNT_TYPE().equals("0")) {
//                                            cashRecord = record;
//                                        } else if ((record.getACCOUNT_TYPE().equals("1"))) {
//                                            subsidyRecord = record;
//                                        }
//                                    }
//                                    if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
//                                        try {
//                                            TfCardInfo finalInfo = info;
//                                            TfMemberInfo finalMemberInfo = memberInfo;
//                                            TfMemberAccountRecord finalCashRecord = cashRecord;
//                                            TfMemberAccountRecord finalSubsidyRecord = subsidyRecord;
//                                            mainActivity.runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Log.d("frost", "calcurlateMemberBalance----4");
////                                                Float cashBalance = Float.parseFloat(finalCashRecord.getBALANCE());
////                                                Float subsidyBalance = Float.parseFloat(finalCashRecord != null ? finalCashRecord.getBALANCE() : "0");
////                                                String overallBalance = MyConstant.gFormat.format(subsidyBalance + cashBalance);
////                                                business_balance.setText(overallBalance);
////                                                business_vip_name.setText(finalMemberInfo.getMI_NAME());
////                                                business_vip_no.setText(finalMemberInfo.getMI_NO());
//                                                    MyUtil.appendFile("#####################会员卡线上onFailure 订单号" + curOrderNo + "#####################" );
//                                                    MyUtil.appendFile("请求时间：" + star);
//                                                    MyUtil.appendFile("请求地址：" + url);
//                                                    MyUtil.appendFile("请求参数：" + url);
//                                                    MyUtil.appendFile("响应参数：" + e.getMessage());
//                                                    MyUtil.appendFile("耗时：" + (end - star) + "");
//                                                    MyUtil.appendFile("结束时间：" + end);
//                                                    MyUtil.appendFile("##################### end #####################"+ "\n");
//                                                    calcurlateMemberBalance(icId, finalInfo, finalMemberInfo, finalCashRecord, finalSubsidyRecord);
//                                                }
//                                            });
//                                        } catch (Exception e1) {
//                                            uploadingError(url, e1.getMessage(), "会员卡支付失败onFailure", mContext);
//                                        } finally {
//                                            cashRecord = null;
//                                            subsidyRecord = null;
//                                            isduringOrderNo = "";
//                                            requestCard = false;
//                                            return;
//                                        }
//                                    }
//                                }
//                            }

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
                            isduringOrderNo = "";
                            requestCard = false;
                            playCardError = true;
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        long end = System.currentTimeMillis();
                        try {
                            isPaying = false;
                            TfCardInfo info = null;
                            TfMemberAccountRecord cashRecord = null;
                            TfMemberAccountRecord subsidyRecord = null;
                            TfMemberInfo memberInfo = null;
                            float cashBalance = 0, subsidyBalance = 0;
                            requestCard = false;

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
                                    showMessage("", resultBaseBean.getMsg());
                                    playVoice(MyConstant.SP_LOST_CARD);
                                } else if (resultBaseBean.getCode().equals(MyConstant.CARD_STATUS_CANCELED)) {
                                    handler.sendEmptyMessage(CARD_STATUS_CANCELEDS);
                                    playVoice(MyConstant.SP_CANCEL_CARD);
                                } else if (resultBaseBean.getCode().equals("0015")) {
                                    mainActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            business_state.setText(resultBaseBean.getMsg());
                                        }
                                    });
                                } else {
                                    handler.sendEmptyMessage(CARD_ILLEGALITY);
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
                                                        memberInfo.setMI_NO(bean.getMI_NO());
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
                                                            }
                                                        }
                                                    }
                                                    if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
//                                                        business_state.setText("");
                                                        Log.d("frost", "calcurlateMemberBalance----2");
                                                        onMainThreadRunCalaulater(icId, info, memberInfo, cashRecord, subsidyRecord);
                                                    } else {
                                                        isduringOrderNo = "";
                                                        playCardError = true;
                                                        //  isduringPayment = false;
                                                        handler.sendEmptyMessage(CARD_ILLEGALITY);
                                                        playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                                                    }
                                                }
                                            } else if (icId.substring(0, 2).equals("56") && icId.length() == 20) {
                                                info = new TfCardInfo();
                                                QueryCardResultInfo.DataBean.MemberBean bean = queryCardResultInfo.getData().getMember();
                                                if (bean != null) {
                                                    memberInfo = new TfMemberInfo();
                                                    memberInfo.setMI_NAME(bean.getMI_NAME());
                                                    memberInfo.setMI_NO(bean.getMI_NO());
                                                    memberInfo.setMI_TYPE(bean.getMI_TYPE());
                                                }
                                                if (queryCardResultInfo.getData().getAccount() != null && queryCardResultInfo.getData().getAccount().size() > 0) {
                                                    for (QueryCardResultInfo.DataBean.AccountBean record : queryCardResultInfo.getData().getAccount()) {
                                                        //<editor-fold desc="cash">
//                                                        if (record.getMI_ID().equals(cardBean.getMI_ID())) {
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
                                                            info.setMI_ID(cashRecord.getMI_ID());
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
//                                                        }
                                                    }
                                                }
                                                if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
//                                                        business_state.setText("");
                                                    Log.d("frost", "calcurlateMemberBalance----2");
                                                    onMainThreadRunCalaulater(icId, info, memberInfo, cashRecord, subsidyRecord);
                                                } else {
                                                    isduringOrderNo = "";
                                                    playCardError = true;
                                                    //  isduringPayment = false;
                                                    handler.sendEmptyMessage(CARD_ILLEGALITY);
                                                    playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
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
                            isduringOrderNo = "";
                            Log.d("frost", "onResponse: " + e.getMessage());
                            requestCard = false;
                            isPaying = false;
                            playCardError = true;
                            showMessage("提示", "请求错误，请稍后再试");
                            handler.sendEmptyMessage(REFRESH);
                            return;
//                            playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
                        }
                    }
                });
            } else {
                // TODO: 2019/12/2 线下支付
                if (fixed_price_status && !first) {
                    payMsg = business_price.getText().toString();
                }
                long star = System.currentTimeMillis();
                isPaying = false;
                Log.e("url", "线下支付");
                TfCardInfo info = null;
                TfMemberAccountRecord cashRecord = null;
                TfMemberAccountRecord subsidyRecord = null;
                TfMemberInfo memberInfo = null;
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
                    Log.e("android", "getMemberCardBalance info.getMI_ID():" + memberInfoList.toString());
                    if (memberInfoList == null || memberInfoList.isEmpty()) {
                        isduringOrderNo = "";
                        playCardError = true;
                        return;
                    }
                    memberInfo = memberInfoList.get(0);
                    List<TfMemberAccountRecord> accountRecordList = pDaoSession.queryBuilder(TfMemberAccountRecord.class)
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
                        Log.e("frost_test", "balance" + cashRecord.getBALANCE() + "\n" + subsidyRecord.getBALANCE());
                        Log.e("frost", "--------" + payMsg);
                        if (info != null && memberInfo != null && cashRecord != null && subsidyRecord != null) {
                            try {
                                long end = System.currentTimeMillis();
                                Float cashBalance1 = Float.parseFloat(cashRecord.getBALANCE());
                                Float subsidyBalance1 = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                                String balance = MyConstant.gFormat.format(subsidyBalance1 + cashBalance1);
                                Log.d("frost", "calcurlateMemberBalance----3");
                                MyUtil.appendFile("#####################会员卡离线 start 订单号" + curOrderNo + "#####################" + "\n");
                                MyUtil.appendFile("请求时间：" + star);
                                MyUtil.appendFile("请求地址：" + "离线支付");
                                MyUtil.appendFile("请求参数：" + "离线支付");
                                MyUtil.appendFile("响应参数：" + "离线支付");
                                MyUtil.appendFile("耗时：" + (end - star) + "");
                                MyUtil.appendFile("结束时间：" + end);
                                MyUtil.appendFile("##################### end #####################" + "\n");
                                calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                                if ("".equals(business_price.getText().toString()) || business_price.getText().toString() == null) {
                                    showMemberInfo(memberInfo, balance, "准备下单");
                                }
                                pDaoSession.clear();
                            } catch (Exception e) {
                                isPaying = false;
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
                business_state.setText("非法卡");
                playVoice(MyConstant.SP_MEDIA_ILLEGAL_CARD);
            }
        } catch (Exception e) {
            Log.d("frost", "getMemberCardBalance--error");
            requestCard = false;
            isPaying = false;
            //Trust anchor for certification path not found
        }
    }

    public boolean isNumber(String str) {

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match = pattern.matcher(str);
//        if (str.contains("."))
        if (str.contains(".") && str.length() - 1 == str.indexOf(".")) {
//            business_price.setText("");
//            business_put_price.setText("");
//            business_state.setText("准备下单");
//            handler.sendEmptyMessage(5);
//            showMessage("", "请输入正确金额！");
            return false;

        }
        if (match.matches() == false || str.equals("0")) {
//            business_price.setText("");
//            business_put_price.setText("");
//            business_state.setText("准备下单");
//            handler.sendEmptyMessage(5);
//            showMessage("", "请输入正确金额！");
            return false;
        } else {
            return true;
        }
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

    public void onMainThreadRunCalaulater(final String icId, final TfCardInfo info, final TfMemberInfo memberInfo, final TfMemberAccountRecord cashRecord, final TfMemberAccountRecord subsidyRecord) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("frost", "onMainThreadRunCalaulater");
                Float cashBalance1 = Float.parseFloat(cashRecord.getBALANCE());
                Float subsidyBalance1 = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
                String balance = MyConstant.gFormat.format(cashBalance1 + subsidyBalance1);
                if (!"".equals(payMsg) && payMsg != null) {
                    calcurlateMemberBalance(icId, info, memberInfo, cashRecord, subsidyRecord);
                }
//                Float cashBalance = Float.parseFloat(cashRecord.getBALANCE());
//                Float subsidyBalance = Float.parseFloat(subsidyRecord != null ? subsidyRecord.getBALANCE() : "0");
//                String overallBalance = MyConstant.gFormat.format(subsidyBalance + cashBalance);
//                business_vip_name.setText(memberInfo.getMI_NAME());
//                business_vip_no.setText(memberInfo.getMI_PHONE());
                if (!"支付成功".equals(business_state.getText().toString())) {
                    business_balance.setText(balance);
                    business_vip_name.setText(memberInfo.getMI_NAME());
                    business_vip_no.setText(memberInfo.getMI_NO());
                }
                if ("".equals(business_price.getText().toString())) {
                    handler.sendEmptyMessage(4);
                    mainActivity.showPay(mainActivity, "card", "准备下单 ", memberInfo.getMI_NO(), memberInfo.getMI_NAME(), balance);
                }
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

    /**
     * 结算
     */
    private void settlement() {
        handler.sendEmptyMessage(SETTLEMENT);
    }

    public boolean isPayMsg = false;

    /**
     * 刷新
     */
    private void refresh() {
        Log.e("frost", "refresh");

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isPaying) {
//            showMessage("警告", "请等待支付结果完成");
                } else {
                    playCardError = false;
                    playWxAliError = false;
                    isPayMsg = false;
                    requestCardTime = false;
//        isPayment = false;
                    payMsg = "";
                    curOrderNo = "";
                    business_put_price.setText("");
                    business_price.setText("");
                    business_vip_name.setText("");
                    business_balance.setText("");
                    business_vip_no.setText("");
                    business_state.setText("准备下单");
                    mainActivity.showPay(mainActivity, "card", "准备下单 ", "", "", "");
                }
            }
        });
    }

    /**
     * 输入框
     *
     * @param content
     */
    public void inputEdit(String content) {
        if (business_put_price != null) {
            business_put_price.requestFocus();
            int index = business_put_price.getSelectionStart();
            Editable editable = business_put_price.getText();
            editable.insert(index, content);
            String curContent[] = editable.toString().split("\\.");
            if (curContent.length >= 2) {
                if (curContent[1].length() >= 2) {
                    Log.d("frost", "length error");
//                    showMessage("提示", "请输入正确金额！");
                    return;
                }
            }
            business_put_price.setText(editable.toString());
            business_put_price.setSelection(index + content.length());
        }
    }

    /**
     * 输入的金额正确则返回true，反之false
     *
     * @return
     */
    private boolean judgeInputPrice(String inputStr) {
        String curContent[] = inputStr.split("\\.");
        float price;
        try {
            price = Float.parseFloat(inputStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            showMessage("", "请输入正确金额！");
            business_price.setText("");
            business_put_price.setText("");
            return false;
        }
        if (inputStr.isEmpty() || price == 0) {
//            showMessage("", "请输入正确金额！");
            business_price.setText("");
            business_put_price.setText("");
            return false;
        } else if (curContent.length >= 2) {
            if (curContent[1].length() >= 2) {
//                showMessage("提示", "请输入正确金额！");
                business_price.setText("");
                business_put_price.setText("");
                return false;
            }
        }
        return true;
    }

    public class OperationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:
//                    refresh();
                    break;
                case MyConstant.OPERATION_UPDATE_PERSON_INFO:
                    Log.d("frost", "mCurMealPersonCount:" + mCurMealPersonCount);
                    try {
//                        String curMealtimes = MyUtil.getCurMealTimes();
//                        if (mCurMealType == null || !mCurMealType.equals(curMealtimes)) {
//                            Log.e("pay", "curMealtimes != mCurMealType curMealtimes:" + curMealtimes + " mCurMealType:" + mCurMealType);
//                            gOperationHandler.obtainMessage(UPDATE_PERSON_NUMBER).sendToTarget();
//                        }
                        business_num_people.setText(mCurMealPersonCount + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
//                case UPDATE_PERSON_NUMBER:
//                    try {
//                        Log.d("frost", "更新人数");
//                        List<TfConsumeCardRecord> list = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
//                                .where(TfConsumeCardRecordDao.Properties.CREATETIME.ge(MyUtil.obtainCurrentSysDate(0) + " 00:00:00"),
//                                        TfConsumeCardRecordDao.Properties.CREATETIME.le(MyUtil.obtainCurrentSysDate(0) + " 23:59:59"))
//                                .build()
//                                .list();
//                        if (!list.isEmpty() && list != null) {
//                            mTotalPersonCount = list.size();
//                            // 当餐人数
//                            int mealcount = 0;
//                            String curMealtimes = MyUtil.getCurMealTimes();
//                            mCurMealType = curMealtimes;
//                            for (TfConsumeCardRecord record : list) {
//                                if (record.getMT_ID() == null) {
//                                    continue;
//                                }
//                                if (record.getMT_ID().equals(curMealtimes)) {
//                                    mealcount += 1;
//                                }
//                                mCurMealPersonCount = mealcount;
//                            }
//                        }
//                        // 当天账单总数
//                        business_num_people.setText("" + mCurMealPersonCount);
//                    } catch (Exception e) {
//                        Log.e("frost", "UPDATE_PERSON_NUMBER:" + e.getMessage());
//                    }
//                    break;
                case CLEAR_SCANNER_NUM:
                    business_put_price.setText("");
                    break;

            }
        }
    }

    private MediaPlayer mPlayer;
    public static OperationHandler gOperationHandler;

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
                //无效卡
                resId = R.raw.noeffectcard;
                break;
            case MyConstant.SP_MEDIA_ILLEGAL_CARD:
                //非法卡
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
//                            if (fixed_price_status) {
//
//                                Handler handler1 = new Handler();
//                                handler1.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        settlement();
////                                getMemberCardBalance(card_ID);
//                                    }
//                                }, 1500);
//                            }
                        }
                    }
                });
                mPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    //    @Override
//    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
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

    /**
     * 按键启动扫描功能
     */
    public void setupScan() {
//        Log.e("pay","setupScan");

        if (!isOpenScan) {
            isOpenScan = true;
            try {
                if (isFirst) {
//                    openScan();
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


    public void handleDecode(Result resultString, Bitmap barcode) {
//        isPaying = true;
//        List<TfConsumeCardRecord> cardRecords = pDaoSession.queryBuilder(TfConsumeCardRecord.class)
//                .where(TfConsumeCardRecordDao.Properties.COR_ID.eq(curOrderNo))
//                .limit(1)
//                .build()
//                .list();
//        try {
//            if (cardRecords.get(0).getCOR_ID().equals(curOrderNo)) {
//                Log.e("frost", "订单号已存在");
//                Log.e("frost", "curOrderNo:" + curOrderNo);
//                showMessage("提示", "该订单已支付");
//                isPaying = false;
//                handler.sendEmptyMessage(REFRESH);
//                return;
//            }
//        } catch (Exception e) {
//            Log.d("frost", e.toString());
//        }
//        if (cardRecords != null && cardRecords.size() > 0) {
//            Log.e("frost", "订单号已存在");
//            Log.e("frost", "curOrderNo:" + curOrderNo);
//            showMessage("提示", "该订单已支付");
//            handler.sendEmptyMessage(REFRESH);
//            return;
//        }
//
//        if (resultString == null) {
//            return;
//        }
//        business_put_price.requestFocus();
//
//        if (resultString.getText().substring(0, 2).equals("18")) {
//
//        } else if (resultString.getText().substring(0, 2).equals("28")) {
//            business_put_price.setText("");
//            showMessage("提示", "暂不支持支付宝支付" + "\n" + "如需开通请联系深圳科拜斯物联网科技有限公司");
//            return;
//        } else if (resultString.getText().substring(0, 2).equals("13")) {
//
//        } else {
//            business_put_price.setText("");
//            return;
//        }
//
//        if (resultString.getText().substring(0, 2).equals("18")) {
//            if (isPayment) {
//                refresh();
//            }
//            getMemberCardBalance(resultString.getText());
//            business_put_price.setText("");
//            return;
//        }
//
//        if (!MyUtil.networkState) {
//            showMessage("", "离线无法支付");
//            business_put_price.setText("");
//            // mainActivity.showPay("wait","离线无法支付");
//            mainActivity.showPay(mainActivity, "card", "离线无法支付", "", "", "");
//            return;
//        }
////
////        if (playWxAliError) {
////            showMessage("", "请重新刷新");
////            mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
////            return;
////        }
////
////        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
////            showMessage("", "正在支付中");
////            return;
////        }
//
//
////        if (isPayment) {
////            showMessage("", "该单已支付");
////            //mainActivity.showPay("wait","该单已支付");
////            mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
////            return;
////        }
////
////        if (isduringPayment) {
////            showMessage("", "订单正在支付中");
////            mainActivity.showPay(mainActivity, "card", "订单正在支付中", "", "", "");
////            return;
////        }
//
//
//        playBeepSoundAndVibrate();
//        Log.d("frost", "handleBarcodeScanner: " + resultString);
//        //清除付款码数字
//        gOperationHandler.sendEmptyMessageDelayed(CLEAR_SCANNER_NUM, MyConstant.SANNER_CLEAR_TIME_);
//        if (oldCode.isEmpty() || oldCode == null || !resultString.equals(oldCode)) {
//            oldCode = resultString.getText();
//        } else {
//            Log.d("frost", "can't repeat payment with same code!!!");
//            return;
//        }
//
//        String payPrice = business_price.getText().toString();
//        int dis_count = getDisCountForAll();//全员折扣
//        Log.i("frost", "handleBarcodeScanner-dis_count: " + dis_count);
//        if (dis_count < 100) {
//            payPrice = calculateDisCountPrice(payPrice, dis_count);
//            Log.i("frost", "handleBarcodeScanner- scan->payPrice   折扣后的值为->" + payPrice);
//        }
//        float payTotalPrice;
//        try {
//            payTotalPrice = Float.parseFloat(business_price.getText().toString());
//            if (payTotalPrice == 0) {
//                business_put_price.setText("");
//                return;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        if (resultString != null && resultString.getText().trim().length() > 0) {
//            switch (resultString.getText().substring(0, 2)) {
//                case "28":
//                    payWay = PAY_ALIPAY;
//                    payName = "支付宝";
//                    break;
//                case "13":
//                    payWay = PAY_WECHAT;
//                    payName = "微信";
//                    break;
//            }
//
//            isduringPayment = true;
//            isduringOrderNo = "" + curOrderNo;
//            long star = System.currentTimeMillis();
//            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
//            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
//            String url = ip + ":" + port + "/k-occ/pay/reverse/scan";
//            if (!"".equals(payMsg)) {
//                handler.sendEmptyMessage(3);
//            }
//            String clienCode, storeCode;
//            try {
//                clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
//                storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
////                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
////                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
//            } catch (Exception e) {
//                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
//                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
//            }
//            final JsonObject jsonData = new JsonObject();
//            jsonData.addProperty("clientCode", clienCode);
//            jsonData.addProperty("storeCode", storeCode);
//            jsonData.addProperty("authCode", resultString.getText());
//            jsonData.addProperty("requestNum", curOrderNo);
//            jsonData.addProperty("transamt", payPrice);
//
//
//            Log.e("frost", "handleBarcodeScanner jsonData:   " + jsonData.toString());
//            String price = payPrice;
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("data", jsonData.toString())
//                    .build();
//
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .writeTimeout(10, TimeUnit.SECONDS)
//                    .readTimeout(40, TimeUnit.SECONDS)
//                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
//                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
//                    .build();
//
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(requestBody)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    playWxAliError = true;
//                    isPaying = false;
//                    isPayment = false;
//                    isduringPayment = false;
//                    business_state.setText("支付错误");
//                    mainActivity.showPay(mainActivity, "card", "支付错误", "", "", "");
//                    pay_result_select = 1000;
//                    isduringOrderNo = "";
//                    createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
//                    reStartScan();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    long end = System.currentTimeMillis();
//                    try {
//                        Gson gson = new Gson();
//                        String body = response.body().string();
//                        MyUtil.appendFile("##################### 验证start 订单号:" + curOrderNo + " #####################" + "\n");
//                        MyUtil.appendFile("请求时间：" + star);
//                        MyUtil.appendFile("请求地址：" + url);
//                        MyUtil.appendFile("请求参数：" + jsonData.toString());
//                        MyUtil.appendFile("响应参数：" + body);
//                        MyUtil.appendFile("耗时：" + (end - star) + "");
//                        MyUtil.appendFile("结束时间：" + end);
//                        MyUtil.appendFile("##################### end #####################");
//                        Log.e("frost", "handleBarcodeScanner....3...body:" + body);
//
//                        JSONObject jsonRes = new JSONObject(body);
//                        if (jsonRes != null) {
//                            String code = jsonRes.getString("code");
//                            String orderId = jsonRes.optString("data");
//                            Log.e("frost", "payResultSelect....3=4...code:" + code + " orderId:" + orderId);
//
//                            if ("0000".equals(code)) {
//                                if (!fixed_price_status) {
//                                    // true 支付成功
//                                    Log.d("frost", "非估价coming");
//                                    isPayment = true;
//                                    isPaying = false;
//                                    isduringPayment = false;
//                                    isduringOrderNo = "";
//
//                                    createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 1);
//                                    Log.e("frost_test", "price:" + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))));
//                                    Log.e("frost", "success payWay:" + payWay + " payName...." + payName);
////                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
//                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER_WEIXIN);
//                                    mainActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            business_state.setText("支付成功");
//                                            mainActivity.showPay(mainActivity, "card", "支付成功 ", "", "", "");
//                                        }
//                                    });
////                                    VoiceUtils.with(mContext).Play(price + "", true);
//                                    int code_speak = mTts.startSpeaking(price + "", mTtsListener);
//                                    if (code_speak != ErrorCode.SUCCESS) {
//                                        Log.e("frost_speak", "语音合成失败,错误码: " + code);
//                                    }
//
//                                } else {
//                                    // true 支付成功
//                                    isPayment = true;
//                                    isPaying = false;
//                                    isduringPayment = false;
//                                    isduringOrderNo = "";
//                                    createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 1);
//                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER_WEIXIN);
//                                    Log.e("frost", "success payWay:" + payWay + " payName...." + payName);
////                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
////                                    VoiceUtils.with(mContext).Play(price + "", true);
//                                    int code_speak = mTts.startSpeaking(price + "", mTtsListener);
//                                    if (code_speak != ErrorCode.SUCCESS) {
//                                        Log.e("frost_speak", "语音合成失败,错误码: " + code);
//                                    }
//                                    mainActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            business_state.setText("支付成功");
//                                            mainActivity.showPay(mainActivity, "card", "支付成功 ", "", "", "");
//                                        }
//                                    });
//                                    if (fixed_price_status) {
//                                        Log.d("frost", "----------------------固价");
//                                    }
//                                }
//                            } else if ("0001".equals(code) || "0002".equals(code)) {
//                                isPayment = false;
//                                isPaying = false;
//                                handler.sendEmptyMessage(5);
//                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
//                                isduringPayment = false;
//                                Log.e("frost", "weixin or alipay fail code:" + code);
//                                isduringOrderNo = "";
//                                reStartScan();
//                            } else if ("0004".equals(code)) {
//                                isPayment = false;
//                                isPaying = false;
//                                isduringPayment = false;
//                                handler.sendEmptyMessage(2);
//                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
//                                Log.e("frost", "weixin or alipay CCR_STATUS2 code:" + code);
//                                isduringOrderNo = "";
//                                reStartScan();
//                            } else {
//                                handler.sendEmptyMessage(5);
//                                isPayment = true;
//                                isPaying = false;
//                                isduringPayment = false;
//                                isduringOrderNo = "";
//                                pay_result_select = 0;
//                                try {
//                                    Log.d("frost", "更新人数");
//                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER_WEIXIN);
//
//                                } catch (Exception e) {
//                                    Log.e("frost", "更新人数失败");
//                                    e.printStackTrace();
//                                }
//                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
//                                Log.e("frost", "weixin or alipay other code:" + code);
//                            }
//
//                            String msg = jsonRes.optString("msg");
//                            if (msg != null && msg.startsWith("交易")) {
//                                msg = msg.replaceAll("交易", "支付");
//                            }
//
//                            if (msg != null && msg.length() > 0) {
////                                showMessage("", msg);
////                                isPayMsg = true;
//                                if ("支付成功".equals(msg)) {
//                                    isPayment = true;
//                                } else {
//                                    isPayment = false;
//                                }
//                                isduringPayment = false;
//                            }
//                            Log.e("frost", "--87-msg:" + msg);
//                        } else {
//                            isPayment = false;
//                            isduringPayment = false;
//                        }
//
//                    } catch (Exception e) {
//                        isPayment = false;
//                        isduringPayment = false;
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public static boolean syncing = false;

    /**
     * 去数据库查找更新到服务器
     * 启动收银界面时，在线状态下，自动的去查找上次没有更新的数据
     * 即离线状态下的更新
     */
    public void syncDataToServer() {
        try {
            if (syncing || onlineSync) {
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
                handler.sendEmptyMessage(GONE_PROGRESS);
                // empty data does not need to sync
                return;
            } else {
                handler.sendEmptyMessage(VISIBILITY_PROGRESS);
            }

            if (!MyUtil.networkState) {
                // 无网络，不同步
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
     * 离线更新
     * 断网重连后调用
     */
    private void updateToServerInline(final List<TfConsumeCardRecord> cardRecordList,
                                      final List<TfConsumeDetailsRecord> detailsRecordList,
                                      final List<TfConsumeOrderRecord> orderRecordList) {
        Log.e("frost_syncDataToServer", "updateToServerInline");
        Gson gson = new Gson();
        final InlineUpdateInfo syncInfo = new InlineUpdateInfo();
        //
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
        long star = System.currentTimeMillis();
        syncInfo.setCard(cardBeans);
        syncInfo.setDetails(details);
        syncInfo.setOrder(orderBeans);
        String data = gson.toJson(syncInfo, InlineUpdateInfo.class);
        Log.e("frost", "离线更新数据json----" + data);
        Log.d(TAG, "updateToServerInline: " + data);
        if (MyUtil.isConfigServer()) {
            LocalRetrofit.createService().postUpdateInline(data)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BaseBean<?>>() {
                        @Override
                        public void onCompleted() {
                            syncing = false;
                        }

                        @Override
                        public void onError(Throwable e) {
//                            handler.sendEmptyMessage(GONE_PROGRESS);
                            Log.e("frost_error", "updateToServerInline:555");
                            syncing = false;
                            Log.d(TAG, "离线状态更新 onError: " + e.getMessage());
                            long end = System.currentTimeMillis();
                            MyUtil.appendFile("#####################离线数据更新Error 订单号" + "#####################" + "\n");
                            MyUtil.appendFile("请求时间：" + star);
                            MyUtil.appendFile("请求地址：" + " @POST(\"k-occ/consume/inline\")");
                            MyUtil.appendFile("请求参数：" + data);
                            MyUtil.appendFile("响应参数：" + e.getMessage());
                            MyUtil.appendFile("耗时：" + (end - star) + "");
                            MyUtil.appendFile("结束时间：" + end);
                            MyUtil.appendFile("##################### end #####################" + "\n");
                        }

                        @Override
                        public void onNext(BaseBean<?> syncResultInfo) {
//                            handler.sendEmptyMessage(GONE_PROGRESS);
                            long end = System.currentTimeMillis();
                            MyUtil.appendFile("#####################离线数据更新onNext 订单号" + curOrderNo + "#####################" + "\n");
                            MyUtil.appendFile("请求时间：" + star);
                            MyUtil.appendFile("请求地址：" + " @POST(\"k-occ/consume/inline\")");
                            MyUtil.appendFile("请求参数：" + data);
                            MyUtil.appendFile("响应参数：" + syncResultInfo.toString());
                            MyUtil.appendFile("耗时：" + (end - star) + "");
                            MyUtil.appendFile("结束时间：" + end);
                            MyUtil.appendFile("##################### end #####################" + "\n");
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
                                uploadingError(data, e.getMessage(), "线上上传onResponse", mContext);
                            }
                        }
                    });
        }
    }

    public ScheduledExecutorService mExecutor = new ScheduledThreadPoolExecutor(4);

    private void initTimer() {
        mExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                try {
                    payResultSelect();
                } catch (Exception e) {
                    Log.d("frost", "payResultSelect is error");
                    e.printStackTrace();
                }

//                try {
//                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER_ZERO);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }, 0, 30, TimeUnit.SECONDS);
//        mExecutor.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    Log.e("frost_syncDataToServer", "syncDataToServer");
//                    syncDataToServer();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.d("frost", "syncDataToServer is error");
//                }
//            }
//        }, 0, 3, TimeUnit.SECONDS);

    }

    public String calculateDisCountPrice(String price, int dis_count) {
        float result = Float.parseFloat(price);
        result = result * dis_count / 100f;
        price = MyConstant.gFormat.format(result);
        return price;
    }

    String result = "";

    public void handleBarcodeScanner(String resultString) {
        if (!MyUtil.obtainNetworkStatus(mContext)) {
            showMessage("", "离线无法支付");
            isPaying = false;
            business_put_price.setText("");
            // mainActivity.showPay("wait","离线无法支付");
            mainActivity.showPay(mainActivity, "card", "离线无法支付", "", "", "");
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
                handler.sendEmptyMessage(REFRESH);
                return;
            }
        } catch (Exception e) {
            Log.d("frost", e.toString());
        }
        if (cardRecords != null && cardRecords.size() > 0) {
            Log.e("frost", "订单号已存在");
            Log.e("frost", "curOrderNo:" + curOrderNo);
            showMessage("提示", "该订单已支付");
            handler.sendEmptyMessage(REFRESH);
            return;
        }

        if (resultString == null) {
            return;
        }
        business_put_price.requestFocus();


        if (resultString.substring(0, 2).equals("56")) {   //&& resultString.length() == 20
            getMemberCardBalance(resultString);
            business_put_price.setText("");
            return;
        } else if (resultString.substring(0, 2).equals("28")) {
//            isPaying = false;
//            business_put_price.setText("");
//            showMessage("提示", "暂不支持支付宝支付" + "\n" + "如需开通请联系深圳科拜斯物联网科技有限公司");
//            return;
            if (!"".equals(payMsg) && payMsg != null) {
                handler.sendEmptyMessage(6);
            }
        } else if (resultString.substring(0, 2).equals("13")) {
            if (!"".equals(payMsg) && payMsg != null) {
                handler.sendEmptyMessage(3);
            }
        } else {
            business_put_price.setText("");
            return;
        }


        if (!MyUtil.networkState) {
            showMessage("", "离线无法支付");
            isPaying = false;
            business_put_price.setText("");
            // mainActivity.showPay("wait","离线无法支付");
            mainActivity.showPay(mainActivity, "card", "离线无法支付", "", "", "");
            return;
        }
//
//        if (playWxAliError) {
//            showMessage("", "请重新刷新");
//            mainActivity.showPay(mainActivity, "card", "请重新刷新", "", "", "");
//            return;
//        }
//
//        if (isduringOrderNo != null && isduringOrderNo.length() > 0 && isduringOrderNo.equals(curOrderNo)) {
//            showMessage("", "正在支付中");
//            return;
//        }


//        if (isPayment) {
//            showMessage("", "该单已支付");
//            //mainActivity.showPay("wait","该单已支付");
//            mainActivity.showPay(mainActivity, "card", "该单已支付", "", "", "");
//            return;
//        }
//
//        if (isduringPayment) {
//            showMessage("", "订单正在支付中");
//            mainActivity.showPay(mainActivity, "card", "订单正在支付中", "", "", "");
//            return;
//        }


        playBeepSoundAndVibrate();
        Log.d("frost", "handleBarcodeScanner: " + resultString);
        //清除付款码数字
        gOperationHandler.sendEmptyMessageDelayed(CLEAR_SCANNER_NUM, MyConstant.SANNER_CLEAR_TIME_);
        if (oldCode.isEmpty() || oldCode == null || !resultString.equals(oldCode)) {
            oldCode = resultString;
        } else {
            Log.d("frost", "can't repeat payment with same code!!!");
            return;
        }

        String payPrice = business_price.getText().toString();
        int dis_count = getDisCountForAll();//全员折扣
        Log.i("frost", "handleBarcodeScanner-dis_count: " + dis_count);
        if (dis_count < 100) {
            payPrice = calculateDisCountPrice(payPrice, dis_count);
            Log.i("frost", "handleBarcodeScanner- scan->payPrice   折扣后的值为->" + payPrice);
        }
        float payTotalPrice;
        try {
            payTotalPrice = Float.parseFloat(business_price.getText().toString());
            if (payTotalPrice == 0) {
                isPaying = false;
                business_put_price.setText("");
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

            isduringPayment = true;
            isduringOrderNo = "" + curOrderNo;

            if ("".equals(payMsg) || payMsg == null) {
                isPaying = false;
                return;
            }
            long star = System.currentTimeMillis();
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String url = ip + ":" + port + "/k-occ/pay/reverse/scan";
            String clienCode, storeCode;
            try {
                clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
//                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
//                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
            } catch (Exception e) {
                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            }
            if ("".equals(curOrderNo) || curOrderNo == null || curOrderNo.isEmpty()) {
                curOrderNo = MyUtil.getPrimaryValue();
            }
            final JsonObject jsonData = new JsonObject();
            jsonData.addProperty("clientCode", clienCode);
            jsonData.addProperty("storeCode", storeCode);
            jsonData.addProperty("authCode", resultString);
            jsonData.addProperty("requestNum", curOrderNo);
            jsonData.addProperty("transamt", payPrice);

            Log.e("frost", "handleBarcodeScanner jsonData:   " + jsonData.toString());
            String price = payPrice;
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
                        playWxAliError = true;
                        isPaying = false;
                        isPayment = false;
                        isduringPayment = false;

                        handler.sendEmptyMessage(7);

                        mainActivity.showPay(mainActivity, "card", "支付超时", "", "", "");
                        pay_result_select = 1000;
                        isduringOrderNo = "";
                        createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
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
                                isPayment = true;
                                isPaying = false;
                                isduringPayment = false;
                                isduringOrderNo = "";
                                payMsg = "";
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 1);
                                Log.e("frost_test", "price:" + Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))));
                                Log.e("frost", "success payWay:" + payWay + " payName...." + payName);
//                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);
                                mTotalPersonCount += 1;
                                handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        business_state.setText("支付成功");
                                        mainActivity.showPay(mainActivity, "card", "支付成功 ", "", "", "");
                                    }
                                });
                                int code_speak = mTts.startSpeaking("支付成功" + price + "元", mTtsListener);
                                if (code_speak != ErrorCode.SUCCESS) {
                                    Log.e("frost_speak", "语音合成失败,错误码: " + code);
                                }
//                                    VoiceUtils.with(mContext).Play(price + "", true);
                            } else if ("0001".equals(code) || "0002".equals(code)) {
                                isPayment = false;
                                isPaying = false;
                                payMsg = "";
                                handler.sendEmptyMessage(REFRESH);
//                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
                                isduringPayment = false;
                                Log.e("frost", "weixin or alipay fail code:" + code);
                                isduringOrderNo = "";
                                showMessage("提示", msg1);
//                                reStartScan();
                            } else if ("0004".equals(code)) {
                                isPayment = false;
                                isPaying = false;
                                isduringPayment = false;
                                payMsg = "";
                                handler.sendEmptyMessage(2);
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
                                Log.e("frost", "weixin or alipay CCR_STATUS2 code:" + code);
                                isduringOrderNo = "";
                                reStartScan();
                            } else {
                                if (msg1.equals("缓存中没有第三方支付配置")) {
                                    showMessage("警告", msg1);
                                    handler.sendEmptyMessage(REFRESH);
                                    return;
                                }
                                handler.sendEmptyMessage(7);
                                isPayment = true;
                                isPaying = false;
                                isduringPayment = false;
                                isduringOrderNo = "";
                                payMsg = "";
                                pay_result_select = 0;
                                try {
                                    Log.d("frost", "更新人数");
//                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER_WEIXIN);

                                } catch (Exception e) {
                                    Log.e("frost", "更新人数失败");
                                    e.printStackTrace();
                                }
                                createConsumeInfo(Float.parseFloat(MyConstant.gFormat.format(Float.parseFloat(price.toString()))), 0, "0", "0", "", 1 + "", 0);
                                Log.e("frost", "weixin or alipay other code:" + code);
                            }

                            String msg = jsonRes.optString("msg");
                            if (msg != null && msg.startsWith("交易")) {
                                msg = msg.replaceAll("交易", "支付");
                            }

                            if (msg != null && msg.length() > 0) {
//                                showMessage("", msg);
//                                isPayMsg = true;
                                if ("支付成功".equals(msg)) {
                                    isPayment = true;
                                } else {
                                    isPayment = false;
                                }
                                isduringPayment = false;
                            }
                            Log.e("frost", "--87-msg:" + msg);
                        } else {
                            isPayment = false;
                            isduringPayment = false;
                        }
                    } catch (Exception e) {
                        isPaying = false;
                        handler.sendEmptyMessage(WEIXIN_CODE);
                        Log.e("frost", "handleBarcode  exception" + e.getMessage());
                        isPayment = false;
                        isduringPayment = false;
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
//            main_progress.setVisibility(View.VISIBLE);
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            String clienCode = "", storeCode = "";
            try {
                clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
//                clienCode = mainActivity.pUserInfo.getCLIENT_CODE();
//                storeCode = mainActivity.pUserInfo.getSTORE_CODE();
            } catch (Exception e) {
                clienCode = gSharedPre.getString(MyConstant.CLIENT_CODE, "");
                storeCode = gSharedPre.getString(MyConstant.STORE_CODE, "");
            }
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
//                        main_progress.setVisibility(View.GONE);
                        Gson gson = new Gson();
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
//                                    mCurMealPersonCount += 1;
//                                    gOperationHandler.obtainMessage(MyConstant.OPERATION_UPDATE_PERSON_INFO).sendToTarget();
                                    handler.sendEmptyMessage(UPDATE_PERSON_NUMBER);

                                    isPayment = true;
//                                    printerReceipt(null);
                                    playVoice(MyConstant.SP_MEDIA_CONSUME_SUCCESS);

//                                    String msg = jsonRes.optString("msg");
//                                    if (msg != null && msg.length() > 0) {
//                                        showMessage("", msg);
//                                    }
                                }
                            } else if ("0003".equals(code)) {

                            } else {
                                pay_result_select = 1000;
                                CCR_STATUS = 2;
                                Log.e("frost", "come here");
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
                        uploadingError(jsonData.toString(), e.getMessage(), "支付验证onResponse", mContext);
                    }
                }
            });
        }
    }

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
        if (captureActivityHandler == null) {
            captureActivityHandler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
        if (isFirst) {
            isFirst = false;
            stopScan();
        }
    }


    public void stopScan() {
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

    /**
     * 扫描初始化
     */
    private void openScan() {
        Log.e("pay", "openScan");
        // 初始化相机画布
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view1);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            Log.e("frost_test", "open_Scan");
            initCamera(surfaceHolder);
        } else {
            Log.e("frost_test", "openScan  surfaceHolder.addCallback(this);");
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
