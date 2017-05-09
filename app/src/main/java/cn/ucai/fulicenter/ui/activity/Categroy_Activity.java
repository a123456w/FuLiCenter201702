package cn.ucai.fulicenter.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;
import cn.ucai.fulicenter.ui.view.CatItemCategoryButton;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class Categroy_Activity extends AppCompatActivity {
    @BindView(R.id.BoutiqueFrameLayout)
    FrameLayout BoutiqueFrameLayout;
    Unbinder bind;
    GoodsFragment Fragment;
    boolean btnCurrencyPriceAcs, btnAddTimeAcs;
    @BindView(R.id.btnCurrencyPrice)
    Button btnCurrencyPrice;
    @BindView(R.id.btnAddTime)
    Button btnAddTime;
    @BindView(R.id.CBTiter)
    CatItemCategoryButton CBTiter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categroy_child);
        bind = ButterKnife.bind(this);
        int intExtra = getIntent().getIntExtra(I.CategoryChild.CAT_ID, I.CAT_ID);
        Fragment = new GoodsFragment(intExtra);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.BoutiqueFrameLayout, Fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick({R.id.btnCurrencyPrice, R.id.btnAddTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCurrencyPrice:
                btnCurrencyPriceAcs = !btnCurrencyPriceAcs;
                Fragment.shorAdapter(btnCurrencyPriceAcs ? I.SORT_BY_PRICE_ASC : I.SORT_BY_PRICE_DESC);

                Drawable top = ContextCompat.getDrawable(this, btnCurrencyPriceAcs ? R.drawable.arrow_order_up : R.drawable.arrow_order_down);
                top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                // btnCurrencyPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,top,null);
                btnCurrencyPrice.setCompoundDrawables(null, null, top, null);
                break;
            case R.id.btnAddTime:
                btnAddTimeAcs = !btnAddTimeAcs;
                Fragment.shorAdapter(btnAddTimeAcs ? I.SORT_BY_ADDTIME_ASC : I.SORT_BY_ADDTIME_DESC);
                Drawable tp = ContextCompat.getDrawable(this, btnAddTimeAcs ? R.drawable.arrow_order_up : R.drawable.arrow_order_down);
                tp.setBounds(0, 0, tp.getMinimumWidth(), tp.getMinimumHeight());
                // btnCurrencyPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,top,null);
                btnAddTime.setCompoundDrawables(null, null, tp, null);
                break;
        }
    }
}
