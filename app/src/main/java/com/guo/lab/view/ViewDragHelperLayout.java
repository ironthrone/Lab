package com.guo.lab.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ViewDragHelperLayout extends ViewGroup {
    public ViewDragHelperLayout(Context context) {
        this(context,null);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewDragHelper viewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return false;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        View normal = getChildAt(0);
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
