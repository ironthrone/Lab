package com.guo.lab.accessibility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ironthrone on 2017/8/25 0025.
 */

public class BlockingView extends FrameLayout {
    public BlockingView(@NonNull Context context) {
        super(context);
    }

    public BlockingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
