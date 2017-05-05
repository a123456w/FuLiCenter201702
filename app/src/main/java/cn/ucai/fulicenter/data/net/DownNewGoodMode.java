package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class DownNewGoodMode implements IDownNewGood {
    @Override
    public void DownNewGoodData(Context context, int id, int pageid, int pageSize, OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(id))
                .addParam(I.PAGE_ID,String.valueOf(pageid))
                .addParam(I.PAGE_SIZE,String.valueOf(pageSize))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    @Override
    public void DownBoutinue(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    @Override
    public void DownGoodsDetails(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils=new  OkHttpUtils<GoodsDetailsBean>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.REQUEST_FIND_GOODS_DETAILS,""+goodsId)
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
}
