package com.guo.lab;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BinderAdapters {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, @DrawableRes int resId) {
        Picasso.with(view.getContext())
                .load(resId)
                .into(view);
    }
}
