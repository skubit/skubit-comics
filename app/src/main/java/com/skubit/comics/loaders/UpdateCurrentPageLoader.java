package com.skubit.comics.loaders;

import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicSelection;

import android.content.Context;

import java.io.IOException;

public class UpdateCurrentPageLoader extends BaseLoader {

    private final int mCurrentPage;

    private final String mArchiveFile;

    public UpdateCurrentPageLoader(Context context, String archiveFile, int currentPage) {
        super(context);
        mCurrentPage = currentPage;
        mArchiveFile = archiveFile;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public Integer loadInBackground() {
        ComicSelection ks = new ComicSelection();
        ks.archiveFile(mArchiveFile);

        ComicContentValues ccv = new ComicContentValues();
        ccv.putLastPageRead(mCurrentPage);
        return ccv.update(mContext.getContentResolver(), ks);
    }
}
