package com.guo.lab.lottie;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
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


        batteryPortion = ValueAnimator.ofFloat(0.5f, 0.61f)
                .setDuration(2000);


        batteryPortion.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = ((Float) animation.getAnimatedValue() - 0.5f) * levelFraction + 0.5f;
                binding.battery.setProgress(currentValue);
            }
        });
        batteryPortion.setRepeatCount(3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

                Intent intent = registerReceiver(null, intentFilter);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                String material = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                ToastUtils.showShortToast("level: "+ level + " technology: " + material);

//                Intent intent
//                LottieComposition composition = ((LottieDrawable) binding.battery.getDrawable()).getComposition();
//                LottieComposition layerComposition = null;
//                try {
//                    Method getLayers = LottieComposition.class.getDeclaredMethod("getLayers");
//                    getLayers.setAccessible(true);
//                    Class<?> layerClass = Class.forName("com.airbnb.lottie.Layer");
//                    List layers = (List) getLayers.invoke(composition);
//                    Field compositionField = layerClass.getDeclaredField("composition");
//                    compositionField.setAccessible(true);
//                    layerComposition = (LottieComposition) compositionField.get(layers.get(2));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                try {
                    float progress = Float.parseFloat(binding.input.getText().toString());
                    binding.battery.setProgress(progress);
                } catch (Exception e) {

                }
//                batteryPortion.start();
                break;
        }
    }
}
