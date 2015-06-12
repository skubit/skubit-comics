package com.skubit.shared.rest;

public class PathParameter {

    /** Comic* */
    public static final String COMIC_BOOK = "{cbid}";

    public static final String COMIC_DOWNLOAD = "{cbid}/comic/download";

    public static final String COMIC_BOOKS_GET_ALL = "issues/all";

    public static final String COMIC_BOOKS_MOST_RECENT = "issues/mostRecent";

    public static final String COMIC_BOOKS_GET_BY_SERIES = "issues/series/{series}";

    public static final String COMIC_SCREENSHOTS_DOWNLOAD = "{cbid}/screenshots/download";

    public static final String COMIC_DOWNLOAD_SAMPLE = "{cbid}/comic/download/sample";

    public static final String COMIC_FILES = "{cbid}/comicFiles";

    public static final String COMIC_FILTER = "issues/filter";

    public static final String COMICS_OPEN_ORDERS = "orders";

    public static final String LOCKER_ITEMS = "items/{application}";

    public static final String LOCKER_ITEM_EXIST = "item/{cbid}/exist";

    /**Publisher**/
    public static final String PUBLISHERS = "publishers";

    /**Series**/
    public static final String SERIES = "series";

    public static final String GENRES = "genres/all";

    public static final String CREATORS = "creators";

    public static final String SERIES_BY_PUBLISHER = "publisher/{publisher}/series";

    public static final String SERIES_BY_CREATOR = "creator/{creator}/series";

    public static final String SERIES_BY_GENRE = "genre/{genre}/series";

    public static final String SEARCH = "search";
}
