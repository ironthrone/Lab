package com.guo.lab.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.utils.SizeUtils;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/8/4 0004.
 */

public class CrossView extends View {

    private int lineColor = 0x20ffffff;

    private float width = SizeUtils.dp2px(2);
    private Paint paint;

    public CrossView(Context context) {
        this(context, null);
    }

    public CrossView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(width);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[0]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float size = getWidth();
        float halfSize = size / 2.0f;

        canvas.drawLine(0, halfSize, size, halfSize, paint);
        canvas.drawLine(halfSize, 0, halfSize, size, paint);
    }

}
