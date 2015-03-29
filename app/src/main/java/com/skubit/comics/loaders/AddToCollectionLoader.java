package com.skubit.comics.loaders;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

public class AddToCollectionLoader extends BaseCursorLoader {

    public AddToCollectionLoader(Context context,
            AdapterListener itemClickListener) {
        super(context, itemClickListener);
    }

    @Override
    public CursorRecyclerViewAdapter onCreateAdapter(Cursor cursor) {
        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }
}
