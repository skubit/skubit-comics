package com.skubit.comics.provider.collectionmapping;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

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
        return getLongOrNull(CollectionMappingColumns._ID);
    }

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    public String getCid() {
        return getStringOrNull(CollectionMappingColumns.CID);
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        return getStringOrNull(CollectionMappingColumns.CBID);
    }
}
