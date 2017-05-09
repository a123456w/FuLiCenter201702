package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.Goods2Activity;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;

    List<NewGoodsBean> list;
    Context context;
    boolean isMroe;


    public boolean isMroe() {
        return isMroe;
    }

    public void setMroe(boolean mroe) {
        isMroe = mroe;
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(List<NewGoodsBean> list, Context context) {
        this.list = list;
        this.context = context;
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

        final NewGoodsBean bean = list.get(position);
        GoodsViewHolder holder= (GoodsViewHolder) holders;
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvGoodsPrice.setText(bean.getCurrencyPrice());
        holder.GoodsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, Goods2Activity.class)
                        .putExtra(I.Goods.KEY_GOODS_ID,bean.getGoodsId()));
            }
        });
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

    public void initData() {
        this.list.clear();
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.GoodsLinearLayout)
        LinearLayout GoodsLinearLayout;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


     class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void shortByList(final int shortBy){
        Collections.sort(list, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean o1, NewGoodsBean o2) {
                int result=0;
                switch (shortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (o1.getAddTime()-o2.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (o2.getAddTime()-o1.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result= parselRes(o1.getCurrencyPrice())-parselRes(o2.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result= parselRes(o2.getCurrencyPrice())-parselRes(o1.getCurrencyPrice());
                        break;
                }

                return result;
            }
        });
        notifyDataSetChanged();
    }

    private int parselRes(String price) {
        return Integer.parseInt(price.substring(price.indexOf("￥") + 1));
    }

    ;
}
