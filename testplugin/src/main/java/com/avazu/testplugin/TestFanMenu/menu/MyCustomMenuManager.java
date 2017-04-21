package com.avazu.testplugin.TestFanMenu.menu;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.avazu.testplugin.R;

/**
 * Created by Administrator on 2017/3/12.
 */

public class MyCustomMenuManager {
    private Context mContext;
    private WindowManager mWindowManager;
    private LayoutInflater mLayoutInflater;
    private View mRootView;

    public MyCustomMenuManager(Context context, WindowManager wm){
        this.mContext = context;
        this.mWindowManager = wm;

        this.mLayoutInflater = LayoutInflater.from(context);
        initView();
    }

    public void initView(){

        mRootView = mLayoutInflater.inflate(R.layout.my_custom_fan_menu, null);
    }

    private WindowManager.LayoutParams generateLP(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        return lp;
    }

    public void showFanMenu(){

        if(mRootView == null) {
            return;
        }
        if(mRootView.getParent() != null) {
            return;
        }

        mWindowManager.addView(mRootView, generateLP());
    }
}
