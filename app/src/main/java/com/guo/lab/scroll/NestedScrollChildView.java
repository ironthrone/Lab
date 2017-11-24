package com.guo.lab.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.support.v4.view.NestedScrollingChild;

/**
 * Created by ironthrone on 2017/6/12 0012.
 */

public class NestedScrollChildView extends View implements NestedScrollingChild {
    public NestedScrollChildView(Context context) {
        super(context);
    }

    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
