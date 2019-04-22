package com.rv.lib.refresh;

/**
 * @Description : 刷新和加载更多的监听
 * @Author : wdk
 * @CretaTime : 2019/4/22 14:19
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/22 14:19
 * @LastCheckBy :wdk
 */
public interface RefreshAndLoadMoreListener {

    void onRefresh();

    void onLoadMore();

}
