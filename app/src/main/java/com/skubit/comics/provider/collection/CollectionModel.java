package com.skubit.comics.provider.collection;

import com.skubit.comics.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code collection} table.
 */
public interface CollectionModel extends BaseModel {

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    String getCid();

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    String getName();

    /**
     * Get the {@code tags} value.
     * Can be {@code null}.
     */
    String getTags();

    /**
     * Get the {@code coverart} value.
     * Can be {@code null}.
     */
    String getCoverart();

    /**
     * Get the {@code type} value.
     * Can be {@code null}.
     */
    String getType();
}
