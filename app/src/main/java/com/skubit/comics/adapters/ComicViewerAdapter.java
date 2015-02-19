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
package com.skubit.comics.adapters;

import com.skubit.comics.fragments.ComicPageFragment;
import com.skubit.comics.provider.comicsinfo.ComicsInfoCursor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

/**
 * Adapter creating view fragments of individual comic pages
 */
public final class ComicViewerAdapter extends FragmentStatePagerAdapter {

    private final ComicsInfoCursor mCursor;

    public ComicViewerAdapter(FragmentManager fm, ComicsInfoCursor cursor) {
        super(fm);
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Fragment getItem(int position) {
        mCursor.moveToPosition(position);
        String pageImage = mCursor.getPageImage();
        if(TextUtils.isEmpty(pageImage)) {
            //return page fragment explaining error
        }
        return ComicPageFragment
                .newInstance(pageImage, position + 1, getCount());
    }
}