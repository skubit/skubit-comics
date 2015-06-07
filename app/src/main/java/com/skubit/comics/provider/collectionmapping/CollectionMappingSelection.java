package com.skubit.comics.provider.collectionmapping;

import com.skubit.comics.provider.base.AbstractSelection;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Selection for the {@code collection_mapping} table.
 */
public class CollectionMappingSelection extends AbstractSelection<CollectionMappingSelection> {
    @Override
    protected Uri baseUri() {
        return CollectionMappingColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CollectionMappingCursor} object, which is positioned before the first entry, or null.
     */
    public CollectionMappingCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CollectionMappingCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CollectionMappingCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CollectionMappingCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CollectionMappingSelection id(long... value) {
        addEquals("collection_mapping." + CollectionMappingColumns._ID, toObjectArray(value));
        return this;
    }

    public CollectionMappingSelection cid(String... value) {
        addEquals(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cidNot(String... value) {
        addNotEquals(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cidLike(String... value) {
        addLike(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cidContains(String... value) {
        addContains(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cidStartsWith(String... value) {
        addStartsWith(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cidEndsWith(String... value) {
        addEndsWith(CollectionMappingColumns.CID, value);
        return this;
    }

    public CollectionMappingSelection cbid(String... value) {
        addEquals(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingSelection cbidNot(String... value) {
        addNotEquals(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingSelection cbidLike(String... value) {
        addLike(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingSelection cbidContains(String... value) {
        addContains(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingSelection cbidStartsWith(String... value) {
        addStartsWith(CollectionMappingColumns.CBID, value);
        return this;
    }

    public CollectionMappingSelection cbidEndsWith(String... value) {
        addEndsWith(CollectionMappingColumns.CBID, value);
        return this;
    }
}
