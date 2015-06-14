package com.skubit.comics.provider.comicreader;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code comic_reader} table.
 */
public class ComicReaderContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ComicReaderColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  ComicReaderSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context,  ComicReaderSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ComicReaderContentValues putCbid(String value) {
        mContentValues.put(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderContentValues putCbidNull() {
        mContentValues.putNull(ComicReaderColumns.CBID);
        return this;
    }

    public ComicReaderContentValues putArchiveFile(String value) {
        mContentValues.put(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderContentValues putArchiveFileNull() {
        mContentValues.putNull(ComicReaderColumns.ARCHIVE_FILE);
        return this;
    }

    public ComicReaderContentValues putPage(Integer value) {
        mContentValues.put(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderContentValues putPageNull() {
        mContentValues.putNull(ComicReaderColumns.PAGE);
        return this;
    }

    public ComicReaderContentValues putPageImage(String value) {
        mContentValues.put(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderContentValues putPageImageNull() {
        mContentValues.putNull(ComicReaderColumns.PAGE_IMAGE);
        return this;
    }
}
