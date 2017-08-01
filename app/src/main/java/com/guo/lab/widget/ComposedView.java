package com.guo.lab.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.guo.lab.R;

/**
 * Created by ironthrone on 2017/8/1 0001.
 */

public class ComposedView extends FrameLayout {
    public ComposedView(@NonNull Context context) {
        super(context);
    }

    public ComposedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context)
                .inflate(R.layout.view_composed, this);
    }


}
