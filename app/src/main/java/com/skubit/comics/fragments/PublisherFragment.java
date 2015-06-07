package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.LinearRecyclerScrollListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.activities.SeriesActivity;
import com.skubit.comics.adapters.PublisherAdapter;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.comics.loaders.PublishersLoader;
import com.skubit.shared.dto.PublisherDto;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

public class PublisherFragment extends BaseComicFragment  implements
        ClickComicListener {

    private static final String TAG = "PublisherFragment";

    private PublisherAdapter mAdapter;

    private PublishersLoader.Callbacks mLoader;

    public PublisherFragment() {
    }

    public static PublisherFragment newInstance(SeriesFilter filter) {
        PublisherFragment fragment = new PublisherFragment();
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
                                    .initLoader(mLoader.mWebCursor.hashCode(), null, mLoader);
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
        return R.layout.fragment_publishers;
    }

    @Override
    public int getLoaderId() {
        return LoaderId.PUBLISHER_LOADER;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mAdapter = new PublisherAdapter(getActivity(), this);
        mLoader = new PublishersLoader.Callbacks(getActivity().getBaseContext(),
                mAdapter, mSeriesFilter);
    }

    @Override
    public void onClick(View v, int position) {
        PublisherDto dto = mAdapter.get(position);
        if (dto != null) {
            SeriesFilter filter = new SeriesFilter();
            filter.publisher = dto.getPublisherName();
            getActivity().startActivity(SeriesActivity.newInstance(filter));
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        }
    }

    @Override
    public void onClickOption(View v) {

    }
}
