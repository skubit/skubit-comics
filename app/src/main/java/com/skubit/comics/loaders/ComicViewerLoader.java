package com.skubit.comics.loaders;


import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.adapters.ComicViewerAdapter;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;


public class ComicViewerLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String mArchiveFile;

    private ComicViewerActivity mContext;

    private FragmentManager mFragmentManager;

    private ComicViewerAdapter mAdapter;

    public ComicViewerLoader(ComicViewerActivity activity, FragmentManager fragmentManager,
            String archiveFile) {
        mContext = activity;
        mArchiveFile = archiveFile;
        mFragmentManager = fragmentManager;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, ComicReaderColumns.CONTENT_URI,
                null, ComicReaderColumns.ARCHIVE_FILE + "=?", new String[]{mArchiveFile}, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mContext.resetAdapter(mAdapter);
        } else {
            mAdapter = new ComicViewerAdapter(mContext, mFragmentManager, data);
            mContext.resetAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter = null;
        mContext = null;
        mFragmentManager = null;
    }
}
