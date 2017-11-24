package com.guo.lab.scroll;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ScrollView;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by ironthrone on 2017/8/14 0014.
 */

public class SpringScrollView2 extends ScrollView {


    private View contentView;
    private Rect contentBound;
    private VelocityTracker tracker;

    private float overScrollAmount;

    public SpringScrollView2(Context context) {
        this(context, null);
    }

    public SpringScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        contentBound = new Rect();
        tracker = VelocityTracker.obtain();

        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        contentBound.left = l;
        contentBound.top = t;
        contentBound.right = r;
        contentBound.bottom = b;
    }

    private float oldX;
    private float oldY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float newX = ev.getX();
        float newY = ev.getY();
        float deltaY = newY - oldY;
        boolean canDown = canOverScrollDown();
        boolean canUp = canOverScrollUp();
        tracker.addMovement(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                overScrollAmount = 0;
                oldX = ev.getX();
                oldY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDown || canUp) {
                    overScrollAmount += deltaY;
                    contentView.layout(contentBound.left, (int) (contentBound.top + overScrollAmount),
                            contentBound.right, (int) (contentBound.bottom + overScrollAmount));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (canDown || canUp) {
                    tracker.computeCurrentVelocity(1000);
                    float yVelocity = tracker.getYVelocity();
                    if (overScrollAmount < 0) {
                        //down

                    } else if (overScrollAmount > 0) {

                    }
                }
                tracker.clear();
                break;
        }
        oldX = newX;
        oldY = newY;
        return super.dispatchTouchEvent(ev);
    }

    private float maxOverscrollDistance = SizeUtils.dp2px(100);

    private boolean canOverScrollDown() {
        if (contentView != null) {
            return getScrollY() < 0 && getScrollY() > -maxOverscrollDistance ||
                    contentView.getHeight() < getHeight();
        } else {
            return true;
        }
    }

    private boolean childFullInSide() {
        if (contentView != null) {
            return contentView.getHeight() < getHeight();
        }
        return true;
    }

    private boolean canOverScrollUp() {
        if (contentView != null) {
            return getScrollY() + getHeight() >= contentView.getHeight() && getScrollY() + getHeight()
                    < contentView.getHeight() + maxOverscrollDistance;
        } else {
            return true;
        }
    }
}
