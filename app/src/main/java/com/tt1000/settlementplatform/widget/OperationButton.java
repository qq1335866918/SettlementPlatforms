package com.tt1000.settlementplatform.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;

public class OperationButton extends LinearLayout {

    public ImageView imageView;
    public TextView textView;
    int size;
    String text;
    int src;
    int color;

    public OperationButton(Context context) {
        this(context, null);
    }

    public OperationButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceAsColor")
    public OperationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OperationButton);
        size = (int) a.getDimension(R.styleable.OperationButton_textSize, 14);
        text = a.getString(R.styleable.OperationButton_text);
        src = a.getResourceId(R.styleable.OperationButton_src, 0);
        color = a.getColor(R.styleable.OperationButton_textColor, R.color.text_color);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view_group, null);
        a.recycle();
        addView(view);
        init(view);
    }

    private void init(View view) {
        imageView = view.findViewById(R.id.im_custom_group_icon);
        textView = view.findViewById(R.id.tx_custom_group_content);
        textView.setText(text);
        textView.setTextSize(size);
        textView.setTextColor(color);
        imageView.setImageResource(src);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        // 横向
//        int totalWidth = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//
//            totalWidth +=getChildAt(i).getWidth();
//        }
//        if (widthMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(totalWidth, height);
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        int curWidrh = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            int width = getChildAt(i).getWidth();
//            layout(curWidrh, t, curWidrh + width, b);
//            curWidrh+=width;
//        }
    }

    public void setTextSize(int textSize) {
        textView.setTextSize(textSize);
    }

    public void setTextContent(String content) {
        textView.setText(content);
    }

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }

    public void setTextColor(int Color) {
        textView.setTextColor(color);
    }
}
