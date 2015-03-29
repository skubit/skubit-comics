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
package com.skubit.comics.archive;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ZipManager {

    private static final int KEEP_ALIVE_TIME = 5;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static final String[] ACCEPTS = new String[]{"jpeg", "jpg", "png"};

    private static ZipManager sInstance = null;

    static {
        sInstance = new ZipManager();
    }

    private final ThreadPoolExecutor mThreadPool;

    private int NUMBER_OF_CORES = Math
            .max(1, Runtime.getRuntime().availableProcessors() - 1);

    private ZipManager() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        mThreadPool = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue);
    }

    public static ZipManager getInstance() {
        return sInstance;
    }

    private static void createDir(File dir) throws IOException {
        if (!dir.mkdirs()) {
            throw new IOException("Can not create dir " + dir);
        }
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

    private static void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir)
            throws IOException {
        if (Thread.interrupted()) {
            return;
        }

        if (entry.isDirectory() || !hasExtension(entry.getName())) {
            return;
        }

        File outputFile = new File(outputDir, entry.getName());
        if (!outputFile.getParentFile().exists()) {
            createDir(outputFile.getParentFile());
        }

        ReadableByteChannel readableByteChannel = Channels
                .newChannel(zipfile.getInputStream(entry));
        WritableByteChannel writableByteChannel = Channels
                .newChannel(new FileOutputStream(outputFile));

        try {
            ByteStreams.copy(readableByteChannel, writableByteChannel);
        } finally {
            readableByteChannel.close();
            writableByteChannel.close();
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unzip(final ZipFile zipFile, final ZipEntry entry, final File outputDir) {
        mThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    unzipEntry(zipFile, entry, outputDir);
                } catch (IOException e) {

                }
            }
        });

    }

}
