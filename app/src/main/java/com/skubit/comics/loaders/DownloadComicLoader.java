package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.Constants;
import com.skubit.comics.services.ComicService;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.ErrorMessage;
import com.skubit.shared.dto.UrlDto;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import retrofit.RetrofitError;

public class DownloadComicLoader extends BaseLoader<LoaderResult<UrlDto>> {

    private final ComicService mComicService;

    private final String mCbid;

    public DownloadComicLoader(Context context, String cbid) {
        super(context);
        mCbid = cbid;
        String account = AccountSettings.get(context).retrieveBitId();
        mComicService = new ComicService(account, context);
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public LoaderResult<UrlDto> loadInBackground() {

        LoaderResult result = new LoaderResult();
        try {
            result.result = mComicService.getRestService().getDownloadUrl(mCbid);

            File rootDir = new File(Constants.SKUBIT_ARCHIVES_DOWNLOAD,
                    mCbid);
            if (rootDir.exists()) {
                for (File file : rootDir.listFiles()) {
                    file.delete();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof RetrofitError) {
                ErrorMessage message = readRetrofitError(e);
                if (message == null) {
                    result.errorMessage = "Bad server request";
                    return result;
                }
                if (message.getMessages() != null && message.getMessages().length > 0) {
                    result.errorMessage = message.getMessages()[0].getMessage();
                }
                return result;
            }
        }
        result.errorMessage = "Bad server request";
        return result;
    }
}
