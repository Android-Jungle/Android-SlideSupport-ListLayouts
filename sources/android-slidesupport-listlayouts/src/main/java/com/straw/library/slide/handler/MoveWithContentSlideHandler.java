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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.straw.library.slide.support.SlideSupportLayout;

public class MoveWithContentSlideHandler extends MoveBaseSlideHandler {

    public MoveWithContentSlideHandler() {
        super();
    }

    public MoveWithContentSlideHandler(
            int leftItemViewId, int rightItemViewId, int contentViewId) {

        super(leftItemViewId, rightItemViewId, contentViewId);
    }

    public MoveWithContentSlideHandler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void handleAnimationUpdate(
            SlideSupportLayout layout, View itemView,
            float itemTranslationX, float contentTranslationX) {

        View contentView = getItemContentView(layout);
        if (contentView != null) {
            contentView.setTranslationX(contentTranslationX);
        }

        itemView.setTranslationX(itemTranslationX);
    }
}
