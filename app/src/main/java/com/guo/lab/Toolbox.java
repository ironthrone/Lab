package com.guo.lab;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.R.attr.width;

/**
 * Created by ironthrone on 2017/6/5 0005.
 */

public class Toolbox {

    public static final double DELTA = 1e-10;

    public static double angle2Radian(double angle) {
        return angle * Math.PI / 180;
    }

    public static double radian2Angle(double radian) {
        return radian * 180 / Math.PI;
    }

    public static boolean equal(double a, double b) {
        return equal(a, b, DELTA);
    }

    public static boolean equal(double a, double b, double delta) {
        return Math.abs(a - b) < delta;
    }

    public static int addAlpha(int color, float alpha) {
        return (((int) (0xff * alpha)) << 24) | (color & ~(0xff << 24));

    }


    public static Rect toRect(RectF rectF) {
        Rect rect = new Rect();
        rect.left = (int) rectF.left;
        rect.right = (int) rectF.right;
        rect.top = (int) rectF.top;
        rect.bottom = (int) rectF.bottom;
        return rect;
    }

    public static RectF toRectF(Rect rect) {
        RectF rectF = new RectF();
        rectF.left =  rect.left;
        rectF.right = rect.right;
        rectF.top =  rect.top;
        rectF.bottom =  rect.bottom;
        return rectF;
    }

    /**
     * measure view's width/height equal to layoutparam.width/height or width/height of parent granted
     *
     * @param view
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @return int array containing two item,first is width spec
     */
    public static int[] measureView(View view, int widthMeasureSpec, int heightMeasureSpec) {
        int withMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int grantedWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int grantedHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        // from Exactly,At most determine final size of view
        if (withMode == View.MeasureSpec.EXACTLY) {
            width = grantedWidth;
        } else {
            if (view.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                width = grantedWidth;
            } else {
                width = 0;
            }

        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = grantedHeight;
        } else {
            if (view.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
                height = grantedHeight;
            } else {
                height = 0;
            }
        }

        return new int[]{View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)};
    }


    /**
     * 读取一个文本文件中的内容，性能未优化，只能处理小文件
     *
     * @param fname
     * @return
     */
    public static final String TAG = Toolbox.class.getSimpleName();

    public static String read(String fname) {
        File file = new File(fname);
        if (!file.exists()) {
            Log.d(TAG, "file is not exist");
            return null;
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();

                String line;
                while (((line = reader.readLine()) != null)) {
                    sb.append(line);
                    sb.append("\n");
                }
                reader.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    public static void removeAllListener(Animator animator) {
        if (animator == null) return;
        if (animator instanceof AnimatorSet) {
            List<Animator> childs = ((AnimatorSet) animator).getChildAnimations();
            for (Animator child : childs) {
                removeAllListener(child);
            }
        } else {
            animator.removeAllListeners();
            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).removeAllUpdateListeners();
            }
        }
    }
}
