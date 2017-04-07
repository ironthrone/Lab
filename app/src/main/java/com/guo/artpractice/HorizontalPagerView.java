package com.guo.artpractice;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HorizontalPagerView extends ViewGroup {
    private static final String TAG = HorizontalPagerView.class.getSimpleName();
    private float lastX;
    private float lastY;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalPagerView(Context context) {
        this(context, null);
    }

    public HorizontalPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            child.layout(childLeft, 0, childLeft + childWidth, childHeight);
            childLeft += childWidth;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                intercept = mVelocityTracker.getXVelocity() > mVelocityTracker.getYVelocity();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mVelocityTracker.clear();
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getChildCount() == 0) return false;
        int childWidth = getChildAt(0).getWidth();
        int childCount = getChildCount();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - lastX;
                float dest = getScrollX() - deltaX;
                dest = Math.max(0, Math.min(dest, (childCount - 1) * childWidth));
                scrollTo(((int) dest), 0);
                break;
            case MotionEvent.ACTION_UP:
                int destScrollX;
                int currentScrollX = getScrollX();

                int extraScrollX = currentScrollX % childWidth;
                if (extraScrollX < childWidth / 2) destScrollX = currentScrollX - extraScrollX;
                else destScrollX = currentScrollX - extraScrollX + childWidth;

                mScroller.startScroll(currentScrollX, 0, destScrollX - currentScrollX, 0);
                invalidate();
                break;
        }
        lastX = event.getX();
        lastY = event.getY();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 自己等于义子的大小之和，假定义子具有相同的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        //测量儿子
        measureChildren(widthMeasureSpec,
                heightMeasureSpec);

        //测量自己
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        View child = getChildAt(0);
        Log.d(TAG, "child width: " + child.getWidth() + " child height: " + child.getHeight());
        if (widthMeasureMode == MeasureSpec.AT_MOST) {
            int width = getChildCount() * child.getMeasuredWidth();
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, widthMeasureMode), heightMeasureSpec);
        } else if (heightMeasureMode == MeasureSpec.AT_MOST) {
            int height = child.getMeasuredHeight();
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, heightMeasureMode));
        } else {
            int width = getChildCount() * child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, widthMeasureMode),
                    MeasureSpec.makeMeasureSpec(height, heightMeasureMode));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
