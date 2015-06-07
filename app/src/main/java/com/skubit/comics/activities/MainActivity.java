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
import com.skubit.comics.fragments.CatalogTabsFragment;
import com.skubit.comics.fragments.CreatorsTabsFragment;
import com.skubit.comics.fragments.GenreFragment;
import com.skubit.comics.fragments.LockerFragment;
import com.skubit.comics.fragments.MyCollectionsFragment;
import com.skubit.comics.fragments.MyComicsFragment;
import com.skubit.comics.fragments.PublishersTabsFragment;
import com.skubit.comics.fragments.SeriesTabsFragment;
import com.skubit.navigation.NavigationDrawerCallbacks;
import com.skubit.navigation.NavigationDrawerFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks,
        ControllerCallback {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private IBillingService mService;

    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IBillingService.Stub.asInterface(service);
            if (doLogin) {
                doLogin = false;
                Utils.startAuthorization(MainActivity.this, mService);
            }
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

        bindService(Utils.getBillingServiceIntent(), mServiceConn, Context.BIND_AUTO_CREATE);

        if("com.skubit.comics.MY_COMICS".equals(getIntent().getAction())) {
            mNavigationDrawerFragment.selectItem(5);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSubmitButtonEnabled(true);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setQueryHint("Search Comics");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int order = item.getOrder();
        if (order == 1) {
            if (mService != null) {
                Utils.startAuthorization(this, mService);
            } else {
                if (!Utils.isIabInstalled(getPackageManager())) {
                    startActivityForResult(Utils.getIabIntent(), Utils.PLAY_CODE);
                }
            }
        } else if (order == 4) {
            Intent i = new Intent();
            i.setClass(this, DisplayLicensesActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean doLogin;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.AUTHORIZATION_CODE && data != null && !TextUtils
                .isEmpty(data.getStringExtra("response"))) {
            Utils.createNewAccount(this, data);
        } else if (requestCode == Utils.PLAY_CODE) {
            doLogin = true;
            bindService(Utils.getBillingServiceIntent(), mServiceConn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void showComicDetails(ComicData comicData) {
        startActivity(ComicDetailsActivity.newInstance(comicData));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == 0) {
            showFragment(new CatalogTabsFragment(), UiState.CATALOG + "_TABS", position);
            setTitle("Catalog");
        } else if (position == 1) {
            showFragment(new PublishersTabsFragment(), UiState.PUBLISHER, position);
            setTitle("Publisher");
        } else if (position == 2) {
            showFragment(new SeriesTabsFragment(), UiState.SERIES, position);
            setTitle("Series");
        } else if (position == 3) {
            showFragment(new GenreFragment(), UiState.GENRE, position);
            setTitle("Genre");
        } else if (position == 4) {
            showFragment(new CreatorsTabsFragment(), UiState.CREATOR, position);
            setTitle("Creator");
        } else if (position == 5) {
            showFragment(new MyComicsFragment(), UiState.MY_COMICS, position);
            setTitle("My Comics");
        } else if (position == 6) {
            showFragment(new MyCollectionsFragment(), UiState.MY_COLLECTIONS, position);
            setTitle("My Collections");
        } else if (position == 7) {
            showFragment(new LockerFragment(), UiState.LOCKER, position);
            setTitle("Locker");
        } else if (position == 8) {
            String serviceName = BuildConfig.FLAVOR.startsWith("dev") ? Constants.IAB_TEST
                    : Constants.IAB_PROD;
            Intent i = new Intent(serviceName + ".MAIN");
            startActivity(i);

        }
        mCurrentPosition = position;
    }

    private Fragment[] fragments = new Fragment[8];

    private static String[] fragmentTags = new String[]{UiState.CATALOG + "_TABS",
            UiState.PUBLISHER, UiState.SERIES, UiState.GENRE, UiState.CREATOR,
            UiState.MY_COMICS, UiState.MY_COLLECTIONS, UiState.LOCKER};

    private void addFragmentToCache(Fragment targetFragment, String tag, int position) {
        Fragment oldFragment = getFragmentManager().findFragmentByTag(tag);
        fragments[position] = (oldFragment == null) ? targetFragment : oldFragment;
    }

    private boolean isFragmentInCache(String tag) {
        return getFragmentManager().findFragmentByTag(tag) != null;
    }

    private void showFragment(Fragment targetFragment, String tag, int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction().setTransition(
                FragmentTransaction.TRANSIT_NONE);
        hidesFragments(position, transaction);

        Fragment f = fragments[position];

        if(f == null) {
            System.out.println("foo - f is null: " + position + ", " + tag);
            addFragmentToCache(targetFragment, tag, position);
            if(isFragmentInCache(tag)) {
                System.out.println("foo - show in cache: " + position + ", " + tag);
                transaction.show(fragments[position]);
            } else {
                System.out.println("foo - add: " + position + ", " + tag);
                transaction.add(R.id.container, fragments[position], tag);
            }
        } else {
            System.out.println("foo - frag - show: " + position + ", " + tag);
            transaction.show(fragments[position]);
        }

        transaction.commitAllowingStateLoss();
    }

    private void hidesFragments(int visPosition, FragmentTransaction transaction) {
        for(int i = 0; i < fragments.length; i++) {
            if(i != visPosition) {
                if(fragments[i] != null) {
                    System.out.println("foo - found frag1 - hide" + i + ", " + fragments[i].getTag());
                    transaction.hide(fragments[i]);
                } else {
                    Fragment fragment = getFragmentManager().findFragmentByTag(fragmentTags[i]);
                    if(fragment != null) {
                        System.out.println("foo - found frag2 - hide" + i);
                        transaction.hide(fragment);
                    }
                }
            }
        }
    }

    private void showFragment(Fragment newFragment, String tag) {
        Fragment oldFragment = getFragmentManager().findFragmentByTag(tag);

        if (oldFragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, oldFragment, tag)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .replace(R.id.container, newFragment, tag).commitAllowingStateLoss();
        }
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
