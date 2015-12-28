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

public enum SlideMode {

    None(1),
    LeftToRight(2),
    RightToLeft(4),
    Both(6);

    private int mValue;

    private SlideMode(int val) {
        mValue = val;
    }

    public int value() {
        return mValue;
    }

    public static SlideMode fromValue(int value) {
        if (value == None.value()) {
            return None;
        } else if (value == LeftToRight.value()) {
            return LeftToRight;
        } else if (value == RightToLeft.value()) {
            return RightToLeft;
        } else if (value == Both.value()) {
            return Both;
        }

        return null;
    }

    public static SlideMode fromValue(int value, SlideMode defMode) {
        SlideMode mode = fromValue(value);
        if (mode == null) {
            mode = defMode;
        }

        return mode;
    }
}
