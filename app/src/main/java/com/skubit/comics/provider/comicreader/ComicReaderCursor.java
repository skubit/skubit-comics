package com.skubit.comics.provider.comicreader;

import com.skubit.comics.provider.base.AbstractCursor;

import android.database.Cursor;

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
        Long res = getLongOrNull(ComicReaderColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        String res = getStringOrNull(ComicReaderColumns.CBID);
        return res;
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        String res = getStringOrNull(ComicReaderColumns.ARCHIVE_FILE);
        return res;
    }

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    public Integer getPage() {
        Integer res = getIntegerOrNull(ComicReaderColumns.PAGE);
        return res;
    }

    /**
     * Get the {@code page_image} value.
     * Can be {@code null}.
     */
    public String getPageImage() {
        String res = getStringOrNull(ComicReaderColumns.PAGE_IMAGE);
        return res;
    }
}
