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

package com.straw.samples.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.straw.samples.R;
import com.straw.samples.data.HelperUtils;

public class SampleItemView extends FrameLayout {

    private View mIconView;
    private View mLineView;
    private TextView mTitleView;
    private TextView mDescriptionView;


    public SampleItemView(Context context) {
        super(context);
        initLayout(context);
    }

    public SampleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public SampleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View.inflate(context, R.layout.layout_sample_item, this);
        setBackgroundResource(R.drawable.white_item_with_press_bkg);

        mIconView = findViewById(R.id.icon);
        mTitleView = (TextView) findViewById(R.id.title);
        mDescriptionView = (TextView) findViewById(R.id.description);
        mLineView = findViewById(R.id.line);
    }

    public void updateByItem(HelperUtils.Item item) {
        mIconView.setBackgroundResource(item.mIconResId);
        mTitleView.setText(item.mTitle);
        mDescriptionView.setText(item.mDescription);
        setItemFaved(item.mFaved);
    }

    public void showLine(boolean show) {
        mLineView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setItemFaved(boolean faved) {
        mTitleView.setTextColor(getResources().getColor(faved
                ? R.color.faved_color : R.color.un_faved_color));
    }
}
