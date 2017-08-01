package com.guo.lab.anim;

import android.graphics.RectF;

public  class ReflectBubble extends Bubble {
    public ReflectBubble(float x, float y, float radius, int color, float stepX, float stepY, RectF bound) {
        super(x, y, radius, color, stepX, stepY, bound);
    }

    @Override
    public void move() {
        x += stepX;
        y += stepY;

        if (x < bound.left) {
            x = bound.left + bound.left - x;
            stepX = -stepX;
        }
        if (x > bound.right) {
            x = bound.right - (x - bound.right);
            stepX = -stepX;
        }

        y += stepY;
        if (y < bound.top) {
            y = bound.top + bound.top - y;
            stepY = -stepY;
        }
        if (y > bound.bottom) {
            y = bound.bottom - (y - bound.bottom);
            stepY = -stepY;
        }
    }
}