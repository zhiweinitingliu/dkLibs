package com.rv.lib.strong;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.rv.lib.rv.DefaultLoadMoreView;
import com.rv.lib.rv.ListLoadStateEnum;
import com.rv.lib.rv.LoadMoreView;
import com.rv.lib.rv.QRecyclerView;
import com.rv.lib.R;
import com.rv.lib.refresh.MClassicsFooter;
import com.rv.lib.refresh.MLoadMoreListener;
import com.rv.lib.refresh.MRefreshListener;
import com.rv.lib.refresh.RefreshAndLoadMoreListener;
import com.rv.lib.refresh.RefreshLoadMoreHelper;
import com.rv.lib.utils.LoadMoreType;
import com.rv.lib.utils.RvUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * @Description :组合控件 带有刷新
 * @Author : wdk
 * @CretaTime : 2019/4/22 10:10
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/22 10:10
 * @LastCheckBy :wdk
 */
public class QSwipeRecyclerView extends LinearLayout {

    private LoadMoreType loadmoreType = LoadMoreType.Q_RECYCLERVIEW_LOADMORE_TYPE;


    private SmartRefreshLayout smartRefreshLayout;
    private QRecyclerView qRecyclerView;
    private RefreshLoadMoreHelper refreshLoadMoreHelper;
    private RefreshHeader refreshHeader;
    private RefreshFooter refreshFooter;
    private LoadMoreView loadMoreView;

    public QSwipeRecyclerView(Context context) {
        this(context, null);
    }

    public QSwipeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QSwipeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_q_recyclerview, this, true);
        //smartRefreshLayout
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        refreshHeader = new ClassicsHeader(context);

        MClassicsFooter mClassicsFooter = new MClassicsFooter(context);
        mClassicsFooter.setTextSizeTitle(13);
        DensityUtil density = new DensityUtil();
        mClassicsFooter.setMPadingTop(density.dip2px(13));
        mClassicsFooter.setMPaddingBottom(density.dip2px(13));
        refreshFooter = mClassicsFooter;

        //recyclerview
        qRecyclerView = findViewById(R.id.q_recyclerView);
        loadMoreView = new DefaultLoadMoreView(context);
    }

    /**
     * set LinearLayoutManager LayoutManager
     */
    public void setLinearLayoutManager() {
        RvUtil.initLinearLayout(getContext(), qRecyclerView);
    }

    public void setGridLayoutManager(int spanCount) {
        RvUtil.initGridLayoutManager(getContext(), qRecyclerView, spanCount);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        qRecyclerView.setAdapter(adapter);
    }

    /**
     * add item decoration
     * provide a class ItemDivider.java
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        qRecyclerView.addItemDecoration(decor);
    }

    /**
     * set recyclerview item animator
     */
    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        qRecyclerView.setItemAnimator(itemAnimator);
    }

    /**
     * Add view at the headers.
     */
    public void addHeaderView(View view) {
        qRecyclerView.addHeaderView(view);
    }

    /**
     * Remove view from header.
     */
    public void removeHeaderView(View view) {
        qRecyclerView.removeHeaderView(view);
    }

    /**
     * Add view at the footer.
     */
    public void addFooterView(View view) {
        qRecyclerView.addFooterView(view);
    }

    public void removeFooterView(View view) {
        qRecyclerView.removeFooterView(view);
    }

    /**
     * set smartRefreshHeader
     */
    public void setRefreshHeader(RefreshHeader refreshHeader) {
        this.refreshHeader = refreshHeader;
    }

    /**
     * set smartRefreshFooter
     */
    public void setLoadMoreFooter(RefreshFooter refreshFooter) {
        this.refreshFooter = refreshFooter;
    }

    /**
     * set qRecyclerView loadmoreFooter
     */
    public void setQLoadMoreFooter(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    /**
     * LoadMoreType
     */
    public void setLoadMoreType(LoadMoreType loadmoreType) {
        this.loadmoreType = loadmoreType;
    }

    RefreshAndLoadMoreListener mRefreshAndLoadMoreListener;

    public void setRefreshAndLoadMore(RefreshAndLoadMoreListener refreshAndLoadMoreListener) {
        mRefreshAndLoadMoreListener = refreshAndLoadMoreListener;

        refreshLoadMoreHelper = RefreshLoadMoreHelper.bindRefreshView(smartRefreshLayout);
        smartRefreshLayout.setRefreshHeader(refreshHeader);
        refreshLoadMoreHelper.setOnRefreshListener(new MRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshAndLoadMoreListener.onRefresh();
            }
        });


        if (loadmoreType == LoadMoreType.SMART_LOADMORE_TYPE) {
            //smartRefreshLayout loadMore
            smartRefreshLayout.setRefreshFooter(refreshFooter);
            refreshLoadMoreHelper.setOnLoadMoreListener(new MLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    mRefreshAndLoadMoreListener.onLoadMore();
                }
            });

        } else if (loadmoreType == LoadMoreType.Q_RECYCLERVIEW_LOADMORE_TYPE) {
            //qRecyclerView loadMore
            qRecyclerView.addFooterView(loadMoreView.getView());
            qRecyclerView.setLoadMoreView(loadMoreView);
            qRecyclerView.setLoadMoreListener(new QRecyclerView.LoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mRefreshAndLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    /**
     * return qRecyclerView
     */
    public QRecyclerView getRecyclerView() {
        return qRecyclerView;
    }

    /**
     * finish smartRefreshLayout refresh
     */
    public void finishSmartRefresh() {
        smartRefreshLayout.finishRefresh();
    }

    /**
     * finish loadMore
     *
     * @param dataEmpty is empty data
     * @param hasMore   is has more
     * @param loadState load state , for example: FIRSTLOAD, REFRESH, LOADMORE
     */
    public void finishLoadMore(boolean dataEmpty, boolean hasMore, ListLoadStateEnum loadState) {
        if (loadmoreType == LoadMoreType.SMART_LOADMORE_TYPE) {
            //smartRefreshLayout load more
            smartRefreshLayout.finishLoadMore();
            smartRefreshLayout.setNoMoreData(hasMore);

        } else if (loadmoreType == LoadMoreType.Q_RECYCLERVIEW_LOADMORE_TYPE) {
            //qRecyclerView load more
            qRecyclerView.loadMoreFinish(dataEmpty, hasMore, loadState);
        }
    }


}
