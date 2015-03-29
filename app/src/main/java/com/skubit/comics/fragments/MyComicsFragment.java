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

import com.skubit.comics.AdapterListener;
import com.skubit.comics.ComicGridView;
import com.skubit.comics.Constants;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.activities.DeleteFromCollectionActivity;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyCollectionsComicOptionAdapter;
import com.skubit.comics.archive.ArchiveLoadedCallback;
import com.skubit.comics.archive.loaders.ArchiveScannerLoader;
import com.skubit.comics.archive.responses.ArchiveScannerResponse;
import com.skubit.comics.loaders.MyComicsLoader;
import com.skubit.comics.provider.comic.ComicCursor;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public final class MyComicsFragment extends Fragment implements
        ArchiveLoadedCallback, AdapterListener {

    private static final String TAG = "MyComicsFragment";

    private final LoaderManager.LoaderCallbacks<ArchiveScannerResponse> mArchiveScannerCallback
            = new LoaderManager.LoaderCallbacks<ArchiveScannerResponse>() {

        @Override
        public Loader<ArchiveScannerResponse> onCreateLoader(int id, Bundle args) {
            return new ArchiveScannerLoader(getActivity(), Constants.SKUBIT_ARCHIVES, false);
        }

        @Override
        public void onLoadFinished(Loader<ArchiveScannerResponse> loader,
                ArchiveScannerResponse data) {
            Toast.makeText(getActivity(),
                    "Load finished - archives found: " + data.comicArchives.size(),
                    Toast.LENGTH_SHORT).show();
            archiveLoaded(!data.comicArchives.isEmpty());
            mSwipe.setRefreshing(false);
        }

        @Override
        public void onLoaderReset(Loader<ArchiveScannerResponse> loader) {
            mSwipe.setRefreshing(false);
        }
    };

    private TextView mLoadingText;

    private SwipeRefreshLayout mSwipe;

    private CursorRecyclerViewAdapter mAdapter;

    private ComicGridView comicGridView;

    public MyComicsFragment() {
    }

    public static MyComicsFragment newInstance() {
        return new MyComicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_comics, container, false);
        mSwipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(20, null, mArchiveScannerCallback);
            }
        });

        mLoadingText = (TextView) v.findViewById(R.id.no_archives);
        comicGridView = (ComicGridView) v.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);

        comicGridView.addItemDecoration(new PaddingItemDecoration(10));

        getLoaderManager().initLoader(6000, null, new MyComicsLoader(getActivity(), this));
        return v;
    }

    @Override
    public void onClickOption(View v, final Bundle data) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setContentWidth(400);
        listPopupWindow.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);

        listPopupWindow.setAdapter(new MyCollectionsComicOptionAdapter());

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                  //  startActivity(DeleteFromCollectionActivity.newInstance());
                } else if (position == 1) {

                }
                listPopupWindow.dismiss();
            }
        });

        listPopupWindow.show();

    }

    @Override
    public void archiveLoaded(boolean foundArchives) {
        if (foundArchives) {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    @Override
    public void resetAdapter(CursorRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        comicGridView.setAdapter(adapter);
        mSwipe.setRefreshing(false);

        if (adapter.getCursor().getCount() == 0) {
            getLoaderManager().restartLoader(20, null, mArchiveScannerCallback);
        } else {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v, int position, Cursor cursor) {
        if (cursor != null && position >= 0) {
            ComicCursor c = new ComicCursor(cursor);
            c.moveToPosition(position);
            Intent i = ComicViewerActivity
                    .newInstance(c.getStoryTitle(), c.getArchiveFile(), c.getLastPageRead());
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        }
    }
}
