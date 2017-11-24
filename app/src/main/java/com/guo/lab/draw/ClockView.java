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

public class ClockView extends View {

    private Paint paint;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    private int radius = 200;
    private int diameter = 2 * radius;
    private static int indexCount = 60;

    /**
     * 使用到了Canvas#save,restore,translate,rotate,drawLine,drawCircle,drawTextOnPath
     * Path#arcTo
     * Paint#setStyle,setTextSize
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(diameter, diameter);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radius, paint);

        //绘制刻度
        canvas.save();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        for (int i = 0; i < indexCount; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0, -radius, 0, -radius - 20, paint);
                String notion = i + "";
                canvas.drawText(notion, -paint.getTextSize() * notion.length() / 2, -radius - 30, paint);

            } else {

                canvas.drawLine(0, -radius, 0, -radius - 5, paint);
            }
            canvas.rotate(360 / indexCount);
        }
        canvas.restore();


        //绘制表上文字
        Path path = new Path();
        int innerRadius = radius - 20;
        path.arcTo(new RectF(-innerRadius, -innerRadius, innerRadius, innerRadius), 180, 100, false);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setStrokeWidth(1);
        canvas.drawTextOnPath("旋转表面", path, 0, 0, paint);

        //绘制指针
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(0, 0, 10, paint);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, 10, paint);
        canvas.drawLine(0, 0, 0, -(radius - 30), paint);

    }
}
