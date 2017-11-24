package com.guo.lab.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/8/3 0003.
 */

public class HomeRingView extends View {
    private int duration = 2000;
    private AnimatorSet animator;
    private Paint paint;

    public HomeRingView(Context context) {
        this(context, null);
    }

    public HomeRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
    }

    private void createAnimator() {
        ObjectAnimator startAngleFirstPeriod = ObjectAnimator.ofFloat(ring, "startAngle",
                0, 360)
                .setDuration(duration);
        Path startAngleChangePath = new Path();
        startAngleChangePath.lineTo(0.5f,0.5f);
        startAngleChangePath.lineTo(0.9f, 1);
        startAngleChangePath.lineTo(1, 1);
        startAngleFirstPeriod.setInterpolator(PathInterpolatorCompat.create(startAngleChangePath));
        startAngleFirstPeriod.setRepeatCount(3);
        ObjectAnimator startAngleSecondPeriod = ObjectAnimator.ofFloat(ring, "startAngle", 0, 360)
                .setDuration(duration);
        startAngleSecondPeriod.setInterpolator(new LinearInterpolator());

        AnimatorSet startAngleAnim = new AnimatorSet();
        startAngleAnim.playSequentially(startAngleFirstPeriod,startAngleSecondPeriod);

        int firstPeriodLength = 40;
//        Path lengthFirstPeroidPath = new Path();
//        lengthFirstPeroidPath.lineTo(0.25f,1);
//        lengthFirstPeroidPath.lineTo(0.75f,1);
//        lengthFirstPeroidPath.lineTo(1,0);
        PropertyValuesHolder lengthFirstPeriodHolder = PropertyValuesHolder.ofKeyframe("length",
                Keyframe.ofFloat(0,0),
                Keyframe.ofFloat(0.25f,firstPeriodLength),
                Keyframe.ofFloat(0.75f,firstPeriodLength),
                Keyframe.ofFloat(1,0)
        );

        ObjectAnimator lengthFirstPeriod = ObjectAnimator.ofPropertyValuesHolder(ring, lengthFirstPeriodHolder);
        lengthFirstPeriod.setDuration(duration);
        int secondPeriodLength = 130;
        PropertyValuesHolder lengthSecondPeriodHolder = PropertyValuesHolder.ofKeyframe("length",
                Keyframe.ofFloat(0, 0),
                Keyframe.ofFloat(0.25f, 90),
                Keyframe.ofFloat(0.6f, secondPeriodLength),
                Keyframe.ofFloat(1, 0)
        );


        ObjectAnimator lengthSecondPeriod = ObjectAnimator.ofPropertyValuesHolder(ring, lengthSecondPeriodHolder)
                .setDuration(duration);
        lengthSecondPeriod.setRepeatCount(2);
        ObjectAnimator lengthThirdPeriod = ObjectAnimator.ofFloat(ring, "length", 0, 360);
        lengthThirdPeriod.setInterpolator(new LinearInterpolator());
        lengthThirdPeriod.setDuration(duration);
        AnimatorSet lengthAnimator = new AnimatorSet();
        lengthAnimator.playSequentially(lengthFirstPeriod, lengthSecondPeriod, lengthThirdPeriod);
        lengthAnimator.setInterpolator(new LinearInterpolator());
        lengthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ring.length = 360;
                ring.startAngle = 360;
                invalidate();
                animate()
                        .alpha(0)
                        .setDuration(500)
                        .start();
            }
        });

        ValueAnimator invalidator = ValueAnimator.ofFloat(0, 1)
                .setDuration(duration * 5);
        invalidator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        animator = new AnimatorSet();
        animator.playTogether(startAngleAnim, lengthAnimator, invalidator);

    }

    public HomeRingView setDuration(int duration) {
        this.duration = duration;
        return this;
    }


    public void start() {
        createAnimator();
        animator.start();
    }

    public void end() {
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
    }

    private Ring ring = new Ring();
    private float ringWidth = SizeUtils.dp2px(5);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[0]);
    }


    private RectF bound = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        float centerX = width / 2.0f;
        float centerY = centerX;
        float radius = (getWidth() - ringWidth) / 2.0f;
        bound.left = centerX - radius;
        bound.right = centerX + radius;
        bound.top = bound.left;
        bound.bottom = bound.right;
        // change coordinator
        float startAngle = ring.startAngle - 90;
        float endAngle = Math.max(startAngle - ring.length, -90);
        // if startAngle close to endAngle, canvas may draw a whole ring,so ignore these situation
        if(Math.abs(startAngle - endAngle) < 0.001) return;
//        if(startAngle < endAngle) return;

        canvas.drawArc(bound, startAngle, endAngle-startAngle, false, paint);

//        canvas.drawArc(bound, 0, -1, false, paint);


    }

    private class Ring {
        private float startAngle;
        private float length;

        public float getStartAngle() {
            return startAngle;
        }

        public float getLength() {
            return length;
        }

        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        public void setLength(float length) {
            this.length = length;
        }
    }
}
