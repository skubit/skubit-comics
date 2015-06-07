package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.LinearRecyclerScrollListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.activities.SeriesActivity;
import com.skubit.comics.adapters.CreatorAdapter;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;
import com.skubit.comics.loaders.CreatorLoader;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.shared.dto.CreatorDto;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class CreatorFragment extends BaseComicFragment implements ClickComicListener {

    private CreatorAdapter mAdapter;

    private CreatorLoader.Callbacks mLoader;

    public static CreatorFragment newInstance(SeriesFilter filter) {
        CreatorFragment fragment = new CreatorFragment();
        fragment.setArguments(SeriesFilter.toBundle(filter));
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView comicGridView = (RecyclerView) view.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(mAdapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(20));
        comicGridView.setLayoutManager(layoutManager);

        comicGridView
                .setOnScrollListener(new LinearRecyclerScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore() {
                        if (!TextUtils.isEmpty(mLoader.mWebCursor)) {
                            getLoaderManager()
                                    .restartLoader(LoaderId.CREATOR_LOADER, null, mLoader);
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
        return R.layout.fragment_creator;
    }

    @Override
    public int getLoaderId() {
        return LoaderId.CREATOR_LOADER;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mAdapter = new CreatorAdapter(getActivity(), this);
        mLoader = new CreatorLoader.Callbacks(getActivity().getBaseContext(),
                mAdapter, mSeriesFilter);
    }

    @Override
    public void onClick(View v, int position) {
        CreatorDto dto = mAdapter.get(position);
        if (dto != null) {
            SeriesFilter filter = new SeriesFilter();
            filter.creator = dto.getFullName();
            startActivity(SeriesActivity.newInstance(filter));
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickOption(View v) {

    }

}
