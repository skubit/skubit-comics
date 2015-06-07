package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.ComicFilter;
import com.skubit.comics.SeriesFilter;
import com.skubit.comics.services.ComicService;
import com.skubit.comics.services.rest.ComicRestService;

import android.content.Context;

import java.io.IOException;

public abstract class BaseComicServiceLoader<T> extends BaseLoader<T> {

    protected final ComicRestService mComicService;

    protected final String mWebCursor;

    protected final ComicFilter mComicFilter;

    protected final SeriesFilter mSeriesFilter;

    public BaseComicServiceLoader(Context context, String webCursor, SeriesFilter seriesFilter,
            ComicFilter comicFilter) {
        super(context);
        String account = AccountSettings.get(context).retrieveBitId();
        mComicService = new ComicService(account, context).getRestService();
        mWebCursor = webCursor;
        this.mComicFilter = comicFilter != null ? comicFilter : new ComicFilter();
        this.mSeriesFilter = seriesFilter != null ? seriesFilter : new SeriesFilter();
    }

    @Override
    protected void closeStream() throws IOException {

    }

}
