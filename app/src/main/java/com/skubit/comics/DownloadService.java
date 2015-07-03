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
package com.skubit.comics;

import com.skubit.comics.archive.loaders.ArchiveScannerLoader;
import com.skubit.comics.archive.responses.ArchiveScannerResponse;
import com.skubit.comics.loaders.ComicDetailsLoader;
import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicSelection;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicBookDto;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.Date;

public class
        DownloadService extends IntentService implements
        Loader.OnLoadCompleteListener<ArchiveScannerResponse> {

    private DownloadManager mDownloadManager;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = (DownloadManager) getSystemService(
                Context.DOWNLOAD_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        String fileName = getFileUri(downloadId);
        if (TextUtils.isEmpty(fileName)) {;
            return;
        }
        if (fileName.toLowerCase().endsWith(".cbz")) {
            File parent = new File(fileName).getParentFile();
            ArchiveScannerLoader loader = new ArchiveScannerLoader(getBaseContext(), parent, true);
            loader.registerListener(fileName.hashCode(), this);
            loader.startLoading();
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            File file = new File(fileName);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent = Intent.createChooser(target, "Open File");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {

            }
        }
    }

    private String getFileUri(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c = mDownloadManager.query(query);
        if (c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                return c.getString(c
                        .getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
            }
        }
        return null;
    }


    @Override
    public void onLoadComplete(Loader<ArchiveScannerResponse> loader, ArchiveScannerResponse data) {
        if (data != null && data.comicArchives != null && data.comicArchives.size() > 0) {
            final File archive = new File(data.comicArchives.get(0).archiveFile);
            String cbid = archive.getParentFile().getName();

            ComicDetailsLoader comicLoader = new ComicDetailsLoader(getBaseContext(), cbid);
            comicLoader.registerListener(cbid.hashCode(),
                    new Loader.OnLoadCompleteListener<ComicBookDto>() {

                        @Override
                        public void onLoadComplete(Loader<ComicBookDto> loader, ComicBookDto data) {
                            updateComicInfo(data, archive);
                            Utils.doDownloadedNotification(getBaseContext(), data);
                        }
                    });
            comicLoader.startLoading();
        }
    }

    private void updateComicInfo(ComicBookDto data, File archive) {
        ComicSelection ks = new ComicSelection();
        ks.archiveFile(archive.getAbsolutePath());

        ComicContentValues ccv = new ComicContentValues();
        ccv.putCbid(data.getCbid());
        ccv.putIssue(data.getIssueNumber());
        ccv.putPublisher(data.getPublisher());
        ccv.putArchiveFile(archive.getAbsolutePath());
        ccv.putDownloadDate(new Date());
        ccv.putAccessDate(new Date());

        if(!TextUtils.isEmpty(data.getGenre())) {
            ccv.putGenre(data.getGenre());
        }

        if(data.getAgeRating() != null) {
            ccv.putAgeRating(data.getAgeRating().name());
        }

        if(!TextUtils.isEmpty(data.getLanguage().name())) {
            ccv.putLanguage(data.getLanguage().name());
        }

        if(!TextUtils.isEmpty(data.getSeries())) {
            ccv.putSeries(data.getSeries());
        }

        if(!TextUtils.isEmpty(data.getVolume())) {
            ccv.putVolume(data.getVolume());
        }

        String fileName = archive.getName().toLowerCase();
        ccv.putIsSample(fileName.contains("sample"));

        if (fileName.endsWith(".cbz")) {
            ccv.putArchiveFormat(ArchiveFormat.CBZ.name());
        } else if(fileName.endsWith(".pdf")) {
            ccv.putArchiveFormat(ArchiveFormat.PDF.name());
        }

        if (ccv.update(getBaseContext().getContentResolver(), ks) != 1) {
            ccv.insert(getBaseContext().getContentResolver());
        }
    }
}
