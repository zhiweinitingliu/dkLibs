package com.rv.lib.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rv.lib.R;

/**
 * @Description :默认的加载更多样式
 * @Author : wdk
 * @CretaTime : 2019/4/3 15:04
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/3 15:04
 * @LastCheckBy :wdk
 */
public class DefaultLoadMoreView extends LinearLayout implements LoadMoreView, View.OnClickListener {

    private ProgressBar progress_bar;
    private TextView tv_message;

    private QRecyclerView.LoadMoreListener loadMoreListener;

    public DefaultLoadMoreView(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        setGravity(Gravity.CENTER);
        setVisibility(GONE);

        //这一行指定高度很重要，在加载更多的时候用来判断position用
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int minHeight = (int) (displayMetrics.density * 45 + 0.5);
        setMinimumHeight(minHeight);

        inflate(context, R.layout.layout_fotter_loadmore, this);
        progress_bar = findViewById(R.id.progress_bar);
        tv_message = findViewById(R.id.tv_message);

        setOnClickListener(this);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void onLoading() {
        setVisibility(VISIBLE);
        progress_bar.setVisibility(VISIBLE);
        tv_message.setVisibility(VISIBLE);
        tv_message.setText("正在加载中...");
    }

    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore, ListLoadStateEnum loadState) {

        if (ListLoadStateEnum.FIRSTLOAD.equals(loadState) || ListLoadStateEnum.REFRESH.equals(loadState)) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
        }

        if (hasMore) {
            setVisibility(INVISIBLE);

        } else {
            //没有更多数据了
            if (dataEmpty) {
                progress_bar.setVisibility(GONE);
                tv_message.setVisibility(VISIBLE);
                tv_message.setText("暂时没有数据");
            } else {
                progress_bar.setVisibility(GONE);
                tv_message.setVisibility(VISIBLE);
                tv_message.setText("已经加载全部数据");
            }
        }
    }

    @Override
    public void onWaitToLoadMore(QRecyclerView.LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        setVisibility(VISIBLE);
        progress_bar.setVisibility(VISIBLE);
        tv_message.setVisibility(VISIBLE);
        tv_message.setText("点击加载更多");
    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {
        setVisibility(VISIBLE);
        progress_bar.setVisibility(GONE);
        tv_message.setVisibility(VISIBLE);
        tv_message.setText(errorMessage);
    }

    /**
     * foot 的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }
}
