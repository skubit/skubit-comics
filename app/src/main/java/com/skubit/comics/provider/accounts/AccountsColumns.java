package com.skubit.comics.provider.accounts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

/**
 * Columns for the {@code accounts} table.
 */
public class AccountsColumns implements BaseColumns {
    public static final String TABLE_NAME = "accounts";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String BITID = "bitId";

    public static final String TOKEN = "token";

    public static final String DATE = "date";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            BITID,
            TOKEN,
            DATE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(BITID) || c.contains("." + BITID)) return true;
            if (c.equals(TOKEN) || c.contains("." + TOKEN)) return true;
            if (c.equals(DATE) || c.contains("." + DATE)) return true;
        }
        return false;
    }

}
