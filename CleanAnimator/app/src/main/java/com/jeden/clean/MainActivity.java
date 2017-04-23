package com.jeden.clean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private MyProgressView myProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jeden.clean.R.layout.activity_main);

        initView();
    }

    private void initView() {
        myProgressView = (MyProgressView) findViewById(R.id.clean_progress_view);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        int width = getWindow().getDecorView().getWidth();
//        int height = getResources().getDimensionPixelSize(R.dimen.clean_circle_view_height);
//        RubbishFactory factory = new RubbishFactory();
//        myProgressView.setRubbishes(factory.generateRubbishs(20, width, height));
    }
}
