package com.skubit.comics.provider.comic;

import com.skubit.comics.provider.base.AbstractContentValues;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Content values wrapper for the {@code comic} table.
 */
public class ComicContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ComicColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver,  ComicSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ComicContentValues putCbid(String value) {
        mContentValues.put(ComicColumns.CBID, value);
        return this;
    }

    public ComicContentValues putCbidNull() {
        mContentValues.putNull(ComicColumns.CBID);
        return this;
    }

    public ComicContentValues putStoryTitle(String value) {
        mContentValues.put(ComicColumns.STORY_TITLE, value);
        return this;
    }

    public ComicContentValues putStoryTitleNull() {
        mContentValues.putNull(ComicColumns.STORY_TITLE);
        return this;
    }

    public ComicContentValues putCoverArt(String value) {
        mContentValues.put(ComicColumns.COVER_ART, value);
        return this;
    }

    public ComicContentValues putCoverArtNull() {
        mContentValues.putNull(ComicColumns.COVER_ART);
        return this;
    }

    public ComicContentValues putPublisher(String value) {
        mContentValues.put(ComicColumns.PUBLISHER, value);
        return this;
    }

    public ComicContentValues putPublisherNull() {
        mContentValues.putNull(ComicColumns.PUBLISHER);
        return this;
    }

    public ComicContentValues putSeries(String value) {
        mContentValues.put(ComicColumns.SERIES, value);
        return this;
    }

    public ComicContentValues putSeriesNull() {
        mContentValues.putNull(ComicColumns.SERIES);
        return this;
    }

    public ComicContentValues putIssue(Integer value) {
        mContentValues.put(ComicColumns.ISSUE, value);
        return this;
    }

    public ComicContentValues putIssueNull() {
        mContentValues.putNull(ComicColumns.ISSUE);
        return this;
    }

    public ComicContentValues putArchiveFile(String value) {
        mContentValues.put(ComicColumns.ARCHIVE_FILE, value);
        return this;
    }

    public ComicContentValues putArchiveFileNull() {
        mContentValues.putNull(ComicColumns.ARCHIVE_FILE);
        return this;
    }

    public ComicContentValues putImageDirectory(String value) {
        mContentValues.put(ComicColumns.IMAGE_DIRECTORY, value);
        return this;
    }

    public ComicContentValues putImageDirectoryNull() {
        mContentValues.putNull(ComicColumns.IMAGE_DIRECTORY);
        return this;
    }

    public ComicContentValues putIsDeleted(Boolean value) {
        mContentValues.put(ComicColumns.IS_DELETED, value);
        return this;
    }

    public ComicContentValues putIsDeletedNull() {
        mContentValues.putNull(ComicColumns.IS_DELETED);
        return this;
    }

    public ComicContentValues putLastPageRead(Integer value) {
        mContentValues.put(ComicColumns.LAST_PAGE_READ, value);
        return this;
    }

    public ComicContentValues putLastPageReadNull() {
        mContentValues.putNull(ComicColumns.LAST_PAGE_READ);
        return this;
    }
}
