package com.skubit.comics.provider.archiveroots;

import com.skubit.comics.provider.base.BaseModel;

import java.util.Date;

/**
 * Data model for the {@code archive_roots} table.
 */
public interface ArchiveRootsModel extends BaseModel {

    /**
     * Get the {@code archive_root} value.
     * Can be {@code null}.
     */
    String getArchiveRoot();
}
