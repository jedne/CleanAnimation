package com.avazu.testplugin.TestViewPager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avazu.testplugin.R;
import com.avazu.testplugin.TestViewPager.ui.MyLoopViewPager;

public class TestViewPagerFragment extends Fragment
{
    public Object fragmentData;

    public static TestViewPagerFragment getInstance(Object data) {
        TestViewPagerFragment fragment = new TestViewPagerFragment();
        fragment.fragmentData = data;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_view_pager, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.fragment_textview);
        tv.setText("this is fragment " + fragmentData);
        scaleFragmentView(1.0f);
    }

    public void scaleFragmentView(Float offset){
        View view = this.getView();
        Float scale = 1 - MyLoopViewPager.mMaxScaleOffset * offset;
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
