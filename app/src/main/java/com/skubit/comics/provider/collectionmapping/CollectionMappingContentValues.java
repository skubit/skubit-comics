package com.skubit.comics.provider.collectionmapping;

import com.skubit.comics.provider.base.AbstractContentValues;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Content values wrapper for the {@code collection_mapping} table.
 */
public class CollectionMappingContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CollectionMappingColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  CollectionMappingSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CollectionMappingContentValues putCid(String value) {
        mContentValues.put(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingContentValues putCidNull() {
        mContentValues.putNull(CollectionMappingColumns.CID);
        return this;
    }

    public CollectionMappingContentValues putCbid(String value) {
        mContentValues.put(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingContentValues putCbidNull() {
        mContentValues.putNull(CollectionMappingColumns.CBID);
        return this;
    }
}
