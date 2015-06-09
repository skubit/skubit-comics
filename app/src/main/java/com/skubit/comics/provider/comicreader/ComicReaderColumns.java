package com.skubit.comics.provider.comicreader;

import com.skubit.comics.provider.ComicsProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Columns for the {@code comic_reader} table.
 */
public class ComicReaderColumns implements BaseColumns {
    public static final String TABLE_NAME = "comic_reader";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CBID = "cbid";

    public static final String ARCHIVE_FILE = "archive_file";

    public static final String PAGE = "page";

    public static final String PAGE_IMAGE = "page_image";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CBID,
            ARCHIVE_FILE,
            PAGE,
            PAGE_IMAGE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CBID) || c.contains("." + CBID)) return true;
            if (c.equals(ARCHIVE_FILE) || c.contains("." + ARCHIVE_FILE)) return true;
            if (c.equals(PAGE) || c.contains("." + PAGE)) return true;
            if (c.equals(PAGE_IMAGE) || c.contains("." + PAGE_IMAGE)) return true;
        }
        return false;
    }

}
