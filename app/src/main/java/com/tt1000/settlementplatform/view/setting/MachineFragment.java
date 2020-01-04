package com.tt1000.settlementplatform.view.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

public class MachineFragment extends BaseFragment {

    public EditText etMachineNo;
    public Button btnMachineBind;

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting_machine_config;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
//        fillData();
    }
    private void fillData(){
        etMachineNo.setText("156108432202015");
    }
    private void initView() {
        etMachineNo = (EditText) findViewById(R.id.et_setting_machine_num);
        btnMachineBind = (Button) findViewById(R.id.btn_setting_machine_bind);
        String machineNo = MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NO, "");
        etMachineNo.setText(machineNo);

        btnMachineBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtil.hideInputMethodManager(mContext, etMachineNo);
                String machineNo = etMachineNo.getText().toString();
                if (machineNo.isEmpty()) {
                    showMessage("提示", "请输入机器号");
                    return;
                }
                MyConstant.gEditor.putString(MyConstant.SP_MACHINE_NO, machineNo);
                MyConstant.gEditor.commit();
                //showMessage("提示", "保存成功");
                registerMachine();
            }
        });
    }
}
