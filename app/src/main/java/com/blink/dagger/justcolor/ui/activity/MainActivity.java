package com.blink.dagger.justcolor.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.adapter.HomePagerAdapter;
import com.blink.dagger.justcolor.entity.Colors;
import com.blink.dagger.justcolor.ui.fragment.CollectionFragment;
import com.blink.dagger.justcolor.ui.fragment.CustomColorFragment;
import com.blink.dagger.justcolor.ui.fragment.PorpularColorFragment;
import com.blink.dagger.justcolor.util.ColorDbUtil;
import com.blink.dagger.justcolor.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tab_navi)
    TabLayout tabNavi;
    @Bind(R.id.pager_content)
    ViewPager pagerContent;

    //是否是第一次启动
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isFirst = PreferencesUtils.getValueOfSharedPreferences(this, "isFirst", true);
        initDB();
        initTable();
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
            PreferencesUtils.addToSharedPreferences(MainActivity.this,"isFirst",false);
        }
    }

    /**
     * 初始化tab导航与viewpager
     */
    private void initTable() {
        List<String> title = new ArrayList<>();
        title.add("色彩飞扬");
        title.add("有颜任性");
        title.add("我的收藏");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PorpularColorFragment.getInstance());
        fragments.add(CustomColorFragment.getInstance());
        fragments.add(CollectionFragment.getInstance());
        pagerContent.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),fragments,title));
        tabNavi.setupWithViewPager(pagerContent);
    }
}