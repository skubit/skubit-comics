package com.skubit.comics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ComicFile implements Parcelable {

    public String format;

    public long size;

    public String md5Hash;


    public static Bundle toBundle(ComicFile file) {
        Bundle data = new Bundle();
        Parcel parcel = Parcel.obtain();
        file.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        data.putParcelable("com.skubit.comics.COMIC_FILE", file);
        return data;
    }

    public static final Creator<ComicFile> CREATOR
            = new Creator<ComicFile>() {
        @Override
        public ComicFile createFromParcel(Parcel parcel) {
            ComicFile file = new ComicFile();
            file.format = readString(parcel);
            file.md5Hash = readString(parcel);
            file.size = parcel.readLong();
            return file;
        }

        @Override
        public ComicFile[] newArray(int size) {
            return new ComicFile[size];
        }
    };

    private static void writeString(String s, Parcel p) {
        p.writeByte((byte) (s != null ? 1 : 0));
        p.writeString(s);
    }

    private static String readString(Parcel p) {
        boolean isPresent = p.readByte() == 1;
        return isPresent ? p.readString() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        writeString(format, parcel);
        writeString(md5Hash, parcel);
        parcel.writeLong(size);
    }
}
