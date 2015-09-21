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

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.skubit.comics.archive.ArchiveManager;
import com.skubit.comics.CodeGenerator;
import com.skubit.comics.archive.AlphanumComparator;
import com.skubit.comics.archive.ArchiveUtils;
import com.skubit.comics.archive.responses.CbzResponse;
import com.skubit.comics.loaders.BaseLoader;
import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicSelection;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;
import com.skubit.comics.provider.comicreader.ComicReaderContentValues;
import com.skubit.shared.dto.ArchiveFormat;

import android.content.ContentValues;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class CbrLoader extends BaseLoader<CbzResponse> {

    private final File mArchiveFile;

    private final File mDestDir;

    private Archive mArchive;

    public CbrLoader(Context context, File archiveFile, File destDir) {
        super(context);
        mArchiveFile = archiveFile;
        mDestDir = destDir;
    }

    @Override
    public CbzResponse loadInBackground() {
        final HashMap<String, FileHeader> compressionEntries = new HashMap<>(
                100);
        mArchive = ArchiveUtils.readFileHeaders(mArchiveFile, compressionEntries);

        List<String> orderedEntries = new ArrayList<>();
        orderedEntries.addAll(compressionEntries.keySet());
        Collections.sort(orderedEntries, new AlphanumComparator());

        if(orderedEntries.isEmpty()) {
            return new CbzResponse();
        }

        ContentValues[] values = new ContentValues[orderedEntries.size()];

        for (int i = 0; i < orderedEntries.size(); i++) {
            String archiveFileEntry = orderedEntries.get(i).replaceAll("\\\\", "/");

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
        ccv.putArchiveFile(mArchiveFile.getAbsolutePath());
        ccv.putArchiveFormat(ArchiveFormat.CBR.name());
        if (ccv.update(mContext.getContentResolver(), ks) != 1) {
            ccv.putCbid(CodeGenerator.generateCode(6));
            ccv.insert(mContext.getContentResolver());
        }
        ArchiveManager zm = ArchiveManager.getInstance();
        zm.unrar(mArchiveFile, mDestDir);

        return new CbzResponse();
    }

    protected void closeStream() throws IOException {
       if(mArchive != null) {
           mArchive.close();
       }
    }
}
