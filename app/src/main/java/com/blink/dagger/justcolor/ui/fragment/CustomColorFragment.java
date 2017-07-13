package com.blink.dagger.justcolor.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blink.dagger.justcolor.ColorApp;
import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.entity.Colors;
import com.blink.dagger.justcolor.util.ToastUtils;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.xw.repo.BubbleSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomColorFragment extends Fragment {

    @Bind(R.id.area_change)
    LinearLayout areaChange;
    @Bind(R.id.seekbar_red)
    BubbleSeekBar seekbarRed;
    @Bind(R.id.seekbar_green)
    BubbleSeekBar seekbarGreen;
    @Bind(R.id.seekbar_blue)
    BubbleSeekBar seekbarBlue;
    @Bind(R.id.generate_color)
    Button generateColor;
    @Bind(R.id.color_value)
    TextView calorValue;

    //记录颜色采样值,默认黑色
    String r = "00";
    String g = "00";
    String b = "00";
    String rgb = "#000000";

    public static CustomColorFragment getInstance() {
        return new CustomColorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_color, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
    }

    private void initListener() {
        //解决与viewpager的冲突
        areaChange.setOnTouchListener((v, event) -> {
            areaChange.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        seekbarRed.setOnTouchListener((v, event) -> {
            areaChange.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        seekbarGreen.setOnTouchListener((v, event) -> {
            areaChange.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        seekbarBlue.setOnTouchListener((v, event) -> {
            areaChange.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        //开始记录颜色采样
        seekbarRed.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                r = Integer.toHexString((int) progressFloat).toUpperCase();
                if (r.length() == 1){
                    r = "0"+r;
                }
                showColor4RGB();
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });

        seekbarGreen.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                g = Integer.toHexString((int) progressFloat).toUpperCase();
                if (g.length() == 1){
                    g = "0"+g;
                }
                showColor4RGB();
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });

        seekbarBlue.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                b = Integer.toHexString((int) progressFloat).toUpperCase();
                if (b.length() == 1){
                    b = "0"+b;
                }
                showColor4RGB();
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void showColor4RGB(){
        rgb = "#"+r+g+b;
        generateColor.setBackgroundColor(Color.parseColor(rgb));
        calorValue.setText("#"+r+g+b);
        calorValue.setTextColor(Color.parseColor(rgb));
    }

    @OnClick(R.id.generate_color)
    public void onClick() {
        Colors colors = new Colors(rgb,0,0);
        colors.isCollection = 1;
        ColorApp.db.insert(colors, ConflictAlgorithm.Abort);
        ToastUtils.showShort(getContext(),"以成功为小主生成了此颜色哦!(●'◡'●)");
    }
}
