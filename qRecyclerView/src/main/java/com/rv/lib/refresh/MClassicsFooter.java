package com.rv.lib.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * @Description: 自定义加载更多的脚布局
 * @Author: wdk
 * @CreatTime: 2018/6/12 15:10
 * @LastModify: wdk
 * @LastModifyTime: 2018/6/12 15:10
 * @LastCheckedBy: wdk
 */
public class MClassicsFooter extends ClassicsFooter {

    public MClassicsFooter(Context context) {
        this(context, null);
    }

    public MClassicsFooter(Context context, AttributeSet attrs) {
        this(context, null, 0);

    }

    public MClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        REFRESH_FOOTER_ALLLOADED = "已经加载全部数据";
    }

    public void setMPadingTop(int mPaddingTop) {
        this.mPaddingTop = mPaddingTop;
    }

    public void setMPaddingBottom(int mPaddingBottom) {
        this.mPaddingBottom = mPaddingBottom;
    }
}
