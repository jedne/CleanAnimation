package com.avazu.testplugin.TestStaticView;

import android.graphics.drawable.Drawable;

/**
 * Created by jeden on 2017/4/13.
 */

public class GlobalProvider {
    private static LockerProvider mLocker;
    public static LockerProvider getLockerUpdateInstance() {
        if(mLocker == null) {
            synchronized (GlobalProvider.class) {
                if(mLocker == null) {
                    mLocker = new LockerProvider();
                }
            }
        }

        return mLocker;
    }

    public static class LockerProvider {
        private BaseStuff mBaseStuff;
        private LockerProvider(){}
        public void setBaseView(BaseStuff baseStuff) {
            mBaseStuff = baseStuff;
        }

        public BaseStuff getBaseView() {
            return mBaseStuff;
        }

        public void onRealse() {
            mBaseStuff = null;
        }
    }

    public static class LockerUpdateView {
        private String title;
        private String subTitle;
        private Drawable icon;
        private String btnStr;
        private int viewId;

        public String getTitle() {
            return title;
        }

        public LockerUpdateView setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public LockerUpdateView setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Drawable getIcon() {
            return icon;
        }

        public LockerUpdateView setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public String getBtnStr() {
            return btnStr;
        }

        public LockerUpdateView setBtnStr(String btnStr) {
            this.btnStr = btnStr;
            return this;
        }

        public int getViewId() {
            return viewId;
        }

        public LockerUpdateView setViewId(int viewId) {
            this.viewId = viewId;
            return this;
        }
    }
}
