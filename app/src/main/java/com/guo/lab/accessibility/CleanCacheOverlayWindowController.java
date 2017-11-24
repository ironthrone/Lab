package com.guo.lab.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.guo.lab.R;


/**
 * Created by ironthrone on 2017/10/10 0010.
 */

public class CleanCacheOverlayWindowController extends OverlayWindowController {

    public CleanCacheOverlayWindowController(Context context) {
        super(context);
    }

    public void setView(View view) {
        rootView = view;
    }
    @Override
    public void onShow(Bundle bundle) {

    }


    @Override
    public View onCreateView() {
//        return LayoutInflater.from(context).inflate(R.layout.activity_clean,null);
        return rootView;
    }
}
