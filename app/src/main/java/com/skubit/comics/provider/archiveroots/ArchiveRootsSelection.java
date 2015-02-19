package com.skubit.comics.provider.archiveroots;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractSelection;

/**
 * Selection for the {@code archive_roots} table.
 */
public class ArchiveRootsSelection extends AbstractSelection<ArchiveRootsSelection> {
    @Override
    protected Uri baseUri() {
        return ArchiveRootsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ArchiveRootsCursor} object, which is positioned before the first entry, or null.
     */
    public ArchiveRootsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ArchiveRootsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ArchiveRootsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ArchiveRootsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ArchiveRootsSelection id(long... value) {
        addEquals("archive_roots." + ArchiveRootsColumns._ID, toObjectArray(value));
        return this;
    }

    public ArchiveRootsSelection archiveRoot(String... value) {
        addEquals(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsSelection archiveRootNot(String... value) {
        addNotEquals(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsSelection archiveRootLike(String... value) {
        addLike(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsSelection archiveRootContains(String... value) {
        addContains(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsSelection archiveRootStartsWith(String... value) {
        addStartsWith(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }

    public ArchiveRootsSelection archiveRootEndsWith(String... value) {
        addEndsWith(ArchiveRootsColumns.ARCHIVE_ROOT, value);
        return this;
    }
}
