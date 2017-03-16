package com.guo.artpractice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/16.
 */

public class WithACircleView extends View {

    private int color;
    private Paint mPaint;

    public WithACircleView(Context context) {
        this(context, null);
    }

    public WithACircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WithACircleView);
        try {
            color = typedArray.getColor(R.styleable.WithACircleView_circle_color, Color.WHITE);
        } finally {
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int width = widthSpec;
        int height = heightSpec;
        if (widthMeasureMode == MeasureSpec.AT_MOST) {
            if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    width = widthSpec;
            }
        }
        if (heightMeasureMode == MeasureSpec.AT_MOST) {
            if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    height = heightSpec;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        width = Math.max(0, width);
        height = Math.max(0, height);
        float radius = Math.min(width, height) / 2;
        canvas.drawCircle(getPaddingLeft()+ width / 2, getPaddingTop()+height / 2, radius, mPaint);
    }
}
