package com.guo.lab.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;


import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.R;
import com.guo.lab.Toolbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ironthrone on 2017/7/11 0011.
 */

public class SpiralBalls extends View {

    private Paint paint;


    private Random random = new Random();
    private int pointColor;

    private int duration = 2000;
    private List<PolePoint> points = new ArrayList<>();

    private AnimatorSet groupSpiralAnimator;
    private Point center = new Point();
    private int repeatCount;
    private Subscription groupAnimatorSubscription;

    public SpiralBalls(Context context) {
        this(context, null);
    }

    public SpiralBalls(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.SpiralBalls);
            pointColor = typedArray.getColor(R.styleable.SpiralBalls_point_color,
                    Color.RED);
            typedArray.recycle();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(pointColor);
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        center.x = getWidth() / 2;
        center.y = getHeight() / 2;


    }


    private void updatePointsAndAnim() {
        points.clear();
        int oneSixWidth = getWidth() / 6;
        int oneSixHeight = getHeight() / 6;
        int baseSizeRadius = SizeUtils.dp2px(4);

        PolePoint point1 = PolePoint.ofXY(center.x + oneSixWidth + getPositionRandomAmount(),
                center.y - oneSixHeight * 2 + getPositionRandomAmount(), 2 * baseSizeRadius, center);

        PolePoint point2 = PolePoint.ofXY(center.x + oneSixWidth * 2 + getPositionRandomAmount(),
                center.y - oneSixHeight + getPositionRandomAmount(), baseSizeRadius, center);

        PolePoint point3 = PolePoint.ofXY(center.x + oneSixWidth * 2 + getPositionRandomAmount(),
                center.y + oneSixHeight * 2 + getPositionRandomAmount(), 2.5 * baseSizeRadius, center);
        PolePoint point4 = PolePoint.ofXY(center.x - oneSixWidth * 2 + getPositionRandomAmount(),
                center.y + oneSixHeight * 2 + getPositionRandomAmount(), baseSizeRadius, center);
        PolePoint point5 = PolePoint.ofXY(center.x - oneSixWidth * 2 + getPositionRandomAmount(),
                center.y - oneSixHeight + getPositionRandomAmount(), 2.5 * baseSizeRadius, center);

        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);

        List<Animator> spiralAnimators = new ArrayList<>();

        Interpolator point1Interpolar = new DelayInterpolator(0.15f);
        ValueAnimator point1spiral = new LogarithmicSpiralAnimator(point1);
        point1spiral.setInterpolator(point1Interpolar);

        spiralAnimators.add(point1spiral);
        ValueAnimator point2spiral = new LogarithmicSpiralAnimator(point2);
        spiralAnimators.add(point2spiral);
