package com.guo.lab.scroll;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SizeUtils;

import java.lang.reflect.Field;

import static android.R.attr.overScrollMode;

/**
 * Created by ironthrone on 2017/8/11 0011.
 */

public class SpringScrollView extends ScrollView {

    private float oldY;

    private VelocityTracker velocityTracker;
    private OverScroller scroller;
    private float oldX;
    private boolean clampedY;

    public SpringScrollView(Context context) {
        super(context);
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        setOverScrollMode(OVER_SCROLL_NEVER);
        velocityTracker = VelocityTracker.obtain();
        scroller = new OverScroller(context);

        clearColorForEdgeEffect("mEdgeGlowTop");
        clearColorForEdgeEffect("mEdgeGlowBottom");
    }

    private void clearColorForEdgeEffect(String name) {
        try {
            Field effectTop = ScrollView.class.getDeclaredField(name);
            effectTop.setAccessible(true);
            EdgeEffect top = (EdgeEffect) effectTop.get(this);
            Field paintField = EdgeEffect.class.getDeclaredField("mPaint");
            paintField.setAccessible(true);
            Paint paint = (Paint) paintField.get(top);
            paint.setColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        velocityTracker.addMovement(ev);
//        int action = ev.getActionMasked();
//        float newX = ev.getX();
//        float newY = ev.getY();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                oldY = ev.getY();
//                oldX = ev.getX();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float deltaY = newY - oldY;
//                scrollBy(0, -(int) deltaY);
//                break;
//            case MotionEvent.ACTION_UP:
//                velocityTracker.computeCurrentVelocity(1000);
//                velocityTracker.getYVelocity();
//                int maxFlingY = 100;
//                scroller.fling(0, getScrollY(), 0, -((int) velocityTracker.getYVelocity()), 0, -Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
//                invalidate();
//                velocityTracker.clear();
//
//                break;
//        }
//        oldX = newX;
//        oldY = newY;
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//        int customMaxOverScrollY = SizeUtils.dp2px(100);
//        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, customMaxOverScrollY, isTouchEvent);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();

                if (scrollY < 0) {
                    smoothScrollTo(0, 0);
                }
                int bottomScrollY = getScrollRange();
                if (scrollY > bottomScrollY) {

                    smoothScrollTo(0, scrollY);
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * copy from ScrollView
     *
     * @return
     */
    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0,
                    child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
        }
        return scrollRange;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        LogUtils.d("scrollY: " + scrollY + "  clampedY: " + clampedY);
        this.clampedY = clampedY;
    }


    private int customMaxOverScrollY = SizeUtils.dp2px(100);


    @Override
    @SuppressWarnings({"UnusedParameters"})
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {


        int newScrollY = scrollY + deltaY;
        maxOverScrollY = customMaxOverScrollY;

        // Clamp values if at the limits and record
        final int left = -maxOverScrollX;
        final int right = maxOverScrollX + scrollRangeX;
        final int top = -maxOverScrollY;
        final int bottom = maxOverScrollY + scrollRangeY;


        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }

        onOverScrolled(0, newScrollY, false, clampedY);

        return  clampedY;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
//        if (scroller.computeScrollOffset()) {
//            LogUtils.d("scroller compute");
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//            LogUtils.d("currentX:" + scroller.getCurrX()+ "currentY: " + scroller.getCurrY());
//            invalidate();
//        }
    }
}
