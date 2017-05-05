package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;

    List<NewGoodsBean> list;
    Context context;
    boolean isMroe;

    GridLayoutManager gm1;


    public boolean isMroe() {
        return isMroe;
    }

    public void setMroe(boolean mroe) {
        isMroe = mroe;
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(List<NewGoodsBean> list, Context context, GridLayoutManager gm1) {
        this.list = list;
        this.context = context;
        this.gm1 = gm1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_FOOTER:
                return new FooterViewHolder(View.inflate(context, R.layout.item_footer, null));
            case TYPE_ITEM:
                return new GoodsViewHolder(View.inflate(context, R.layout.item_goods, null));
        }
           return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {
        if(getItemViewType(position)==TYPE_FOOTER){

            FooterViewHolder holder= (FooterViewHolder) holders;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(getFooter());
            return;
        }

        NewGoodsBean bean = list.get(position);
        GoodsViewHolder holder= (GoodsViewHolder) holders;
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvGoodsPrice.setText(bean.getPromotePrice());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb, bean.getGoodsThumb());
    }

    private int getFooter() {
        return isMroe?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount()-1==position){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void addDate(ArrayList<NewGoodsBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            gm1.setSpanCount(2);
            Unbinder bind = ButterKnife.bind(this, view);
        }
    }


     class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            gm1.setSpanCount(1);
            ButterKnife.bind(this, view);
        }
    }
}
