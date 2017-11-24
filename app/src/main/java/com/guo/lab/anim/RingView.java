package com.guo.lab.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ironthrone on 2017/7/13 0013.
 */

public class RingView extends View {
    private Path path;
    private int ringWidth = 15;
    private int color = Color.RED;
    private Paint paint;

    public RingView(Context context) {
        this(context,null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = centerX - ringWidth / 2;
        paint.setColor(color);
        paint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(centerX,centerY,radius,paint);
        canvas.drawArc(new RectF(0,0,getWidth(),getHeight()),0,360,false,paint);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }
}
