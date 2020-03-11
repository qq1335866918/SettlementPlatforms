package com.tt1000.settlementplatform;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.AutoUpdateBean;
import com.tt1000.settlementplatform.bean.BaseBean;
import com.tt1000.settlementplatform.bean.HeartBean;
import com.tt1000.settlementplatform.bean.TaskBean;
import com.tt1000.settlementplatform.bean.TotalNum;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecord;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecord;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.SyncResultInfo;
import com.tt1000.settlementplatform.bean.member.SyncTableRecord;
import com.tt1000.settlementplatform.bean.member.SyncTableRecordDao;
import com.tt1000.settlementplatform.bean.member.TfCardInfo;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeDetailsRecordDao;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecord;
import com.tt1000.settlementplatform.bean.member.TfConsumeOrderRecordDao;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecord;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecord;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.TfPrintTask;
import com.tt1000.settlementplatform.bean.member.TfStoreRecord;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.pay.InlineUpdateInfo;
import com.tt1000.settlementplatform.bean.result_info.VerifyMachineResultInfo;
import com.tt1000.settlementplatform.feature.BuiltInPrinter;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;
import com.tt1000.settlementplatform.utils.DeviceInfoManageUtil;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.utils.SSLSocketClient;
import com.tt1000.settlementplatform.view.HomeFragment;
import com.tt1000.settlementplatform.view.LoginFragment;
import com.tt1000.settlementplatform.view.OperationFragment;
import com.tt1000.settlementplatform.view.SettingFragment;
import com.tt1000.settlementplatform.view.presentation.SecondaryDisplayService;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
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

