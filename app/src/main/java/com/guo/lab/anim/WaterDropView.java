package com.guo.lab.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/19 0019.
 */

public class WaterDropView extends View {

    private Paint paint;
    private Path path;

    public WaterDropView(Context context) {
        super(context);
    }

    public WaterDropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        path.moveTo(0,100);
        path.quadTo(0,200,100,200);
        path.quadTo(200,200,200,100);
        path.quadTo(200,0,100,0);
        path.quadTo(0,0,0,100);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        canvas.drawPath(path,paint);
    }
}
