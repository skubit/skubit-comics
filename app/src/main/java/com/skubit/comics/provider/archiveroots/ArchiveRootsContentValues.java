package com.skubit.comics.provider.archiveroots;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code archive_roots} table.
 */
public class ArchiveRootsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ArchiveRootsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  ArchiveRootsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ArchiveRootsContentValues putArchiveRoot(String value) {
        mContentValues.put(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsContentValues putArchiveRootNull() {
        mContentValues.putNull(ArchiveRootsColumns.ARCHIVE_ROOT);
        return this;
    }
}
