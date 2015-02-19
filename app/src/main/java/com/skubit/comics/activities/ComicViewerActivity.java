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
package com.skubit.comics.activities;

import com.skubit.comics.Constants;
import com.skubit.comics.R;
import com.skubit.comics.adapters.ComicViewerAdapter;
import com.skubit.comics.loaders.CbzLoader;
import com.skubit.comics.loaders.responses.CbzResponse;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoCursor;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

public class ComicViewerActivity extends Activity {

    public static final String CURRENT_COMIC_EXTRA = "com.skubit.comics.CURRENT_COMIC_EXTRA";

    private static final String ARCHIVE_EXTRA = "com.skubit.comics.ARCHIVE_EXTRA";

    private Cursor mCursor;

    private final LoaderManager.LoaderCallbacks<CbzResponse> mUnarchiverCallback
            = new LoaderManager.LoaderCallbacks<CbzResponse>() {

        @Override
        public Loader<CbzResponse> onCreateLoader(int id, Bundle args) {
            File archive = new File(args.getString(ARCHIVE_EXTRA));
            return new CbzLoader(getBaseContext(),
                    archive,
                    new File(Constants.SKUBIT_UNARCHIVES, archive.getName()));
        }

        @Override
        public void onLoadFinished(Loader<CbzResponse> loader, CbzResponse data) {
            mCursor.requery();
            createViewer(mCursor);
        }

        @Override
        public void onLoaderReset(Loader<CbzResponse> loader) {

        }
    };

    private ViewPager mPager;

    public static Intent newInstance(String archiveFile) {
        Intent i = new Intent();
        i.putExtra(ARCHIVE_EXTRA, archiveFile);
        i.setClassName("com.skubit.comics", ComicViewerActivity.class.getName());
        return i;
    }

    private void createViewer(Cursor c) {

        ComicsInfoCursor comicsInfoCursor = new ComicsInfoCursor(c);

        ComicViewerAdapter comicViewerAdapter = new ComicViewerAdapter(getFragmentManager(), comicsInfoCursor);

        mPager.setAdapter(comicViewerAdapter);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.horizontal_page_margin));
        mPager.setOffscreenPageLimit(2);

        mPager.setAdapter(comicViewerAdapter);

        int extraCurrentItem = getIntent().getIntExtra(CURRENT_COMIC_EXTRA, -1);
        if (extraCurrentItem != -1) {
            mPager.setCurrentItem(extraCurrentItem);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_viewer);

        mPager = (ViewPager) findViewById(R.id.pager);

        String archiveFile = getIntent().getStringExtra(ARCHIVE_EXTRA);
        mCursor = getContentResolver()
                .query(ComicsInfoColumns.CONTENT_URI, null, ComicsInfoColumns.ARCHIVE_FILE
                        + "=?", new String[]{archiveFile}, null);
        if (mCursor.getCount() > 0) {
            createViewer(mCursor);
        } else {
            Bundle args = new Bundle();
            args.putString(ARCHIVE_EXTRA, archiveFile);
            getLoaderManager().initLoader(archiveFile.hashCode(), args, mUnarchiverCallback);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                decorView.setSystemUiVisibility(uiOptions);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }

}
