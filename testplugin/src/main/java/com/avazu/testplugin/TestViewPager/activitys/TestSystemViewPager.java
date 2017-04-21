package com.avazu.testplugin.TestViewPager.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avazu.testplugin.R;
import com.avazu.testplugin.TestViewPager.fragments.TestViewPagerFragment;
import com.avazu.testplugin.TestViewPager.ui.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class TestSystemViewPager extends AppCompatActivity {

    private List<Fragment> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_system_view_pager);
        initData();
        init();
    }

    public void initData(){
        mDatas = new ArrayList<>();
        for(int i = 0; i < 24; i++) {
            mDatas.add(TestViewPagerFragment.getInstance("system page " + changePosition(20, i)));
        }
    }

    public void init(){

        final ViewPager vp = (ViewPager)findViewById(R.id.test_system_view_pager);
        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        });

        vp.setCurrentItem(1);
        vp.setOffscreenPageLimit(mDatas.size());
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    vp.setCurrentItem(mDatas.size() - 3, false);
                } else if (position == 0) {
                    vp.setCurrentItem(mDatas.size() - 4, false);
                }else if(position == mDatas.size() - 2) {
                    vp.setCurrentItem(2, false);
                } else if(position == mDatas.size() - 1) {
                    vp.setCurrentItem(3, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int changePosition(int mOldDataCount, int position) {
        if (position == 1) {
            return mOldDataCount - 1;
        } else if (position == 0) {
            return mOldDataCount - 2;
        } else if (position == mOldDataCount + 2) {
            return 0;
        } else if (position == mOldDataCount + 3) {
            return 1;
        }
        return position - 2;
    }
}
