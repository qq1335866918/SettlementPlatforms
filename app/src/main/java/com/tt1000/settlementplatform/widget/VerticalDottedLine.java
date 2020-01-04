package com.tt1000.settlementplatform.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tt1000.settlementplatform.R;

public class VerticalDottedLine extends View {

    private Paint paint;
    private Path path;

    public VerticalDottedLine(Context context) {
        this(context, null);
    }

    public VerticalDottedLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDottedLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setPathEffect(new DashPathEffect(new float[]{2, 5}, 0));
        paint.setColor(Color.parseColor("#959595"));
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.moveTo(0, 0);
        path.lineTo(0, getHeight());
        canvas.drawPath(path, paint);
    }
}
