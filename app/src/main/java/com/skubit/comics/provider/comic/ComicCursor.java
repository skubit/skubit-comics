package com.skubit.comics.provider.comic;

import java.util.Date;

import android.database.Cursor;

import com.skubit.comics.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code comic} table.
 */
public class ComicCursor extends AbstractCursor implements ComicModel {
    public ComicCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ComicColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    public String getCbid() {
        String res = getStringOrNull(ComicColumns.CBID);
        return res;
    }

    /**
     * Get the {@code story_title} value.
     * Can be {@code null}.
     */
    public String getStoryTitle() {
        String res = getStringOrNull(ComicColumns.STORY_TITLE);
        return res;
    }

    /**
     * Get the {@code archive_format} value.
     * Can be {@code null}.
     */
    public String getArchiveFormat() {
        String res = getStringOrNull(ComicColumns.ARCHIVE_FORMAT);
        return res;
    }

    /**
     * Get the {@code is_sample} value.
     * Can be {@code null}.
     */
    public Boolean getIsSample() {
        Boolean res = getBooleanOrNull(ComicColumns.IS_SAMPLE);
        return res;
    }

    /**
     * Get the {@code download_date} value.
     * Can be {@code null}.
     */
    public Date getDownloadDate() {
        Date res = getDateOrNull(ComicColumns.DOWNLOAD_DATE);
        return res;
    }

    /**
     * Get the {@code age_rating} value.
     * Can be {@code null}.
     */
    public String getAgeRating() {
        String res = getStringOrNull(ComicColumns.AGE_RATING);
        return res;
    }

    /**
     * Get the {@code genre} value.
     * Can be {@code null}.
     */
    public String getGenre() {
        String res = getStringOrNull(ComicColumns.GENRE);
        return res;
    }

    /**
     * Get the {@code language} value.
     * Can be {@code null}.
     */
    public String getLanguage() {
        String res = getStringOrNull(ComicColumns.LANGUAGE);
        return res;
    }

    /**
     * Get the {@code access_date} value.
     * Can be {@code null}.
     */
    public Date getAccessDate() {
        Date res = getDateOrNull(ComicColumns.ACCESS_DATE);
        return res;
    }

    /**
     * Get the {@code cover_art} value.
     * Can be {@code null}.
     */
    public String getCoverArt() {
        String res = getStringOrNull(ComicColumns.COVER_ART);
        return res;
    }

    /**
     * Get the {@code publisher} value.
     * Can be {@code null}.
     */
    public String getPublisher() {
        String res = getStringOrNull(ComicColumns.PUBLISHER);
        return res;
    }

    /**
     * Get the {@code volume} value.
     * Can be {@code null}.
     */
    public String getVolume() {
        String res = getStringOrNull(ComicColumns.VOLUME);
        return res;
    }

    /**
     * Get the {@code series} value.
     * Can be {@code null}.
     */
    public String getSeries() {
        String res = getStringOrNull(ComicColumns.SERIES);
        return res;
    }

    /**
     * Get the {@code issue} value.
     * Can be {@code null}.
     */
    public Integer getIssue() {
        Integer res = getIntegerOrNull(ComicColumns.ISSUE);
        return res;
    }

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    public String getArchiveFile() {
        String res = getStringOrNull(ComicColumns.ARCHIVE_FILE);
        return res;
    }

    /**
     * Get the {@code image_directory} value.
     * Can be {@code null}.
     */
    public String getImageDirectory() {
        String res = getStringOrNull(ComicColumns.IMAGE_DIRECTORY);
        return res;
    }

    /**
     * Get the {@code is_deleted} value.
     * Can be {@code null}.
     */
    public Boolean getIsDeleted() {
        Boolean res = getBooleanOrNull(ComicColumns.IS_DELETED);
        return res;
    }

    /**
     * Get the {@code last_page_read} value.
     * Can be {@code null}.
     */
    public Integer getLastPageRead() {
        Integer res = getIntegerOrNull(ComicColumns.LAST_PAGE_READ);
        return res;
    }

    /**
     * Get the {@code is_favorite} value.
     * Can be {@code null}.
     */
    public Boolean getIsFavorite() {
        Boolean res = getBooleanOrNull(ComicColumns.IS_FAVORITE);
        return res;
    }
}
