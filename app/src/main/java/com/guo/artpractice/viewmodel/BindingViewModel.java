package com.guo.artpractice.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by Administrator on 2017/3/15.
 */

public class BindingViewModel {
    public ObservableInt androidVisibility;
    public ObservableField<String> observableWord = new ObservableField<>("那堪配在江州");


    public BindingViewModel() {
        androidVisibility = new ObservableInt(View.VISIBLE);
    }


    public void toggleVisible(View view) {
        androidVisibility.set(androidVisibility.get() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        observableWord.set("天下之言不归于杨即归墨");
    }
}
