package com.guo.lab.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;

/**
 * Created by ironthrone on 2017/10/19 0019.
 */

public class CanOpenFromReceiverDialog extends Dialog {
    public CanOpenFromReceiverDialog(@NonNull Context context) {
        super(context);
        //修改窗口的类型，在receiver中可以打开这个窗口
        getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
    }
}
