package com.tt1000.settlementplatform.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class SecondExpandableListView extends ExpandableListView {
    public SecondExpandableListView(Context context) {
        super(context);
    }

    public SecondExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecondExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
