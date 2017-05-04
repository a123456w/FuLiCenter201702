package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<NewGoodsAdapter.GoodsViewHolder> {
    List<NewGoodsBean> list;
    Context context;
    boolean isMroe;

    public boolean isMroe() {
        return isMroe;
    }

    public void setMroe(boolean mroe) {
        isMroe = mroe;
    }

    public NewGoodsAdapter(List<NewGoodsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("main", "onCreateViewHolder");
        return new GoodsViewHolder(View.inflate(context, R.layout.item_goods, null));
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        Log.i("main", "onBindViewHolder");
        NewGoodsBean bean = list.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvGoodsPrice.setText(bean.getPromotePrice());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb, bean.getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
        @BindView(R.id.layoutgoods)
        LinearLayout layoutgoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}