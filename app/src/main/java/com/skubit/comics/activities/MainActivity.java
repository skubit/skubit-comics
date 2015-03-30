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

import com.skubit.android.billing.IBillingService;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicData;
import com.skubit.comics.Constants;
import com.skubit.comics.ControllerCallback;
import com.skubit.comics.R;
import com.skubit.comics.UiState;
import com.skubit.comics.Utils;
import com.skubit.comics.fragments.CatalogFragment;
import com.skubit.comics.fragments.LockerFragment;
import com.skubit.comics.fragments.MyCollectionsFragment;
import com.skubit.comics.fragments.MyComicsFragment;
import com.skubit.navigation.NavigationDrawerCallbacks;
import com.skubit.navigation.NavigationDrawerFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks,
        ControllerCallback {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private IBillingService mService;

    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IBillingService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };

    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(
                        R.id.fragment_drawer);
        mNavigationDrawerFragment
                .setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);

        String serviceName = BuildConfig.FLAVOR.startsWith("dev") ? Constants.IAB_TEST
                : Constants.IAB_PROD;
        bindService(new Intent(serviceName + ".billing.IBillingService.BIND"),
                mServiceConn, Context.BIND_AUTO_CREATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int order = item.getOrder();
        if (order == 1) {
            Utils.startAuthorization(this, mService);
        } else if (order == 4) {
            Intent i = new Intent();
            i.setClass(this, DisplayLicensesActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.AUTHORIZATION_CODE && data != null && !TextUtils
                .isEmpty(data.getStringExtra("response"))) {
            Utils.createNewAccount(this, data);
        }
    }

    @Override
    public void showComicDetails(ComicData comicData) {
        startActivity(ComicDetailsActivity.newInstance(comicData));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == 0) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new CatalogFragment(), UiState.CATALOG)
                    .commit();
            setTitle("Catalog");
        }
        /*else if (position == 1) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new PublisherFragment(), UiState.PUBLISHER)
                    .commit();
            setTitle("Publisher");
        } else if (position == 2) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new SeriesFragment(), UiState.SERIES)
                    .commit();
            setTitle("Series");
        }
        */
        else if (position == 1) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MyComicsFragment(), UiState.MY_COMICS)
                    .commit();
            setTitle("My Comics");
        } else if (position == 2) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MyCollectionsFragment(), UiState.MY_COLLECTIONS)
                    .commit();
            setTitle("My Collections");
        } else if (position == 3) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new LockerFragment(), UiState.LOCKER)
                    .commit();
            setTitle("Locker");
        } else if (position == 4) {
            String serviceName = BuildConfig.FLAVOR.startsWith("dev") ? Constants.IAB_TEST
                    : Constants.IAB_PROD;
            Intent i = new Intent(serviceName + ".MAIN");
            startActivity(i);

        }
        mCurrentPosition = position;
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            this.unbindService(mServiceConn);
        }
    }


}
