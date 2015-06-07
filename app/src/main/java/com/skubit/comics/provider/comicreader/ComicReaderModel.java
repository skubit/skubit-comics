package com.skubit.comics.provider.comicreader;

import com.skubit.comics.provider.base.BaseModel;

/**
 * Data model for the {@code comic_reader} table.
 */
public interface ComicReaderModel extends BaseModel {

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    String getCbid();

    /**
     * Get the {@code archive_file} value.
     * Can be {@code null}.
     */
    String getArchiveFile();

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
