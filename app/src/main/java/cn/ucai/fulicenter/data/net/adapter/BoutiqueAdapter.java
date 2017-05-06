package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.BoutiqueActivity;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    List<BoutiqueBean> list;
    Context context;

    public BoutiqueAdapter(Context context, List<BoutiqueBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new BoutiqueViewHolder(View.inflate(context, R.layout.item_boutique, null));
    }

    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, final int position) {
        final BoutiqueBean bean = list.get(position);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvManages.setText(bean.getName());
        holder.tvManages1.setText(bean.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BoutiqueActivity.class)
                        .putExtra(I.NewAndBoutiqueGoods.CAT_ID,bean.getId())
                        .putExtra(I.Boutique.TITLE,bean.getTitle())
                        .putExtra("name",bean.getName())
                );
            }
        });
        ImageLoader.downloadImg(context,holder.ivBoutiqueImg,bean.getImageurl());
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size();
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if(this.list!=null){
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @BindView(R.id.tv1Title)
        TextView tvTitle;
        @BindView(R.id.tvManages)
        TextView tvManages;
        @BindView(R.id.tvManages1)
        TextView tvManages1;

         BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
