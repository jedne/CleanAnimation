package com.avazu.testplugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avazu.testplugin.TestFanMenu.activitys.MyFanCycleMenu;
import com.avazu.testplugin.TestProgressView.TestProgressActivity;
import com.avazu.testplugin.TestShake.ShakeActivity;
import com.avazu.testplugin.TestStaticView.TestStaticActivity;
import com.avazu.testplugin.TestViewPager.activitys.TestViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "jeden";
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView() {
        Button btn1 = (Button) findViewById(R.id.test_draw_image);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btn2 = (Button) findViewById(R.id.test_fan_menu);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyFanCycleMenu.class);
                startActivity(intent);
            }
        });

        Button btn3 = (Button) findViewById(R.id.test_shake);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShakeActivity.class);
                startActivity(intent);
            }
        });

        Button btn4 = (Button) findViewById(R.id.test_view_pager);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestViewPagerActivity.class);
                startActivity(intent);
            }
        });

        Button btn5 = (Button) findViewById(R.id.test_static_view);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestStaticActivity.class);
                startActivity(intent);
            }
        });

        Button btn6 = (Button) findViewById(R.id.test_progress_view);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestProgressActivity.class);
                startActivity(intent);
            }
        });
    }
}
