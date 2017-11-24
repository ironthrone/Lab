package com.guo.lab.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.lab.R;

public class ListBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.guo.lab.databinding.ActivityListBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list_binding);
        binding.setViewModel(new ListBindingViewModel());


    }
}
