package com.skubit.comics.loaders;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicFilter;
import com.skubit.comics.ICatalogAdapter;
import com.skubit.shared.dto.CatalogFilter;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookListDto;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;

public class CatalogLoader extends BaseComicServiceLoader<ComicBookListDto> {

    public CatalogLoader(Context context, String webCursor, ComicFilter comicFilter) {
        super(context, webCursor, null, comicFilter);
    }

    @Override
    public ComicBookListDto loadInBackground() {
        try {
            if (mComicFilter.isFeatured) {
                return mComicService.getByFilter(CatalogFilter.FEATURED, 20, mWebCursor);
            } else if (mComicFilter.isFree) {
                return mComicService.getByFilter(CatalogFilter.TOP_FREE, 20, mWebCursor);
            } else if (mComicFilter.isPaid) {
                return mComicService.getByFilter(CatalogFilter.TOP_PAID, 20, mWebCursor);
            } else if (!TextUtils.isEmpty(mComicFilter.series)) {
                return mComicService.getBySeries(mComicFilter.series, 20, mWebCursor,
                        BuildConfig.APPLICATION_ID);
            }
            //TODO: JUST released
            return mComicService.getAllComics(50, mWebCursor,
                    true, BuildConfig.APPLICATION_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return new ComicBookListDto();
        }
    }

    public static class Callbacks extends BaseComicServiceLoaderCallbacks<ComicBookListDto,
            ComicBookDto> {

        private final ComicFilter mComicFilter;

        private Context mContext;

        public Callbacks(Context context, ICatalogAdapter<ComicBookDto> adapter,
                ComicFilter comicFilter) {
            super(adapter);
            this.mContext = context;
            this.mComicFilter = comicFilter;
        }

        @Override
        public Loader<ComicBookListDto> onCreateLoader(int id, Bundle args) {
            return new CatalogLoader(mContext, mWebCursor, mComicFilter);
        }

    }
}
