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

import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.fragments.ComicPageFragment;
import com.skubit.comics.provider.comicreader.ComicReaderCursor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * Adapter creating view fragments of individual comic pages
 */
public final class ComicViewerAdapter extends FragmentStatePagerAdapter {

    private final ComicReaderCursor mCursor;

    private final ComicViewerActivity mActivity;

    private final int mCount;

    public ComicViewerAdapter(ComicViewerActivity activity, FragmentManager fm, Cursor cursor) {
        super(fm);
        mCursor = new ComicReaderCursor(cursor);
        mActivity = activity;
        mCount = mCursor.getCount();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Fragment getItem(int position) {
        mCursor.moveToPosition(position);
        String pageImage = mCursor.getPageImage();
        ComicPageFragment frag = ComicPageFragment
                .newInstance(pageImage, position + 1, getCount());

        frag.setPageTapListener(mActivity);
        return frag;
    }
}