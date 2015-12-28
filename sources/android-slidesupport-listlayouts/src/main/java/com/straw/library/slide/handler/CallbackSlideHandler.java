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

public class CallbackSlideHandler extends SlideBaseHandler {

    public interface SlideCallback {
        void onFinished(boolean isSlide, SlideSupportLayout layout, SlideMode mode);
    }


    private SlideCallback mSlideCallback;

    public CallbackSlideHandler() {
    }

    public CallbackSlideHandler(SlideCallback callback) {
        mSlideCallback = callback;
    }

    public void setSlideCallback(SlideCallback callback) {
        mSlideCallback = callback;
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);
        notifySlideFinish();

        if (mSlideCallback != null) {
            mSlideCallback.onFinished(true, layout, mode);
        }
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);
        notifyUnSlideFinish();

        if (mSlideCallback != null) {
            mSlideCallback.onFinished(false, layout, mode);
        }
    }

    @Override
    public boolean needHandleThisTouch(SlideSupportLayout layout, float x, float y) {
        return false;
    }
}
