package com.blink.dagger.justcolor.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.adapter.HomePagerAdapter;
import com.blink.dagger.justcolor.ui.fragment.CollectionFragment;
import com.blink.dagger.justcolor.ui.fragment.CustomColorFragment;
import com.blink.dagger.justcolor.ui.fragment.PorpularColorFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tab_navi)
    TabLayout tabNavi;
    @Bind(R.id.pager_content)
    ViewPager pagerContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTable();
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