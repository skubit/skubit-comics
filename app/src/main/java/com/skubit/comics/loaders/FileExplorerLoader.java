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

import com.skubit.comics.loaders.responses.FileExplorerResponse;

import android.content.Context;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class FileExplorerLoader extends BaseLoader<FileExplorerResponse> {

    private final File mRoot;

    public FileExplorerLoader(Context context, File root) {
        super(context);
        mRoot = root;
    }

    @Override
    protected void closeStream() throws IOException {
        //noop
    }

    @Override
    public FileExplorerResponse loadInBackground() {
        FileExplorerResponse response = new FileExplorerResponse();
        response.directories = mRoot.listFiles(new DirectoryFileTypes());
        return response;
    }

    private class DirectoryFileTypes implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {
            return new File(dir, filename).isDirectory();
        }
    }
}
