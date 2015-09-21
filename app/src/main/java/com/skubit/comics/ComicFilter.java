package com.skubit.comics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ComicFilter implements Parcelable {

    //Lookup comics by series
    public String series;

    //Comics
    public boolean isPaid;

    public boolean isFree;

    public boolean isFeatured;

    public boolean isElectricomic;

    public static Bundle toBundle(ComicFilter filter) {
        Bundle data = new Bundle();
        Parcel parcel = Parcel.obtain();
        filter.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        data.putParcelable("com.skubit.comics.COMIC_FILTER", filter);
        return data;
    }

    public static final Parcelable.Creator<ComicFilter> CREATOR
            = new Parcelable.Creator<ComicFilter>() {
        @Override
        public ComicFilter createFromParcel(Parcel parcel) {
            ComicFilter filter = new ComicFilter();
            filter.series = readString(parcel);
            return filter;
        }

        @Override
        public ComicFilter[] newArray(int size) {
            return new ComicFilter[size];
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
        writeString(series, parcel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComicFilter that = (ComicFilter) o;

        if (isPaid != that.isPaid) {
            return false;
        }
        if (isFree != that.isFree) {
            return false;
        }
        if (isFeatured != that.isFeatured) {
            return false;
        }
        if(isElectricomic != this.isElectricomic) {
            return false;
        }
        return !(series != null ? !series.equals(that.series) : that.series != null);

    }

    @Override
    public int hashCode() {
        int result = series != null ? series.hashCode() : 0;
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (isFree ? 1 : 0);
        result = 31 * result + (isFeatured ? 1 : 0);
        return result;
    }
}
