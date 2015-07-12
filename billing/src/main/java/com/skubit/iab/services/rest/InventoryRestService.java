/**
 * Copyright 2014 Skubit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.skubit.iab.services.rest;

import com.skubit.shared.dto.ApplicationsListDto;
import com.skubit.shared.dto.SkuDetailsDto;
import com.skubit.shared.dto.SkuDetailsListDto;
import com.skubit.shared.rest.IabPathParameter;
import com.skubit.shared.rest.IabResourcesPath;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface InventoryRestService {

    public static final String baseUri = IabResourcesPath.INVENTORY_API;

    @GET(baseUri + "/" + IabPathParameter.INVENTORY_COUNT)
    Integer getApplicationCount(@Path("application") String application);

    @GET(baseUri + "/applications")
    ApplicationsListDto getApplications(
            @Query("vendorId") String vendorId,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("cursor") String cursor);

    @GET(baseUri + "/" + IabPathParameter.SKUS_SKUDETAILS)
    SkuDetailsDto getSkuDetails(
            @Path("application") String application,
            @Path("productId") String productId);

    @GET(baseUri + "/" + IabPathParameter.SKUS_LIST)
    SkuDetailsListDto getSkuDetailsByIds(
            @Path("application") String application,
            @Query("ids") String productIds);

    @GET(baseUri + "/" + IabPathParameter.SKUS_COUNT)
    Integer getSkuDetailsCount(
            @Path("application") String application);
}
