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

package com.straw.samples.activity.scrollview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.widget.SlideSupportScrollView;
import com.straw.samples.R;
import com.straw.samples.activity.base.ModeSwitchableSampleActivity;
import com.straw.samples.data.HelperUtils;
import com.straw.samples.widget.SampleItemView;

import java.util.List;

public class ScrollViewSampleActivity extends ModeSwitchableSampleActivity {

    private List<HelperUtils.Item> mItemList;
    private SlideSupportScrollView mScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemList = HelperUtils.getItemList();
        setContentView(R.layout.activity_scroll_view_sample);
        mScrollView = (SlideSupportScrollView) findViewById(R.id.scroll_view);

        bindSlideLayoutInXml();
        createItemsManual();
    }

    @Override
    public void switchSlideMode(SlideMode mode) {
        mScrollView.setSlideMode(mode);
    }

    private void bindSlideLayoutInXml() {
        SlideSupportLayout layout = (SlideSupportLayout)
                findViewById(R.id.slide_layout);
        mScrollView.bindSlideLayout(layout);

        SampleItemView itemView = (SampleItemView)
                layout.findViewById(R.id.sample_item);
        itemView.updateByItem(mItemList.get(0));
    }

    private void createItemsManual() {
        final LinearLayout contentContainer = (LinearLayout)
                findViewById(R.id.manual_content_container);

        for (int i = 1; i < mItemList.size(); ++i) {
            final SlideSupportLayout layout =
                    mScrollView.createSlideLayout(contentContainer);
            View.inflate(this, R.layout.layout_item_with_delete, layout);

            final SampleItemView itemView = (SampleItemView)
                    layout.findViewById(R.id.sample_item);

            HelperUtils.Item item = mItemList.get(i);
            itemView.setTag(item);
            itemView.updateByItem(item);
            itemView.setOnClickListener(mItemClickListener);

            View favView = layout.findViewById(R.id.fav_item);
            favView.setBackgroundResource(R.color.fav_item_bkg_color);
            favView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mScrollView.cancelCurrentSlide(true);
                            HelperUtils.Item item = (HelperUtils.Item) itemView.getTag();
                            item.mFaved = !item.mFaved;
                            itemView.updateByItem(item);

                            ImageView favIconView = (ImageView) layout.findViewById(R.id.fav_icon);
                            favIconView.setImageResource(item.mFaved
                                    ? R.drawable.faved : R.drawable.un_faved);
                        }
                    });

            layout.findViewById(R.id.delete_item).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mScrollView.cancelCurrentSlide(true);
                            contentContainer.removeView(layout);
                        }
                    }
            );

            contentContainer.addView(layout);
        }
    }

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HelperUtils.Item item = (HelperUtils.Item) v.getTag();
            Toast.makeText(getContext(), item.mTitle, Toast.LENGTH_SHORT).show();
        }
    };
}
