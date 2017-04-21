package com.avazu.testplugin.TestFanMenu.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/3/12.
 */

public class MyCustomCenterView extends View {
    private boolean isLeft = false;
    private int offsetX;
    private int offsetY;
    private Paint mTextPaint;
    private Paint mBgPaint;
    private RectF mRectF;
    private float[] mLines;

    public MyCustomCenterView(Context context) {
        super(context);
    }

    public MyCustomCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomCenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initView();
    }

    private void initView(){
        mTextPaint = new Paint();
        mTextPaint.setARGB(255,255,255,255);
        mTextPaint.setTextSize(36);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setStyle(Paint.Style.STROKE);

        mBgPaint = new Paint();
        mBgPaint.setARGB(255,0,140,255);
        mBgPaint.setStyle(Paint.Style.FILL);


        mRectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : 150, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : 150);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        offsetX = (int)(getMeasuredHeight() * 0.25);
        offsetY = (int)(getMeasuredHeight() * 0.25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = isLeft ? offsetX : getWidth() - offsetX;
        int centerY = getHeight() - offsetY;
        canvas.drawCircle(centerX, centerY, getHeight() - offsetX, mBgPaint);

        if(isLeft) {
            centerX += 20;
            centerY += 20;
        }
        else
        {
            centerX -= 20;
            centerY -= 20;
        }

        canvas.drawLine(centerX - 20, centerY - 20, centerX + 20, centerY + 20, mTextPaint);
        canvas.drawLine(centerX - 20, centerY + 20, centerX + 20, centerY - 20, mTextPaint);

        canvas.save();
        if(isLeft) {
            canvas.rotate(90, getWidth() / 2, getHeight() / 2);
        }
        else
        {
        }
        super.onDraw(canvas);
        canvas.restore();
    }
}
