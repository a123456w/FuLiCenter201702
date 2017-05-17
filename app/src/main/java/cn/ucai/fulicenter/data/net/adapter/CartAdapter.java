package cn.ucai.fulicenter.data.net.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.Goods2Activity;
import cn.ucai.fulicenter.ui.activity.MainActivity;
import cn.ucai.fulicenter.ui.fiagment.CartFragment;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    Context context;
    ArrayList<CartBean> list;
    CompoundButton.OnCheckedChangeListener cclListener;

    View.OnClickListener Clicklistener;

    public void setClicklistener(View.OnClickListener clicklistener) {
        Clicklistener = clicklistener;
    }

    public void setCclListener(CompoundButton.OnCheckedChangeListener cclListener) {
        this.cclListener = cclListener;
    }

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        this.context = context;
        this.list = list;
    }

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
        CheckBox radioBtn;
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
                final GoodsDetailsBean goods = bean.getGoods();
                radioBtn.setOnCheckedChangeListener(null);
                if(goods!=null){
                    ImageLoader.downloadImg(context,ivCart,goods.getGoodsImg());
                    tvCartName.setText(goods.getGoodsName());
                    tvTotal.setText(goods.getCurrencyPrice());
                    radioBtn.setChecked(bean.isChecked());
                    tvNumber.setText(String.valueOf(bean.getCount()));
                    ivCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity)context).startActivityForResult(
                                    new Intent(context, Goods2Activity.class)
                                    .putExtra(I.Goods.KEY_GOODS_ID,goods.getGoodsId())
                                    ,I.REQUEST_CODE_LOGIN_FROM_CART
                            );
                        }
                    });
                    radioBtn.setTag(position);
                    radioBtn.setOnCheckedChangeListener(cclListener);
                    ivAddCart.setTag(position);
                    ivAddCart.setOnClickListener(Clicklistener);
                    ivDelCart.setTag(position);
                    ivDelCart.setOnClickListener(Clicklistener);

                }
            }
        }
    }


}
