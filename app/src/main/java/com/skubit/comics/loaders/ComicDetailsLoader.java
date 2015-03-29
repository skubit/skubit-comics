package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.services.ComicService;
import com.skubit.shared.dto.ComicBookDto;

import android.content.Context;

import java.io.IOException;

public class ComicDetailsLoader extends BaseLoader<ComicBookDto> {

    private final String mCbid;

    private final ComicService mComicService;

    public ComicDetailsLoader(Context context, String cbid) {
        super(context);
        mCbid = cbid;
        String account = AccountSettings.get(context).retrieveBitId();
        mComicService = new ComicService(account, context);
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ComicBookDto loadInBackground() {
        try {
            return mComicService.getRestService().getComicBook(mCbid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
