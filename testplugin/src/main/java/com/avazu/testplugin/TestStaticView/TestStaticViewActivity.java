package com.avazu.testplugin.TestStaticView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avazu.testplugin.R;

public class TestStaticViewActivity extends AppCompatActivity {
    private RelativeLayout mViewGroup;
    private BaseStuff mBaseStuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_static_view);

        initView();
    }

    private void initView() {
        mViewGroup = (RelativeLayout) findViewById(R.id.test_static_view_content_layout);

        mBaseStuff = GlobalProvider.getLockerUpdateInstance().getBaseView();

        if(mBaseStuff == null) {
            return;
        }

        mBaseStuff.setViewListener(new BaseStuff.ProviderChangeListener() {
            @Override
            public void onChanged() {
                handleShow();
            }
        });

        mBaseStuff.checkInit();
    }

    private void handleShow() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GlobalProvider.LockerUpdateView uv = (GlobalProvider.LockerUpdateView) mBaseStuff.getContent();
                if(uv == null || mBaseStuff == null) {
                    return;
                }

                getLayoutInflater().inflate(uv.getViewId(), mViewGroup);

                ImageView iconView = (ImageView) mViewGroup.findViewById(R.id.update_icon);
                TextView titleView = (TextView) mViewGroup.findViewById(R.id.update_title_view);
                TextView subView = (TextView) mViewGroup.findViewById(R.id.update_subtitle_view);
                Button btn = (Button) mViewGroup.findViewById(R.id.update_down_btn);

                iconView.setBackground(uv.getIcon());
                titleView.setText(uv.getTitle());
                subView.setText(uv.getSubTitle());
                btn.setText(uv.getBtnStr());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBaseStuff.contentClicked(getApplicationContext(), v);
                    }
                });

                mViewGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        GlobalProvider.getLockerUpdateInstance().onRealse();
    }
}
