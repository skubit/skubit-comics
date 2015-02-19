package com.skubit.comics.provider.archiveroots;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.archiveroots.ArchiveRootsColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;

/**
 * Columns for the {@code archive_roots} table.
 */
public class ArchiveRootsColumns implements BaseColumns {
    public static final String TABLE_NAME = "archive_roots";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ARCHIVE_ROOT = "archive_root";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ARCHIVE_ROOT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == ARCHIVE_ROOT || c.contains("." + ARCHIVE_ROOT)) return true;
        }
        return false;
    }

}
