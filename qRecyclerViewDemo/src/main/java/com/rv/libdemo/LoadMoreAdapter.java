package com.rv.libdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : wdk
 * @CretaTime : 2019/4/11 16:24
 * @LastModify(最终修改人) :wdk
 * @LastModifyTime(最终修改时间) : 2019/4/11 16:24
 * @LastCheckBy :wdk
 */
public class LoadMoreAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> listData = new ArrayList<>();

    public LoadMoreAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<String> listData) {
        this.listData = listData;
    }

    public void addData(int position){
        listData.add(position,"new data"+position);
        notifyItemInserted(position);
    }

    public void removeData(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_load_more, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView tvContent = holder.itemView.findViewById(R.id.tv_content);
        tvContent.setText(listData.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
