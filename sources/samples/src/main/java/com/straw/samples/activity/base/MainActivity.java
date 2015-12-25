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

package com.straw.samples.activity.base;

import android.os.Bundle;
import android.view.View;
import com.straw.samples.R;
import com.straw.samples.activity.customized.CompositeSlideActionSampleActivity;
import com.straw.samples.activity.customized.CustomizedSlideActionSampleActivity;
import com.straw.samples.activity.customized.SlideActionSetSampleActivity;
import com.straw.samples.activity.listview.ExpandableListViewSampleActivity;
import com.straw.samples.activity.listview.ListViewSampleActivity;
import com.straw.samples.activity.pulltorefresh.WithPullToRefreshLibrarySampleActivity;
import com.straw.samples.activity.recyclerview.RecyclerViewSampleActivity;
import com.straw.samples.activity.scrollview.ScrollViewSampleActivity;
import com.straw.samples.activity.swpierefresh.WithSwipeRefreshLayoutSampleActivity;

public class MainActivity extends SampleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onListViewSample(View view) {
        startActivityInternal(ListViewSampleActivity.class);
    }

    public void onRecyclerViewSample(View view) {
        startActivityInternal(RecyclerViewSampleActivity.class);
    }

    public void onExpandableListViewSample(View view) {
        startActivityInternal(ExpandableListViewSampleActivity.class);
    }

    public void onScrollViewSample(View view) {
        startActivityInternal(ScrollViewSampleActivity.class);
    }

    public void onWithSwipeRefreshLayoutSample(View view) {
        startActivityInternal(WithSwipeRefreshLayoutSampleActivity.class);
    }

    public void onWithPullToRefreshLibrarySample(View view) {
        startActivityInternal(WithPullToRefreshLibrarySampleActivity.class);
    }

    public void onCompositeSlideActionHandler(View view) {
        startActivityInternal(CompositeSlideActionSampleActivity.class);
    }

    public void onSlideActionHandlerSet(View view) {
        startActivityInternal(SlideActionSetSampleActivity.class);
    }

    public void onCustomizedSlideActionHandler(View view) {
        startActivityInternal(CustomizedSlideActionSampleActivity.class);
    }
}
