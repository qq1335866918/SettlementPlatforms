package com.tt1000.settlementplatform.view.presentation;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.tt1000.settlementplatform.R;

public class SecondaryDisplayService extends Service {

    private static final String TAG = "SecondaryDisplayService";

    public static final int TYPE_SHOW = 1;

    private boolean mPlaying = false;

    private MediaRouter mMediaRouter;

    private MyPresentation mPresentation;

    private final MsgBinder mBinder = new MsgBinder();

    public class MsgBinder extends Binder {
        public SecondaryDisplayService getService() {
            return SecondaryDisplayService.this;
        }
    }


    @Override
    public void onCreate() {
        mMediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Log.d(TAG, "Received start id " + startId + ": " + intent.toString());
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }


    public void play(int type,String st, String price) {
        //stop();
        MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
        if (route != null) {
            Display secondaryDisplay = route.getPresentationDisplay();
            if (secondaryDisplay != null) {
                switch (type) {
                    case TYPE_SHOW:
                        Log.d(TAG, "Star play video!");
                        if (mPresentation == null) {
                            mPresentation = new MyPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay,st,price);
                            mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        }else{
                            mPresentation.setInfo(st,price);
                        }
                        mPresentation.show();
                        mPlaying = true;
                        break;
                }
            } else {
                Log.d(TAG, "Have not secondary display!");
                Toast.makeText(SecondaryDisplayService.this, "Have not secondary display!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void play(Activity activity,int type, String st, String price, String phone, String name, String blance) {
        //stop();
        MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
        if (route != null) {
            Display secondaryDisplay = route.getPresentationDisplay();
            if (secondaryDisplay != null) {
                switch (type) {
                    case TYPE_SHOW:
                        Log.d(TAG, "Star play video!");
                        if (mPresentation == null) {

                            mPresentation = new MyPresentation(getApplicationContext(), secondaryDisplay, R.style.SecondaryDisplay,st,price, phone, name, blance);
                            mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        }else{
                            mPresentation.setInfo2(activity,st,price, phone, name, blance);
                        }
                        mPresentation.show();
                        mPlaying = true;
                        break;
                }
            } else {
                Log.d(TAG, "Have not secondary display!");
                Toast.makeText(SecondaryDisplayService.this, "Have not secondary display!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void stop() {
        if (mPresentation != null) {
            Log.d(TAG, "Stop play video!");
            mPresentation.dismiss();
            mPlaying = false;
            mPresentation = null;
        }
    }

    public boolean isPlaying() {
        return mPlaying;
    }

}
