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

package com.straw.samples.activity.layout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import com.straw.library.slide.handler.ByItemIdSlideHandler;
import com.straw.library.slide.handler.SlideBaseHandler;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.samples.R;

public class CustomizedSlideActionLayout
        extends RecyclerViewSampleLayout {

    public CustomizedSlideActionLayout(Context context) {
        super(context);
        initLayout(context, null);
    }

    public CustomizedSlideActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public CustomizedSlideActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        setFavViewBkgColorResId(R.color.fav_item_bkg_color);

        ByItemIdSlideHandler handler = new ByItemIdSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item) {

            @Override
            protected void doSlideInternal(
                    SlideSupportLayout layout, View itemView, SlideMode mode) {

                if (mode == SlideMode.RightToLeft) {
                    showRightViewAnimation(itemView, true, this);
                } else {
                    showLeftViewAnimation(itemView, true, this);
                }
            }

            @Override
            protected void doUnSlideInternal(
                    SlideSupportLayout layout, View itemView, SlideMode mode) {

                if (mode == SlideMode.RightToLeft) {
                    showRightViewAnimation(itemView, false, this);
                } else {
                    showLeftViewAnimation(itemView, false, this);
                }
            }
        };

        mRecyclerView.setSlideHandler(handler);
    }

    private int getAnimationOffset(View view) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        return view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
    }

    private void showLeftViewAnimation(
            final View view,
            final boolean showSlide,
            final SlideBaseHandler handler) {

        ValueAnimator animator = null;
        if (showSlide) {
            animator = ObjectAnimator.ofFloat(0, 1);
        } else {
            animator = ObjectAnimator.ofFloat(1, 0);
        }

        animator.setDuration(400);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addListener(handler.createAnimatorListener(view, showSlide));
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

    private void showRightViewAnimation(
            final View view,
            final boolean showSlide,
            final SlideBaseHandler handler) {

        ValueAnimator translation = null;
        int slideOffset = getAnimationOffset(view);
        if (showSlide) {
            translation = ObjectAnimator.ofFloat(
                    view, "translationX", slideOffset, 0);
        } else {
            translation = ObjectAnimator.ofFloat(
                    view, "translationX", 0, slideOffset);
        }

        ValueAnimator rotate = ObjectAnimator.ofFloat(
                view, "rotation", 0, 360);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(translation, rotate);
        set.addListener(handler.createAnimatorListener(view, showSlide));

        set.start();
    }
}
