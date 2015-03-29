package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.services.ComicService;
import com.skubit.shared.dto.ComicBookListDto;

import android.content.Context;

import java.io.IOException;

public class CatalogLoader extends BaseLoader<ComicBookListDto> {

    private final ComicService mComicService;

    private final String mWebCursor;

    public CatalogLoader(Context context, String webCursor) {
        super(context);
        String account = AccountSettings.get(context).retrieveBitId();
        mComicService = new ComicService(account, context);
        mWebCursor = webCursor;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ComicBookListDto loadInBackground() {
        try {
            return mComicService.getRestService().getAllComics(5, mWebCursor, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ComicBookListDto();
        }
    }
}
