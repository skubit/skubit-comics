package com.skubit.comics.provider;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.base.BaseContentProvider;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

public class ComicsProvider extends BaseContentProvider {
    private static final String TAG = ComicsProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ACCOUNTS = 0;
    private static final int URI_TYPE_ACCOUNTS_ID = 1;

    private static final int URI_TYPE_COLLECTION = 2;
    private static final int URI_TYPE_COLLECTION_ID = 3;

    private static final int URI_TYPE_COLLECTION_MAPPING = 4;
    private static final int URI_TYPE_COLLECTION_MAPPING_ID = 5;

    private static final int URI_TYPE_COMIC = 6;
    private static final int URI_TYPE_COMIC_ID = 7;

    private static final int URI_TYPE_COMIC_READER = 8;
    private static final int URI_TYPE_COMIC_READER_ID = 9;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, AccountsColumns.TABLE_NAME, URI_TYPE_ACCOUNTS);
        URI_MATCHER.addURI(AUTHORITY, AccountsColumns.TABLE_NAME + "/#", URI_TYPE_ACCOUNTS_ID);
        URI_MATCHER.addURI(AUTHORITY, CollectionColumns.TABLE_NAME, URI_TYPE_COLLECTION);
        URI_MATCHER.addURI(AUTHORITY, CollectionColumns.TABLE_NAME + "/#", URI_TYPE_COLLECTION_ID);
        URI_MATCHER.addURI(AUTHORITY, CollectionMappingColumns.TABLE_NAME, URI_TYPE_COLLECTION_MAPPING);
        URI_MATCHER.addURI(AUTHORITY, CollectionMappingColumns.TABLE_NAME + "/#", URI_TYPE_COLLECTION_MAPPING_ID);
        URI_MATCHER.addURI(AUTHORITY, ComicColumns.TABLE_NAME, URI_TYPE_COMIC);
        URI_MATCHER.addURI(AUTHORITY, ComicColumns.TABLE_NAME + "/#", URI_TYPE_COMIC_ID);
        URI_MATCHER.addURI(AUTHORITY, ComicReaderColumns.TABLE_NAME, URI_TYPE_COMIC_READER);
        URI_MATCHER.addURI(AUTHORITY, ComicReaderColumns.TABLE_NAME + "/#", URI_TYPE_COMIC_READER_ID);
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
            case URI_TYPE_ACCOUNTS:
                return TYPE_CURSOR_DIR + AccountsColumns.TABLE_NAME;
            case URI_TYPE_ACCOUNTS_ID:
                return TYPE_CURSOR_ITEM + AccountsColumns.TABLE_NAME;

            case URI_TYPE_COLLECTION:
                return TYPE_CURSOR_DIR + CollectionColumns.TABLE_NAME;
            case URI_TYPE_COLLECTION_ID:
                return TYPE_CURSOR_ITEM + CollectionColumns.TABLE_NAME;

            case URI_TYPE_COLLECTION_MAPPING:
                return TYPE_CURSOR_DIR + CollectionMappingColumns.TABLE_NAME;
            case URI_TYPE_COLLECTION_MAPPING_ID:
                return TYPE_CURSOR_ITEM + CollectionMappingColumns.TABLE_NAME;

            case URI_TYPE_COMIC:
                return TYPE_CURSOR_DIR + ComicColumns.TABLE_NAME;
            case URI_TYPE_COMIC_ID:
                return TYPE_CURSOR_ITEM + ComicColumns.TABLE_NAME;

            case URI_TYPE_COMIC_READER:
                return TYPE_CURSOR_DIR + ComicReaderColumns.TABLE_NAME;
            case URI_TYPE_COMIC_READER_ID:
                return TYPE_CURSOR_ITEM + ComicReaderColumns.TABLE_NAME;

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
            case URI_TYPE_ACCOUNTS:
            case URI_TYPE_ACCOUNTS_ID:
                res.table = AccountsColumns.TABLE_NAME;
                res.idColumn = AccountsColumns._ID;
                res.tablesWithJoins = AccountsColumns.TABLE_NAME;
                res.orderBy = AccountsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COLLECTION:
            case URI_TYPE_COLLECTION_ID:
                res.table = CollectionColumns.TABLE_NAME;
                res.idColumn = CollectionColumns._ID;
                res.tablesWithJoins = CollectionColumns.TABLE_NAME;
                res.orderBy = CollectionColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COLLECTION_MAPPING:
            case URI_TYPE_COLLECTION_MAPPING_ID:
                res.table = CollectionMappingColumns.TABLE_NAME;
                res.idColumn = CollectionMappingColumns._ID;
                res.tablesWithJoins = CollectionMappingColumns.TABLE_NAME;
                res.orderBy = CollectionMappingColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COMIC:
            case URI_TYPE_COMIC_ID:
                res.table = ComicColumns.TABLE_NAME;
                res.idColumn = ComicColumns._ID;
                res.tablesWithJoins = ComicColumns.TABLE_NAME;
                res.orderBy = ComicColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_COMIC_READER:
            case URI_TYPE_COMIC_READER_ID:
                res.table = ComicReaderColumns.TABLE_NAME;
                res.idColumn = ComicReaderColumns._ID;
                res.tablesWithJoins = ComicReaderColumns.TABLE_NAME;
                res.orderBy = ComicReaderColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ACCOUNTS_ID:
            case URI_TYPE_COLLECTION_ID:
            case URI_TYPE_COLLECTION_MAPPING_ID:
            case URI_TYPE_COMIC_ID:
            case URI_TYPE_COMIC_READER_ID:
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
