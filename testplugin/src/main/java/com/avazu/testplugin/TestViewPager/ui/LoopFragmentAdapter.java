package com.avazu.testplugin.TestViewPager.ui;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jeden on 2017/3/15.
 */

public abstract  class LoopFragmentAdapter extends PagerAdapter{
    private PagerAdapter mAdapter;
    private SparseArray<LoopPagerAdapterWrapper.ToDestroy> mToDestroy = new SparseArray<>();

    public LoopFragmentAdapter(PagerAdapter adapter){
        this.mAdapter = adapter;
    }

    private boolean isFakePosition(int position) {
        return toRealPosition(position) == position - 2;
    }

    public int toRealPosition(int position) {
        int oldDataCount = getRealCount();
        if (position == 1) {
            return oldDataCount - 1;
        } else if (position == 0) {
            return oldDataCount - 2;
        } else if (position == oldDataCount + 2) {
            return 0;
        } else if (position == oldDataCount + 3) {
            return 1;
        }
        return position - 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        LoopPagerAdapterWrapper.ToDestroy toDestroy = mToDestroy.get(position);
        if (toDestroy != null) {
            mToDestroy.remove(position);
            return toDestroy.object;
        }
        return mAdapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = toRealPosition(position);

        if (isFakePosition(position)) {
            mToDestroy.put(position, new LoopPagerAdapterWrapper.ToDestroy(container, realPosition, object));
        } else {
            mAdapter.destroyItem(container, realPosition, object);
        }
    }

    public int getRealCount() {
        return mAdapter.getCount();
    }

    @Override
    public int getCount() {
        return getRealCount() + 4;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        mAdapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return mAdapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        mAdapter.startUpdate(container);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        public ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }
}
