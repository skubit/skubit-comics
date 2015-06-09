package com.skubit.comics.provider.collectionmapping;

import com.skubit.comics.provider.base.AbstractCursor;

import android.database.Cursor;

/**
 * Cursor wrapper for the {@code collection_mapping} table.
 */
public class CollectionMappingCursor extends AbstractCursor implements CollectionMappingModel {
    public CollectionMappingCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CollectionMappingColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    public String getCid() {
        String res = getStringOrNull(CollectionMappingColumns.CID);
        return res;
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        String res = getStringOrNull(CollectionMappingColumns.CBID);
        return res;
    }
}
