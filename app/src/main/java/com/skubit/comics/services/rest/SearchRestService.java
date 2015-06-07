package com.skubit.comics.services.rest;

import com.skubit.shared.dto.LockerItemListDto;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SearchRestService {


    @GET("/suggest")
    LockerItemListDto suggest(
            @Path("application") String application,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("cursor") String cursor);
}
