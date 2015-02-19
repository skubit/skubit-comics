package com.skubit.comics.provider.comicsinfo;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.archiveroots.ArchiveRootsColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;

/**
 * Columns for the {@code comics_info} table.
 */
public class ComicsInfoColumns implements BaseColumns {
    public static final String TABLE_NAME = "comics_info";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ARCHIVE_FILE = "archive_file";

    public static final String ARCHIVE_FILE_ENTRY = "archive_file_entry";

    public static final String PAGE = "page";

    public static final String PAGE_IMAGE = "page_image";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ARCHIVE_FILE,
            ARCHIVE_FILE_ENTRY,
            PAGE,
            PAGE_IMAGE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == ARCHIVE_FILE || c.contains("." + ARCHIVE_FILE)) return true;
            if (c == ARCHIVE_FILE_ENTRY || c.contains("." + ARCHIVE_FILE_ENTRY)) return true;
            if (c == PAGE || c.contains("." + PAGE)) return true;
            if (c == PAGE_IMAGE || c.contains("." + PAGE_IMAGE)) return true;
        }
        return false;
    }

}
