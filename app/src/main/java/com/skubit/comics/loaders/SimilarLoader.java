package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.services.ComicService;
import com.skubit.comics.services.rest.ComicRestService;
import com.skubit.shared.dto.ComicBookListDto;

import android.content.Context;

import java.io.IOException;

public class SimilarLoader extends BaseLoader<ComicBookListDto> {

    public final int mOffset;

    public final int mLimit;

    private final ComicRestService mComicService;

    private final String mQuery;

    public SimilarLoader(Context context, String query, int offset, int limit) {
        super(context);
        String account = AccountSettings.get(context).retrieveBitId();
        mComicService = new ComicService(account, context).getRestService();
        mOffset = offset;
        mLimit = limit;
        mQuery = query;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ComicBookListDto loadInBackground() {
        try {
            return mComicService.search(mQuery, mOffset, mLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new ComicBookListDto();
        }
    }
}

