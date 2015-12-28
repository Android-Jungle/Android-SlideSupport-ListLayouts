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

public class SlideHandlerSequence extends SlideBaseHandler {

    private List<SlideHandler> mSlideHandlerList = new ArrayList<>();
    private AtomicInteger mShowSlideHandlerIndex = new AtomicInteger();
    private AtomicInteger mHideSlideHandlerIndex = new AtomicInteger();
    private OnSlideHandleListener mSlideListener;
    private SlideSupportLayout mCurrHandleLayout;
    private boolean mUnSlideImmediately;


    public SlideHandlerSequence() {
        super();
        createHandlerListener();
    }

    public SlideHandlerSequence(List<SlideHandler> list) {
        this();

        if (list != null) {
            for (SlideHandler handler : list) {
                addSlideHandler(handler);
            }
        }
    }

    public SlideHandlerSequence(SlideHandler... handlers) {
        this();

        for (SlideHandler handler : handlers) {
            addSlideHandler(handler);
        }
    }

    public void addSlideHandler(SlideHandler handler) {
        handler.setSlideHandleListener(mSlideListener);
        handler.setHideViewWhenUnSlideFinished(mSlideHandlerList.isEmpty());
        mSlideHandlerList.add(handler);
    }

    private void createHandlerListener() {
        mSlideListener = new OnSlideHandleListener() {
            @Override
            public void onSlideFinished() {
                int index = mShowSlideHandlerIndex.addAndGet(1);
                if (mCurrHandleLayout == null
                        || index >= mSlideHandlerList.size()) {
                    mCurrHandleLayout = null;
                    notifySlideFinish();
                } else {
                    scheduleNextSlideHandler(index);
                }
            }

            @Override
            public void onUnSlideFinished() {
                int index = mHideSlideHandlerIndex.decrementAndGet();
                if (mCurrHandleLayout == null || index < 0) {
                    mCurrHandleLayout = null;
                    notifyUnSlideFinish();
                } else {
                    scheduleNextUnSlideHandler(index);
                }
            }
        };
    }

    private void scheduleNextSlideHandler(int index) {
        SlideHandler handler = mSlideHandlerList.get(index);
        handler.onSlide(mCurrHandleLayout,
                mCurrHandleLayout.getCurrSlideMode());
    }

    private void scheduleNextUnSlideHandler(int index) {
        SlideHandler handler = mSlideHandlerList.get(index);
        handler.onUnSlide(mCurrHandleLayout,
                mCurrHandleLayout.getCurrSlideMode(), mUnSlideImmediately);
    }

    @Override
    public void onSlide(SlideSupportLayout layout, SlideMode mode) {
        super.onSlide(layout, mode);

        // Set Handler-Count to Cancel hide slide.
        mHideSlideHandlerIndex.set(1);

        if (mSlideHandlerList.isEmpty()) {
            notifySlideFinish();
            return;
        }

        mCurrHandleLayout = layout;
        mShowSlideHandlerIndex.set(0);
        scheduleNextSlideHandler(0);
    }

    @Override
    public void onUnSlide(SlideSupportLayout layout, SlideMode mode, boolean immediately) {
        super.onUnSlide(layout, mode, immediately);

        // Set Handler-Count to Cancel show slide.
        int count = mSlideHandlerList.size();
        mShowSlideHandlerIndex.set(count);

        if (mSlideHandlerList.isEmpty()) {
            notifyUnSlideFinish();
            return;
        }

        mCurrHandleLayout = layout;
        mUnSlideImmediately = immediately;
        mHideSlideHandlerIndex.set(count - 1);
        scheduleNextUnSlideHandler(count - 1);
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
