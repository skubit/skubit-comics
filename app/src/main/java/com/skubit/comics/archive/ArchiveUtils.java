package com.skubit.comics.archive;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ArchiveUtils {

    private static final String[] ACCEPTS = new String[]{"jpeg", "jpg", "png", "webp"};

    public static ZipFile readZipEntries(File archive,
            final HashMap<String, ZipEntry> compressionEntries) {
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(archive);
            for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                if (!entry.getName().contains("META-INF") && !entry.getName().contains("MACOSX")
                          && entry.getCompressedSize() > 20000
                        && ArchiveUtils.hasExtension(entry.getName())) {

                    compressionEntries.put(entry.getName(), entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return zipfile;
    }

    public static boolean hasExtension(String filename) {
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

    public static Archive readFileHeaders(File archiveFile,
            final HashMap<String, FileHeader> compressionEntries) {

        Archive archive = null;
        try {
            archive = new Archive(archiveFile);
            if (archive.isEncrypted()) {
                return archive;
            }

            while (true) {
                FileHeader fh = archive.nextFileHeader();
                if (fh == null) {
                    break;
                }
                String fileName = fh.getFileNameString();
                if (!fh.isDirectory() && !fh.isEncrypted() && fh.getFullUnpackSize() > 20000
                        && ArchiveUtils.hasExtension(fileName)
                        && !fileName.contains("META-INF") && !fileName.contains("MACOSX")) {
                    compressionEntries.put(fileName, fh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archive;
    }
}
