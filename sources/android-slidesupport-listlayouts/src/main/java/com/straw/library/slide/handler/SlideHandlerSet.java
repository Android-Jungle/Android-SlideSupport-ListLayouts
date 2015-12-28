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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SlideHandlerSet extends SlideBaseHandler {

    private List<SlideHandler> mSlideHandlerList = new ArrayList<>();
    private AtomicInteger mShowSlideHandlerCount = new AtomicInteger();
    private AtomicInteger mHideSlideHandlerCount = new AtomicInteger();
    private OnSlideHandleListener mSlideListener;


    public SlideHandlerSet() {
        super();
        createHandlerListener();
    }

    public SlideHandlerSet(List<SlideHandler> list) {
        this();

        if (list != null) {
            for (SlideHandler handler : list) {
                addSlideHandler(handler);
            }
        }
    }

    public SlideHandlerSet(SlideHandler... handlers) {
        this();

        for (SlideHandler handler : handlers) {
            addSlideHandler(handler);
        }
    }

    public void addSlideHandler(SlideHandler handler) {
        handler.setSlideHandleListener(mSlideListener);
        mSlideHandlerList.add(handler);
    }

    private void createHandlerListener() {
        mSlideListener = new OnSlideHandleListener() {
            @Override
            public void onSlideFinished() {
                if (mShowSlideHandlerCount.decrementAndGet() <= 0) {
                    notifySlideFinish();
                }
            }

            @Override
            public void onUnSlideFinished() {
                if (mHideSlideHandlerCount.decrementAndGet() <= 0) {
                    notifyUnSlideFinish();
                }
            }
        };
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);

        if (mSlideHandlerList.isEmpty()) {
            notifySlideFinish();
            return;
        }

        mShowSlideHandlerCount.set(mSlideHandlerList.size());
        for (SlideHandler handler : mSlideHandlerList) {
            handler.onSlide(layout, mode);
        }
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);

        if (mSlideHandlerList.isEmpty()) {
            notifyUnSlideFinish();
            return;
        }

        mHideSlideHandlerCount.set(mSlideHandlerList.size());
        for (SlideHandler handler : mSlideHandlerList) {
            handler.onUnSlide(layout, mode, immediately);
        }
    }

    @Override
    public boolean needHandleThisTouch(SlideSupportLayout layout, float x, float y) {
        for (SlideHandler handler : mSlideHandlerList) {
            if (handler.needHandleThisTouch(layout, x, y)) {
                return true;
            }
        }

        return false;
    }
}
