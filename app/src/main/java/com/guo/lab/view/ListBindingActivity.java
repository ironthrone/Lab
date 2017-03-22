package com.guo.lab.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guo.lab.R;
import com.guo.lab.databinding.ActivityListBindingBinding;
import com.guo.lab.viewmodel.ListBindingViewModel;

public class ListBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list_binding);
        binding.setViewModel(new ListBindingViewModel());


    }
}
