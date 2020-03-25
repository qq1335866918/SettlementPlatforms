package com.tt1000.settlementplatform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * abc
 * Created by 搬砖小能手 on 6/5/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class XCFlowLayout extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {
    /**
     * 存储所有子View,每行每行的储存
     */
    private ArrayList<ArrayList<View>> mAllChildViews = new ArrayList<>();
    /**
     * 存储所有选择的子View,
     */
    private ArrayList<View> BackViews = new ArrayList<>();
    /**
     * 存储所有选择的item,
     */
    private ArrayList<String> Backitem = new ArrayList<>();
    /**
     * 记录每个子view的选中情况
     */
    private Map<String, Boolean> MapBack = new HashMap<>();
    /**
     * 孩子的数量，复制
     */
    public ArrayList<String> listChild;
    /**
     * 每一个孩子的左右的间距  默认值 12,单位是px
     */
    int mHSpace = 12;
    /**
     * 每一行的上下的间距
     */
    int mVSpace = 16;
    /**
     * 每一行的高度
     */
    private ArrayList<Integer> mLineHeight = new ArrayList<>();
    /**
     * 嵌套  ScrollView 的子view的高度，重新计算的
     */
    public int viewHeight = 0;
    /**
     * 用来计算子View占据的高度
     */
    public int childHeight;
    private MarginLayoutParams lp;
    private Context context;

    private boolean isCheck;
    private addViewListener OnaddListener;
    // 显示隐藏键盘用的
    InputMethodManager m;

    public XCFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCFlowLayout(Context context) {
        this(context, null);
    }

    public XCFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XCFlowLayout, defStyleAttr, 0);
        //获取横纵向的间距
        mHSpace = a.getDimensionPixelSize(R.styleable.XCFlowLayout_h_space, dpToPx(6));
        mVSpace = a.getDimensionPixelSize(R.styleable.XCFlowLayout_v_space, dpToPx(8));

    }

    /**
     * 设置间距
     * @param hSpace
     */
    public void setHSpace(int hSpace) {
        this.mHSpace = hSpace;
    }

    /**
     * 设置上下间距
     * @param vSpace
     */
    public void setVSpace(int vSpace) {
        this.mVSpace = vSpace;
    }

    public void setAddView(addViewListener OnaddListener) {
        this.OnaddListener = OnaddListener;
    }

    @SuppressWarnings("ResourceType")
    public void setChildView(ArrayList<String> listChild, Context context) {
        this.listChild = listChild;
        this.context = context;
        lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = mHSpace/2;
        lp.rightMargin = mHSpace/2;
        lp.topMargin = mVSpace/2;
        lp.bottomMargin = mVSpace/2;
        for (int i = 0; i < listChild.size(); i++) {
            TextView view = new TextView(context);
            view.setText(listChild.get(i));
            view.setTextColor(Color.WHITE);
            view.setTag(listChild.get(i));//当做点击事件用
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.textview_bg));
            view.setLayoutParams(lp);
            addView(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            MapBack.put(view.getTag().toString(), false);
        }
        TextView view = new TextView(context);
//        view.setText("点击添加");
//        view.setTextColor(Color.GREEN);
//        view.setTag("点击添加");//当做点击事件用
//        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
//        view.setLayoutParams(lp);
//        addView(view);
//        view.setOnClickListener(this);
        // 实例化显示隐藏键盘用的，当前显示则隐藏，当前隐藏则显示
