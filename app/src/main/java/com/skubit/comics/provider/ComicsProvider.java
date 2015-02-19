package com.skubit.comics.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.provider.base.BaseContentProvider;
import com.skubit.comics.provider.archiveroots.ArchiveRootsColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;

public class ComicsProvider extends BaseContentProvider {
    private static final String TAG = ComicsProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.skubit.comics.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ARCHIVE_ROOTS = 0;
    private static final int URI_TYPE_ARCHIVE_ROOTS_ID = 1;

    private static final int URI_TYPE_COMICS_ARCHIVE = 2;
    private static final int URI_TYPE_COMICS_ARCHIVE_ID = 3;

    private static final int URI_TYPE_COMICS_INFO = 4;
    private static final int URI_TYPE_COMICS_INFO_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, ArchiveRootsColumns.TABLE_NAME, URI_TYPE_ARCHIVE_ROOTS);
        URI_MATCHER.addURI(AUTHORITY, ArchiveRootsColumns.TABLE_NAME + "/#", URI_TYPE_ARCHIVE_ROOTS_ID);
        URI_MATCHER.addURI(AUTHORITY, ComicsArchiveColumns.TABLE_NAME, URI_TYPE_COMICS_ARCHIVE);
        URI_MATCHER.addURI(AUTHORITY, ComicsArchiveColumns.TABLE_NAME + "/#", URI_TYPE_COMICS_ARCHIVE_ID);
        URI_MATCHER.addURI(AUTHORITY, ComicsInfoColumns.TABLE_NAME, URI_TYPE_COMICS_INFO);
        URI_MATCHER.addURI(AUTHORITY, ComicsInfoColumns.TABLE_NAME + "/#", URI_TYPE_COMICS_INFO_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return ComicsSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_ARCHIVE_ROOTS:
                return TYPE_CURSOR_DIR + ArchiveRootsColumns.TABLE_NAME;
            case URI_TYPE_ARCHIVE_ROOTS_ID:
                return TYPE_CURSOR_ITEM + ArchiveRootsColumns.TABLE_NAME;

            case URI_TYPE_COMICS_ARCHIVE:
                return TYPE_CURSOR_DIR + ComicsArchiveColumns.TABLE_NAME;
            case URI_TYPE_COMICS_ARCHIVE_ID:
                return TYPE_CURSOR_ITEM + ComicsArchiveColumns.TABLE_NAME;

            case URI_TYPE_COMICS_INFO:
                return TYPE_CURSOR_DIR + ComicsInfoColumns.TABLE_NAME;
            case URI_TYPE_COMICS_INFO_ID:
                return TYPE_CURSOR_ITEM + ComicsInfoColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_ARCHIVE_ROOTS:
            case URI_TYPE_ARCHIVE_ROOTS_ID:
                res.table = ArchiveRootsColumns.TABLE_NAME;
                res.idColumn = ArchiveRootsColumns._ID;
                res.tablesWithJoins = ArchiveRootsColumns.TABLE_NAME;
                res.orderBy = ArchiveRootsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COMICS_ARCHIVE:
            case URI_TYPE_COMICS_ARCHIVE_ID:
                res.table = ComicsArchiveColumns.TABLE_NAME;
                res.idColumn = ComicsArchiveColumns._ID;
                res.tablesWithJoins = ComicsArchiveColumns.TABLE_NAME;
                res.orderBy = ComicsArchiveColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COMICS_INFO:
            case URI_TYPE_COMICS_INFO_ID:
                res.table = ComicsInfoColumns.TABLE_NAME;
                res.idColumn = ComicsInfoColumns._ID;
                res.tablesWithJoins = ComicsInfoColumns.TABLE_NAME;
                res.orderBy = ComicsInfoColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ARCHIVE_ROOTS_ID:
            case URI_TYPE_COMICS_ARCHIVE_ID:
            case URI_TYPE_COMICS_INFO_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
