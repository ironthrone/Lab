package com.guo.lab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * image,text,textSize,textColor
 */
public class TextImageView extends View {
    private static final int DEFAULT_TEXT_SIZE = 30;
    private int mTextColor;
    private int mTextSize;
    private String mText;
    private Bitmap mImage;
    private Paint mPaint;
    private Rect mTextBound;

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextImageView);

            mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.TextImageView_image, 0));
            mText = a.getString(R.styleable.TextImageView_text);
            mTextSize = a.getDimensionPixelSize(R.styleable.TextImageView_textSize, DEFAULT_TEXT_SIZE);
            mTextColor = a.getColor(R.styleable.TextImageView_textColor, Color.BLACK);
            a.recycle();
        }

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        //计算文字的范围
        mTextBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //AT_MOST或者UNSPECIFIED的时候，子View指定自己的大小
        if (widthMeasureMode == MeasureSpec.AT_MOST || widthMeasureMode == MeasureSpec.UNSPECIFIED) {

            int textWidth = mTextBound.width();
            int imageWidth = mImage.getWidth();
            width = Math.min(width, Math.max(textWidth, imageWidth) + getPaddingLeft() + getPaddingRight());
        }
        if (heightMeasureMode == MeasureSpec.AT_MOST || heightMeasureMode == MeasureSpec.UNSPECIFIED) {
            int textHeight = mTextBound.height();
            int imageHeight = mImage.getHeight();
            height = Math.min(height, textHeight + imageHeight + getPaddingTop() + getPaddingBottom());
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = getWidthWithoutPadding();
        int heightWithoutPadding = getHeightWithoutPadding();

        Rect imageBound = new Rect();
        imageBound.left = Math.max(getPaddingLeft(), (width   - mImage.getWidth()) / 2);
        imageBound.right = Math.min(imageBound.left + mImage.getWidth(),width-getPaddingRight());
        imageBound.top = getPaddingTop();
        imageBound.bottom = imageBound.top + mImage.getHeight();
        canvas.drawBitmap(mImage, null, imageBound, mPaint);

        TextPaint textPaint = new TextPaint(mPaint);
        String truncated = mText;
        if (widthWithoutPadding < textPaint.measureText(mText, 0, mText.length())) {
            truncated = TextUtils.ellipsize(mText, textPaint, widthWithoutPadding, TextUtils.TruncateAt.END).toString();
        }
        //更新文字边界
        mPaint.getTextBounds(truncated, 0, truncated.length(), mTextBound);

        int textX =  (width - mTextBound.width()) / 2;
        int textY =  (getPaddingTop() + mImage.getHeight() + mTextBound.height());
        canvas.drawText(truncated, 0, truncated.length(), textX, textY, mPaint);
    }


    private int getWidthWithoutPadding() {
        return getWidth() - getPaddingLeft() - getPaddingRight();

    }

    private int getHeightWithoutPadding() {
        return getHeight() - getPaddingTop() - getPaddingBottom();

    }
}