import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;
import static com.tt1000.settlementplatform.utils.MyUtil.uploadingError;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "mainmenu";
    public static final int WIFI_ERROR = 1011;
    public static final int WIFI_NORMAL = 1012;
    public static final int CANCEL_PROGRESS_DIALOG = 0x3011;
    public static final int START_SCHEDULE_TASK = 0x3012;
    public static final int START_SCHEDULE_TASKS = 0x3111;
    public static final int OPEN_SOCKET = 0x5555;
    //机器绑定成功  权限是否拥有
    public static final int BIND_MACHINE_SUCD = 0x3013;
    public static final int SHOW_BIND_SUCC = 0x3014;
    public static final int SHOW_PROGRESS = 0x3019;
    public static final int SYNC_VISIBILITY = 132;
    public static final int SYNC_GONE = 133;
    //检查服务器是否有新版本
    public static final int CHECKED_VRESION = 0x3015;
    public static final int UPLOAD_ORDER = 0x3016;

    public static final int INSTALL_APK = 123456;

    private Context mContext;

    private FragmentManager fragmentManager;
    private final int containerId = R.id.fragment_container;
    //nfc
    public NfcAdapter mNfcAdapter;
    public IntentFilter[] mIntentFilter = null;
    public PendingIntent mPendingIntent = null;
    public String[][] mTechList = null;
    private ProgressBar sync_progress;
    private ImageView img_sync;
    private ProgressBar progressBar;
    private ProgressBar version_bar;
    private TextView txAppTitle;
    public TextView txFps;
    private TextView txUserId;
    private TextView txVersion;
    private TextView txMachineName;
    private TextClock txDate;
    public ImageView ivWifi;
    private ImageView ivReadCard;
    private ImageView ivPower;
    private FrameLayout fragment_container;
    private LinearLayout ll_sync;
    private ProgressDialog progressDialog;
    private AlertDialog exitSysDialog, backHomeDialog, backLoginDialog;
    private AlertDialog dialog;

    public OnScanResultCallback callback;
    public OnBarcodeScannerCallback barcodeCallback;

    public static List<BaseFragment> fragmentList;
    // 当前登陆账号的操作员信息
    public TfUserInfo pUserInfo;
    public StoreConfig pStoreConfig;
    // 同步数据的个数
    public int syncNum = 0;
    public int taskCount = 0;

    public boolean isDestory = false;

    public List<TaskBean> taskList;
    // 网络连接是否断开
    public boolean wifiState = false;

    public boolean machineExpire = false;

    public static SyncTableRecord syncTableRecord = null;

    public LoginFragment loginFragment;

    public DaoSession session;
    // 是否在同步
    public boolean isSync = false;
    //是否是 刚登录完正在初始化
    private boolean isInit = false;
    private int init_index = 0;
    private int num = 0;

    public BuiltInPrinter pPrinter;
    private ImageView img_upload;
    public static final int VISIBILITY_PROGRESS = 520;
    public static final int GONE_PROGRESS = 521;
    public static boolean is_can = true;
    private String download_url;
    public ScheduledThreadPoolExecutor mExecutor = new ScheduledThreadPoolExecutor(5);
    public ScheduledThreadPoolExecutor mExecutor_sync = new ScheduledThreadPoolExecutor(5);

    public static ThreadPoolExecutor gTHREAD_POOL = new ThreadPoolExecutor(2,
            5,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    public static MainHandler gUiHandler;
    private String bindSuccMsg = "";
    private AlertDialog.Builder alertDialog;
    private static int PORT = 9698;
    IoAcceptor acceptor = null;

    public class MainHandler extends Handler {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case UPLOAD_ORDER:
                    timeToUpload();
                    break;
                case VISIBILITY_PROGRESS:
                    img_upload.setImageResource(R.drawable.upload_end);
                    break;
                case GONE_PROGRESS:
                    img_upload.setImageResource(R.drawable.upload_start);
                    break;
                case OPEN_SOCKET:
                    try {
//                        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory();定时上传
//                        textLineCodecFactory.setDecoderMaxLineLength(102400000);
//                        textLineCodecFactory.setEncoderMaxLineLength(102400000);
//                        ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(textLineCodecFactory);
//                        // 设置过滤器（使用mina提供的文本换行符编解码器）
//                        acceptor.getFilterChain().addLast("codec", protocolCodecFilter);

                        // 创建一个非阻塞的server端的Socket
                        acceptor = new NioSocketAcceptor();
                        // 设置过滤器（使用mina提供的文本换行符编解码器）
//                        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
//                                        LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));

                        DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = acceptor.getFilterChain();
                        LoggingFilter loggingFilter = new LoggingFilter();
                        defaultIoFilterChainBuilder.addLast("loggingFilter", loggingFilter);
//		 TextLineCodecFactory textLineCodecFactory =
//		         new TextLineCodecFactory(charset,LineDelimiter.WINDOWS.getValue(),
//				 LineDelimiter.WINDOWS.getValue());
                        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory();
                        ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(textLineCodecFactory);
                        defaultIoFilterChainBuilder.addLast("protocolCodecFilter", protocolCodecFilter);
                        // 自定义的编解码器
                        // acceptor.getFilterChain().addLast("codec", new
                        // ProtocolCodecFilter(new CharsetCodecFactory()));
                        // 设置读取数据的换从区大小
                        acceptor.getSessionConfig().setReadBufferSize(2048);
                        // 读写通道10秒内无操作进入空闲状态
                        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
                        // 为接收器设置管理服务
                        acceptor.setHandler(new ServerHandler());
                        // 绑定端口
                        acceptor.bind(new InetSocketAddress(PORT));
                        System.out.println("服务器启动成功... 端口号未：" + PORT);

                        MyUtil.appendFile("##########################################");
                        MyUtil.appendFile("服务器启动成功... 端口号：" + PORT);
                        MyUtil.appendFile("时间：" + System.currentTimeMillis());
                        MyUtil.appendFile("##########################################");
                    } catch (Exception e) {
                        System.out.println("服务器启动异常...");
                        MyUtil.appendFile("##########################################");
                        MyUtil.appendFile("服务器启动异常..." + e.getMessage());
                        MyUtil.appendFile("时间：" + System.currentTimeMillis());
                        MyUtil.appendFile("##########################################");
                        acceptor.setCloseOnDeactivation(true);
                    }

                    break;
                case INSTALL_APK:
                    MainActivity.this.version_bar.setVisibility(View.GONE);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //aandroid N的权限问题
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.tt1000.settlementplatform", new File(Environment.getExternalStorageDirectory(), "SettlementPlatform.apk"));//注意修改
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "SettlementPlatform.apk")), "application/vnd.android.package-archive");
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    MainActivity.this.startActivity(intent);
                    break;
                case SYNC_VISIBILITY:
                    img_sync.setImageResource(R.drawable.sync_end);
                    break;
                case SYNC_GONE:
                    img_sync.setImageResource(R.drawable.sync_start);
                    break;
                case SHOW_PROGRESS:
                    MainActivity.this.version_bar.setVisibility(View.VISIBLE);
                    break;
                case BIND_MACHINE_SUCD:
                    bindSuccMsg = (String) msg.obj;
                    String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
                    boolean registerMachine = MyConstant.gSharedPre.getBoolean(MyConstant.SP_REGISTER_MACHINE, false);
                    if (registerMachine && !machineNo.isEmpty() && !progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                    isInit = true;
                    initData();
                    break;
//                case 1010:
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                    builder1.setIcon(android.R.drawable.ic_dialog_info);
//                    builder1.setTitle("提示");
//                    builder1.setCancelable(true);
//                    builder1.setMessage("发现最新版本，是否现在安装");
//                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            if (!"".equals(download_url)) {
//                                downLoadFile(download_url);
//                            }
//                        }
//                    });
//                    builder1.create().show();
//                    break;
//                case 1023:
//                    break;
                case CHECKED_VRESION:
                    try {
                        String url = "";
                        // 获取packagemanager的实例
                        PackageManager packageManager = getPackageManager();
                        // getPackageName()获取当前类的包名，0代表是获取版本信息
                        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
                        String versionName = packInfo.versionName;
                        int versionCode = packInfo.versionCode;
                        String ip = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
                        String port = MyConstant.gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                        String productId = MyConstant.gSharedPre.getString(MyConstant.SP_PRODUCT_ID, "");
                        if ("".equals(productId)) {
                            url = ip + ":" + port + "/k-occ/version/select/by/productId/00";
                        } else {
                            url = ip + ":" + port + "/k-occ/version/select/by/productId/" + productId;
                        }
                        try {
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            client.newCall(request).enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "获取服务器版本失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String body = response.body().string();
                                    Log.d("frost", body);
                                    Gson gson = new Gson();
                                    try {
                                        AutoUpdateBean autoUpdateBean = gson.fromJson(body, AutoUpdateBean.class);
//                                        String msg1 = autoUpdateBean.getMsg();
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(MainActivity.this,"升级onResponse:"+msg1,Toast.LENGTH_LONG).show();
//                                            }
//                                        });
                                        if (autoUpdateBean.getCode().equals("0000")) {
                                            String version_no = autoUpdateBean.getData().get(0).getVERSION_NO();
                                            download_url = autoUpdateBean.getData().get(0).getDOWNLOAD_URL();
                                            if (Float.parseFloat(version_no) > Float.parseFloat(versionName)) {
//                                                gUiHandler.sendEmptyMessage(1010);
                                                downLoadFile(download_url);
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.e("frost_update_onResponse", e.getMessage());
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Log.e("frost", e.getMessage());
                        }
                        Log.d("frost", "version:" + versionName);
                        Log.d("frost", "versionCode:" + versionCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SHOW_BIND_SUCC:
                    showBindMachineSucc();
                    break;
                case MyConstant.CHANGE_APP_TITLE:
                    txAppTitle.setText(msg.obj.toString());
                    break;
                case 2:
                    long fps = (long) msg.obj;
                    if (fps <= 100 && fps > 0) {
                        txFps.setTextColor(Color.parseColor("#33b422"));
                    } else if (fps <= 300 && fps > 100) {
                        txFps.setTextColor(Color.parseColor("#fdcc23"));
                    } else {
                        txFps.setTextColor(Color.parseColor("#d5434a"));
                    }
                    txFps.setText(msg.obj + "ms");
                    break;
                case 6:
//                    boolean isConnect = MyUtil.networkState;
                    boolean isConnect = MyUtil.obtainNetworkStatus(MainActivity.this);
                    if (isConnect) {
                        ivWifi.setImageResource(R.drawable.wifi_normal);
                        inlineUpdate();
                    } else {
                        ivWifi.setImageResource(R.drawable.wifi_error);
                        txFps.setText("网络断开");
                    }
                    break;
                case WIFI_ERROR:
                    ivWifi.setImageResource(R.drawable.wifi_error);
                    txFps.setText("服务器连接失败");
                    txFps.setTextColor(Color.parseColor("#d5434a"));
                    break;
                case WIFI_NORMAL:
                    ivWifi.setImageResource(R.drawable.wifi_normal);
                    inlineUpdate();
                    break;
                case 7:
                    if (hasNfc(mContext)) {
                        ivReadCard.setImageResource(R.drawable.card_normal);
                    } else {
                        ivReadCard.setImageResource(R.drawable.card_error);
                    }
                    break;
                case MyConstant.REPLACE_FRAGMENT_TO_STACK:
                    BaseFragment stackFragment = (BaseFragment) msg.obj;
                    repleaseFragmentToStack(stackFragment);
                    break;
                case MyConstant.REPLACE_FRAGMENT_NORMAL:
                    BaseFragment normalFramgnet = (BaseFragment) msg.obj;
                    repleaseFragment(normalFramgnet);
                    break;
                case MyConstant.SHOW_PROGRESS_DIALOG:

                    break;
                case CANCEL_PROGRESS_DIALOG:
                    cancelProgressDiaglog();
                    break;
                case START_SCHEDULE_TASKS:
                    mExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                if (MyUtil.obtainNetworkStatus(mContext)) {
                                    sendFps();
                                } else {
                                    gUiHandler.sendEmptyMessage(WIFI_ERROR);
                                }
                            } catch (Exception e) {
                                Log.d("frost", "fps error");
                            }
                        }
                    }, 0, 1, TimeUnit.SECONDS);
                    break;
                case START_SCHEDULE_TASK://开启定时任务
                    //关于心跳的定时任务
                    Log.e("url", "-----------------START_SCHEDULE_TASK");
                    try {
                        sendHeartbeatAtFixedRate();
                        regularlyUpdateTable();
                    } catch (Exception e) {
                        Log.e("frost", e.toString());
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private void timeToUpload() {
        mExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Log.e("frost_syncDataToServer", "syncDataToServer");
                    syncDataToServer();
                } catch (Exception e) {
                    Log.d("frost", "syncDataToServer is error");
                }
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public static boolean syncing = false;

    /**
     * 去数据库查找更新到服务器
     * 启动收银界面时，在线状态下，自动的去查找上次没有更新的数据
     * 即离线状态下的更新
     */
    public void syncDataToServer() {
        try {
            if (syncing) {
                return;
            }
            List<TfConsumeOrderRecord> orderRecords = new ArrayList<>();
            List<TfConsumeDetailsRecord> detailsRecords = new ArrayList<>();
            List<TfConsumeCardRecord> cardRecords = new ArrayList<>();
            cardRecords = session.queryBuilder(TfConsumeCardRecord.class)
                    .where(
                            //TfConsumeCardRecordDao.Properties.MI_ID.isNotNull(),
                            TfConsumeCardRecordDao.Properties.CCR_STATUS.eq(1),
                            TfConsumeCardRecordDao.Properties.ISM_STATUS.eq(0))
                    .orderAsc(TfConsumeCardRecordDao.Properties.CCR_ID)
                    .limit(100)
                    .build()
                    .list();
            for (TfConsumeCardRecord cardRecord : cardRecords) {
                List<TfConsumeOrderRecord> orderList = new ArrayList<>();
                List<TfConsumeDetailsRecord> detailList = new ArrayList<>();

                orderList = session.queryBuilder(TfConsumeOrderRecord.class)
                        .where(TfConsumeOrderRecordDao.Properties.ISM_STATUS.eq(0),
                                TfConsumeOrderRecordDao.Properties.COR_ID.eq(cardRecord.getCOR_ID()))
                        .build()
                        .list();

                detailList = session.queryBuilder(TfConsumeDetailsRecord.class)
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
                gUiHandler.sendEmptyMessage(GONE_PROGRESS);
                // empty data does not need to sync
                return;
            } else {
                gUiHandler.sendEmptyMessage(VISIBILITY_PROGRESS);
            }

            if (!MyUtil.obtainNetworkStatus(mContext)) {
                // 无网络，不同步
                return;
            }
            if (!syncing) {
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
                    cardBean.setMI_ID("");
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
                            gUiHandler.sendEmptyMessage(GONE_PROGRESS);
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
                            gUiHandler.sendEmptyMessage(GONE_PROGRESS);
                        }

                        @Override
                        public void onNext(BaseBean<?> syncResultInfo) {
//                            handler.sendEmptyMessage(GONE_PROGRESS);
                            gUiHandler.sendEmptyMessage(GONE_PROGRESS);
                            long end = System.currentTimeMillis();
                            MyUtil.appendFile("#####################离线数据更新onNext 订单号" + "#####################" + "\n");
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
                                                    cardRecord.setCCR_UPLOAD_STATUS("1");
                                                    cardRecord.setCCR_UPLOAD_TIME(MyUtil.dateConversion(System.currentTimeMillis()));
                                                    session.update(cardRecord);
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

    // TODO: 2019/12/10 新版本下载
    private void downLoadFile(String fileUrl) {
        Log.e("frost_downLoad", fileUrl);
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .connectTimeout(30, TimeUnit.SECONDS)//设置连接超时时间
                    .build();
            Request request = new Request.Builder().url(fileUrl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "新版本连接失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("frost_downLoad", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;//输入流
                    FileOutputStream fos = null;//输出流
                    try {
                        is = response.body().byteStream();//获取输入流
                        long total = response.body().contentLength();//获取文件大小
                        gUiHandler.sendEmptyMessage(SHOW_PROGRESS);
                        MainActivity.this.version_bar.setMax((int) total);//为progressDialog设置大小
                        if (is != null) {
                            Log.d("frost", "onResponse: 不为空");
                            File file = new File(Environment.getExternalStorageDirectory(), "SettlementPlatform.apk");// 设置路径
                            fos = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int ch = -1;
                            int process = 0;
                            while ((ch = is.read(buf)) != -1) {
                                fos.write(buf, 0, ch);
                                process += ch;
                                downLoading(process);       //这里就是关键的实时更新进度了！
                            }
                        }
                        fos.flush();
                        // 下载完成
                        if (fos != null) {
                            fos.close();
                        }
                        gUiHandler.sendEmptyMessage(INSTALL_APK);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.version_bar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                            Log.e("frost", e.toString());
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                            Log.e("frost", e.toString());
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e("frost", e.getMessage());
        }
    }

    /**
     * 进度条实时更新
     *
     * @param i
     */
    public void downLoading(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                version_bar.setProgress(i);
            }
        });
    }

    /**
     * 显示 绑定 成功的提示
     */
    private void showBindMachineSucc() {
        Log.i(TAG, "showBindMachineSucc: ");
        AlertDialog bindSuccDialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("同步完成")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到主界面
                        gUiHandler.obtainMessage(MyConstant.REPLACE_FRAGMENT_NORMAL, new HomeFragment()).sendToTarget();
                        dialog.cancel();
                    }
                })
                .create();
        bindSuccDialog.show();
    }

    public static class AutoStartRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    /**
     * 获取当前版本号
     *
     * @return
     * @throws Exception
     */
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.GONE);
        }
