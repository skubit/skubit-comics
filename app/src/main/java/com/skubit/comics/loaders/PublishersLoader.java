package com.skubit.comics.loaders;

import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.SeriesFilter;
import com.skubit.shared.dto.PublisherDto;
import com.skubit.shared.dto.PublisherListDto;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

public class PublishersLoader extends BaseComicServiceLoader<PublisherListDto> {

    public PublishersLoader(Context context, String webCursor, SeriesFilter seriesFilter) {
        super(context, webCursor, seriesFilter, null);
    }

    @Override
    public PublisherListDto loadInBackground() {
        try {
            return mComicService.getPublishers(mSeriesFilter.alphaBucket, 0, 50, mWebCursor);
        } catch (Exception e) {
            e.printStackTrace();
            return new PublisherListDto();
        }
    }

    public static class Callbacks extends BaseComicServiceLoaderCallbacks<PublisherListDto,
            PublisherDto> {

        private final SeriesFilter mSeriesFilter;

        private Context mContext;

        public Callbacks(Context context, ICatalogAdapter<PublisherDto> adapter,
                SeriesFilter comicFilter) {
            super(adapter);
            this.mContext = context;
            this.mSeriesFilter = comicFilter;
        }

        @Override
        public Loader<PublisherListDto> onCreateLoader(int id, Bundle args) {
            return new PublishersLoader(mContext, mWebCursor, mSeriesFilter);
        }

    }
}
