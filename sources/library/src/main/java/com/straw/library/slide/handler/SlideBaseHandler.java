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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.view.View;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public abstract class SlideBaseHandler implements SlideHandler {

    protected OnSlideHandleListener mSlideHandleListener;
    protected boolean mHideViewWhenUnSlideFinished = true;
    protected boolean mIsSliding = false;
    protected boolean mIsUnSliding = false;


    @Override
    public void setSlideHandleListener(OnSlideHandleListener listener) {
        mSlideHandleListener = listener;
    }

    @Override
    public void setHideViewWhenUnSlideFinished(boolean hide) {
        mHideViewWhenUnSlideFinished = hide;
    }

    @Override
    public boolean isSliding() {
        return mIsSliding;
    }

    @Override
    public boolean isUnSliding() {
        return mIsUnSliding;
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        mIsSliding = true;
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        mIsSliding = false;
    }

    public void notifySlideFinish() {
        mIsSliding = false;
        mSlideHandleListener.onSlideFinished();
    }

    public void notifyUnSlideFinish() {
        mIsUnSliding = false;
        mSlideHandleListener.onUnSlideFinished();
    }

    public boolean isPointInViewContent(View view, float x, float y) {
        if (view != null) {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            return rect.contains((int) x, (int) y);
        }

        return false;
    }

    public Animator.AnimatorListener createAnimatorListener(
            final View itemView, final boolean isSlideAction) {

        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                itemView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (isSlideAction) {
                    notifySlideFinish();
                } else {
                    if (mHideViewWhenUnSlideFinished) {
                        itemView.setVisibility(View.GONE);
                    }

                    notifyUnSlideFinish();
                }
            }
        };
    }
}
