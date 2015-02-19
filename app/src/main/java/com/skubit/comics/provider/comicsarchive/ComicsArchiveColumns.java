package com.skubit.comics.provider.comicsarchive;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.archiveroots.ArchiveRootsColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;

/**
 * Columns for the {@code comics_archive} table.
 */
public class ComicsArchiveColumns implements BaseColumns {
    public static final String TABLE_NAME = "comics_archive";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ARCHIVE_FILE = "archive_file";

    public static final String COVER_ART = "cover_art";

    public static final String STORY_TITLE = "story_title";

    public static final String UNARCHIVED_PAGES = "unarchived_pages";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ARCHIVE_FILE,
            COVER_ART,
            STORY_TITLE,
            UNARCHIVED_PAGES
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == ARCHIVE_FILE || c.contains("." + ARCHIVE_FILE)) return true;
            if (c == COVER_ART || c.contains("." + COVER_ART)) return true;
            if (c == STORY_TITLE || c.contains("." + STORY_TITLE)) return true;
            if (c == UNARCHIVED_PAGES || c.contains("." + UNARCHIVED_PAGES)) return true;
        }
        return false;
    }

}
