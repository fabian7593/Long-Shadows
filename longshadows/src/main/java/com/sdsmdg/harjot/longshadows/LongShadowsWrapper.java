package com.sdsmdg.harjot.longshadows;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.sdsmdg.harjot.longshadows.shadowutils.LongShadowsGenerator;

public class LongShadowsWrapper extends RelativeLayout {

    LongShadowsGenerator longShadowsGenerator;

    boolean shouldShowWhenAllReady = Constants.DEFAULT_SHOW_WHEN_ALL_READY;
    boolean shouldCalculateAsync = Constants.DEFAULT_CALCULATE_ASYNC;
    boolean shouldAnimateShadow = Constants.DEFAULT_ANIMATE_SHADOW;

    int animationDuration = Constants.DEFAULT_ANIMATION_TIME;

    boolean isAttached = false;

    public LongShadowsWrapper(Context context) {
        super(context);
        init();
    }

    public LongShadowsWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initXMLAttrs(context, attrs);
        init();
    }

    public LongShadowsWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initXMLAttrs(context, attrs);
        init();
    }

    void initXMLAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LongShadowsWrapper);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.LongShadowsWrapper_calculateAsync) {
                shouldCalculateAsync = a.getBoolean(attr, Constants.DEFAULT_CALCULATE_ASYNC);
            } else if (attr == R.styleable.LongShadowsWrapper_animateShadow) {
                shouldAnimateShadow = a.getBoolean(attr, Constants.DEFAULT_ANIMATE_SHADOW);
            } else if (attr == R.styleable.LongShadowsWrapper_showWhenAllReady) {
                shouldShowWhenAllReady = a.getBoolean(attr, Constants.DEFAULT_SHOW_WHEN_ALL_READY);
            } else if (attr == R.styleable.LongShadowsWrapper_animationDuration) {
                animationDuration = a.getInteger(attr, Constants.DEFAULT_ANIMATION_TIME);
            }
        }
        a.recycle();
    }

    void init() {

        setClipChildren(false);
        setClipToPadding(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (longShadowsGenerator == null) {
            longShadowsGenerator = new LongShadowsGenerator(this, shouldShowWhenAllReady, shouldCalculateAsync, shouldAnimateShadow, animationDuration);
        }
        longShadowsGenerator.generate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttached = false;
        if (longShadowsGenerator != null) {
            longShadowsGenerator.releaseResources();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttached = true;
    }

    public boolean isAttached() {
        return isAttached;
    }

    public void setAttached(boolean attached) {
        isAttached = attached;
    }
}