//        m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void setChildView(String[] listChild, Context context) {
        for (int i = 0; i < listChild.length; i++) {
            this.listChild.add(listChild[i]);
        }
        setChildView(this.listChild, context);
    }

    public void addView(String str) {

        TextView view = new TextView(getContext());
        view.setText(str);
        view.setTextColor(Color.WHITE);
        view.setTag(str);//当做点击事件用
        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
        view.setLayoutParams(lp);
        addView(view, listChild.size());
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        MapBack.put(view.getTag().toString(), false);
        listChild.add(listChild.size(), str);
    }

    private void OnremoveView(View v,String tag) {
        removeView(v);
        MapBack.remove(tag);
        listChild.remove(tag);
        if (OnaddListener!=null)
            OnaddListener.Onremove(tag);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            String id = v.getTag().toString();
            if (id.equals("点击添加")) {
                // 点击添加按钮的代码
                // 显示隐藏键盘用的，当前显示则隐藏，当前隐藏则显示
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 点击添加按钮的代码
                if (OnaddListener != null) {
                    // 获取焦点
                    // 显示光标、
                    OnaddListener.addView();
                }
            } else {
//                setBackground(v,id);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        final String id = v.getTag().toString();
        if (!id.equals("点击添加")) {
            OnremoveView(v,id);
//            ShowDialog(v.getContext(), v,id);
            //这里是长按删除的
        }
        return true;
        // setOnLongClickListener中return的值决定是否在长按后再加一个短按动作
        // true为不加短按,false为加入短按
    }

    /**
     * 因为我们自定义view对于属性为wrap_content这种情况，如果不做处理其实是与match_parent是一样效果的。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec 拿到父容器推荐的宽和高以及计算模式
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据自身的宽度约束子控件宽度，测量孩子的大小 计算模式
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;// 自己测量的 宽度
        int height = 0;// 自己测量的高度 当有ScrollView时有bug 所以用mLineHeight 替换
        // 记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        // 获取子view的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            // 子View占据的高度
            childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            // 换行时候//￥当前行宽加即将添加的行宽大于父控件行宽时，即换行
            if (lineWidth + childWidth > sizeWidth) {
                // 对比得到最大的宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {// 不换行情况
                // 叠加行宽
                lineWidth += childWidth;
                // 得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        // 已下只是单独针对ScrollView 一般标签使用
        viewHeight = 0;
        for (int i = 0; i < mLineHeight.size(); i++) {
            viewHeight = mLineHeight.get(i) + viewHeight;
        }
        // 行高 childHeight
        // 设置自身宽度 原先添加高度无限大之后导致真的无限大，于是删除，
        // 重先bug添加到最后一个时无法显示，因为父容器高度不够，于是想到给父容器添加
        // 一行行高，bug解决，加加10间距
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                // 第一次点击viewHeight=0
                resolveSize(viewHeight + 10, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllChildViews.clear();
        mLineHeight.clear();
        // 获取当前ViewGroup的宽度
        int width = getWidth();
        int lineWidth = 0;//行宽
        int lineHeight = 0;//行高
        // 记录当前行的view
        ArrayList<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果需要换行，当前计算的宽加上即将加上的宽和间距
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                // 记录LineHeight
                mLineHeight.add(lineHeight);
                // 记录当前行的Views
                mAllChildViews.add(lineViews);
                // 重置行的宽高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 重置view的集合
                lineViews = new ArrayList();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        // 设置子View的位置
        int left = 0;
        int top = 0;
        // 获取行数
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            // 当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                // 判断是否显示
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                // 进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    private void setBackground(View v,String tag) {
        //根据tag看是否
        if (!MapBack.get(tag)) {
            BackViews.add(v);
            Backitem.add(tag);
            MapBack.put(tag, true);
            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg02));
        } else {
            BackViews.remove(v);
            Backitem.remove(tag);
            MapBack.put(tag, false);
            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
        }
        if (OnaddListener!=null)
            OnaddListener.BackTag(Backitem);

    }
/******************************************************************************
 * 长按删除的
    private void ShowDialog(final Context context, final View v,final String tag) {
        BaseDialog.Builder customBuilder = new
                BaseDialog.Builder(context)
                .setTitle("确定删除")
                .setMessage("点击确定即删除该排除条件，确定删除？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                OnremoveView(v,tag);
                                dialog.dismiss();

                            }
                        }).setImage(-1);
        BaseDialog dialog = null;
        if (dialog == null) {
            dialog = customBuilder.create();
        }
        dialog.show();
    }
*******************************************************************************/
    /**
     * dp的单位转换为px的
     *
     * @param dps
     * @return
     */
    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    public interface addViewListener {
        /**
         * 点击添加按钮，弹出布局添加的
         */
        void addView();
        /**
         * 返回选中的list字符串集合
         */
        void BackTag(ArrayList<String> list);
        /**
         * 点击删除的，返回点击的当前字符串
         */
        void Onremove(String tag);
    }
}
