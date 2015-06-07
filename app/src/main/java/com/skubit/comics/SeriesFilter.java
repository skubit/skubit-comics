package com.skubit.comics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class SeriesFilter implements Parcelable {

    //Series
    public int alphaBucket;

    public String creator;

    public String genre;

    public String publisher;

    /**
     * Lookup Series by Series Name
     */
    public String series;

    public boolean hasSeries() {
        return !TextUtils.isEmpty(series);
    }

    public boolean hasCreator() {
        return !TextUtils.isEmpty(creator);
    }

    public boolean hasGenre() {
        return !TextUtils.isEmpty(genre);
    }

    public boolean hasPublisher() {
        return !TextUtils.isEmpty(publisher);
    }

    public static Bundle toBundle(SeriesFilter filter) {
        Bundle data = new Bundle();
        Parcel parcel = Parcel.obtain();
        filter.writeToParcel(parcel, 0);
        data.putParcelable("com.skubit.comics.SERIES_FILTER", filter);
        parcel.setDataPosition(0);
        return data;
    }

    public static final Creator<SeriesFilter> CREATOR
            = new Creator<SeriesFilter>() {
        @Override
        public SeriesFilter createFromParcel(Parcel parcel) {
            SeriesFilter filter = new SeriesFilter();
            filter.alphaBucket = parcel.readInt();
            filter.creator= readString(parcel);
            filter.genre = readString(parcel);
            filter.publisher = readString(parcel);
            filter.series = readString(parcel);
            return filter;
        }

        @Override
        public SeriesFilter[] newArray(int size) {
            return new SeriesFilter[size];
        }
    };

    private static void writeString(String s, Parcel p) {
        if(TextUtils.isEmpty(s)) {
            p.writeByte((byte) 0);
        } else {
            p.writeByte((byte) 1);
            p.writeString(s);
        }
    }


    private static String readString(Parcel p) {
        return p.readByte() == 1 ? p.readString() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(alphaBucket);
        writeString(creator, parcel);
        writeString(genre, parcel);
        writeString(publisher, parcel);
        writeString(series, parcel);
    }

    @Override
    public String toString() {
        return "SeriesFilter{" +
                "alphaBucket=" + alphaBucket +
                ", creator='" + creator + '\'' +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", series='" + series + '\'' +
                '}';
    }
}
