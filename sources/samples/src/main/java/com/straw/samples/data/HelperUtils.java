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

import com.straw.samples.R;

import java.util.ArrayList;
import java.util.List;

public class HelperUtils {

    public static final String EXTRA_TITLE = "extra_title";


    public static class Item {
        public int mIconResId;
        public String mTitle;
        public String mDescription;
        public boolean mFaved;

        public Item(int iconResId, String title, String description) {
            mIconResId = iconResId;
            mTitle = title;
            mDescription = description;
        }
    }


    public static class ExpandItem {
        public String mGroupTitle;
        public List<Item> mItemList = new ArrayList<>();

        public ExpandItem(String title) {
            mGroupTitle = title;
        }

        public void addItem(Item item) {
            mItemList.add(item);
        }

        public void addItems(List<Item> items) {
            mItemList.addAll(items);
        }
    }


    public static List<Item> getItemList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(R.drawable.java, "JVM Languages", "Java/ Scala/ Groovy/ Kotlin"));
        list.add(new Item(R.drawable.frameworks, "Enterprise Frameworks", "Spring/ Jboss/ Java EE/ Grails"));
        list.add(new Item(R.drawable.android, "Mobile Development", "Android/ PhoneGap/ Cordova/ Lonic"));
        list.add(new Item(R.drawable.web, "Web Development", "Javascript/ HTML/ CSS/ Node.js/ React"));
        list.add(new Item(R.drawable.build_tools, "Built-in Tools", "Build Tools/ Version Controls/ Decompiler/ SQL"));
        list.add(new Item(R.drawable.ide, "Development IDE", "IDEA/ Android Studio/ Eclipse/ Visual Studio"));
        list.add(new Item(R.drawable.platform, "Development Platform", "Windows/ Android/ Windows Phone/ iOS"));

        return list;
    }

    public static List<ExpandItem> getExpandItemList() {
        List<ExpandItem> list = new ArrayList<>();

        {
            List<Item> itemList = getItemList();
            itemList.remove(0);
            itemList.remove(0);
            itemList.remove(0);
            ExpandItem item = new ExpandItem("First Group");
            item.addItems(itemList);

            list.add(item);
        }

        {
            List<Item> itemList = getItemList();
            ExpandItem item = new ExpandItem("Second Group");
            item.addItems(itemList);

            list.add(item);
        }

        return list;
    }
}
