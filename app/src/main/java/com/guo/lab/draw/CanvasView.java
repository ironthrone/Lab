package com.guo.lab.draw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.guo.lab.R;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CanvasView extends View {

    //paint包含着信息关于怎么绘制位图，形状，文本
    private Paint paint;
    private BitmapShader shader;
    private LinearGradient linearGradient;
    private Bitmap bitmap1;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wusong);

        Drawable light = getResources().getDrawable(R.drawable.light);
        bitmap1 = Bitmap.createBitmap(light.getIntrinsicWidth(), light.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        light.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        light.draw(canvas);


        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 6, true);


        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        linearGradient = new LinearGradient(0, 0, 100, 0, new int[]{0x00ffffff, Color.WHITE, 0x00ffffff},
                null
                , Shader.TileMode.REPEAT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Path path1 = new Path();
        path1.moveTo(300, 300);
        path1.rLineTo(200, 0);
        path1.rLineTo(0, 200);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap1, 300, 400, null);
        paint.setShader(linearGradient);
        canvas.drawPath(path1, paint);
        paint.setShader(null);

    }
}
