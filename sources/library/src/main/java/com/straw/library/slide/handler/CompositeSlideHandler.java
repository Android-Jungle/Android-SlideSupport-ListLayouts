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

import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public class CompositeSlideHandler extends SlideBaseHandler {

    private SlideHandler mLeftToRightHandler;
    private SlideHandler mRightToLeftHandler;


    public CompositeSlideHandler() {
    }

    public CompositeSlideHandler(
            SlideHandler leftToRightHandler,
            SlideHandler rightToLeftHandler) {

        mLeftToRightHandler = leftToRightHandler;
        mRightToLeftHandler = rightToLeftHandler;
    }

    public void setLeftToRightHandler(SlideHandler handler) {
        mLeftToRightHandler = handler;
        if (mLeftToRightHandler != null) {
            mLeftToRightHandler.setSlideHandleListener(mSlideHandleListener);
        }
    }

    public void setRightToLeftHandler(SlideHandler handler) {
        mRightToLeftHandler = handler;
        if (mRightToLeftHandler != null) {
            mRightToLeftHandler.setSlideHandleListener(mSlideHandleListener);
        }
    }

    @Override
    public void setSlideHandleListener(OnSlideHandleListener listener) {
        super.setSlideHandleListener(listener);

        if (mLeftToRightHandler != null) {
            mLeftToRightHandler.setSlideHandleListener(listener);
        }

        if (mRightToLeftHandler != null) {
            mRightToLeftHandler.setSlideHandleListener(listener);
        }
    }

    @Override
    public boolean isSliding() {
        return (mLeftToRightHandler != null && mLeftToRightHandler.isSliding())
                || (mRightToLeftHandler != null && mRightToLeftHandler.isSliding());
    }

    @Override
    public boolean isUnSliding() {
        return (mLeftToRightHandler != null && mLeftToRightHandler.isUnSliding())
                || (mRightToLeftHandler != null && mRightToLeftHandler.isUnSliding());
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);

        boolean handled = false;
        if (mode == SlideMode.LeftToRight) {
            if (mLeftToRightHandler != null) {
                mLeftToRightHandler.onSlide(layout, mode);
                handled = true;
            }
        } else if (mode == SlideMode.RightToLeft) {
            if (mRightToLeftHandler != null) {
                mRightToLeftHandler.onSlide(layout, mode);
                handled = true;
            }
        }

        if (!handled) {
            notifySlideFinish();
        }
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);

        boolean handled = false;
        if (mode == SlideMode.LeftToRight) {
            if (mLeftToRightHandler != null) {
                mLeftToRightHandler.onUnSlide(layout, mode, immediately);
                handled = true;
            }
        } else if (mode == SlideMode.RightToLeft) {
            if (mRightToLeftHandler != null) {
                mRightToLeftHandler.onUnSlide(layout, mode, immediately);
                handled = true;
            }
        }

        if (!handled) {
            notifyUnSlideFinish();
        }
    }

    @Override
    public boolean needHandleThisTouch(SlideSupportLayout layout, float x, float y) {
        SlideMode mode = layout.getCurrSlideMode();
        if (mode == SlideMode.LeftToRight) {
            if (mLeftToRightHandler != null) {
                return mLeftToRightHandler.needHandleThisTouch(layout, x, y);
            }
        } else if (mode == SlideMode.RightToLeft) {
            if (mRightToLeftHandler != null) {
                return mRightToLeftHandler.needHandleThisTouch(layout, x, y);
            }
        }

        return false;
    }
}
