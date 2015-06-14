package com.skubit.comics.provider.comic;

import com.skubit.comics.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code comic} table.
 */
public interface ComicModel extends BaseModel {

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    String getCbid();

    /**
     * Get the {@code story_title} value.
     * Can be {@code null}.
     */
    String getStoryTitle();

    /**
     * Get the {@code archive_format} value.
     * Can be {@code null}.
     */
    String getArchiveFormat();

    /**
     * Get the {@code is_sample} value.
     * Can be {@code null}.
     */
    Boolean getIsSample();

    /**
     * Get the {@code download_date} value.
     * Can be {@code null}.
     */
    Date getDownloadDate();

    /**
     * Get the {@code age_rating} value.
     * Can be {@code null}.
     */
    String getAgeRating();

    /**
     * Get the {@code genre} value.
     * Can be {@code null}.
     */
    String getGenre();

    /**
     * Get the {@code language} value.
     * Can be {@code null}.
     */
    String getLanguage();

    /**
     * Get the {@code access_date} value.
     * Can be {@code null}.
     */
    Date getAccessDate();

    /**
     * Get the {@code cover_art} value.
     * Can be {@code null}.
     */
    String getCoverArt();

    /**
     * Get the {@code publisher} value.
     * Can be {@code null}.
     */
    String getPublisher();

    /**
     * Get the {@code volume} value.
     * Can be {@code null}.
     */
    String getVolume();

    /**
     * Get the {@code series} value.
     * Can be {@code null}.
     */
    String getSeries();

    /**
     * Get the {@code issue} value.
     * Can be {@code null}.
     */
    Integer getIssue();

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    String getArchiveFile();

    /**
     * Get the {@code image_directory} value.
     * Can be {@code null}.
     */
    String getImageDirectory();

    /**
     * Get the {@code is_deleted} value.
     * Can be {@code null}.
     */
    Boolean getIsDeleted();

    /**
     * Get the {@code last_page_read} value.
     * Can be {@code null}.
     */
    Integer getLastPageRead();

    /**
     * Get the {@code is_favorite} value.
     * Can be {@code null}.
     */
    Boolean getIsFavorite();
}
