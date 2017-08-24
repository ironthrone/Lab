package com.guo.lab.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.guo.lab.R;

public class RingActivity extends AppCompatActivity {

    private CircularRingProgressView ringProgressView;
    private SpiralBalls spiralBalls;
    private BroadcastView broadcastView;
    private Button end;
    private ObjectAnimator endAnim;
    private ObjectAnimator xAnim;
    private DiffusionView diffusionView;
    private ObjectAnimator compareAnim;
    private BatteryView batteryView;
    private ChargingStageBackgroundView chargingStageBackgroundView;
    private Button compare;
    private HomeRingView homeRingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ringProgressView = (CircularRingProgressView) findViewById(R.id.ring);
        spiralBalls = (SpiralBalls) findViewById(R.id.spiral);
        broadcastView = (BroadcastView) findViewById(R.id.broadcast_view);
        diffusionView = (DiffusionView) findViewById(R.id.diffusion);

        end = (Button) findViewById(R.id.end);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("y", 100, 300);
        endAnim = ObjectAnimator.ofPropertyValuesHolder(end, yHolder)
                .setDuration(2000);

        endAnim.setRepeatCount(ValueAnimator.INFINITE);

        compare = (Button) findViewById(R.id.compare);
        compareAnim = ObjectAnimator.ofPropertyValuesHolder(compare, yHolder)
                .setDuration(2000);

        xAnim = ObjectAnimator.ofFloat(end, "translationX", 0, 200)
                .setDuration(1000);
//        xAnim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                end.setX(0);
//            }
//        });
        endAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

        });
        batteryView = ((BatteryView) findViewById(R.id.battery));
        batteryView.setBatteryLevel(80);
        homeRingView = (HomeRingView) findViewById(R.id.home_ring);

        chargingStageBackgroundView = ((ChargingStageBackgroundView) findViewById(R.id.charge));
    }


    private void postXAnim() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xAnim.start();
            }
        }, 1000);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                homeRingView.setDuration(2000).start();
                ObjectAnimator bottomAnim = ObjectAnimator.ofInt(end, "bottom", end.getBottom(), end.getBottom() + 1)
                        .setDuration(2000);
                ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(end, "scaleY", 1, 2)
                        .setDuration(2000);
                bottomAnim.setRepeatCount(ValueAnimator.INFINITE);
                bottomAnim.setRepeatMode(ValueAnimator.REVERSE);
                bottomAnim.start();
                scaleXAnim.start();
                ((PolishView) findViewById(R.id.polish)).start();

                batteryView.startCharging();


                ((GradientAnimView) findViewById(R.id.gradient)).changeTo(Color.GREEN,Color.BLUE);

                chargingStageBackgroundView.activate();


                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 100);
                translateAnimation.setDuration(2000);
                translateAnimation.setFillAfter(true);
                compare.startAnimation(translateAnimation);
//                compareAnim.addListener(new RepeatListener(compareAnim,5));
//                compareAnim.start();
//                endAnim.start();
                broadcastView.start();
//                diffusionView.setRepeatCount(3);
//                diffusionView.start();
//                spiralBalls.start();
//                ringProgressView.start();
                break;
            case R.id.end:
                homeRingView.start();
//                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, 2);
//                scaleAnimation.setDuration(2000);
//                compare.startAnimation(scaleAnimation);

                compare.clearAnimation();
                chargingStageBackgroundView.deactivate();
                ringProgressView.end();
                batteryView.endCharging();
                break;
        }
    }
}
