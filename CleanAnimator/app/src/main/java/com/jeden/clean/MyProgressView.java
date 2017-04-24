package com.jeden.clean;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeden on 2017/4/20.
 */

public class MyProgressView extends View {
    private static final String TAG = MyProgressView.class.getSimpleName();
    private static final int CIRCLE_DEGREE = 360;
    private static final int mTotalTime = 2500;
    private static final float CIRCLE_START = 270;
    private static final float ARC_TOTAL_ANGLE = CIRCLE_DEGREE * 4.5f;
    private static final float ARC_START_SWEEP = 40;
    private static final float RUBBISH_SPEED = 500;
    private static final float RUBBISH_ROTATE = 500;

    private Paint mArcPaint;
    private Paint mCirclePaint;
    private TextPaint mTitlePaint;
    private TextPaint mSubPaint;
    private Paint mBitmapPaint;

    private int mCenterX;
    private int mCenterY;
    private int mHalfWidth;
    private float mCircleRadius;
    private float mCircleStartRadius;
    private float mTitleBottom;
    private float mTitleLeft;
    private float mSubLeft;
    private float mSubBottom;
    private int mSubMarginTop;
    private int mCenterBgMargin;
    private float mArcBorderOffsetWidth;
    private float mArcBorderEndWidth;
    private float mArcBorderWidth;

    private float mCircleBorderWidth;
    private float mStartAngle;
    private float mSweepAngle;
    private RectF mRectF;
    private int mArcColors[];

    private ValueAnimator mMainAnimator;
    private ValueAnimator mArcAnimator;
    private ValueAnimator mAngleAnimator;
    private ValueAnimator mCircleAnimator;
    private ValueAnimator mTextAnimator;
    private boolean mInited = false;

    private String mTitle = "100%";
    private String mSubTitle = "Memory Usage";
    private Drawable mCenterBg;
    private boolean mCenterBgShow = false;
    private Bitmap mRubbishBitmap;

    private List<Rubbish> mRubbishes;
    private int mRubbishHalfW;
    private int mRubbishHalfH;

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

        Resources rs = context.getResources();
        mHalfWidth = rs.getDimensionPixelSize(R.dimen.clean_circle_view_half_width);
        mCircleStartRadius = rs.getDimensionPixelSize(R.dimen.clean_circle_view_inner_start_radius);
        mArcBorderWidth = rs.getDimensionPixelSize(R.dimen.clean_circle_view_arc_border_start_width);
        mArcBorderEndWidth = rs.getDimensionPixelSize(R.dimen.clean_circle_view_arc_border_end_width);
        mCircleBorderWidth = rs.getDimensionPixelSize(R.dimen.clean_circle_view_inner_border_width);
        mSubMarginTop = rs.getDimensionPixelSize(R.dimen.clean_circle_view_sub_text_margin_top);
        mCenterBgMargin = rs.getDimensionPixelSize(R.dimen.clean_circle_view_center_bg_margin);
        int titleSize = rs.getDimensionPixelSize(R.dimen.clean_circle_view_per_text_size);
        int subSize = rs.getDimensionPixelSize(R.dimen.clean_circle_view_sub_text_size);
        mRubbishHalfW = rs.getDimensionPixelSize(R.dimen.clean_circle_view_rubbish_half_width);
        mRubbishHalfH = rs.getDimensionPixelSize(R.dimen.clean_circle_view_rubbish_half_height);

        int titleColor = rs.getColor(R.color.clean_circle_view_text_color);
        int subColor = rs.getColor(R.color.clean_circle_view_sub_text_color);
        int arcStartColor = rs.getColor(R.color.clean_circle_view_arc_start_color);
        int arcEndColor = rs.getColor(R.color.clean_circle_view_arc_end_color);
        int innerColor = rs.getColor(R.color.clean_circle_view_inner_color);

        mArcColors = new int[3];
        mArcColors[0] = arcStartColor;
        mArcColors[1] = arcEndColor;
        mArcColors[2] = arcStartColor;

        mArcBorderOffsetWidth = mArcBorderWidth - mArcBorderEndWidth;
        mCircleRadius = mCircleStartRadius;
        mStartAngle = CIRCLE_START;
        mSweepAngle = ARC_START_SWEEP;

