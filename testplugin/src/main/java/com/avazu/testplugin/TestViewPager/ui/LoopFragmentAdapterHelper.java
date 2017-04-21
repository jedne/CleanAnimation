package com.avazu.testplugin.TestViewPager.ui;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeden on 2017/3/15.
 */

public class LoopFragmentAdapterHelper{
    private List<Fragment> mDataList;

    public LoopFragmentAdapterHelper(List<Object> dataList, LoopAdapterCallBack callBack)
    {
        initData(dataList, callBack);
    }

    public void initData(List<Object> dataList, LoopAdapterCallBack callBack)
    {
        if(mDataList != null)
        {
            mDataList.clear();
        }
        else
        {
            mDataList = new ArrayList<>();
        }

        for(int i = 0; i < dataList.size() + 4; i++){
            this.mDataList.add(callBack.getItem(toRealPosition(i, dataList.size())));
        }
    }

    public Fragment getItem(int position) {
        return mDataList.get(position);
    }

    public int getCount() {
        return mDataList.size();
    }

    public interface LoopAdapterCallBack {
        Fragment getItem(Object position);
    }

    public int toRealPosition(int position, int oldDataCount) {
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
}
