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
import android.view.ViewGroup;
import com.straw.library.slide.R;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public abstract class MoveBaseSlideHandler extends ByItemIdSlideHandler {

    protected ValueAnimator mAnimator;
    protected int mMoveDistance = 0;


    public MoveBaseSlideHandler() {
        super();
    }

    public MoveBaseSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        super(leftItemViewId, rightItemViewId, contentViewId);
    }

    public MoveBaseSlideHandler(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.MoveSlideHandler);
        mMoveDistance = arr.getDimensionPixelSize(
                R.styleable.MoveSlideHandler_slideMoveDistance, 0);
        arr.recycle();
    }

    protected abstract void handleAnimationUpdate(
            SlideSupportLayout layout, View itemView,
            float itemTranslationX, float contentTranslationX);

    @Override
    protected void doSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        int slideDistance = getSlideDistance(itemView, mode);
        if (mode == SlideMode.LeftToRight) {
            showAnimation(layout, itemView, -slideDistance, 0, mode, true);
        } else if (mode == SlideMode.RightToLeft) {
            showAnimation(layout, itemView, slideDistance, 0, mode, true);
        }
    }

    @Override
    public void doUnSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode) {

        int slideDistance = getSlideDistance(itemView, mode);
        if (mode == SlideMode.LeftToRight) {
            showAnimation(layout, itemView, 0, -slideDistance, mode, false);
        } else if (mode == SlideMode.RightToLeft) {
            showAnimation(layout, itemView, 0, slideDistance, mode, false);
        }
    }

    @Override
    protected void onUnSlideItem(SlideSupportLayout layout, View itemView) {
        super.onUnSlideItem(layout, itemView);

        tryCancelAnimator();
        handleAnimationUpdate(layout, itemView, 0, 0);
    }

    protected void tryCancelAnimator() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    protected ValueAnimator showAnimation(
            final SlideSupportLayout layout, final View itemView,
            final float start, final float end,
            final SlideMode mode, final boolean isSlideAction) {

        tryCancelAnimator();

        final float slideDistance = Math.abs(start - end);
        mAnimator = ObjectAnimator.ofFloat(start, end);
        mAnimator.setDuration(getAnimatorDuration());
        mAnimator.setInterpolator(getSlideInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationX = (float) animation.getAnimatedValue();
                float contentTranslationX = getContentTranslationX(
                        mode, translationX, slideDistance);
                handleAnimationUpdate(layout, itemView, translationX, contentTranslationX);
            }
        });

        itemView.setVisibility(View.VISIBLE);
        mAnimator.addListener(createAnimatorListener(itemView, isSlideAction));
        mAnimator.start();
        return mAnimator;
    }

    protected float getContentTranslationX(
            SlideMode mode, float itemTranslationX, float totalMoveOffset) {

        boolean leftToRight = mode == SlideMode.LeftToRight;
        return itemTranslationX + (leftToRight ? totalMoveOffset : -totalMoveOffset);
    }

    protected int getSlideDistance(View itemView, SlideMode mode) {
        if (mMoveDistance != 0) {
            return mMoveDistance;
        }

        int slideOffset = itemView.getMeasuredWidth();
        if (slideOffset == 0) {
            int measureSpec = View.MeasureSpec.makeMeasureSpec(
                    0, View.MeasureSpec.UNSPECIFIED);
            itemView.measure(measureSpec, measureSpec);
            slideOffset = itemView.getMeasuredWidth();
        }

        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
            slideOffset += marginParams.leftMargin + marginParams.rightMargin;
        }

        return slideOffset;
    }
}
