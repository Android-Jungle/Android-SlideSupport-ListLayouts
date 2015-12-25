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

package com.straw.library.slide.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.straw.library.slide.handler.SlideHandler;
import com.straw.library.slide.support.SlideImplSupporter;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.support.SlideSupporter;
import com.straw.library.slide.support.SlideTouchActionHandler;
import com.straw.library.slide.support.SlideUtils;

public class SlideSupportListView extends ListView implements SlideSupporter {

    public static abstract class SlideAdapter extends BaseAdapter {

        private SlideImplSupporter mSlideSupporter;


        private void setSlideSupporter(SlideImplSupporter supporter) {
            mSlideSupporter = supporter;
        }

        protected SlideSupportLayout createSlideLayout(ViewGroup parent) {
            return SlideUtils.createSlideLayout(mSlideSupporter, parent);
        }
    }


    private SlideTouchActionHandler mActionHandler;

    public SlideSupportListView(Context context) {
        super(context);
        initLayout(context, null);
    }


    public SlideSupportListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public SlideSupportListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attrs) {
        mActionHandler = new SlideTouchActionHandler(context, attrs);
    }

    @Override
    public SlideSupportLayout createSlideLayout(ViewGroup parent) {
        return SlideUtils.createSlideLayout(mActionHandler, parent);
    }

    @Override
    public void cancelCurrentSlide(boolean immediately) {
        mActionHandler.cancelCurrentSlide(immediately);
    }

    @Override
    public boolean isSlided() {
        return mActionHandler.isSlided();
    }

    @Override
    public SlideMode getSlideMode() {
        return mActionHandler.getSlideMode();
    }

    @Override
    public void setSlideMode(SlideMode mode) {
        mActionHandler.setSlideMode(mode);
    }

    @Override
    public void setSlideHandler(SlideHandler handler) {
        mActionHandler.setSlideHandler(handler);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof SlideAdapter) {
            SlideAdapter slideAdapter = (SlideAdapter) adapter;
            slideAdapter.setSlideSupporter(mActionHandler);
        }

        super.setAdapter(adapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        SlideTouchActionHandler.HandleResult result =
                mActionHandler.handleInterceptTouchEvent(e);
        if (result.isHandled()) {
            return result.getResult();
        }

        return super.onInterceptTouchEvent(e);
    }
}