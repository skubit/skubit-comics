/* Copyright 2015 Skubit
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
package com.skubit.comics.archive.loaders;

import com.skubit.comics.CodeGenerator;
import com.skubit.comics.archive.AlphanumComparator;
import com.skubit.comics.archive.ArchiveManager;
import com.skubit.comics.archive.ArchiveUtils;
import com.skubit.comics.archive.responses.CbzResponse;
import com.skubit.comics.loaders.BaseLoader;
import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicSelection;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;
import com.skubit.comics.provider.comicreader.ComicReaderContentValues;

import android.content.ContentValues;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class CbzLoader extends BaseLoader<CbzResponse> {

    private final File mArchiveFile;

    private final File mDestDir;

    private ZipFile mZipFile;

    public CbzLoader(Context context, File archiveFile, File destDir) {
        super(context);
        mArchiveFile = archiveFile;
        mDestDir = destDir;
    }

    private static ZipFile readEntries(File archive,
            final HashMap<String, ZipEntry> compressionEntries) {
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(archive);
            for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements(); ) {

                ZipEntry entry = (ZipEntry) e.nextElement();
                if (!entry.getName().contains("META-INF") && !entry.getName().contains("MACOSX")
                        && entry.getCompressedSize() > 20000 && ArchiveUtils
                        .hasExtension(entry.getName())) {
                    compressionEntries.put(entry.getName(), entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipfile;
    }

    @Override
    public CbzResponse loadInBackground() {
        if (!mDestDir.exists()) {
            mDestDir.mkdirs();
        }
        try {
            mZipFile = new ZipFile(mArchiveFile);
        } catch (Exception e) {
            return null;
        }

        final HashMap<String, ZipEntry> compressionEntries = new HashMap<String, ZipEntry>(
                100);

        ZipFile zipFile = readEntries(mArchiveFile, compressionEntries);

        List<String> orderedEntries = new ArrayList<String>();
        orderedEntries.addAll(compressionEntries.keySet());
        Collections.sort(orderedEntries, new AlphanumComparator());

        ContentValues[] values = new ContentValues[orderedEntries.size()];

        for (int i = 0; i < orderedEntries.size(); i++) {
            String archiveFileEntry = orderedEntries.get(i);

            ComicReaderContentValues cv = new ComicReaderContentValues();
            cv.putPage(i);
            cv.putPageImage(new File(mDestDir, archiveFileEntry).getAbsolutePath());
            cv.putArchiveFile(mArchiveFile.getAbsolutePath());
            values[i] = cv.values();
        }
        getContext().getContentResolver()
                .delete(ComicReaderColumns.CONTENT_URI, ComicReaderColumns.ARCHIVE_FILE + " =?",
                        new String[]{mArchiveFile.getAbsolutePath()});

        getContext().getContentResolver().bulkInsert(ComicReaderColumns.CONTENT_URI, values);

        ComicSelection ks = new ComicSelection();
        ks.archiveFile(mArchiveFile.getAbsolutePath());

        ComicContentValues ccv = new ComicContentValues();
        ccv.putCoverArt(new File(mDestDir, orderedEntries.get(0)).getAbsolutePath());
        ccv.putArchiveFile(mArchiveFile.getAbsolutePath());

        if (ccv.update(mContext.getContentResolver(), ks) != 1) {
            ccv.putCbid(CodeGenerator.generateCode(6));
            ccv.insert(mContext.getContentResolver());
        }

        ArchiveManager zm = ArchiveManager.getInstance();
        for (String archiveFileEntry : orderedEntries) {
            zm.unzip(zipFile, compressionEntries.get(archiveFileEntry), mDestDir);
        }

        return new CbzResponse();
    }

    protected void closeStream() throws IOException {
        if (mZipFile != null) {
            mZipFile.close();
        }
    }
}
