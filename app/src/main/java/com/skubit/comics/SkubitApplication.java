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

import android.app.Application;
import android.os.StrictMode;

import java.io.File;
import java.io.IOException;

public final class SkubitApplication extends Application {
    static {
        System.loadLibrary("unrar");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new FontManager(this);
        /*
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        */
        if (!Constants.SKUBIT_ARCHIVES.exists()) {
            Constants.SKUBIT_ARCHIVES.mkdirs();
        }

        if (!Constants.SKUBIT_UNARCHIVES.exists()) {
            Constants.SKUBIT_UNARCHIVES.mkdirs();
            File nomedia = new File(Constants.SKUBIT_UNARCHIVES, ".nomedia");
            try {
                nomedia.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File nomedia = new File(Constants.SKUBIT_UNARCHIVES, ".nomedia");
        try {
            nomedia.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
