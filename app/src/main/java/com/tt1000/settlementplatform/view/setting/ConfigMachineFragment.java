package com.tt1000.settlementplatform.view.setting;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.SystemLoginBean;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.utils.SSLSocketClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.layout.simple_list_item_1;
import static com.tt1000.settlementplatform.utils.MyConstant.gEditor;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;

public class ConfigMachineFragment extends BaseFragment {
    private Spinner client_name;
    private Spinner machine_name;
    private Spinner store_name;
    private ArrayAdapter<String> storeAdapter;
    private ArrayAdapter<String> machineAdapter;
    private ArrayAdapter<String> clientAdapter;
    private String selectItem;
    private Button machine_bind;
    private SharedPreferences sharedPreferences;
//    private Button machine_cancel;

    SystemLoginBean bean;

    @Override
    protected int setContentView() {
        return R.layout.fragment_config_machine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        client_name = (Spinner) findViewById(R.id.client_name);
        machine_name = (Spinner) findViewById(R.id.machine_name);
        store_name = (Spinner) findViewById(R.id.store_name);

        machine_bind = (Button) findViewById(R.id.machine_bind);

//        machine_cancel = (Button) findViewById(R.id.machine_cancel);
//        machine_cancel.setOnClickListener(mainActivity);
        List<StoreConfig> list = pDaoSession.queryBuilder(StoreConfig.class).build().list();

        if (!list.isEmpty() && list.size() > 0) {
//            if (MyConstant.gSharedPre.getBoolean(MyConstant.BIND_SUCCEED,false)) {
            machine_bind.setEnabled(false);
            List list1 = new ArrayList();
            List list2 = new ArrayList();
            List list3 = new ArrayList();
            String clien_name1 = gSharedPre.getString(MyConstant.SP_CLIENT_NAME, "");
            String store_name1 = gSharedPre.getString(MyConstant.SP_STORE_NAME, "");
            String machine_name1 = gSharedPre.getString(MyConstant.SP_MACHINE_NAME, "");
            list1.add(clien_name1);
            list2.add(store_name1);
            list3.add(machine_name1);
            clientAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1, list1);
            client_name.setAdapter(clientAdapter);
            storeAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1, list2);
            store_name.setAdapter(storeAdapter);
            machineAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1, list3);
            machine_name.setAdapter(machineAdapter);
            client_name.setEnabled(false);
            store_name.setEnabled(false);
            machine_name.setEnabled(false);
        } else {
            initData();
        }

        machine_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItem = machine_name.getSelectedItem().toString();
                if (bean != null) {
                    List<SystemLoginBean.DataBean.StoreInfoBean> list = new ArrayList<>();
                    Map<String, SystemLoginBean.DataBean.StoreInfoBean> map = bean.getData().getStoreInfo();
                    String client_name = bean.getData().getCLIENT_NAME();
                    for (Map.Entry<String, SystemLoginBean.DataBean.StoreInfoBean> entry : map.entrySet()) {
                        list.add(entry.getValue());
                    }
                    for (int i = 0; i < list.size(); i++) {
                        String store_duty_person = list.get(i).getSTORE_DUTY_PERSON();
                        String store_name = list.get(i).getSTORE_NAME();
                        List<SystemLoginBean.DataBean.StoreInfoBean.MeterInfoBean> meterInfo = list.get(i).getMeterInfo();
                        for (int j = 0; j < meterInfo.size(); j++) {
                            String machine_name = meterInfo.get(j).getMACHINE_NAME();
                            if (machine_name.equals(selectItem)) {
                                String machine_no = meterInfo.get(j).getMACHINE_NO();
                                String client_code = meterInfo.get(j).getCLIENT_CODE();
                                String store_code = meterInfo.get(j).getSTORE_CODE();
                                String syn_status = meterInfo.get(j).getSYN_STATUS();
//                                if (!syn_status.equals("0")) {
//                                    AlertDialog a = new AlertDialog.Builder(mContext)
//                                            .setTitle("提示")
//                                            .setMessage("该机器号已被使用，请重新选择").create();
//                                    a.show();
//                                    return;
//                                }
                                String u_id = meterInfo.get(j).getU_ID();
                                Log.d("frost", "client_code：" + client_code);
                                Log.d("frost", "store_code：" + store_code);
                                Log.d("frost", "store_duty_person：" + store_duty_person);
                                Log.d("frost", "machine_name：" + machine_name);
                                gEditor.putString(MyConstant.SP_CLIENT_NAME, client_name);
                                gEditor.putString(MyConstant.SP_STORE_NAME, store_name);
                                gEditor.putString(MyConstant.SP_MACHINE_NO, machine_no);
                                gEditor.putString(MyConstant.SP_MACHINE_NAME, machine_name);
                                gEditor.putString(MyConstant.CLIENT_CODE, client_code);
                                gEditor.putString(MyConstant.STORE_CODE, store_code);
                                gEditor.putString(MyConstant.SP_STORE_PRESON, store_duty_person);
                                gEditor.putString(MyConstant.U_ID, u_id);
                                gEditor.commit();
                            }
                        }
                    }
                }
                registerMachine();
            }
        });
    }

    private void initData() {
        String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
        String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
        MyUtil.appendFile("ip--"+ip);
        MyUtil.appendFile("port--"+port);
        if (!"".equals(ip) && !"".equals(port)) {
            String url = ip + ":" + port + "/k-occ/user/android/login";
            Log.e("frost_bind", "-----" + url);
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    MyUtil.appendFile("Exception--"+e.getMessage());
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("frost_bind", e.getMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    String body = response.body().string();
                    MyUtil.appendFile("response---"+body);
                    Log.d("frost_bind", body);
                    try {
                        ArrayList<String> list1 = new ArrayList<>();
                        bean = gson.fromJson(body, SystemLoginBean.class);
                        String client_name1 = bean.getData().getCLIENT_NAME();
                        list1.add(client_name1);
                        List<SystemLoginBean.DataBean.StoreInfoBean> list = new ArrayList<>();
                        Map<String, SystemLoginBean.DataBean.StoreInfoBean> map = bean.getData().getStoreInfo();
                        for (Map.Entry<String, SystemLoginBean.DataBean.StoreInfoBean> entry : map.entrySet()) {
                            list.add(entry.getValue());
                        }
                        final String[][] arr = new String[list.size()][];
                        String[] arr1 = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            String store_name = list.get(i).getSTORE_NAME();
                            String store_code = list.get(i).getSTORE_CODE();
//                                Log.d("frost", store_name);
                            arr1[i] = store_name;
                            List<SystemLoginBean.DataBean.StoreInfoBean.MeterInfoBean> meterInfo = list.get(i).getMeterInfo();
                            arr[i] = new String[meterInfo.size()];
                            for (int j = 0; j < meterInfo.size(); j++) {
                                arr[i][j] = meterInfo.get(j).getMACHINE_NAME();
                                String syn_status = meterInfo.get(j).getSYN_STATUS();
                                Log.d("frost", "syn_status：" + syn_status);
                            }
                        }

                        for (int i = 0; i < arr.length; i++) {
                            String[] array = arr[i];
                            for (int j = 0; j < array.length; j++) {
                                System.out.println(array[j] + "\n");
                                Log.d("frost", array[j] + "\n");
                            }
                        }
                        Message message = new Message();
                        message.what = 1;
                        message.obj = arr;
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("arr1", arr1);
                        bundle.putStringArrayList("list1", list1);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Log.e("frost", "-------" + e.getMessage());
                        MyUtil.appendFile("catch---"+e.getMessage());
                    }
                }

            });
        } else {
            Toast.makeText(mContext, "请先保存IP和端口", Toast.LENGTH_LONG);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String[][] obj = (String[][]) msg.obj;
                    Bundle data = msg.getData();
                    try {
                        clientAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1, data.getStringArrayList("list1"));
                        client_name.setAdapter(clientAdapter);
                        storeAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1, data.getStringArray("arr1"));
                        store_name.setAdapter(storeAdapter);
                        machineAdapter = new ArrayAdapter<String>(mContext, simple_list_item_1);
                        machine_name.setAdapter(machineAdapter);
                        store_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String[] store = obj[position];
                                machineAdapter.clear();
                                machineAdapter.addAll(store);
                                machine_name.setSelection(0);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                machineAdapter.clear();
                                machine_name.setSelection(0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("frost", e.getMessage());
                    }
                    break;
            }
        }
    };

}
