package com.tt1000.settlementplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tt1000.settlementplatform.app.MyApplication;

import java.text.DecimalFormat;

public class MyConstant {

    public static SharedPreferences gSharedPre = MyApplication.getContext().getSharedPreferences("settlement", Context.MODE_PRIVATE);
    public static SharedPreferences.Editor gEditor = gSharedPre.edit();

    public static DecimalFormat gFormat = new DecimalFormat("##0.0");

    public static final int REPLACE_FRAGMENT_TO_STACK = 9;
    public static final int REPLACE_FRAGMENT_NORMAL = 2111;
    public static final int CHANGE_APP_TITLE = 1;
    //    public static final int SYNC_TABLE_DATA = 1001;
    public static final int SYNC_MACHINE_INFO = 2111;
    public static final int SHOW_PROGRESS_DIALOG = 2211;
    // 定价商品
    public static final int CUSTOM_PRICING_GOODS = 10101;
    // 固价
    public static final int CUSTOM_FIXED_PRICE_GOODS = 10202;

    public static final String BIND_SUCCEED = "bind_succeed";

    public static final String FIXED_PRICE_STATUS = "fixed_price_status";

    public static final String SP_Server_IP = "server_ip";
    public static final String SP_Server_PORT = "server_port";
    public static final String SP_MACHINE_NO = "machine_no";
    public static final String SP_MACHINE_NAME = "machine_name";
    public static final String SP_CLIENT_NAME = "client_name";
    public static final String SP_STORE_NAME = "store_name";
    public static final String SP_STORE_ADDR = "store_addr";
    public static final String SP_REGULAR_PRICE = "regular_price";
    public static final String SP_AUTO_PRINT = "auto_print";
    public static final String SP_MAX_SUM = "max_sum";
    public static final String SP_PRODUCT_ID = "productId";
    public static final String SP_CRM_ADDRESS = "crm_addr";
    public static final String SP_REGISTER_MACHINE = "register_machine";
    public static final String SP_MACHINE_EXPIRE = "machine_expire";
    public static final String SP_STORE_PRESON = "store_duty_person";
    public static final String U_ID = "u_id";

    // 在创建订单时使用
    public static final String CLIENT_CODE = "client_code";
    public static final String STORE_CODE = "store_code";

    public static final int SP_MEDIA_CONSUME_SUCCESS = 101;
    public static final int SP_MEDIA_MONEY_NOT_ENOUGH = 102;
    public static final int SP_MEDIA_PLEASE_SET_CARD = 103;
    public static final int SP_MEDIA_NO_EFFECT_CARD = 104;
    public static final int SP_MEDIA_ILLEGAL_CARD = 105;
    public static final int SP_CANCEL_CARD = 106;
    public static final int SP_LOST_CARD = 107;

    public static final int OPERATION_UPDATE_PERSON_INFO = 0X101;

    public static final String TAG = "LOG_TAG";

    public static final String BASE_URL = "";

    public static final String SYNC_TABLE_RECORD = "sync_table_record";

    public static final String DB_PATH = "data/data/"
            + MyApplication.getContext().getPackageName()
            + "/databases/";
    public static final String DB_NAME = "local_db.db";
    // 心跳间隔时间
    public static final int HEARTBEAT_INTERVAL_TIME_ = 30;
    //正常卡
    public static final String CARD_STATUS_NORMAL = "0000";
    //挂失卡
    public static final String CARD_STATUS_LOST = "0004";
    //注销卡
    public static final String CARD_STATUS_CANCELED = "0003";
    //绑定机器成功
//    public static final String BIND_MACHINE_SUCC = "00000";
    //外接扫码盒子进行扫码支付，扫出的付款码数字 停留时间(毫秒)
    public static final int SANNER_CLEAR_TIME_ = 600;
}
