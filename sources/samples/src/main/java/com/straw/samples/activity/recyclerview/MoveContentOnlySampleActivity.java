package com.straw.samples.activity.recyclerview;

import android.os.Bundle;
import com.straw.library.slide.handler.MoveContentOnlySlideHandler;
import com.straw.samples.R;

public class MoveContentOnlySampleActivity extends RecyclerViewSampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerLayout.setSampleItemLayoutResId(
                R.layout.layout_item_with_delete_overlay);
        mRecyclerLayout.getSlideRecyclerView().setSlideHandler(
                new MoveContentOnlySlideHandler(
                        R.id.fav_item, R.id.delete_item, R.id.sample_item));
    }
}
