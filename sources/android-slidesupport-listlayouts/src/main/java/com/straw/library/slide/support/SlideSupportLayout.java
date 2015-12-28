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

package com.straw.library.slide.support;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import com.straw.library.slide.handler.SlideHandler;

public class SlideSupportLayout extends FrameLayout {

    private float mLastX;
    private float mLastY;
    private float mTriggerSlideSlop = 0;
    private boolean mNeedHandleThisRound = false;
    private boolean mSkipNextPress = false;
    private boolean mTouchHandleMove = true;
    private SlideMode mCurrSlideMode = null;
    private SlideImplSupporter mSlideSupporter;


    public SlideSupportLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public SlideSupportLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public SlideSupportLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        mTriggerSlideSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setClickable(true);
    }

    public void setSlideSupporter(SlideImplSupporter supporter) {
        mSlideSupporter = supporter;
    }

    public SlideMode getCurrSlideMode() {
        return mCurrSlideMode;
    }

    /*package*/ void setCurrSlideMode(SlideMode mode) {
        mCurrSlideMode = mode;
    }

    public void cancelCurrentSlide(boolean immediately) {
        if (mSlideSupporter != null) {
            mSlideSupporter.cancelCurrentSlide(immediately);
        }
    }

    private boolean handleMove(MotionEvent event) {
        SlideHandler handler = mSlideSupporter.getSlideHandler();
        if (mSlideSupporter == null || handler == null) {
            return false;
        }

        final float x = event.getX();
        final float y = event.getY();
        final float xOffset = Math.abs(x - mLastX);
        final float yOffset = Math.abs(y - mLastY);

        if (xOffset > yOffset && xOffset >= mTriggerSlideSlop) {
            SlideMode mode = mSlideSupporter.getSlideMode();
            boolean leftToRight = x > mLastX;

            if (mode == SlideMode.Both
                    || (leftToRight && mode == SlideMode.LeftToRight)
                    || (!leftToRight && mode == SlideMode.RightToLeft)) {

                mCurrSlideMode = leftToRight ? SlideMode.LeftToRight : SlideMode.RightToLeft;
                handler.onSlide(this, mCurrSlideMode);
                mSlideSupporter.viewSlide(this);
                mNeedHandleThisRound = true;

                setPressed(false);
                mSkipNextPress = true;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        SlideMode mode = mSlideSupporter.getSlideMode();
        if (mode == null || mode == SlideMode.None) {
            return super.onInterceptTouchEvent(event);
        }

        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            mLastX = event.getX();
            mLastY = event.getY();

            mNeedHandleThisRound = false;
            mTouchHandleMove = true;
        } else if (action == MotionEvent.ACTION_MOVE) {
            mTouchHandleMove = false;
            if (handleMove(event)) {
                return true;
            }
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mNeedHandleThisRound = false;
            mTouchHandleMove = true;
        }

        if (mNeedHandleThisRound) {
            return true;
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public void setPressed(boolean pressed) {
        if (mSkipNextPress) {
            mSkipNextPress = false;
            return;
        }

        super.setPressed(pressed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        SlideMode mode = mSlideSupporter.getSlideMode();
        if (mode == null || mode == SlideMode.None) {
            return super.onTouchEvent(event);
        }

        int action = event.getActionMasked();
        if (mNeedHandleThisRound) {
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                mNeedHandleThisRound = false;
                mTouchHandleMove = true;
                return true;
            }

            return super.onTouchEvent(event);
        }

        if (mTouchHandleMove && action == MotionEvent.ACTION_MOVE) {
            if (handleMove(event)) {
                return true;
            }
        }

        return super.onTouchEvent(event);
    }
}
