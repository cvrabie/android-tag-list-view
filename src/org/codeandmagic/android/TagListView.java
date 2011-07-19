package org.codeandmagic.android;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Based on PredicateLayout by Henrik Gustafsson
 * @see http://stackoverflow.com/questions/549451/line-breaking-widget-layout-for-android
 * @license http://creativecommons.org/licenses/by-sa/2.5/
 */
public class TagListView extends ViewGroup {

    private int mLineHeight;
    private final ArrayList<String> mTags = new ArrayList<String>();
    private final LayoutInflater mInflater;

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public final int mHorizontalSpacing;
        public final int mVerticalSpacing;

        /**
         * @param horizontalSpacing Pixels between items, horizontally
         * @param verticalSpacing Pixels between items, vertically
         */
		public LayoutParams(int horizontalSpacing, int verticalSpacing) {
            super(0, 0);
            this.mHorizontalSpacing = horizontalSpacing;
            this.mVerticalSpacing = verticalSpacing;
        }
    }

    public TagListView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public TagListView(Context context, AttributeSet attrs){
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
    }

    public void addTag(String tag){
        mTags.add(tag);
        inflateTagView(tag);
    }

    private void inflateTagView(String tag) {
        TagView tagView = (TagView) mInflater.inflate(R.layout.tag, null);
        tagView.setText(tag);
        addView(tagView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);

        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();
        int line_height = 0;

        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                child.measure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));

                final int childw = child.getMeasuredWidth();
                line_height = Math.max(line_height, child.getMeasuredHeight() + lp.mVerticalSpacing);

                if (xpos + childw > width) {
                    xpos = getPaddingLeft();
                    ypos += line_height;
                }

                xpos += childw + lp.mHorizontalSpacing;
            }
        }
		this.mLineHeight = line_height;

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED){
            height = ypos + line_height;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
            if (ypos + line_height < height){
                height = ypos + line_height;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(1, 5); // default of 1px spacing
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams)
            return true;
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childw = child.getMeasuredWidth();
                final int childh = child.getMeasuredHeight();
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (xpos + childw > width) {
                    xpos = getPaddingLeft();
                    ypos += mLineHeight;
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh);
                xpos += childw + lp.mHorizontalSpacing;
            }
        }
    }
}