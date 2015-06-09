package com.skubit.comics;

import com.skubit.AccountSettings;
import com.skubit.comics.loaders.DownloadComicLoader;
import com.skubit.comics.services.LockerService;
import com.skubit.comics.services.rest.LockerRestService;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.UrlDto;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.IBinder;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService extends Service {

    private LockerService mLockerService;

    private DownloadManager mDownloadManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static long interval = 300000;

    private long timestamp;

    private int sleepTimeSeconds = 5;

    private Lock lock = new ReentrantLock();

    @Override
    public void onCreate() {
        super.onCreate();
        String account = AccountSettings.get(getBaseContext()).retrieveBitId();
        mLockerService = new LockerService(account, getBaseContext());
        mDownloadManager = (DownloadManager) getBaseContext().getSystemService(
                Context.DOWNLOAD_SERVICE);
    }

    private final CopyOnWriteArrayList<ComicData> comicList = new CopyOnWriteArrayList<>();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timestamp = System.currentTimeMillis();
        final LockerRestService service = mLockerService.getRestService();
        if (intent != null) {
            ComicData comicData = intent.getParcelableExtra(ComicData.EXTRA_NAME);
            if (comicData != null) {
                if(!comicList.contains(comicData)) {
                    comicList.add(comicData);
                } else {
                    return Service.START_STICKY;
                }
                Utils.beginOrderNotification(this, comicData.getCbid(), comicData.getTitle());
            }
        }

        try {
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (System.currentTimeMillis() <= timestamp + interval) {
                            Iterator<ComicData> it = comicList.iterator();
                            while (it.hasNext()) {
                                ComicData comicData = it.next();
                                Boolean exists = null;
                                try {
                                    exists = service.itemExists(comicData.getCbid());
                                } catch (Exception e) {

                                }
                                if (exists != null && exists) {
                                    Utils.endOrderNotification(OrderService.this,
                                            comicData.getCbid(),
                                            comicData.getTitle(), false);
                                    downloadComic(comicData);
                                    /*
                                    Dialog option
                                    sendBroadcast(OrderReceiver
                                            .newInstance(comicData.getCbid(),
                                                    comicData.getTitle()));
                                                    */
                                    comicList.remove(comicData);
                                    if (comicList.isEmpty()) {
                                        sleepTimeSeconds = 20;
                                    }
                                }
                            }

                            try {
                                TimeUnit.SECONDS.sleep(sleepTimeSeconds);
                            } catch (InterruptedException e) {

                            }
                        }
                        //TODO: time check
                        cancelPendingOrderNotifications();
                    }
                });
                t.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
            lock.unlock();
        }

        return Service.START_STICKY;
    }

    private void cancelPendingOrderNotifications() {
        Iterator<ComicData> it = comicList.iterator();
        while (it.hasNext()) {
            ComicData comicData = it.next();
            Utils.cancelOrderNotification(OrderService.this,
                    comicData.getCbid(),
                    comicData.getTitle());

        }
    }

    private void downloadComic(final ComicData comicData) {
        DownloadComicLoader downloader = new DownloadComicLoader(getBaseContext(),
                comicData.getCbid(),
                false, ArchiveFormat.CBZ);
        downloader.registerListener(comicData.getCbid().hashCode(),
                new Loader.OnLoadCompleteListener<LoaderResult<UrlDto>>() {

                    @Override
                    public void onLoadComplete(Loader<LoaderResult<UrlDto>> loader,
                            LoaderResult<UrlDto> data) {
                        if (data != null) {
                            if (!TextUtils.isEmpty(data.errorMessage)) {
                                Utils.downloadFailedNotification(getBaseContext(),
                                        comicData.getCbid(), comicData.getTitle());
                            } else if (data.result != null) {
                                Utils.download(getBaseContext(), data.result.getUrl(), "",
                                        comicData,
                                        mDownloadManager);
                            }
                        }

                    }
                });
        downloader.startLoading();

    }
}
