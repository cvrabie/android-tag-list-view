package org.codeandmagic.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.EditText;

public class TagView extends EditText implements OnClickListener, AnimationListener {
	public final static String T = "tagview";

    private final int mTagBackground;
    private final int mTagBorder;
    private final Paint mBackgroundPaint;
	private final Paint mBorderPaint;
	
	private boolean mSelectedForDelete = false;

    public TagView(Context context) {
		this(context, null, 0);
    }

	public TagView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagView);
		mTagBackground = parseColor(context, a, R.styleable.TagView_background, R.color.tagBackgroundDefault);
		mTagBorder = parseColor(context, a, R.styleable.TagView_border, R.color.tagBorderDefault);
		a.recycle();

		mBackgroundPaint = backgroundPaint();
		mBorderPaint = borderPaint();

		setCursorVisible(false);
		setOnClickListener(this);
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

    private Paint backgroundPaint() {
        Paint p = new Paint();
        p.setColor(mTagBackground);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        return p;
    }

    private Paint borderPaint(){
        Paint p = new Paint();
        p.setColor(mTagBorder);
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

        canvas.drawRoundRect(new RectF(push, 0, width, height), roundness, roundness, mBackgroundPaint);
        canvas.drawRoundRect(new RectF(push, 0, width, height), roundness, roundness, mBorderPaint);
        super.onDraw(canvas);
    }

	@Override
	public void onClick(View v) {

		if (!mSelectedForDelete) {
			mSelectedForDelete = true;

			final int initialWidth = getMeasuredWidth();
			final int increaseBy = 20;
			final Animation resize = new Animation() {
				@Override
				protected void applyTransformation(float interpolatedTime, Transformation t) {
					int newWidth = (int) (increaseBy * interpolatedTime) + initialWidth;
					setWidth(newWidth);
				}

				@Override
				public boolean willChangeBounds() {
					return true;
				}
			};

			resize.setDuration(300);
			resize.setFillAfter(true);
			resize.setInterpolator(new BounceInterpolator());
			resize.setAnimationListener(this);
			startAnimation(resize);
		}
		else {
			ViewGroup parent = (ViewGroup) getParent();
			parent.removeView(this);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {}

	@Override
	public void onAnimationStart(Animation animation) {}
}
