package com.tt1000.settlementplatform.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ExpandableListView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.adapter.AssitsExpandFirstAdapter;
import com.tt1000.settlementplatform.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class AssistsFragment extends BaseFragment {

    private ExpandableListView firstExpand;
    private List<String> firstGroup;
    private List<String> secondGroup;
    private List<List<String>> secondChild;

//    public AssistsFragment(FragmentManager mainFragmentManager) {
//        super(mainFragmentManager);
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_assists;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        firstGroup = new ArrayList<>();
        secondGroup = new ArrayList<>();
        secondChild = new ArrayList<>();
//
        firstGroup.add("帮助");

        secondGroup.add("在线帮助");
        secondGroup.add("使用说明书");

        List<String> data = new ArrayList<>();
        data.add("文字提示");
        data.add("远程维护");
        data.add("常见问题列表");
        data.add("在线升级");

        secondChild.add(data);
        secondChild.add(new ArrayList<String>());
    }

    private void initView() {
        firstExpand = (ExpandableListView) findViewById(R.id.expand_ls_assites_first);
        firstExpand.setAdapter(new AssitsExpandFirstAdapter(firstGroup, secondGroup, secondChild));
//        firstExpand.setAdapter();
    }
}
