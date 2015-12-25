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

public class RotateSlideHandler extends ByItemIdSlideHandler {

    private float mFromDegree;
    private float mToDegree;

    public RotateSlideHandler() {
        super();
    }

    public RotateSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        super(leftItemViewId, rightItemViewId, contentViewId);
    }

    public RotateSlideHandler(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.RotateSlideHandler);

        mFromDegree = arr.getFloat(R.styleable.RotateSlideHandler_fromDegree, 0);
        mToDegree = arr.getFloat(R.styleable.RotateSlideHandler_toDegree, 0);
        int rotateDuration = arr.getInt(R.styleable.RotateSlideHandler_rotateDuration, 0);
        if (rotateDuration != 0) {
            setAnimatorDuration(rotateDuration);
        }

        arr.recycle();
    }

    public void setFromDegree(float degree) {
        mFromDegree = degree;
    }

    public void setToDegree(float degree) {
        mToDegree = degree;
    }

    @Override
    protected void doSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showRotateViewAnimation(itemView, true);
    }

    @Override
    protected void doUnSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        showRotateViewAnimation(itemView, false);
    }

    private void showRotateViewAnimation(final View view, final boolean showSlide) {
        ValueAnimator animator = null;
        if (showSlide) {
            animator = ObjectAnimator.ofFloat(
                    view, "rotation", mFromDegree, mToDegree);
        } else {
            animator = ObjectAnimator.ofFloat(
                    view, "rotation", mToDegree, mFromDegree);
        }

        animator.setDuration(getAnimatorDuration());
        animator.setInterpolator(getSlideInterpolator());
        animator.addListener(createAnimatorListener(view, showSlide));
        animator.start();
    }
}
