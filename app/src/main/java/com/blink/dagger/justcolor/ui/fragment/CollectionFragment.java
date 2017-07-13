package com.blink.dagger.justcolor.ui.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.blink.dagger.justcolor.ColorApp;
import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.adapter.CommonRecyclerAdapter;
import com.blink.dagger.justcolor.entity.Colors;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    @Bind(R.id.swipe_collection)
    SwipeRefreshLayout swipeCollection;
    @Bind(R.id.rv_collection)
    RecyclerView rvCollection;

    List<Colors> colorsData = new ArrayList<>();
    CommonRecyclerAdapter<Colors> colorsAdapter;

    PopupWindow colorPreview;

    public static CollectionFragment getInstance() {
        return new CollectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initData();
        initListener();
    }

    /**
     * setup view
     */
    private void initViews() {
        rvCollection.setLayoutManager(new GridLayoutManager(getContext(), 2));
        colorsAdapter = new CommonRecyclerAdapter<Colors>(getContext(), R.layout.item_color_popular, colorsData) {
            @Override
            protected void convert(ViewHolder holder, Colors colors, int position) {
                holder.setBackgroundColor(R.id.color_content, Color.parseColor(colors.rgb));
                holder.setText(R.id.color_value, colors.rgb);
                holder.setImageResource(R.id.color_collect, colors.isCollection == 0 ? R.drawable.ic_uncollect : R.drawable.ic_collect);
                holder.setOnClickListener(R.id.color_collect, v -> {
                    colors.isCollection = 0;
                    ColorApp.db.update(colors);
                    colorsData.remove(position);
                    colorsAdapter.notifyItemRemoved(position);
                    colorsAdapter.notifyItemRangeChanged(position,colorsData.size()-position);
                });
            }
        };
        rvCollection.setAdapter(colorsAdapter);
    }

    /**
     * 加载数据
     */
    private void initData() {
        colorsData.clear();
        colorsData.addAll(ColorApp.db.query(new QueryBuilder<Colors>(Colors.class)
                .where("isCollection = 1")));
        colorsAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        swipeCollection.setOnRefreshListener(() -> {
            initData();
            if (swipeCollection.isRefreshing()){
                swipeCollection.postDelayed(() -> {
                    swipeCollection.setRefreshing(false);
                },1000);
            }
        });

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
            colorPreview.showAtLocation(rvCollection, Gravity.CENTER,0,0);
        });
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
