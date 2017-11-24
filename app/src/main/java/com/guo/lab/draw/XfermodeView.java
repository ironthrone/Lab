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

import com.guo.lab.Toolbox;

/**
 * Created by Administrator on 2017/5/10.
 */

public class XfermodeView extends View {

    private PorterDuffXfermode add;
    private Bitmap src;
    private Bitmap dst;
    private Paint paint;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        dst = getDst(100);
        src = getSrc(100, 100);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int desireSpec = MeasureSpec.makeMeasureSpec(4000, MeasureSpec.EXACTLY);
//        //scroll指定的高度模式是Unspecfied，导致出现measuredHeight为0
//        setMeasuredDimension(widthMeasureSpec, desireSpec);
        int[] measured = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measured[0], measured[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //同样的不支持硬件加速

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

        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            //在上面离屏bitmap（an offscreen bitmap.）上绘制
            int layer = canvas.saveLayer(new RectF(0,0,getRight(),getBottom()),paint,Canvas.ALL_SAVE_FLAG);
            canvas.translate(0, 160 * mode.ordinal());

            canvas.drawBitmap(dst, 100, 0, paint);
            paint.setXfermode(new PorterDuffXfermode(mode));
            canvas.drawBitmap(src, 150, 50, paint);
            paint.setXfermode(null);
            canvas.drawText(mode.name(), 300, 0, paint);
            canvas.restoreToCount(layer);
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
