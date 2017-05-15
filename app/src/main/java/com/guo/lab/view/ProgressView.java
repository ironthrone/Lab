package com.guo.lab.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.guo.lab.R;

/**
 * Created by Administrator on 2017/5/15.
 */

public class ProgressView extends View {

    private static final int MINIMUM_INNER_RADIUS = 50;
    private int mSpeed = 1;
    private int mProgressColor;
    private int mBackgroundColor;
    private int mRangeWidth;
    private boolean attached = false;
    private int[] mColorArray;
    private Paint mPaint;
    private float mProgress;
    private Thread refresher;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
            for (int i = 0; i < a.length(); i++) {
                int index = a.getIndex(i);
                switch (index) {
                    case R.styleable.ProgressView_rangeWidth:
                        mRangeWidth = a.getDimensionPixelSize(index, 5);
                        break;
                    case R.styleable.ProgressView_backgroundColor:
                        mBackgroundColor = a.getColor(index, Color.BLACK);
                        break;
                    case R.styleable.ProgressView_progressColor:
                        mProgressColor = a.getColor(index, Color.WHITE);
                        break;
                    case R.styleable.ProgressView_speed:
                        mSpeed = a.getIndex(index);
                        break;
                }
            }
            a.recycle();
        }
        mColorArray = new int[]{mBackgroundColor, mProgressColor};
        mPaint = new Paint();

        //创建刷新线程，但不开始，等到view添加到窗口后再开始,从窗口移走后打断线程
        refresher = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        postInvalidate();
                        Thread.sleep(100);
                        mProgress += mSpeed;
                        if (mProgress >= 360) {
                            mProgress = 0;
                            exchangeColorArray();
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    private void exchangeColorArray() {
        int temp = mColorArray[0];
        mColorArray[0] = mColorArray[1];
        mColorArray[1] = temp;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refresher.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        refresher.interrupt();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //AT_MOST或者UNSPECIFIED的时候，子View指定自己的大小
        if (widthMeasureMode == MeasureSpec.AT_MOST || widthMeasureMode == MeasureSpec.UNSPECIFIED) {

            width = Math.min(width, mRangeWidth + MINIMUM_INNER_RADIUS + getPaddingLeft() + getPaddingRight());
        }
        if (heightMeasureMode == MeasureSpec.AT_MOST || heightMeasureMode == MeasureSpec.UNSPECIFIED) {
            height = Math.min(height, mRangeWidth + MINIMUM_INNER_RADIUS + getPaddingTop() + getPaddingBottom());
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = Math.min(getWidth() - getPaddingLeft() - getPaddingRight() / 2,
                (getHeight() - getPaddingTop() - getPaddingBottom()) / 2);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        mPaint.setColor(mColorArray[0]);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        mPaint.setColor(mColorArray[1]);

        canvas.drawArc(new RectF(centerX - radius, centerX - radius, centerY + radius, centerY + radius), -90, mProgress, true, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerX, radius - mRangeWidth, mPaint);
    }

}
