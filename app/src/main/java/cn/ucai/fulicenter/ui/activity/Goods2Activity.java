package cn.ucai.fulicenter.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;

public class Goods2Activity extends AppCompatActivity {
    DownNewGoodMode mode;
    int mGoodsid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_goods2);
        mGoodsid = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID,I.CAT_ID);
        mode = new DownNewGoodMode();
        DownDetails();
    }

    private void DownDetails() {

        mode.DownGoodsDetails(this, mGoodsid, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean data) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
