package com.guo.lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class RecyclerActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private String[] heros = {"武松", "林冲", "鲁达", "张清"};
    private List<String> heroList;

    {
        heroList = Arrays.asList(heros);
    }

    BaseQuickAdapter<String,BaseViewHolder> mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, heroList) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(android.R.id.text1, item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this,recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void onLoadMoreRequested() {
        ToastUtils.showLongToastSafe("Load more");
        rx.Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mAdapter.loadMoreComplete();
                    }
                });
    }
}
