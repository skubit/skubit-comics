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
import com.skubit.comics.ComicGridView;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.RecyclerScrollListener;
import com.skubit.comics.activities.ComicDetailsActivity;
import com.skubit.comics.adapters.CatalogAdapter;
import com.skubit.comics.archive.ArchiveLoadedCallback;
import com.skubit.comics.loaders.CatalogLoader;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookListDto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class CatalogFragment extends Fragment implements
        ClickComicListener, ArchiveLoadedCallback {

    private static final String TAG = "CatalogFragment";

    private SwipeRefreshLayout mSwipe;

    private CatalogAdapter adapter;

    private String mWebCursor;

    private final LoaderManager.LoaderCallbacks<ComicBookListDto> mCatalogLoader
            = new LoaderManager.LoaderCallbacks<ComicBookListDto>() {

        @Override
        public Loader<ComicBookListDto> onCreateLoader(int id, Bundle args) {
            return new CatalogLoader(getActivity(), mWebCursor);
        }

        @Override
        public void onLoadFinished(Loader<ComicBookListDto> loader, ComicBookListDto data) {
            if (data != null && data.getItems() != null && data.getItems().size() > 0) {
                mWebCursor = data.getNextWebCursor();
                adapter.add(data.getItems());
                adapter.notifyDataSetChanged();
            }
            mSwipe.setRefreshing(false);
        }

        @Override
        public void onLoaderReset(Loader<ComicBookListDto> loader) {
            mSwipe.setRefreshing(false);
        }
    };

    public CatalogFragment() {
    }

    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().restartLoader(LoaderId.CATALOG_LOADER, null, mCatalogLoader);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        mSwipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(LoaderId.CATALOG_LOADER, null, mCatalogLoader);
            }
        });

        adapter = new CatalogAdapter(getActivity(), this);

        ComicGridView comicGridView = (ComicGridView) v.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(adapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(20));

        comicGridView
                .setOnScrollListener(new RecyclerScrollListener(comicGridView.getLayoutManager()) {
                    @Override
                    public void onLoadMore() {
                        if (!TextUtils.isEmpty(mWebCursor)) {
                            getLoaderManager()
                                    .restartLoader(LoaderId.CATALOG_LOADER, null, mCatalogLoader);
                        }
                    }
                });
        return v;
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
