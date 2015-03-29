package com.skubit.comics.loaders;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyCollectionsAdapter;
import com.skubit.comics.provider.collection.CollectionColumns;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

public class MyCollectionsLoader extends BaseCursorLoader {

    public MyCollectionsLoader(Context context, AdapterListener itemClickListener) {
        super(context, itemClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, CollectionColumns.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public CursorRecyclerViewAdapter onCreateAdapter(Cursor cursor) {
        return new MyCollectionsAdapter(mContext, cursor, mItemClickListener);
    }

}


