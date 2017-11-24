package com.guo.lab.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.blankj.utilcode.util.ScreenUtils;
import com.guo.lab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironthrone on 2017/7/13 0013.
 */

public class DiffusionView extends FrameLayout {
    private AnimatorSet groupAnimator;
    private int duration = 2000;
    private List<Animator> animators = new ArrayList<>();
    private int[] circleColor = new int[]{0xff00dd68,0xff00b957,0xff2d3039};
    private int[] ringColor = new int[]{0xfffff600, 0xff00dd68};

    public DiffusionView(@NonNull Context context) {
        super(context);
    }

    public DiffusionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.diffusion_view, this);

    }


    private int getIterationTime(int duration) {
        return (int) (duration * 1.0 / 7);
    }

    private class VisibleListener extends AnimatorListenerAdapter {
        private View target;

        public VisibleListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            target.setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            target.setVisibility(GONE);
        }
    }

    private int repeatCount;

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setDuration(int duration) {
        this.duration = duration;

        for (Animator animator :
                animators) {
            animator.setDuration(getIterationTime(duration));
        }
    }

    private static final int REPEAT_ANIMATOR = 100;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REPEAT_ANIMATOR:
                    if (groupAnimator.isRunning()) {
                        groupAnimator.end();
                    }
                    groupAnimator.start();
                    haveRepeatedCount++;
                    break;
            }
            return false;
        }
    });
    private int haveRepeatedCount;

    public void start() {
        if (groupAnimator == null) {
            createAnimator();
        }

        groupAnimator.start();

        if (haveRepeatedCount < repeatCount) {
            handler.sendEmptyMessageDelayed(REPEAT_ANIMATOR, duration);
        }
    }


    private void createAnimator() {
        final CircleView circle1 = (CircleView) findViewById(R.id.circle1);
        final RingView ring1 = (RingView) findViewById(R.id.ring1);

        float toScale = ScreenUtils.getScreenHeight() * 1.0f / circle1.getWidth();

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1, toScale);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1, toScale);

        int iterateTime = getIterationTime(duration);

        ObjectAnimator circle1Anim = ObjectAnimator.ofPropertyValuesHolder(circle1, scaleX, scaleY)
                .setDuration(iterateTime);
        circle1Anim.setRepeatCount(2);
        circle1Anim.addListener(new RobostAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                circle1.setVisibility(VISIBLE);
                circle1.setColor(circleColor[0]);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                if (repeatCount == 1) {
                    circle1.setColor(circleColor[1]);
                } else if (repeatCount == 2) {
                    circle1.setColor(circleColor[2]);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                circle1.setVisibility(INVISIBLE);

            }
        });


        animators = new ArrayList<>();
        ObjectAnimator ring1Anim = ObjectAnimator.ofPropertyValuesHolder(ring1, scaleX, scaleY)
                .setDuration(iterateTime);
        ring1Anim.setRepeatCount(1);
        ring1Anim.addListener(new RobostAnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ring1.setVisibility(VISIBLE);
                ring1.setColor(ringColor[0]);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ring1.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                ring1.setColor(ringColor[1]);
            }
        });


        ObjectAnimator ring1White = ObjectAnimator.ofPropertyValuesHolder(circle1, scaleX, scaleY)
                .setDuration(iterateTime);
        ring1White.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                circle1.setVisibility(VISIBLE);
                circle1.setColor(Color.WHITE);
            }
        });
        animators.add(circle1Anim);
        animators.add(ring1Anim);
        animators.add(ring1White);

        groupAnimator = new AnimatorSet();

        groupAnimator.playSequentially(animators);
    }

    public void end() {
        handler.removeMessages(REPEAT_ANIMATOR);
        if (groupAnimator.isRunning()) {
            groupAnimator.end();
        }
    }


    private static class RobostAnimatorListener extends AnimatorListenerAdapter {
        protected int repeatCount;
        @Override
        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            repeatCount++;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            repeatCount = 0;
        }
    }

}

