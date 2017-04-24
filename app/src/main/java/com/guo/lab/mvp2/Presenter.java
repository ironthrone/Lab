package com.guo.lab.mvp2;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface Presenter<V extends View> {
    /**
     * 在onCreate()中绑定View
     * @param view
     */
    void attachView(V view);

    /**
     * 在onDestroy()中解除绑定，防止内存泄露
     */
    void detachView();
}
