package com.guo.lab.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.guo.lab.R;

/**
 * Created by ironthrone on 2017/6/30 0030.
 */

public class NewDialog extends Dialog {

    public NewDialog(@NonNull Context context) {
        super(context);
        // 去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //滑动取消，但是在API 20才可以用
        requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.dialog_new);
//        setCancelable(false);
        setCanceledOnTouchOutside(false);
        //这些配置的外观属性是Window的属性，位于LayoutParams
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.5f;
        attributes.gravity = Gravity.TOP|Gravity.START;
        //如果有设置gravity的话，x，y是相对于Gravity的偏移量
        attributes.x = 100;
        attributes.y = 200;
        attributes.width = 300;
        attributes.height = 300;
        attributes.alpha = 0.8f;
//        attributes.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        getWindow().setAttributes(attributes);

    }

}
