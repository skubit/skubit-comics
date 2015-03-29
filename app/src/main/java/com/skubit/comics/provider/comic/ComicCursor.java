package com.skubit.comics.provider.comic;

import com.skubit.comics.provider.base.AbstractCursor;

import android.database.Cursor;

/**
 * Cursor wrapper for the {@code comic} table.
 */
public class ComicCursor extends AbstractCursor implements ComicModel {

    public ComicCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(ComicColumns._ID);
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        return getStringOrNull(ComicColumns.CBID);
    }

    /**
     * Get the {@code story_title} value.
     * Can be {@code null}.
     */
    public String getStoryTitle() {
        return getStringOrNull(ComicColumns.STORY_TITLE);
    }

    /**
     * Get the {@code cover_art} value.
     * Can be {@code null}.
     */
    public String getCoverArt() {
        return getStringOrNull(ComicColumns.COVER_ART);
    }

    /**
     * Get the {@code publisher} value.
     * Can be {@code null}.
     */
    public String getPublisher() {
        return getStringOrNull(ComicColumns.PUBLISHER);
    }

    /**
     * Get the {@code series} value.
     * Can be {@code null}.
     */
    public String getSeries() {
        return getStringOrNull(ComicColumns.SERIES);
    }

    /**
     * Get the {@code issue} value.
     * Can be {@code null}.
     */
    public Integer getIssue() {
        return getIntegerOrNull(ComicColumns.ISSUE);
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        return getStringOrNull(ComicColumns.ARCHIVE_FILE);
    }

    /**
     * Get the {@code image_directory} value.
     * Can be {@code null}.
     */
    public String getImageDirectory() {
        return getStringOrNull(ComicColumns.IMAGE_DIRECTORY);
    }

    /**
     * Get the {@code is_deleted} value.
     * Can be {@code null}.
     */
    public Boolean getIsDeleted() {
        return getBooleanOrNull(ComicColumns.IS_DELETED);
    }

    /**
     * Get the {@code last_page_read} value.
     * Can be {@code null}.
     */
    public Integer getLastPageRead() {
        return getIntegerOrNull(ComicColumns.LAST_PAGE_READ);
    }
}
