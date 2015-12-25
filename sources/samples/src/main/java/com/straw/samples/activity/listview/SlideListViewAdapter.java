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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.widget.SlideSupportListView;
import com.straw.samples.R;
import com.straw.samples.data.HelperUtils;
import com.straw.samples.data.SampleItemHolder;

import java.util.List;

public class SlideListViewAdapter extends SlideSupportListView.SlideAdapter {

    private Context mContext;
    private SlideSupportListView mListView;
    private List<HelperUtils.Item> mItemList;


    public SlideListViewAdapter(
            Context context, SlideSupportListView listView) {

        this(context, listView, HelperUtils.getItemList());
    }

    public SlideListViewAdapter(
            Context context,
            SlideSupportListView listView,
            List<HelperUtils.Item> itemList) {

        mContext = context;
        mListView = listView;
        mItemList = itemList;
    }

    public void reload() {
        mItemList = HelperUtils.getItemList();
        notifyDataSetChanged();
    }

    public void loadMore() {
        mItemList.addAll(HelperUtils.getItemList());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SampleItemHolder holder = null;
        if (convertView != null) {
            holder = (SampleItemHolder) convertView.getTag();
        } else {
            SlideSupportLayout layout = createSlideLayout(parent);
            View.inflate(mContext, R.layout.layout_item_with_delete, layout);
            holder = new SampleItemHolder(layout, mListView, mDataSetChangedListener);

            convertView = layout;
            convertView.setTag(holder);
        }

        holder.update(mItemList.get(position));
        return convertView;
    }

    private SampleItemHolder.OnDataSetChangedListener mDataSetChangedListener =
            new SampleItemHolder.OnDataSetChangedListener() {
                @Override
                public void onRemoveItem(HelperUtils.Item item) {
                    mItemList.remove(item);
                }

                @Override
                public void onRemoveItemGroup(HelperUtils.ExpandItem expandItem) {
                }

                @Override
                public void onDataSetChanged() {
                    notifyDataSetChanged();
                }
            };
}
