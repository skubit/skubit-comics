package com.skubit.comics.provider.comic;

import com.skubit.comics.provider.base.AbstractSelection;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Selection for the {@code comic} table.
 */
public class ComicSelection extends AbstractSelection<ComicSelection> {
    @Override
    protected Uri baseUri() {
        return ComicColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ComicCursor} object, which is positioned before the first entry, or null.
     */
    public ComicCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ComicCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public ComicCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public ComicCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ComicSelection id(long... value) {
        addEquals("comic." + ComicColumns._ID, toObjectArray(value));
        return this;
    }

    public ComicSelection cbid(String... value) {
        addEquals(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection cbidNot(String... value) {
        addNotEquals(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection cbidLike(String... value) {
        addLike(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection cbidContains(String... value) {
        addContains(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection cbidStartsWith(String... value) {
        addStartsWith(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection cbidEndsWith(String... value) {
        addEndsWith(ComicColumns.CBID, value);
        return this;
    }

    public ComicSelection storyTitle(String... value) {
        addEquals(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection storyTitleNot(String... value) {
        addNotEquals(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection storyTitleLike(String... value) {
        addLike(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection storyTitleContains(String... value) {
        addContains(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection storyTitleStartsWith(String... value) {
        addStartsWith(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection storyTitleEndsWith(String... value) {
        addEndsWith(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicSelection coverArt(String... value) {
        addEquals(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection coverArtNot(String... value) {
        addNotEquals(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection coverArtLike(String... value) {
        addLike(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection coverArtContains(String... value) {
        addContains(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection coverArtStartsWith(String... value) {
        addStartsWith(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection coverArtEndsWith(String... value) {
        addEndsWith(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicSelection publisher(String... value) {
        addEquals(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection publisherNot(String... value) {
        addNotEquals(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection publisherLike(String... value) {
        addLike(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection publisherContains(String... value) {
        addContains(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection publisherStartsWith(String... value) {
        addStartsWith(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection publisherEndsWith(String... value) {
        addEndsWith(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicSelection series(String... value) {
        addEquals(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection seriesNot(String... value) {
        addNotEquals(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection seriesLike(String... value) {
        addLike(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection seriesContains(String... value) {
        addContains(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection seriesStartsWith(String... value) {
        addStartsWith(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection seriesEndsWith(String... value) {
        addEndsWith(ComicColumns.SERIES, value);
        return this;
    }

    public ComicSelection issue(Integer... value) {
        addEquals(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection issueNot(Integer... value) {
        addNotEquals(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection issueGt(int value) {
        addGreaterThan(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection issueGtEq(int value) {
        addGreaterThanOrEquals(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection issueLt(int value) {
        addLessThan(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection issueLtEq(int value) {
        addLessThanOrEquals(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicSelection archiveFile(String... value) {
        addEquals(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection archiveFileNot(String... value) {
        addNotEquals(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection archiveFileLike(String... value) {
        addLike(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection archiveFileContains(String... value) {
        addContains(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection archiveFileStartsWith(String... value) {
        addStartsWith(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection archiveFileEndsWith(String... value) {
        addEndsWith(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicSelection imageDirectory(String... value) {
        addEquals(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection imageDirectoryNot(String... value) {
        addNotEquals(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection imageDirectoryLike(String... value) {
        addLike(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection imageDirectoryContains(String... value) {
        addContains(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection imageDirectoryStartsWith(String... value) {
        addStartsWith(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection imageDirectoryEndsWith(String... value) {
        addEndsWith(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicSelection isDeleted(Boolean value) {
        addEquals(ComicColumns.IS_DELETED, toObjectArray(value));
        return this;
    }

    public ComicSelection lastPageRead(Integer... value) {
        addEquals(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicSelection lastPageReadNot(Integer... value) {
        addNotEquals(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicSelection lastPageReadGt(int value) {
        addGreaterThan(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicSelection lastPageReadGtEq(int value) {
        addGreaterThanOrEquals(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicSelection lastPageReadLt(int value) {
        addLessThan(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicSelection lastPageReadLtEq(int value) {
        addLessThanOrEquals(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }
}
