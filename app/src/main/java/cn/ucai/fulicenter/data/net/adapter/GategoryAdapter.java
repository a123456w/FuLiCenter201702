package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.Categroy_Activity;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GategoryAdapter extends BaseExpandableListAdapter {
    List<CategoryGroupBean> groupList;
    List<ArrayList<CategoryChildBean>> childList;
    Context context;



    public GategoryAdapter(List<CategoryGroupBean> groupList, List<ArrayList<CategoryChildBean>> childList, Context context) {
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
        return getGroup(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childList.get((int) getGroupId(groupPosition)).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
int pageId;
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
      Log.i("main","getGroupView");
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
        Log.i("main","getChildView");
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
        @BindView(R.id.RelativeLayoutCategroys)
        RelativeLayout RelativeLayoutCategroy;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        public void bind(final int groupPosition, int childPosition) {
            final CategoryChildBean bean = getChild(groupPosition, childPosition);
            if(bean!=null){
                ImageLoader.downloadImg(context,ivCategoryChild,bean.getImageUrl());
                tvCategoryChildName.setText(bean.getName());
                RelativeLayoutCategroy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context,Categroy_Activity.class)
                                .putExtra(I.CategoryChild.CAT_ID,bean.getId())
                                .putExtra(I.Category.KEY_NAME,groupList.get(groupPosition).getName())
                                .putExtra(I.CategoryChild.ID,childList.get(groupPosition))
                        );
                    }
                });
            }
        }
    }

}
