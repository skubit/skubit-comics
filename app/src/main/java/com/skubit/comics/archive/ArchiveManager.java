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

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.github.junrar.unpack.decode.Compress;
import com.ssb.droidsound.utils.UnRar;

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

public final class ArchiveManager {

    private static final int KEEP_ALIVE_TIME = 5;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static final String[] ACCEPTS_IMAGE = new String[]{"jpeg", "jpg", "png", "webp"};

    private static ArchiveManager sInstance = null;

    static {
        sInstance = new ArchiveManager();
    }

    private final ThreadPoolExecutor mThreadPool;

    private int NUMBER_OF_CORES = Math
            .max(1, Runtime.getRuntime().availableProcessors() - 1);

    private ArchiveManager() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        mThreadPool = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue);
    }

    public static ArchiveManager getInstance() {
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
            for (String accept : ACCEPTS_IMAGE) {
                if (accept.equals(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir, boolean filter)
            throws IOException {
        if (Thread.interrupted()) {
            return;
        }

        if (entry.isDirectory() || (filter &&  !hasExtension(entry.getName()))) {
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

    public void unrar(final File archiveFile, final File destDir) {

        mThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                UnRar ur = new UnRar(archiveFile.getAbsolutePath());
                ur.extractTo(destDir.getAbsolutePath());
            }
        });

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

    }

    private static File makeFile(File destination, String name)
            throws IOException {
        String[] dirs = name.split("\\\\");
        String path = "";
        int size = dirs.length;
        if (size == 1) {
            return new File(destination, name);
        } else if (size > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                path = path + File.separator + dirs[i];
                new File(destination, path).mkdir();
            }
            path = path + File.separator + dirs[dirs.length - 1];
            File f = new File(destination, path);
            f.createNewFile();
            return f;
        } else {
            return null;
        }
    }

    public static File createFileNoDir(FileHeader fh, File destination) {
        File f;
        String name;
        if (fh.isFileHeader() && fh.isUnicode()) {
            name = fh.getFileNameW();
        } else {
            name = fh.getFileNameString();
        }
        int i = name.lastIndexOf("\\");
        if(i != -1 && i != name.length()) {
            name = name.substring(i + 1, name.length()) ;
        }

        f = new File(destination, name);
        if (!f.exists()) {
            try {
                f = makeFile(destination, name);
            } catch (IOException e) {
                // logError(e, "error creating the new file: " + f.getName());
            }
        }
        return f;
    }

    public File unrar(final Archive archive, final FileHeader fileHeader, final File outputDir) {
        if(archive == null) {
            throw new IllegalArgumentException("archive is null");
        }
        final File file = createFileNoDir(fileHeader, outputDir);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            archive.extractFile(fileHeader, os);
        } catch (Exception e) {
            Compress.adjustWindowSize(false);
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
        }

        return file;
    }

    public void unzip(final ZipFile zipFile, final ZipEntry entry, final File outputDir) {
        mThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    unzipEntry(zipFile, entry, outputDir, true);
                } catch (IOException e) {

                } finally {

                }
            }
        });

    }

    public void unzip(final ZipFile zipFile, final ZipEntry entry, final File outputDir, final boolean filter) {
        mThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    unzipEntry(zipFile, entry, outputDir, filter);
                } catch (IOException e) {

                } finally {

                }
            }
        });

    }

}
