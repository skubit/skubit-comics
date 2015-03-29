package com.skubit.comics.loaders;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyComicsAdapter;
import com.skubit.comics.provider.comic.ComicColumns;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

public class MyComicsLoader extends BaseCursorLoader {

    public MyComicsLoader(Context context, AdapterListener itemClickListener) {
        super(context, itemClickListener);
    }

    @Override
    public CursorRecyclerViewAdapter onCreateAdapter(Cursor cursor) {
        return new MyComicsAdapter(mContext, cursor, mItemClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String selection = args.getString("selection");
            String[] params = args.getStringArray("params");
            return new CursorLoader(mContext, ComicColumns.CONTENT_URI,
                    null, selection, params, null);
        } else {
            return new CursorLoader(mContext, ComicColumns.CONTENT_URI,
                    null, null, null, null);
        }

    }
}
