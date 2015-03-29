package com.skubit.comics.loaders;


import com.skubit.comics.AdapterListener;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;

public abstract class BaseCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    protected final AdapterListener mItemClickListener;

    protected final Context mContext;

    protected CursorRecyclerViewAdapter mAdapter;

    public BaseCursorLoader(Context context, AdapterListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    public abstract CursorRecyclerViewAdapter onCreateAdapter(Cursor cursor);

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mAdapter != null) {
            mAdapter.swapCursor(data);
        } else {
            mAdapter = onCreateAdapter(data);
            mItemClickListener.resetAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
