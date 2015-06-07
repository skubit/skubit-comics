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
package com.skubit.comics.fragments;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.ItemClickListener;
import com.skubit.comics.R;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.activities.DownloadDialogActivity;
import com.skubit.comics.adapters.LockerAdapter;
import com.skubit.comics.adapters.LockerOptionAdapter;
import com.skubit.comics.loaders.LoaderId;
import com.skubit.comics.loaders.LockerLoader;
import com.skubit.comics.provider.comic.ComicCursor;
import com.skubit.comics.provider.comic.ComicSelection;
import com.skubit.dialog.LoaderResult;
import com.skubit.shared.dto.LockerItemListDto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class LockerFragment extends Fragment implements ItemClickListener {
/*
    private final LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>> mDownloadCallback
            = new LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>>() {

        private String mCbid;

        private String mTitle;

        @Override
        public Loader<LoaderResult<UrlDto>> onCreateLoader(int id, Bundle args) {
            mCbid = args.getString("cbid");
            mTitle = args.getString("title");

            return new DownloadComicLoader(getActivity().getBaseContext(), mCbid, false, null);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<UrlDto>> loader,
                final LoaderResult<UrlDto> data) {
            DownloadManager mDownloadManager = (DownloadManager) getActivity().getSystemService(
                    Context.DOWNLOAD_SERVICE);
            if (!TextUtils.isEmpty(data.errorMessage)) {
                Toast.makeText(getActivity().getBaseContext(), data.errorMessage,
                        Toast.LENGTH_SHORT).show();
            } else {
                Utils.download(data.result.getUrl(), mCbid, mTitle, null, ArchiveFormat.CBZ, mDownloadManager);
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<UrlDto>> loader) {

        }
    };
*/
    private final LoaderManager.LoaderCallbacks<LoaderResult<LockerItemListDto>> mCatalogLoader
            = new LoaderManager.LoaderCallbacks<LoaderResult<LockerItemListDto>>() {

        @Override
        public Loader<LoaderResult<LockerItemListDto>> onCreateLoader(int id, Bundle args) {
            return new LockerLoader(getActivity(), BuildConfig.APPLICATION_ID);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<LockerItemListDto>> loader,
                LoaderResult<LockerItemListDto> data) {
            if (data != null && TextUtils.isEmpty(data.errorMessage) && data.result != null
                    && data.result.getItems() != null) {
                adapter.add(data.result.getItems());
                adapter.notifyDataSetChanged();
            }
            mSwipe.setRefreshing(false);
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<LockerItemListDto>> loader) {
            mSwipe.setRefreshing(false);
        }
    };

    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipe;

    private LockerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LoaderId.CATALOG_LOADER, null, mCatalogLoader);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locker, null);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(LoaderId.CATALOG_LOADER, null, mCatalogLoader);
            }
        });

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.locker);

        adapter = new LockerAdapter(getActivity(), this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        rv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v, int position, Cursor cursor) {

    }

    @Override
    public void onClickOption(View v, final Bundle bundle) {
        final String cbid = bundle.getString("cbid");
        //TODO: move off main thread
        ComicSelection ks = new ComicSelection();
        ks.cbid(cbid);

        final ComicCursor c = ks.query(getActivity().getContentResolver());

        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(new LockerOptionAdapter());
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setContentWidth(400);
        listPopupWindow.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//Download
                    startActivity(DownloadDialogActivity
                            .newInstance(cbid, bundle.getString("title"), false));

                //    getLoaderManager().initLoader(cbid.hashCode(), bundle, mDownloadCallback);
                } else if (position == 1) {//Open
                    if (c != null && c.getCount() > 0) {
                        c.moveToFirst();
                        if (!TextUtils.isEmpty(c.getArchiveFile())) {
                            startActivity(ComicViewerActivity
                                    .newInstance(c.getStoryTitle(), c.getArchiveFile(),
                                            c.getLastPageRead()));

                        }
                        c.close();
                        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
                    }
                }
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }
}
