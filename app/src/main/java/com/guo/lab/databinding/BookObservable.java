package com.guo.lab.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.guo.lab.BR;


/**
 * Created by Administrator on 2017/3/16.
 */

public class BookObservable extends BaseObservable{

    public double price;
    public String name;

    public BookObservable(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
