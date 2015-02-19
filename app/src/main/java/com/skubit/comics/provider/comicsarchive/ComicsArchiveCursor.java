package com.skubit.comics.provider.comicsarchive;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code comics_archive} table.
 */
public class ComicsArchiveCursor extends AbstractCursor implements ComicsArchiveModel {
    public ComicsArchiveCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(ComicsArchiveColumns._ID);
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        return getStringOrNull(ComicsArchiveColumns.ARCHIVE_FILE);
    }

    /**
     * Get the {@code cover_art} value.
     * Can be {@code null}.
     */
    public String getCoverArt() {
        return getStringOrNull(ComicsArchiveColumns.COVER_ART);
    }

    /**
     * Get the {@code story_title} value.
     * Can be {@code null}.
     */
    public String getStoryTitle() {
        return getStringOrNull(ComicsArchiveColumns.STORY_TITLE);
    }

    /**
     * Get the {@code unarchived_pages} value.
     * Can be {@code null}.
     */
    public String getUnarchivedPages() {
        return getStringOrNull(ComicsArchiveColumns.UNARCHIVED_PAGES);
    }
}
