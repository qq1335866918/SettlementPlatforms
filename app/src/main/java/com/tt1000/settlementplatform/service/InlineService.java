package com.tt1000.settlementplatform.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tt1000.settlementplatform.utils.MyUtil;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InlineService extends Service {
    private Context mContext;
    public ThreadPoolExecutor mTHREAD_POOL = new ThreadPoolExecutor(6,
            6,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());

    public ScheduledExecutorService mExecutor = new ScheduledThreadPoolExecutor(4);

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
