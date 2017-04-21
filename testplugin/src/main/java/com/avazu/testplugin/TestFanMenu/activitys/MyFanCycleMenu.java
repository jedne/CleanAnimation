package com.avazu.testplugin.TestFanMenu.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avazu.testplugin.R;
import com.avazu.testplugin.TestFanMenu.menu.MyCustomMenuManager;

public class MyFanCycleMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fan_cycle_menu);

        initView();
    }

    public void initView(){
        Button btn = (Button)findViewById(R.id.myfan_show_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyCustomMenuManager(MyFanCycleMenu.this, getWindowManager()).showFanMenu();
            }
        });
    }
}
