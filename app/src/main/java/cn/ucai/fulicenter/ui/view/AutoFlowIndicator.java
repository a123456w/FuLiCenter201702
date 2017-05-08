package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.ucai.fulicenter.data.net.adapter.GoodsPageAdapter;



public class AutoFlowIndicator extends ViewPager {
    FlowIndicator mFlowIndicator;
    int mCount;
    Timer mTimer;
    Handler mHandler;
    List<String> mList;
    boolean isMore=true;
    int anInt;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case 0:
                isMore=false;

                break;
            case 1:
                isMore=true;
                break;
            case 2:
                isMore=false;

                break;
        }
        return super.onTouchEvent(ev);
    }

    public AutoFlowIndicator(Context context) {
        super(context);
    }

    public AutoFlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener();
        initHandler();
    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(isMore){
                    AutoFlowIndicator.this.setCurrentItem(getCurrentItem()+1);
                }
            }
        };
    }

    private void setListener() {
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFlowIndicator.setFocus(position%mList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void startPlay(Context context,List<String> list,FlowIndicator flowIndicator){
        mFlowIndicator=flowIndicator;
        mCount=list.size();
        mList=list;
        GoodsPageAdapter goodsPageAdapter = new GoodsPageAdapter(mList, context);
        this.setAdapter(goodsPageAdapter);
        mFlowIndicator.setFocus(0);
        mFlowIndicator.setCount(mCount);


        final MyScroller myScroller = new MyScroller(context);
        myScroller.setDuration(2000);
        try {
            Field filed = ViewPager.class.getDeclaredField("mScroller");
            filed.setAccessible(true);
//            this.mScroller=myScroller;
            filed.set(this,myScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mTimer=new Timer();
        if(isMore) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                        mHandler.sendEmptyMessage(0);
                }
            }, 4000, 500);
        }
    }
    class MyScroller extends Scroller {
        int duration;

        public void setDuration(int duration) {
            this.duration=duration;
        }

        public MyScroller(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }
}
