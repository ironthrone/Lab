package com.guo.lab.mvp;

import android.content.Context;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface MVPView {
    /**
     * 在Presenter中只知道MVPView，不知道Activity或者Fragment
     * @return
     */
    Context getContext();
}
