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
package com.skubit.comics.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skubit.comics.BuildConfig;

import android.content.Context;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;


public abstract class BaseService<T> {

    private T mRestService;

    public BaseService(String account, Context context) {
        CookieInterceptor interceptor = new CookieInterceptor(
                account, context);

        JacksonConverter jc;

        if (BuildConfig.DEBUG) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jc = new JacksonConverter(mapper);
        } else {
            jc = new JacksonConverter();
        }

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getEndpoint())
                .setConverter(jc)
                .setRequestInterceptor(interceptor).build();

        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        mRestService = restAdapter.create(getClazz());
    }

    public abstract Class<T> getClazz();

    public T getRestService() {
        return mRestService;
    }

    public abstract String getEndpoint();
}
