package com.skubit.comics.provider.comicsarchive;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractSelection;

/**
 * Selection for the {@code comics_archive} table.
 */
public class ComicsArchiveSelection extends AbstractSelection<ComicsArchiveSelection> {
    @Override
    protected Uri baseUri() {
        return ComicsArchiveColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ComicsArchiveCursor} object, which is positioned before the first entry, or null.
     */
    public ComicsArchiveCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ComicsArchiveCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ComicsArchiveCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ComicsArchiveCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ComicsArchiveSelection id(long... value) {
        addEquals("comics_archive." + ComicsArchiveColumns._ID, toObjectArray(value));
        return this;
    }

    public ComicsArchiveSelection archiveFile(String... value) {
        addEquals(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection archiveFileNot(String... value) {
        addNotEquals(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection archiveFileLike(String... value) {
        addLike(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection archiveFileContains(String... value) {
        addContains(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection archiveFileStartsWith(String... value) {
        addStartsWith(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection archiveFileEndsWith(String... value) {
        addEndsWith(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveSelection coverArt(String... value) {
        addEquals(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection coverArtNot(String... value) {
        addNotEquals(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection coverArtLike(String... value) {
        addLike(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection coverArtContains(String... value) {
        addContains(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection coverArtStartsWith(String... value) {
        addStartsWith(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection coverArtEndsWith(String... value) {
        addEndsWith(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveSelection storyTitle(String... value) {
        addEquals(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection storyTitleNot(String... value) {
        addNotEquals(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection storyTitleLike(String... value) {
        addLike(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection storyTitleContains(String... value) {
        addContains(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection storyTitleStartsWith(String... value) {
        addStartsWith(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection storyTitleEndsWith(String... value) {
        addEndsWith(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPages(String... value) {
        addEquals(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPagesNot(String... value) {
        addNotEquals(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPagesLike(String... value) {
        addLike(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPagesContains(String... value) {
        addContains(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPagesStartsWith(String... value) {
        addStartsWith(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveSelection unarchivedPagesEndsWith(String... value) {
        addEndsWith(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }
}
