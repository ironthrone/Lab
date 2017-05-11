package com.guo.lab.draw;

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
 * Created by Administrator on 2017/5/10.
 */

public class CanvasView extends View {

    //paint包含着信息关于怎么绘制位图，形状，文本
    private Paint paint;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

        RectF rectF = new RectF(100,100,800,500);
    RectF rectF2 = new RectF(100, 600, 600, 800);
    RectF rectF3 = new RectF(100,900,800,1100);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.DKGRAY);
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,120,true,paint);

        //矩形可能是不是正方形
        paint.setColor(Color.GRAY);
        canvas.drawRoundRect(rectF2,10,10,paint);

        paint.setColor(Color.YELLOW);
        canvas.drawOval(rectF3,paint);

        //绘制线条，指定宽度
        paint.setColor(Color.GREEN);
        //线条结尾处的形状我半圆
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        canvas.drawLines(new float[]{100,100,800,100,
        200,300,200,400,800,300,700,900},0,8,paint);

        //paint的风格
        paint.setColor(Color.WHITE);
        canvas.drawCircle(500,500,200,paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(800,800,200,paint);
//        canvas.drawTextOnPath("hello",);


//        clip裁剪绘制
//        canvas.clipRect(100, 800, 500, 1300);
//        canvas.drawColor(Color.RED);
//        canvas.drawCircle(200,900,400,paint);


        //path
        Path path = new Path();
        path.moveTo(100,200);
        path.lineTo(200,300);
        path.lineTo(400,700);
        path.lineTo(500,600);
        path.lineTo(100,200);
        paint.setStrokeWidth(20);
        canvas.drawPath(path,paint);

        paint.setShadowLayer(10,2,2,Color.RED);
        canvas.drawCircle(1000,1000,200,paint);

        canvas.translate(200,200);
        canvas.drawCircle(0,0,200,paint);
        canvas.drawLine(0,0,100,0,paint);
//        canvas.scale(0.5f,0.6f);
//        canvas.drawCircle(0,0,200,paint);
        canvas.rotate(30);
        paint.setColor(Color.RED);
        canvas.drawLine(0,0,100,0,paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(100,100,200,paint);

    }
}
