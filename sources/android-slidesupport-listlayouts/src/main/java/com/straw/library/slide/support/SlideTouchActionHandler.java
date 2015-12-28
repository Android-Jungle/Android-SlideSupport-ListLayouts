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
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.straw.library.slide.R;
import com.straw.library.slide.handler.CompositeSlideHandler;
import com.straw.library.slide.handler.SlideHandler;
import com.straw.library.slide.handler.SlideStyle;

public class SlideTouchActionHandler
        implements SlideImplSupporter, SlideHandler.OnSlideHandleListener {

    public static class HandleResult {
        public boolean mResult;
        public boolean mHandled;

        public void reset() {
            mResult = false;
            mHandled = false;
        }

        public HandleResult setHandled() {
            mHandled = true;
            return this;
        }

        public HandleResult setUnHandled() {
            mHandled = false;
            return this;
        }

        public boolean isHandled() {
            return mHandled;
        }

        public boolean getResult() {
            return mResult;
        }

        public HandleResult setResult(boolean result) {
            mResult = result;
            return this;
        }
    }


    private SlideSupportLayout mCurrSlideLayout;
    private HandleResult mResult = new HandleResult();
    private SlideMode mSlideMode = SlideMode.RightToLeft;
    private SlideHandler mSlideHandler;


    public SlideTouchActionHandler() {
    }

    public SlideTouchActionHandler(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(
                attrs, R.styleable.SlideSupport);
        int mode = arr.getInt(R.styleable.SlideSupport_slideMode, 0);
        mSlideMode = SlideMode.fromValue(mode, mSlideMode);

        int leftToRightStyle = arr.getInt(R.styleable.SlideSupport_leftToRightSlideStyle, 0);
        int rightToLeftStyle = arr.getInt(R.styleable.SlideSupport_rightToLeftSlideStyle, 0);
        if (leftToRightStyle != 0 || rightToLeftStyle != 0) {
            SlideHandler leftToRightHandler = SlideStyle.createByStyle(
                    SlideStyle.fromValue(leftToRightStyle), context, attrs);
            SlideHandler rightToLeftHandler = SlideStyle.createByStyle(
                    SlideStyle.fromValue(rightToLeftStyle), context, attrs);

            CompositeSlideHandler handler = new CompositeSlideHandler(
                    leftToRightHandler, rightToLeftHandler);
            setSlideHandler(handler);
        } else {
            int handlerStyle = arr.getInt(R.styleable.SlideSupport_slideStyle, 0);
            SlideStyle style = SlideStyle.fromValue(handlerStyle, SlideStyle.MoveWithContent);
            setSlideHandler(SlideStyle.createByStyle(style, context, attrs));
        }

        arr.recycle();
    }

    public HandleResult handleInterceptTouchEvent(MotionEvent e) {
        if (mSlideMode == null || mSlideMode == SlideMode.None) {
            return mResult.setUnHandled();
        }

        if (mCurrSlideLayout != null
                && mSlideHandler != null
                && (mSlideHandler.isSliding() || mSlideHandler.isUnSliding())) {

            return mResult.setResult(true).setHandled();
        }

        mResult.reset();
        int action = e.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mCurrSlideLayout != null) {
                float x = e.getRawX();
                float y = e.getRawY();

                Rect rc = new Rect();
                mCurrSlideLayout.getGlobalVisibleRect(rc);

                if (rc.contains((int) x, (int) y)
                        && mSlideHandler.needHandleThisTouch(mCurrSlideLayout, x, y)) {
                    return mResult.setResult(false).setHandled();
                }

                cancelCurrentSlide(false);
                return mResult.setResult(true).setHandled();
            }
        }

        if (mCurrSlideLayout != null) {
            return mResult.setResult(false).setHandled();
        }

        return mResult.setUnHandled();
    }

    @Override
    public void viewSlide(SlideSupportLayout layout) {
        mCurrSlideLayout = layout;
    }

    @Override
    public void cancelCurrentSlide(boolean immediately) {
        if (mCurrSlideLayout != null) {
            mSlideHandler.onUnSlide(
                    mCurrSlideLayout,
                    mCurrSlideLayout.getCurrSlideMode(),
                    immediately);
        }
    }

    @Override
    public boolean isSlided() {
        return mCurrSlideLayout != null;
    }

    @Override
    public SlideMode getSlideMode() {
        return mSlideMode;
    }

    @Override
    public void setSlideMode(SlideMode mode) {
        mSlideMode = mode;
    }

    @Override
    public SlideSupportLayout createSlideLayout(ViewGroup parent) {
        return SlideUtils.createSlideLayout(this, parent);
    }

    @Override
    public SlideHandler getSlideHandler() {
        return mSlideHandler;
    }

    @Override
    public void setSlideHandler(SlideHandler handler) {
        mSlideHandler = handler;
        if (mSlideHandler != null) {
            mSlideHandler.setSlideHandleListener(this);
        }
    }

    @Override
    public void onSlideFinished() {
    }

    @Override
    public void onUnSlideFinished() {
        if (mCurrSlideLayout != null) {
            mCurrSlideLayout.setCurrSlideMode(null);
            mCurrSlideLayout = null;
        }
    }
}
