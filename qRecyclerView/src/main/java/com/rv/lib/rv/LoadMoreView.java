package com.rv.lib.rv;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * @Description :
 * @Author : wdk
 * @CretaTime : 2019/4/22 15:01
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/22 15:01
 * @LastCheckBy :wdk
 */
public interface LoadMoreView {


    /**
     * 获取实体视图
     * @return 实体视图
     */
    @NonNull
    View getView();

    void show();

    /**
     * Show progress.
     */
    void onLoading();

    /**
     * Load finish, handle result.
     */
    void onLoadFinish(boolean dataEmpty, boolean hasMore, ListLoadStateEnum loadState);

    /**
     * Non-auto-loading mode, you can to click on the item to load.
     */
    void onWaitToLoadMore(QRecyclerView.LoadMoreListener loadMoreListener);

    /**
     * Load error.
     */
    void onLoadError(int errorCode, String errorMessage);

}
