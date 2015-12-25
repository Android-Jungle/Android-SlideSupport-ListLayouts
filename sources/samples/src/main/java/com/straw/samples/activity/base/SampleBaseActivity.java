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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.straw.samples.data.HelperUtils;

public abstract class SampleBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra(HelperUtils.EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            int length = title.length();
            String activity = "Activity";
            int index = title.lastIndexOf(activity);
            if (index == length - activity.length()) {
                title = title.substring(0, index);
            }

            setTitle(title);
        }
    }

    protected Context getContext() {
        return this;
    }

    protected void startActivityInternal(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(HelperUtils.EXTRA_TITLE, clazz.getSimpleName());
        startActivity(intent);
    }
}
