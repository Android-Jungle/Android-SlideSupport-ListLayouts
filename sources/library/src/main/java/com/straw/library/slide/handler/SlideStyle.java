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

public enum SlideStyle {
    MoveItemOnly(1),
    MoveContentOnly(2),
    MoveWithContent(3),
    ScaleItem(4),
    RotateItem(5),
    AlphaItem(6),
    Composited(7);

    private int mValue;

    private SlideStyle(int val) {
        mValue = val;
    }

    public int value() {
        return mValue;
    }

    public static SlideStyle fromValue(int value) {
        if (value == MoveItemOnly.value()) {
            return MoveItemOnly;
        } else if (value == MoveContentOnly.value()) {
            return MoveContentOnly;
        } else if (value == MoveWithContent.value()) {
            return MoveWithContent;
        } else if (value == ScaleItem.value()) {
            return ScaleItem;
        } else if (value == RotateItem.value()) {
            return RotateItem;
        } else if (value == AlphaItem.value()) {
            return AlphaItem;
        }

        return null;
    }

    public static SlideStyle fromValue(int value, SlideStyle defStyle) {
        SlideStyle mode = fromValue(value);
        if (mode == null) {
            mode = defStyle;
        }

        return mode;
    }

    public static SlideHandler createByStyle(
            SlideStyle style, Context context, AttributeSet attrs) {

        if (style == MoveItemOnly) {
            return new MoveItemOnlySlideHandler(context, attrs);
        } else if (style == MoveContentOnly) {
            return new MoveContentOnlySlideHandler(context, attrs);
        } else if (style == MoveWithContent) {
            return new MoveWithContentSlideHandler(context, attrs);
        } else if (style == ScaleItem) {
            return new ScaleSlideHandler(context, attrs);
        } else if (style == RotateItem) {
            return new RotateSlideHandler(context, attrs);
        } else if (style == AlphaItem) {
            return new AlphaSlideHandler(context, attrs);
        } else if (style == Composited) {
            return new CompositeSlideHandler();
        }

        return null;
    }
}
