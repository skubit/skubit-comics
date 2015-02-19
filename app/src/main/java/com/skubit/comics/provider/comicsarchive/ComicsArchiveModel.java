package com.skubit.comics.provider.comicsarchive;

import com.skubit.comics.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code comics_archive} table.
 */
public interface ComicsArchiveModel extends BaseModel {

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    String getArchiveFile();

    /**
     * Get the {@code cover_art} value.
     * Can be {@code null}.
     */
    String getCoverArt();

    /**
     * Get the {@code story_title} value.
     * Can be {@code null}.
     */
    String getStoryTitle();

    /**
     * Get the {@code unarchived_pages} value.
     * Can be {@code null}.
     */
    String getUnarchivedPages();
}
