package com.skubit.comics.loaders;

import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.SeriesFilter;
import com.skubit.shared.dto.GenreDto;
import com.skubit.shared.dto.GenreListDto;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import java.util.ArrayList;


public class GenreLoader extends BaseComicServiceLoader<GenreListDto> {

    public GenreLoader(Context context, String webCursor,
            SeriesFilter seriesFilter) {
        super(context, webCursor, seriesFilter, null);
    }

    @Override
    public GenreListDto loadInBackground() {
        try {
            return mComicService.getGenres();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GenreListDto genres = new GenreListDto();
        genres.setItems(new ArrayList<GenreDto>());
        return genres;
    }

    public static class Callbacks extends BaseComicServiceLoaderCallbacks<GenreListDto,
            GenreDto> {

        private final SeriesFilter mSeriesFilter;

        private Context mContext;

        public Callbacks(Context context, ICatalogAdapter<GenreDto> adapter,
                SeriesFilter seriesFilter) {
            super(adapter);
            this.mContext = context;
            this.mSeriesFilter = seriesFilter;
        }


        @Override
        public Loader<GenreListDto> onCreateLoader(int id, Bundle args) {
            return new GenreLoader(mContext, mWebCursor, mSeriesFilter);
        }

    }
}
