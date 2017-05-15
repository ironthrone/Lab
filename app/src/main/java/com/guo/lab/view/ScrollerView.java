package com.guo.lab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ScrollerView extends View {
    private static final String TAG = ScrollerView.class.getSimpleName();
    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public ScrollerView(Context context) {
        this(context, null);
    }

    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        int mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.d(TAG, "touch slop: " + mTouchSlop);
    }

    private float mLastX;
    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //接收事件
        mVelocityTracker.addMovement(event);
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //如果这时候滑动还未结束，终止它
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - mLastX;
                float deltaY = event.getY() - mLastY;
                scrollBy((-(int) deltaX), (-(int) deltaY));
                break;
            case MotionEvent.ACTION_UP:
                //在需要的时候计算速度
                mVelocityTracker.computeCurrentVelocity(1000);
                /**
                 * 因为滑动的方向和view坐标相反，速度需要改变符号
                 * 在fling或者startScroll之后调用invalidate()发起绘制，这样UI上的位置才能更新
                 */
                mScroller.fling(getScrollX(),getScrollY(),-(int)mVelocityTracker.getXVelocity(),-(int)mVelocityTracker.getYVelocity(),
                        Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
                invalidate();
                //重置为初始状态，清除缓存
                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            //继续刷新绘制
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = canvas.getWidth() / 2f;
        float centerY = canvas.getHeight() / 2f;
        canvas.drawCircle(centerX, centerY, centerX / 5, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //回收VelocityTracker
        mVelocityTracker.recycle();
    }
}
