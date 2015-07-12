package com.skubit.comics.provider.comic;

import android.net.Uri;
import android.provider.BaseColumns;

import com.skubit.comics.provider.ComicsProvider;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

/**
 * Columns for the {@code comic} table.
 */
public class ComicColumns implements BaseColumns {
    public static final String TABLE_NAME = "comic";
    public static final Uri CONTENT_URI = Uri.parse(ComicsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CBID = "cbid";

    public static final String STORY_TITLE = "story_title";

    public static final String ARCHIVE_FORMAT = "archive_format";

    public static final String IS_SAMPLE = "is_sample";

    public static final String DOWNLOAD_DATE = "download_date";

    public static final String AGE_RATING = "age_rating";

    public static final String GENRE = "genre";

    public static final String LANGUAGE = "language";

    public static final String ACCESS_DATE = "access_date";

    public static final String COVER_ART = "cover_art";

    public static final String PUBLISHER = "publisher";

    public static final String VOLUME = "volume";

    public static final String SERIES = "series";

    public static final String ISSUE = "issue";

    public static final String ARCHIVE_FILE = "archive_file";

    public static final String IMAGE_DIRECTORY = "image_directory";

    public static final String IS_DELETED = "is_deleted";

    public static final String LAST_PAGE_READ = "last_page_read";

    public static final String IS_FAVORITE = "is_favorite";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CBID,
            STORY_TITLE,
            ARCHIVE_FORMAT,
            IS_SAMPLE,
            DOWNLOAD_DATE,
            AGE_RATING,
            GENRE,
            LANGUAGE,
            ACCESS_DATE,
            COVER_ART,
            PUBLISHER,
            VOLUME,
            SERIES,
            ISSUE,
            ARCHIVE_FILE,
            IMAGE_DIRECTORY,
            IS_DELETED,
            LAST_PAGE_READ,
            IS_FAVORITE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CBID) || c.contains("." + CBID)) return true;
            if (c.equals(STORY_TITLE) || c.contains("." + STORY_TITLE)) return true;
            if (c.equals(ARCHIVE_FORMAT) || c.contains("." + ARCHIVE_FORMAT)) return true;
            if (c.equals(IS_SAMPLE) || c.contains("." + IS_SAMPLE)) return true;
            if (c.equals(DOWNLOAD_DATE) || c.contains("." + DOWNLOAD_DATE)) return true;
            if (c.equals(AGE_RATING) || c.contains("." + AGE_RATING)) return true;
            if (c.equals(GENRE) || c.contains("." + GENRE)) return true;
            if (c.equals(LANGUAGE) || c.contains("." + LANGUAGE)) return true;
            if (c.equals(ACCESS_DATE) || c.contains("." + ACCESS_DATE)) return true;
            if (c.equals(COVER_ART) || c.contains("." + COVER_ART)) return true;
            if (c.equals(PUBLISHER) || c.contains("." + PUBLISHER)) return true;
            if (c.equals(VOLUME) || c.contains("." + VOLUME)) return true;
            if (c.equals(SERIES) || c.contains("." + SERIES)) return true;
            if (c.equals(ISSUE) || c.contains("." + ISSUE)) return true;
            if (c.equals(ARCHIVE_FILE) || c.contains("." + ARCHIVE_FILE)) return true;
            if (c.equals(IMAGE_DIRECTORY) || c.contains("." + IMAGE_DIRECTORY)) return true;
            if (c.equals(IS_DELETED) || c.contains("." + IS_DELETED)) return true;
            if (c.equals(LAST_PAGE_READ) || c.contains("." + LAST_PAGE_READ)) return true;
            if (c.equals(IS_FAVORITE) || c.contains("." + IS_FAVORITE)) return true;
        }
        return false;
    }

}