//        SQLiteDatabase database = MyUtil.readDatabase(MyConstant.DB_PATH, MyConstant.DB_NAME);
        MyUtil.removeFileByTime("/sdcard/JsonLog/");
        String crm_addr = MyConstant.gSharedPre.getString(MyConstant.SP_CRM_ADDRESS, "");
        if (TextUtils.isEmpty(crm_addr)) {
            Log.e("frost", "crm-address为空");
            MyConstant.gEditor.putString(MyConstant.SP_CRM_ADDRESS, "").commit();
        }

//        ConnectionInfo info = new ConnectionInfo("192.168.1.50", 9698);
//        OkSocket.open(info).connect();
//        OkSocket.open(info).send(new TestSendData());


        alertDialog = new AlertDialog.Builder(MainActivity.this);
        mContext = this;
        session = MyApplication.getInstance();
        // TODO: 2019/12/13   语音播报错误注释
//        pPrinter = new BuiltInPrinter(this);
        gUiHandler = new MainHandler();
        taskList = new ArrayList<>();

        initView();
        initNFC();
        initFragment();
//        gUiHandler.sendEmptyMessage(6);
        gUiHandler.sendEmptyMessage(CHECKED_VRESION);
        gUiHandler.sendEmptyMessage(UPLOAD_ORDER);
        dialog = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exit();
                    }
                })
                .create();
        Intent intent = new Intent(MainActivity.this, SecondaryDisplayService.class);
        startService(intent);
        bindService(intent, mConn, Service.BIND_AUTO_CREATE);
        MainActivity.gUiHandler.sendEmptyMessage(MainActivity.START_SCHEDULE_TASK);
        MainActivity.gUiHandler.sendEmptyMessage(MainActivity.START_SCHEDULE_TASKS);
        MainActivity.gUiHandler.sendEmptyMessage(OPEN_SOCKET);
        verifyMachineDate();
        daleteDao();
    }

    private void daleteDao() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date formNow3Month = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(formNow3Month);
        session.queryBuilder(TfConsumeCardRecord.class)
                .where(TfConsumeCardRecordDao.Properties.CREATETIME.le(format))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        session.queryBuilder(TfConsumeDetailsRecord.class)
                .where(TfConsumeDetailsRecordDao.Properties.CREATETIME.le(format))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        session.queryBuilder(TfConsumeOrderRecord.class)
                .where(TfConsumeOrderRecordDao.Properties.CREATETIME.le(format))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    /**
     * 在固定时间点 发送心跳服务
     */
    private void sendHeartbeatAtFixedRate() {
//        try {
//            // 网络延迟: ms
//            mExecutor.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    sendHeart();
//                }
//            }, 0, MyConstant.HEARTBEAT_INTERVAL_TIME_, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            Log.e("frost_heart", "心跳中断" + e.getMessage());
//        }
        try {
            // 网络延迟: ms
            mExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (MyUtil.obtainNetworkStatus(mContext)) {
                        String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                        String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                        String machine_no = gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
                        String url = ip + ":" + port + "/k-occ/api/kbs/device/heartbeat";
                        List<TfConsumeCardRecord> cardRecords = session.queryBuilder(TfConsumeCardRecord.class)
                                .where(TfConsumeCardRecordDao.Properties.ISM_STATUS.eq(0),
                                        TfConsumeCardRecordDao.Properties.CCR_STATUS.eq(1))
                                .build()
                                .list();
                        JsonObject jsonObject = new JsonObject();
                        double cup = Double.parseDouble(DeviceInfoManageUtil.getTotalCpuRate() + "");
                        double memory = Double.parseDouble(DeviceInfoManageUtil.getUsedPercentValue(MainActivity.this));
                        DecimalFormat df = new DecimalFormat("####0.00");
                        jsonObject.addProperty("machineNo", machine_no);
                        jsonObject.addProperty("notUploadOrder", cardRecords.size());
                        jsonObject.addProperty("cpu", df.format(cup));
                        jsonObject.addProperty("readCard", 0);
                        jsonObject.addProperty("rfid", 0);
                        jsonObject.addProperty("memory", df.format(memory));
                        List<StoreConfig> storeConfigList = session.queryBuilder(StoreConfig.class)
                                .build()
                                .list();
                        if (!storeConfigList.isEmpty() && storeConfigList != null) {
                            jsonObject.addProperty("sync", 1);
                        } else {
                            jsonObject.addProperty("sync", 0);
                        }
                        jsonObject.addProperty("registerIp", MyUtil.getIP(mContext));
                        jsonObject.addProperty("marchineTime", System.currentTimeMillis());
                        RequestBody requestBody = new FormBody.Builder().add("data", jsonObject.toString()).build();
                        OkHttpClient client = new OkHttpClient.Builder()
                                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                        client.newCall(request).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                gUiHandler.sendEmptyMessage(WIFI_ERROR);
                                Log.d("frost_heart", "心跳----onFailure" + e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.d("frost_heart", "心跳----onResponse");
                                gUiHandler.obtainMessage(WIFI_NORMAL).sendToTarget();
                                String body = response.body().string();
                                Gson gson = new Gson();
                                try {
                                    HeartBean bean = gson.fromJson(body, HeartBean.class);
                                    if (bean.getCode().equals("0000") && bean.isResult()) {
                                        String systemTime = bean.getData().getSystemTime();
                                        long responseTime = 0;
                                        try {
                                            responseTime = MyUtil.stringToLong(systemTime, "yyyy-MM-dd HH:mm:ss");
                                            long sysTime = System.currentTimeMillis();
                                            if (responseTime - sysTime < 1000 * 3600 * 24) {
                                            } else {
                                                showMessage("错误", "设备时间错误" + "\n" + "请矫正后重新登录");
                                            }
                                        } catch (ParseException e) {
                                        }
                                    }
                                } catch (Exception e) {
                                    gUiHandler.sendEmptyMessage(WIFI_ERROR);
                                    Log.e("frost", e.getMessage());
                                }
                            }
                        });
//                        String delay = new String();
//                        Process p = null;
//                        try {
////                            String x = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
//                            String substring = ip.substring(8);
//                            p = Runtime.getRuntime().exec("/system/bin/ping -c 4 " + substring);
//                            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                            String str = new String();
//                            while ((str = buf.readLine()) != null) {
//                                if (str.contains("avg")) {
//                                    int i = str.indexOf("/", 20);
//                                    int j = str.indexOf(".", i);
//                                    delay = str.substring(i + 1, j);
//                                }
//                            }
//                            Log.e("frost_ping", delay);
//                            gUiHandler.obtainMessage(2, Long.parseLong(delay)).sendToTarget();
//                        } catch (IOException e) {
//                            Log.e("frost_ping:", e.getMessage());
//                        }
                    } else {
                        gUiHandler.sendEmptyMessage(WIFI_ERROR);
                    }
                }
            }, 0, MyConstant.HEARTBEAT_INTERVAL_TIME_, TimeUnit.SECONDS);
            //sendHeratDB();
