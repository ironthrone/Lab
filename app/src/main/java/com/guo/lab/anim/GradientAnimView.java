package com.guo.lab.anim;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/27 0027.
 */

public class GradientAnimView extends View {

    private AnimatorSet changeColorAnim;
    private Paint paint;
    private Rect drawBound;
    private int startColor = Color.WHITE;
    private int endColor = Color.WHITE;
    private LinearGradient linearGradient;
    private long duration = 2000;

    public GradientAnimView(Context context) {
        super(context);
    }

    public GradientAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);

        if (drawBound == null) {
            drawBound = new Rect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (drawBound != null) {
            if (linearGradient == null || (changeColorAnim != null && changeColorAnim.isRunning())) {
                linearGradient = new LinearGradient(drawBound.left, drawBound.top, drawBound.left, drawBound.bottom,
                        startColor, endColor, Shader.TileMode.CLAMP);
            }
            paint.setShader(linearGradient);
            canvas.drawRect(drawBound, paint);
        }

    }


    public void changeTo(final int toStartColor, int toEndColor) {
        if (changeColorAnim != null && changeColorAnim.isRunning()) {
            changeColorAnim.end();
        }

        ValueAnimator startColorAnim = ValueAnimator.ofInt(startColor, toStartColor)
                .setDuration(duration);
        startColorAnim.setInterpolator(new LinearInterpolator());
        startColorAnim.setEvaluator(new ArgbEvaluator());
        startColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startColor = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator endColorAnim = ValueAnimator.ofInt(endColor, toEndColor)
                .setDuration(duration);
        endColorAnim.setInterpolator(new LinearInterpolator());
        endColorAnim.setEvaluator(new ArgbEvaluator());
        endColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endColor = (int) animation.getAnimatedValue();
            }
        });

        changeColorAnim = new AnimatorSet();
        changeColorAnim.playTogether(startColorAnim, endColorAnim);
        changeColorAnim.start();
    }
}
