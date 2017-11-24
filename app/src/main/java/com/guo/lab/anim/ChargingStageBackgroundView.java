package com.guo.lab.anim;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/31 0031.
 */

public class ChargingStageBackgroundView extends View {

    private final long duration = 2000;
    private int pointInnerRadius = SizeUtils.dp2px(5);
    private int pointOuterRaidus = SizeUtils.dp2px(9);
    private Paint paint;
    private int activeColor = Color.WHITE;
    private int deactiveColor = 0x88ffffff;
    private ValueAnimator degreeAnim;
    private float ringWidth = SizeUtils.dp2px(2);


    public ChargingStageBackgroundView(Context context) {
        this(context, null);
    }

    public ChargingStageBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        degreeAnim = ValueAnimator.ofFloat(0, 360)
                .setDuration(duration);
        degreeAnim.setInterpolator(new LinearInterpolator());

        degreeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        degreeAnim.setRepeatCount(ValueAnimator.INFINITE);
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
        int size = Math.min(width, height) / 2;
        float radius = size / 2 - Math.max(pointOuterRaidus, ringWidth / 2);
        float centerX = size/2;
        float centerY = size/2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        if (degreeAnim.isRunning()) {
            paint.setColor(activeColor);
            canvas.drawCircle(centerX, centerY, radius, paint);
            float degree = (float) degreeAnim.getAnimatedValue();
            canvas.save();
            canvas.rotate(degree,centerX,centerX);
            paint.setStyle(Paint.Style.FILL);
            float pointX = centerX;
            float pointY = pointOuterRaidus;
            canvas.drawCircle(pointX,pointY,pointInnerRadius,paint);

            paint.setColor(deactiveColor);
            canvas.drawCircle(pointX,pointY,pointOuterRaidus,paint);
            canvas.restore();
        } else {
            paint.setColor(deactiveColor);
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }


    public void activate() {
        degreeAnim.start();
    }

    public void deactivate() {
        degreeAnim.end();
    }
}

