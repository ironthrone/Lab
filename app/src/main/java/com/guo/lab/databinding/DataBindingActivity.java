package com.guo.lab.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.blankj.utilcode.utils.ToastUtils;
import com.guo.lab.R;

public class DataBindingActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.guo.lab.databinding.ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setCheckChangeListener(this);
        binding.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ToastUtils.showShortToast("clicked");
    }

    @Override
    public void onClick(View v) {
        ToastUtils.showShortToast("onClick");

    }
}
