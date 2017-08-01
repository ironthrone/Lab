package com.guo.lab.anim;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import com.guo.lab.Toolbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ironthrone on 2017/7/25 0025.
 */

public class FloatingBubblesView extends View {
    private Random random = new Random();
    private float radius = 15;
    private int stepX = 2;
    private int stepY = 2;
    private int color = Color.WHITE;
    private List<Bubble> bubbles = new ArrayList<>();


    private Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            for (Bubble bubble :
                    bubbles) {
                bubble.move();
            }
            invalidate();
            Choreographer.getInstance().postFrameCallback(this);
        }
    };
    private Paint paint;


    public FloatingBubblesView(Context context) {
        this(context, null);
    }

    public FloatingBubblesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Choreographer.getInstance().postFrameCallback(frameCallback);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] specs = Toolbox.measureView(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(specs[0], specs[1]);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (bubbles.size() == 0) {
            for (int i = 0; i < 10; i++) {
                Bubble bubble = new Bubble(width * random.nextFloat(),
                        height * random.nextFloat(),
                        radius * (random.nextFloat() + 1),
                        Color.argb(((int) (Color.alpha(color) * (random.nextFloat() * 0.4 + 0.2))),
                                Color.red(color),
                                Color.green(color),
                                Color.blue(color)),
                        (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepX,
                        (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepY,
                        new RectF(0 + getPaddingLeft(), 0 + getPaddingTop(), width - getPaddingRight(),
                                height - getPaddingBottom()));
                bubbles.add(bubble);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Bubble bubble :
                bubbles) {
            paint.setColor(bubble.color);
            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, paint);
        }
    }



}
