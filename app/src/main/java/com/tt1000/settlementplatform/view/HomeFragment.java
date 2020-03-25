package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt1000.settlementplatform.MainActivity;
import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.HomeRecyclerAdapter;
import com.tt1000.settlementplatform.adapter.OnAdapterItemClickListener;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.bean.MainMenu;
import com.tt1000.settlementplatform.bean.member.TfConsumeCardRecordDao;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMealTimesDao;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.MyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {

    private static final String TAG = "homefragment";
    private GridView gvHome;
    private RecyclerView recyclerHome;
    private List<MainMenu> menuList;
    private int width;
    private int height;

    private LinearLayout ll_down_bar;
    private LinearLayout ll_top_bar;
    private TextView tx_login_user_name;
    private TextView tx_user_id;

    public HomeFragment() {
        super();
    }

//    public HomeFragment(FragmentManager mainFragmentManager) {
//        super(mainFragmentManager);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initGrid();
    }

    private void initView() {
        recyclerHome = (RecyclerView) findViewById(R.id.recycler_home);
        tx_user_id = (TextView) mainActivity.findViewById(R.id.tx_user_id);
        tx_login_user_name = (TextView) mainActivity.findViewById(R.id.tx_login_user_name);
        tx_user_id.setText(MyConstant.gSharedPre.getString(MyConstant.SP_STORE_NAME,""));
        tx_login_user_name.setText(MyConstant.gSharedPre.getString(MyConstant.SP_MACHINE_NAME,""));
    }

    private void initData() {
        menuList = new ArrayList<>();

        MainMenu open = new MainMenu();
        open.setResId(R.drawable.open);
        open.setText("营业");
        menuList.add(open);

        MainMenu count = new MainMenu();
        count.setResId(R.drawable.count);
        count.setText("统计");
        menuList.add(count);

        MainMenu setting = new MainMenu();
        setting.setResId(R.drawable.setting);
        setting.setText("设置");
        menuList.add(setting);

        MainMenu help = new MainMenu();
        help.setResId(R.drawable.help);
        help.setText("帮助");
        menuList.add(help);

    }

    private void initGrid() {
        final LinearLayout linearLayout = rootView.findViewById(R.id.ll_home);
        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                width = width == 0 ? recyclerHome.getWidth() : width;
                height = height == 0 ? recyclerHome.getHeight() : height;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerHome.getLayoutParams();
//                params.topMargin = (height -(height / 3 * 2) )/ 6;
                params.topMargin = (height - (height / 3 * 2 + 30)) / 2;
                recyclerHome.setLayoutParams(params);
                HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(menuList, width, height);
                adapter.setListener(new OnAdapterItemClickListener() {

                    @Override
                    public void onClick(int position, Object data) {
                        switch (position) {
                            case 0:
                                Date startTime = null, endTime = null, curTime = null;
                                List<TfMealTimes> tfMealTimesList = pDaoSession.queryBuilder(TfMealTimes.class)
                                        .orderAsc(TfMealTimesDao.Properties.MT_ID)
                                        .build()
                                        .list();
                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                boolean operation = false;
                                try {
                                    for (TfMealTimes mealTimes : tfMealTimesList) {
                                        startTime = format.parse(mealTimes.getSTARTTIME());
                                        endTime = format.parse(mealTimes.getENDTIME());
                                        curTime = format.parse(MyUtil.obtainCurrentSysDate(1));
                                        if (curTime.getTime() >= startTime.getTime() && curTime.getTime() <= endTime.getTime()) {
                                            operation = true;
                                            break;
                                        }
                                    }
                                    if (!operation) {
                                        StringBuilder sb = new StringBuilder();
                                        for (TfMealTimes mealTimes : tfMealTimesList) {
                                            sb.append(mealTimes.getMT_NAME() + " : " + mealTimes.getSTARTTIME() + " ~ " + mealTimes.getENDTIME() + "\n");
                                        }
                                        if (!sb.toString().trim().isEmpty()) {
                                            showMessage("营业时间：", sb.toString());
                                        } else {
                                            showMessage("警告", "数据同步不完整");
                                        }
                                        return;
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

//                                MainActivity.gUiHandler
//                                        .obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, new OperationFragment()).sendToTarget();
//                                appTitle.setText("运营");
                                MainActivity.gUiHandler
                                        .obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, new GeneralFragment()).sendToTarget();
                                appTitle.setText("运营");
                                break;
                            case 1:
                                MainActivity.gUiHandler
                                        .obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, new StatisticsFragment()).sendToTarget();
                                appTitle.setText("统计");
                                break;
                            case 2:
                                MainActivity.gUiHandler
                                        .obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, new SettingFragment()).sendToTarget();
                                appTitle.setText("系统设置");
                                break;
                            case 3:
                                MainActivity.gUiHandler
                                        .obtainMessage(MyConstant.REPLACE_FRAGMENT_TO_STACK, new AssistsFragment()).sendToTarget();
                                appTitle.setText("帮助");
                                break;
                            default:
                                break;
                        }
                    }
                });
                recyclerHome.setLayoutManager(new GridLayoutManager(mContext, 2));
                recyclerHome.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rootView != null) {
            rootView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
