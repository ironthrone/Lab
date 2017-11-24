package com.guo.lab.anim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.guo.lab.R;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/25 0025.
 */

public class PolishView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private ValueAnimator fractionAnimm;
    private int backgroundPadding = 10;

    public PolishView(Context context) {
        super(context);
    }

    public PolishView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);


        Drawable light = getResources().getDrawable(R.drawable.light);
        bitmap = Bitmap.createBitmap(light.getIntrinsicWidth(), light.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        light.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        light.draw(canvas);

        fractionAnimm = ValueAnimator.ofFloat(0, 1)
                .setDuration(3000);
        fractionAnimm.setInterpolator(new LinearInterpolator());
        fractionAnimm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        fractionAnimm.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        RectF rectF = new RectF(backgroundPadding, backgroundPadding,
                width - backgroundPadding, height - backgroundPadding);

        paint.setColor(0x66ffffff);
        canvas.drawRoundRect(rectF, 5, 5, paint);
        canvas.save();
        float fraction = (float) fractionAnimm.getAnimatedValue();
        canvas.clipRect(backgroundPadding, 0, width - backgroundPadding, height);
        float xPosition = fraction * (bitmap.getWidth() + rectF.width()) - bitmap.getWidth();
        float yPosition = backgroundPadding - bitmap.getHeight() / 2;
        canvas.drawBitmap(bitmap, xPosition, yPosition, null);
        canvas.restore();
    }

    public void start() {
        fractionAnimm.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
