package com.guo.lab.databinding;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by Administrator on 2017/3/17.
 */

public class TextViewModel {
    public String haohan;

    public TextViewModel(String haohan) {
        this.haohan = haohan;
    }


    public void echo(View view) {
        ToastUtils.showShortSafe(((TextView)view).getText().toString());
    }
}
