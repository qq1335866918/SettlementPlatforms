package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.OnAdapterItemClickListener;
import com.tt1000.settlementplatform.adapter.SettingTabRecyclerAdapter;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.view.setting.ConfigMachineFragment;
import com.tt1000.settlementplatform.view.setting.DbFragment;
import com.tt1000.settlementplatform.view.setting.MachineFragment;
import com.tt1000.settlementplatform.view.setting.ServerConfigFragment;
import com.tt1000.settlementplatform.view.setting.SystemFragment;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView tabRecyclerView;

    private List<String> tabList;

    private FragmentManager fragmentManager;
    private final int containerId = R.id.setting_fragment_container;

//    public SettingFragment(FragmentManager mainFragmentManager) {
//        super(mainFragmentManager);
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        initFragment();
//        boolean aBoolean = MyConstant.gSharedPre.getBoolean(MyConstant.SP_REGISTER_MACHINE, false);
//        if (isExistsUserInfo() && !aBoolean) {
//            mainActivity.gUiHandler.sendEmptyMessage(mainActivity.SYNC_TABLE_DATA);
//        }
    }

    public boolean isExistsUserInfo() {
        DaoSession session = MyApplication.getInstance();
        List<TfUserInfo> userInfoList = session.queryBuilder(TfUserInfo.class).build().list();
        if (userInfoList == null || userInfoList.isEmpty()) {
            return false;
        }
        return true;
    }

    private void initData() {
        tabList = new ArrayList<>();
        tabList.add("服务器配置");
        tabList.add("系统配置");
        tabList.add("机器配置");
        if (TextUtils.isEmpty(MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, ""))) {
            MyConstant.gEditor.putString(MyConstant.SP_Server_IP, "http://cloud.rfidstar.cn");
            MyConstant.gEditor.putString(MyConstant.SP_Server_PORT, "80");
            MyConstant.gEditor.putString(MyConstant.SP_CRM_ADDRESS, "http://xb.rfidstar.cn:7000/k-crm/machine/bind");
            MyConstant.gEditor.putString(MyConstant.SP_PRODUCT_ID, "29");
            MyConstant.gEditor.commit();
        }
    }

    private void initView() {
        tabRecyclerView = (RecyclerView) findViewById(R.id.setting_recycler_tab);
        SettingTabRecyclerAdapter adapter = new SettingTabRecyclerAdapter(tabList);
        adapter.setListener(new OnAdapterItemClickListener() {
            @Override
            public void onClick(int position, Object data) {
                replaceFragment(position);
            }
        });
        tabRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        tabRecyclerView.setAdapter(adapter);

    }

    private void initFragment() {

        ServerConfigFragment serverConfigFragment = new ServerConfigFragment();

        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, serverConfigFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()) {

        }

    }

    public void replaceFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(containerId, new ServerConfigFragment());
                transaction.commit();
                break;
            case 1:
                transaction.replace(containerId, new SystemFragment());
                transaction.commit();
                break;
            case 2:
//                transaction.replace(containerId, new ConfigMachineFragment());
                transaction.replace(containerId, new MachineFragment());
                transaction.commit();
                break;

            case 3:
                transaction.replace(containerId, new DbFragment());
                transaction.commit();
                break;
            default:
                break;
        }
    }

}
