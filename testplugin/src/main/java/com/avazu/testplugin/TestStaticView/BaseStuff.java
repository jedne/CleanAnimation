package com.avazu.testplugin.TestStaticView;

import android.content.Context;
import android.view.View;

/**
 * Created by jeden on 2017/4/13.
 */

public abstract class BaseStuff<T> {
    private ProviderChangeListener mListener;

    public abstract void checkInit();
    public abstract T getContent();
    public abstract void contentClicked(Context context, View view);

    public void updateShowView() {
        if(mListener != null) {
            mListener.onChanged();
        }
    }

    public void setViewListener(ProviderChangeListener listener) {
        mListener = listener;
    }

    public interface ProviderChangeListener {
        void onChanged();
    }
}