//
        Interpolator point3Interpolator = new DelayInterpolator(0.2f);
        ValueAnimator point3Spiral = new LogarithmicSpiralAnimator(point3);
        point3Spiral.setInterpolator(point3Interpolator);
        ObjectAnimator point3Alpha = ObjectAnimator.ofFloat(point3, "alpha", 0, 1);
        point3Alpha.setInterpolator(point3Interpolator);
        spiralAnimators.add(point3Spiral);
        spiralAnimators.add(point3Alpha);


        Interpolator point4Interpolator = new DelayInterpolator(0.3f);
        ValueAnimator point4Spiral = new LogarithmicSpiralAnimator(point4);
        point4Spiral.setInterpolator(point4Interpolator);
        ObjectAnimator point4Alpha = ObjectAnimator.ofFloat(point4, "alpha", 0, 1);
        point4Alpha.setInterpolator(point4Interpolator);
        spiralAnimators.add(point4Spiral);
        spiralAnimators.add(point4Alpha);


        Interpolator point5Interpolator = new DelayInterpolator(0.4f);
        ValueAnimator point5Spiral = new LogarithmicSpiralAnimator(point5);
        point5Spiral.setInterpolator(point5Interpolator);
        ObjectAnimator point5Alpha = ObjectAnimator.ofFloat(point5, "alpha", 0, 1);
        point5Alpha.setInterpolator(point5Interpolator);
        spiralAnimators.add(point5Spiral);
        spiralAnimators.add(point5Alpha);

        for (Animator animator :
                spiralAnimators) {
            ((ValueAnimator) animator).setRepeatCount(repeatCount);
        }


        groupSpiralAnimator = new AnimatorSet();
        groupSpiralAnimator.setDuration(duration);
        groupSpiralAnimator.playTogether(spiralAnimators);

    }

    private static class DelayInterpolator implements Interpolator {

        private float delayRatio = 0.2f;

        public DelayInterpolator(float delayRatio) {
            this.delayRatio = delayRatio;
        }

        @Override
        public float getInterpolation(float input) {
            if (input < delayRatio) {
                return 0;
            } else {
                return (input - delayRatio) / (1 - delayRatio);
            }
        }
    }

    private int getPositionRandomAmount() {
        int randomPosition = 20;
        return random.nextInt(randomPosition) - randomPosition / 2;

    }

    private static class PolePoint {
        private double radius;
        private double theta;
        private Point center;
        private double sizeRadius;
        private float alpha = 1;

        public PolePoint(double radius, double theta, double sizeRadius, Point center) {
            this.radius = radius;
            this.theta = theta;
            this.center = center;
            this.sizeRadius = sizeRadius;
        }

        public static PolePoint ofXY(int x, int y, double sizeRadius, Point center) {
            double deltaX = x - center.x;
            double deltaY = y - center.y;
            double radius = Math.hypot(deltaX, deltaY);
            double theta;
            if (deltaX > 0) {
                theta = Math.atan(deltaY / deltaX);
            } else {
                theta = Math.atan(deltaY / deltaX) + Math.PI;
            }
            return new PolePoint(radius, theta, sizeRadius, center);
        }

        public float x() {
            System.out.println(Math.toDegrees(theta));
            return (float) (center.x + radius * Math.cos(theta));
        }

        public float y() {
            return (float) (center.y + radius * Math.sin(theta));
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public float getAlpha() {
            return alpha;
        }

        @Override
        public String toString() {
            return "PolePoint{" +
                    "radius=" + radius +
                    ", theta=" + theta +
                    '}';
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (PolePoint point :
                points) {


            int color = (((int) (0xff * point.alpha)) << 24) | (pointColor & ~(0xff<<24));
            paint.setColor(color);

            canvas.drawCircle(point.x(),
                    point.y(), (float) point.sizeRadius, paint);

        }
    }

    /**
     * some method from ValueAnimator
     */
    private class LogarithmicSpiralAnimator extends ValueAnimator {
        private PolePoint benchPoint;
        private PolePoint targetPoint;
        private double a;
        //control clock direction
        private double b = -0.3;
        private double omega;
        private double minRaius = 50;
        private final double minTheta;


        public LogarithmicSpiralAnimator(PolePoint point) {
            // copy a local instance
            this.benchPoint = new PolePoint(point.radius, point.theta + 16 * Math.PI, point.sizeRadius, point.center);
            this.targetPoint = point;

            /**
             * solve minTheta respect to minRadius
             * radius = a * e^(b * theta)
             * minRadius = a * e^(b * minTheta)
             * solve this function group to get minTheta
             */
            minTheta = benchPoint.theta - 1 / b * Math.log(benchPoint.radius / minRaius);

            setIntValues(0, 1);
            addUpdateListener(toCenterUpdater);
        }


        AnimatorUpdateListener toCenterUpdater = new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                // not real elapsed time, just animator fraction to time
                long currentTime = (long) (animation.getDuration() * fraction);
                targetPoint.theta = benchPoint.theta - omega * currentTime;
                targetPoint.radius = (a * Math.exp(b * targetPoint.theta));

                invalidate();
                System.out.println(targetPoint);
            }
        };


        private void updateCoeff(long duration) {
            omega = (benchPoint.theta - minTheta) / duration;
            a = (benchPoint.radius) / Math.exp(b * benchPoint.theta);
        }

        @Override
        public ValueAnimator setDuration(long duration) {
            updateCoeff(duration);
            return super.setDuration(duration);
        }
    }



    public void setRepeatCount(int count) {
        this.repeatCount = count;
    }

    public void start() {

        updatePointsAndAnim();
        groupSpiralAnimator.start();
//        groupAnimatorSubscription = Observable.range(0, 10)
//                .onBackpressureBuffer()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        groupSpiralAnimator.start();
//                    }
//                });
    }

    public void end() {
//        groupAnimatorSubscription.unsubscribe();
        groupSpiralAnimator.end();
    }
}
