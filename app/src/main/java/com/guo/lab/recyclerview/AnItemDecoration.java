package com.guo.lab.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by ironthrone on 2017/6/9 0009.
 */

public class AnItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;
    private final Rect mBounds;
    private  Rect mDivider;

    public AnItemDecoration() {
        super();
        mPaint = new Paint();
        mPaint.setColor(0xfff3f7fc);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mBounds = new Rect();
        mDivider = new Rect();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);

        switch (viewHolder.getItemViewType()) {
            case 0:
                outRect.set(0,SizeUtils.dp2px(10),0,0);
                break;
            case 1:
                outRect.set(0,1,0,0);
                break;
        }
        mDivider = outRect;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int leftWithMargin = SizeUtils.dp2px(6);
        int left;
        int right;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int adapterPosition = parent.getChildLayoutPosition(child);
            int type = parent.getAdapter().getItemViewType(adapterPosition);
            // 拿到当前view的装饰区域
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            left = type == 0 ?  mBounds.left : leftWithMargin;
            right = type == 0 ? mBounds.right : mBounds.right - leftWithMargin;

            mPaint.setColor(type ==0? 0xbbf3f7fc:Color.GRAY);
            mPaint.setStrokeWidth(mDivider.height());
            canvas.drawRect(left,mBounds.top,right,mBounds.bottom - child.getHeight(),mPaint);
        }
        canvas.restore();
    }
}
