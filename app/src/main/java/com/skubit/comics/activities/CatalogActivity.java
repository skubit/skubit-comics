package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicFilter;
import com.skubit.comics.R;
import com.skubit.comics.UiState;
import com.skubit.comics.fragments.CatalogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class CatalogActivity extends ActionBarActivity {

    private Toolbar toolbar;

    public static Intent newInstance(String series) {
        Intent i = new Intent();
        i.putExtra("SERIES", series);
        i.setClassName(BuildConfig.APPLICATION_ID, CatalogActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        String series = getIntent().getStringExtra("SERIES");
        setTitle("Series: " + series);

        ComicFilter filter = new ComicFilter();
        filter.series = series;

        CatalogFragment frag = CatalogFragment.newInstance(filter);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, frag, UiState.CATALOG)
                .commit();
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
