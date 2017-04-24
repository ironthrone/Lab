package com.guo.lab.mvp2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 使用
 * 1.编写Presenter
 * 2.注解Activity的Presenter类型
 * @param <P>
 */
public  class BaseMvpActivity<P extends AbstractPresenter> extends AppCompatActivity implements WithLoadingView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = PresenterFactory.<P>newPresenter(getClass());
        if(mPresenter == null) throw new UnsupportedOperationException("Need Presenter");
        mPresenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
