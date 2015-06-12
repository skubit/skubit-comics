package com.skubit.comics.loaders;

import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingCursor;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comic.ComicSelection;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;

/**
 * : threadid=11: thread exiting with uncaught exception (group=0x41e68c08)
 * D/StatusBarManagerService( 2436): semi p:23528,o:f
 * E/AndroidRuntime(23528): FATAL EXCEPTION: AsyncTask #1
 * E/AndroidRuntime(23528): Process: com.skubit.comics, PID: 23528
 * E/AndroidRuntime(23528): java.lang.RuntimeException: An error occured while executing
 * doInBackground()
 * E/AndroidRuntime(23528): 	at android.os.AsyncTask$3.done(AsyncTask.java:300)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.FutureTask.finishCompletion(FutureTask.java:355)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.FutureTask.setException(FutureTask.java:222)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.FutureTask.run(FutureTask.java:242)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
 * E/AndroidRuntime(23528): 	at java.lang.Thread.run(Thread.java:841)
 * E/AndroidRuntime(23528): Caused by: java.lang.IllegalArgumentException: the bind value at index 1
 * is null
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteProgram.bindString(SQLiteProgram.java:164)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteProgram.bindAllArgsAsStrings(SQLiteProgram.java:200)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteDirectCursorDriver.query(SQLiteDirectCursorDriver.java:47)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteDatabase.rawQueryWithFactory(SQLiteDatabase.java:1448)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteDatabase.queryWithFactory(SQLiteDatabase.java:1295)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteDatabase.query(SQLiteDatabase.java:1166)
 * E/AndroidRuntime(23528): 	at android.database.sqlite.SQLiteDatabase.query(SQLiteDatabase.java:1372)
 * E/AndroidRuntime(23528): 	at com.skubit.comics.provider.base.BaseContentProvider.query(BaseContentProvider.java:131)
 * E/AndroidRuntime(23528): 	at com.skubit.comics.provider.ComicsProvider.query(ComicsProvider.java:135)
 * E/AndroidRuntime(23528): 	at android.content.ContentProvider.query(ContentProvider.java:857)
 * E/AndroidRuntime(23528): 	at android.content.ContentProvider$Transport.query(ContentProvider.java:200)
 * E/AndroidRuntime(23528): 	at android.content.ContentResolver.query(ContentResolver.java:464)
 * E/AndroidRuntime(23528): 	at android.content.ContentResolver.query(ContentResolver.java:407)
 * E/AndroidRuntime(23528): 	at com.skubit.comics.loaders.CollectionOfComicsLoader.loadInBackground(CollectionOfComicsLoader.java:33)
 * E/AndroidRuntime(23528): 	at com.skubit.comics.loaders.CollectionOfComicsLoader.loadInBackground(CollectionOfComicsLoader.java:14)
 * E/AndroidRuntime(23528): 	at android.content.AsyncTaskLoader.onLoadInBackground(AsyncTaskLoader.java:312)
 * E/AndroidRuntime(23528): 	at android.content.AsyncTaskLoader$LoadTask.doInBackground(AsyncTaskLoader.java:69)
 * E/AndroidRuntime(23528): 	at android.content.AsyncTaskLoader$LoadTask.doInBackground(AsyncTaskLoader.java:57)
 * E/AndroidRuntime(23528): 	at android.os.AsyncTask$2.call(AsyncTask.java:288)
 * E/AndroidRuntime(23528): 	at java.util.concurrent.FutureTask.run(FutureTask.java:237)
 * E/AndroidRuntime(23528): 	... 3 more
 * W/ActivityManager( 2436):   Force finishing activity com.skubit.comics/.activities.CollectionFilterActivity
 * W/ActivityManager( 2436):   Force finishing activity com.skubit.comics/.activities.MainActivity
 */
public class CollectionOfComicsLoader extends BaseLoader<Cursor> {

    private final String mCid;

    public CollectionOfComicsLoader(Context context, String cid) {
        super(context);
        mCid = cid;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public Cursor loadInBackground() {
        //NPE
        Cursor collectionCursor =
                mContext.getContentResolver().query(CollectionMappingColumns.CONTENT_URI, null,
                        CollectionMappingColumns.CID + " =?",
                        new String[]{mCid}, null);

        CollectionMappingCursor collectionMappingCursor = new CollectionMappingCursor(collectionCursor);
        String[] comicIds = new String[collectionCursor.getCount()];
        int length = comicIds.length;
        for (int i = 0; i < length; i++) {
            collectionMappingCursor.moveToPosition(i);
            comicIds[i] = collectionMappingCursor.getCbid();
        }
        collectionMappingCursor.close();
        collectionCursor.close();

        ComicSelection where = new ComicSelection();
        if (comicIds.length > 0) {
            where = where.cbid(comicIds);
        }
        Cursor c = mContext.getContentResolver().query(ComicColumns.CONTENT_URI, null,
                where.sel(), where.args(), null);

        return c;
    }
}
