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
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;


import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/6 0006.
 */

public class PowerSaveMultiRingRotateView extends View {

    private static final String TAG = PowerSaveMultiRingRotateView.class.getSimpleName();
    int SIZE_UNIT = 700;
    int FIRST_RING_WIDTH_UNIT = 15;
    int SECOND_RING_WIDTH_UNIT = 12;
    int THIRD_RING_WIDTH_UNIT = 9;
    int RING_INTERVAL_UNIT = 5;
    int ringInterval;

    private Paint paint;
    // millisecond
    private RectF rectF;
    private int defaultSize = SizeUtils.dp2px(200);
    private int ringColor;
    private Ring firstRing;
    private Ring secondRing;
    private Ring thirdRing;
    private AnimatorSet animatorSet;

    private static final int STATE_IDLE = 1;
    private static final int STATE_ANIM = 2;
    private int state = STATE_IDLE;

    private static final int SUGGEST_PERIOD = 750;
    private int duration = 3000;
    private int rotateCount = duration/SUGGEST_PERIOD;
    private int period = SUGGEST_PERIOD;
    //interval 30 degree，this is time which is converted from degree
    private int ringStartIntervalDegree = 45;


    public PowerSaveMultiRingRotateView(Context context) {
        this(context, null);
    }

    public PowerSaveMultiRingRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        ringColor = getContext().getResources().getColor(android.R.color.holo_red_dark);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        rectF = new RectF();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    /**
     * after receiver new duration ,update period,rotateCount,ringStartInterval
     */
    public PowerSaveMultiRingRotateView setDuration(int duration) {
        this.duration = duration;
        float decimalCount = duration * 1f / SUGGEST_PERIOD;
        if (decimalCount - Math.floor(decimalCount) < 0.5) {
            rotateCount = ((int) Math.floor(decimalCount));
        } else {
            rotateCount = (int) (Math.floor(decimalCount) + 1);
        }
        period = (int) (duration * 1f / rotateCount);
        return this;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int grantedWidth = MeasureSpec.getSize(widthMeasureSpec);
        int grantedHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        // from Exactly,At most determine final size of view
        //when mode is EXACTLY, we can directly use granted size
        if (withMode == MeasureSpec.EXACTLY) {
            width = grantedWidth;
        } else {
            //now measure mode is AT_MOST/UNSPECIFIED,
            if (getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                width = grantedWidth;
            } else {
                width = defaultSize;
            }
        }

        // only width make a difference,and its a square
        height = width;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (state == STATE_ANIM) {

            drawRing(canvas, firstRing);
            Log.d(TAG, "firstRing startAngle: " + firstRing.startAngle);

            drawRing(canvas, secondRing);
            drawRing(canvas, thirdRing);
        } else {
            canvas.drawColor(Color.TRANSPARENT);
            Log.d(TAG, "clear canvas");
        }
    }

    private void drawRing(Canvas canvas, Ring ring) {
//        if (ring.length - 0 < 0.0001) return;

        int size = getMeasuredWidth();
        int halfSize = size / 2;
        rectF.left = halfSize - ring.outerRadius;
        rectF.top = rectF.left;
        rectF.right = halfSize + ring.outerRadius;
        rectF.bottom = rectF.right;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ring.width);
        paint.setColor(ringColor);

        float drawStartAngle = ring.startAngle - 90;
        Log.d(TAG, " startAngle: " + drawStartAngle);

        float drawLength = -Math.min(ring.length, drawStartAngle + 90);

