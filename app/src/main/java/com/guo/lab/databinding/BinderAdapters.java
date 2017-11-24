package com.guo.lab.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/3/17.
 */

@BindingMethods({@BindingMethod(type = android.view.View.class,
        attribute = "android:tint",
        method = "setImageTintList")})
public class BinderAdapters {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, @DrawableRes int resId) {
        Picasso.with(view.getContext())
                .load(resId)
                .into(view);
    }

    //TODO


//    @BindingConversion
//    public static ColorDrawable convertColorToDrawable(int color) {
//        return new ColorDrawable(color);
//    }

}
