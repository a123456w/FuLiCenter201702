package cn.ucai.fulicenter.data.net.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.data.utils.ImageLoader;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class GoodsPageAdapter extends PagerAdapter {
    List<String> list;
    Context context;

    public GoodsPageAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView ivGoods = new ImageView(context);
        ivGoods.setLayoutParams(new LinearLayout.LayoutParams(200,250));
        container.addView(ivGoods);
        ImageLoader.downloadImg(context,ivGoods,list.get(position%list.size()));
        return ivGoods;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView ivGoods= (ImageView) object;
        container.removeView(ivGoods);
    }
}
