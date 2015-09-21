/**
 * Copyright 2015 Skubit
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

import com.gc.materialdesign.views.ButtonRectangle;
import com.skubit.AccountSettings;
import com.skubit.android.billing.PurchaseData;
import com.skubit.comics.BillingResponseCodes;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicData;
import com.skubit.comics.OrderReceiver;
import com.skubit.comics.OrderService;
import com.skubit.comics.R;
import com.skubit.comics.Utils;
import com.skubit.comics.adapters.ScreenshotAdapter2;
import com.skubit.comics.fragments.SimilarFragment;
import com.skubit.comics.loaders.ComicDetailsLoader;
import com.skubit.comics.loaders.DownloadComicLoader;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.comics.loaders.ScreenshotsLoader;
import com.skubit.dialog.LoaderResult;
import com.skubit.iab.activities.PurchaseActivity;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookType;
import com.skubit.shared.dto.IssueFormat;
import com.skubit.shared.dto.UrlDto;
import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.TwoWayView;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.Loader;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ComicDetailsActivity extends ActionBarActivity {

    private static final String COMIC_ID = "com.skubit.comics.COMIC_ID";

    private static final int PURCHASE_CODE = 102;

    private ComicData mComicData;

    private ComicBookDto mComicBookDto;

    private DownloadManager mDownloadManager;

    private final LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>> mDownloadCallback
            = new LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>>() {

        @Override
        public Loader<LoaderResult<UrlDto>> onCreateLoader(int id, Bundle args) {
            return new DownloadComicLoader(getBaseContext(), mComicData, false);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<UrlDto>> loader,
                final LoaderResult<UrlDto> data) {
            if (!TextUtils.isEmpty(data.errorMessage)) {
                //  Toast.makeText(getBaseContext(), data.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                mBuyBtn.setText("Purchased");
                mBuyBtn.setEnabled(false);
                Utils.download(getBaseContext(), data.result.getUrl(), "", mComicBookDto, mDownloadManager);
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<UrlDto>> loader) {

        }
    };

    private TextView mTitle;

    private ImageView mCoverArt;

    private TextView mDescription;

    private TextView mPublisher;

    private ButtonRectangle mBuyBtn;

    private ScreenshotAdapter2 mScreenshotAdapter;

    private final LoaderManager.LoaderCallbacks<ArrayList<UrlDto>> mScreenshotsCallback
            = new LoaderManager.LoaderCallbacks<ArrayList<UrlDto>>() {

        @Override
        public Loader<ArrayList<UrlDto>> onCreateLoader(int id, Bundle args) {
            return new ScreenshotsLoader(getBaseContext(), mComicData.getCbid());
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<UrlDto>> loader,
                ArrayList<UrlDto> data) {

            mScreenshotAdapter = new ScreenshotAdapter2(ComicDetailsActivity.this);
            mScreenshotAdapter.setUrls(data);
            mScreenshotGrid.setAdapter(mScreenshotAdapter);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<UrlDto>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<ComicBookDto> mComicDetailsCallback
            = new LoaderManager.LoaderCallbacks<ComicBookDto>() {

        @Override
        public Loader<ComicBookDto> onCreateLoader(int id, Bundle args) {
            return new ComicDetailsLoader(getBaseContext(), mComicData.getCbid());
        }

        @Override
        public void onLoadFinished(Loader<ComicBookDto> loader, final ComicBookDto data) {
            mComicBookDto = data;
            mDescription.setText(data.getDescription());
            refreshButtonState();

            mBuyBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String userId = AccountSettings.get(ComicDetailsActivity.this)
                                .retrieveBitId();
                        if (TextUtils.isEmpty(userId)) {
                            Utils.startAuthorization(ComicDetailsActivity.this);
                            return;
                        }
                        purchase();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void onLoaderReset(Loader<ComicBookDto> loader) {

        }
    };

    private TextView mIssue;

    private TextView mWriter;

    private TextView mPages;

    private TextView mAgeRating;

    private TwoWayView mScreenshotGrid;

    private ButtonRectangle mSampleBtn;

    private IntentFilter mIntentFilter;

    private OrderReceiver mOrderReceiver;

    public static Intent newInstance(ComicData comicData) {
        Intent i = new Intent();
        i.putExtra(ComicData.EXTRA_NAME, comicData);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setClassName(BuildConfig.APPLICATION_ID, ComicDetailsActivity.class.getName());
        return i;
    }

    private Intent makePurchaseIntent(int apiVersion, String userId, String packageName,
            String sku, String devPayload, String type) {

        PurchaseData info = new PurchaseData();
        info.signatureHash = "";
        info.apiVersion = apiVersion;
        info.versionCode = 1;
        info.sku = sku;
        info.developerPayload = devPayload;
        info.packageName = packageName;
        info.type = type;

        return PurchaseActivity.newIntent(userId, info, BuildConfig.APPLICATION_ID);
    }

    private void purchase() throws IntentSender.SendIntentException, RemoteException {
        String userId = AccountSettings.get(ComicDetailsActivity.this)
                .retrieveBitId();
        if (TextUtils.isEmpty(userId)) {
            Utils.startAuthorization(this);
            return;
        }
        //get buy intent
        Intent purchaseIntent = makePurchaseIntent(1, userId, BuildConfig.APPLICATION_ID, mComicBookDto.getCbid(),
                "inapp",
                "payload");
        startActivityForResult(purchaseIntent, PURCHASE_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_comic_details);
        mComicData = getIntent().getParcelableExtra(ComicData.EXTRA_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setTitle(mComicData.getTitle());

        mDownloadManager = (DownloadManager) getSystemService(
                Context.DOWNLOAD_SERVICE);

        mTitle = (TextView) findViewById(R.id.title);
        mCoverArt = (ImageView) findViewById(R.id.coverArt);
        mDescription = (TextView) findViewById(R.id.description);
        mPublisher = (TextView) findViewById(R.id.publisher);
        mIssue = (TextView) findViewById(R.id.issue);
        mWriter = (TextView) findViewById(R.id.writer);
        mPages = (TextView) findViewById(R.id.pages);
        mAgeRating = (TextView) findViewById(R.id.age_rating);
        mBuyBtn = (ButtonRectangle) findViewById(R.id.buyBtn);
        mSampleBtn = (ButtonRectangle) findViewById(R.id.sampleBtn);

        mTitle.setText(mComicData.getTitle());
        mPublisher.setText(mComicData.getPublisher());

        String issueFormat = mComicData.getIssueFormat();
        if (!TextUtils.isEmpty(issueFormat)) {
            if (IssueFormat.GraphicNovel.name().equals(issueFormat)) {
                mIssue.setText("Graphic Novel: Vol. " + mComicData.getVolume());
            } else if (IssueFormat.Omnibus.name().equals(issueFormat)) {
                mIssue.setText("Omnibus Vol." + mComicData.getVolume());
            } else if (IssueFormat.AudioBook.name().equals(issueFormat)) {
                mIssue.setText("Audio Book");
            } else if (TextUtils.isEmpty(mComicData.getVolume())) {
                mIssue.setText("");
            } else {
                mIssue.setText("Vol. " + mComicData.getVolume() + " Issue #" + mComicData
                        .getIssueNumber());
            }
        }

        mWriter.setText("Writer: " + mComicData.getWriter());
        mPages.setText("Pages: " + mComicData.getPageCount());
        mAgeRating.setText("Age: " + mComicData.getAgeRating());

        mScreenshotGrid = (TwoWayView) findViewById(R.id.gridview);

        if (!TextUtils.isEmpty(mComicData.getCoverArt())) {
            Picasso.with(getBaseContext()).load(mComicData.getCoverArt() + "=s300-rw")//.resize(320, 0)
                    .into(mCoverArt);//350,500
        }

        refreshButtonState();

        getLoaderManager().initLoader(9999, null, mScreenshotsCallback);
        getLoaderManager().initLoader(LoaderId.COMIC_DETAILS_LOADER, null, mComicDetailsCallback);

        getFragmentManager().beginTransaction()
                .replace(R.id.similar_container, SimilarFragment.newInstance(mComicData.getTitle()),
                        "SIM_CONTAINER")
                .commit();

        ArchiveFormat archiveFormat = ComicBookType.ELECTRICOMIC.name().equals(mComicData.getComicBookType()) ?
                ArchiveFormat.ELCX : ArchiveFormat.CBZ;
        if(ArchiveFormat.ELCX.equals(archiveFormat)) {
            mSampleBtn.setVisibility(View.GONE);
        } else {
            mSampleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(DownloadDialogActivity
                            .newInstance(mComicData.getCbid(),
                                    mComicData.getTitle()
                                            + " Vol." + mComicData.getVolume()
                                            + " #" + mComicData.getIssueNumber(), true));
                }
            });
        }


        mCoverArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle opts = ActivityOptions.makeScaleUpAnimation(
                        mCoverArt,
                        0, 0,
                        mCoverArt.getWidth(), mCoverArt.getHeight()).toBundle();
                startActivity(CoverArtActivity.newInstance(mComicData.getCoverArt()), opts);
            }
        });

        ButtonRectangle seeMore = (ButtonRectangle) findViewById(R.id.see_more);
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.newInstance(mComicData.getTitle()));
            }
        });

        mOrderReceiver = new OrderReceiver(); 
        mIntentFilter = new IntentFilter(OrderReceiver.ACTION);
    }

    private void refreshButtonState() {
        mBuyBtn.setEnabled(true);
        if (mComicData.isPurchased()) {
            mBuyBtn.setText("Purchased");
            mBuyBtn.setEnabled(false);
        } else if (mComicData.getPrice() == 0) {//TODO: native check
            mBuyBtn.setText("FREE");
        } else {

            String currencySymbol = mComicData.getCurrencySymbol();

            if ("USD".equals(currencySymbol)) {
                mBuyBtn.setText("BUY $" + String.format("%.2f", mComicData.getPrice()));
            } else if ("EUR".equals(currencySymbol)) {

            } else {
                mBuyBtn.setText(
                        "BUY " + mComicData.getNativePrice());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == BillingResponseCodes.RESULT_OK && data != null
                && (data.getIntExtra("RESPONSE_CODE", -1) == 0)) {
            Intent intent = new Intent();
            intent.setClass(this, OrderService.class);
            intent.putExtra(ComicData.EXTRA_NAME, mComicData);
            startService(intent);
        }
        else if (resultCode == BillingResponseCodes.RESULT_ORDER_INITIATED) {
            //TODO: this requires coming back to app
            Intent intent = new Intent();
            intent.setClass(this, OrderService.class);
            intent.putExtra(ComicData.EXTRA_NAME, mComicData);
            startService(intent);

            Toast.makeText(getBaseContext(),
                    "Ordering comic. Notification will display when processed...",
                    Toast.LENGTH_LONG).show();

        } else if (requestCode == PURCHASE_CODE) {
            getLoaderManager().initLoader(mComicData.getCbid().hashCode(), null, mDownloadCallback);
        } else if (requestCode == Utils.AUTHORIZATION_CODE && data != null && !TextUtils
                .isEmpty(data.getStringExtra("response"))) {

           // Utils.createNewAccount(this, data);
            try {
                purchase();
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.push_out_right);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mOrderReceiver, mIntentFilter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mOrderReceiver);
    }
}