        mArcPaint = new Paint();
//        mArcPaint.setColor(Color.GREEN);
        mArcPaint.setAlpha(0);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcBorderWidth);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setAlpha(0);
        mCirclePaint.setColor(innerColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleBorderWidth);

        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(titleColor);
        mTitlePaint.setTextSize(titleSize);
        mTitlePaint.setAlpha(0);
        mTitlePaint.setFakeBoldText(true);

        mSubPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSubPaint.setColor(subColor);
        mSubPaint.setTextSize(subSize);
        mSubPaint.setAlpha(0);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mCenterBg = rs.getDrawable(R.drawable.progress_center_light_bg);
        mRubbishBitmap = ((BitmapDrawable)rs.getDrawable(R.drawable.clean_rubbish)).getBitmap();

        mRectF = new RectF();
        mRubbishes = new RubbishFactory().generateRubbishs(10, 800, 800);
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
        float temp = mHalfWidth - mArcBorderOffsetWidth;
        mRectF.set(mCenterX - temp, mCenterY - temp, mCenterX + temp, mCenterY + temp);

        mTitleLeft = mCenterX - mTitlePaint.measureText(mTitle) / 2;
        mSubLeft = mCenterX - mSubPaint.measureText(mSubTitle) / 2;
        mTitleBottom = mCenterY - (mTitlePaint.descent() + mTitlePaint.ascent()) / 2;
        mSubBottom = mTitleBottom - (mSubPaint.descent() + mSubPaint.ascent()) + mSubMarginTop;

        SweepGradient lg = new SweepGradient(mCenterX, mCenterY, mArcColors, new float[]{0.0f, 0.5f, 1.0f});
        mArcPaint.setShader(lg);

        mCenterBg.setBounds(mCenterX - mHalfWidth - mCenterBgMargin, mCenterY - mHalfWidth - mCenterBgMargin,
                mCenterX + mHalfWidth + mCenterBgMargin, mCenterY + mHalfWidth + mCenterBgMargin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mCirclePaint);
        if(mSweepAngle == CIRCLE_DEGREE) {
            canvas.save();
            canvas.rotate(mStartAngle, mCenterX, mCenterY);
            canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mArcPaint);
            canvas.restore();
        }
        else {
            canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mArcPaint);
        }
        canvas.drawText(mTitle, mTitleLeft, mTitleBottom, mTitlePaint);
        canvas.drawText(mSubTitle, mSubLeft, mSubBottom, mSubPaint);

        if(mCenterBgShow) {
            mCenterBg.draw(canvas);
        }

        drawRubbish(canvas);
    }

    private void drawRubbish(Canvas canvas) {
        int size = mRubbishes.size();

        if(size <= 0) {
            return;
        }
        long currentTime = System.currentTimeMillis();

        for(int i = 0; i < size; i++) {
            Rubbish rb = mRubbishes.get(i);
            getRubbishLocation(rb, currentTime);
            if(rb.isRecycled) {
                continue;
            }

            canvas.save();
            canvas.rotate(rb.locationAngle, mCenterX, mCenterY);

            Matrix matrix = new Matrix();
            matrix.postTranslate(rb.x, rb.y);
            matrix.postRotate(rb.rotateAngle, rb.x, rb.y);
            mBitmapPaint.setAlpha(rb.alpha);
            canvas.drawBitmap(mRubbishBitmap, matrix, mBitmapPaint);

            canvas.restore();
        }
    }

    public void getRubbishLocation(Rubbish rb, long currentTime) {
        if(rb.startTime < mTotalTime) {
            rb.startTime += currentTime;
        }

        long intervalTime = currentTime - rb.startTime;
        if(intervalTime < 0) {
            return;
        }

        float distance = getTwoPointDistance(mCenterX, mCenterY, rb.x, rb.y);
        if(distance < mHalfWidth) {
            rb.isRecycled = new Random().nextInt(2) == 0;

            if(distance < mCircleStartRadius) {
                rb.isRecycled = true;
            }
        }
        if(rb.isRecycled || intervalTime > RUBBISH_SPEED) {
            rb.isRecycled = true;
            return;
        }

        float interval = intervalTime / RUBBISH_SPEED;
        interval = (float) Math.pow(interval, 1);
        rb.x += interval * (mCenterX - rb.x);
        rb.y += interval * (mCenterY - rb.y);
        rb.locationAngle = 450 * intervalTime / RUBBISH_SPEED;
        rb.rotateAngle += 360 * intervalTime / RUBBISH_SPEED;
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

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public void setRubbishes(List<Rubbish> rubbishes) {
        mRubbishes = rubbishes;
    }

    public void startAnimation() {
        if(!mInited) {
            initAnimation();
        }

        mMainAnimator.start();
        mAngleAnimator.start();
        mArcAnimator.start();
        mCircleAnimator.start();
        mTextAnimator.start();
    }

    public void stopAnimation() {
        if(mInited) {
            mMainAnimator.cancel();
            mAngleAnimator.cancel();
            mArcAnimator.cancel();
            mCircleAnimator.cancel();
            mTextAnimator.cancel();
            mInited = false;
        }
    }

    public void initAnimation() {

        mMainAnimator = generateAnimator(0.0f, 1.0f, mTotalTime, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mStartAngle = CIRCLE_START + value * ARC_TOTAL_ANGLE;
                mStartAngle = mStartAngle % CIRCLE_DEGREE;
                invalidate();
            }
        });

        mAngleAnimator = generateAnimator(0.0f, 1.0f, mTotalTime / 2, 500, new AccelerateDecelerateInterpolator(), new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mSweepAngle = ARC_START_SWEEP + value * (CIRCLE_DEGREE - ARC_START_SWEEP);
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

                mArcBorderWidth = mArcBorderOffsetWidth * value + mArcBorderEndWidth;
                mArcPaint.setStrokeWidth(mArcBorderWidth);

                float temp = mHalfWidth - (mArcBorderWidth - mArcBorderEndWidth);
                mRectF.set(mCenterX - temp, mCenterY - temp,
                        mCenterX + temp, mCenterY + temp);
            }
        });

        mCircleAnimator = generateAnimator(0.0f, 1.0f, mTotalTime - 300, 900, new OvershootInterpolator(2.5f), new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCircleRadius = mCircleStartRadius + (mHalfWidth - mCircleStartRadius) * value;
                if(value < 1) {
                    mCirclePaint.setAlpha((int)(value * 140));
                } else {
                    mCirclePaint.setAlpha(140);
                }
            }
        });

        mTextAnimator = generateAnimator(0.0f, 1.0f, mTotalTime, 500, null, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                if(value > 0.2 && value < 0.4) {
                    if(value <= 0.3) {
                        mTitleBottom -= 4 * value;
                    }
                    mTitlePaint.setAlpha((int)((value - 0.2) / 2 * 2550));
                }

                if(value > 0.1 && value < 0.3) {
                    mSubPaint.setAlpha((int)((value - 0.1) / 2 * 2550));
                }

                if(value > 0.2 && value <= 0.3) {
                    mSubBottom -= 4 * value;
                }
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

    public static float getTwoPointDistance(float x1, float y1, float x2, float y2)
    {
        return  (float) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }
}
