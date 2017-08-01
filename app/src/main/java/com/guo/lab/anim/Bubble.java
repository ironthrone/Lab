package com.guo.lab.anim;

import android.graphics.RectF;

import java.util.Random;

public class Bubble {
    protected float x;
    protected float y;
    protected float radius;
    protected int color;
    protected float stepX;
    protected float stepY;
    protected float stepXBound;
    protected float stepYBound;
    protected RectF bound;
    protected final Random random;

    public Bubble(float x, float y, float radius, int color, float stepX, float stepY, RectF bound) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        stepXBound = 2;
        stepYBound = 2;
        this.bound = bound;
        this.stepX = stepX;
        this.stepY = stepY;
        random = new Random();
    }

    public void move() {
        x += stepX;
        y += stepY;
        if (x < bound.left || x > bound.right || y < bound.top || y > bound.bottom) {
            reset();
        }
    }

    private void reset() {
        x = bound.left + bound.width() * random.nextFloat();
        y = bound.top + bound.height() * random.nextFloat();
        stepX = (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepXBound;
        stepY = (random.nextBoolean() ? 1 : -1) * random.nextFloat() * stepYBound;
    }

    @Override
    public String toString() {
        return "Bubble{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", color=" + color +
                ", stepX=" + stepX +
                ", stepY=" + stepY +
                ", stepXBound=" + stepXBound +
                ", stepYBound=" + stepYBound +
                ", bound=" + bound +
                '}';
    }
}

