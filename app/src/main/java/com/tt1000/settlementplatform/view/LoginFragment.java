package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.LoginAcountAdapter;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.SystemLoginBean;
import com.tt1000.settlementplatform.bean.UserData;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.member.TfUserInfoDao;
import com.tt1000.settlementplatform.bean.result_info.RegMachineResultInfo;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;

@SuppressLint("ValidFragment")
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private RecyclerView rcKeyboard;
    public Button btnLogin;
    public Button btnActivate;
    private EditText etUser;
    private EditText etPassword;
    private EditText etMachineCode;
    private TextView txUserId;
    public TextView txExpireDate;
    public ConstraintLayout constraintLayout;

    private AlertDialog mLoginWarningDialog;
    private SharedPreferences sharedPreferences;
    private List<String> keyboradList;
    private StringBuilder loginStringBuilder;

    private Gson gson;
    List<UserData> arrayList;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private PopupWindow popupWindow;
    private LoginAcountAdapter loginAcountAdapter;
    private Button down_but;
    View pop_loginacount;

    public LoginFragment() {
        super();
    }

//    @SuppressLint("ValidFragment")
//    public LoginFragment(FragmentManager mainFragmentManager) {
//        super(mainFragmentManager);
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        init();
        gson = new Gson();
        arrayList = new ArrayList<>();
//        handler.sendEmptyMessage(2);

