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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.straw.library.slide.support.SlideMode;
import com.straw.samples.R;

public abstract class ModeSwitchableSampleActivity extends SampleBaseActivity
        implements SlideModeSwitcher {

    private View mPrevModeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mode_switchable_sample);
    }

    @Override
    public void setContentView(int layoutResID) {
        View contentView = View.inflate(this, layoutResID, null);

        LinearLayout container = (LinearLayout) findViewById(R.id.root_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        container.addView(contentView, params);

        container.post(new Runnable() {
            @Override
            public void run() {
                onBothEnableMode(null);
            }
        });
    }

    public void onLeftToRightMode(View view) {
        switchMode(R.id.left_2_right_mode, SlideMode.LeftToRight);
    }

    public void onRightToLeftMode(View view) {
        switchMode(R.id.right_2_left_mode, SlideMode.RightToLeft);
    }

    public void onBothEnableMode(View view) {
        switchMode(R.id.both_mode, SlideMode.Both);
    }

    private void switchMode(int newModeViewId, SlideMode mode) {
        switchSlideMode(mode);
        if (mPrevModeView != null) {
            mPrevModeView.setSelected(false);
        }

        mPrevModeView = findViewById(newModeViewId);
        mPrevModeView.setSelected(true);
    }
}
