package com.guo.lab.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * 如果不重写getViewHorizontalDragRange或者getViewVerticalDragRange,shouldInterceptTouchEvent()返回false，
 * 当子View消耗事件的时候，ViewGroup自己就不能消耗事件，拖拽操作没法实现。解决思路有
 * 1. 重写上面两个方法，使返回值不为0，ACTION_MOVE事件的时候shouldInterceptTouchEvent（）返回true，拦截住事件自己处理，实现拖拽
 * 2. onInterceptTouchEvent直接返回true，不让子View接触到任何类型的事件
 */

public class ViewDragHelperLayout extends ViewGroup {

    private View normal;
    private View dropToOrigin;
    private ViewDragHelper viewDragHelper;
    private Random random;
    private Point mOrigin;
    private View edgeDrag;

    public ViewDragHelperLayout(Context context) {
        this(context, null);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return child == normal || child == dropToOrigin;
            }

            //确定竖直方向的移动位置，默认返回0，不可移动
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                if (child == edgeDrag) {
                    return child.getTop();
                }
                return Math.min(Math.max(top,getTop()),getBottom()-child.getMeasuredHeight());
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return Math.min(Math.max(getLeft(),left),getRight() - child.getMeasuredWidth());
            }


            /**
             * 他和下面的方法任意一个返回不为0，ViewDragHelper拦截ACTION_MOVE事件（shouldInterruptTouchEvent()在ACTION_MOVE时返回true）
             * 这样仍然可以拖动view
             */

            @Override
            public int getViewVerticalDragRange(View child) {

                return getMeasuredHeight()-child.getMeasuredHeight();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == dropToOrigin) {
                    viewDragHelper.settleCapturedViewAt(mOrigin.x, mOrigin.y);
                    //开始返回原来位置，需要在computeScroll()中接力
                    invalidate();
                }
            }

            /**
             * 检测到触摸到边界，captureChildView()捕捉目标view，移动目标View的位置
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                viewDragHelper.captureChildView(edgeDrag,pointerId);
            }
        });
        //设置追踪某个边界
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        random = new Random();
                mOrigin = new Point();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //测量儿子
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = child.getLayoutParams();
            child.measure(getChildMeasureSpec(widthMeasureSpec,getPaddingLeft()+getPaddingRight(),layoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec,getPaddingTop()+getPaddingBottom(),layoutParams.height));
        }
        super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
    }


    @Override
    protected void onFinishInflate() {
        normal = getChildAt(0);
        dropToOrigin = getChildAt(1);
        edgeDrag = getChildAt(2);
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            int randomWidth = random.nextInt(getMeasuredWidth()-child.getMeasuredWidth());
            int randomHeight = random.nextInt(getMeasuredHeight()-child.getMeasuredHeight());
            int left = l + randomWidth;
            int right = left + child.getMeasuredWidth();
            int top = t + randomHeight;
            int bottom = top + child.getMeasuredHeight();
            if (child == dropToOrigin) {
                mOrigin.x = left;
                mOrigin.y = top;
            }
            child.layout(left,top,right,bottom);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
