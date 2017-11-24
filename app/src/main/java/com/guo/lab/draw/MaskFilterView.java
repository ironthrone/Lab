package com.guo.lab.draw;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/5/10.
 */

public class MaskFilterView extends View {

    private Paint paint;

    public MaskFilterView(Context context) {
        super(context);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    private BlurMaskFilter innerBlurMaskFilter = new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER);
    private BlurMaskFilter outerBlurMaskFilter = new BlurMaskFilter(2, BlurMaskFilter.Blur.OUTER);
    private BlurMaskFilter solidBlurMaskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID);
    private BlurMaskFilter normalBlurMaskFilter = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //需要关闭硬件加速，不然看到的都是模糊一片
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        paint.setColor(Color.RED);
        paint.setTextSize(68);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        canvas.translate(150,0);
        canvas.translate(0, 200);
        paint.setMaskFilter(normalBlurMaskFilter);
        canvas.drawText("恰如猛虎卧荒丘",0,0,paint);

        canvas.translate(0, 200);
        paint.setMaskFilter(innerBlurMaskFilter);
        canvas.drawText("恰如猛虎卧荒丘",0,0,paint);

        canvas.translate(0, 200);
        paint.setMaskFilter(outerBlurMaskFilter);
        canvas.drawText("恰如猛虎卧荒丘",0,0,paint);

        canvas.translate(0, 200);
        paint.setMaskFilter(solidBlurMaskFilter);
        canvas.drawText("恰如猛虎卧荒丘",0,0,paint);

    }
}
