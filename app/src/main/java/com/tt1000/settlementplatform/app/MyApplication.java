package com.tt1000.settlementplatform.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tt1000.settlementplatform.CrashHandler;
import com.tt1000.settlementplatform.bean.member.DaoMaster;
import com.tt1000.settlementplatform.bean.member.DaoSession;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

public class MyApplication extends Application {

    public static Context mContext;

    private static DaoSession sSession;


    @Override
    public void onCreate() {

        mContext = this;
//        MultiDex.install(this);       5de1cbd0
        setupDao();
//        CrashHandler.getInstance().init(getApplicationContext());

////        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=5de1cbd0");
//        StringBuffer param = new StringBuffer();
////        param.append("appid="+"5de1cbd0");
//        param.append("appid="+"5df4b1d4");//正式版使用
//        param.append(",");
//        // 设置使用v5+
//        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
//        SpeechUtility.createUtility(mContext, param.toString());
        super.onCreate();
    }

    private void setupDao() {
        SQLiteDatabase database = MyUtil.readDatabase(MyConstant.DB_PATH, MyConstant.DB_NAME);
        DaoMaster master = new DaoMaster(database);
        sSession = master.newSession();
    }

    public static DaoSession getInstance() {
        return sSession;
    }

    public static Context getContext() {
        return mContext;
    }
}
