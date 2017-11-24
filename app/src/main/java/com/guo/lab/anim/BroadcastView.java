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

import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.R;
import com.guo.lab.Toolbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironthrone on 2017/7/11 0011.
 */

public class BroadcastView extends View {

    private int duration = 1500;
    private int ringCount = 3;
    private AnimatorSet animatorSet;
    private Paint paint;
    private int repeatCount = ValueAnimator.INFINITE;
    private float ringRadius = 0;

    private float ringWidth = 0;
    private float ringAlpha = 0.2f;
    private int ringColor;

    public BroadcastView(Context context) {
        this(context, null);
    }

    private List<Ring> rings = new ArrayList<>();

    public BroadcastView(Context context, @Nullable AttributeSet attrs) {
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
        int broadcastInterval = duration / ringCount;
        float viewRadius = (float) Math.max(getWidth(), getHeight()) / 2;
        List<Animator> ringAnimators = new ArrayList<>();
        for (int i = 0; i < ringCount; i++) {
            Ring ring = new Ring(ringRadius, ringWidth, 0);
            ring.color = ringColor;

            rings.add(ring);
            PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofFloat("radius", ring.radius,viewRadius);
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", ring.width,ring.width * 0.5f);

            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofKeyframe("alpha",
                    Keyframe.ofFloat(0,0f),
                    Keyframe.ofFloat(0.3f,ringAlpha),
                    Keyframe.ofFloat(1,0f));
            ObjectAnimator ringAnimator = ObjectAnimator.ofPropertyValuesHolder(ring, radiusHolder,
                    widthHolder, alphaHolder)
                    .setDuration(duration);
            ringAnimator.setStartDelay(broadcastInterval * i);
            ringAnimator.setRepeatCount(repeatCount);
            ringAnimators.add(ringAnimator);
        }
        ValueAnimator invalidater = ValueAnimator.ofFloat(0, 1)
                .setDuration(duration);
        invalidater.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
//                postInvalidateOnAnimation();
//                postInvalidate();
            }
        });
        invalidater.setRepeatCount(repeatCount);
        ringAnimators.add(invalidater);
        animatorSet.playTogether(ringAnimators);

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
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        if (animatorSet != null && animatorSet.isRunning()) {

            for (Ring ring :
                    rings) {
                paint.setStrokeWidth(ring.width);
                paint.setAlpha((int) (255 * ring.alpha));
                canvas.drawCircle(centerX, centerY, ring.radius, paint);
            }
        }
    }

    private class Ring {
        private float radius;
        private float width;
        private float alpha;
        private int color;

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
