package com.skubit.comics.provider.comicsinfo;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractSelection;

/**
 * Selection for the {@code comics_info} table.
 */
public class ComicsInfoSelection extends AbstractSelection<ComicsInfoSelection> {
    @Override
    protected Uri baseUri() {
        return ComicsInfoColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ComicsInfoCursor} object, which is positioned before the first entry, or null.
     */
    public ComicsInfoCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ComicsInfoCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ComicsInfoCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ComicsInfoCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ComicsInfoSelection id(long... value) {
        addEquals("comics_info." + ComicsInfoColumns._ID, toObjectArray(value));
        return this;
    }

    public ComicsInfoSelection archiveFile(String... value) {
        addEquals(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileNot(String... value) {
        addNotEquals(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileLike(String... value) {
        addLike(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileContains(String... value) {
        addContains(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileStartsWith(String... value) {
        addStartsWith(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEndsWith(String... value) {
        addEndsWith(ComicsInfoColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntry(String... value) {
        addEquals(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntryNot(String... value) {
        addNotEquals(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntryLike(String... value) {
        addLike(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntryContains(String... value) {
        addContains(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntryStartsWith(String... value) {
        addStartsWith(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection archiveFileEntryEndsWith(String... value) {
        addEndsWith(ComicsInfoColumns.ARCHIVE_FILE_ENTRY, value);
        return this;
    }

    public ComicsInfoSelection page(Integer... value) {
        addEquals(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageNot(Integer... value) {
        addNotEquals(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageGt(int value) {
        addGreaterThan(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageGtEq(int value) {
        addGreaterThanOrEquals(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageLt(int value) {
        addLessThan(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageLtEq(int value) {
        addLessThanOrEquals(ComicsInfoColumns.PAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImage(String... value) {
        addEquals(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImageNot(String... value) {
        addNotEquals(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImageLike(String... value) {
        addLike(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImageContains(String... value) {
        addContains(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImageStartsWith(String... value) {
        addStartsWith(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }

    public ComicsInfoSelection pageImageEndsWith(String... value) {
        addEndsWith(ComicsInfoColumns.PAGE_IMAGE, value);
        return this;
    }
}
