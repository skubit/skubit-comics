package com.skubit.comics.provider.comicsarchive;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import com.skubit.comics.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code comics_archive} table.
 */
public class ComicsArchiveContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ComicsArchiveColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  ComicsArchiveSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ComicsArchiveContentValues putArchiveFile(String value) {
        mContentValues.put(ComicsArchiveColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicsArchiveContentValues putArchiveFileNull() {
        mContentValues.putNull(ComicsArchiveColumns.ARCHIVE_FILE);
        return this;
    }

    public ComicsArchiveContentValues putCoverArt(String value) {
        mContentValues.put(ComicsArchiveColumns.COVER_ART, value);
        return this;
    }

    public ComicsArchiveContentValues putCoverArtNull() {
        mContentValues.putNull(ComicsArchiveColumns.COVER_ART);
        return this;
    }

    public ComicsArchiveContentValues putStoryTitle(String value) {
        mContentValues.put(ComicsArchiveColumns.STORY_TITLE, value);
        return this;
    }

    public ComicsArchiveContentValues putStoryTitleNull() {
        mContentValues.putNull(ComicsArchiveColumns.STORY_TITLE);
        return this;
    }

    public ComicsArchiveContentValues putUnarchivedPages(String value) {
        mContentValues.put(ComicsArchiveColumns.UNARCHIVED_PAGES, value);
        return this;
    }

    public ComicsArchiveContentValues putUnarchivedPagesNull() {
        mContentValues.putNull(ComicsArchiveColumns.UNARCHIVED_PAGES);
        return this;
    }
}
