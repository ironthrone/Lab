package com.guo.lab.mvp2;

/**
 * Created by Administrator on 2016/11/10.
 */
public abstract class AbstractPresenter<T extends View> implements Presenter<T> {
    protected T mView;
    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
