package com.tt1000.settlementplatform.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt1000.settlementplatform.R;

import java.util.List;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;

public class AssitsExpandSecondAdapter extends BaseExpandableListAdapter {

    private List<String> group;
    private List<List<String>> child;

    public AssitsExpandSecondAdapter(List<String> group, List<List<String>> child) {
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
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
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_assits_second_group, null);
            holder.ivArrow = convertView.findViewById(R.id.iv_assits_expand_second_arrows);
            holder.ivIcon = convertView.findViewById(R.id.iv_assits_expand_second_icon);
            holder.txTitle = convertView.findViewById(R.id.tx_assits_expand_second_title);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.txTitle.setText(group.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//
//        }
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_assits_second_child, parent, false);
        TextView textView;
        textView = convertView.findViewById(R.id.tx_assits_expand_three_title);
        textView.setText(child.get(groupPosition).get(childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupViewHolder {
        ImageView ivArrow;
        ImageView ivIcon;
        TextView txTitle;
    }
}
