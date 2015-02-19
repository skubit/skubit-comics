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
import com.skubit.comics.provider.archiveroots.ArchiveRootsColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;

public class ComicsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ComicsSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "comics.db";
    private static final int DATABASE_VERSION = 1;
    private static ComicsSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final ComicsSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ARCHIVE_ROOTS = "CREATE TABLE IF NOT EXISTS "
            + ArchiveRootsColumns.TABLE_NAME + " ( "
            + ArchiveRootsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ArchiveRootsColumns.ARCHIVE_ROOT + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_COMICS_ARCHIVE = "CREATE TABLE IF NOT EXISTS "
            + ComicsArchiveColumns.TABLE_NAME + " ( "
            + ComicsArchiveColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ComicsArchiveColumns.ARCHIVE_FILE + " TEXT, "
            + ComicsArchiveColumns.COVER_ART + " TEXT, "
            + ComicsArchiveColumns.STORY_TITLE + " TEXT, "
            + ComicsArchiveColumns.UNARCHIVED_PAGES + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_COMICS_INFO = "CREATE TABLE IF NOT EXISTS "
            + ComicsInfoColumns.TABLE_NAME + " ( "
            + ComicsInfoColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ComicsInfoColumns.ARCHIVE_FILE + " TEXT, "
            + ComicsInfoColumns.ARCHIVE_FILE_ENTRY + " TEXT, "
            + ComicsInfoColumns.PAGE + " INTEGER, "
            + ComicsInfoColumns.PAGE_IMAGE + " TEXT "
            + " );";

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
        db.execSQL(SQL_CREATE_TABLE_ARCHIVE_ROOTS);
        db.execSQL(SQL_CREATE_TABLE_COMICS_ARCHIVE);
        db.execSQL(SQL_CREATE_TABLE_COMICS_INFO);
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
