package com.avazu.testplugin.TestViewPager.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.avazu.testplugin.R;
import com.avazu.testplugin.TestViewPager.fragments.TestViewPagerFragment;
import com.avazu.testplugin.TestViewPager.ui.MyLoopViewPager;

import java.util.ArrayList;
import java.util.List;

public class TestViewPagerActivity extends AppCompatActivity implements View.OnTouchListener{

    private static final String TAG = "jeden";
    private List<Object> fragmentDataList;
    private MyLoopViewPager vp;

    private int currentpage;
    private int viewpagerstate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);

        initData();
        initView();
    }

    public void initData() {

        fragmentDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
//            if (i == 0) {
//                fragmentDataList.add(TestViewPagerFragment.getInstance(19));
//            } else if (i == 21) {
//                fragmentDataList.add(TestViewPagerFragment.getInstance(0));
//            } else {
//                fragmentDataList.add(TestViewPagerFragment.getInstance(i - 1));
//            }
            fragmentDataList.add("page" + i);
        }
    }

    public void initView() {
        vp = (MyLoopViewPager) findViewById(R.id.test_view_pager);
//        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragmentList.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//        };
        vp.setViewPagerListData(fragmentDataList, getSupportFragmentManager(), new MyLoopViewPager.MyCycleAdapterCallBack() {
            @Override
            public Fragment getItem(Object position) {
                return TestViewPagerFragment.getInstance(position);
            }
        });


//        vp.setCurrentItem(1);
//        vp.setOffscreenPageLimit(22);
//        vp.setOnTouchListener(this);
//        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.v(TAG, "onPageScrolled position:" + position + "positionOffset:" + positionOffset
//                        + "positionOffsetPixels:" + positionOffsetPixels + "moveState:" + 0
//                        + "currentposition" + currentpage);
//                if(positionOffset == 0 && positionOffsetPixels == 0){
//                    if (position == 0) {
//                        vp.setCurrentItem(20, false);
//                        return;
//                    } else if (position == 21) {
//                        vp.setCurrentItem(1, false);
//                        return;
//                    }
//
//                    if(viewpagerstate != 1)
//                    {
//                        ((TestViewPagerFragment)fragmentList.get(position - 1)).scaleFragmentView(1.0f);
//                        ((TestViewPagerFragment)fragmentList.get(position)).scaleFragmentView(0.0f);
//                        ((TestViewPagerFragment)fragmentList.get(position + 1)).scaleFragmentView(1.0f);
//                    }
//                }
//                else{
//                    float tempOffset = Math.abs(position - currentpage + positionOffset);
//                    ((TestViewPagerFragment)fragmentList.get(currentpage == position ? position + 1 : position)).scaleFragmentView(1 - tempOffset);
//                    ((TestViewPagerFragment)fragmentList.get(currentpage)).scaleFragmentView(tempOffset);
//                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.v(TAG, "onPageSelected position:" + position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Log.v(TAG, "oonPageScrollStateChanged  state:" + state);
//                viewpagerstate = state;
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int w = getWindow().getDecorView().getWidth();
        int w2 = vp.getWidth();
        Log.v(TAG, "onWindowFocusChanged w:" + w + "w2:" + w2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentpage = vp.getCurrentItem();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return false;
    }
}
