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
        CartBean bean = list.get(position);
        //ImageLoader.downloadImg(context,holder.ivCart,bean.get);

        holder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.tvNumber.getText().toString();
                int number = Integer.parseInt(s);
                int a=++number;
                holder.tvNumber.setText(String.valueOf(a));
            }
        });
        holder.ivDelCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.tvNumber.getText().toString();
                int number = Integer.parseInt(s);
                int a=--number;
                if(a>0){
                    holder.tvNumber.setText(String.valueOf(a));
                }
                if(a==0){
                    list.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        boolean checked = holder.radioBtn.isChecked();
        if(checked){
            String s = holder.tvNumber.getText().toString();
            int number = Integer.parseInt(s);
        }

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
    }


}
