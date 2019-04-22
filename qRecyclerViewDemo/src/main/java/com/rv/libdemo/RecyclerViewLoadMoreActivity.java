package com.rv.libdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.rv.lib.rv.ListLoadStateEnum;
import com.rv.lib.rv.QRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : wdk
 * @CretaTime : 2019/4/11 16:14
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/11 16:14
 * @LastCheckBy :wdk
 */
public class RecyclerViewLoadMoreActivity extends AppCompatActivity {

    private Button btn_refresh;
    private QRecyclerView recyclerView;
    LoadMoreAdapter loadMoreAdapter;
    List<String> listData = new ArrayList<>();

    int count = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_load_more);
        recyclerView = findViewById(R.id.recyclerView);
        btn_refresh = findViewById(R.id.btn_refresh);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadMoreAdapter = new LoadMoreAdapter(this);
        recyclerView.setAdapter(loadMoreAdapter);
        recyclerView.useDefaultLoadMore();

//        TextView textView = new TextView(this);
//        textView.setText("阿道夫房间爱上克劳福德疯狂拉升京东方法律考试的反馈");
//        recyclerView.addHeaderView(textView);

        recyclerView.setLoadMoreListener(new QRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
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

                        recyclerView.loadMoreFinish(false, true, ListLoadStateEnum.LOADMORE);
                    }
                }, 3000);
            }
        });
        initData();
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.clear();
                initData();
            }
        });
    }

    private void initData() {
        recyclerView.postDelayed(new Runnable() {
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
                recyclerView.loadMoreFinish(false, true, ListLoadStateEnum.REFRESH);
            }
        }, 3000);
    }
}
