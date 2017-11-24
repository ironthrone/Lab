package com.guo.lab.anim;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.guo.lab.R;
import com.guo.lab.Toolbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironthrone on 2017/7/11 0011.
 * no improve than BroadcastView
 */

public class BroadcastView2 extends View {

    private int duration = 1500;
    private int ringCount = 3;
    private AnimatorSet animatorSet;
    private Paint paint;
    private int repeatCount = ValueAnimator.INFINITE;
    private float ringRadius = 0;

    private float ringWidth = 0;
    private float ringAlpha = 0.2f;
    private int ringColor;
    private Ring ring1;
    private Ring ring2;
    private Ring ring3;
    private float startRingRadius;
    private float endRingRadius;
    private float startWidth;
    private float endWidth;
    private Ring ring6;
    private Ring ring5;
    private Ring ring4;

    public BroadcastView2(Context context) {
        this(context, null);
    }

    private List<Ring> rings = new ArrayList<>();

    public BroadcastView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BroadcastView);
            ringRadius = typedArray.getDimensionPixelSize(R.styleable.BroadcastView_ringRadius, 0);
            ringWidth = typedArray.getDimensionPixelSize(R.styleable.BroadcastView_ringWidth, 0);
        }
        ringColor = Color.RED;
        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRingCount(int ringCount) {
        this.ringCount = ringCount;
    }

    public void start() {
        createAnimator();
        animatorSet.start();
    }

    private void createAnimator() {
        if (ringRadius == 0) {
            ringRadius = Math.min(getHeight(), getWidth()) * 0.4f;
        }
        if (ringWidth == 0) {
            ringWidth = ringRadius * 0.05f;
        }
        animatorSet = new AnimatorSet();
        List<Animator> ringAnimators = new ArrayList<>();

        ring1 = new Ring();
        ring2 = new Ring();
        ring3 = new Ring();
        ring4 = new Ring();
        ring5 = new Ring();
        ring6 = new Ring();
        startRingRadius = Math.min(getHeight(), getWidth()) * 0.4f;
        endRingRadius = (float) Math.max(getWidth(), getHeight()) / 2;
        startWidth = startRingRadius * 0.05f;
        endWidth = startWidth * 0.5f;

        ringAnimators.add(new RingAnimator(ring1));
        ringAnimators.add(new RingAnimator(ring2));
        ringAnimators.add(new RingAnimator(ring3));
        ringAnimators.add(new RingAnimator(ring4));
        ringAnimators.add(new RingAnimator(ring5));
        ringAnimators.add(new RingAnimator(ring6));
        ValueAnimator invalidater = ValueAnimator.ofFloat(0, 1)
                .setDuration(duration);
        invalidater.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        ringAnimators.add(invalidater);
        invalidater.setRepeatCount(repeatCount);


        animatorSet.playTogether(ringAnimators);

    }

    private  class RingAnimator extends ValueAnimator{
        public RingAnimator(final Ring ring) {
            super();
            setFloatValues(0,1);
            addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    ring.setRadius(startRingRadius + (endRingRadius - startRingRadius) * fraction);
                    ring.setWidth(startWidth + (endWidth - startWidth) * fraction);
                    ring.setAlpha(1-fraction);
                }
            });
            setRepeatCount(repeatCount);
        }
    }

    public void end() {
        animatorSet.end();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animatorSet != null && animatorSet.isRunning()) {
            drawRing(canvas,ring1);
            drawRing(canvas,ring2);
            drawRing(canvas,ring3);
            drawRing(canvas,ring4);
            drawRing(canvas,ring5);
            drawRing(canvas,ring6);
        }
    }

    private void drawRing(Canvas canvas,Ring ring) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        paint.setStrokeWidth(ring.width);
        paint.setAlpha((int) (255 * ring.alpha));
        canvas.drawCircle(centerX, centerY, ring.radius, paint);
    }

    private class Ring {
        private float radius;
        private float width;
        private float alpha;
        private int color;

        public Ring() {

        }
        public Ring(float radius, float width, float alpha) {
            this.radius = radius;
            this.width = width;
            this.alpha = alpha;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public float getRadius() {
            return radius;
        }

        public float getWidth() {
            return width;
        }

        public float getAlpha() {
            return alpha;
        }
    }

}