//        isSaveacountAndPwd();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化
        txUserId.setText("");
        mainActivity.pUserInfo = null;
        mLoginWarningDialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("当前为离线模式，登录有风险，是否继续登录？")
                .setPositiveButton("继续登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        login();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    try {
                        sharedPreferences = mContext.getSharedPreferences("account", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("name", "");
                        if (userId != null) {
                            etUser.setText(userId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        //创建并显示popWindow
                        if (pop_loginacount == null) {
//                            pop_loginacount =getLayoutInflater().inflate(R.layout.pop_loginacount, null);
                            pop_loginacount = LayoutInflater.from(mContext).inflate(R.layout.pop_loginacount, null);
                        }
                        //处理popWindow 显示内容
                        handleListView(pop_loginacount);
                        popupWindow = new PopupWindow(mContext);
                        popupWindow.setContentView(pop_loginacount);
                        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//                        popupWindow = new PopupWindow(pop_loginacount, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        popupWindow.showAsDropDown(etUser, 0, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:

                    try {
                        //  /k-occ/user/plos/login
                        String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
                        String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
                        String url = ip + ":" + port + "/k-occ/user/plos/login";
                        JsonObject jsonData = new JsonObject();
                        jsonData.addProperty("phone", "18512345678");
                        jsonData.addProperty("password", "666666");
                        RequestBody requestBody = new FormBody.Builder()
                                .add("data", jsonData.toString())
                                .build();
                        OkHttpClient client = new OkHttpClient.Builder().build();
                        final Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                        client.newCall(request).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                String body = response.body().string();
                                Log.d("frost",body);
                                SystemLoginBean bean = gson.fromJson(body, SystemLoginBean.class);
                                String[][] data1 = getData(bean);

                                JsonObject jsonObject = (JsonObject) new JsonParser().parse(body);
                                String code = jsonObject.get("code").getAsString();
                                JsonObject data = jsonObject.get("data").getAsJsonObject();
                                String client_code = data.get("CLIENT_CODE").getAsString();
                                String client_name = data.get("CLIENT_NAME").getAsString();
                                JsonObject storeInfo = data.get("storeInfo").getAsJsonObject();
                                List<SystemLoginBean.DataBean.StoreInfoBean> list = new ArrayList();
                                if (storeInfo != null) {
                                    Map<String, SystemLoginBean.DataBean.StoreInfoBean> storeInfoBeanMap = gson.fromJson(storeInfo, new TypeToken<HashMap<String, SystemLoginBean.DataBean.StoreInfoBean>>() {
                                    }.getType());
                                    if (storeInfoBeanMap != null) {
                                        String arr2[][] = new String[storeInfoBeanMap.size()][10];
                                        String arr[] = new String[storeInfoBeanMap.size()];
                                        int count = 0;
                                        for (Map.Entry<String, SystemLoginBean.DataBean.StoreInfoBean> entry : storeInfoBeanMap.entrySet()) {
                                            list.add(entry.getValue());
                                            String key = entry.getKey();
                                            SystemLoginBean.DataBean.StoreInfoBean value = entry.getValue();
                                            String client_code1 = value.getCLIENT_CODE();
                                            String store_addr = value.getSTORE_ADDR();
                                            String store_name = value.getSTORE_NAME();
                                            arr[count] = store_name;
                                            count++;
                                            List<SystemLoginBean.DataBean.StoreInfoBean.MeterInfoBean> meterInfo = value.getMeterInfo();
                                            if (meterInfo != null) {
                                                for (SystemLoginBean.DataBean.StoreInfoBean.MeterInfoBean meterInfoBean : meterInfo) {
                                                    String machine_name = meterInfoBean.getMACHINE_NAME();
                                                    String machine_no = meterInfoBean.getMACHINE_NO();

                                                }
                                            }
                                        }
                                        String[] arr1 = new String[list.size()];
                                        for (int i = 0; i < list.size(); i++) {
                                            String store_name = list.get(i).getSTORE_NAME();
                                            arr1[i] = store_name;
                                        }
//                                        for(int x=0; x<arr2.length; x++) {
//                                            for(int y=0; y<arr2[x].length; y++) {
//                                                Log.d("frost",arr2[x][y]+"");
//                                            }
//                                        }
                                    }
                                }
                            }
                        });

                    } catch (Exception e) {
                        Log.d("frost", e.getMessage());
                    }
                    break;
            }
        }
    };

    private static String[][] getData(SystemLoginBean bean) {
        List<SystemLoginBean.DataBean.StoreInfoBean> list = new ArrayList<>();

        Map<String, SystemLoginBean.DataBean.StoreInfoBean> map = bean.getData().getStoreInfo();
        for (Map.Entry<String, SystemLoginBean.DataBean.StoreInfoBean> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
            list.add(entry.getValue());

        }
        String[][] arr = new String[list.size()][];
        String[] arr1 = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String store_name = list.get(i).getSTORE_NAME();
            arr1[i] = store_name;
            List<SystemLoginBean.DataBean.StoreInfoBean.MeterInfoBean> meterInfo = list.get(i).getMeterInfo();
            arr[i] = new String[meterInfo.size()];
            for (int j = 0; j < meterInfo.size(); j++) {
                arr[i][j] = meterInfo.get(j).getMACHINE_NAME();
            }
        }

        for (int i = 0; i < arr.length; i++) {
            String[] array = arr[i];
            for (int j = 0; j < array.length; j++) {
                System.out.println(array[j] + "\n");
//                Log.d("frost",array[j]+"\n");

            }
        }
        return arr;
    }

    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,1})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    private void initView() {
//        down_but = (Button) findViewById(R.id.down_but);
//        down_but.setOnClickListener(this);
        mContext = rootView.getContext();
        rcKeyboard = rootView.findViewById(R.id.recycler_keyboard);
        btnLogin = rootView.findViewById(R.id.btn_login);
        btnActivate = (Button) findViewById(R.id.btn_login_activate);
        etUser = (EditText) findViewById(R.id.et_login_user);
        etPassword = (EditText) findViewById(R.id.et_login_password);

        //点击edittext隐藏输入法
        etUser.setInputType(InputType.TYPE_NULL);
        etPassword.setInputType(InputType.TYPE_NULL);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        handler.sendEmptyMessage(0);

        etMachineCode = (EditText) findViewById(R.id.et_login_machine_activate_code);
        txUserId = mainActivity.findViewById(R.id.tx_user_id);
        txExpireDate = (TextView) findViewById(R.id.tx_login_machine_expire);
        constraintLayout = (ConstraintLayout) findViewById(R.id.cl_machine_expire);

        btnLogin.setOnClickListener(this);
        btnActivate.setOnClickListener(this);

        etUser.setFocusable(true);
        etUser.requestFocus();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_login_user:
                MyUtil.hideInputMethodManager(mContext, etUser);
                break;
            case R.id.et_login_password:
                MyUtil.hideInputMethodManager(mContext, etPassword);
                break;
            case R.id.btn_login_activate:
                final String machineNo = etMachineCode.getText().toString();
                if (!machineNo.isEmpty()) {
                    registerMachine(machineNo, new Observer<RegMachineResultInfo>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e.getMessage());
                            showMessage("提示", "绑定失败，请检查网络或地址(" + e.getMessage() + ")");
                        }
                        @Override
                        public void onNext(final RegMachineResultInfo regMachineResultInfo) {
                            if (regMachineResultInfo.isResult()) {
                                String clientCode = regMachineResultInfo.getMachineInformation().get(0).getCLIENT_CODE();
                                String storeCode = regMachineResultInfo.getMachineInformation().get(0).getSTORE_CODE();
                                String machineName = regMachineResultInfo.getMachineInformation().get(0).getMACHINE_NAME();
                                String storeName = regMachineResultInfo.getStoreInformation().get(0).getSTORE_NAME();
                                String storeAddr = regMachineResultInfo.getStoreInformation().get(0).getSTORE_ADDR();
                                long machineExpire = regMachineResultInfo.getStoreInformation().get(0).getVAILD_TIME();
                                MyConstant.gEditor.putString(MyConstant.CLIENT_CODE, clientCode);
                                MyConstant.gEditor.putString(MyConstant.STORE_CODE, storeCode);
                                MyConstant.gEditor.putString(MyConstant.SP_MACHINE_NAME, machineName);
                                MyConstant.gEditor.putString(MyConstant.SP_STORE_NAME, storeName);
                                MyConstant.gEditor.putString(MyConstant.SP_STORE_ADDR, storeAddr);
                                MyConstant.gEditor.putBoolean(MyConstant.SP_REGISTER_MACHINE, true);
                                MyConstant.gEditor.putLong(MyConstant.SP_MACHINE_EXPIRE, machineExpire);
                                Log.d(TAG, "onNext: machineExpire ==> " + machineExpire);
                                Log.d(TAG, "onNext: SP_MACHINE_EXPIRE ==> " + MyConstant.gSharedPre.getLong(MyConstant.SP_MACHINE_EXPIRE, 0));
                                MyConstant.gEditor.putString(MyConstant.SP_MACHINE_NO, machineNo);
                                MyConstant.gEditor.commit();
                                RegMachineResultInfo.MachineInformationBean verifyMachineResultInfo = regMachineResultInfo.getMachineInformation().get(0);
                                if (verifyMachineResultInfo.getUSE_STATUS().equals("0") && System.currentTimeMillis() < verifyMachineResultInfo.getVAILD_TIME()) {
                                    mainActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                constraintLayout.setVisibility(View.GONE);
                                                btnLogin.setEnabled(true);
                                                txExpireDate.setText(MyUtil.dateConversion(regMachineResultInfo.getMachineInformation().get(0).getVAILD_TIME()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } else {
                                    showMessage("提示", "激活码无效！");
                                }
                            }
                            showMessage("提示", regMachineResultInfo.getMsg());
                        }
                    });
                } else {
                    showMessage("提示", "请输入激活码！");
                }
                break;
            case R.id.btn_login:
                String loginNo = etUser.getText().toString();
                String password = etPassword.getText().toString();
