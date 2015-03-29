/* Copyright 2015 Skubit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.skubit.comics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public final class ComicGridView extends RecyclerView {

    private GridLayoutManager mLayoutManager;

    public ComicGridView(Context context) {
        super(context);
        initManager();
    }

    public ComicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initManager();
    }

    public ComicGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initManager();
    }

    private void initManager() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        setLayoutManager(mLayoutManager);
    }

    public GridLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        mLayoutManager.setSpanCount(Math.max(1, getMeasuredWidth() / 300));
    }
}
