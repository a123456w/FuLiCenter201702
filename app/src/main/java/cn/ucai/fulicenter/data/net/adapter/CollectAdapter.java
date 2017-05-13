package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    static final int TYPE_FOOTER = 0;
    static final int TYPE_ITEM = 1;
    Context context;
    List<CollectBean> list;

    boolean isCancel=false;

    public void setCancel(boolean cancel) {
        isCancel = cancel;
        notifyDataSetChanged();
    }

    public boolean isCancel() {
        return isCancel;
    }

    String footertext;

    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public String getFootertext() {
        return footertext;
    }

    public void setFootertext(String footertext) {
        this.footertext = footertext;
        notifyDataSetChanged();
    }

    public CollectAdapter(Context context, List<CollectBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return new FooterViewHolder(View.inflate(context, R.layout.item_footer, null));
            case TYPE_ITEM:
                return new CollectViewHolder(View.inflate(context, R.layout.item_collect, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holders, int position) {
        if(getItemViewType(position)==TYPE_FOOTER){
            FooterViewHolder holder= (FooterViewHolder) holders;
            holder.tvFooter.setText(footertext);
            holder.tvFooter.setVisibility(View.VISIBLE);
            return;
        }
        final CollectViewHolder holder= (CollectViewHolder) holders;
        CollectBean bean = list.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb, bean.getGoodsImg());
        holder.ivRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除收藏
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               holder.ivRemoveCart.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivRemoveCart.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void setDate(ArrayList<CollectBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivRemoveCart)
        ImageView ivRemoveCart;
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.GoodsLinearLayout)
        LinearLayout GoodsLinearLayout;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
