package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.ucai.fulicenter.R;


/**
 * Created by yao on 2016/12/28.
 */

public class FlowIndicator extends View {
    int mCount;
    int mRadius;
    int mSpace;
    int mNormalColor;
    int mFocusColor;
    int mFocus;

    Paint mPaint;

    public void setCount(int count) {
        this.mCount = count;
        requestLayout();
//        invalidate();
    }

    public int getFocus() {
        return mFocus;
    }

    public void setFocus(int focus) {
        this.mFocus = focus;
        invalidate();
    }

    public FlowIndicator(Context context) {
        super(context);
    }

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mCount = array.getInt(R.styleable.FlowIndicator_count, 2);
        mSpace = array.getDimensionPixelSize(R.styleable.FlowIndicator_space, 10);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focus_color, 0xfff);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normal_color, 0xfff);
        mRadius = array.getDimensionPixelOffset(R.styleable.FlowIndicator_r, 15);

        setCount(mCount);
        setFocus(mFocus);

        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("main", "onMeasure()");
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result=size;
        if (mode != MeasureSpec.EXACTLY) {
            size=getPaddingBottom()+getPaddingTop()+2*mRadius;
            result = Math.min(result, size);
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result=size;
        if (mode != MeasureSpec.EXACTLY) {
            size=getPaddingLeft()+getPaddingRight()+mCount*2*mRadius+(mCount-1)*mSpace;
            result = Math.min(result, size);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<mCount;i++) {
            int color=mFocus==i?mFocusColor:mNormalColor;
            mPaint.setColor(color);
            int x=mRadius+i*2*mRadius+i*mSpace;
            canvas.drawCircle(x,mRadius,mRadius,mPaint);
        }
    }
}
