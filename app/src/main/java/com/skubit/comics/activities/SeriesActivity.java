package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.UiState;
import com.skubit.comics.fragments.SeriesFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class SeriesActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private SeriesFilter mSeriesFilter;

    public static Intent newInstance(SeriesFilter filter) {
        Intent i = new Intent();
        i.putExtra("com.skubit.comics.SERIES_FILTER", filter);
        i.setClassName(BuildConfig.APPLICATION_ID, SeriesActivity.class.getName());
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.push_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        if (getIntent().getExtras() != null) {
            mSeriesFilter = getIntent().getParcelableExtra("com.skubit.comics.SERIES_FILTER");

            if (mSeriesFilter.hasCreator()) {
                setTitle(mSeriesFilter.creator);
            } else if (mSeriesFilter.hasGenre()) {
                setTitle(mSeriesFilter.genre);
            } else if (mSeriesFilter.hasPublisher()) {
                setTitle(mSeriesFilter.publisher);
            } else if (mSeriesFilter.hasSeries()) {
                setTitle(mSeriesFilter.series);
            } else {
                setTitle("Unknown");
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, SeriesFragment.newInstance(mSeriesFilter),
                            UiState.SERIES)
                    .commit();
        } else {
            Toast.makeText(this, "SeriesActivity Intent is null", Toast.LENGTH_SHORT).show();
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

}
