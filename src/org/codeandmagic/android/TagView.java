package org.codeandmagic.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class TagView extends TextView {
    private final static String T = "tagview";

    private int tagBackground;
    private int tagBorder;
    private Paint backgroundPaint;
    private Paint borderPaint;

    public TagView(Context context) {
        super(context);
        init(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private int parseColor(final Context context, final TypedArray a, final int index, final int defaultColorRes){
        //try to get as a resource
        final int resId = a.getResourceId(index,-1);
        if(resId > -1) return context.getResources().getColor(resId);
        //try to get as a hex string
        final String str = a.getString(index);
        if(null != str) return Color.parseColor(str);
        //return default
        return context.getResources().getColor(defaultColorRes);
    }

    private void init(Context context, AttributeSet attrs){
        final TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TagView);
        tagBackground = parseColor(context, a, R.styleable.TagView_background, R.color.tagBackgroundDefault);
        tagBorder = parseColor(context, a, R.styleable.TagView_border, R.color.tagBorderDefault);
        a.recycle();

        backgroundPaint = backgroundPaint();
        borderPaint = borderPaint();
    }

    private Paint backgroundPaint() {
        Paint p = new Paint();
        p.setColor(tagBackground);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        return p;
    }

    private Paint borderPaint(){
        Paint p = new Paint();
        p.setColor(tagBorder);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        return p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int push = 2;
        final int width = getWidth()-push;
        final int height = getHeight();
        final int roundness = height/2;

        canvas.drawRoundRect(new RectF(push, 0, width, height), roundness, roundness, backgroundPaint);
        canvas.drawRoundRect(new RectF(push, 0, width, height), roundness, roundness, borderPaint);
        super.onDraw(canvas);
    }
}
