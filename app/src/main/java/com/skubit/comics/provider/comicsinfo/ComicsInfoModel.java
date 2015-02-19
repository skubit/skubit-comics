package com.skubit.comics.provider.comicsinfo;

import com.skubit.comics.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code comics_info} table.
 */
public interface ComicsInfoModel extends BaseModel {

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    String getArchiveFile();

    /**
     * Get the {@code archive_file_entry} value.
     * Can be {@code null}.
     */
    String getArchiveFileEntry();

    /**
     * Get the {@code page} value.
     * Can be {@code null}.
     */
    Integer getPage();

    /**
     * Get the {@code page_image} value.
     * Can be {@code null}.
     */
    String getPageImage();
}
