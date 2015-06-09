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
        Long res = getLongOrNull(CollectionColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    public String getCid() {
        String res = getStringOrNull(CollectionColumns.CID);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        String res = getStringOrNull(CollectionColumns.NAME);
        return res;
    }

    /**
     * Get the {@code tags} value.
     * Can be {@code null}.
     */
    public String getTags() {
        String res = getStringOrNull(CollectionColumns.TAGS);
        return res;
    }

    /**
     * Get the {@code coverart} value.
     * Can be {@code null}.
     */
    public String getCoverart() {
        String res = getStringOrNull(CollectionColumns.COVERART);
        return res;
    }

    /**
     * Get the {@code type} value.
     * Can be {@code null}.
     */
    public String getType() {
        String res = getStringOrNull(CollectionColumns.TYPE);
        return res;
    }
}