//            sendFps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFps() {
        try {
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String delay = new String();
            Process p = null;
            String serverIp = ip.substring(8);

            p = Runtime.getRuntime().exec("ping -c 1 -w 20 " + serverIp);
//            p = Runtime.getRuntime().exec("/system/bin/ping -t " + serverIp);
            int status = p.waitFor();
            if (status == 0) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String str = new String();
                while ((str = buf.readLine()) != null) {
                    if (str.contains("avg")) {
//                        Log.e("frost_ping", str);
                        int i = str.indexOf("/", 20);
                        int j = str.indexOf(".", i);
                        delay = str.substring(i + 1, j);
                    }
                }
//                Log.e("frost_ping", delay);
                gUiHandler.obtainMessage(2, Long.parseLong(delay)).sendToTarget();
            }
        } catch (IOException e) {
            Log.e("frost_ping:", e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendHeart() {
        if (MyUtil.obtainNetworkStatus(mContext)) {
            try {
                String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                String machine_no = gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
                String url = ip + ":" + port + "/k-occ/api/kbs/device/heartbeat";
                List<TfConsumeCardRecord> cardRecords = session.queryBuilder(TfConsumeCardRecord.class)
                        .where(TfConsumeCardRecordDao.Properties.ISM_STATUS.eq(0))
                        .build()
                        .list();
                JsonObject jsonObject = new JsonObject();
                double cup = Double.parseDouble(DeviceInfoManageUtil.getTotalCpuRate() + "");
                double memory = Double.parseDouble(DeviceInfoManageUtil.getUsedPercentValue(MainActivity.this));
                DecimalFormat df = new DecimalFormat("####0.00");
                jsonObject.addProperty("machineNo", machine_no);
                jsonObject.addProperty("notUploadOrder", cardRecords.size());
                jsonObject.addProperty("cpu", df.format(cup));
                jsonObject.addProperty("readCard", 0);
                jsonObject.addProperty("rfid", 0);
                jsonObject.addProperty("memory", df.format(memory));
                List<StoreConfig> storeConfigList = session.queryBuilder(StoreConfig.class)
                        .build()
                        .list();
                if (!storeConfigList.isEmpty() && storeConfigList != null) {
                    jsonObject.addProperty("sync", 1);
                } else {
                    jsonObject.addProperty("sync", 0);
                }
                jsonObject.addProperty("registerIp", MyUtil.getIP(mContext));
                jsonObject.addProperty("marchineTime", System.currentTimeMillis());
                Log.e("frost", jsonObject.toString());
                RequestBody requestBody = new FormBody.Builder().add("data", jsonObject.toString()).build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        gUiHandler.obtainMessage(WIFI_ERROR).sendToTarget();
                        Log.d("frost_heart", "心跳----onFailure" + e.getMessage());
                        MyUtil.appendFile("心跳----onFailure:------------>" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.d("frost_heart", "心跳----onResponse");
                            gUiHandler.obtainMessage(WIFI_NORMAL).sendToTarget();
                            String body = response.body().string();
                            Gson gson = new Gson();
                            HeartBean bean = gson.fromJson(body, HeartBean.class);
                            if (bean.getCode().equals("0000") && bean.isResult()) {
                                String systemTime = bean.getData().getSystemTime();
                                long responseTime = 0;
                                try {
                                    responseTime = MyUtil.stringToLong(systemTime, "yyyy-MM-dd HH:mm:ss");
                                    long sysTime = System.currentTimeMillis();
                                    if (responseTime - sysTime < 1000 * 3600 * 24) {
                                    } else {
                                        showMessage("错误", "设备时间错误" + "\n" + "请矫正后重新登录");
                                    }
                                } catch (ParseException e) {
                                }
                            }
                        } catch (Exception e) {
                            gUiHandler.obtainMessage(WIFI_ERROR).sendToTarget();
                            Log.e("frost", e.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("frost", "心跳失败？");
            }
        } else {
            gUiHandler.obtainMessage(WIFI_ERROR).sendToTarget();
            MyUtil.appendFile("心跳---->无网络连接");
        }
    }

    public void showMessage(final String title, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.setTitle(title);
                dialog.setMessage(msg);
                dialog.show();
            }
        });
    }

    private void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private void cancelProgressDiaglog() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void verifyMachineDate() {
        try {
            // 验证机器到期时间
            String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
            long machineExpire = MyConstant.gSharedPre.getLong(MyConstant.SP_MACHINE_EXPIRE, 0);
            // Log.e("url","machineExpire:" + machineExpire);
//            Log.e("url","MyUtil.dateConversion(machineExpire):" + MyUtil.dateConversion(machineExpire));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//            Log.e("url","format:" + format.format(new Date(machineExpire)));
            String time = format.format(new Date(machineExpire));
            // Log.e("url","time:" + time);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            date = format2.parse(time);
            //  Log.e("url","format.parse:" + date);
            machineExpire = date.getTime();
            // Log.e("url","date:" + date.getTime());
            //if (machineExpire != 0 && machineExpire < System.currentTimeMillis()) {
            long daytimes = 15 * 24 * 60 * 60 * 1000;
            if (machineExpire != 0 && machineExpire - daytimes < System.currentTimeMillis()) {
                MyConstant.gEditor.putBoolean(MyConstant.SP_REGISTER_MACHINE, false);
                MyConstant.gEditor.commit();
                long finalMachineExpire = machineExpire;
                gUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (loginFragment != null) {
                                // 激活界面显示
                                loginFragment.constraintLayout.setVisibility(View.VISIBLE);                  ///crash--------
                                loginFragment.txExpireDate.setText(time);    ///crash--------

                                if (finalMachineExpire < System.currentTimeMillis()) {
                                    loginFragment.btnLogin.setEnabled(false);
                                } else {
                                    loginFragment.btnLogin.setEnabled(true);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("CrashHandler", "loginFragment.." + e.getMessage());
                        }
                    }
                }, 1200);
            }
            if (machineNo != null && machineNo.length() > 0) {
                try {

                    LocalRetrofit.createService().verifyMachineDate(machineNo)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Observer<BaseBean<VerifyMachineResultInfo>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(BaseBean<VerifyMachineResultInfo> verifyMachineResultInfoBaseBean) {

                                    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    long curTime = System.currentTimeMillis();
                                    // 0 正常 1 停止
                                    for (final VerifyMachineResultInfo verifyMachineResultInfo : verifyMachineResultInfoBaseBean.getData()) {
                                        if (verifyMachineResultInfo != null) {
                                            if (verifyMachineResultInfo.getVAILD_TIME() > 0) {
                                                MyConstant.gEditor.putLong(MyConstant.SP_MACHINE_EXPIRE, verifyMachineResultInfo.getVAILD_TIME());
                                                MyConstant.gEditor.commit();
                                            }
                                            if (verifyMachineResultInfo.getUSE_STATUS().equals("1") || curTime > verifyMachineResultInfo.getVAILD_TIME()) {
                                                MyConstant.gEditor.putBoolean(MyConstant.SP_REGISTER_MACHINE, false);
                                                MyConstant.gEditor.commit();
                                            }
                                        }
                                    }
                                }
                            });

                    /*
                    String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                    String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                    String url = "http://" + ip + ":" + port + "/k-occ/machineManager/details/"+machineNo;
                    OkHttpClient client = new OkHttpClient.Builder().build();

                    final Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Log.e("pay","getMemberCardBalance....2.1");
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String body = response.body().string();
                                Log.e("look","verifyMachineResultInfo body:"+body);

                            } catch (Exception e) {

                            }
                        }
                    });
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("verifyMachineDate", "machineNo2:" + machineNo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean mShowing = false;
    private SecondaryDisplayService mService = null;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((SecondaryDisplayService.MsgBinder) service).getService();
            //refresh UI
            if (mService.isPlaying()) {
                Log.d(TAG, "video playing");
                mShowing = true;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mService != null) {
                mService = null;
            }
        }
    };

    public void showPay(String type, String price) {
        mShowing = true;
        if (mService != null) {
            mService.play(SecondaryDisplayService.TYPE_SHOW, type, price);
        }
    }

    public void showPay(Activity activity, String type, String price, String phone, String name, String blance) {
        mShowing = true;
        if (mService != null) {
            mService.play(activity, SecondaryDisplayService.TYPE_SHOW, type, price, phone, name, blance);
        }
    }


    private void initView() {
        img_upload = findViewById(R.id.img_upload);
        img_sync = findViewById(R.id.img_sync);
        version_bar = findViewById(R.id.version_bar);
//        sync_progress = findViewById(R.id.sync_progress);
        fragment_container = findViewById(R.id.fragment_container);
        txAppTitle = findViewById(R.id.app_title);
        txFps = findViewById(R.id.tx_fps);
        txUserId = findViewById(R.id.tx_user_id);
        txVersion = findViewById(R.id.tx_version_info);
        try {
            txVersion.setText(getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        txMachineName = findViewById(R.id.tx_login_user_name);
        ivWifi = findViewById(R.id.iv_wifi);
        ivReadCard = findViewById(R.id.iv_read_card);
        ivPower = findViewById(R.id.iv_power);
        progressDialog = new ProgressDialog(mContext);
        //设置ProgressDialog 是否可以按返回键取消
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("同步中...");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext)
                .setNegativeButton("取消", null);
        exitSysDialog = dialogBuilder.setMessage("是否退出系统？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish();
                    }
                })
                .create();

        backLoginDialog = dialogBuilder.setMessage("是否返回登录界面？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repleaseFragment(new LoginFragment());
                        txAppTitle.setText("登录");
                    }
                })
                .create();

        backHomeDialog = dialogBuilder.setMessage("是否返回主界面？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repleaseFragment(new HomeFragment());
                        txAppTitle.setText("主界面");
                    }
                })
                .create();

//        notifyDialog = dialogBuilder.setMessage("当前机器不可用")
//                .setTitle("提示")
//                .setPositiveButton("确认", null)
//                .create();

        ivPower.setOnClickListener(this);

        // 判断nfc是否开启
        gUiHandler.obtainMessage(7).sendToTarget();
        /*
         每隔一段时间去检测wifi状态和nfc状态-
         check the wifi status every once in a while
          */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable() {
            @Override
            public void run() {
//                gUiHandler.obtainMessage(6).sendToTarget();
                gUiHandler.obtainMessage(7).sendToTarget();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task, 0, 60, TimeUnit.SECONDS);
        String name = MyConstant.gSharedPre.getString(MyConstant.SP_STORE_NAME, "");
        Log.e("frost_name", name);
        if (!"".equals(name)) {
            txUserId.setText(name);
        }
        // 机器号显示
        String machineName = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NAME, "");
        txMachineName.setText(machineName);
        txAppTitle.setText("主界面");
    }

    private void initNFC() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "不支持NFC功能", Toast.LENGTH_SHORT).show();
        } else if (!mNfcAdapter.isEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        Intent subIntent = new Intent();
        subIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent = PendingIntent.getActivity(this, 0, subIntent, 0);
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter filter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            // 接受所有类型
            filter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        mIntentFilter = new IntentFilter[]{filter, filter2};
        mTechList = null;
    }

    private void initData() {
        Log.i(TAG, "initData: ");
        syncNum = 0;
        syncData();
        if (MyUtil.obtainNetworkStatus(mContext)) {
            if (MyUtil.isConfigServer()) {
                if (!txFps.equals("网络断开")) {
                    //syncNum = 0;
                    //syncData();
                }
            } else {
                if (isExistsUserInfo()) {
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("警告")
                            .setMessage("当前服务器地址尚未配置或配置错误，无法更新数据")
                            .create();
                    dialog.show();
                }
            }
        } else {
            Toast.makeText(mContext, "当前无网络连接，无法更新数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

//        loginFragment = new LoginFragment();
//        transaction.add(containerId, loginFragment);
//        transaction.show(loginFragment);

        if (!isExistsUserInfo()) {
            SettingFragment settingFragment = new SettingFragment();
            transaction.add(containerId, settingFragment);
            transaction.show(settingFragment);
        } else {
            HomeFragment homeFragment = new HomeFragment();
            transaction.add(containerId, homeFragment);
            transaction.show(homeFragment);
//            loginFragment = new LoginFragment();
//            transaction.add(containerId, loginFragment);
//            transaction.show(loginFragment);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_power:
//                fragment_container

//                Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
//                Log.d("frost", String.valueOf(fragment));
                if (txAppTitle.getText().equals(getString(R.string.operate)) ||
                        txAppTitle.getText().equals("统计") ||
                        txAppTitle.getText().equals("系统设置") ||
                        txAppTitle.getText().equals("帮助")) {
                    backHomeDialog.show();
                } else if (txAppTitle.getText().equals("主界面")) {
                    exitSysDialog.show();
                }
                break;
//                if (pUserInfo != null) {
//                    switch (pUserInfo.getUTYPE()) {
//                        case "1":
//                            //verifyMachineDate();
//                            if (txAppTitle.getText().equals(getString(R.string.operate))) {
//                                backLoginDialog.show();
//                            }
//                            break;
//                        case "2":
//                        case "3":
//                            if (!txAppTitle.getText().equals("主界面")) {
//                                backHomeDialog.show();
//                            } else {
//                                backLoginDialog.show();
//                            }
//                            break;
//                    }
//                } else {
//                    exitSysDialog.show();
//                }
//                break;
            default:
                break;
        }
    }

    public boolean hasNfc(Context context) {
        boolean bRet = false;
        if (context == null) {
            return bRet;
        }
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled()) {
            // adapter存在，能启用
            bRet = true;
        }
        return bRet;
    }

    /**
     * 同步数据
     */

    boolean isFirstIn = false;

    public void syncData() {
        try {
            Log.i(TAG, "syncData: ");
            syncTable(1111, "", 0, 0);
            taskList.clear();
            getTaskType();
            Log.i(TAG, "syncData: 4");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("syncData", "syncData Exception");
            cancelProgressDiaglog();
        }
    }

    /**
     * copy operation
     */
    public void copy() {
        try {
            InputStream input = null;
            FileOutputStream output;
            try {

                File downloadFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Download");
                if (!downloadFile.exists()) {
                    downloadFile.mkdirs();
                }
                File dbFile = new File(downloadFile.getPath() + "/outputDatabase.db");
                if (dbFile.exists()) {
                    dbFile.delete();
                }
                input = new FileInputStream(new File(MyConstant.DB_PATH + MyConstant.DB_NAME));
                output = new FileOutputStream(dbFile);

                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
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

    public void syncTable(final int type, final String tableName, long l_updatetime, int page_no) {
//        if (!MyUtil.obtainNetworkStatus(mContext) &&!MyUtil.isConfigServer()) {
        Log.i(TAG, "networkstate->syncTable->" + MyUtil.obtainNetworkStatus(mContext));
        if (!MyUtil.obtainNetworkStatus(mContext)) {
            return;
        }
        String updateTime = "";
        String t_record_name = "";

        if (!TextUtils.isEmpty(tableName)) {
            session.clear();
            List<SyncTableRecord> syncTableRecordList = session.queryBuilder(SyncTableRecord.class)
                    .where(SyncTableRecordDao.Properties.TABLENAME.eq(tableName))
                    .build()
                    .list();

            if (syncTableRecordList != null && syncTableRecordList.size() > 0) {
                syncTableRecord = syncTableRecordList.get(0);
            }
        }

        try {
            updateTime = MyUtil.dateConversion(l_updatetime);
        } catch (NumberFormatException e) {

        }

        if (!is_can) {
            return;
        }

        /**
         * 同步表格
         */
        Log.d("look", "table_type:" + type + " t_record_name:" + t_record_name + " l_updatetime" + l_updatetime + " updateTime:" + updateTime + " page_no->" + page_no);
        String clienCode = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
        String storeCode = MyConstant.gSharedPre.getString(MyConstant.STORE_CODE, "");
        String ip = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
        String port = MyConstant.gSharedPre.getString(MyConstant.SP_Server_PORT, "");
        final Map<String, Object> map = new HashMap<String, Object>();
//        List<TfCardInfo> tfMemberInfos = session.queryBuilder(TfCardInfo.class)
//                .orderDesc(TfCardInfoDao.Properties.UPDATETIME)
//                .limit(1)
//                .build().list();
//        long updatetime5 = tfMemberInfos.get(0).getUPDATETIME();
//        Log.e("frost_sysdata_",String.valueOf(updatetime5));

//        List<TfMealTimes> tfMealTimes = session.queryBuilder(TfMealTimes.class)
//                .orderDesc(TfMealTimesDao.Properties.UPDATETIME)
//                .limit(1)
//                .build().list();
//        long updateTime1 = tfMealTimes.get(0).getUPDATETIME();
//        Log.e("frost_look",String.valueOf(updateTime1));

        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("updatetime", updateTime);
        map.put("client_code", clienCode);
        map.put("store_code", storeCode);
        map.put("api", "1");
        map.put("pageNo", "" + page_no);
//        Log.i("syncData", "timestamp->" + map.get("timestamp") + "  updatetime->" + map.get("updatetime")
//                + "   client_code->" + map.get("client_code") + "  store_code->" + map.get("store_code")
//                + "   api->" + map.get("api") + "  pageNo->" + map.get("pageNo"));

        switch (type) {
            case 0:
                LocalRetrofit.createService().syncCommodityRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<?>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;
                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<?> objectSyncResultInfo) {
                                Log.i("look", "table->commodity_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
//                                    if (!verifyDataNum(type)) {
//                                        iterationTableData();
//                                    } else {
//                                        task_page_no = 0;
//                                        task_table_index++;
//                                    }
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            CommodityRecord record = (CommodityRecord) objectSyncResultInfo.getData().get(i);
                                            if (max_time < record.getUPDATETIME()) {
                                                max_time = record.getUPDATETIME();
                                            }
                                            session.insertOrReplace(record);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;
                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<CommodityTypeRecord> objectSyncResultInfo) {
                                Log.i("look", "table->commodity_type_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
//                                    if (!verifyDataNum(type)) {
//                                        iterationTableData();
//                                    } else {
//                                        task_page_no = 0;
//                                        task_table_index++;
//                                    }
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            CommodityTypeRecord commodityTypeRecord = (CommodityTypeRecord) objectSyncResultInfo.getData().get(i);

                                            if (max_time < commodityTypeRecord.getUPDATETIME()) {
                                                max_time = commodityTypeRecord.getUPDATETIME();
                                            }

                                            session.insertOrReplace(commodityTypeRecord);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<MemberTypeRecord> objectSyncResultInfo) {
                                Log.i("look", "table->member_type_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            MemberTypeRecord memberTypeRecord = (MemberTypeRecord) objectSyncResultInfo.getData().get(i);

                                            if (max_time < memberTypeRecord.getUPDATETIME()) {
                                                max_time = memberTypeRecord.getUPDATETIME();
                                            }

                                            session.insertOrReplace(memberTypeRecord);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<StoreConfig> objectSyncResultInfo) {
                                Log.i("look", "table->store_config->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            StoreConfig storeConfig = (StoreConfig) objectSyncResultInfo.getData().get(i);

                                            if (max_time < storeConfig.getUPDATETIME()) {
                                                max_time = storeConfig.getUPDATETIME();
                                            }
                                            if (storeConfig.getSEQNO() == null) {
                                                continue;
                                            }
                                            session.insertOrReplace(storeConfig);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
//                                Log.d(TAG, "syncTable: tablename :  " + tableName +" insert done Time :  " + MyUtil.obtainCurrentSysDate(2));
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfCardInfo> objectSyncResultInfo) {
                                Log.i("look", "table->tf_cardinfo->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
//                                    session.queryBuilder(TfCardInfo.class)
//                                            .where(TfCardInfoDao.Properties.UPDATETIME.eq(0))
//                                            .buildDelete().executeDeleteWithoutDetachingEntities();
//                                    session.clear();
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {

                                            TfCardInfo tfCardInfo = (TfCardInfo) objectSyncResultInfo.getData().get(i);
                                            if (max_time < tfCardInfo.getUPDATETIME()) {
                                                max_time = tfCardInfo.getUPDATETIME();
                                            }
                                            session.insertOrReplace(tfCardInfo);
                                        }
                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfDiscountRecord> objectSyncResultInfo) {
                                Log.i("look", "table->tf_discount_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfDiscountRecord discountRecord = (TfDiscountRecord) objectSyncResultInfo.getData().get(i);

                                            if (max_time < discountRecord.getUPDATETIME()) {
                                                max_time = discountRecord.getUPDATETIME();
                                            }
                                            session.insertOrReplace(discountRecord);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

//                                Log.d(TAG, "onError: " + e.getMessage());
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfMealTimes> objectSyncResultInfo) {
                                Log.i("look", "table->tf_mealtimes->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfMealTimes mealTimes = (TfMealTimes) objectSyncResultInfo.getData().get(i);

                                            if (max_time < mealTimes.getUPDATETIME()) {
                                                max_time = mealTimes.getUPDATETIME();
                                            }
                                            session.insertOrReplace(mealTimes);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("look", "table->tTfMemberAccountRecord onError");

                                isSync = false;
                                syncNum += 1;
                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfMemberAccountRecord> objectSyncResultInfo) {

                                Log.i("look", "table->tf_member_account_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfMemberAccountRecord memberAccountRecord = (TfMemberAccountRecord) objectSyncResultInfo.getData().get(i);

                                            if (max_time < memberAccountRecord.getUPDATETIME()) {
                                                max_time = memberAccountRecord.getUPDATETIME();
                                            }
                                            session.insertOrReplace(memberAccountRecord);
                                        }
                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfMemberInfo> objectSyncResultInfo) {
                                Log.i("look", "table->tf_memberinfo->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfMemberInfo memberInfo = (TfMemberInfo) objectSyncResultInfo.getData().get(i);
                                            if (max_time < memberInfo.getUPDATETIME()) {
                                                max_time = memberInfo.getUPDATETIME();
                                            }
                                            session.insertOrReplace(memberInfo);
                                        }
                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfPrintTask> objectSyncResultInfo) {
                                Log.i("look", "table->tf_print_task->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfPrintTask printTask = (TfPrintTask) objectSyncResultInfo.getData().get(i);

                                            if (max_time < printTask.getUPDATETIME()) {
                                                max_time = printTask.getUPDATETIME();
                                            }
                                            session.insertOrReplace(printTask);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
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
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfUserInfo> objectSyncResultInfo) {
                                Log.i("look", "table->tf_userinfo->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;
                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfUserInfo userInfo = (TfUserInfo) objectSyncResultInfo.getData().get(i);

                                            if (max_time < userInfo.getUPDATETIME()) {
                                                max_time = userInfo.getUPDATETIME();
                                            }
                                            session.insertOrReplace(userInfo);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
                                    }
                                });
                            }
                        });
                break;
            case 11:
                LocalRetrofit.createService().syncTfStoreRecordData(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<TfStoreRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
                                syncNum += 1;
                                if (isInit) {
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                task_table_index++;
                            }

                            @Override
                            public void onNext(final SyncResultInfo<TfStoreRecord> objectSyncResultInfo) {
                                Log.i("look", "table->tf_store_record->count->" + objectSyncResultInfo.getData().size());
                                if (objectSyncResultInfo.getData().isEmpty()) {
                                    verifyDataNum(type);
                                    task_page_no = 0;
                                    task_table_index++;


                                    return;
                                } else {
                                    gUiHandler.sendEmptyMessage(SYNC_VISIBILITY);
                                    task_page_no++;
                                }
                                session.runInTx(new Runnable() {
                                    @Override
                                    public void run() {
                                        long max_time = 0;
                                        for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                            TfStoreRecord storeRecord = (TfStoreRecord) objectSyncResultInfo.getData().get(i);
                                            if (max_time < storeRecord.getUPDATETIME()) {
                                                max_time = storeRecord.getUPDATETIME();
                                            }
                                            session.insertOrReplace(storeRecord);
                                        }

                                        if (syncTableRecord != null) {
                                            syncTableRecord.setUPDATETIME(String.valueOf(max_time));
                                            syncTableRecord.setISM_STATUS("1");
                                            session.update(syncTableRecord);
                                        }
//                                        Log.d("look", "table_type:" + type + " tname:" + syncTableRecord.getTABLENAME() + "  objectSyncResultInfo.getData().size():" + objectSyncResultInfo.getData().size() + " max_time" + MyUtil.dateConversion(max_time));
                                    }
                                });

                            }
                        });
                break;
            case 1111:
                Log.e("sysData", "1111");
                LocalRetrofit.createService().syncTableRecord(map)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SyncResultInfo<SyncTableRecord>>() {
                            @Override
                            public void onCompleted() {
                                isSync = false;
                                Log.i("sysData", "syncData: isInit->" + isInit);
                                if (isInit) {
                                    taskList.clear();
                                    getTaskType();
                                    Log.i("sysData", "syncData: 5");
                                    iterationTableData();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                isSync = false;
                                syncNum += 1;

                                task_page_no = 0;
                                //task_table_index++;
                            }

                            @Override
                            public void onNext(SyncResultInfo<SyncTableRecord> objectSyncResultInfo) {

                                task_page_no = 0;
                                // task_table_index++;


                                for (int i = 0; i < objectSyncResultInfo.getData().size(); i++) {
                                    SyncTableRecord tableRecord = (SyncTableRecord) objectSyncResultInfo.getData().get(i);
                                    Log.e("sysData", "tableRecord.getTABLENAME():" + tableRecord.getTABLENAME());
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
                                    //session.insertOrReplace(tableRecord);
                                    List<SyncTableRecord> recordList = session.queryBuilder(SyncTableRecord.class)
                                            .where(SyncTableRecordDao.Properties.TABLENAME.eq(tableRecord.getTABLENAME())).build().list();
                                    SyncTableRecord record;
                                    if (recordList.isEmpty()) {
                                        record = null;
                                    } else {
                                        record = recordList.get(0);
                                    }
                                    if (record != null) {
                                        // 已存在这条，则更新数据
                                        session.update(tableRecord);
                                        continue;
                                    }
                                    session.insert(tableRecord);
                                }
                                //syncData();
                                Log.i("sysData", "syncData: isInit->");
                            }
                        });
                break;
        }
        gUiHandler.sendEmptyMessage(SYNC_GONE);
    }

    private int task_page_no = 0;
    private int task_table_index = 0;

    public void taskTable() {
        mExecutor_sync.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("test_schedule","come here");
                    if (MyUtil.networkState) {
                        if (!isSync) {
                            Log.e("look", "task_table_index:" + task_table_index + " task_page_no:" + task_page_no);
                            // TODO: 2020/3/4 测试专用，正式版消除
//                            MyUtil.appendFile(System.currentTimeMillis()+":"+"task_table_index:" + task_table_index + " task_page_no:" + task_page_no);
                            iterationTableData();
                        }
                    }
                } catch (Exception e) {
                    isSync = false;
                    MyUtil.appendFile("---------同步线程异常--->"+e.getMessage());
                    mExecutor_sync.shutdown();
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * 迭代循环 所有表的所有页的数据
     */
    private void iterationTableData() {
        Log.i(TAG, "iterationTableData: ");

        if (task_table_index >= taskList.size()) {
            if (isInit) {
                if (isFirstIn) {
                    SharedPreferences preferences = getSharedPreferences("first_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstIn", false);
                    editor.commit();
                }
                Log.e("syncData", "同步数据完成以后");
                //同步数据完成以后
                isInit = false;
                //隐藏 同步中的 弹窗
                gUiHandler.sendEmptyMessage(CANCEL_PROGRESS_DIALOG);
                gUiHandler.sendEmptyMessage(SHOW_BIND_SUCC);
//                //跳转到登录界面
//                gUiHandler.obtainMessage(MyConstant.REPLACE_FRAGMENT_NORMAL, new LoginFragment()).sendToTarget();

                return;
            }

            getTaskType();

            task_table_index = 0;
            verifyMachineDate();

            if (syncNum >= 12) {
                gUiHandler.sendEmptyMessage(CANCEL_PROGRESS_DIALOG);
            }

            try {
                copy();
            } catch (Exception e) {
            }
        }


        TaskBean bean = taskList.get(task_table_index);
        Log.e("TaskBeanbean", "task_table_index:" + task_table_index);
        if (a == 0) {
            a++;
            for (int i = 0; i < taskList.size(); i++) {
                Log.e("TaskBeanbean", "task:" + taskList.get(i).toString());
            }
        }


        if (bean != null) {
            isSync = true;
            SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
            isFirstIn = preferences.getBoolean("isFirstIn", true);
            if (isFirstIn) {
                Long time = null;
                Date date = null;
                try {
                    String time2 = "1970-01-01 08:00:00";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = format.parse(time2);
                    time = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("syncData", "1:" + bean.getTableName() + ": 1970-01-01 08:00:00");
                if ("1111".equals(bean.getType())) {
                    syncTable(bean.getType(), bean.getTableName(), bean.getUpdatetime(), task_page_no);
                } else {
                    syncTable(bean.getType(), bean.getTableName(), time, task_page_no);
                }
            } else {
//                long updateTime = 0;
//                session.clear();
//                if (bean.getType() == 6) {
//                    List<TfMealTimes> tfMealTimes = session.queryBuilder(TfMealTimes.class)
//                            .orderDesc(TfMealTimesDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfMealTimes != null && tfMealTimes.size() != 0) {
//                        updateTime = tfMealTimes.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 3) {
//                    List<StoreConfig> list = session.queryBuilder(StoreConfig.class)
//                            .orderDesc(StoreConfigDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//
//                    if (list != null && list.size() != 0) {
//                        updateTime = list.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 4) {
//                    List<TfCardInfo> tfCardInfos = session.queryBuilder(TfCardInfo.class)
//                            .orderDesc(TfCardInfoDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfCardInfos != null && tfCardInfos.size() != 0) {
//                        updateTime = tfCardInfos.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 5) {
//                    List<TfDiscountRecord> tfDiscountRecords = session.queryBuilder(TfDiscountRecord.class)
//                            .orderDesc(TfDiscountRecordDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfDiscountRecords != null && tfDiscountRecords.size() != 0) {
//                        updateTime = tfDiscountRecords.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 7) {
//                    List<TfMemberAccountRecord> tfMemberAccountRecords = session.queryBuilder(TfMemberAccountRecord.class)
//                            .orderDesc(TfMemberAccountRecordDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfMemberAccountRecords != null && tfMemberAccountRecords.size() != 0) {
//                        updateTime = tfMemberAccountRecords.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 8) {
//                    List<TfMemberInfo> tfMemberInfos = session.queryBuilder(TfMemberInfo.class)
//                            .orderDesc(TfMemberInfoDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfMemberInfos != null && tfMemberInfos.size() != 0) {
//                        updateTime = tfMemberInfos.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 10) {
//                    List<TfUserInfo> tfUserInfos = session.queryBuilder(TfUserInfo.class)
//                            .orderDesc(TfUserInfoDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfUserInfos != null && tfUserInfos.size() != 0) {
//                        updateTime = tfUserInfos.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                } else if (bean.getType() == 11) {
//                    List<TfStoreRecord> tfStoreRecords = session.queryBuilder(TfStoreRecord.class)
//                            .orderDesc(TfStoreRecordDao.Properties.UPDATETIME)
//                            .limit(1)
//                            .build().list();
//                    if (tfStoreRecords != null && tfStoreRecords.size() != 0) {
//                        updateTime = tfStoreRecords.get(0).getUPDATETIME();
//                    }else {
//                        updateTime = 0;
//                    }
//                }
//                if (updateTime != 0) {
//                    syncTable(bean.getType(), bean.getTableName(), updateTime, task_page_no);
//                    Log.e("frost_sysdata", updateTime + "   1");
//                } else {
////                    syncTable(bean.getType(), bean.getTableName(), bean.getUpdatetime(), task_page_no);
//                    syncTable(bean.getType(), bean.getTableName(), updateTime, task_page_no);
//                    Log.e("frost_sysdata", updateTime + "   2");
//                }
                syncTable(bean.getType(), bean.getTableName(), bean.getUpdatetime(), task_page_no);
            }

        }
    }

    int a = 0;

    /**
     * 根据sync_table_record 数据定时更新指定表
     */
    public void regularlyUpdateTable() {
        getTaskType();
        taskTable();
    }

    boolean isSame = false;

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
            case 0://商品表
                primaryKey = "CI_ID";
                tableName = "commodity_record";
                obj = new CommodityRecord();
                break;
            case 1://商品类型
                primaryKey = "SQENO";
                tableName = "commodity_type_record";
                obj = new CommodityTypeRecord();
                break;
            case 2://会员类型
                primaryKey = "SQENO";
                tableName = "member_type_record";
                obj = new MemberTypeRecord();
                break;
            case 3://店铺配置
                primaryKey = "SQENO";
                tableName = "store_config";
                obj = new StoreConfig();
                break;
            case 4://会员卡信息
                primaryKey = "IC_ID";
                tableName = "tf_cardinfo";
                obj = new TfCardInfo();
                break;
            case 5://折扣表
                primaryKey = "SQENO";
                tableName = "tf_discount_record";
                obj = new TfDiscountRecord();
                break;
            case 6://餐次
                primaryKey = "MT_ID";
                tableName = "tf_mealtimes";
                obj = new TfMealTimes();
                break;
            case 7://账户表
                primaryKey = "ACCOUNT_ID";
                tableName = "tf_member_account_record";
                obj = new TfMemberAccountRecord();
                break;
            case 8://会员表
                primaryKey = "MI_ID";
                tableName = "tf_memberinfo";
                obj = new TfMemberInfo();
                break;
            case 9://打印任务
                primaryKey = "SQENO";
                tableName = "tf_print_task";
                obj = new TfPrintTask();
                break;
            case 10://用户表
                primaryKey = "U_ID";
                tableName = "tf_userinfo";
                obj = new TfUserInfo();
                break;
            case 11://商店表
                primaryKey = "STORE_ID";
                tableName = "tf_store_record";
                obj = new TfStoreRecord();
                break;
        }

        if (obj == null || primaryKey.isEmpty() || tableName.isEmpty()) {
            Log.d("look", "return :" + tableName);
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
        Log.e(TAG, "心跳--check" + tableName);
        Object finalObj = obj;
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
                        if (totalNum != null && totalNum.getData() >= 0) {
                            Log.d("look", "onNext: tableName : " + finalTableName + "  totalNum " + totalNum.getData() + " 本地数据量：" + dbList.size());
                            if (dbList.size() != totalNum.getData()) {
                                if (record != null) {
//                                    isSame = false;
                                    // TODO: 2020/3/4 同步数据删除表数据
                                    Log.e("frost", "type:" + type);
                                    record.setUPDATETIME("0");
//                                    record.setISM_STATUS("0");
                                    session.deleteAll(finalObj.getClass());
                                    session.update(record);
//                                    if (type == 4) {
//                                        session.runInTx(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                List<TfCardInfo> list = session.queryBuilder(TfCardInfo.class).build().list();
////                                                for (TfCardInfo lists : list) {
////                                                    lists.setUPDATETIME(0);
////                                                }
//                                                for (int i =0;i<list.size();i++){
//                                                    list.get(i).setUPDATETIME(0);
//                                                }
//                                                Log.e("sysdata","tf_cardinfo:"+list.size());
//                                                session.update(list);
//                                            }
//                                        });
//
//                                    } else if (type == 0) {
//                                        List<CommodityRecord> list = session.queryBuilder(CommodityRecord.class).build().list();
//                                        for (CommodityRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 1) {
//                                        List<CommodityTypeRecord> list = session.queryBuilder(CommodityTypeRecord.class).build().list();
//                                        for (CommodityTypeRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 2) {
//                                        List<MemberTypeRecord> list = session.queryBuilder(MemberTypeRecord.class).build().list();
//                                        for (MemberTypeRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 3) {
//                                        List<StoreConfig> list = session.queryBuilder(StoreConfig.class).build().list();
//                                        for (StoreConfig lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 5) {
//                                        List<TfDiscountRecord> list = session.queryBuilder(TfDiscountRecord.class).build().list();
//                                        for (TfDiscountRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 6) {
//                                        List<TfMealTimes> list = session.queryBuilder(TfMealTimes.class).build().list();
//                                        for (TfMealTimes lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 7) {
//                                        List<TfMemberAccountRecord> list = session.queryBuilder(TfMemberAccountRecord.class).build().list();
//                                        for (TfMemberAccountRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 8) {
//                                        List<TfMemberInfo> list = session.queryBuilder(TfMemberInfo.class).build().list();
//                                        for (TfMemberInfo lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 9) {
//                                        List<TfPrintTask> list = session.queryBuilder(TfPrintTask.class).build().list();
//                                        for (TfPrintTask lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 10) {
//                                        List<TfUserInfo> list = session.queryBuilder(TfUserInfo.class).build().list();
//                                        for (TfUserInfo lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    } else if (type == 11) {
//                                        List<TfStoreRecord> list = session.queryBuilder(TfStoreRecord.class).build().list();
//                                        for (TfStoreRecord lists : list) {
//                                            lists.setUPDATETIME(0);
//                                        }
//                                        session.update(list);
//                                    }
//                                    session.clear();
                                }
                            }
//                            else {
//                                isSame = true;
//                            }
                        }
                    }
                });
//        return isSame;
    }

    /**
     * 从数据库中获取所有需要 同步的表格
     */
    public void getTaskType() {
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
                        case "commodity_record":
//                            type = 0;
                            break;
                        case "commodity_type_record":
//                            type = 1;
                            break;
                        case "tf_mealtimes_chipcategory_relation":
                            break;
                        case "member_type_record":
//                            type = 2;
                            break;
                        case "store_config":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < list.size(); i++) {
//                                        StoreConfig storeConfig = (StoreConfig) list.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
                            type = 3;
                            break;
                        case "tf_cardinfo":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfCardInfos.size(); i++) {
//                                        TfCardInfo storeConfig = (TfCardInfo) tfCardInfos.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });

                            type = 4;
                            break;
                        case "tf_discount_record":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfDiscountRecords.size(); i++) {
//                                        TfDiscountRecord storeConfig = (TfDiscountRecord) tfDiscountRecords.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
                            type = 5;
                            break;
                        case "tf_mealtimes":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfMealTimes.size(); i++) {
//                                        TfMealTimes storeConfig = (TfMealTimes) tfMealTimes.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });

                            type = 6;
                            break;
                        case "tf_member_account_record":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfMemberAccountRecords.size(); i++) {
//                                        TfMemberAccountRecord storeConfig = (TfMemberAccountRecord) tfMemberAccountRecords.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
                            type = 7;
                            break;
                        case "tf_memberinfo":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfMemberInfos.size(); i++) {
//                                        TfMemberInfo storeConfig = (TfMemberInfo) tfMemberInfos.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
                            type = 8;
                            break;
                        case "tf_print_task":
//                            type = 9;
                            break;
                        case "tf_userinfo":

//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfUserInfos.size(); i++) {
//                                        TfUserInfo storeConfig = (TfUserInfo) tfUserInfos.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
//                            type = 10;
                            break;
                        case "tf_store_record":
//                            session.runInTx(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long max_time = 0;
////                                long time = 0;
//                                    for (int i = 0; i < tfStoreRecords.size(); i++) {
//                                        TfStoreRecord storeConfig = (TfStoreRecord) tfStoreRecords.get(i);
//                                        if (max_time < storeConfig.getUPDATETIME()) {
//                                            max_time = storeConfig.getUPDATETIME();
//                                        }
//                                        bean.setUpdatetime(max_time);
//                                    }
//                                }
//                            });
//                            type = 11;
                            break;
                        case "commodity_img_record":
                            break;
                        case "tf_chip_category":
                            break;
                        case "tf_store_discount_record":
                            break;
                        case "tf_chipinfo":
                            break;
                        case "tf_member_subsidy_config":
                            break;
                    }
                    if (type != -1) {
                        TaskBean bean = new TaskBean();
                        bean.setUpdatetime(Long.parseLong(tableRecord.getUPDATETIME()));
                        bean.setTableName(tableRecord.getTABLENAME());
                        bean.setMs(tableRecord.getQUESTTIME());
                        bean.setType(type);
                        taskList.add(bean);
                    }
                }
            }
        }
    }

    public void inlineUpdate() {
        if (OperationFragment.gOperationHandler != null) {
//            OperationFragment.gOperationHandler.obtainMessage(OperationFragment.INLINE_UPDATE_ORDER_INFO).sendToTarget();
        }
    }

    /**
     * 判断是否存在登陆信息这个表
     * 或者该表有没有数据
     *
     * @return
     */
    public boolean isExistsUserInfo() {
        DaoSession session = MyApplication.getInstance();
        List<StoreConfig> list = session.queryBuilder(StoreConfig.class).build().list();
        if (list == null || list.isEmpty()) {
            return false;
        }
//        List<TfUserInfo> userInfoList = session.queryBuilder(TfUserInfo.class).build().list();
//        if (userInfoList == null || userInfoList.isEmpty()) {
//            return false;
//        }
        return true;
    }

    public void repleaseFragmentToStack(BaseFragment fragment) {
        repleaseFragmentInternal(fragment, true);
    }

    public void repleaseFragment(BaseFragment fragment) {
        repleaseFragmentInternal(fragment, false);
    }

    private void repleaseFragmentInternal(BaseFragment fragment, boolean addTostack) {
        Log.d(TAG, "repleaseFragmentInternal: " + fragment.mainFragmentManager);
//        fragmentManager = fragment.mainFragmentManager;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        if (addTostack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void setScanCallback(OnScanResultCallback callback) {
        this.callback = callback;
    }

    public void setBarcodeScannerCallback(OnBarcodeScannerCallback callback) {
        Log.d(TAG, "hansen setBarcodeScannerCallback");
        this.barcodeCallback = callback;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callback.onresult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (callback != null) {
            callback.onNewIntent(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilter, mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mConn);
        }
        isDestory = true;
        mExecutor.shutdown();
        gTHREAD_POOL.shutdown();
        mExecutor_sync.shutdown();
        pPrinter = null;
//        unregisterReceiver(networkReceiver);
        mContext = null;
        System.exit(0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (barcodeCallback != null) {
                barcodeCallback.onKey(event);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public interface OnScanResultCallback {
        void onresult(int requestCode, int resultCode, Intent data);

        void onNewIntent(Intent intent);
    }

    public interface OnBarcodeScannerCallback {
        void onKey(KeyEvent event);
    }

}

