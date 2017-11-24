package com.guo.lab.anim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Choreographer;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.Toolbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ironthrone on 2017/7/26 0026.
 */

public class FloatingBubblesDrawable extends Drawable {
    private float radius = SizeUtils.dp2px(2);
    private Random random = new Random();
    private int color = Color.WHITE;
    private float stepX = SizeUtils.dp2px(0.5f);
    private float stepY = SizeUtils.dp2px(0.5f);
    private int count = 6;
    private View target;

    private List<Bubble> bubbles = new ArrayList<>();

    Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            for (Bubble bubble :
                    bubbles) {
                bubble.move();
            }
            if (target != null) {
                target.invalidate();
            }
            Choreographer.getInstance().postFrameCallback(this);
        }
    };
    private final Paint paint;


    public FloatingBubblesDrawable() {
        super();
        Choreographer.getInstance().postFrameCallback(frameCallback);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public void setTarget(View view) {
        this.target = view;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        Rect bound = getBounds();
        int width = getBounds().width();
        int height = getBounds().height();

        bubbles.clear();
        for (int i = 0; i < count; i++) {
            Bubble bubble = new Bubble(bound.left + width * random.nextFloat(),
                    bound.top + height * random.nextFloat(),
                    radius * (random.nextFloat() + 1),
                    Color.argb(((int) (Color.alpha(color) * (random.nextFloat() * 0.4 + 0.2))),
                            Color.red(color),
                            Color.green(color),
                            Color.blue(color)),
                    (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepX,
                    (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepY,
                    Toolbox.toRectF(bound));
            bubbles.add(bubble);
        }
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        for (Bubble bubble :
                bubbles) {
            paint.setColor(bubble.color);
            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, paint);
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
