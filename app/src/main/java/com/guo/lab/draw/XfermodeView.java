package com.guo.lab.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.io.File;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/5/10.
 */

public class XfermodeView extends View {

    private PorterDuffXfermode add;
    private Bitmap src;
    private Bitmap dst;
    private Paint paint;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        dst = getDst(100);
        src = getSrc(100, 100);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireSpec = MeasureSpec.makeMeasureSpec(4000, MeasureSpec.EXACTLY);
        //scroll指定的高度模式是Unspecfied，导致出现measuredHeight为0
        super.onMeasure(widthMeasureSpec, desireSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //同样的不支持硬件加速
        //TODO 但是不注释掉这一句，就没有内容显示，好奇快

//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setFilterBitmap(false);
        paint.setDither(false);

        paint.setTextSize(30);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        canvas.translate(0, 50);
        canvas.drawBitmap(dst, 100, 0, paint);
        canvas.drawText("dst", 240, 0, paint);
        canvas.translate(0, 120);
        canvas.drawBitmap(src, 100, 0, paint);
        canvas.drawText("src", 240, 0, paint);

        //TODO 有bug，和期望的显示不一样
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            canvas.translate(0, 160);
            canvas.drawBitmap(dst, 100, 0, paint);
            paint.setXfermode(new PorterDuffXfermode(mode));
            canvas.drawBitmap(src, 150, 50, paint);
            paint.setXfermode(null);
            canvas.drawText(mode.name(), 300, 0, paint);
        }
    }

    private Bitmap getDst(int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        return bitmap;
    }

    private Bitmap getSrc(int with, int height) {
        Bitmap bitmap = Bitmap.createBitmap(with, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, with, height, paint);
        return bitmap;
    }
}
