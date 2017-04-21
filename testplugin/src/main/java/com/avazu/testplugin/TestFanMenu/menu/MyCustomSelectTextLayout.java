package com.avazu.testplugin.TestFanMenu.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/12.
 */

public class MyCustomSelectTextLayout extends ViewGroup{
    private Paint mPaint;
    private boolean isLeft;

    public MyCustomSelectTextLayout(Context context) {
        super(context);
        initView();
    }

    public MyCustomSelectTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyCustomSelectTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setARGB(255, 222, 222, 222);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : sizeWidth, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : sizeWidth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();

        int startX = isLeft ? 550 : r - 550;
        for(int i = 0; i < count; i++){
            View child = getChildAt(i);
            child.layout(startX, b - child.getMeasuredHeight() / 2, r - 200, b + child.getMeasuredHeight() / 2);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {

        int count = getChildCount();
        int index = indexOfChild(child);
        int degree = 90 / count;
        canvas.save();
        canvas.rotate(degree * (index + 0.5f), getWidth(), getHeight());
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(getWidth(), getHeight(), 600.0f, mPaint);
    }

}
