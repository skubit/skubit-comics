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
package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.ComicData;
import com.skubit.comics.ComicFilter;
import com.skubit.comics.ComicGridView;
import com.skubit.comics.GridRecyclerScrollListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.activities.ComicDetailsActivity;
import com.skubit.comics.adapters.CatalogAdapter;
import com.skubit.comics.archive.ArchiveLoadedCallback;
import com.skubit.comics.loaders.BaseComicServiceLoaderCallbacks;
import com.skubit.comics.loaders.CatalogLoader;
import com.skubit.shared.dto.ComicBookDto;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public final class CatalogFragment extends BaseComicFragment implements
        ClickComicListener, ArchiveLoadedCallback {

    private static final String TAG = "CatalogFragment";

    private CatalogAdapter adapter;

    private CatalogLoader.Callbacks mCatalogLoader;

    public CatalogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    public static CatalogFragment newInstance(ComicFilter filter) {
        Bundle data = ComicFilter.toBundle(filter);

        CatalogFragment fragment = new CatalogFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ComicGridView comicGridView = (ComicGridView) view.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(adapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(10));

        comicGridView
                .setOnScrollListener(
                        new GridRecyclerScrollListener(comicGridView.getLayoutManager()) {
                            @Override
                            public void onLoadMore() {
                                if (!TextUtils.isEmpty(mCatalogLoader.mWebCursor)) {
                                    getLoaderManager()
                                            .initLoader(mCatalogLoader.mWebCursor.hashCode(), null,
                                                    mCatalogLoader);
                                }
                            }
                        });
    }

    @Override
    public BaseComicServiceLoaderCallbacks getLoaderCallbacks() {
        return mCatalogLoader;
    }

    @Override
    public int getViewResource() {
        return R.layout.fragment_catalog;
    }

    @Override
    public int getLoaderId() {
        return mComicFilter.hashCode();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        adapter = new CatalogAdapter(getActivity(), this);
        mCatalogLoader = new CatalogLoader.Callbacks(getActivity().getBaseContext(),
                adapter, mComicFilter);
    }

    @Override
    public void onClick(View v, int position) {
        ComicBookDto comicBookDto = adapter.get(position);
        if (comicBookDto != null) {
            startActivity(ComicDetailsActivity.newInstance(new ComicData(comicBookDto)));
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        }
    }

    @Override
    public void onClickOption(View v) {

    }

    @Override
    public void archiveLoaded(boolean foundArchives) {

    }
}
