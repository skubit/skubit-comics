package com.skubit.comics.provider.collection;

import com.skubit.comics.provider.ComicsProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Columns for the {@code collection} table.
 */
public class CollectionColumns implements BaseColumns {
    public static final String TABLE_NAME = "collection";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CID = "cid";

    public static final String NAME = "name";

    public static final String TAGS = "tags";

    public static final String COVERART = "coverArt";

    public static final String TYPE = "type";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CID,
            NAME,
            TAGS,
            COVERART,
            TYPE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CID) || c.contains("." + CID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(TAGS) || c.contains("." + TAGS)) return true;
            if (c.equals(COVERART) || c.contains("." + COVERART)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
        }
        return false;
    }

}
