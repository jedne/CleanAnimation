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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.avazu.testplugin.R;

/**
 * Created by jeden on 2017/4/20.
 */

public class MyProgressView extends View {
    private static final String TAG = MyProgressView.class.getSimpleName();
    private int mTotalTime = 2000;
    private Paint mArcPaint;
    private Paint mCirclePaint;
    private TextPaint mTitlePaint;
    private TextPaint mSubPaint;

    private int mCenterX;
    private int mCenterY;
    private int mHalfWidth = 300;
    private float mCircleRadius = 80;
    private float mTitleBottom;
    private float mTitleLeft;
    private float mSubLeft;
    private float mSubBottom;

    private float mArcBorderWidth = 200;

    private float mCircleBorderWidth = 5;
    private float mStartAngle = 270;
    private float mEndAngle = 40;
    private RectF mRectF;

    private ValueAnimator mMainAnimator;
    private ValueAnimator mArcAnimator;
    private ValueAnimator mAngleAnimator;
    private ValueAnimator mCircleAnimator;
    private ValueAnimator mColorAnimator;
    private ValueAnimator mTitleAnimator;
    private ValueAnimator mSubAnimator;
    private boolean mInited = false;

    private String mTitle = "100%";
    private String mSubTitle = "Memory Usage";
    private Drawable mCenterBg;
    private boolean mCenterBgShow = false;

    public MyProgressView(Context context) {
        super(context);
        initView(context);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mArcPaint = new Paint();
//        mArcPaint.setColor(Color.GREEN);
        mArcPaint.setAlpha(20);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcBorderWidth);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setAlpha(0);
        mCirclePaint.setColor(Color.parseColor("#00445A3F"));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleBorderWidth);

        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(Color.parseColor("#6DE053"));
        mTitlePaint.setTextSize(180);
        mTitlePaint.setAlpha(0);
        mTitlePaint.setFakeBoldText(true);

        mSubPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSubPaint.setColor(Color.parseColor("#DCDCDC"));
        mSubPaint.setTextSize(50);
        mSubPaint.setAlpha(0);

        mCenterBg = context.getResources().getDrawable(R.drawable.progress_center_light_bg);
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
        mRectF = new RectF(mCenterX - mHalfWidth, mCenterY - mHalfWidth, mCenterX + mHalfWidth, mCenterY + mHalfWidth);

        mTitleLeft = mCenterX - mTitlePaint.measureText(mTitle) / 2;
        mSubLeft = mCenterX - mSubPaint.measureText(mSubTitle) / 2;
        mTitleBottom = mCenterY - (mTitlePaint.descent() + mTitlePaint.ascent()) / 2;
        mSubBottom = mTitleBottom - (mSubPaint.descent() + mSubPaint.ascent()) + 50;

        int colors[] = new int[3];
        colors[0] = Color.parseColor("#5DD964");
        colors[1] = Color.parseColor("#A5F717");
        colors[2] = Color.parseColor("#5DD964");

        SweepGradient lg = new SweepGradient(mCenterX, mCenterY, colors, new float[]{0.0f, 0.5f, 1.0f});
        mArcPaint.setShader(lg);

        mCenterBg.setBounds(mCenterX - mHalfWidth - 90, mCenterY - mHalfWidth - 90, mCenterX + mHalfWidth + 90, mCenterY + mHalfWidth + 90);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mCirclePaint);
        if(mEndAngle == 360) {
            canvas.save();
            canvas.rotate(mStartAngle, mCenterX, mCenterY);
            canvas.drawArc(mRectF, mStartAngle, mEndAngle, false, mArcPaint);
            canvas.restore();
        }
        else {
            canvas.drawArc(mRectF, mStartAngle, mEndAngle, false, mArcPaint);
        }
        canvas.drawText(mTitle, mTitleLeft, mTitleBottom, mTitlePaint);
        canvas.drawText(mSubTitle, mSubLeft, mSubBottom, mSubPaint);

        if(mCenterBgShow) {
            mCenterBg.draw(canvas);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    public void startAnimation() {
        if(!mInited) {
            initAnimation();
        }

        mMainAnimator.start();
        mAngleAnimator.start();
        mArcAnimator.start();
        mCircleAnimator.start();
        mTitleAnimator.start();
        mSubAnimator.start();
        mColorAnimator.start();
    }

    public void stopAnimation() {
        if(mInited) {
            mMainAnimator.cancel();
            mAngleAnimator.cancel();
            mArcAnimator.cancel();
            mCircleAnimator.cancel();
            mTitleAnimator.cancel();
            mSubAnimator.cancel();
            mColorAnimator.cancel();
            mInited = false;
        }
    }

    public void initAnimation() {

        mMainAnimator = generateAnimator(0.0f, 1.0f, mTotalTime, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mStartAngle = 270 + value * 4 * 360;
                mStartAngle = mStartAngle % 360;
                invalidate();
            }
        });

        mAngleAnimator = generateAnimator(0.0f, 1.0f, mTotalTime / 2, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mEndAngle = 40 + value * 320;
                mArcPaint.setAlpha((int)(20.4 + value * 171));
                if(value > 0.9) {
                    mCenterBg.setAlpha((int)((value - 0.9) * 2550));
                    mCenterBgShow = true;
                }
            }
        });

        mArcAnimator = generateAnimator(1.0f, 0.0f, mTotalTime, 500, new DecelerateInterpolator(2), new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                mArcBorderWidth = 180 * value + 20;
                mArcPaint.setStrokeWidth(mArcBorderWidth);
            }
        });

        mCircleAnimator = generateAnimator(0.0f, 1.0f, mTotalTime - 300, 900, new OvershootInterpolator(2.5f), new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCircleRadius = 80 + 220 * value;
                if(value < 1) {
                    mCirclePaint.setAlpha((int)(value * 140));
                } else {
                    mCirclePaint.setAlpha(140);
                }
            }
        });

        mTitleAnimator = generateAnimator(0.0f, 1.0f, mTotalTime, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                if(value > 0.2 && value < 0.4) {
                    if(value <= 0.3) {
                        mTitleBottom -= 3 * value;
                    }
                    mTitlePaint.setAlpha((int)((value - 0.2) / 2 * 2550));
                }
            }
        });

        mSubAnimator = generateAnimator(0.0f, 1.0f, mTotalTime, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                if(value > 0.1 && value < 0.3) {
                    mSubPaint.setAlpha((int)((value - 0.1) / 2 * 2550));
                }

                if(value > 0.2 && value <= 0.3) {
                    mSubBottom -= 3 * value;
                }
            }
        });

        mColorAnimator = generateAnimator(0.0f, 1.0f, mTotalTime - 100, 600, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        mInited = true;

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
