package com.guo.lab.anim;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ironthrone on 2017/7/6 0006.
 */

public class CircularRingProgressView extends View {
    private static final int abstrSize = 700;
    private static final int abstrRingWidth = 20;
    private static final int abstrRingInterval = 10;

    private int realSize;
    private int realRingWidth;
    private int realRingInterval;
    //outer radius

    private Paint paint;
    private Ring firstRing = new Ring();
    private Ring secondRing = new Ring();
    private Ring thirdRing = new Ring();


    //TODO replace with xml
    private int mainColor = 0xffff5959;
    // millisecond
    private RectF rectF;
    private Rotator rotator;

    public CircularRingProgressView(Context context) {
        super(context);
    }

    public CircularRingProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        rectF = new RectF();

        rotator = new Rotator();
    }

    private static class Ring {
        private static final int BASE_MAX_LENGTH = 80;
        private float startAngle;
        private float length;
        private float width;
        private float outerRadius;
        private float maxLength = BASE_MAX_LENGTH;

        private static Property<Ring, Keyframe> lengthProperty = new Property<Ring, Keyframe>(
                Keyframe.class, "length") {
            @Override
            public Keyframe get(Ring object) {
                return Keyframe.ofFloat(0, object.length);
            }

            @Override
            public void set(Ring object, Keyframe value) {
                object.length = ((Float) value.getValue());
            }
        };

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

    private class Rotator {
        private float differenceBeatInStartTime = 0.1f;
        private int period = 3000;
        private float baseMaxLength = 100;
        private float differenceBeatInLength = 0.2f;
        AngleTimeFunction firstFunction = new AngleTimeFunction() {
            @Override
            public float angle(float timeFraction) {
                return 360 * timeFraction;
            }
        };
        AngleTimeFunction secondFunction = new AngleTimeFunction() {
            @Override
            public float angle(float timeFraction) {
                if (timeFraction < differenceBeatInStartTime) {
                    return 0;
                } else {
                    return (timeFraction - differenceBeatInStartTime) / (1 - differenceBeatInStartTime) * 360;
                }
            }
        };

        AngleTimeFunction thirdFunction = new AngleTimeFunction() {
            @Override
            public float angle(float timeFraction) {
                float difference = 2 * differenceBeatInStartTime;
                if (timeFraction < difference) {
                    return 0;
                } else {
                    return ((timeFraction - difference) / (1 - difference) * 360);
                }
            }
        };
        private final AnimatorSet animatorSet;

        public Rotator() {
            firstRing.maxLength = 2f * Ring.BASE_MAX_LENGTH;
            secondRing.maxLength = 1.5f * Ring.BASE_MAX_LENGTH;

            ValueAnimator startAngleAnimator = ValueAnimator.ofFloat(0, 360);
            startAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
            startAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);

            startAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animation.getAnimatedFraction();
                    float fraction = animation.getAnimatedFraction();
                    firstRing.startAngle = firstFunction.angle(fraction);
                    secondRing.startAngle = secondFunction.angle(fraction);
                    thirdRing.startAngle = thirdFunction.angle(fraction);
                    invalidate();
                }
            });

            ObjectAnimator firstRingLengthAnimator = ObjectAnimator.ofFloat(firstRing, "length", 30, firstRing.maxLength, 8);
            firstRingLengthAnimator.setRepeatCount(ValueAnimator.INFINITE);

            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("length",
                    Keyframe.ofFloat(0, 30), Keyframe.ofFloat(0.1f, 30), Keyframe.ofFloat(0.5f, secondRing.maxLength),
                    Keyframe.ofFloat(1f, 10));

            ObjectAnimator secondRingLengthAnimator = ObjectAnimator.ofPropertyValuesHolder(secondRing, propertyValuesHolder);
            secondRingLengthAnimator.setRepeatCount(ValueAnimator.INFINITE);
            PropertyValuesHolder thirdRingValuesHolder = PropertyValuesHolder.ofKeyframe("length",
                    Keyframe.ofFloat(0, 30), Keyframe.ofFloat(0.2f, 30), Keyframe.ofFloat(0.6f, thirdRing.maxLength),
                    Keyframe.ofFloat(1f, 12));
            ObjectAnimator thirdRingLengthAnimator = ObjectAnimator.ofPropertyValuesHolder(thirdRing, thirdRingValuesHolder);
            thirdRingLengthAnimator.setRepeatCount(ValueAnimator.INFINITE);

            animatorSet = new AnimatorSet()
                    .setDuration(period);
            animatorSet.playTogether(startAngleAnimator, firstRingLengthAnimator, secondRingLengthAnimator, thirdRingLengthAnimator);
            animatorSet.end();
        }


        public void start() {
            if (!animatorSet.isStarted()) {

                animatorSet.start();
            }
        }

        public void end() {
            if (animatorSet.isStarted()) {
                animatorSet.end();
            }
        }
    }

    private interface AngleTimeFunction {
        float angle(float timeFraction);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int withMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int grantedWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int grantedHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        // from Exactly,At most determine final size of view
        //when mode is EXACTLY, we can directly use granted size
        if (withMode == View.MeasureSpec.EXACTLY) {
            width = grantedWidth;
        } else {
            //now measure mode is AT_MOST/UNSPECIFIED,
            if (getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                width = grantedWidth;
            } else {
                width = abstrSize;
            }

        }

        // only width make a difference,and its a square
        height = width;
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        realSize = getMeasuredWidth();
        realRingWidth = (int) (realSize * abstrRingWidth * 1.0 / abstrSize);
        realRingInterval = (int) (realSize * abstrRingInterval * 1.0 / abstrSize);


        firstRing.outerRadius = realSize / 2;
        firstRing.width = realRingWidth;

        secondRing.outerRadius = realSize / 2 - (realRingInterval + realRingWidth);
        secondRing.width = realRingWidth;


        thirdRing.outerRadius = realSize / 2 - 2 * (realRingWidth + realRingInterval);
        thirdRing.width = realRingWidth;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(mainColor);

        drawRing(canvas, firstRing);
        drawRing(canvas, secondRing);
        drawRing(canvas, thirdRing);
    }

    private void drawRing(Canvas canvas, Ring ring) {
        if (ring.length - 0 < 0.0001) return;

        int size = getMeasuredWidth();
        int halfSize = size / 2;
        rectF.left = halfSize - ring.outerRadius;
        rectF.top = rectF.left;
        rectF.right = halfSize + ring.outerRadius;
        rectF.bottom = rectF.right;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ring.width);
        paint.setColor(mainColor);

        float drawStartAngle = ring.startAngle - 90;
        float drawLength = -Math.min(drawStartAngle - (-90), ring.length);
        canvas.drawArc(rectF, ring.startAngle - 90, drawLength, false, paint);
    }

    public void start() {
        rotator.start();
    }

    public void end() {
        rotator.end();
    }
}
