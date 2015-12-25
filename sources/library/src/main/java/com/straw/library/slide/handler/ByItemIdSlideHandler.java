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

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import com.straw.library.slide.R;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public abstract class ByItemIdSlideHandler extends SlideBaseHandler {

    private static final int DEFAULT_ANIMATOR_DURATION = 400;


    protected int mLeftItemViewId;
    protected int mRightItemViewId;
    protected int mContentViewId;
    protected int mAnimatorDuration = DEFAULT_ANIMATOR_DURATION;
    protected TimeInterpolator mSlideInterpolator;


    public ByItemIdSlideHandler() {
    }

    public ByItemIdSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        attachItemViews(leftItemViewId, rightItemViewId, contentViewId);
    }

    public ByItemIdSlideHandler(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.SlideHandler);
        mLeftItemViewId = arr.getResourceId(R.styleable.SlideHandler_leftViewId, 0);
        mRightItemViewId = arr.getResourceId(R.styleable.SlideHandler_rightViewId, 0);
        mContentViewId = arr.getResourceId(R.styleable.SlideHandler_contentViewId, 0);
        mAnimatorDuration = arr.getInt(R.styleable.SlideHandler_slideDuration,
                DEFAULT_ANIMATOR_DURATION);

        int interpolatorId = arr.getResourceId(R.styleable.SlideHandler_slideInterpolator, 0);
        if (interpolatorId > 0) {
            mSlideInterpolator = AnimationUtils.loadInterpolator(context, interpolatorId);
        }

        arr.recycle();
    }

    public void setLeftItemViewId(int viewId) {
        mLeftItemViewId = viewId;
    }

    public void setRightItemViewId(int viewId) {
        mRightItemViewId = viewId;
    }

    public void setContentViewId(int viewId) {
        mContentViewId = viewId;
    }

    public void attachItemViews(int leftItemViewId, int rightItemViewId, int contentViewId) {
        mLeftItemViewId = leftItemViewId;
        mRightItemViewId = rightItemViewId;
        mContentViewId = contentViewId;
    }

    public void setSlideInterpolator(TimeInterpolator interpolator) {
        mSlideInterpolator = interpolator;
    }

    public void setAnimatorDuration(int duration) {
        mAnimatorDuration = duration;
    }

    public int getAnimatorDuration() {
        return mAnimatorDuration;
    }

    public TimeInterpolator getSlideInterpolator() {
        return mSlideInterpolator != null
                ? mSlideInterpolator : new OvershootInterpolator();
    }

    protected abstract void doSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode);

    protected abstract void doUnSlideInternal(
            SlideSupportLayout layout, View itemView, SlideMode mode);

    protected void onUnSlideItem(
            SlideSupportLayout layout, View itemView) {
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);

        View itemView = getItemViewByMode(layout, mode);
        if (itemView == null) {
            notifySlideFinish();
        } else {
            doSlideInternal(layout, itemView, mode);
        }
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);

        View itemView = getItemViewByMode(layout, mode);
        if (itemView == null) {
            notifyUnSlideFinish();
            return;
        }

        if (immediately) {
            itemView.setVisibility(View.GONE);
            onUnSlideItem(layout, itemView);
            notifyUnSlideFinish();
        } else {
            doUnSlideInternal(layout, itemView, mode);
        }
    }

    @Override
    public boolean needHandleThisTouch(SlideSupportLayout layout, float x, float y) {
        SlideMode mode = layout.getCurrSlideMode();
        View itemView = getItemViewByMode(layout, mode);
        return isPointInViewContent(itemView, x, y);
    }

    protected View getItemViewByMode(SlideSupportLayout layout, SlideMode mode) {
        View itemView = null;
        if (mode == SlideMode.LeftToRight && mLeftItemViewId != 0) {
            itemView = layout.findViewById(mLeftItemViewId);
        } else if (mode == SlideMode.RightToLeft && mRightItemViewId != 0) {
            itemView = layout.findViewById(mRightItemViewId);
        }

        return itemView;
    }

    protected View getItemContentView(SlideSupportLayout layout) {
        if (mContentViewId != 0) {
            return layout.findViewById(mContentViewId);
        }

        return null;
    }
}
