/**
 * Copyright 2015 Skubit
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

import com.skubit.currencies.Bitcoin;
import com.skubit.currencies.Satoshi;
import com.skubit.shared.dto.ComicBookDto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


public class ComicData implements Parcelable {

    public static final String EXTRA_NAME = "com.skubit.comics.ComicData";

    private String cbid;

    private String title;

    private String nativePrice;

    private String coverArt;

    private String publisher;

    private int issueNumber;

    private boolean isPurchased;

    private String ageRating;

    private String currencySymbol;

    private int pageCount;

    private String volume;

    private String language;

    private double price;

    private String writer;

    private String issueFormat;

    private String comicBookType;

    public static final Creator<ComicData> CREATOR
            = new Creator<ComicData>() {

        @Override
        public ComicData createFromParcel(Parcel source) {
            ComicData comicData = new ComicData();
            comicData.ageRating = readString(source);
            comicData.cbid = readString(source);
            comicData.coverArt = readString(source);
            comicData.currencySymbol = readString(source);
            comicData.isPurchased = source.readByte() != 0;
            comicData.issueNumber = source.readInt();
            comicData.language = readString(source);
            comicData.nativePrice = readString(source);
            comicData.pageCount = source.readInt();
            comicData.price = source.readDouble();
            comicData.publisher = readString(source);
            comicData.title = readString(source);
            comicData.volume = readString(source);
            comicData.writer = readString(source);
            comicData.issueFormat = readString(source);
            comicData.comicBookType = readString(source);
            return comicData;
        }

        @Override
        public ComicData[] newArray(int size) {
            return new ComicData[size];
        }
    };


    public ComicData() {
    }

    public ComicData(ComicBookDto dto) {
        ageRating = dto.getAgeRating().name();
        cbid = dto.getCbid();
        coverArt = dto.getCoverArtUrl();
        currencySymbol = dto.getCurrencySymbol();
        isPurchased = dto.isPurchased();
        issueNumber = dto.getIssueNumber();
        language = dto.getLanguage().name();
        nativePrice = new Bitcoin(new Satoshi(dto.getSatoshi())).getDisplay() + " BTC";
        pageCount = dto.getNumPages();
        price = dto.getPrice();
        publisher = dto.getPublisher();
        title = dto.getStoryTitle();
        volume = dto.getVolume();
        writer = dto.getWriter();
        if(dto.getIssueFormat() != null) {
            issueFormat = dto.getIssueFormat().name();
        }

        if(dto.getComicBookType() != null) {
            comicBookType = dto.getComicBookType().name();
        }
    }

    private static void writeString(String s, Parcel p) {
        if (TextUtils.isEmpty(s)) {
            p.writeByte((byte) 0);
        } else {
            p.writeByte((byte) 1);
            p.writeString(s);
        }
    }

    private static String readString(Parcel p) {
        return p.readByte() == 1 ? p.readString() : null;
    }

    public String getCbid() {
        return cbid;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public String getTitle() {
        return title;
    }

    public String getNativePrice() {
        return nativePrice;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        writeString(ageRating, parcel);
        writeString(cbid, parcel);
        writeString(coverArt, parcel);
        writeString(currencySymbol, parcel);
        parcel.writeByte((byte) (isPurchased ? 1 : 0));
        parcel.writeInt(issueNumber);
        writeString(language, parcel);
        writeString(nativePrice, parcel);
        parcel.writeInt(pageCount);
        parcel.writeDouble(price);
        writeString(publisher, parcel);
        writeString(title, parcel);
        writeString(volume, parcel);
        writeString(writer, parcel);
        writeString(issueFormat, parcel);
        writeString(comicBookType, parcel);
    }

    public String getIssueFormat() {
        return issueFormat;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public double getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public String getWriter() {
        return writer;
    }

    public String getComicBookType() {
        return comicBookType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComicData comicData = (ComicData) o;

        return cbid.equals(comicData.cbid);

    }

    @Override
    public int hashCode() {
        return cbid.hashCode();
    }

    @Override
    public String toString() {
        return "ComicData{" +
                "ageRating='" + ageRating + '\'' +
                ", cbid='" + cbid + '\'' +
                ", title='" + title + '\'' +
                ", nativePrice='" + nativePrice + '\'' +
                ", coverArt='" + coverArt + '\'' +
                ", publisher='" + publisher + '\'' +
                ", issueNumber=" + issueNumber +
                ", isPurchased=" + isPurchased +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", pageCount=" + pageCount +
                ", volume='" + volume + '\'' +
                ", language='" + language + '\'' +
                ", price=" + price +
                ", writer='" + writer + '\'' +
                '}';
    }
}
