package com.tt1000.settlementplatform.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.utils.MyConstant;

public class ServerConfigFragment extends BaseFragment {

    public EditText etServerIp;
    public EditText etServerPort;
    public Button btnSaveConfig;

    @Override
    protected int setContentView() {
        return R.layout.fragment_server_config;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        fillData();
    }
    private void fillData(){
        etServerIp.setText("https://zhct.weichai.com");
        etServerPort.setText("443");
    }
    private void initView() {
        etServerIp = (EditText) findViewById(R.id.et_setting_server_ip);
        etServerPort = (EditText) findViewById(R.id.et_setting_server_port);
        btnSaveConfig = (Button) findViewById(R.id.btn_setting_server_save);
        String ip = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
        String port = MyConstant.gSharedPre.getString(MyConstant.SP_Server_PORT, "");
        etServerIp.setText(ip);
        etServerPort.setText(port);

        btnSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etServerIp.getText().toString().trim();
                String port = etServerPort.getText().toString().trim();
                if (ip.isEmpty() || port.isEmpty()) {
                    showMessage("提示", "请输入完整信息");
                    return;
                }
                MyConstant.gEditor.putString(MyConstant.SP_Server_IP, ip);
                MyConstant.gEditor.putString(MyConstant.SP_Server_PORT, port);
                MyConstant.gEditor.commit();
                showMessage("提示", "保存成功");
                registerMachine();
            }
        });

    }
}
