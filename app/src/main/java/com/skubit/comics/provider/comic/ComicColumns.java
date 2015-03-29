package com.skubit.comics.provider.comic;

import com.skubit.comics.provider.ComicsProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Columns for the {@code comic} table.
 */
public class ComicColumns implements BaseColumns {

    public static final String TABLE_NAME = "comic";

    public static final Uri CONTENT_URI = Uri
            .parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;

    public static final String CBID = "cbid";

    public static final String STORY_TITLE = "story_title";

    public static final String COVER_ART = "cover_art";

    public static final String PUBLISHER = "publisher";

    public static final String SERIES = "series";

    public static final String ISSUE = "issue";

    public static final String ARCHIVE_FILE = "archive_file";

    public static final String IMAGE_DIRECTORY = "image_directory";

    public static final String IS_DELETED = "is_deleted";

    public static final String LAST_PAGE_READ = "last_page_read";

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            CBID,
            STORY_TITLE,
            COVER_ART,
            PUBLISHER,
            SERIES,
            ISSUE,
            ARCHIVE_FILE,
            IMAGE_DIRECTORY,
            IS_DELETED,
            LAST_PAGE_READ
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) {
            return true;
        }
        for (String c : projection) {
            if (c == CBID || c.contains("." + CBID)) {
                return true;
            }
            if (c == STORY_TITLE || c.contains("." + STORY_TITLE)) {
                return true;
            }
            if (c == COVER_ART || c.contains("." + COVER_ART)) {
                return true;
            }
            if (c == PUBLISHER || c.contains("." + PUBLISHER)) {
                return true;
            }
            if (c == SERIES || c.contains("." + SERIES)) {
                return true;
            }
            if (c == ISSUE || c.contains("." + ISSUE)) {
                return true;
            }
            if (c == ARCHIVE_FILE || c.contains("." + ARCHIVE_FILE)) {
                return true;
            }
            if (c == IMAGE_DIRECTORY || c.contains("." + IMAGE_DIRECTORY)) {
                return true;
            }
            if (c == IS_DELETED || c.contains("." + IS_DELETED)) {
                return true;
            }
            if (c == LAST_PAGE_READ || c.contains("." + LAST_PAGE_READ)) {
                return true;
            }
        }
        return false;
    }

}
