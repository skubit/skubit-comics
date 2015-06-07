package com.skubit.comics.fragments;

import com.skubit.comics.ComicFilter;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseComicFragment extends Fragment {

    protected ComicFilter mComicFilter;

    protected SwipeRefreshLayout mSwipe;

    protected SeriesFilter mSeriesFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null) {
            mComicFilter = getArguments().getParcelable("com.skubit.comics.COMIC_FILTER");
            mSeriesFilter = getArguments().getParcelable("com.skubit.comics.SERIES_FILTER");
        }

        if(mComicFilter == null) {
            mComicFilter = new ComicFilter();
        }

        if(mSeriesFilter == null) {
            mSeriesFilter = new SeriesFilter();
        }
        onPostCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getViewResource(), container, false);
        mSwipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mSwipe.post(new Runnable() {
            @Override public void run() {
                mSwipe.setRefreshing(true);
            }
        });

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(getLoaderId(), null, getLoaderCallbacks());
            }
        });

        getLoaderCallbacks().setSwipe(mSwipe);

        getLoaderManager().restartLoader(getLoaderId(), null, getLoaderCallbacks());

        return v;
    }

    public abstract BaseComicServiceLoaderCallbacks getLoaderCallbacks();

    public abstract int getViewResource();

    public abstract int getLoaderId();

    public abstract void onPostCreate(Bundle savedInstanceState);
}
