package com.guo.artpractice.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;

import com.guo.artpractice.BR;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Book extends BaseObservable{

    @Bindable public String author;
    public double price;

    public Book(String author, double price) {
        this.author = author;
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }
}
