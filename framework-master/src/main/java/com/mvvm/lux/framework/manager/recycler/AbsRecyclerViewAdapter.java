package com.mvvm.lux.framework.manager.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * RecycleViewAdapter基类
 */
public abstract class AbsRecyclerViewAdapter<T> extends
        RecyclerView.Adapter<AbsRecyclerViewAdapter.ClickableViewHolder> {

    private List<T> datas;
    private int resLayout;
    private Context context;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    private List<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();

    public AbsRecyclerViewAdapter(RecyclerView recyclerView, @IdRes int resLayout, List<T> datas) {
        this.resLayout = resLayout;
        this.datas = datas;
        initListener(recyclerView);
    }

    private void initListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {

                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrollStateChanged(rv, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {

                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrolled(rv, dx, dy);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(final ClickableViewHolder holder, final int position) {
        convert(holder, datas.get(position), position);
        holder.getParentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position, holder);
                }
            }
        });
        holder.getParentView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return itemLongClickListener != null
                        && itemLongClickListener.onItemLongClick(position, holder);
            }
        });
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(resLayout, parent, false);
        return new ClickableViewHolder(view);
    }

    public abstract void convert(ClickableViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mListeners.add(listener);
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, ClickableViewHolder holder);
    }

    interface OnItemLongClickListener {
        boolean onItemLongClick(int position, ClickableViewHolder holder);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public void bindContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public static class ClickableViewHolder extends RecyclerView.ViewHolder {

        private View parentView;

        public ClickableViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;
        }

        View getParentView() {
            return parentView;
        }

        public <T extends View> T $(@IdRes int id) {
            return (T) parentView.findViewById(id);
        }
    }
}