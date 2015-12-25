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

package com.straw.samples.activity.swpierefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import com.straw.library.slide.support.SlideMode;
import com.straw.samples.R;
import com.straw.samples.activity.base.ModeSwitchableSampleActivity;
import com.straw.samples.activity.layout.RecyclerViewSampleLayout;

public class WithSwipeRefreshLayoutSampleActivity
        extends ModeSwitchableSampleActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerViewSampleLayout mRecyclerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_with_swipe_refresh_view_sample);
        mSwipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.swipe_layout);
        mRecyclerLayout = (RecyclerViewSampleLayout)
                findViewById(R.id.recycler_view_layout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerLayout.reload();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void switchSlideMode(SlideMode mode) {
        mRecyclerLayout.getSlideRecyclerView().setSlideMode(mode);
    }
}
