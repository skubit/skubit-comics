package com.skubit.comics.provider.comicsinfo;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code comics_info} table.
 */
public class ComicsInfoCursor extends AbstractCursor implements ComicsInfoModel {
    public ComicsInfoCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(ComicsInfoColumns._ID);
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        return getStringOrNull(ComicsInfoColumns.ARCHIVE_FILE);
    }

    /**
     * Get the {@code archive_file_entry} value.
     * Can be {@code null}.
     */
    public String getArchiveFileEntry() {
        return getStringOrNull(ComicsInfoColumns.ARCHIVE_FILE_ENTRY);
    }

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    public Integer getPage() {
        return getIntegerOrNull(ComicsInfoColumns.PAGE);
    }

    /**
     * Get the {@code page_image} value.
     * Can be {@code null}.
     */
    public String getPageImage() {
        return getStringOrNull(ComicsInfoColumns.PAGE_IMAGE);
    }
}
