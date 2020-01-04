package com.tt1000.settlementplatform.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;
import com.tt1000.settlementplatform.widget.SecondExpandableListView;

import java.util.List;

public class AssitsExpandFirstAdapter extends BaseExpandableListAdapter {
    private List<String> group;
    private List<List<String>> child;
    List<String> secondGroup;

    public AssitsExpandFirstAdapter(List<String> group, List<String> secondGroup, List<List<String>> child) {
        this.group = group;
        this.child = child;
        this.secondGroup = secondGroup;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return secondGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_assits_first_group, null);
        }
        TextView textView;
        textView = convertView.findViewById(R.id.tx_assists_expand_first_title);
        textView.setText(group.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_assits_first_child, parent, false);
        }
        SecondExpandableListView secondExpand = convertView.findViewById(R.id.expand_ls_assites_second);
        secondExpand.setAdapter(new AssitsExpandSecondAdapter(secondGroup, child));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
