package com.guo.lab.dimention;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.R;

public class DimensionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimention);
        TextView descView = (TextView) findViewById(R.id.desc);
//        descView
        int width = ScreenUtils.getScreenWidth();
        SizeUtils.px2dp(width);
        String desc = "screen width: "+ ScreenUtils.getScreenWidth() + "  " + SizeUtils.px2dp(ScreenUtils.getScreenWidth()) + "dp\n" +
                "screen height: "  + ScreenUtils.getScreenHeight() + "  "  + SizeUtils.px2dp(ScreenUtils.getScreenHeight()) + "dp\n";

        descView.setText(desc);

    }
}
