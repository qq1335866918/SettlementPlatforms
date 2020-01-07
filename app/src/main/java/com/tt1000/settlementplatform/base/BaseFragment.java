package com.tt1000.settlementplatform.base;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.result_info.RegMachineResultInfo;
import com.tt1000.settlementplatform.feature.network.ApiService;
import com.tt1000.settlementplatform.feature.network.LocalRetrofit;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;
import com.tt1000.settlementplatform.view.AssistsFragment;
import com.tt1000.settlementplatform.view.HomeFragment;
import com.tt1000.settlementplatform.view.LoginFragment;
import com.tt1000.settlementplatform.view.OperationFragment;
import com.tt1000.settlementplatform.view.SettingFragment;
import com.tt1000.settlementplatform.view.StatisticsFragment;
import com.tt1000.settlementplatform.view.WeichaiOperationFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;
import static com.tt1000.settlementplatform.utils.MyConstant.gEditor;

public abstract class BaseFragment extends Fragment {

    public View rootView;
    public Context mContext;
    public FragmentManager fragmentManager;
    public FragmentManager mainFragmentManager;
    public MainActivity mainActivity;
    public TextView appTitle;
    public DaoSession pDaoSession;
    public AlertDialog dialog;
    public boolean isPause = false;

    /**
     * 修改时间值以匹配数据库参数
     * 2018-9-1 ---> 2018-09-01
     *
     * @param date
     * @parameter type 类型，开始/结束时间结尾不一致
     * 0：开始时间结尾  1：结束时间结尾
     * @return
     */
    public static final int START_TIME = 0;
    public static final int STOP_TIME = 1;
    // 不要添加结尾字符
    public static final int WITHOUT_TIME = -1;

//    public BaseFragment(FragmentManager mainFragmentManager) {
//        this.mainFragmentManager = mainFragmentManager;
//    }

    public BaseFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        if (mainFragmentManager == null) {
            mainFragmentManager = mainActivity.getSupportFragmentManager();
        }
        appTitle = mainActivity.findViewById(R.id.app_title);
        pDaoSession = MyApplication.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentView(), container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        mContext = view.getContext();
        fragmentManager = getChildFragmentManager();
        dialog = new AlertDialog.Builder(mContext)
                .setPositiveButton("确认", null)
                .create();
    }


    public View findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    /**
     * 时钟选择器
     *
     * @param textView
     */
    public void showDatePickDlg(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                textView.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showMessage(final String title, final String msg) {
        if (mainActivity != null) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setTitle(title);
                    dialog.setMessage(msg);
                    dialog.show();
                }
            });
        }
    }

    /**
     * 注册机器号
     */
    public void registerMachine() {
//        String ip = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
//        String port = MyConstant.gSharedPre.getString(MyConstant.SP_Server_PORT, "");
        String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
        Log.d("frost","machineNo"+machineNo);
        // 注册机器号的返回，处理数据
        Observer<RegMachineResultInfo> observer = new Subscriber<RegMachineResultInfo>() {
            @Override
            public void onCompleted() {
                Log.d("frost", "onCompleted: ");
            }
            @Override
            public void onError(Throwable e) {
                if (e != null && !TextUtils.isEmpty(e.getMessage())) {
//                    gEditor.putBoolean(MyConstant.BIND_SUCCEED,false);
                    showMessage("提示", "绑定失败，请检查网络或地址(" + e.getMessage() + ")");
                    Log.d("frost", "onError: " + e.getMessage());
                }
            }
            @Override
            public void onNext(RegMachineResultInfo regMachineResultInfo) {
//                gEditor.putBoolean(MyConstant.BIND_SUCCEED,true);
                Log.d("frost", "onNext: ");
                if (regMachineResultInfo.isResult()) {
                    RegMachineResultInfo.MachineInformationBean verifyMachineResultInfo = regMachineResultInfo.getMachineInformation().get(0);
                    String clientCode = verifyMachineResultInfo.getCLIENT_CODE();
                    String storeCode = verifyMachineResultInfo.getSTORE_CODE();
                    String machineName = verifyMachineResultInfo.getMACHINE_NAME();
                    long machineExpire = verifyMachineResultInfo.getVAILD_TIME();
                    String storeName = regMachineResultInfo.getStoreInformation().get(0).getSTORE_NAME();
                    String storeAddr = regMachineResultInfo.getStoreInformation().get(0).getSTORE_ADDR();
                    MyConstant.gEditor.putString(MyConstant.CLIENT_CODE, clientCode);
                    MyConstant.gEditor.putString(MyConstant.STORE_CODE, storeCode);
                    MyConstant.gEditor.putString(MyConstant.SP_MACHINE_NAME, machineName);
                    MyConstant.gEditor.putString(MyConstant.SP_STORE_NAME, storeName);
                    MyConstant.gEditor.putString(MyConstant.SP_STORE_ADDR, storeAddr);
                    MyConstant.gEditor.putBoolean(MyConstant.SP_REGISTER_MACHINE, true);
                    MyConstant.gEditor.putLong(MyConstant.SP_MACHINE_EXPIRE, machineExpire);
                    MyConstant.gEditor.commit();
                    mainActivity.gUiHandler.obtainMessage(MainActivity.BIND_MACHINE_SUCD, regMachineResultInfo.getMsg()).sendToTarget();
//                    mainActivity.gUiHandler.obtainMessage(MyConstant.SYNC_TABLE_DATA).sendToTarget();
//                    mainActivity.gUiHandler.obtainMessage(MyConstant.REPLACE_FRAGMENT_NORMAL, new LoginFragment()).sendToTarget();
                } else {
                    showMessage("提示", regMachineResultInfo.getMsg());
                }
            }
        };
        registerMachine(machineNo, observer);
    }

    public void registerMachine(String machineNo, Observer<RegMachineResultInfo> observer) {
        long expireTime = MyConstant.gSharedPre.getLong(MyConstant.SP_MACHINE_EXPIRE, 0);
        String client_code = MyConstant.gSharedPre.getString(MyConstant.CLIENT_CODE, "");
        String machine_name = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NAME, "");
        Log.d("frost", "registerMachine: ");
        Log.d(TAG, "onNext: registerMachine   " + MyUtil.dateConversion(expireTime) + "     curmillis " + MyUtil.dateConversion(System.currentTimeMillis()));