        Log.d(TAG, " drawLength: " + drawLength);
        canvas.drawArc(rectF, drawStartAngle, drawLength, false, paint);
    }

    /**
     * 使用ValueAnimator，定义一个做确定值变化的ValueAnimator子类，实例化的对象配置updatelistener，delay???
     */
    public void start() {
        createRings();

        if (animatorSet != null && animatorSet.isStarted()) {
            animatorSet.end();
        }
        int ringStartInterval = (int) (ringStartIntervalDegree * period / 360f);
        animatorSet = new AnimatorSet();
        float rotatedDegrees = rotateCount * 360;
        PropertyValuesHolder angleHolder = PropertyValuesHolder.ofFloat("startAngle", 0, rotatedDegrees);
        ObjectAnimator firstRingAngle = ObjectAnimator.ofPropertyValuesHolder(firstRing, angleHolder);
        firstRingAngle.setDuration(duration);
        firstRingAngle.setInterpolator(new LinearInterpolator());
        ObjectAnimator secondRingAngle = ObjectAnimator.ofPropertyValuesHolder(secondRing, angleHolder);
        secondRingAngle.setDuration(duration);
        secondRingAngle.setInterpolator(new LinearInterpolator());
        secondRingAngle.setStartDelay(ringStartInterval);
        ObjectAnimator thirdRingAngle = ObjectAnimator.ofPropertyValuesHolder(thirdRing, angleHolder);
        thirdRingAngle.setDuration(duration);
        thirdRingAngle.setInterpolator(new LinearInterpolator());
        thirdRingAngle.setStartDelay(ringStartInterval * 2);

        int lengthIncreaseTime = (int) (period * 0.5f);
        int lengthDecreaseTime = (int) (period * 0.5f);
        float lengthIncreasePortion = lengthIncreaseTime * 1f / duration;
        float lengthDecreasePartion = lengthDecreaseTime * 1f / duration;
        PropertyValuesHolder lengthHolder = PropertyValuesHolder.ofKeyframe("length",
                Keyframe.ofFloat(0, 0),
                Keyframe.ofFloat(lengthIncreasePortion, Ring.maxLength),
                Keyframe.ofFloat(1 - lengthDecreasePartion, Ring.maxLength),
                Keyframe.ofFloat(1, 0)
        );
        ObjectAnimator firstLengthAnim = ObjectAnimator.ofPropertyValuesHolder(firstRing, lengthHolder);
        firstLengthAnim.setDuration(duration);
        firstLengthAnim.setInterpolator(new LinearInterpolator());
        ObjectAnimator secondLengthAnim = ObjectAnimator.ofPropertyValuesHolder(secondRing, lengthHolder);
        secondLengthAnim.setDuration(duration);
        secondLengthAnim.setStartDelay(ringStartInterval);
        secondLengthAnim.setInterpolator(new LinearInterpolator());
        ObjectAnimator thirdLengthAnim = ObjectAnimator.ofPropertyValuesHolder(thirdRing, lengthHolder);
        thirdLengthAnim.setDuration(duration);
        thirdLengthAnim.setStartDelay(ringStartInterval * 2);
        thirdLengthAnim.setInterpolator(new LinearInterpolator());
        ValueAnimator invalidator = ValueAnimator.ofFloat(0, 0);
        invalidator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        invalidator.setDuration(duration + ringStartInterval * 2);
        animatorSet.playTogether(
                firstRingAngle,
                secondRingAngle,
                thirdRingAngle,
                firstLengthAnim,
                secondLengthAnim,
                thirdLengthAnim,
                invalidator
        );
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = STATE_IDLE;
                //refresh after change to idle
                invalidate();
            }
        });
        state = STATE_ANIM;
        animatorSet.start();
        Log.d(TAG, "duration: " + duration
                + "\nrotateCount: " + rotateCount);
    }


    private void createRings() {

        if (firstRing == null) {
            float size = getMeasuredWidth();
            ringInterval = (int) (size * RING_INTERVAL_UNIT / SIZE_UNIT);

            firstRing = new Ring();
            firstRing.outerRadius = size / 2 - 15;
            firstRing.width = (int) (size * FIRST_RING_WIDTH_UNIT / SIZE_UNIT);

            secondRing = new Ring();
            secondRing.width = (int) (size * SECOND_RING_WIDTH_UNIT / SIZE_UNIT);
            secondRing.outerRadius = firstRing.outerRadius - (ringInterval + firstRing.width);


            thirdRing = new Ring();
            thirdRing.width = (int) (size * THIRD_RING_WIDTH_UNIT / SIZE_UNIT);
            thirdRing.outerRadius = secondRing.outerRadius - (secondRing.width + ringInterval);
        }
    }

    public void end() {
        if (animatorSet != null && animatorSet.isStarted()) {
            animatorSet.end();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animatorSet != null) {
            Toolbox.removeAllListener(animatorSet);
        }
    }

    private static class Ring {
        static final float maxLength = 180;

        float startAngle;
        float length;
        float outerRadius;
        float width;


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

        @Keep
        public float getStartAngle() {
            return startAngle;
        }

        @Keep
        public float getLength() {
            return length;
        }

        @Keep
        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        @Keep
        public void setLength(float length) {
            this.length = length;
        }

    }

}
