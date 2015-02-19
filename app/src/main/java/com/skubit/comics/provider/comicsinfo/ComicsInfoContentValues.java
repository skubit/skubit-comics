package com.skubit.comics.provider.comicsinfo;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code comics_info} table.
 */
public class ComicsInfoContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ComicsInfoColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  ComicsInfoSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ComicsInfoContentValues putArchiveFile(String value) {
        mContentValues.put(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoContentValues putArchiveFileNull() {
        mContentValues.putNull(ComicsInfoColumns.ARCHIVE_FILE);
        return this;
    }

    public ComicsInfoContentValues putArchiveFileEntry(String value) {
        mContentValues.put(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoContentValues putArchiveFileEntryNull() {
        mContentValues.putNull(ComicsInfoColumns.ARCHIVE_FILE_ENTRY);
        return this;
    }

    public ComicsInfoContentValues putPage(Integer value) {
        mContentValues.put(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoContentValues putPageNull() {
        mContentValues.putNull(ComicsInfoColumns.PAGE);
        return this;
    }

    public ComicsInfoContentValues putPageImage(String value) {
        mContentValues.put(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoContentValues putPageImageNull() {
        mContentValues.putNull(ComicsInfoColumns.PAGE_IMAGE);
        return this;
    }
}
