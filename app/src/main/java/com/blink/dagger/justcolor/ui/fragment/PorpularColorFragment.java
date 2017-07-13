package com.blink.dagger.justcolor.ui.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blink.dagger.justcolor.ColorApp;
import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.adapter.CommonRecyclerAdapter;
import com.blink.dagger.justcolor.entity.Colors;
import com.blink.dagger.justcolor.util.ToastUtils;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PorpularColorFragment extends Fragment {

    @Bind(R.id.rv_popular)
    RecyclerView rvPopular;
    @Bind(R.id.color_type)
    TextView colorType;
    @Bind(R.id.choose_type)
    ImageView chooseType;
    @Bind(R.id.go_top)
    ImageView goTop;

    List<Colors> colorsData = new ArrayList<>();
    CommonRecyclerAdapter<Colors> colorsAdapter;

    PopupWindow colorPreview;

    int type = 1;


    public static PorpularColorFragment getInstance() {
        return new PorpularColorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_porpular_color, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initData();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * setup view
     */
    private void initViews() {
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext()));
        colorsAdapter = new CommonRecyclerAdapter<Colors>(getContext(), R.layout.item_color_popular, colorsData) {
            @Override
            protected void convert(ViewHolder holder, Colors colors, int position) {
                holder.setBackgroundColor(R.id.color_content, Color.parseColor(colors.rgb));
                holder.setText(R.id.color_value, colors.rgb);
                holder.setImageResource(R.id.color_collect, colors.isCollection == 0 ? R.drawable.ic_uncollect : R.drawable.ic_collect);
                holder.setOnClickListener(R.id.color_collect, v -> {
                    colors.isCollection = (colors.isCollection == 0) ? 1 : 0;
                    ColorApp.db.update(colors);
                    colorsAdapter.notifyItemChanged(position);
                });
            }
        };
        rvPopular.setAdapter(colorsAdapter);
    }

    /**
     * 加载数据
     */
    private void initData() {
        switch (type){
            case 1:
                colorType.setText("清新系");
                break;
            case 2:
                colorType.setText("纯彩系");
                break;
            case 3:
                colorType.setText("其他系");
                break;
        }
        colorsData.clear();
        colorsData.addAll(ColorApp.db.query(new QueryBuilder<Colors>(Colors.class)
                .where("isPopular = 1")
                .whereAppendAnd()
                .where("colorType = "+String.valueOf(type))));
        colorsAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        colorsAdapter.setOnRecyclerViewItemClickListener((view, position) -> {

            if (colorPreview == null) {
                colorPreview = new PopupWindow(getContext().getResources().getDisplayMetrics().widthPixels,getContext().getResources().getDisplayMetrics().heightPixels/2);
                colorPreview.setFocusable(true);
                colorPreview.setBackgroundDrawable(new ColorDrawable());
                colorPreview.setAnimationStyle(R.style.popwindow_anim_style);
            }
//            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_color_preview,null);
            ImageView contentView = new ImageView(getContext());
            contentView.setBackgroundColor(Color.parseColor(colorsData.get(position).rgb));
            colorPreview.setContentView(contentView);
            colorPreview.showAtLocation(rvPopular,Gravity.CENTER,0,0);
        });
    }

    @OnClick({R.id.choose_type, R.id.go_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_type:
                if (type < 2){
                    type ++ ;
                }else if (type == 2){
                    type = 1;
                }
                initData();
                break;
            case R.id.go_top:
                rvPopular.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (colorPreview != null){
            colorPreview.dismiss();
            colorPreview = null;
        }
        ButterKnife.unbind(this);
    }
}
