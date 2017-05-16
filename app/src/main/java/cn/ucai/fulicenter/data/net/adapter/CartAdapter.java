package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    Context context;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<CartBean> list;

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartHolder(View.inflate(context, R.layout.item_cart, null));
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radioBtn)
        RadioButton radioBtn;
        @BindView(R.id.ivCart)
        ImageView ivCart;
        @BindView(R.id.tvCartName)
        TextView tvCartName;
        @BindView(R.id.ivAddCart)
        ImageView ivAddCart;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.ivDelCart)
        ImageView ivDelCart;

        CartHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            CartBean bean = list.get(position);
            if(bean!=null){
                GoodsDetailsBean goods = bean.getGoods();
                if(goods!=null){
                    ImageLoader.downloadImg(context,ivCart,goods.getGoodsImg());
                    tvCartName.setText(goods.getGoodsName());
                    tvTotal.setText(goods.getCurrencyPrice());
                    radioBtn.setChecked(bean.isChecked());
                }
            }
        }
    }


}
