package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.Categroy_Activity;
import cn.ucai.fulicenter.ui.activity.Goods2Activity;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class CatFilterAdapter extends BaseAdapter {
    Context context;
    ArrayList<CategoryChildBean> list;

    public CatFilterAdapter(Context context, ArrayList<CategoryChildBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public CategoryChildBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatFilterViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cat_fiter_child, null);
            holder = new CatFilterViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (CatFilterViewHolder) convertView.getTag();
        holder.setView(position);

        return convertView;
    }

    class CatFilterViewHolder {
        @BindView(R.id.ivCatFilter)
        ImageView ivCatFilter;
        @BindView(R.id.tvCatFilterName)
        TextView tvCatFilterName;
        @BindView(R.id.line1)
        LinearLayout line1;


        CatFilterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        public void setView(int position) {
            final CategoryChildBean bean = list.get(position);
            ImageLoader.downloadImg(context, ivCatFilter, bean.getImageUrl());
            tvCatFilterName.setText(bean.getName());
            line1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(
                            new Intent(context, Categroy_Activity.class)
                            .putExtra(I.CategoryChild.CAT_ID,bean.getId())
                                    .putExtra(I.CategoryChild.ID,list)


                    );

                }
            });
        }
    }





}
