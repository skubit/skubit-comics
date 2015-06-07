package com.skubit.comics.loaders;

import com.skubit.comics.ICatalogAdapter;
import com.skubit.shared.dto.Dto;
import com.skubit.shared.dto.DtoList;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;

public abstract class BaseComicServiceLoaderCallbacks<T extends DtoList, U extends Dto>
        implements LoaderManager.LoaderCallbacks<T> {

    public String mWebCursor;

    protected SwipeRefreshLayout mSwipe;

    private ICatalogAdapter<U> adapter;

    public BaseComicServiceLoaderCallbacks(ICatalogAdapter<U> adapter) {
        this.adapter = adapter;
    }

    public void setSwipe(SwipeRefreshLayout swipe) {
        this.mSwipe = swipe;
    }

    @Override
    public void onLoadFinished(Loader<T> loader, T data) {
        if (data != null && data.getItems() != null && data.getItems().size() > 0) {
            mWebCursor = data.getNextWebCursor();
            if(adapter != null) {
                adapter.add(data.getItems());
                adapter.notifyDataSetChanged();
            }
        }
        if(mSwipe != null) {
            mSwipe.post(new Runnable() {
                @Override
                public void run() {
                    mSwipe.setRefreshing(false);
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        if(mSwipe != null) {
            mSwipe.post(new Runnable() {
                @Override
                public void run() {
                    mSwipe.setRefreshing(false);
                }
            });
        }
    }
}
