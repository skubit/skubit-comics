package com.skubit.comics.provider.comicreader;

import com.skubit.comics.provider.base.AbstractSelection;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Selection for the {@code comic_reader} table.
 */
public class ComicReaderSelection extends AbstractSelection<ComicReaderSelection> {
    @Override
    protected Uri baseUri() {
        return ComicReaderColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ComicReaderCursor} object, which is positioned before the first entry, or null.
     */
    public ComicReaderCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ComicReaderCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ComicReaderCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ComicReaderCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ComicReaderCursor} object, which is positioned before the first entry, or null.
     */
    public ComicReaderCursor query(Context context, String[] projection, String sortOrder) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ComicReaderCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, projection, null)}.
     */
    public ComicReaderCursor query(Context context, String[] projection) {
        return query(context, projection, null);
    }

    /**
     * Equivalent of calling {@code query(context, projection, null, null)}.
     */
    public ComicReaderCursor query(Context context) {
        return query(context, null, null);
    }


    public ComicReaderSelection id(long... value) {
        addEquals("comic_reader." + ComicReaderColumns._ID, toObjectArray(value));
        return this;
    }

    public ComicReaderSelection cbid(String... value) {
        addEquals(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection cbidNot(String... value) {
        addNotEquals(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection cbidLike(String... value) {
        addLike(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection cbidContains(String... value) {
        addContains(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection cbidStartsWith(String... value) {
        addStartsWith(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection cbidEndsWith(String... value) {
        addEndsWith(ComicReaderColumns.CBID, value);
        return this;
    }

    public ComicReaderSelection archiveFile(String... value) {
        addEquals(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection archiveFileNot(String... value) {
        addNotEquals(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection archiveFileLike(String... value) {
        addLike(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection archiveFileContains(String... value) {
        addContains(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection archiveFileStartsWith(String... value) {
        addStartsWith(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection archiveFileEndsWith(String... value) {
        addEndsWith(ComicReaderColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicReaderSelection page(Integer... value) {
        addEquals(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageNot(Integer... value) {
        addNotEquals(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageGt(int value) {
        addGreaterThan(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageGtEq(int value) {
        addGreaterThanOrEquals(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageLt(int value) {
        addLessThan(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageLtEq(int value) {
        addLessThanOrEquals(ComicReaderColumns.PAGE, value);
        return this;
    }

    public ComicReaderSelection pageImage(String... value) {
        addEquals(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderSelection pageImageNot(String... value) {
        addNotEquals(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderSelection pageImageLike(String... value) {
        addLike(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderSelection pageImageContains(String... value) {
        addContains(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderSelection pageImageStartsWith(String... value) {
        addStartsWith(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicReaderSelection pageImageEndsWith(String... value) {
        addEndsWith(ComicReaderColumns.PAGE_IMAGE, value);
        return this;
    }
}
