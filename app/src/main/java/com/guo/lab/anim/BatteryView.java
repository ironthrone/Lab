package com.guo.lab.anim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.guo.lab.R;
import com.guo.lab.Toolbox;

/**
 * Created by ironthrone on 2017/7/25 0025.
 */

public class BatteryView extends View {

    private ValueAnimator rightAnim;
    private Paint paint;
    private int batteryLevel;
    private Path lightning;
    private RectF batteryRectF;
    private FloatingBubblesDrawable floatingBubblesDrawable;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rightAnim = ValueAnimator.ofFloat(0, 1)
                .setDuration(10000);
        rightAnim.setInterpolator(new LinearInterpolator());
        rightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });


        rightAnim.setRepeatCount(ValueAnimator.INFINITE);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        floatingBubblesDrawable = new FloatingBubblesDrawable();
        floatingBubblesDrawable.setTarget(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float roundRadius = 10;
        int width = getWidth();
        int height = getHeight();


        if (batteryRectF == null) {
            batteryRectF = new RectF();
            batteryRectF.left = 0;
            batteryRectF.right = width;
            batteryRectF.top = 0;
            batteryRectF.bottom = height;
        }


        if (lightning == null) {
            lightning = createLightningPath();

            RectF bound = new RectF();
            lightning.computeBounds(bound, true);

            floatingBubblesDrawable.setBounds(((int) bound.left),
                    ((int) bound.top),
                    ((int) bound.right),
                    ((int) bound.bottom));
        }

        canvas.save();
        canvas.clipPath(lightning);
        floatingBubblesDrawable.draw(canvas);
        canvas.restore();


        canvas.save();
        float levelPosition;
        if (rightAnim.isRunning()) {
            levelPosition = rightAnim.getAnimatedFraction() * width;
        } else {
            levelPosition = batteryLevel * 1.0f / 100 * width;
        }
        canvas.clipRect(batteryRectF.left, batteryRectF.top, levelPosition, batteryRectF.bottom);


        canvas.clipPath(lightning, Region.Op.DIFFERENCE);

        paint.setColor(0x88ffffff);
        canvas.drawRoundRect(batteryRectF, roundRadius, roundRadius, paint);
        canvas.restore();
    }

    private Path createLightningPath() {
        int WIDTH = 100;
        int HEIGHT = 10;

        int width = getWidth();
        int height = getHeight();

        float widthPart = width * 1.0f / WIDTH;
        float heightPart = height * 1.0f / HEIGHT;

        float xCenter = getWidth() / 2;
        float yCenter = getHeight() / 2;

        Path path = new Path();
        path.moveTo(xCenter - 2 * widthPart, yCenter - 3 * heightPart);
        path.lineTo(xCenter + 5 * widthPart, yCenter - 3 * heightPart);
        path.lineTo(xCenter + 2 * widthPart, yCenter - heightPart);
        path.lineTo(xCenter + 8 * widthPart, yCenter - heightPart);
        path.lineTo(xCenter - 3 * widthPart, yCenter + 3 * heightPart);
        path.lineTo(xCenter, yCenter);
        path.lineTo(xCenter - 5 * widthPart, yCenter);


        return path;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
        invalidate();
    }

    public void startCharging() {
        rightAnim.start();
    }

    public void endCharging() {
        rightAnim.end();
    }
}
