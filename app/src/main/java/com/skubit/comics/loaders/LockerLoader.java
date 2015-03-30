package com.skubit.comics.loaders;

import com.skubit.AccountSettings;
import com.skubit.comics.Utils;
import com.skubit.comics.services.LockerService;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.ErrorMessage;
import com.skubit.shared.dto.LockerItemListDto;

import android.content.Context;

import java.io.IOException;

import retrofit.RetrofitError;

public class LockerLoader extends BaseLoader<LoaderResult<LockerItemListDto>> {

    private final LockerService mLockerService;

    private final String mApplication;

    public LockerLoader(Context context, String application) {
        super(context);
        String account = AccountSettings.get(context).retrieveBitId();
        mLockerService = new LockerService(account, context);
        mApplication = application;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public LoaderResult<LockerItemListDto> loadInBackground() {
        LoaderResult result = new LoaderResult();
        try {
            result.result = mLockerService.getRestService()
                    .getLockerItems(mApplication, 0, 500, null, Utils.isExplicitCatalog());
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
