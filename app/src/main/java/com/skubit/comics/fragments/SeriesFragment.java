package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.ComicGridView;
import com.skubit.comics.LinearRecyclerScrollListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.activities.CatalogActivity;
import com.skubit.comics.adapters.SeriesAdapter;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;
import com.skubit.comics.loaders.SeriesLoader;
import com.skubit.shared.dto.SeriesDto;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeriesFragment extends BaseComicFragment implements
        ClickComicListener {

    private SeriesAdapter mAdapter;

    private SeriesLoader.Callbacks mLoader;

    public static SeriesFragment newInstance(SeriesFilter filter) {
        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(SeriesFilter.toBundle(filter));
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ComicGridView comicGridView = (ComicGridView) view.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(mAdapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(20));

        comicGridView
                .setOnScrollListener(
                        new LinearRecyclerScrollListener(comicGridView.getLayoutManager()) {
                            @Override
                            public void onLoadMore() {
                                if (!TextUtils.isEmpty(mLoader.mWebCursor)) {
                                    getLoaderManager()
                                            .initLoader(mLoader.mWebCursor.hashCode(), null,
                                                    mLoader);
                                }
                            }
                        });
    }


    @Override
    public BaseComicServiceLoaderCallbacks getLoaderCallbacks() {
        return mLoader;
    }

    @Override
    public int getViewResource() {
        return R.layout.fragment_series;
    }

    @Override
    public int getLoaderId() {
        return mComicFilter.hashCode();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mAdapter = new SeriesAdapter(getActivity(), this);
        mLoader = new SeriesLoader.Callbacks(getActivity().getBaseContext(),
                mAdapter, mSeriesFilter);
    }

    /**
     * This is a work-around. The Series Fragment will add items on rotation so let's just
     * restart loader for now until further investigation.
     */
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

    @Override
    public void onClick(View v, int position) {
        SeriesDto dto = mAdapter.get(position);
        if (dto != null) {
            startActivity(CatalogActivity.newInstance(dto.getSeriesName()));
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        }
    }

    @Override
    public void onClickOption(View v) {

    }
}
