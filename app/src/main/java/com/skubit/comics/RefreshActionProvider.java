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
package com.skubit.comics;

import com.skubit.comics.loaders.ArchiveScannerLoader;
import com.skubit.comics.loaders.responses.ArchiveScannerResponse;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public final class RefreshActionProvider extends ActionProvider {

    private final Context mContext;

    private final LoaderManager.LoaderCallbacks<ArchiveScannerResponse> mArchiveScannerCallback
            = new LoaderManager.LoaderCallbacks<ArchiveScannerResponse>() {

        @Override
        public Loader<ArchiveScannerResponse> onCreateLoader(int id, Bundle args) {
            return new ArchiveScannerLoader(mContext, Constants.SKUBIT_ARCHIVES);
        }

        @Override
        public void onLoadFinished(Loader<ArchiveScannerResponse> loader,
                ArchiveScannerResponse data) {
            Toast.makeText(mContext, "Load finished - archives found: " + data.comicArchives.size(),
                    Toast.LENGTH_SHORT).show();
            if(mCallback != null) {
                mCallback.archiveLoaded(!data.comicArchives.isEmpty());
            }
            endRefresh();
        }

        @Override
        public void onLoaderReset(Loader<ArchiveScannerResponse> loader) {

        }
    };

    private final View mRefreshIcon;

    private LoaderManager mLoaderManager;

    private ArchiveLoadedCallback mCallback;

    public RefreshActionProvider(Context context) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mRefreshIcon = layoutInflater.inflate(R.layout.refresh_icon, null);
    }

    public void setLoaderManager(final LoaderManager loaderManager) {
        mLoaderManager = loaderManager;

        mRefreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRefresh(null);
            }
        });
    }

    @Override
    public View onCreateActionView() {
        return mRefreshIcon;
    }

    private void endRefresh() {
        if (mRefreshIcon != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshIcon.clearAnimation();
                }
            }, 1000);
        }
    }

    public void startRefresh(ArchiveLoadedCallback callback) {
        mCallback = callback;
        if (mRefreshIcon != null) {
            Animation rotation = AnimationUtils.loadAnimation(mContext, R.anim.clockwise);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mLoaderManager.restartLoader(20, null, mArchiveScannerCallback);

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mRefreshIcon.startAnimation(rotation);
        }
    }
}
