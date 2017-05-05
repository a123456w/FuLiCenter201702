package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class FlowIndicator extends View {
    private int mCount;
    private int mRadius;
    private int mSpace;
    private int mFucos;
    private int mNormalColor;
    private int mFocusColor;

    public int getFucos() {
        return mFucos;
    }

    public void setFucos(int mFucos) {
        this.mFucos = mFucos;
        invalidate();
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    Paint mPaint;

    public FlowIndicator(Context context) {
        super(context);
    }

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mCount=array.getInt(R.styleable.FlowIndicator_count,0);
        mRadius=array.getInt(R.styleable.FlowIndicator_r,10);
        mSpace=array.getInt(R.styleable.FlowIndicator_space,10);
        mFucos=array.getInt(R.styleable.FlowIndicator_focus,0);
        mFocusColor=array.getInt(R.styleable.FlowIndicator_focus_color,0x000000);
        mNormalColor=array.getInt(R.styleable.FlowIndicator_normal_color,0xcccccc);


        mPaint=new Paint();
        mPaint.setAntiAlias(true);



        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpecWidth(widthMeasureSpec),measureSpecHeight(heightMeasureSpec));
    }

    private int measureSpecHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result=size;
        if(mode==MeasureSpec.AT_MOST){
            result=getPaddingBottom()+getPaddingTop()+mRadius*2;
            result=Math.min(result,size);
        }
        return result;

    }

    private int measureSpecWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result=size;
        if (mode==MeasureSpec.AT_MOST){
            result=getPaddingLeft()+getPaddingRight()+mRadius*mCount+(mCount-1)*mSpace;
            result=Math.min(result,size);
        }
        return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i=0;i<mCount;i++){
            int color=mFucos==i?mFocusColor:mNormalColor;
            mPaint.setColor(color);
            int x = mRadius+mRadius*i*2+i*mSpace;
            canvas.drawCircle(x,mRadius,mRadius,mPaint);
        }
    }
}
