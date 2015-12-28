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

public class ScaleSlideHandler extends ByItemIdSlideHandler {

    private float mFromScale;
    private float mToScale;


    public ScaleSlideHandler() {
        super();
    }

    public ScaleSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        super(leftItemViewId, rightItemViewId, contentViewId);
    }

    public ScaleSlideHandler(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.ScaleSlideHandler);

        mFromScale = arr.getFloat(R.styleable.ScaleSlideHandler_fromScale, 0);
        mToScale = arr.getFloat(R.styleable.ScaleSlideHandler_toScale, 0);
        int scaleDuration = arr.getInt(R.styleable.ScaleSlideHandler_scaleDuration, 0);
        if (scaleDuration != 0) {
            setAnimatorDuration(scaleDuration);
        }

        arr.recycle();
    }

    public void setFromScale(float scale) {
        mFromScale = scale;
    }

    public void setToScale(float scale) {
        mToScale = scale;
    }

    @Override
    protected void doSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showScaleViewAnimation(itemView, true);
    }

    @Override
    protected void doUnSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showScaleViewAnimation(itemView, false);
    }

    private void showScaleViewAnimation(final View view, final boolean showSlide) {

        ValueAnimator animator = null;
        if (showSlide) {
            animator = ObjectAnimator.ofFloat(mFromScale, mToScale);
        } else {
            animator = ObjectAnimator.ofFloat(mToScale, mFromScale);
        }

        animator.setDuration(getAnimatorDuration());
        animator.setInterpolator(getSlideInterpolator());
        animator.addListener(createAnimatorListener(view, showSlide));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });

        animator.start();
    }
}
