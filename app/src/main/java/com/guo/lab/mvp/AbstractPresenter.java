package com.guo.lab.mvp;

/**
 * 使用：
 * 1.编写Presenter
 * 2.在View中实例化Presenter
 * 3.绑定Presenter的attachView和detachView到Activity的生命周期
 */
public abstract class AbstractPresenter<T extends MVPView> implements Presenter<T> {
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
