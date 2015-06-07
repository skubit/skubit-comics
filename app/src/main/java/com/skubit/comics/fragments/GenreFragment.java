package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.activities.SeriesActivity;
import com.skubit.comics.adapters.GenreAdapter;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;
import com.skubit.comics.loaders.GenreLoader;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.shared.dto.GenreDto;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GenreFragment extends BaseComicFragment implements ClickComicListener  {

    private  GenreAdapter mAdapter;

    private GenreLoader.Callbacks mLoader;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView comicGridView = (RecyclerView) view.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(mAdapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(20));
        comicGridView.setLayoutManager(new LinearLayoutManager(getActivity()));

/*
        comicGridView
                .setOnScrollListener(new RecyclerScrollListener(comicGridView.getLayoutManager()) {
                    @Override
                    public void onLoadMore() {
                        if (!TextUtils.isEmpty(mLoader.mWebCursor)) {
                            getLoaderManager()
                                    .restartLoader(LoaderId.GENRE_LOADER, null, mLoader);
                        }
                    }
                });
                */

    }

    @Override
    public BaseComicServiceLoaderCallbacks getLoaderCallbacks() {
        return mLoader;
    }

    @Override
    public int getViewResource() {
        return R.layout.fragment_genres;
    }

    @Override
    public int getLoaderId() {
        return LoaderId.GENRE_LOADER;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mAdapter = new GenreAdapter(getActivity(), this);
        mLoader = new GenreLoader.Callbacks(getActivity().getBaseContext(),
                mAdapter, mSeriesFilter);
    }

    @Override
    public void onClick(View v, int position) {
        GenreDto dto = mAdapter.get(position);
        if (dto != null) {
            SeriesFilter filter = new SeriesFilter();
            filter.genre = dto.getGenreName();
            startActivity(SeriesActivity.newInstance(filter));
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        }
    }

    @Override
    public void onClickOption(View v) {

    }
}
