package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;

public class Goods2Activity extends AppCompatActivity {
    DownNewGoodMode mode;
    int mGoodsid;

    @BindView(R.id.tvEnglishName)
    TextView tvEnglishName;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCurrencyPrice)
    TextView tvCurrencyPrice;
    @BindView(R.id.tvBrief)
    TextView tvBrief;
    @BindView(R.id.activity_goods2)
    LinearLayout activityGoods2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods2);
        ButterKnife.bind(this);
        mGoodsid = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, I.CAT_ID);
        mode = new DownNewGoodMode();
        DownDetails();
    }

    private void DownDetails() {

        mode.DownGoodsDetails(this, mGoodsid, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean data) {
                setView(data);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setView(GoodsDetailsBean data) {
        tvEnglishName.setText(data.getGoodsEnglishName());
        tvBrief.setText(data.getGoodsBrief());
        tvCurrencyPrice.setText(data.getCurrencyPrice());
        tvName.setText(data.getGoodsName());
    }
}
