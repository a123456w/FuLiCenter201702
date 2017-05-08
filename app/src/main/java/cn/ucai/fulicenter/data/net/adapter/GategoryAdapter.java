package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GategoryAdapter extends BaseExpandableListAdapter {
    List<CategoryGroupBean> groupList;
    List<List<CategoryChildBean>> childList;
    Context context;



    public GategoryAdapter(List<CategoryGroupBean> groupList, List<List<CategoryChildBean>> childList, Context context) {
        this.groupList = groupList;
        this.childList = childList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && childList.get(groupPosition) != null ? childList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList != null ? groupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList != null && childList.get(groupPosition) != null
                ? childList.get(groupPosition).get(childPosition)
                : null;
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
        CategoryViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category, null);
            holder = new CategoryViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView=View.inflate(context, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ChildViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition,childPosition);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class CategoryViewHolder {
        @BindView(R.id.ivCategory)
        ImageView ivCategory;
        @BindView(R.id.tvCategoryName)
        TextView tvCategoryName;
        @BindView(R.id.ivCategoryoff)
        ImageView ivCategoryoff;
        @BindView(R.id.RelativeLayoutCategroy)
        RelativeLayout RelativeLayoutCategroy;

        CategoryViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        public void bind(int groupPosition, boolean isExpanded) {
            CategoryGroupBean bean = groupList.get(groupPosition);
            tvCategoryName.setText(bean.getName());
            ImageLoader.downloadImg(context, ivCategory, bean.getImageUrl());
            ivCategoryoff.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.ivCategoryChild)
        ImageView ivCategoryChild;
        @BindView(R.id.tvCategoryChildName)
        TextView tvCategoryChildName;
        @BindView(R.id.RelativeLayoutCategroy)
        RelativeLayout RelativeLayoutCategroy;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        public void bind(int groupPosition, int childPosition) {
            CategoryChildBean bean = getChild(groupPosition, childPosition);
            if(bean!=null){
                ImageLoader.downloadImg(context,ivCategoryChild,bean.getImageUrl());
                tvCategoryChildName.setText(bean.getName());
            }
        }
    }
}
