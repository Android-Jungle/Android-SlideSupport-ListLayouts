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

package com.straw.samples.activity.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.straw.library.slide.widget.SlideSupportListView;

public class SlideSupportPullToRefreshListView extends PullToRefreshListView {

    public SlideSupportPullToRefreshListView(Context context) {
        super(context);
    }

    public SlideSupportPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideSupportPullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public SlideSupportPullToRefreshListView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    protected ListView createListView(Context context, AttributeSet attrs) {
        return new SlideSupportListView(context, attrs);
    }
}
