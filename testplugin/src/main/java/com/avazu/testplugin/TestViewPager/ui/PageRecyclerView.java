package com.avazu.testplugin.TestViewPager.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PageRecyclerView extends RecyclerView {

    private Context mContext = null;

    private int shortestDistance; // 超过此距离的滑动才有效
    private float downX = 0; // 手指按下的X轴坐标
    private float slideDistance = 0; // 滑动的距离
    private float scrollX = 0; // X轴当前的位置

    private int totalPage = 0; // 总页数
    private int currentPage = 1; // 当前页

    public PageRecyclerView(Context context) {
        this(context, null);
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        defaultInit(context);
    }

    // 默认初始化
    private void defaultInit(Context context) {
        this.mContext = context;
//        setLayoutManager(new MyLinearLayoutManager(
//                mContext, spanRow, MyLinearLayoutManager.HORIZONTAL, false));
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        shortestDistance = getMeasuredWidth() / 3;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (currentPage == totalPage && downX - event.getX() > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                slideDistance = event.getX() - downX;
                if (Math.abs(slideDistance) > shortestDistance) {
                    // 滑动距离足够，执行翻页
                    if (slideDistance > 0) {
                        // 上一页
                        currentPage = currentPage == 1 ? 1 : currentPage - 1;
                    } else {
                        // 下一页
                        currentPage = currentPage == totalPage ? totalPage : currentPage + 1;
                    }
                }
                // 执行滚动
                smoothScrollBy((int) ((currentPage - 1) * (getWidth() - 50) - scrollX), 0);
                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        scrollX += dx;
        super.onScrolled(dx, dy);
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        super.addOnScrollListener(listener);
    }


}