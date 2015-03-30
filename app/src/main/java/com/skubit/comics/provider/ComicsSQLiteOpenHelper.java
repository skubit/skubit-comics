package com.skubit.comics.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;

public class ComicsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ComicsSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "skubitcomics.db";
    private static final int DATABASE_VERSION = 1;
    private static ComicsSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final ComicsSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS "
            + AccountsColumns.TABLE_NAME + " ( "
            + AccountsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AccountsColumns.BITID + " TEXT, "
            + AccountsColumns.TOKEN + " TEXT, "
            + AccountsColumns.DATE + " INTEGER "
            + " );";

    public static final String SQL_CREATE_INDEX_ACCOUNTS_BITID = "CREATE INDEX IDX_ACCOUNTS_BITID "
            + " ON " + AccountsColumns.TABLE_NAME + " ( " + AccountsColumns.BITID + " );";

    public static final String SQL_CREATE_TABLE_COLLECTION = "CREATE TABLE IF NOT EXISTS "
            + CollectionColumns.TABLE_NAME + " ( "
            + CollectionColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CollectionColumns.CID + " TEXT, "
            + CollectionColumns.NAME + " TEXT, "
            + CollectionColumns.TAGS + " TEXT, "
            + CollectionColumns.COVERART + " TEXT, "
            + CollectionColumns.TYPE + " TEXT "
            + " );";

    public static final String SQL_CREATE_INDEX_COLLECTION_CID = "CREATE INDEX IDX_COLLECTION_CID "
            + " ON " + CollectionColumns.TABLE_NAME + " ( " + CollectionColumns.CID + " );";

    public static final String SQL_CREATE_INDEX_COLLECTION_NAME = "CREATE INDEX IDX_COLLECTION_NAME "
            + " ON " + CollectionColumns.TABLE_NAME + " ( " + CollectionColumns.NAME + " );";

    public static final String SQL_CREATE_INDEX_COLLECTION_TYPE = "CREATE INDEX IDX_COLLECTION_TYPE "
            + " ON " + CollectionColumns.TABLE_NAME + " ( " + CollectionColumns.TYPE + " );";

    public static final String SQL_CREATE_TABLE_COLLECTION_MAPPING = "CREATE TABLE IF NOT EXISTS "
            + CollectionMappingColumns.TABLE_NAME + " ( "
            + CollectionMappingColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CollectionMappingColumns.CID + " TEXT, "
            + CollectionMappingColumns.CBID + " TEXT "
            + " );";

    public static final String SQL_CREATE_INDEX_COLLECTION_MAPPING_CID = "CREATE INDEX IDX_COLLECTION_MAPPING_CID "
            + " ON " + CollectionMappingColumns.TABLE_NAME + " ( " + CollectionMappingColumns.CID + " );";

    public static final String SQL_CREATE_INDEX_COLLECTION_MAPPING_CBID = "CREATE INDEX IDX_COLLECTION_MAPPING_CBID "
            + " ON " + CollectionMappingColumns.TABLE_NAME + " ( " + CollectionMappingColumns.CBID + " );";

    public static final String SQL_CREATE_TABLE_COMIC = "CREATE TABLE IF NOT EXISTS "
            + ComicColumns.TABLE_NAME + " ( "
            + ComicColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ComicColumns.CBID + " TEXT, "
            + ComicColumns.STORY_TITLE + " TEXT, "
            + ComicColumns.COVER_ART + " TEXT, "
            + ComicColumns.PUBLISHER + " TEXT, "
            + ComicColumns.SERIES + " TEXT, "
            + ComicColumns.ISSUE + " INTEGER, "
            + ComicColumns.ARCHIVE_FILE + " TEXT, "
            + ComicColumns.IMAGE_DIRECTORY + " TEXT, "
            + ComicColumns.IS_DELETED + " INTEGER, "
            + ComicColumns.LAST_PAGE_READ + " INTEGER DEFAULT 0 "
            + " );";

    public static final String SQL_CREATE_INDEX_COMIC_CBID = "CREATE INDEX IDX_COMIC_CBID "
            + " ON " + ComicColumns.TABLE_NAME + " ( " + ComicColumns.CBID + " );";

    public static final String SQL_CREATE_INDEX_COMIC_ARCHIVE_FILE = "CREATE INDEX IDX_COMIC_ARCHIVE_FILE "
            + " ON " + ComicColumns.TABLE_NAME + " ( " + ComicColumns.ARCHIVE_FILE + " );";

    public static final String SQL_CREATE_TABLE_COMIC_READER = "CREATE TABLE IF NOT EXISTS "
            + ComicReaderColumns.TABLE_NAME + " ( "
            + ComicReaderColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ComicReaderColumns.CBID + " TEXT, "
            + ComicReaderColumns.ARCHIVE_FILE + " TEXT, "
            + ComicReaderColumns.PAGE + " INTEGER, "
            + ComicReaderColumns.PAGE_IMAGE + " TEXT "
            + " );";

    public static final String SQL_CREATE_INDEX_COMIC_READER_CBID = "CREATE INDEX IDX_COMIC_READER_CBID "
            + " ON " + ComicReaderColumns.TABLE_NAME + " ( " + ComicReaderColumns.CBID + " );";

    public static final String SQL_CREATE_INDEX_COMIC_READER_ARCHIVE_FILE = "CREATE INDEX IDX_COMIC_READER_ARCHIVE_FILE "
            + " ON " + ComicReaderColumns.TABLE_NAME + " ( " + ComicReaderColumns.ARCHIVE_FILE + " );";

    // @formatter:on

    public static ComicsSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static ComicsSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static ComicsSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new ComicsSQLiteOpenHelper(context);
    }

    private ComicsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new ComicsSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ComicsSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new ComicsSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ComicsSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new ComicsSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ACCOUNTS);
        db.execSQL(SQL_CREATE_INDEX_ACCOUNTS_BITID);
        db.execSQL(SQL_CREATE_TABLE_COLLECTION);
        db.execSQL(SQL_CREATE_INDEX_COLLECTION_CID);
        db.execSQL(SQL_CREATE_INDEX_COLLECTION_NAME);
        db.execSQL(SQL_CREATE_INDEX_COLLECTION_TYPE);
        db.execSQL(SQL_CREATE_TABLE_COLLECTION_MAPPING);
        db.execSQL(SQL_CREATE_INDEX_COLLECTION_MAPPING_CID);
        db.execSQL(SQL_CREATE_INDEX_COLLECTION_MAPPING_CBID);
        db.execSQL(SQL_CREATE_TABLE_COMIC);
        db.execSQL(SQL_CREATE_INDEX_COMIC_CBID);
        db.execSQL(SQL_CREATE_INDEX_COMIC_ARCHIVE_FILE);
        db.execSQL(SQL_CREATE_TABLE_COMIC_READER);
        db.execSQL(SQL_CREATE_INDEX_COMIC_READER_CBID);
        db.execSQL(SQL_CREATE_INDEX_COMIC_READER_ARCHIVE_FILE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
