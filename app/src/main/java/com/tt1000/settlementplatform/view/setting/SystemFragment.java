package com.tt1000.settlementplatform.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.utils.MyConstant;

import static com.tt1000.settlementplatform.utils.MyConstant.gEditor;
import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;

public class SystemFragment extends BaseFragment {

    private EditText etSystemConfig;
    private Button btnSaveConfig;
    private ToggleButton toggleButton;


    @Override
    protected int setContentView() {
        return R.layout.fragment_setting_system_config;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
//        initViewWithFillData();
    }

    private void initView() {
        etSystemConfig = (EditText) findViewById(R.id.et_setting_system_config);
        btnSaveConfig = (Button) findViewById(R.id.btn_setting_system_save);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        boolean fixed_price_status = MyConstant.gSharedPre.getBoolean(MyConstant.FIXED_PRICE_STATUS, false);
        toggleButton.setChecked(fixed_price_status);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyConstant.gEditor.putBoolean(MyConstant.FIXED_PRICE_STATUS, b);
                Log.d("frost", String.valueOf(b));
                MyConstant.gEditor.commit();
            }
        });

        /**
         * 写一个服务器的ip地址，服务器的端口号，crm的地址，固价，是否自动打印
         */
        StringBuilder sb = new StringBuilder();
        String ip = MyConstant.gSharedPre.getString(MyConstant.SP_Server_IP, "");
        sb.append("server_ip=" + ip);
        sb.append("\n");
        sb.append("server_port=" + MyConstant.gSharedPre.getString(MyConstant.SP_Server_PORT, ""));
        sb.append("\n");
        String crm_addr = gSharedPre.getString(MyConstant.SP_CRM_ADDRESS, "");
        if (TextUtils.isEmpty(crm_addr)) {
            sb.append("crm_addr= http://kbs.rfidstar.cn/k-crm/machine/bind");
        } else {
            sb.append("crm_addr=" + crm_addr);
        }
//        sb.append("\n");
//        sb.append("regular_price=" + MyConstant.gSharedPre.getString(MyConstant.SP_REGULAR_PRICE, ""));
        sb.append("\n");
        String product_id = MyConstant.gSharedPre.getString(MyConstant.SP_PRODUCT_ID, "");
        if (TextUtils.isEmpty(product_id)) {
            sb.append("productId=29");
        } else {
            sb.append("productId=" + product_id);
        }
        sb.append("\n");
        // 0 关闭 1 开启  自动打印
        sb.append("autoPrint=" + MyConstant.gSharedPre.getString(MyConstant.SP_AUTO_PRINT, ""));
        sb.append("\n");
        if (TextUtils.isEmpty(product_id)) {
            sb.append("maxSum=");
        } else {
            sb.append("maxSum=" + MyConstant.gSharedPre.getString(MyConstant.SP_MAX_SUM, ""));
        }
        etSystemConfig.setText(sb.toString());

        btnSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAndSaveConfig();
            }
        });
    }

    /**
     * 如方法名一样
     * 读取操作后的配置信息后保存
     */
    private void readAndSaveConfig() {
        String config = etSystemConfig.getText().toString();
        if (!TextUtils.isEmpty(config)) {
            try {
                String array[] = config.split("\n");
                // 固定位置的配置
                String server_ip = array[0];
                String server_port = array[1];
                String crm_addr = array[2];
                String productId = array[3];
                String autoPrint = array[4];
                String maxSum = array[5];

                server_ip = server_ip.substring(server_ip.indexOf("=") + 1, server_ip.length());
                server_port = server_port.substring(server_port.indexOf("=") + 1, server_port.length());
                crm_addr = crm_addr.substring(crm_addr.indexOf("=") + 1, crm_addr.length());
                productId = productId.substring(productId.indexOf("=") + 1, productId.length());
                autoPrint = autoPrint.substring(autoPrint.indexOf("=") + 1, autoPrint.length());
                maxSum = maxSum.substring(maxSum.indexOf("=") + 1, maxSum.length());
                // 替换原来的配置
                gEditor.putString(MyConstant.SP_Server_IP, server_ip);
                gEditor.putString(MyConstant.SP_Server_PORT, server_port);
                gEditor.putString(MyConstant.SP_CRM_ADDRESS, crm_addr);
                gEditor.putString(MyConstant.SP_AUTO_PRINT, autoPrint);
                gEditor.putString(MyConstant.SP_PRODUCT_ID, productId);
                gEditor.putString(MyConstant.SP_MAX_SUM, maxSum);
                gEditor.commit();

                showMessage("提示", "保存成功");
            } catch (Exception e) {
                showMessage("提示", "保存失败，确保数据完整");
                e.printStackTrace();
            }
        } else {
            showMessage("提示", "保存失败，请填写数据");
        }
    }

}
