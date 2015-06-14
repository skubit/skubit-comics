package com.skubit.comics.provider.collectionmapping;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

/**
 * Columns for the {@code collection_mapping} table.
 */
public class CollectionMappingColumns implements BaseColumns {
    public static final String TABLE_NAME = "collection_mapping";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CID = "cid";

    public static final String CBID = "cbid";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CID,
            CBID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CID) || c.contains("." + CID)) return true;
            if (c.equals(CBID) || c.contains("." + CBID)) return true;
        }
        return false;
    }

}
