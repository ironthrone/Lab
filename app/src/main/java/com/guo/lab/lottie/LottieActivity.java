package com.guo.lab.lottie;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.R;
import com.guo.lab.databinding.ActivityLottieBinding;

public class LottieActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLottieBinding binding;
    private ValueAnimator batteryPortion;
    private float levelFraction = 0.95f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lottie);


        findViewById(R.id.start)
                .setOnClickListener(this);


//        batteryPortion = ValueAnimator.ofFloat(0.5f, 0.61f)
//                .setDuration(2000);
//
//
//        batteryPortion.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float currentValue = ((Float) animation.getAnimatedValue() - 0.5f) * levelFraction + 0.5f;
//                binding.battery.setProgress(currentValue);
//            }
//        });
//        batteryPortion.setRepeatCount(3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:


//                try {
//                    float progress = Float.parseFloat(binding.input.getText().toString());
//                    binding.battery.setProgress(progress);
//                } catch (Exception e) {
//
//                }
//                batteryPortion.start();
                break;
        }
    }
}
