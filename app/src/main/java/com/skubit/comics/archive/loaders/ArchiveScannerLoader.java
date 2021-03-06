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
import com.skubit.comics.CodeGenerator;
import com.skubit.comics.Constants;
import com.skubit.comics.archive.AlphanumComparator;
import com.skubit.comics.archive.ArchiveManager;
import com.skubit.comics.archive.ArchiveType;
import com.skubit.comics.archive.ArchiveUtils;
import com.skubit.comics.archive.ComicArchiveInfo;
import com.skubit.comics.archive.responses.ArchiveScannerResponse;
import com.skubit.comics.loaders.BaseLoader;
import com.skubit.comics.provider.collection.CollectionContentValues;
import com.skubit.comics.provider.collection.CollectionCursor;
import com.skubit.comics.provider.collection.CollectionSelection;
import com.skubit.comics.provider.collectionmapping.CollectionMappingContentValues;
import com.skubit.comics.provider.collectionmapping.CollectionMappingSelection;
import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicCursor;
import com.skubit.comics.provider.comic.ComicSelection;
import com.skubit.comics.provider.comicreader.ComicReaderColumns;
import com.skubit.comics.provider.comicreader.ComicReaderContentValues;
import com.skubit.shared.dto.ArchiveFormat;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ArchiveScannerLoader extends BaseLoader<ArchiveScannerResponse> {

    private static final String[] ACCEPTS = new String[]{"cbz", "cbr", "elcx"};

    private final File mRoot;

    private final boolean mFirstOnly;

    public ArchiveScannerLoader(Context context, File root, boolean firstOnly) {
        super(context);
        mRoot = root;
        mFirstOnly = firstOnly;
    }

    private static ComicArchiveInfo createComicArchiveInfo(File dir, String filename, int index,
            String extension) {
        ComicArchiveInfo comicInfo = new ComicArchiveInfo();
        comicInfo.archiveFile = new File(dir, filename).getAbsolutePath();
        comicInfo.storyTitle = filename.substring(0, index).replace("_", " ");
        comicInfo.archiveType = ArchiveType.fromString(extension);
        return comicInfo;
    }

    private static ComicArchiveInfo accept(File dir, String filename) {
        int index = filename.lastIndexOf(".");
        if (index > -1) {
            String extension = filename.substring(index + 1).toLowerCase();
            for (String accept : ACCEPTS) {
                if (accept.equals(extension)) {
                    return createComicArchiveInfo(dir, filename, index, extension);
                }
            }
        }
        return null;
    }


    @Override
    protected void closeStream() throws IOException {
        //noop
    }

    private ArrayList<ComicArchiveInfo> getComicArchives(File root) {
        ArrayList<ComicArchiveInfo> comics = new ArrayList<>();
        if (!mFirstOnly && root.toString().contains("SkubitComics")) {
            return comics;
        }

        File[] children = root.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                comics.addAll(getComicArchives(child));
            } else {
                ComicArchiveInfo info = accept(root, child.getName());
                if (info != null) {
                    comics.add(info);

                    if (mFirstOnly) {
                        break;
                    }
                }
            }
        }
        return comics;
    }

    public void putCoverAsFirstPage(File archiveFile, File coverArt) {
        ComicReaderContentValues cv = new ComicReaderContentValues();
        cv.putPage(0);
        cv.putPageImage(coverArt.getAbsolutePath());
        cv.putArchiveFile(archiveFile.getAbsolutePath());

        getContext().getContentResolver().insert(ComicReaderColumns.CONTENT_URI, cv.values());
    }

    @Override
    public ArchiveScannerResponse loadInBackground() {
        HashMap<String, String> collections = new HashMap<>();//name,cid
        ArrayList<ComicArchiveInfo> comics = getComicArchives(mRoot);
        for (ComicArchiveInfo info : comics) {
            final File archive = new File(info.archiveFile);
            File destDir = new File(Constants.SKUBIT_UNARCHIVES, archive.getName());
            File coverArt = loadCoverArtAndUnarchiveFile(destDir, archive, info.archiveType);
            if(coverArt != null) {
                putCoverAsFirstPage(archive, coverArt);
            }

            //TODO: Load up first page of viewer

            ComicSelection ks = new ComicSelection();
            ks.archiveFile(info.archiveFile);

            ComicContentValues ccv = new ComicContentValues();
            ccv.putArchiveFile(info.archiveFile);
            ccv.putStoryTitle(info.storyTitle);
            if(coverArt != null) {
                ccv.putCoverArt(coverArt.getAbsolutePath());
            }

            ccv.putAccessDate(new Date());
            ccv.putDownloadDate(new Date());

            String fileName = info.archiveFile.toLowerCase();

            if (fileName.contains("sample")) {
                ccv.putIsSample(true);
            }

            if (fileName.endsWith(".cbz")) {
                ccv.putArchiveFormat(ArchiveFormat.CBZ.name());
            } else if (fileName.endsWith(".cbr")) {
                ccv.putArchiveFormat(ArchiveFormat.CBR.name());
            } else if (fileName.endsWith(".elcx")) {
                ccv.putArchiveFormat(ArchiveFormat.ELCX.name());
            } else if (fileName.endsWith(".mp3")) {
                ccv.putArchiveFormat(ArchiveFormat.VOICE.name());
            }

            String cbid;
            if (ccv.update(mContext.getContentResolver(), ks) != 1) {
                cbid = CodeGenerator.generateCode(10);
                ccv.putCbid(cbid);
                ccv.insert(mContext.getContentResolver());
            } else {
                final ComicCursor c = ks.query(mContext.getContentResolver());
                c.moveToFirst();
                cbid = c.getCbid();
                c.close();
            }

            String name = archive.getParentFile().getName();
            if (!collections.containsKey(name)) {
                if (archive.getParent()
                        .equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    name = "Default";
                }
                CollectionContentValues cv = new CollectionContentValues();
                cv.putCoverart(coverArt.getAbsolutePath());
                cv.putName(name);

                CollectionSelection where = new CollectionSelection();
                where.name(name);

                final CollectionCursor c = where.query(mContext.getContentResolver());
                String cid;
                if (c.getCount() == 0) {
                    cid = CodeGenerator.generateCode(6);
                    cv.putCid(cid);
                    cv.insert(mContext.getContentResolver());
                } else {
                    c.moveToFirst();
                    cid = c.getCid();
                }
                c.close();
                collections.put(name, cid);
            }
            updateCollectionMapping(collections.get(name), cbid);
        }

        mResponse = new ArchiveScannerResponse();
        mResponse.comicArchives = comics;
        return mResponse;
    }

    private void updateCollectionMapping(String cid, String cbid) {
        CollectionMappingSelection cms = new CollectionMappingSelection();
        Cursor collectionMappingCursor = cms.cid(cid).and().cbid(cbid)
                .query(mContext.getContentResolver());
        if (collectionMappingCursor.getCount() == 0) {
            CollectionMappingContentValues cmap = new CollectionMappingContentValues();
            cmap.putCid(cid);
            cmap.putCbid(cbid);
            cmap.insert(mContext.getContentResolver());

        }
        collectionMappingCursor.close();
    }

    /**
     * Loads cover art and unarchives file if needed
     *
     * @param destDir - unarchive directory
     * @param archiveFile - archive file
     * @param archiveType - archive type (CBZ, CBR, ELCX, MP3)
     *
     * @return cover art file
     */
    public File loadCoverArtAndUnarchiveFile(File destDir, File archiveFile,
            ArchiveType archiveType) {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        if (ArchiveType.CBZ.equals(archiveType)) {
            final HashMap<String, ZipEntry> compressionEntries = new HashMap<>(
                    100);
            ZipFile zipFile = ArchiveUtils.readZipEntries(archiveFile, compressionEntries);

            List<String> orderedEntries = new ArrayList<String>();
            orderedEntries.addAll(compressionEntries.keySet());
            Collections.sort(orderedEntries, new AlphanumComparator());
            if(orderedEntries.isEmpty()) {
                return null;
            }

            ArchiveManager zm = ArchiveManager.getInstance();
            zm.unzip(zipFile, compressionEntries.get(orderedEntries.get(0)), destDir);

            return new File(destDir, orderedEntries.get(0));
        } else if (ArchiveType.CBR.equals(archiveType)) {
            final HashMap<String, FileHeader> compressionEntries = new HashMap<>(
                    100);
            Archive archive = ArchiveUtils.readFileHeaders(archiveFile, compressionEntries);

            List<String> orderedEntries = new ArrayList<>();
            orderedEntries.addAll(compressionEntries.keySet());
            Collections.sort(orderedEntries, new AlphanumComparator());
            if(orderedEntries.isEmpty()) {
                return null;
            }

            ArchiveManager zm = ArchiveManager.getInstance();
            return zm.unrar(archive, compressionEntries.get(orderedEntries.get(0)), destDir);
        } else if (ArchiveType.ELCX.equals(archiveType)) {
            final HashMap<String, ZipEntry> compressionEntries = new HashMap<>(
                    100);
            ZipFile zipFile = ArchiveUtils.readZipEntriesForElectric(archiveFile,
                    compressionEntries);

            List<String> orderedEntries = new ArrayList<>();
            orderedEntries.addAll(compressionEntries.keySet());
            Collections.sort(orderedEntries, new AlphanumComparator());
            if(orderedEntries.isEmpty()) {
                return null;
            }

            ArchiveManager zm = ArchiveManager.getInstance();
            for(ZipEntry ze : compressionEntries.values()) {
                zm.unzip(zipFile, ze, destDir, false);
            }
            return cover(destDir, orderedEntries);

       /*
            File index = index(destDir);
            if(index != null) {
                File cover = cover(index.getParentFile());
                if(cover != null) {
                    return cover;
                }

                File images = new File(index.getParentFile(), "images");
                if(images.exists()) {
                    File[] imageFiles =  images.listFiles();
                    if(imageFiles.length > 0) {
                        return imageFiles[0];
                    }
                }
            }
            */

        } else if (ArchiveType.MP3.equals(archiveType)) {

        }

        return null;//should never happen
    }

    private File cover(File destDir, List<String> orderedEntries)  {
        for(String entry : orderedEntries) {
            if(entry.toLowerCase().contains("cover.")) {
              return new File(destDir, entry);
            }
        }
        for(String entry : orderedEntries) {
            if(entry.contains("images") && !entry.endsWith("images") && !entry.endsWith("images/")
                    && !entry.endsWith("elcxlogosmall.png")) {
                return new File(destDir, entry);
            }
        }
        return null;
    }

    private File cover(File root) {
        for(File file : root.listFiles()) {
            if(file.getName().toLowerCase().startsWith("cover.")) {
                return file;
            }
        }
        return null;
    }

    private File index(File root) {
        File[] files = root.listFiles();
        for(File file : files) {
            if(file.isFile() && file.getName().toLowerCase().startsWith("index.")) {
                return file;
            } else if(file.isDirectory()) {
                File f = index(file);
                if(f != null) {
                    return f;
                }
            }
        }
        return null;
    }

}
