package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
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
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.AlbumsBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.PropertiesBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.CollectAdapter;
import cn.ucai.fulicenter.data.net.adapter.GoodsPageAdapter;
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.ui.view.AutoFlowIndicator;
import cn.ucai.fulicenter.ui.view.FlowIndicator;

public class Goods2Activity extends AppCompatActivity {
    DownNewGoodMode mode;
    DownUserMode model;
    int mGoodsid;
    List<String> mList;
    User user;
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
    @BindView(R.id.ivCollect)
    ImageView ivCollect;
    boolean isCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods2);
        ButterKnife.bind(this);
        mode = new DownNewGoodMode();
        model=new DownUserMode();
        mList = new ArrayList();

        mGoodsid = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        Log.i("main", "Goods2Activity.mGoodsid:" + mGoodsid);
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
                setResult(RESULT_OK,new Intent()
                        .putExtra(I.Goods.KEY_GOODS_ID,mGoodsid)
                        .putExtra(I.Goods.KEY_IS_COLLECT,isCollect)
                );
                finish();
            }
        });
    }

    private void DownDetails() {
        mode.DownGoodDetails(this, mGoodsid, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean data) {
                if (data != null) {
                    setView(data);
                    setImg(data);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
        loadCollect();
    }

    private void loadCollect() {
        user=FuLiCenterApplication.getUser();
        if(user!=null){
            model.isCollects(Goods2Activity.this, String.valueOf(mGoodsid), user.getMuserName(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if(result!=null){
                                isCollect=result.isSuccess()?true:false;
                                updataUi();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            isCollect=false;
                            updataUi();
                        }
                    });
        }
    }

    private void updataUi() {
        ivCollect.setImageResource(isCollect?R.mipmap.bg_collect_out:R.mipmap.bg_collect_in);
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
        wvBrief.loadDataWithBaseURL(null, data.getGoodsBrief(), null, I.UTF_8, null);
        tvCurrencyPrice.setText(data.getCurrencyPrice());
        tvName.setText(data.getGoodsName());
        tvTitle.setText(data.getGoodsName());
        // Log.i("main", "data=" + data.toString());
    }


    @OnClick(R.id.ivCollect)
    public void onUpdataCollect() {
        Log.i("main","Goods2Activity.onUpdataCollect");
        user = FuLiCenterApplication.getInstance().getUser();
        if(user==null){
            startActivityForResult(new Intent(Goods2Activity.this,LoginActivity.class),0);
        }else{
            Log.i("main","Goods2Activity.onUpdataCollect user!=null");
            if(isCollect){
                Log.i("main","Goods2Activity.onUpdataCollect isCollect=true");
                removeCollect();
            }else{
                Log.i("main","Goods2Activity.onUpdataCollect isCollect=false");
                addCollect();
            }
        }


    }

    private void removeCollect() {

        model.removeCollects(Goods2Activity.this, String.valueOf(mGoodsid), user.getMuserName(),
                new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                Log.i("main","Goods2Activity.onUpdataCollect.removeCollect ");
                setUi(result);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void addCollect() {
        model.addCollects(Goods2Activity.this, String.valueOf(mGoodsid), user.getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        setUi(result);
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
    }

    private void setUi(MessageBean result) {

        if(result!=null&&result.isSuccess()){
            isCollect=!isCollect;
            updataUi();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            loadCollect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @OnClick(R.id.ivAddCart)
    public void onCartClick(View view){
        if(FuLiCenterApplication.getInstance().isLogined()){
            addCart();
        }else {
            startActivityForResult(new Intent(Goods2Activity.this,LoginActivity.class),0);
        }
    }

    private void addCart() {
        model.addCart(Goods2Activity.this, mGoodsid, user.getMuserName(), I.ADD_CART_COUNT, false
                , new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null && result.isSuccess()){
                    Log.i("main","addCart.result="+ result.isSuccess()+"mGoodsid="+mGoodsid+" user.getMuserName()="+user.getMuserName()+"  I.ADD_CART_COUNT="+I.ADD_CART_COUNT );
                    CommonUtils.showLongToast(R.string.add_goods_success);
                }else {
                    CommonUtils.showLongToast(R.string.add_goods_fail);
                }
            }
            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.add_goods_fail);
            }
        });
    }
}
