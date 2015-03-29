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


public class ComicData implements Parcelable {

    public static final String EXTRA_NAME = "com.skubit.comics.ComicData";

    private String cbid;

    private String title;

    private String price;

    private String coverArt;

    private String publisher;

    private boolean isPurchased;

    public static final Creator<ComicData> CREATOR
            = new Creator<ComicData>() {

        @Override
        public ComicData createFromParcel(Parcel source) {
            ComicData comicData = new ComicData();
            comicData.cbid = source.readString();
            comicData.title = source.readString();
            comicData.price = source.readString();
            comicData.coverArt = source.readString();
            comicData.publisher = source.readString();
            comicData.isPurchased = source.readByte() != 0;
            return comicData;
        }

        @Override
        public ComicData[] newArray(int size) {
            return new ComicData[0];
        }
    };

    public ComicData() {
    }

    public ComicData(ComicBookDto dto) {
        cbid = dto.getCbid();
        title = dto.getStoryTitle();
        price = new Bitcoin(new Satoshi(dto.getSatoshi())).getDisplay() + " BTC";
        coverArt = dto.getCoverArtUrlMedium();
        publisher = dto.getPublisher();
        isPurchased = dto.isPurchased();
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

    public String getPrice() {
        return price;
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
        parcel.writeString(cbid);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(coverArt);
        parcel.writeString(publisher);
        parcel.writeByte((byte) (isPurchased ? 1 : 0));
    }
}