//        if (expireTime != 0 && expireTime > System.currentTimeMillis()) {
//            return;
//        }
        if (!TextUtils.isEmpty(machineNo.trim())) {
            if (!MyUtil.isConfigServer()) {
                showMessage("提示", "连接服务器失败，请检查配置信息");
                return;
            }
            String crm_addr = MyConstant.gSharedPre.getString(MyConstant.SP_CRM_ADDRESS, "");
            //判断结尾是否有“/“,且不为空时才回去添加“/”
            if (!crm_addr.endsWith("/") && !crm_addr.isEmpty()) {
                crm_addr += "/";
            }
//            String baseUrl = "http://" + ip + ":7000/";
            // 如果有手动配置网址，则用这个手动配置的
            if (!crm_addr.isEmpty() && crm_addr.length() > 6) {
                if (MyUtil.isConfigServer()) {
                    Retrofit retrofit = LocalRetrofit.getInstance(crm_addr);
                    if (retrofit != null) {
                        retrofit.create(ApiService.class)
                                .registerMachine(crm_addr, machineNo, MyUtil.getMacAddress())
                                .subscribeOn(Schedulers.io())
                                .subscribe(observer);
                    } else {
                        Log.d(TAG, "registerMachine retrofit is null");
                    }
                }
            } else {
                showMessage("提示", "请配置CRM_ADDR地址！");
            }
        }
    }

    public String modifyDate(String date, int type) {
        if (date.isEmpty() || date == null) {
            return "";
        }
        String[] origin = date.split("-");
        String foot = "";
        switch (type) {
            case START_TIME:
                foot = " 00:00:00";
                break;
            case STOP_TIME:
                foot = " 23:59:59";
                break;
            case WITHOUT_TIME:
                foot = "";
                break;
            default:
                break;
        }
        if (origin[1].length() < 2) {
            origin[1] = "0" + origin[1];
        }
        if (origin[2].length() < 2) {
            origin[2] = "0" + origin[2];
        }
        String full = origin[0] + "-" + origin[1] + "-" + origin[2] + foot;
        return full;
    }

    /**
     * 获得操作员表的相关信息
     */
    public List<TfUserInfo> getShopAssistant() {
        List<TfUserInfo> userInfos = new ArrayList<>();
        if (pDaoSession != null) {
            userInfos = pDaoSession.queryBuilder(TfUserInfo.class).build().list();
            return userInfos;
        }
        return userInfos;
    }

    /**
     * 餐次相关信息
     *
     * @return
     */
    public List<TfMealTimes> getMealTimes() {
        List<TfMealTimes> mealTimes = new ArrayList<>();
        if (pDaoSession != null) {
            mealTimes = pDaoSession.queryBuilder(TfMealTimes.class).build().list();
            return mealTimes;
        }
        return mealTimes;
    }

    protected abstract int setContentView();

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void onStop() {
        if (this instanceof LoginFragment
                || this instanceof HomeFragment) {
            if (rootView != null) {
                rootView = null;
            }
            mContext = null;
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (rootView != null) {
            rootView = null;
        }
        mContext = null;
        if (this instanceof HomeFragment) {
            appTitle.setText("登陆");
        } else if (this instanceof LoginFragment) {

//        } else if (this instanceof OperationFragment
        } else if (this instanceof WeichaiOperationFragment
                || this instanceof StatisticsFragment
                || this instanceof SettingFragment
                || this instanceof AssistsFragment) {
            appTitle.setText("主界面");
        }

        super.onDestroy();
    }
}