//                saveacountAndPwd(loginNo, password);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", loginNo);
                editor.commit();

                if (loginNo.isEmpty() || password.isEmpty()) {
                    showMessage("提示", "请输入完整的登陆信息");
                    return;
                }
                if (mainActivity.machineExpire) {
                    showMessage("", "当前机器已到期！请续费后在做尝试");
                    return;
                }
                if (judegeLoginUser(loginNo, password)) {
                    if (!MyUtil.obtainNetworkStatus(getActivity())) {
                        // 离线登陆，风险提示（offline login，risk warning）
                        mLoginWarningDialog.show();
                    } else {
                        login();
                    }
                } else {
                    showMessage("提示", "账户或密码错误");
                }
                break;
            case R.id.down_but:
//                handler.sendEmptyMessage(1);
                break;

        }
    }

    private void handleListView(View pop_loginacount) {
        try {
            ListView listView = (ListView) pop_loginacount.findViewById(R.id.lv_acount);
            loginAcountAdapter = new LoginAcountAdapter(mContext, arrayList);
            listView.setAdapter(loginAcountAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        etUser.setText(arrayList.get(position).getAcount());
                        popupWindow.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveacountAndPwd(String loginNo, String password) {
        if (!arrayList.contains(new UserData(loginNo, password))) {
            try {
                arrayList.add(new UserData(loginNo, password));
                String data = gson.toJson(arrayList);
                //MODE_PRIVATE 在该模式下，写入的内容会覆盖原文件的内容
                fileOutputStream = mContext.openFileOutput("user", Context.MODE_APPEND);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                Log.d("frost", "saveacountAndPwd:" + data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("frost", "FileNotFoundException" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("frost", "IOException" + e.getMessage());
            }
        }
    }

    /**
     * 读取存储的文件内容
     *
     * @return 账号和密码的json数据
     */
    private String readFile() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            //以防止没有创建时读取错误
            fileOutputStream = mContext.openFileOutput("user", Context.MODE_APPEND);
            fileInputStream = mContext.openFileInput("user");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileOutputStream.close();
            fileInputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

    }


    private void initData() {
        loginStringBuilder = new StringBuilder();
        keyboradList = new ArrayList<>();
        keyboradList.add("7");
        keyboradList.add("8");
        keyboradList.add("9");
        keyboradList.add("退格(<-)");
        keyboradList.add("4");
        keyboradList.add("5");
        keyboradList.add("6");
        keyboradList.add("清除(c)");
        keyboradList.add("1");
        keyboradList.add("2");
        keyboradList.add("3");
        keyboradList.add(":");
        keyboradList.add("0");
        keyboradList.add(".");
        keyboradList.add("-");
        keyboradList.add(" ");
    }

    private void init() {
        // 动态设置输入框和按钮宽高
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_login);
        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = (int) (linearLayout.getWidth() / 2);
                int height = width / 4;
//                etUser.setWidth(width);
//                etUser.setHeight(height);
//                etPassword.setWidth(width);
//                etPassword.setHeight(height);
//                etPassword.setWidth(width);
//                etPassword.setHeight(height);
//                btnLogin.setWidth((int) (width / 1.5));
                MyAdapter adapter = new MyAdapter(width);
                adapter.setListener(new LoginClickListener() {
                    @Override
                    public void onClick(int position) {
                        inputFromKeyboard(position);
                    }
                });

                rcKeyboard.setLayoutManager(new GridLayoutManager(mContext, 4));
                rcKeyboard.setAdapter(adapter);
            }
        });
    }

    /**
     * 登录
     */
    public void login() {
        BaseFragment fragment = new HomeFragment();
        String title = "";
        switch (mainActivity.pUserInfo.getUTYPE()) {
            case "1":
                // operator
                title = getString(R.string.operate);
//                fragment = new OperationFragment();
                fragment = new WeichaiOperationFragment();

                break;
            case "2":
            case "3":
                // admin
                title = getString(R.string.home);
                fragment = new HomeFragment();
                break;
            default:
                Toast.makeText(mContext, "身份有误", Toast.LENGTH_SHORT).show();
                return;

        }
        MainActivity.gUiHandler.obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, fragment).sendToTarget();
        //开启定时任务
        appTitle.setText(title);
        txUserId.setText(MyConstant.gSharedPre.getString(MyConstant.SP_STORE_PRESON,""));
        // 清除登陆数据
        etUser.setText("");
        etPassword.setText("");
    }

    public boolean judegeLoginUser(String loginNo, String passwrod) {
        // password need to be encrypt before they are added to the database
        String encryptPwd = MyUtil.encryptMD5(passwrod);
        DaoSession session = MyApplication.getInstance();
        List<TfUserInfo> infoList = session.queryBuilder(TfUserInfo.class)
                .where(TfUserInfoDao.Properties.ULOGINNO.eq(loginNo),
                        TfUserInfoDao.Properties.UPASSWORD.eq(encryptPwd))
                .build()
                .list();
        TfUserInfo info;
        if (infoList.isEmpty()) {
            info = null;
        } else {
            info = infoList.get(0);
        }
        if (info != null) {
            // 记录
            mainActivity.pUserInfo = info;
            List<StoreConfig> storeConfigList = session.queryBuilder(StoreConfig.class)
                    .build()
                    .list();
            if (!storeConfigList.isEmpty() && storeConfigList != null) {
                mainActivity.pStoreConfig = storeConfigList.get(0);
            }
            return true;
        } else {
            return false;
        }
    }


    public void inputFromKeyboard(int position) {
        EditText receiveEdit = null;
        if (etUser.isFocused()) {
            receiveEdit = etUser;
        } else if (etPassword.isFocused()) {
            receiveEdit = etPassword;
        }
        if (receiveEdit == null) {
            return;
        }
        switch (position) {
            case 0:
                addToEdit(receiveEdit, "7");
                break;
            case 1:
                addToEdit(receiveEdit, "8");
                break;
            case 2:
                addToEdit(receiveEdit, "9");
                break;
            case 3:
                // 退格
                int index = receiveEdit.getSelectionStart();
                int lenght = receiveEdit.getText().length();
                if (lenght > 0) {
                    String oldContent;
                    String newContent;
                    if (index == lenght) {
                        oldContent = "";
                        newContent = receiveEdit.getText().toString().substring(0, index - 1);
                    } else {
                        oldContent = receiveEdit.getText().toString().substring(index, lenght);
                        newContent = receiveEdit.getText().toString().substring(0, index == 0 ? 0 : index - 1);
                    }
                    receiveEdit.setText(newContent + oldContent);
                    receiveEdit.setSelection(index == 0 ? 0 : index - 1);
                }
                break;
            case 4:
                addToEdit(receiveEdit, "4");
                break;
            case 5:
                addToEdit(receiveEdit, "5");
                break;
            case 6:
                addToEdit(receiveEdit, "6");
                break;
            case 7:
                // C 清除
                receiveEdit.setText("");
                break;
            case 8:
                addToEdit(receiveEdit, "1");
                break;
            case 9:
                addToEdit(receiveEdit, "2");
                break;
            case 10:
                addToEdit(receiveEdit, "3");
                break;
            case 11:
                addToEdit(receiveEdit, ":");
                break;
            case 12:
                addToEdit(receiveEdit, "0");
                break;
            case 13:
                addToEdit(receiveEdit, ".");
                break;
            case 14:
                addToEdit(receiveEdit, "-");
                break;
            case 15:
                addToEdit(receiveEdit, " ");
                break;
            default:
                break;
        }
    }

    public void addToEdit(EditText editText, String newContent) {
        try {
            String upStr = "", footStr = "";
            int index = editText.getSelectionStart();
            String content = editText.getText().toString();
            upStr = content.substring(0, index);
            footStr = content.substring(index);
            editText.setText(upStr + newContent + footStr);
            editText.setSelection(index + newContent.length());
        } catch (Exception e) {

        }
    }

    public void isSaveacountAndPwd() {
        String data = readFile();
        Log.d("frost", "data" + data);
        try {
            if (!TextUtils.isEmpty(data) && !data.equals("[]")) {
                arrayList = gson.fromJson(data, new TypeToken<List<UserData>>() {
                }.getType());
                etUser.setText(arrayList.get(0).getAcount());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("frost", "isSaveacountAndPwd" + e.getMessage());
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public LoginClickListener loginClickListener;
        int width;

        public MyAdapter(int width) {
            this.width = width;
        }

        public void setListener(LoginClickListener listener) {
            loginClickListener = listener;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_login_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            holder.button.setWidth(width / 2);
            holder.button.setHeight(width / 3);
            holder.button.setText(keyboradList.get(position));
            if (position == 3 || position == 7) {
                holder.button.setTextSize(12);
            } else {
                holder.button.setTextSize(20);
            }
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 隐藏输入法
                    if (etPassword.isFocused()) {
                        MyUtil.hideInputMethodManager(mContext, etPassword);
                    } else if (etUser.isFocused()) {
                        MyUtil.hideInputMethodManager(mContext, etUser);
                    }

                    if (loginClickListener != null) {
                        loginClickListener.onClick(position);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return keyboradList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Button button;

            public ViewHolder(View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.btn_statistics_item);
            }
        }
    }

    public interface LoginClickListener {
        void onClick(int position);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rootView != null) {
            rootView = null;
        }
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
