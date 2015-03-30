package com.skubit.comics;

import com.skubit.AccountSettings;
import com.skubit.comics.loaders.ComicDetailsLoader;
import com.skubit.comics.loaders.DownloadComicLoader;
import com.skubit.comics.services.LockerService;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.LockerItemDto;
import com.skubit.shared.dto.LockerItemListDto;
import com.skubit.shared.dto.UrlDto;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;

import java.util.ArrayList;

public class LockerUpdaterService extends IntentService {

    private LockerService mLockerService;

    private DownloadManager mDownloadManager;

    private int count = 0;

    public LockerUpdaterService() {
        super("LockerUpdaterService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String account = AccountSettings.get(getBaseContext()).retrieveBitId();
        mLockerService = new LockerService(account, getBaseContext());
        mDownloadManager = (DownloadManager) getBaseContext().getSystemService(
                Context.DOWNLOAD_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        for (int i = 0; i < 100; i++) {
            LockerItemListDto dto = mLockerService.getRestService()
                    .getLockerItems(BuildConfig.APPLICATION_ID, 0, 500, null, Utils.isExplicitCatalog());
            ArrayList<LockerItemDto> items = dto.getItems();
            if (i == 0) {
                count = items.size();
                continue;
            }
            if (items.size() > count) {
                //TODO: get downloadURL
                //TODO: reset i = 0
                //TODO: download

                final String cbid = dto.getItems().get(0).getProductId();
                DownloadComicLoader downloader = new DownloadComicLoader(getBaseContext(), cbid);
                downloader.registerListener(cbid.hashCode(),
                        new Loader.OnLoadCompleteListener<LoaderResult<UrlDto>>() {

                            @Override
                            public void onLoadComplete(Loader<LoaderResult<UrlDto>> loader,
                                    LoaderResult<UrlDto> data) {
                                loadDetails(cbid, data.result.getUrl());
                            }
                        });
                downloader.startLoading();
                count++;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadDetails(final String cbid, final String url) {
        ComicDetailsLoader comicLoader = new ComicDetailsLoader(getBaseContext(), cbid);
        comicLoader.registerListener(cbid.hashCode(),
                new Loader.OnLoadCompleteListener<ComicBookDto>() {

                    @Override
                    public void onLoadComplete(Loader<ComicBookDto> loader, ComicBookDto data) {
                        Utils.download(url, data, mDownloadManager);
                          }
                });
        comicLoader.startLoading();
    }
}
