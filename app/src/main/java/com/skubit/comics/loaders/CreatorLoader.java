package com.skubit.comics.loaders;

import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.SeriesFilter;
import com.skubit.shared.dto.CreatorDto;
import com.skubit.shared.dto.CreatorListDto;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

public class CreatorLoader extends BaseComicServiceLoader<CreatorListDto> {

    public CreatorLoader(Context context, String webCursor,
            SeriesFilter seriesFilter) {
        super(context, webCursor, seriesFilter, null);
    }

    @Override
    public CreatorListDto loadInBackground() {
        try {
            return mComicService.getCreators(mSeriesFilter.alphaBucket, 0, 50, mWebCursor);
        } catch (Exception e) {
            e.printStackTrace();
            return new CreatorListDto();
        }
    }

    public static class Callbacks extends BaseComicServiceLoaderCallbacks<CreatorListDto,
            CreatorDto> {

        private final SeriesFilter mSeriesFilter;

        private Context mContext;

        public Callbacks(Context context, ICatalogAdapter<CreatorDto> adapter,
               SeriesFilter comicFilter) {
            super(adapter);
            this.mContext = context;
            this.mSeriesFilter = comicFilter;
        }

        @Override
        public Loader<CreatorListDto> onCreateLoader(int id, Bundle args) {
            return new CreatorLoader(mContext, mWebCursor, mSeriesFilter);
        }

    }
}
