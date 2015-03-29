package com.skubit.comics.provider.collectionmapping;

import com.skubit.comics.provider.base.BaseModel;

/**
 * Data model for the {@code collection_mapping} table.
 */
public interface CollectionMappingModel extends BaseModel {

    /**
     * Get the {@code cid} value.
     * Can be {@code null}.
     */
    String getCid();

    /**
     * Get the {@code cbid} value.
     * Can be {@code null}.
     */
    String getCbid();
}
