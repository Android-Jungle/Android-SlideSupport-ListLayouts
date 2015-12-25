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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.support.SlideSupportLayout;
import com.straw.library.slide.widget.SlideSupportExpandableListView;
import com.straw.samples.R;
import com.straw.samples.activity.base.ModeSwitchableSampleActivity;
import com.straw.samples.data.HelperUtils;
import com.straw.samples.data.SampleItemHolder;

import java.util.List;

public class ExpandableListViewSampleActivity
        extends ModeSwitchableSampleActivity {

    private List<HelperUtils.ExpandItem> mExpandItemList;
    private SlideSupportExpandableListView mExpandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpandItemList = HelperUtils.getExpandItemList();
        setContentView(R.layout.activity_expandable_list_view_sample);
        mExpandableListView = (SlideSupportExpandableListView)
                findViewById(R.id.expandable_list_view);
        mExpandableListView.setAdapter(mAdapter);

        for (int i = 0; i < mAdapter.getGroupCount(); ++i) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void switchSlideMode(SlideMode mode) {
        mExpandableListView.setSlideMode(mode);
    }

    private SampleItemHolder.OnDataSetChangedListener mDataSetChangedListener =
            new SampleItemHolder.OnDataSetChangedListener() {
                @Override
                public void onRemoveItem(HelperUtils.Item item) {
                }

                @Override
                public void onRemoveItemGroup(HelperUtils.ExpandItem expandItem) {
                    mExpandItemList.remove(expandItem);
                }

                @Override
                public void onDataSetChanged() {
                    mAdapter.notifyDataSetChanged();
                }
            };

    private SlideSupportExpandableListView.SlideAdapter mAdapter =
            new SlideSupportExpandableListView.SlideAdapter() {
                @Override
                public int getGroupCount() {
                    return mExpandItemList.size();
                }

                @Override
                public int getChildrenCount(int groupPosition) {
                    return mExpandItemList.get(groupPosition).mItemList.size();
                }

                @Override
                public Object getGroup(int groupPosition) {
                    return mExpandItemList.get(groupPosition);
                }

                @Override
                public Object getChild(int groupPosition, int childPosition) {
                    return mExpandItemList.get(groupPosition)
                            .mItemList.get(childPosition);
                }

                @Override
                public long getGroupId(int groupPosition) {
                    return groupPosition;
                }

                @Override
                public long getChildId(int groupPosition, int childPosition) {
                    return childPosition;
                }

                @Override
                public boolean hasStableIds() {
                    return false;
                }

                @Override
                public View getGroupView(int groupPosition, boolean isExpanded,
                        View convertView, ViewGroup parent) {

                    if (convertView == null) {
                        convertView = View.inflate(getContext(), R.layout.layout_group_item, null);
                    }

                    TextView titleView = (TextView) convertView;
                    titleView.setText(mExpandItemList.get(groupPosition).mGroupTitle);

                    return convertView;
                }

                @Override
                public View getChildView(int groupPosition, int childPosition,
                        boolean isLastChild, View convertView, ViewGroup parent) {

                    SampleItemHolder holder = null;
                    if (convertView != null) {
                        holder = (SampleItemHolder) convertView.getTag();
                    } else {
                        SlideSupportLayout layout = createSlideLayout(parent);
                        View.inflate(getContext(), R.layout.layout_item_with_delete, layout);
                        holder = new SampleItemHolder(layout, mExpandableListView, mDataSetChangedListener);

                        convertView = layout;
                        convertView.setTag(holder);
                    }

                    HelperUtils.ExpandItem item = mExpandItemList.get(groupPosition);
                    holder.update(item, item.mItemList.get(childPosition));
                    return convertView;
                }

                @Override
                public boolean isChildSelectable(int groupPosition, int childPosition) {
                    return false;
                }
            };
}
