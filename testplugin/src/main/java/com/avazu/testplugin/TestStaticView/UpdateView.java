package com.avazu.testplugin.TestStaticView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avazu.testplugin.R;

/**
 * Created by jeden on 2017/4/13.
 */

public class UpdateView extends BaseStuff<GlobalProvider.LockerUpdateView> {

    private Drawable mIcon;
    public UpdateView(Drawable icon) {
        mIcon = icon;
    }
    @Override
    public void checkInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateShowView();
            }
        }).start();
    }

    @Override
    public GlobalProvider.LockerUpdateView getContent() {
        long time = System.currentTimeMillis();
        Log.v("jeden", "getContent current time:" + time);
        return new GlobalProvider.LockerUpdateView()
                .setViewId(R.layout.layout_update_item)
                .setBtnStr("更新")
                .setIcon(mIcon)
                .setTitle("更新提示")
                .setSubTitle("最新版本强势来袭，赶快体验！" + time);
    }

    @Override
    public void contentClicked(Context context, View view) {
        // context.startActivity();
        Toast.makeText(context, "在锁屏上点击了更新按钮", Toast.LENGTH_SHORT).show();
    }
}
