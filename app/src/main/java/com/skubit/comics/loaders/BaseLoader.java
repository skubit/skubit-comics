/* Copyright 2015 Skubit
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
package com.skubit.comics.loaders;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.skubit.shared.dto.ErrorMessage;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    protected final Context mContext;

    protected T mResponse;

    public BaseLoader(Context context) {
        super(context);
        mContext = context;
    }

    public static ErrorMessage readRetrofitError(Exception e) {
        RetrofitError error = (RetrofitError) e;
        if (error.getResponse() != null) {
            String json = new String(((TypedByteArray) error.getResponse().getBody())
                    .getBytes());
            try {
                return new Gson().fromJson(json, ErrorMessage.class);
            } catch (JsonSyntaxException e1) {
            }
        }

        return null;
    }

    protected abstract void closeStream() throws IOException;

    @Override
    protected void onStartLoading() {
        if (mResponse != null) {
            deliverResult(mResponse);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mResponse != null) {
            mResponse = null;
            try {
                closeStream();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void deliverResult(T data) {
        if (isReset() && mResponse != null) {
            return;
        }
        mResponse = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }
}
