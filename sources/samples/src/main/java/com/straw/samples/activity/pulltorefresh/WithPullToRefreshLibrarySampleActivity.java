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

package com.straw.samples.activity.pulltorefresh;

import android.os.Bundle;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.straw.library.slide.support.SlideMode;
import com.straw.library.slide.widget.SlideSupportListView;
import com.straw.samples.R;
import com.straw.samples.activity.base.ModeSwitchableSampleActivity;
import com.straw.samples.activity.listview.SlideListViewAdapter;

public class WithPullToRefreshLibrarySampleActivity
        extends ModeSwitchableSampleActivity {

    private SlideSupportPullToRefreshListView mRefreshListView;
    private SlideListViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_with_pull_to_refresh);
        mRefreshListView = (SlideSupportPullToRefreshListView)
                findViewById(R.id.refresh_list_view);

        initRefreshListView();
        mAdapter = new SlideListViewAdapter(this, getSlideListView());
        mRefreshListView.setAdapter(mAdapter);
        mRefreshListView.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> pullToRefreshBase) {

                        mAdapter.reload();
                        refreshComplete();
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> pullToRefreshBase) {

                        mAdapter.loadMore();
                        refreshComplete();
                    }
                });
    }

    private SlideSupportListView getSlideListView() {
        return (SlideSupportListView) mRefreshListView.getRefreshableView();
    }

    @Override
    public void switchSlideMode(SlideMode mode) {
        getSlideListView().setSlideMode(mode);
    }

    private void initRefreshListView() {
        ILoadingLayout layout = mRefreshListView.getLoadingLayoutProxy();
        layout.setPullLabel(getString(R.string.pull_torefresh));
        layout.setReleaseLabel(getString(R.string.release_to_refresh));
        layout.setRefreshingLabel(getString(R.string.refreshing_tips));
    }

    private void refreshComplete() {
        mRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshListView.onRefreshComplete();
            }
        }, 500);
    }
}
