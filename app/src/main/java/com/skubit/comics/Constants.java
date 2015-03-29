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
package com.skubit.comics;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String LOCKER_URI = "https://catalog.skubit.net/rest/v1/locker";

    public static final String COMICS_CATALOG_URI = "https://catalog.skubit.net/rest/comicbooks";

    public static final File SKUBIT_ARCHIVES = Environment.getExternalStorageDirectory();

    public static final File SKUBIT_ARCHIVES_DOWNLOAD = new File(
            Environment.getExternalStorageDirectory(),
            "SkubitComics/archives");

    public static final File SKUBIT_UNARCHIVES = new File(Environment.getExternalStorageDirectory(),
            "SkubitComics/unarchive");

    public static final String ARCHIVE_SCAN_COMPLETE = "com.skubit.comics.ARCHIVE_SCAN_COMPLETE";
}
