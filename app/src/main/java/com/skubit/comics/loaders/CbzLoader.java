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
package com.skubit.comics.loaders;

import com.skubit.comics.AlphanumComparator;
import com.skubit.comics.ZipManager;
import com.skubit.comics.loaders.responses.CbzResponse;
import com.skubit.comics.provider.comicsinfo.ComicsInfoColumns;
import com.skubit.comics.provider.comicsinfo.ComicsInfoContentValues;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveContentValues;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveSelection;

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

    private static final String[] ACCEPTS = new String[]{"jpeg", "jpg", "png"};

    private final File mArchiveFile;

    private final File mDestDir;

    private ZipFile mZipFile;

    public CbzLoader(Context context, File archiveFile, File destDir) {
        super(context);
        mArchiveFile = archiveFile;
        mDestDir = destDir;
    }

    private static boolean hasExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index > -1) {
            String extension = filename.substring(index + 1).toLowerCase();
            for (String accept : ACCEPTS) {
                if (accept.equals(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ZipFile readEntries(File archive,
            final HashMap<String, ZipEntry> compressionEntries) {
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(archive);
            for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements(); ) {

                ZipEntry entry = (ZipEntry) e.nextElement();
                if (!entry.getName().contains("META-INF") && hasExtension(entry.getName())) {
                    compressionEntries.put(entry.getName(), entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                /*
                try {
                    zipfile.close();
                } catch (IOException e) {

                }
                */
            }
        }
        return zipfile;
    }

    @Override
    public CbzResponse loadInBackground() {
        if(!mDestDir.exists()) {
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

            ComicsInfoContentValues cv = new ComicsInfoContentValues();
            cv.putPage(i);
            cv.putPageImage(new File(mDestDir, archiveFileEntry).getAbsolutePath());
            cv.putArchiveFile(mArchiveFile.getAbsolutePath());
            values[i] = cv.values();
        }

        getContext().getContentResolver().bulkInsert(ComicsInfoColumns.CONTENT_URI, values);

        ComicsArchiveSelection ks = new ComicsArchiveSelection();
        ks.archiveFile(mArchiveFile.getAbsolutePath());

        ComicsArchiveContentValues ccv = new ComicsArchiveContentValues();
        ccv.putCoverArt(new File(mDestDir, orderedEntries.get(0)).getAbsolutePath());
        ccv.putArchiveFile(mArchiveFile.getAbsolutePath());

        if (ccv.update(mContext.getContentResolver(), ks) != 1) {
            ccv.insert(mContext.getContentResolver());
        }

        ZipManager zm = ZipManager.getInstance();
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
