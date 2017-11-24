package com.guo.lab.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/5/11.
 */

public class PathView extends View {

    private Paint paint;
    private Path path;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        path = new Path();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path2 = new Path();
        path2.addArc(new RectF(100, 100, 200, 200), 0, 360);
        path.addCircle(100,100,100, Path.Direction.CCW);
        path.addPath(path2);
        path.op(path2,Path.Op.UNION);
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
//        path.addCircle(120,120,100, Path.Direction.CCW);
//        path.addRect(120,120,240,240,Path.Direction.CCW);

//        Path path = new Path();
//        path.moveTo(100,100);
//        path.lineTo(400,400);
//        path.lineTo(500,500);
//        path.rLineTo(-200,200);
//        path.close();

//        path.quadTo(100,200,300,400);
//        path.cubicTo(100,100,300,50,400,200);

//        path.addCircle(200,200,100, Path.Direction.CW);

        canvas.drawPath(path,paint);
    }

}
