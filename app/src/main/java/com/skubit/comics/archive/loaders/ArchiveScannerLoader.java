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
import com.skubit.comics.Constants;
import com.skubit.comics.archive.AlphanumComparator;
import com.skubit.comics.archive.ArchiveType;
import com.skubit.comics.archive.ComicArchiveInfo;
import com.skubit.comics.archive.ZipManager;
import com.skubit.comics.archive.responses.ArchiveScannerResponse;
import com.skubit.comics.loaders.BaseLoader;
import com.skubit.comics.provider.comic.ComicContentValues;
import com.skubit.comics.provider.comic.ComicSelection;

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

public final class ArchiveScannerLoader extends BaseLoader<ArchiveScannerResponse> {

    private static final String[] ACCEPTS = new String[]{"cbz"};

    private static final String[] ACCEPTS_MEDIA = new String[]{"jpeg", "jpg", "png"};

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

    private static boolean hasExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index > -1) {
            String extension = filename.substring(index + 1).toLowerCase();
            for (String accept : ACCEPTS_MEDIA) {
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

        }
        return zipfile;
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

    @Override
    public ArchiveScannerResponse loadInBackground() {

        ArrayList<ComicArchiveInfo> comics = getComicArchives(mRoot);
        for (ComicArchiveInfo info : comics) {
            final File archive = new File(info.archiveFile);

            File destDir = new File(Constants.SKUBIT_UNARCHIVES, archive.getName());
            File coverArt = loadCoverArt(destDir, archive);

            ComicSelection ks = new ComicSelection();
            ks.archiveFile(info.archiveFile);

            ComicContentValues ccv = new ComicContentValues();
            ccv.putArchiveFile(info.archiveFile);
            ccv.putStoryTitle(info.storyTitle);
            ccv.putCoverArt(coverArt.getAbsolutePath());

            if (ccv.update(mContext.getContentResolver(), ks) != 1) {
                ccv.putCbid(CodeGenerator.generateCode(6));
                ccv.insert(mContext.getContentResolver());
            }
        }

        mResponse = new ArchiveScannerResponse();
        mResponse.comicArchives = comics;
        return mResponse;
    }

    public File loadCoverArt(File mDestDir, File mArchiveFile) {
        if (!mDestDir.exists()) {
            mDestDir.mkdirs();
        }
        final HashMap<String, ZipEntry> compressionEntries = new HashMap<String, ZipEntry>(
                100);

        ZipFile zipFile = readEntries(mArchiveFile, compressionEntries);

        List<String> orderedEntries = new ArrayList<String>();
        orderedEntries.addAll(compressionEntries.keySet());
        Collections.sort(orderedEntries, new AlphanumComparator());

        ZipManager zm = ZipManager.getInstance();
        zm.unzip(zipFile, compressionEntries.get(orderedEntries.get(0)), mDestDir);

        return new File(mDestDir, orderedEntries.get(0));
    }
}
