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

import com.skubit.comics.ArchiveType;
import com.skubit.comics.ComicArchiveInfo;
import com.skubit.comics.loaders.responses.ArchiveScannerResponse;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveContentValues;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveSelection;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class ArchiveScannerLoader extends BaseLoader<ArchiveScannerResponse> {

    private static final String[] ACCEPTS = new String[]{"cbz"};

    private final File mRoot;

    public ArchiveScannerLoader(Context context, File root) {
        super(context);
        mRoot = root;
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
        File[] children = root.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                comics.addAll(getComicArchives(child));
            } else {
                ComicArchiveInfo info = accept(root, child.getName());
                if (info != null) {
                    comics.add(info);
                }
            }
        }
        return comics;
    }

    @Override
    public ArchiveScannerResponse loadInBackground() {

        ArrayList<ComicArchiveInfo> comics = getComicArchives(mRoot);
        for (ComicArchiveInfo info : comics) {
            ComicsArchiveSelection ks = new ComicsArchiveSelection();
            ks.archiveFile(info.archiveFile);

            ComicsArchiveContentValues ccv = new ComicsArchiveContentValues();
            ccv.putArchiveFile(info.archiveFile);
            ccv.putStoryTitle(info.storyTitle);

            if (ccv.update(mContext.getContentResolver(), ks) != 1) {
                ccv.insert(mContext.getContentResolver());
            }
        }

        mResponse = new ArchiveScannerResponse();
        mResponse.comicArchives = comics;
        return mResponse;
    }
}
