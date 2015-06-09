package com.skubit.comics.provider.collection;

import com.skubit.comics.provider.base.AbstractSelection;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Selection for the {@code collection} table.
 */
public class CollectionSelection extends AbstractSelection<CollectionSelection> {
    @Override
    protected Uri baseUri() {
        return CollectionColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CollectionCursor} object, which is positioned before the first entry, or null.
     */
    public CollectionCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CollectionCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CollectionCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CollectionCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code CollectionCursor} object, which is positioned before the first entry, or null.
     */
    public CollectionCursor query(Context context, String[] projection, String sortOrder) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CollectionCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, projection, null)}.
     */
    public CollectionCursor query(Context context, String[] projection) {
        return query(context, projection, null);
    }

    /**
     * Equivalent of calling {@code query(context, projection, null, null)}.
     */
    public CollectionCursor query(Context context) {
        return query(context, null, null);
    }


    public CollectionSelection id(long... value) {
        addEquals("collection." + CollectionColumns._ID, toObjectArray(value));
        return this;
    }

    public CollectionSelection cid(String... value) {
        addEquals(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection cidNot(String... value) {
        addNotEquals(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection cidLike(String... value) {
        addLike(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection cidContains(String... value) {
        addContains(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection cidStartsWith(String... value) {
        addStartsWith(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection cidEndsWith(String... value) {
        addEndsWith(CollectionColumns.CID, value);
        return this;
    }

    public CollectionSelection name(String... value) {
        addEquals(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection nameNot(String... value) {
        addNotEquals(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection nameLike(String... value) {
        addLike(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection nameContains(String... value) {
        addContains(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection nameStartsWith(String... value) {
        addStartsWith(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection nameEndsWith(String... value) {
        addEndsWith(CollectionColumns.NAME, value);
        return this;
    }

    public CollectionSelection tags(String... value) {
        addEquals(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection tagsNot(String... value) {
        addNotEquals(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection tagsLike(String... value) {
        addLike(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection tagsContains(String... value) {
        addContains(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection tagsStartsWith(String... value) {
        addStartsWith(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection tagsEndsWith(String... value) {
        addEndsWith(CollectionColumns.TAGS, value);
        return this;
    }

    public CollectionSelection coverart(String... value) {
        addEquals(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection coverartNot(String... value) {
        addNotEquals(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection coverartLike(String... value) {
        addLike(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection coverartContains(String... value) {
        addContains(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection coverartStartsWith(String... value) {
        addStartsWith(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection coverartEndsWith(String... value) {
        addEndsWith(CollectionColumns.COVERART, value);
        return this;
    }

    public CollectionSelection type(String... value) {
        addEquals(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionSelection typeNot(String... value) {
        addNotEquals(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionSelection typeLike(String... value) {
        addLike(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionSelection typeContains(String... value) {
        addContains(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionSelection typeStartsWith(String... value) {
        addStartsWith(CollectionColumns.TYPE, value);
        return this;
    }

    public CollectionSelection typeEndsWith(String... value) {
        addEndsWith(CollectionColumns.TYPE, value);
        return this;
    }
}
