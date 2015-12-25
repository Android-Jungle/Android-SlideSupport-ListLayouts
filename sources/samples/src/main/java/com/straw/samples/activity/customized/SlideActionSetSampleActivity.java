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

package com.straw.samples.activity.customized;

import android.os.Bundle;
import android.widget.Toast;
import com.straw.library.slide.handler.CallbackSlideHandler;
import com.straw.library.slide.handler.CompositeSlideHandler;
import com.straw.library.slide.handler.DelayTimeSlideHandler;
import com.straw.library.slide.handler.MoveWithContentSlideHandler;
import com.straw.library.slide.handler.RotateSlideHandler;
import com.straw.library.slide.handler.ScaleSlideHandler;
import com.straw.library.slide.handler.SlideHandler;
import com.straw.library.slide.handler.SlideHandlerSequence;
import com.straw.library.slide.handler.SlideHandlerSet;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.widget.SlideSupportRecyclerView;
import com.straw.samples.R;
import com.straw.samples.activity.recyclerview.RecyclerViewSampleActivity;

public class SlideActionSetSampleActivity
        extends RecyclerViewSampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFavViewBkgColorResId(R.color.fav_item_bkg_color);

        SlideSupportRecyclerView recyclerView =
                mRecyclerLayout.getSlideRecyclerView();

        CompositeSlideHandler handler = new CompositeSlideHandler(
                createLeftToRightHandler(),
                createRightToLeftHandler());
        recyclerView.setSlideHandler(handler);
    }

    private SlideHandler createLeftToRightHandler() {
        MoveWithContentSlideHandler move = new MoveWithContentSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);

        DelayTimeSlideHandler delay = new DelayTimeSlideHandler(800);

        RotateSlideHandler rotate = new RotateSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        rotate.setFromDegree(0);
        rotate.setToDegree(360);
        rotate.setAnimatorDuration(600);

        CallbackSlideHandler callback = new CallbackSlideHandler(
                new CallbackSlideHandler.SlideCallback() {
                    @Override
                    public void onFinished(boolean isSlide,
                            SlideSupportLayout layout, SlideMode mode) {

                        if (isSlide) {
                            Toast.makeText(getContext(),
                                    mode.toString() + " Finished!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return new SlideHandlerSequence(move, delay, rotate, callback);
    }

    private SlideHandler createRightToLeftHandler() {
        RotateSlideHandler rotate = new RotateSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        rotate.setFromDegree(0);
        rotate.setToDegree(360);
        rotate.setAnimatorDuration(600);

        ScaleSlideHandler scale = new ScaleSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        scale.setFromScale(0);
        scale.setToScale(1);
        scale.setAnimatorDuration(600);

        return new SlideHandlerSet(rotate, scale);
    }
}
