/**
 * Copyright 2015 Skubit
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
package com.skubit.comics.services.rest;

import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.CatalogFilter;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookListDto;
import com.skubit.shared.dto.ComicFileClassifier;
import com.skubit.shared.dto.ComicFileDto;
import com.skubit.shared.dto.CreatorListDto;
import com.skubit.shared.dto.GenreListDto;
import com.skubit.shared.dto.ImageType;
import com.skubit.shared.dto.PublisherListDto;
import com.skubit.shared.dto.SeriesListDto;
import com.skubit.shared.dto.UrlDto;
import com.skubit.shared.rest.PathParameter;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ComicRestService {

    String baseUri = "/";

    @GET(baseUri + PathParameter.COMIC_BOOKS_GET_ALL)
    ComicBookListDto getAllComics(
            @Query("limit") int limit,
            @Query("cursor") String cursor,
            @Query("isReduced") boolean isReduced,
            @Query("packageName") String packageName);

    @GET(baseUri + PathParameter.COMIC_BOOKS_MOST_RECENT)
    ComicBookListDto getMostRecent(@Query("packageName") String packageName);

    @GET(baseUri + PathParameter.COMIC_BOOK)
    ComicBookDto getComicBook(@Path("cbid") String cbid);

    @GET(baseUri + PathParameter.COMIC_DOWNLOAD)
    UrlDto getDownloadUrl(@Path("cbid") String cbid,  @Query("format") ArchiveFormat format);

    @GET(baseUri + PathParameter.COMIC_DOWNLOAD_SAMPLE)
    UrlDto getSampleDownload(@Path("cbid") String cbid,
            @Query("format") ArchiveFormat format);

    @GET(baseUri + PathParameter.COMIC_FILES)
    ArrayList<ComicFileDto> getComicFiles(@Path("cbid") String cbid,
            @Query("classifier") ComicFileClassifier classifier);

    @GET(baseUri + PathParameter.GENRES)
    GenreListDto getGenres();

    @GET(baseUri + PathParameter.PUBLISHERS)
    PublisherListDto getPublishers(@Query("alphaBucket") int alphaBucket,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("cursor") String cursor);

    @GET(baseUri + PathParameter.CREATORS)
    CreatorListDto getCreators(@Query("alphaBucket") int alphaBucket,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("cursor") String cursor);

    @GET(baseUri + PathParameter.COMIC_BOOKS_GET_BY_SERIES)
    ComicBookListDto getBySeries(@Path("series") String series,
            @Query("limit") int limit,
            @Query("cursor") String cursor,
            @Query("packageName") String packageName);

    @GET(baseUri + PathParameter.SERIES)
    SeriesListDto getSeries(
            @Query("alphaBucket") int alphaBucket,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("cursor") String cursor);

    @GET(baseUri + PathParameter.SERIES_BY_CREATOR)
    SeriesListDto getByCreator(@Path("creator") String creator,
            @Query("limit") int limit,
            @Query("cursor") String cursor,
            @Query("packageName") String packageName);

    @GET(baseUri + PathParameter.SERIES_BY_PUBLISHER)
    SeriesListDto getByPublisher(@Path("publisher") String publisher,
            @Query("limit") int limit,
            @Query("cursor") String cursor,
            @Query("packageName") String packageName);

    @GET(baseUri + PathParameter.SERIES_BY_GENRE)
    SeriesListDto getByGenre(@Path("genre") String genre,
            @Query("limit") int limit,
            @Query("cursor") String cursor,
            @Query("packageName") String packageName);

    @GET(baseUri + PathParameter.COMIC_FILTER)
    ComicBookListDto getByFilter(@Query("filter") CatalogFilter filter,
            @Query("limit") int limit,
            @Query("cursor") String cursor);

    @GET(baseUri + PathParameter.COMIC_SCREENSHOTS_DOWNLOAD)
    ArrayList<UrlDto> getScreenshotsDownload(@Path("cbid") String cbid,
            @Query("type") ImageType type);

    @GET(baseUri + PathParameter.SEARCH)
    ComicBookListDto search(@Query("q") String query, @Query("offset") int offset,
            @Query("limit") int limit);
}