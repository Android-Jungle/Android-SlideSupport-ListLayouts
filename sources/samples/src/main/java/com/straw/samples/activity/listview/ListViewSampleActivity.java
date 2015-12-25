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

package com.straw.samples.activity.listview;

import android.os.Bundle;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.widget.SlideSupportListView;
import com.straw.samples.R;
import com.straw.samples.activity.base.ModeSwitchableSampleActivity;

public class ListViewSampleActivity extends ModeSwitchableSampleActivity {

    protected SlideSupportListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_view_sample);
        mListView = (SlideSupportListView) findViewById(R.id.list_view);
        mListView.setAdapter(new SlideListViewAdapter(this, mListView));
    }

    @Override
    public void switchSlideMode(SlideMode mode) {
        mListView.setSlideMode(mode);
    }
}
