package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.services.ComicService;
import com.skubit.comics.services.rest.ComicRestService;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicFileClassifier;
import com.skubit.shared.dto.ComicFileDto;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

public class ComicFilesLoader extends BaseLoader<ArrayList<ComicFileDto>> {

    private final ComicRestService mComicService;

    private final String mCbid;

    private final ComicFileClassifier mClassifier;

    public ComicFilesLoader(Context context, String cbid, ComicFileClassifier classifier) {
        super(context);
        String account = AccountSettings.get(context).retrieveBitId();
        mCbid = cbid;
        mClassifier = classifier;
        mComicService = new ComicService(account, context).getRestService();
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ArrayList<ComicFileDto> loadInBackground() {
        ArrayList<ComicFileDto> cf = new ArrayList<>();
        try {
            ArrayList<ComicFileDto> cfs = mComicService.getComicFiles(mCbid, mClassifier);
            for (ComicFileDto cfd : cfs) {
                if (!ArchiveFormat.CBR.equals(cfd.getFormat())) {
                    cf.add(cfd);
                }
            }
            return cf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
