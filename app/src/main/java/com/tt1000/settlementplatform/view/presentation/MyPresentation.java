package com.tt1000.settlementplatform.view.presentation;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;

public class MyPresentation extends Presentation {
    private static final String TAG = "PhotoPresentation";

    private Context mContext;

    private TextView presentationPrice;
    private TextView presentationPhone;
    private TextView presentationName;
    private TextView presentationBalance;

    private LinearLayout presentationInfoLy;

    private String mPrice = "";
    private String mType = "";
    private String mPhone = "";
    private String mName = "";
    private String mBlance = "";


    public MyPresentation(Context context, Display display, int theme) {
        super(context, display, theme);
        mContext = context;
    }

    public MyPresentation(Context context, Display display, int theme, String type, String price) {
        super(context, display, theme);
        mContext = context;
        mType = type;
        mPrice = price;
    }

    public MyPresentation(Context context, Display display, int theme, String type, String price, String phone, String name, String blance) {
        super(context, display, theme);
        mContext = context;
        mType = type;
        mPrice = price;
        mPhone = phone;
        mName = name;
        mBlance = blance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_ly);
        presentationPrice = (TextView) findViewById(R.id.presentation_price);
        presentationPhone = (TextView) findViewById(R.id.presentation_phone);
        presentationName = (TextView) findViewById(R.id.presentation_name);
        presentationBalance = (TextView) findViewById(R.id.presentation_balance);
        presentationInfoLy = (LinearLayout) findViewById(R.id.presentation_info_ly);
        presentationPrice.setText(mPrice);
        if(mType.equals("card")){//卡
            presentationPhone.setText(mPhone);
            presentationName.setText(mName);
            presentationBalance.setText(mBlance);
        }else if(mType.equals("cardoffline")){//卡
            presentationInfoLy.setVisibility(View.GONE);
        }else if(mType.equals("wx&zfb")){//微信支付宝
            presentationInfoLy.setVisibility(View.GONE);
        }else if(mType.equals("cash")){//现金
            presentationInfoLy.setVisibility(View.GONE);
        }else if(mType.equals("wait")){//待支付
            presentationInfoLy.setVisibility(View.GONE);
        }
    }

    public void setInfo(String type, String price){
        presentationPrice.setText(price);
        if(type.equals("card")){//卡
            presentationInfoLy.setVisibility(View.VISIBLE);
        }else if(type.equals("cardoffline")){//卡
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("wx&zfb")){//微信支付宝
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("cash")){//现金
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("wait")){//待支付
            presentationInfoLy.setVisibility(View.GONE);
        }
    }


    public void setInfo2(Activity mainActivity,String type, String price, String phone, String name, String blance){
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
        presentationPrice.setText(price);
        if(type.equals("card")){//卡
            presentationPhone.setText(phone);
            presentationName.setText(name);
            presentationBalance.setText(blance);
            presentationInfoLy.setVisibility(View.VISIBLE);
        }else if(type.equals("cardoffline")){//卡
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("wx&zfb")){//微信支付宝
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("cash")){//现金
            presentationInfoLy.setVisibility(View.GONE);
        }else if(type.equals("wait")){//待支付
            presentationInfoLy.setVisibility(View.GONE);
        }
            }
        });
    }



}
