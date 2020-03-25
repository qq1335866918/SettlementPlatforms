package com.tt1000.settlementplatform.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    GridView commodity_grid;
    EditText commodity_put;
    ArrayList<String> BackList,list;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private XCFlowLayout mXCFlowLayout;
    @Override
    protected int setContentView() {
        return R.layout.fragment_commodity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        BackList = new ArrayList<>();
        list = new ArrayList<>();
        getData();
        String [] from ={"name","price"};
        int [] to = {R.id.item_name,R.id.item_price};
        sim_adapter = new SimpleAdapter(mainActivity, data_list, R.layout.grid_item, from, to);
        commodity_grid.setAdapter(sim_adapter);
        mXCFlowLayout.setChildView(list,mainActivity);
        mXCFlowLayout.setAddView(new XCFlowLayout.addViewListener() {
            @Override
            public void addView() {

            }

            @Override
            public void BackTag(ArrayList<String> list) {
                BackList.clear();
                BackList.addAll(list);
            }

            @Override
            public void Onremove(String tag) {

            }
        });
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<20;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "青菜"+i);
            map.put("price", 1+i);
            data_list.add(map);
        }
        return data_list;
    }

    private void initView() {
        commodity_grid = (GridView) findViewById(R.id.commodity_grid);
        LinearLayout commodity_ll = (LinearLayout) findViewById(R.id.commodity_ll);
        commodity_ll.measure(0,0);
        int height = commodity_ll.getMeasuredHeight();
        commodity_grid.setOnItemClickListener(this);
        commodity_put = (EditText) findViewById(R.id.commodity_put);
        commodity_put.requestFocus();
        commodity_put.setInputType(InputType.TYPE_NULL);
        mXCFlowLayout= (XCFlowLayout) findViewById(R.id.mXCFlowLayout);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        for (int i = 0;i<data_list.size();i++){
            if (i == position){
                Map<String, Object> stringObjectMap = data_list.get(i);
                String name = (String) stringObjectMap.get("name");
                Toast.makeText(mainActivity,name,Toast.LENGTH_SHORT).show();
                mXCFlowLayout.addView(name);
            }
        }
    }
}
