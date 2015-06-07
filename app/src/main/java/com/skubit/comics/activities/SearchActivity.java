package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.R;
import com.skubit.comics.UiState;
import com.skubit.comics.fragments.SearchFragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class SearchActivity extends ActionBarActivity {

    private String mQuery;

    private Toolbar toolbar;

    public static Intent newInstance(String query) {
        Intent i = new Intent("MOST_LIKE_THIS");
        i.putExtra(SearchManager.QUERY, query);
        i.setClassName(BuildConfig.APPLICATION_ID, SearchActivity.class.getName());
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        setContentView(R.layout.activity_catalog);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            setTitle("Search: " + mQuery);
            SearchFragment frag = SearchFragment.newInstance(mQuery, 50);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, UiState.CATALOG)
                    .commit();
        } else if("MOST_LIKE_THIS".equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            setTitle("Similar Comics");
            SearchFragment frag = SearchFragment.newInstance(mQuery, 15);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, UiState.CATALOG)
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.push_out_right);
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
