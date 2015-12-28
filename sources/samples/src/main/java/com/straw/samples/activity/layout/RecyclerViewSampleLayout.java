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

package com.straw.samples.activity.layout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.widget.SlideSupportRecyclerView;
import com.straw.samples.R;
import com.straw.samples.data.HelperUtils;
import com.straw.samples.widget.SampleItemView;

import java.util.List;

public class RecyclerViewSampleLayout extends FrameLayout {

    protected SlideSupportRecyclerView mRecyclerView;
    private List<HelperUtils.Item> mItemList;
    private SlideSupportRecyclerView.SlideAdapter<ItemHolder> mAdapter;
    private int mFavViewBkgColorResId;


    public RecyclerViewSampleLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public RecyclerViewSampleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public RecyclerViewSampleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View.inflate(context, R.layout.layout_recycler_view_sample, this);
        mItemList = HelperUtils.getItemList();
        mRecyclerView = (SlideSupportRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(createAdapter(R.layout.layout_item_with_delete));
    }

    public void reload() {
        mItemList = HelperUtils.getItemList();
        mAdapter.notifyDataSetChanged();
    }

    public SlideSupportRecyclerView getSlideRecyclerView() {
        return mRecyclerView;
    }

    public void setFavViewBkgColorResId(int colorResId) {
        mFavViewBkgColorResId = colorResId;
    }

    public void setSampleItemLayoutResId(int layoutResId) {
        mRecyclerView.setAdapter(createAdapter(layoutResId));
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        private SampleItemView mSampleItemView;
        private ImageView mFavIconView;
        private View mFavView;

        private View.OnClickListener mFavClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.cancelCurrentSlide(true);
                HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
                item.mFaved = !item.mFaved;
                mAdapter.notifyDataSetChanged();
            }
        };

        private View.OnClickListener mDeleteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.cancelCurrentSlide(true);
                HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
                mItemList.remove(item);
                mAdapter.notifyDataSetChanged();
            }
        };

        private View.OnClickListener mItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperUtils.Item item = (HelperUtils.Item) mSampleItemView.getTag();
                Toast.makeText(getContext(), item.mTitle, Toast.LENGTH_SHORT).show();
            }
        };

        public ItemHolder(View itemView) {
            super(itemView);
            mFavIconView = (ImageView) itemView.findViewById(R.id.fav_icon);
            mSampleItemView = (SampleItemView) itemView.findViewById(R.id.sample_item);
            mSampleItemView.showLine(false);
            mSampleItemView.setOnClickListener(mItemClickListener);
            mFavView = itemView.findViewById(R.id.fav_item);

            mFavView.setOnClickListener(mFavClickListener);
            itemView.findViewById(R.id.delete_item)
                    .setOnClickListener(mDeleteClickListener);
        }

        public void update(HelperUtils.Item item) {
            mSampleItemView.updateByItem(item);
            mSampleItemView.setTag(item);
            mFavIconView.setImageResource(item.mFaved ? R.drawable.faved : R.drawable.un_faved);

            if (mFavViewBkgColorResId != 0) {
                mFavView.setBackgroundResource(mFavViewBkgColorResId);
            }
        }
    }

    private SlideSupportRecyclerView.SlideAdapter<ItemHolder> createAdapter(
            final int layoutResId) {

        mAdapter = new SlideSupportRecyclerView.SlideAdapter<ItemHolder>() {

            @Override
            public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                SlideSupportLayout layout = createSlideLayout(parent);
                View.inflate(getContext(), layoutResId, layout);
                return new ItemHolder(layout);
            }

            @Override
            public void onBindViewHolder(ItemHolder holder, int position) {
                holder.update(mItemList.get(position));
            }

            @Override
            public int getItemCount() {
                return mItemList.size();
            }
        };

        return mAdapter;
    }
}
