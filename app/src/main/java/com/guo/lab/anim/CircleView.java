package com.guo.lab.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ironthrone on 2017/7/13 0013.
 */

public class CircleView extends View {

    private int color = Color.RED;
    private Paint paint;

    public CircleView(Context context) {
        this(context,null);

    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = centerX;

        paint.setColor(color);
        canvas.drawCircle(centerX,centerY,radius,paint);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }
}
