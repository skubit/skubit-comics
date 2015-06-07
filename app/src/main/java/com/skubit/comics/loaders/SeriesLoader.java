package com.skubit.comics.loaders;

import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.SeriesFilter;
import com.skubit.shared.dto.SeriesDto;
import com.skubit.shared.dto.SeriesListDto;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

public class SeriesLoader extends BaseComicServiceLoader<SeriesListDto> {

    public SeriesLoader(Context context, String webCursor,
            SeriesFilter seriesFilter) {
        super(context, webCursor, seriesFilter, null);
    }

    @Override
    public SeriesListDto loadInBackground() {
        try {
            if (mSeriesFilter.hasCreator()) {
                return mComicService.getByCreator(mSeriesFilter.creator, 20, mWebCursor, "");
            } else if (mSeriesFilter.hasGenre()) {
                return mComicService.getByGenre(mSeriesFilter.genre, 20, mWebCursor, "");
            } else if (mSeriesFilter.hasPublisher()) {
                return mComicService.getByPublisher(mSeriesFilter.publisher, 20, mWebCursor, "");
            } else {
                return mComicService.getSeries(mSeriesFilter.alphaBucket, 0, 20, mWebCursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SeriesListDto();
        }
    }

    public static class Callbacks extends BaseComicServiceLoaderCallbacks<SeriesListDto,
            SeriesDto> {

        private final SeriesFilter mSeriesFilter;

        private Context mContext;

        public Callbacks(Context context, ICatalogAdapter<SeriesDto> adapter,
                SeriesFilter comicFilter) {
            super(adapter);
            this.mContext = context;
            this.mSeriesFilter = comicFilter;
        }


        @Override
        public Loader<SeriesListDto> onCreateLoader(int id, Bundle args) {
            return new SeriesLoader(mContext, mWebCursor, mSeriesFilter);
        }

    }
}
