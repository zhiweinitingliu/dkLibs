package com.rv.lib.refresh;

import android.content.Context;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * @Description: 刷新和加载更多的帮助类，因为刷新控件升级后，刷新方法可能会变，所以单独拿出来统一调用
 * @Author: wdk
 * @CreatTime: 2018/6/12 13:58
 * @LastModify: wdk
 * @LastModifyTime: 2018/6/12 13:58
 * @LastCheckedBy: wdk
 */
public class RefreshLoadMoreHelper {
    private SmartRefreshLayout smartRefreshLayout;

    public RefreshLoadMoreHelper(SmartRefreshLayout smartRefreshLayout) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//当内容没有填充满的时候不能加载更多，为了隐藏底部的“已经加载全部数据”
        }
        this.smartRefreshLayout = smartRefreshLayout;
    }

    public synchronized static RefreshLoadMoreHelper bindRefreshView(SmartRefreshLayout smartRefreshLayout) {
        return new RefreshLoadMoreHelper(smartRefreshLayout);
    }

    //----------------------------start-----------------获取刷新的头 加载更多的脚布局 并且进行默认设置--------------------------------------------------------------

    /**
     * 设置刷新头classicsHeader 经典风格
     */
    public void setRefreshClassicsHeader(Context context) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setRefreshHeader(getClassicsHeader(context));
    }

    private ClassicsHeader classicsHeader;

    /**
     * 获取经典风格的头布局
     *
     * @param context 上下文
     * @return 经典风格的头布局
     */
    public ClassicsHeader getClassicsHeader(Context context) {
        if (classicsHeader == null) {
            classicsHeader = new ClassicsHeader(context);
        }
        return classicsHeader;
    }


    /**
     * 设置刷新头MaterialHeader  materia design风格
     */
    public void setRefreshMaterialHeader(Context context) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setRefreshHeader(getMaterialHeader(context));
    }

    private MaterialHeader materialHeader;

    /**
     * 获取design风格的头布局
     *
     * @param context 上下文
     * @return design风格的头布局
     */
    public MaterialHeader getMaterialHeader(Context context) {
        if (materialHeader == null) {
            materialHeader = new MaterialHeader(context);
        }
        return materialHeader;
    }


    /**
     * 设置加载更多的脚布局 ClassicsFooter 经典风格
     * 返回MClassicsFooter 可以用来设置footer的属性
     */
    public void setLoadMoreClassicsFooter(Context context) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setRefreshFooter(getMClassicsFooter(context));
    }

    private MClassicsFooter mClassicsFooter;

    /**
     * 获取脚布局类
     *
     * @param context 上下文
     * @return 脚布局类
     */
    public MClassicsFooter getMClassicsFooter(Context context) {
        if (mClassicsFooter == null) {
            mClassicsFooter = new MClassicsFooter(context);
            mClassicsFooter.setTextSizeTitle(13);
            DensityUtil density = new DensityUtil();
            mClassicsFooter.setMPadingTop(density.dip2px(13));
            mClassicsFooter.setMPaddingBottom(density.dip2px(13));
        }
        return mClassicsFooter;
    }

    //----------------------------end-----------------获取刷新的头 加载更多的脚布局 并且进行默认设置--------------------------------------------------------------

    //----------------------------start-----------------设置刷新 加载更多的监听--------------------------------------------------------------

    /**
     * 设置刷新的监听
     *
     * @param mRefreshListener 刷新监听接口
     */
    public void setOnRefreshListener(final MRefreshListener mRefreshListener) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh(refreshLayout);
                }
            }
        });
    }

    /**
     * 设置加载更多的监听
     *
     * @param mLoadMoreListener 加载更多监听接口
     */
    public void setOnLoadMoreListener(final MLoadMoreListener mLoadMoreListener) {
        if (smartRefreshLayout == null) {
            return;
        }

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore(refreshLayout);
                }
            }
        });
    }

    //----------------------------end-----------------设置刷新 加载更多的监听--------------------------------------------------------------

    //----------------------------start-----------------一些公用的方法--------------------------------------------------------------

    /**
     * 刷新完成
     */
    public void finishRefresh() {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.finishRefresh();
    }

    /**
     * 加载更多完成
     *
     * @param delayed 动画时间
     */
    public void finishLoadMore(int delayed) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.finishLoadMore(delayed);
    }

    /**
     * 设置没有更多数据
     */
    public void setNoMoreData(boolean noMoreData) {
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setNoMoreData(noMoreData);
    }
}
