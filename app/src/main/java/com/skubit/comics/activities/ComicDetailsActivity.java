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
import com.skubit.android.billing.IBillingService;
import com.skubit.comics.BillingResponseCodes;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicData;
import com.skubit.comics.Constants;
import com.skubit.comics.LockerUpdaterService;
import com.skubit.comics.R;
import com.skubit.comics.Utils;
import com.skubit.comics.loaders.ComicDetailsLoader;
import com.skubit.comics.loaders.DownloadComicLoader;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.UrlDto;
import com.squareup.picasso.Picasso;

import android.app.DownloadManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.Loader;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
            return new DownloadComicLoader(getBaseContext(), mComicData.getCbid());
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<UrlDto>> loader,
                final LoaderResult<UrlDto> data) {
            if (!TextUtils.isEmpty(data.errorMessage)) {
              //  Toast.makeText(getBaseContext(), data.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                mBuyBtn.setText("Purchased");
                mBuyBtn.setEnabled(false);
                Utils.download(data.result.getUrl(), mComicBookDto, mDownloadManager);
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<UrlDto>> loader) {

        }
    };

    private TextView mTitle;

    private ImageView mCoverArt;

    private TextView mDescription;

    private TextView mWebContact;

    private TextView mEmalContact;

    private TextView mPublisher;

    private ButtonRectangle mBuyBtn;

    private IBillingService mService;

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
                            if(!Utils.isIabInstalled(getPackageManager())) {
                                startActivityForResult(Utils.getIabIntent(), Utils.PLAY_CODE);
                            } else {
                                //TODO: prompt to install and then we need to restart service
                                Utils.startAuthorization(ComicDetailsActivity.this, mService);
                            }
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

            if (!TextUtils.isEmpty(data.getWebsite())) {
                mWebContact.setText(data.getWebsite());
                mWebContact.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            if (!TextUtils.isEmpty(data.getContactEmail())) {
                mEmalContact.setText(data.getContactEmail());
            }
        }

        @Override
        public void onLoaderReset(Loader<ComicBookDto> loader) {

        }
    };

    private ServiceConnection mServiceConn;

    public static Intent newInstance(ComicData comicData) {
        Intent i = new Intent();
        i.putExtra(ComicData.EXTRA_NAME, comicData);
        i.setClassName(BuildConfig.APPLICATION_ID, ComicDetailsActivity.class.getName());
        return i;
    }

    private void purchase() throws IntentSender.SendIntentException, RemoteException {
        String userId = AccountSettings.get(ComicDetailsActivity.this)
                .retrieveBitId();

        if(mService != null) {
            if(TextUtils.isEmpty(userId)) {
                Utils.startAuthorization(this, mService);
                return;
            }
            Bundle bundle = mService
                    .getBuyIntent(1, userId, BuildConfig.APPLICATION_ID, mComicBookDto.getCbid(),
                            "inapp",
                            "payload");
            int code = bundle.getInt("RESPONSE_CODE");
            if (code == BillingResponseCodes.RESULT_OK) {
                PendingIntent pendingIntent = bundle.getParcelable("BUY_INTENT");
                startIntentSenderForResult(pendingIntent.getIntentSender(),
                        PURCHASE_CODE, null, 0, 0, 0);
            } else if (code == BillingResponseCodes.RESULT_USER_ACCESS) {
                Utils.startAuthorization(this, mService);
            }
        } else {
            if(!Utils.isIabInstalled(getPackageManager())) {
                startActivityForResult(Utils.getIabIntent(), Utils.PLAY_CODE);
            }
            //TODO: prompt to install and then we need to restart service
        }

    }

    private void startBillingService() {
        bindService(Utils.getBillingServiceIntent(), mServiceConn, Context.BIND_AUTO_CREATE);
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

        mServiceConn = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                mService = IBillingService.Stub.asInterface(service);
                if(doPurchase) {
                    doPurchase = false;
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
            public void onServiceDisconnected(ComponentName arg0) {
                mService = null;
            }
        };

        mDownloadManager = (DownloadManager) getSystemService(
                Context.DOWNLOAD_SERVICE);

        mTitle = (TextView) findViewById(R.id.title);
        mCoverArt = (ImageView) findViewById(R.id.coverArt);
        mDescription = (TextView) findViewById(R.id.description);
        mWebContact = (TextView) findViewById(R.id.publisher_webpage);
        mEmalContact = (TextView) findViewById(R.id.publisher_email);
        mPublisher = (TextView) findViewById(R.id.publisher);
        mBuyBtn = (ButtonRectangle) findViewById(R.id.buyBtn);

        mTitle.setText(mComicData.getTitle());
        mPublisher.setText(mComicData.getPublisher());

        if (!TextUtils.isEmpty(mComicData.getCoverArt())) {
            Picasso.with(getBaseContext()).load(mComicData.getCoverArt()).resize(300, 0)
                    .into(mCoverArt);//350,500
        }
        refreshButtonState();

        startBillingService();

        getLoaderManager().initLoader(LoaderId.COMIC_DETAILS_LOADER, null, mComicDetailsCallback);
    }

    private void refreshButtonState() {
        mBuyBtn.setEnabled(true);
        if (mComicData.isPurchased()) {
            mBuyBtn.setText("Purchased");
            mBuyBtn.setEnabled(false);
        } else {
            mBuyBtn.setText(
                    "BUY " + mComicData.getPrice());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BillingResponseCodes.RESULT_ORDER_INITIATED) {
            Intent intent = new Intent();
            intent.setClass(this, LockerUpdaterService.class);
            startService(intent);
        } else if (requestCode == PURCHASE_CODE) {
            getLoaderManager().initLoader(mComicData.getCbid().hashCode(), null, mDownloadCallback);
        } else if (requestCode == Utils.AUTHORIZATION_CODE && data != null && !TextUtils
                .isEmpty(data.getStringExtra("response"))) {

            Utils.createNewAccount(this, data);
            try {
                purchase();
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else if(requestCode == Utils.PLAY_CODE) {
            doPurchase = true;
            startBillingService();
        }
    }

    private boolean doPurchase;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            this.unbindService(mServiceConn);
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
}
