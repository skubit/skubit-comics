package com.skubit.comics.provider.collection;

import com.skubit.comics.provider.base.AbstractContentValues;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Content values wrapper for the {@code collection} table.
 */
public class CollectionContentValues extends AbstractContentValues {

    @Override
    public Uri uri() {
        return CollectionColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, CollectionSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(),
                where == null ? null : where.args());
    }

    public CollectionContentValues putCid(String value) {
        mContentValues.put(CollectionColumns.CID, value);
        return this;
    }

    public CollectionContentValues putCidNull() {
        mContentValues.putNull(CollectionColumns.CID);
        return this;
    }

    public CollectionContentValues putName(String value) {
        mContentValues.put(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionContentValues putNameNull() {
        mContentValues.putNull(CollectionColumns.NAME);
        return this;
    }

    public CollectionContentValues putTags(String value) {
        mContentValues.put(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionContentValues putTagsNull() {
        mContentValues.putNull(CollectionColumns.TAGS);
        return this;
    }

    public CollectionContentValues putCoverart(String value) {
        mContentValues.put(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionContentValues putCoverartNull() {
        mContentValues.putNull(CollectionColumns.COVERART);
        return this;
    }

    public CollectionContentValues putType(String value) {
        mContentValues.put(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionContentValues putTypeNull() {
        mContentValues.putNull(CollectionColumns.TYPE);
        return this;
    }
}
