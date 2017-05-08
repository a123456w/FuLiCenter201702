package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.AlbumsBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.PropertiesBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.GoodsPageAdapter;
import cn.ucai.fulicenter.ui.view.AutoFlowIndicator;
import cn.ucai.fulicenter.ui.view.FlowIndicator;

public class Goods2Activity extends AppCompatActivity {
    DownNewGoodMode mode;
    int mGoodsid;
    List<String> mList;

    GoodsPageAdapter mAdapter;
    @BindView(R.id.ivTitle)
    ImageView ivTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvEnglishName)
    TextView tvEnglishName;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCurrencyPrice)
    TextView tvCurrencyPrice;
    @BindView(R.id.AutoFlowIndicator)
    cn.ucai.fulicenter.ui.view.AutoFlowIndicator AutoFlowIndicator;
    @BindView(R.id.FlowIndicator)
    cn.ucai.fulicenter.ui.view.FlowIndicator FlowIndicator;
    @BindView(R.id.wvBrief)
    WebView wvBrief;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods2);
        ButterKnife.bind(this);
        mode = new DownNewGoodMode();
        mList = new ArrayList();

        mGoodsid = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        DownDetails();
        setListener();
    }

    private void setListener() {
        AutoFlowIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return Goods2Activity.super.onTouchEvent(event);
            }
        });
        ivTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DownDetails() {

        mode.DownGoodsDetails(this, mGoodsid, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean data) {
                setView(data);
                setImg(data);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setImg(GoodsDetailsBean data) {
        PropertiesBean[] properties = data.getProperties();
        for (PropertiesBean property : properties) {
            for (AlbumsBean bean : property.getAlbums()) {
                mList.add(bean.getImgUrl());
            }
        }
        mAdapter = new GoodsPageAdapter(mList, this);
        AutoFlowIndicator.setAdapter(mAdapter);
        AutoFlowIndicator.startPlay(this, mList, FlowIndicator);
    }

    private void setView(GoodsDetailsBean data) {
        tvEnglishName.setText(data.getGoodsEnglishName());
        wvBrief.loadDataWithBaseURL(null,data.getGoodsBrief(),null,I.UTF_8,null);
        tvCurrencyPrice.setText(data.getCurrencyPrice());
        tvName.setText(data.getGoodsName());
        tvTitle.setText(data.getGoodsName());
        Log.i("main", "data=" + data.toString());
    }
}
