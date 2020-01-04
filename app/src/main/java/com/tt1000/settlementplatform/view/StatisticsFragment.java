package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.StatisticsListAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;
import com.tt1000.settlementplatform.view.statistics.DetailFragment;
import com.tt1000.settlementplatform.view.statistics.OverviewFragment;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StatisticsFragment extends BaseFragment {

    private ListView countListView;
    private List<String> countList;

    private final int containerId = R.id.count_fragment_container;

//    public StatisticsFragment(FragmentManager mainFragmentManager) {
//        super(mainFragmentManager);
//    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_count;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        countList = new ArrayList<>();
        countList.add("概况");
        //countList.add("统计");
        countList.add("明细");
        //countList.add("商品零售统计");
    }

    private void initView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        countListView = rootView.findViewById(R.id.ls_count);
        StatisticsListAdapter adapter = new StatisticsListAdapter(countList, metrics.widthPixels, metrics.heightPixels);
        adapter.setClickListener(new StatisticsListAdapter.OnStatisticsClickListener() {
            @Override
            public void onClick(int position, StatisticsListAdapter.ViewHolder holder) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (position) {
                    case 0:
                        transaction.replace(containerId, new OverviewFragment());
                        transaction.commit();
                        break;
//                    case 1:
//                        transaction.replace(containerId, new CountFragment());
//                        transaction.commit();
//                        break;
                    case 1:
                        transaction.replace(containerId, new DetailFragment());
                        transaction.commit();
                        break;
//                    case 3:
//                        transaction.replace(containerId, new LeakageFragment());
//                        transaction.commit();
//                        break;
//                    case 3:
//                        transaction.replace(containerId, new CommodityFragment());
//                        transaction.commit();
//                        break;
                    default:
                        break;
                }
            }
        });
        countListView.setAdapter(adapter);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, new OverviewFragment());
        transaction.commit();


    }


}
