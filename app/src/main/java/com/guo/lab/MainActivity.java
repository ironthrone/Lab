package com.guo.lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.guo.lab.progress.ProgressDialogHandler;
import com.guo.lab.service.Service;
import com.guo.lab.service.ServiceHost;
import com.guo.lab.service.XiChengCallback;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String key = "a39a97d23b0d4a6327a96d7d27eba283";
    private String invalidKey = "d4a6327a96d7d27eba283";

    private String cid = "adfasdf";
    private Service service;
    private ProgressDialogHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handler = new ProgressDialogHandler(Utils.getContext());

        findViewById(R.id.zan)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMemberInfo(key);
                    }
                });
        findViewById(R.id.invalid_key)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMemberInfo(invalidKey);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.show();
    }

    private void getMemberInfo(String key) {
        ServiceHost.getInstance().getService().memberInfo(key).enqueue(new XiChengCallback<MemberModel>() {
            @Override
            protected void onSuccess(MemberModel data) {
                LogUtils.d(data);
            }

            @Override
            protected void onFail(String message) {
                ToastUtils.showShortToastSafe(message);
            }
        });
    }



}
