package com.blink.dagger.justcolor.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import static android.view.View.GONE;


/**
 * Created by lucky on 16-8-1.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerAdapter.ViewHolder>{
    private Context context;
    private int layoutResId;
    private List<T> data;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    public CommonRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        this.context=context;
        this.layoutResId=layoutResId;
        this.data=data;

    }
    public CommonRecyclerAdapter(Context context, int layoutResId) {
        this(context,layoutResId,null);

    }
    //用于回调子view的点击
    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int position);
    }
    //设置listener
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layoutResId,parent,false);
        final ViewHolder holder=new ViewHolder(context,view);
        if (onRecyclerViewItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, data.get(position),position);
    }
    //在具体的adapter中实现此抽象方法即可
    protected abstract void convert(ViewHolder holder, T t, int position) ;

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> views;
        private final Context context;
        private View convertView;
        //将holder中的子view存储至SparseArray中
        protected ViewHolder(Context context,View itemView) {
            super(itemView);
            this.context=context;
            this.convertView=itemView;
            this.views=new SparseArray<View>();
        }
        //每次操作子View前，执行此方法，若该子view已在SparseArray中直接返回，如不存在则实例化它并将其存放至SparseArray
        protected <T extends View> T retrieveView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }
        //处理子View (根据具体情况还可增加setChecked,setTag,setvisible等方法)
        public ViewHolder setText(int viewId, String value) {
            TextView view = retrieveView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setEditText(int viewId, String value) {
            EditText view = retrieveView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setCharSequence(int viewId, CharSequence value) {
            TextView view = retrieveView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = retrieveView(viewId);
            view.setImageResource(imageResId);
            return this;
        }

        public ViewHolder setBackgroundColor(int viewId, int color) {
            View view = retrieveView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public void setOnClickListener(int viewId, View.OnClickListener listener){
            View view = retrieveView(viewId);
            view.setOnClickListener(listener);
        }

        public void setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener){
            CheckBox view = retrieveView(viewId);
            view.setOnCheckedChangeListener(listener);
        }

        public void addTextChangeListener(int viewId, TextWatcher watcher){
            EditText view = retrieveView(viewId);
            view.addTextChangedListener(watcher);
        }

        public void setViewVisible(int viewId, boolean flag){
            View view = retrieveView(viewId);
            view.setVisibility(flag ? View.VISIBLE:GONE);
        }

        public ViewHolder setViewTag(int viewId, int position){
            View view = retrieveView(viewId);
            view.setTag(position);
            return  this;
        }

        public int getViewTag(int viewId){
            View view = retrieveView(viewId);
            return (int) view.getTag();
        }

    }
}