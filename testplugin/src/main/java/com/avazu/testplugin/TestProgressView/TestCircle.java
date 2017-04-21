package com.avazu.testplugin.TestProgressView;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.avazu.testplugin.R;

/**
 * Created by jeden on 2017/4/21.
 */

public class TestCircle extends View {
    private ValueAnimator mMainAnimator;
    private Paint mArcPaint;
    private int mCenterX;
    private int mCenterY;
    private RectF mRectF;
    private float mStartAngle = 270;
    private Drawable mDrawable;

    public TestCircle(Context context) {
        super(context);
        initView(context);
    }

    public TestCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TestCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mArcPaint = new Paint();
        mArcPaint.setAlpha(140);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(30);

        mDrawable = context.getResources().getDrawable(R.drawable.clean_rubbish);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
        mRectF = new RectF(mCenterX - 100, mCenterY - 100, mCenterX + 100, mCenterY + 100);

        int colors[] = new int[3];
        colors[0] = Color.parseColor("#5DD964");
        colors[1] = Color.parseColor("#A5F717");
        colors[2] = Color.parseColor("#5DD964");

        SweepGradient lg = new SweepGradient(mCenterX, mCenterY, colors, new float[]{0.0f, 0.5f, 1.0f});
        mArcPaint.setShader(lg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(mStartAngle, mCenterX, mCenterY);
        canvas.drawArc(mRectF, mStartAngle, 360f, false, mArcPaint);
        canvas.restore();

        for(int i = 0; i < 5; i++) {
            int centerX = 80 * i;
            int centerY = 60 * i;
            canvas.save();
            canvas.rotate(60 * i, centerX, centerY);
            mDrawable.setBounds(centerX - 20, centerY - 25, centerX + 20, centerY + 25);
            mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    private void startAnim() {
        mMainAnimator = generateAnimator(0.0f, 1.0f, 20000, 1500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mStartAngle = 270 + value * 4 * 360;
                mStartAngle = mStartAngle % 360;
                invalidate();
            }
        });
        mMainAnimator.start();
    }

    public ValueAnimator generateAnimator(float from, float to, long duration, long delay,
                                          TimeInterpolator interpolator, ValueAnimator.AnimatorUpdateListener listener) {
        ValueAnimator va = ValueAnimator.ofFloat(from, to);
        va.setDuration(duration);
        va.setStartDelay(delay);
        va.setInterpolator(interpolator);
        va.addUpdateListener(listener);
        return va;
    }
}
