package com.skubit.comics.loaders;

import com.skubit.shared.dto.ImageType;
import com.skubit.shared.dto.UrlDto;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;


public class ScreenshotsLoader extends BaseComicServiceLoader<ArrayList<UrlDto>> {

    private final String mCbid;

    public ScreenshotsLoader(Context context, String cbid) {
        super(context, null, null, null);
        mCbid = cbid;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ArrayList<UrlDto> loadInBackground() {
        try {
            return mComicService.getScreenshotsDownload(mCbid, ImageType.WEBP);
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
