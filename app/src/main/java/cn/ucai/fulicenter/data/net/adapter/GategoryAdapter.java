package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GategoryAdapter extends BaseExpandableListAdapter {
    List<CategoryGroupBean> groupList;
    List<List<CategoryChildBean>> childList;
    Context context;


    @Override
    public int getGroupCount() {
        return groupList!=null?groupList.size():0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList!=null&&childList.get(groupPosition)!=null?childList.get(groupPosition).size():0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList!=null?groupList.get(groupPosition):null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList!=null&&childList.get(childPosition)!=null
                ?childList.get(groupPosition).get(childPosition)
                :null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
