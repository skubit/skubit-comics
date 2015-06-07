package com.skubit.comics.provider.collection;

import com.skubit.comics.provider.base.AbstractCursor;

import android.database.Cursor;

/**
 * Cursor wrapper for the {@code collection} table.
 */
public class CollectionCursor extends AbstractCursor implements CollectionModel {
    public CollectionCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        return getLongOrNull(CollectionColumns._ID);
    }

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    public String getCid() {
        return getStringOrNull(CollectionColumns.CID);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        return getStringOrNull(CollectionColumns.NAME);
    }

    /**
     * Get the {@code tags} value.
     * Can be {@code null}.
     */
    public String getTags() {
        return getStringOrNull(CollectionColumns.TAGS);
    }

    /**
     * Get the {@code coverart} value.
     * Can be {@code null}.
     */
    public String getCoverart() {
        return getStringOrNull(CollectionColumns.COVERART);
    }

    /**
     * Get the {@code type} value.
     * Can be {@code null}.
     */
    public String getType() {
        return getStringOrNull(CollectionColumns.TYPE);
    }
}
