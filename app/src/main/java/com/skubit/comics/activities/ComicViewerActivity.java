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

import com.gc.materialdesign.views.Slider;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.Constants;
import com.skubit.comics.PageTapListener;
import com.skubit.comics.R;
import com.skubit.comics.adapters.ComicViewerAdapter;
import com.skubit.comics.archive.loaders.CbzLoader;
import com.skubit.comics.archive.responses.CbzResponse;
import com.skubit.comics.loaders.ComicViewerLoader;
import com.skubit.comics.loaders.UpdateCurrentPageLoader;
import com.squareup.picasso.Picasso;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.File;

public class ComicViewerActivity extends ActionBarActivity implements PageTapListener {

    public static final String CURRENT_COMIC_EXTRA = "com.skubit.comics.CURRENT_COMIC_EXTRA";

    private static final String ARCHIVE_EXTRA = "com.skubit.comics.ARCHIVE_EXTRA";

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
            getLoaderManager().restartLoader(7000, null, new ComicViewerLoader(ComicViewerActivity.this,
                    getFragmentManager(), mArchiveFile));
        }

        @Override
        public void onLoaderReset(Loader<CbzResponse> loader) {

        }
    };

    private ViewPager mPager;

    private String mArchiveFile;

    private final LoaderManager.LoaderCallbacks<Integer> mSavePageCallback
            = new LoaderManager.LoaderCallbacks<Integer>() {

        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return new UpdateCurrentPageLoader(getBaseContext(), mArchiveFile,
                    mPager.getCurrentItem());
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {

        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {

        }
    };

    private ComicViewerAdapter mAdapter;

    private View mMainView;

    private Slider mSlider;

    private Toolbar toolbar;

    private Picasso mPicasso;

    private boolean mIsFullView = true;

    public static Intent newInstance(String title, String archiveFile, int lastPageRead) {
        Intent i = new Intent();
        i.putExtra("title", title);
        i.putExtra(ARCHIVE_EXTRA, archiveFile);
        i.putExtra(CURRENT_COMIC_EXTRA, lastPageRead);
        i.setClassName(BuildConfig.APPLICATION_ID, ComicViewerActivity.class.getName());
        return i;
    }

    private void createViewer() {
        mPager.setAdapter(mAdapter);
        int extraCurrentItem = getIntent().getIntExtra(CURRENT_COMIC_EXTRA, 1);
        mPager.setCurrentItem(extraCurrentItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {//TODO: option to reload
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_viewer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setVisibility(View.INVISIBLE);

        mMainView = findViewById(R.id.comic_viewer);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.horizontal_page_margin));
        mPager.setOffscreenPageLimit(2);
        mPager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.coverArt));


        mSlider = (Slider) findViewById(R.id.slider);

        mSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int i) {
                if (mPager.getCurrentItem() != i - 1) {
                    mPager.setCurrentItem(i - 1);
                }
            }
        });

        mSlider.setVisibility(View.INVISIBLE);
        String title = getIntent().getStringExtra("title");
        setTitle(title);

        mArchiveFile = getIntent().getStringExtra(ARCHIVE_EXTRA);
        getLoaderManager().initLoader(7000, null, new ComicViewerLoader(this,
                getFragmentManager(), mArchiveFile));
        mMainView.setBackgroundColor(getResources()
                .getColor(android.R.color.background_dark));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPicasso = new Picasso.Builder(getBaseContext()).build();

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
                /*
                if(mSlider.getValue() != position + 1) {
                    mSlider.setValue(position + 1);
                }
                */
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPicasso.shutdown();
        mPicasso = null;
    }

    public void resetAdapter(ComicViewerAdapter adapter) {
        mAdapter = adapter;

        if (mAdapter.getCount() > 0) {
            createViewer();
        } else {
            Bundle args = new Bundle();
            args.putString(ARCHIVE_EXTRA, mArchiveFile);
            getLoaderManager().initLoader(mArchiveFile.hashCode(), args, mUnarchiverCallback);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.none, R.anim.push_out_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoaderManager().restartLoader(2453, null, mSavePageCallback);
    }

    @Override
    public void toggleView() {
        if (mIsFullView) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.animate().translationY(0);

            mMainView.setBackgroundColor(getResources()
                    .getColor(android.R.color.background_light));
            mIsFullView = false;

            mSlider.setVisibility(View.VISIBLE);
            mSlider.animate().translationY(0);
            mSlider.setValue(mPager.getCurrentItem());

            final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_in);
            anim.setFillEnabled(true);
            anim.setFillAfter(true);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                    mPager.setDrawingCacheEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    mPager.setScaleX(.83f);
                    mPager.setScaleY(.83f);
                    mPager.clearAnimation();
                }
            });

            mPager.setAnimation(anim);
            mPager.startAnimation(anim);

        } else {
            toolbar.animate().translationY(-toolbar.getHeight());
            toolbar.setLayoutAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    toolbar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mMainView.setBackgroundColor(getResources()
                    .getColor(android.R.color.background_dark));
            mIsFullView = true;

            mSlider.animate().translationY(mMainView.getHeight() + mSlider.getHeight());
            mSlider.setLayoutAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSlider.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_out);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                    mPager.setScaleX(1f);
                    mPager.setScaleY(1f);
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    mPager.clearAnimation();
                }
            });
            mPager.setAnimation(anim);
            mPager.startAnimation(anim);


        }
    }

    @Override
    public void setTotalPages(int totalPages) {
        mSlider.setMax(totalPages);
    }

    @Override
    public Picasso getPicasso() {
        if (mPicasso == null) {
            mPicasso = new Picasso.Builder(getBaseContext()).build();
        }
        return mPicasso;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.none, R.anim.push_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
