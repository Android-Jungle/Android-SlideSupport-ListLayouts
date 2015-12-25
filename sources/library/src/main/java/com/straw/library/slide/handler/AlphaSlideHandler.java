/*
 * Copyright (C) 2015 Arno Zhang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.straw.library.slide.handler;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.straw.library.slide.R;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public class AlphaSlideHandler extends ByItemIdSlideHandler {

    private float mFromAlpha;
    private float mToAlpha;

    public AlphaSlideHandler() {
        super();
    }

    public AlphaSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        super(leftItemViewId, rightItemViewId, contentViewId);
    }

    public AlphaSlideHandler(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.RotateSlideHandler);

        mFromAlpha = arr.getFloat(R.styleable.AlphaSlideHandler_fromAlpha, 0);
        mToAlpha = arr.getFloat(R.styleable.AlphaSlideHandler_toAlpha, 0);
        int alphaDuration = arr.getInt(R.styleable.AlphaSlideHandler_alphaDuration, 0);
        if (alphaDuration != 0) {
            setAnimatorDuration(alphaDuration);
        }

        arr.recycle();
    }

    public void setFromAlpha(float alpha) {
        mFromAlpha = alpha;
    }

    public void setToAlpha(float alpha) {
        mToAlpha = alpha;
    }

    @Override
    protected void doSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showAlphaViewAnimation(itemView, true);
    }

    @Override
    protected void doUnSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showAlphaViewAnimation(itemView, false);
    }

    private void showAlphaViewAnimation(final View view, final boolean showSlide) {
        ValueAnimator animator = null;
        if (showSlide) {
            animator = ObjectAnimator.ofFloat(
                    view, "alpha", mFromAlpha, mToAlpha);
        } else {
            animator = ObjectAnimator.ofFloat(
                    view, "alpha", mToAlpha, mFromAlpha);
        }

        animator.setDuration(getAnimatorDuration());
        animator.setInterpolator(getSlideInterpolator());
        animator.addListener(createAnimatorListener(view, showSlide));
        animator.start();
    }
}
