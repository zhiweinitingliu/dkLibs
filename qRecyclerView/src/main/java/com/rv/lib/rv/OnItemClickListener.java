package com.rv.lib.rv;

import android.view.View;

/**
 * @Description :recyclerview 条目点击监听
 * @Author : wdk
 * @CretaTime : 2019/4/2 17:06
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/2 17:06
 * @LastCheckBy :wdk
 */
public interface OnItemClickListener {

    void onItemClick(View itemView, int position);
}
