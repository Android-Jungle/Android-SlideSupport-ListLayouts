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

import android.os.Handler;
import android.os.Looper;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;

public class DelayTimeSlideHandler extends SlideBaseHandler {

    private int mDelayMillSeconds;
    private Handler mUIHandler;


    public DelayTimeSlideHandler() {
    }

    public DelayTimeSlideHandler(int delayMillSeconds) {
        mDelayMillSeconds = delayMillSeconds;
    }

    public void setDelayMillSeconds(int delayMillSeconds) {
        mDelayMillSeconds = delayMillSeconds;
    }

    private void ensureUIHandler() {
        if (mUIHandler == null) {
            mUIHandler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);

        ensureUIHandler();
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifySlideFinish();
            }
        }, mDelayMillSeconds);
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);

        ensureUIHandler();
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyUnSlideFinish();
            }
        }, mDelayMillSeconds);
    }

    @Override
    public boolean needHandleThisTouch(SlideSupportLayout layout, float x, float y) {
        return false;
    }
}
