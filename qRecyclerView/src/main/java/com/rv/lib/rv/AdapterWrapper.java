package com.rv.lib.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @Description : recyclerview adapter 的包装adapter
 * @Author : wdk
 * @CretaTime : 2019/4/2 17:15
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/2 17:15
 * @LastCheckBy :wdk
 */
public class AdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private LayoutInflater mInflater;
    private RecyclerView.Adapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    AdapterWrapper(Context context, RecyclerView.Adapter adapter) {
        this.mInflater = LayoutInflater.from(context);
        this.mAdapter = adapter;
    }

    /**
     * 获取到项目中传给recyclerview的adapter
     *
     * @return adapter
     */
    public RecyclerView.Adapter getOriginAdapter() {
        return mAdapter;
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getContentItemCount() + getFooterCount();
    }

    private int getContentItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            //返回集合中元素的key，在onCreateViewHolder中用到
            return mHeaderViews.keyAt(position);
        } else if (isFooter(position)) {
            return mFootViews.keyAt(position - getHeaderCount() - getContentItemCount());
        }
        return mAdapter.getItemViewType(position - getHeaderCount());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //headView
        View contentView = mHeaderViews.get(viewType);
        if (contentView != null) {
            return new ViewHolder(contentView);
        }

        //footView
        contentView = mFootViews.get(viewType);
        if (contentView != null) {
            return new ViewHolder(contentView);
        }

        //自定义adapter中的条目
        final RecyclerView.ViewHolder viewHolder = mAdapter.onCreateViewHolder(viewGroup, viewType);

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (isHeaderOrFooter(holder)) return;

        position -= getHeaderCount();
        mAdapter.onBindViewHolder(holder, position, payloads);
    }


    /**
     * 头布局 脚布局
     */

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public boolean isHeaderOrFooter(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) return true;

        return isHeaderOrFooter(holder.getAdapterPosition());
    }

    public boolean isHeaderOrFooter(int position) {
        return isHeader(position) || isFooter(position);
    }

    public boolean isHeader(int position) {
        return position >= 0 && position < getHeaderCount();
    }

    public boolean isFooter(int position) {
        return position >= getHeaderCount() + getContentItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(getHeaderCount() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addHeaderViewAndNotify(View view) {
        addHeaderView(view);
        notifyItemInserted(getHeaderCount() - 1);
    }

    public void removeHeaderViewAndNotify(View view) {
        int headerIndex = mHeaderViews.indexOfValue(view);
        if (headerIndex == -1) return;

        mHeaderViews.removeAt(headerIndex);
        notifyItemRemoved(headerIndex);
    }

    public void addFooterView(View view) {
        mFootViews.put(getFooterCount() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public void addFooterViewAndNotify(View view) {
        addFooterView(view);
        notifyItemInserted(getHeaderCount() + getContentItemCount() + getFooterCount() - 1);
    }

    public void removeFooterViewAndNotify(View view) {
        int footerIndex = mFootViews.indexOfValue(view);
        if (footerIndex == -1) return;

        mFootViews.removeAt(footerIndex);
        notifyItemRemoved(getHeaderCount() + getContentItemCount() + footerIndex);
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFootViews.size();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
