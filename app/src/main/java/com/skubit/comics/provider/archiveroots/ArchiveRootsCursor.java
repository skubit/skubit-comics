package com.skubit.comics.provider.archiveroots;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code archive_roots} table.
 */
public class ArchiveRootsCursor extends AbstractCursor implements ArchiveRootsModel {
    public ArchiveRootsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(ArchiveRootsColumns._ID);
    }

    /**
     * Get the {@code archive_root} value.
     * Can be {@code null}.
     */
    public String getArchiveRoot() {
        return getStringOrNull(ArchiveRootsColumns.ARCHIVE_ROOT);
    }
}
