package cn.ucai.fulicenter.data.net;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public interface IDownNewGood {
    void DownNewGoodData(Context context, int id, int pageid, int pageSize, OnCompleteListener<NewGoodsBean[]> listener);
    void DownBoutinue(Context context, OnCompleteListener<BoutiqueBean[]> listener);

}
