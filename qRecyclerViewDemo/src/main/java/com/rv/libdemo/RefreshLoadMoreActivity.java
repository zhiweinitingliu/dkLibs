package com.rv.libdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rv.lib.rv.ListLoadStateEnum;
import com.rv.lib.refresh.RefreshAndLoadMoreListener;
import com.rv.lib.strong.QSwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : wdk
 * @CretaTime : 2019/4/22 15:54
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/22 15:54
 * @LastCheckBy :wdk
 */
public class RefreshLoadMoreActivity extends AppCompatActivity {

    private QSwipeRecyclerView qSwipeRecyclerView;
    LoadMoreAdapter loadMoreAdapter;
    List<String> listData = new ArrayList<>();

    int count = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_refresh_load_more);
        qSwipeRecyclerView = findViewById(R.id.qSwipeRecyclerView);
        qSwipeRecyclerView.setLinearLayoutManager();

        loadMoreAdapter = new LoadMoreAdapter(this);
        qSwipeRecyclerView.setAdapter(loadMoreAdapter);

        TextView textView = new TextView(this);
        textView.setText("阿道夫房间爱上克劳福德疯狂拉升京东方法律考试的反馈");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RefreshLoadMoreActivity.this, "我是第一个头", Toast.LENGTH_SHORT).show();
            }
        });

        TextView textViewFooter = new TextView(this);
        textViewFooter.setText("**************");
        textViewFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RefreshLoadMoreActivity.this, "我是第一个脚", Toast.LENGTH_SHORT).show();
            }
        });

        qSwipeRecyclerView.addHeaderView(textView);
        qSwipeRecyclerView.addFooterView(textViewFooter);

        initListener();
        initData();
    }

    private void initListener() {
        qSwipeRecyclerView.setRefreshAndLoadMore(new RefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                qSwipeRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> listDataInner = new ArrayList<>();
                        for (int i = 0; i < count; i++) {
                            String desc = "这是第" + (listData.size() + i) + "个条目";
                            listDataInner.add(desc);
                        }
                        listData.addAll(listDataInner);
                        loadMoreAdapter.setData(listData);
                        loadMoreAdapter.notifyDataSetChanged();


                        qSwipeRecyclerView.finishLoadMore(false, false, ListLoadStateEnum.LOADMORE);
                    }
                }, 3000);
            }
        });
    }

    private void refreshData() {
        listData.clear();
        qSwipeRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> listDataInner = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    String desc = "这是第" + (listData.size() + i) + "个条目";
                    listDataInner.add(desc);
                }
                listData.addAll(listDataInner);
                loadMoreAdapter.setData(listData);
                loadMoreAdapter.notifyDataSetChanged();
                qSwipeRecyclerView.finishLoadMore(false, true, ListLoadStateEnum.REFRESH);
                qSwipeRecyclerView.finishSmartRefresh();

            }
        }, 3000);
    }

    private void initData() {
        List<String> listDataInner = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String desc = "这是第" + (listData.size() + i) + "个条目";
            listDataInner.add(desc);
        }
        listData.addAll(listDataInner);
        loadMoreAdapter.setData(listData);
        loadMoreAdapter.notifyDataSetChanged();
        qSwipeRecyclerView.finishLoadMore(false, true, ListLoadStateEnum.REFRESH);
    }


}
