package com.guo.artpractice.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.artpractice.R;
import com.guo.artpractice.databinding.ActivityBindingBinding;
import com.guo.artpractice.viewmodel.BindingViewModel;

public class BindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        binding.setViewModel(new BindingViewModel());
    }
}
