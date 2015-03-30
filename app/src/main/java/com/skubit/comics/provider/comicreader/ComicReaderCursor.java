package com.skubit.comics.provider.comicreader;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code comic_reader} table.
 */
public class ComicReaderCursor extends AbstractCursor implements ComicReaderModel {
    public ComicReaderCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(ComicReaderColumns._ID);
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        return getStringOrNull(ComicReaderColumns.CBID);
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        return getStringOrNull(ComicReaderColumns.ARCHIVE_FILE);
    }

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    public Integer getPage() {
        return getIntegerOrNull(ComicReaderColumns.PAGE);
    }

    /**
     * Get the {@code page_image} value.
     * Can be {@code null}.
     */
    public String getPageImage() {
        return getStringOrNull(ComicReaderColumns.PAGE_IMAGE);
    }
}
