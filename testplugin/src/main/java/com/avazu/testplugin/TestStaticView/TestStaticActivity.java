package com.avazu.testplugin.TestStaticView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.avazu.testplugin.R;

public class TestStaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_static);

        initView();
    }

    private void initView() {
        Button btn1 = (Button) findViewById(R.id.test_static_goto);
        Button btn2 = (Button) findViewById(R.id.test_static_add);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestStaticActivity.this, TestStaticViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalProvider.getLockerUpdateInstance().setBaseView(new UpdateView(getResources().getDrawable(R.drawable.google_my_love_test)));
            }
        });
    }
}
