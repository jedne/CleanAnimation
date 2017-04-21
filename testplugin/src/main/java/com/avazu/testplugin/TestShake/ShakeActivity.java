package com.avazu.testplugin.TestShake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.avazu.testplugin.R;

public class ShakeActivity extends AppCompatActivity {

    private ShakeUtils mShake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        mShake = new ShakeUtils(this);
        mShake.setOnShakeListener(sShakeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShake.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShake.onPause();
    }

    ShakeUtils.OnShakeListener sShakeListener = new ShakeUtils.OnShakeListener() {
        @Override
        public void onShake() {
            Toast.makeText(ShakeActivity.this, "shake", Toast.LENGTH_SHORT).show();
        }
    };
}
