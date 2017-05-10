package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.net.adapter.CatFilterAdapter;
import cn.ucai.fulicenter.data.utils.CommonUtils;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class CatItemCategoryButton extends Button{
    Context context;
    PopupWindow mPopupWindow;
    GridView gv;
    CatFilterAdapter adapter;
    boolean isExpan=false;
    public CatItemCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setListener();
    }

    private void setListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpan){
                    if(mPopupWindow!=null&&mPopupWindow.isShowing()){
                        mPopupWindow.dismiss();
                    }
                }else {
                    initPopWin();
                }
                setExpan();
            }


        });
    }

    private void setExpan() {
        Drawable tp = ContextCompat.getDrawable(context, isExpan? R.mipmap.arrow2_up:R.mipmap.arrow2_down);
        tp.setBounds(0,0,tp.getMinimumWidth(),tp.getMinimumHeight());
        // btnCurrencyPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,top,null);
        this.setCompoundDrawables(null,null,tp,null);
        isExpan=!isExpan;
    }

    private void initPopWin() {
        if(mPopupWindow==null){
            mPopupWindow=new PopupWindow(context);
            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

            /*TextView tv=new TextView(context);
            tv.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setText("bausbaubfaiubfau");
            tv.setTextSize(30);
            tv.setTextColor(getResources().getColor(R.color.google_red));*/

            mPopupWindow.setContentView(gv);
        }
        mPopupWindow.showAsDropDown(this);
    }
    public void initView(String name, ArrayList<CategoryChildBean> list) {
        if(name==null||list==null||list.size()==0){
            CommonUtils.showLongToast("数据下载异常，请重试");
            return;
        }
        this.setText(name);
        adapter=new CatFilterAdapter(context,list,name);
        gv=new GridView(context);
        gv.setNumColumns(GridView.AUTO_FIT);
        gv.setHorizontalSpacing(10);
        gv.setVerticalSpacing(10);
        gv.setAdapter(adapter);
    }

    public void release() {
        if(mPopupWindow!=null){
            mPopupWindow.dismiss();
        }
    }
}
