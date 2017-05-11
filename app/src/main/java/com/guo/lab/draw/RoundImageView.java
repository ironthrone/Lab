package com.guo.lab.draw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.guo.lab.R;

/**
 * Created by Administrator on 2017/5/11.
 */

public class RoundImageView extends AppCompatImageView {


    private int radius;
    private static final int default_raduis = 100;
    private PorterDuffXfermode porterDuffXfermode;
    private Paint paint;
    private Bitmap src;


    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        try {
            radius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_radius, default_raduis);
        }finally {
            typedArray.recycle();
        }
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        src = getRoundBitmap();
        canvas.drawBitmap(src,0,0,paint);

    }



    private Bitmap getFilterBitmap() {
        int size = getMeasuredHeight();
        Bitmap filter = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(filter);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(0,0,size,size),radius,radius,paint);
        return filter;
    }

    //在另一张画布上创建出圆角的bitmap
    //TODO 圆形Bitmap
    public Bitmap getRoundBitmap() {
        int size = getMeasuredHeight();
        if (src == null) {
            src = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(src);
            Drawable drawable = getDrawable();
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            float scale = Math.max(size*1.0f/width,size*1.0f/height);

            drawable.setBounds(0,0, ((int) (width * scale)) , ((int) (height * scale)));

            drawable.draw(canvas);

            Paint paint = new Paint();
//            paint.setFilterBitmap(false);
//            paint.setAntiAlias(true);
            //设置DSTIN xfermode，原画布上的内容为DST，新的绘制为src
            paint.setXfermode(porterDuffXfermode);
            canvas.drawBitmap(getFilterBitmap(),0,0,paint);

        }
        return src;
    }
}
