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

package com.straw.samples.data;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.straw.library.slide.support.SlideSupporter;
import com.straw.samples.R;
import com.straw.samples.widget.SampleItemView;

public class SampleItemHolder {

    public interface OnDataSetChangedListener {
        void onRemoveItem(HelperUtils.Item item);

        void onDataSetChanged();

        void onRemoveItemGroup(HelperUtils.ExpandItem expandItem);
    }


    private ImageView mFavIconView;
    private SampleItemView mSampleItemView;
    private SlideSupporter mSlideSupporter;
    private OnDataSetChangedListener mDataSetChangedListener;
    private HelperUtils.ExpandItem mExpandItem;


    public SampleItemHolder(
            View itemView,
            SlideSupporter supporter,
            OnDataSetChangedListener listener) {

        mSlideSupporter = supporter;
        mDataSetChangedListener = listener;

        mFavIconView = (ImageView) itemView.findViewById(R.id.fav_icon);
        mSampleItemView = (SampleItemView) itemView.findViewById(R.id.sample_item);
        mSampleItemView.showLine(false);
        mSampleItemView.setOnClickListener(mItemClickListener);

        itemView.findViewById(R.id.fav_item)
                .setOnClickListener(mFavClickListener);
        itemView.findViewById(R.id.delete_item)
                .setOnClickListener(mDeleteClickListener);
    }

    public void update(HelperUtils.ExpandItem expandItem, HelperUtils.Item item) {
        mExpandItem = expandItem;
        update(item);
    }

    public void update(HelperUtils.Item item) {
        mSampleItemView.updateByItem(item);
        mSampleItemView.setTag(item);
        mFavIconView.setImageResource(item.mFaved ? R.drawable.faved : R.drawable.un_faved);
    }

    private View.OnClickListener mFavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSlideSupporter.cancelCurrentSlide(true);
            HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
            item.mFaved = !item.mFaved;
            mDataSetChangedListener.onDataSetChanged();
        }
    };

    private View.OnClickListener mDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSlideSupporter.cancelCurrentSlide(true);
            HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
            if (mExpandItem != null) {
                mExpandItem.mItemList.remove(item);
                if (mExpandItem.mItemList.isEmpty()) {
                    mDataSetChangedListener.onRemoveItemGroup(mExpandItem);
                }
            } else {
                mDataSetChangedListener.onRemoveItem(item);
            }

            mDataSetChangedListener.onDataSetChanged();
        }
    };

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
            Toast.makeText(mSampleItemView.getContext(),
                    item.mTitle, Toast.LENGTH_SHORT).show();
        }
    };
}
