package com.blink.dagger.justcolor.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.util.ColorDbUtil;
import com.blink.dagger.justcolor.util.PreferencesUtils;

public class WelcomeActivity extends AppCompatActivity {

    //是否是第一次启动
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        isFirst = PreferencesUtils.getValueOfSharedPreferences(this, "isFirst", true);
        initDB();
        Handler handler = new Handler();
        if (isFirst){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoMain();
                }
            },1200);
        }else {
            gotoMain();
        }
    }

    private void gotoMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 第一次进入app时初始化颜色库数据
     */
    private void initDB() {
        if (isFirst){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ColorDbUtil.initColor();
                }
            }).start();
            PreferencesUtils.addToSharedPreferences(WelcomeActivity.this,"isFirst",false);
        }
    }
}
