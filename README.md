#Android-SlideSupport-ListLayouts 使用简介

####&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**English Usage Click [Here](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/README-en.md)。**


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Android-SlideSupport-ListLayouts 是一款用于为 Android 上的 List 排布提供左滑右滑操作的库。目前支持的 Layout 主要有： `ListView`、`RecyclerView`、`ScrollView`、`ExpandableListView` 等。另外还可以与 `SwpieRefreshLayout`、`PullToRefresh` 等第三方库协同工作。

### 1、支持的 Layout

- ListView
- **RecyclerView**
- ExpandableListView
- ScrollView
- **SwipeRefreshLayout** + xxxView
- **PullToRefresh** + xxxView

### 2、相关下载

- **Library aar**：[Android-SlideSupport-ListLayouts__1.0.aar](https://raw.githubusercontent.com/arnozhang/Android-SlideSupport-ListLayouts/master/release/Android-SlideSupport-ListLayouts__1.0.aar)
- **Samples apk**：[Android-SlideSupport-ListLayouts__samples.apk](https://raw.githubusercontent.com/arnozhang/Android-SlideSupport-ListLayouts/master/release/Android-SlideSupport-ListLayouts__samples.apk)

### 3、使用预览
|ListView|RecyclerView + SwipeRefreshLayout|ListView + PullToRefresh Library|
|---|---|---|
|![ListView](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/docs/screenshots/list_view.gif?raw=true)|![RecyclerView + SwipeRefreshLayout](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/docs/screenshots/with_swipe_refresh_layout.gif?raw=true)|![ListView + PullToRefreshLibrary](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/docs/screenshots/pull_to_refresh_list_view.gif?raw=true)|

|ExpandableListView|Customized Slide Action|
|---|---|
|![ExpandableListView](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/docs/screenshots/expandable_list_view.gif?raw=true)|![Customized Slide Action](https://github.com/arnozhang/Android-SlideSupport-ListLayouts/blob/master/docs/screenshots/customized_slide_action.gif?raw=true)|

### 4、使用方式
#### 4.1、XML 中指定滑动 View 和滑动动作
```xml
<com.straw.library.slide.widget.SlideSupportListView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/list_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:leftViewId="@+id/fav_item"
    app:rightViewId="@+id/delete_item"
    app:contentViewId="@+id/sample_item"
    app:slideStyle="moveWithContent"
    app:slideInterpolator="@android:anim/decelerate_interpolator"
    app:slideDuration="200"/>
```

#### 4.2、为 SlideSupportListView 配置 Adapter
```java
SlideSupportListView.SlideAdapter adapter = new SlideSupportListView.SlideAdapter {

    // ...
    
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
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以看出，和平常所用 Adapter 主要的不同在 getView 的时候：

```java
SlideSupportLayout layout = createSlideLayout(parent);
View.inflate(mContext, R.layout.layout_item_with_delete, layout);
convertView = layout;
```

#### 4.3、支持的滑动类型

|滑动类型|作用|
|---|---|
|none|不进行任何滑动|
|leftToRight|只支持手指从左往右滑动|
|rightToLeft|只支持手指从右往左滑动|
|both|左右两个方向的滑动均支持|

#### 4.4、XML 中支持的滑动动作

|滑动动作|作用|
|---|---|
|moveItemOnly|只滑动配置的 View|
|moveWithContent|同时滑动配置的 View 和 contentView|
|scaleItem|缩放配置的 View|
|rotateItem|旋转配置的 View|
|alphaItem|调整配置 View 的透明度|

#### 4.5、XML 中支持配置的属性

|属性|作用|
|---|---|
|slideMode|配置滑动类型（`none`、`leftToRight`、`rightToLeft`、`both`）|
|slideStyle|配置滑动动作（一次性指定左右两个方向的滑动动作）|
|leftToRightSlideStyle|指定从左往右滑动的动作|
|rightToLeftSlideStyle|指定从右往左滑动的动作|
|leftViewId|指定左边的滑动 View Id|
|rightViewId|指定右边的滑动 View Id|
|contentViewId|指定整个根 View 的 Id，以支持联动|
|slideDuration|滑动时长，ms|
|slideInterpolator|滑动的插值器，如 `@android:anim/decelerate_interpolator`|

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中，Move、Scale、Rotate 还可以单独指定属性：

|属性|生效的动作类型|作用|
|---|---|---|
|slideMoveDistance|`moveItemOnly` & `moveWithContent`|手动设置滑动距离（很少用到，除非有非常特殊的情况）|

|属性|生效的动作类型|作用|
|---|---|---|
|fromScale|`scaleItem`|缩放开始比例|
|toScale|`scaleItem`|缩放结束比例|
|scaleDuration|`scaleItem`|缩放时长，ms. 如不指定将继承 `slideDuration` 的值|

|属性|生效的动作类型|作用|
|---|---|---|
|fromDegree|`rotateItem`|旋转开始角度|
|toDegree|`rotateItem`|旋转结束角度|
|rotateDuration|`rotateItem`|旋转时长，ms. 如不指定将继承 `slideDuration` 的值|

|属性|生效的动作类型|作用|
|---|---|---|
|fromAlpha|`alphaItem`|开始透明度，[ 0, 1.0f ]|
|toAlpha|`alphaItem`|结束透明度，[ 0, 1.0f ]|
|alphaDuration|`alphaItem`|透明度渐变时长，ms. 如不指定将继承 `slideDuration` 的值|

### 5、XML 中必须配置的属性

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一般来说，大部分属性都有默认值，如果你要一个简单的效果，有下面几项在对应情况下是必须配置的：

- `leftViewId`：想要从左往右滑动效果时必须指定
- `rightViewId`：想要从右往左滑动效果时必须指定
- `contentViewId`：想要滑动 View 和 contentView 联动时必须配置（如 slideStyle 为 `moveWithContent`）

### 6、滑动动作列表

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;滑动的具体展现方式是通过 `SlideHandler` 来配置的，目前支持的滑动动作列表如下：

|动作|是否支持在 XML 中指定|作用|
|---|---|---|
|MoveItemOnlySlideHandler|是，`moveItemOnly`|只滑动指定的 View|
|MoveWithContentSlideHandler|是，`moveWithContent`|联动滑动 ContentView 和指定的 View|
|ScaleSlideHandler|是，`scaleItem`|缩放指定的 View|
|RotateSlideHandler|是，`rotateItem`|旋转指定的 View|
|AlphaSlideHandler|是，`alphaItem`|调整指定 View 的透明度|
|CompositeSlideHandler|是，通过 `leftToRightStyle` 和 `rightToLeftStyle` 指定|组合两个动作 Handler，分别为左滑和右滑指定单独的动作|
|DelayTimeSlideHandler|否|延时器，比如这个滑动动作需要延时若干 ms 再执行|
|SlideHandlerSet|否|动作叠加器，可叠加若干动作同时执行|
|SlideHandlerSequence|否|动作序列，可将若干动作按顺序依次执行|
|CallbackSlideHandler|否|动作执行回调，可和 `SlideHandlerSequence` 结合执行，在某个动作执行完后回调|

### 7、自定义动作

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以通过实现 `SlideHandler` 接口来实现自己的动作，通过 `SlideSupporter.setSlideHandler` 方法设置到对应的 View。具体可参考 samples 中的 `CustomizedSlideActionLayout` 的实现。

### 8、高级操作

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以通过 `CompositeSlideHandler`、`SlideHandlerSet`、`SlideHandlerSequence`、`CallbackSlideHandler`、`DelayTimeSlideHandler` 等 Handler 组合，来实现一系列高级的用法。比如：

```java
public class SlideActionSetSampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        // ...
        SlideSupportRecyclerView recyclerView = (SlideSupportRecyclerView)
                findViewById(R.id.slide_recycler_view);

        CompositeSlideHandler handler = new CompositeSlideHandler(
                createLeftToRightHandler(),
                createRightToLeftHandler());
        recyclerView.setSlideHandler(handler);
    }

    private SlideHandler createLeftToRightHandler() {
        MoveWithContentSlideHandler move = new MoveWithContentSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);

        DelayTimeSlideHandler delay = new DelayTimeSlideHandler(800);

        RotateSlideHandler rotate = new RotateSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        rotate.setFromDegree(0);
        rotate.setToDegree(360);
        rotate.setAnimatorDuration(600);

        CallbackSlideHandler callback = new CallbackSlideHandler(
                new CallbackSlideHandler.SlideCallback() {
                    @Override
                    public void onFinished(boolean isSlide,
                            SlideSupportLayout layout, SlideMode mode) {

                        if (isSlide) {
                            Toast.makeText(getContext(),
                                    mode.toString() + " Finished!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return new SlideHandlerSequence(move, delay, rotate, callback);
    }

    private SlideHandler createRightToLeftHandler() {
        RotateSlideHandler rotate = new RotateSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        rotate.setFromDegree(0);
        rotate.setToDegree(360);
        rotate.setAnimatorDuration(600);

        ScaleSlideHandler scale = new ScaleSlideHandler(
                R.id.fav_item, R.id.delete_item, R.id.sample_item);
        scale.setFromScale(0);
        scale.setToScale(1);
        scale.setAnimatorDuration(600);

        return new SlideHandlerSet(rotate, scale);
    }

    // ...
}
```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以看出，在 `createLeftToRightHandler` 这个方法中，通过 `SlideHandlerSequence` Handler，将四个动作依次连接起来。具体按顺序执行为：`移动` - `等待一段时间` - `旋转` - `回调`。

---

### 联系作者

arnozhang

zyfgood12@gmail.com

zyfgood12@163.com

---
### License

```java
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
```
