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
import com.skubit.comics.GridRecyclerScrollListener;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.activities.ComicDetailsActivity;
import com.skubit.comics.adapters.CatalogAdapter;
import com.skubit.comics.loaders.SearchLoader;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookListDto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class SearchFragment extends Fragment implements
        ClickComicListener {

    private static final String TAG = "CatalogFragment";

    private CatalogAdapter adapter;

    private final LoaderManager.LoaderCallbacks<ComicBookListDto> mSearchLoader
            = new LoaderManager.LoaderCallbacks<ComicBookListDto>() {

        @Override
        public Loader<ComicBookListDto> onCreateLoader(int id, Bundle args) {
            return new SearchLoader(getActivity(), mQuery, mOffset, mLimit);
        }

        @Override
        public void onLoadFinished(Loader<ComicBookListDto> loader, ComicBookListDto data) {
            if(data != null) {
                if(data.getItems().isEmpty()) {
                    //done
                }

                adapter.add(data.getItems());
                adapter.notifyDataSetChanged();
                String nextLink = data.getNextLink();
                if(!TextUtils.isEmpty(nextLink)) {
                    Uri uri = Uri.parse(nextLink);
                    String offsetStr = uri.getQueryParameter("offset");
                    String limitStr = uri.getQueryParameter("limit");
                    if(!TextUtils.isEmpty(offsetStr) && !TextUtils.isEmpty(limitStr)) {
                        int offset = Integer.getInteger(offsetStr);
                        int limit = Integer.getInteger(limitStr);
                        //increment these by N
                        mLimit = limit;
                        mOffset = mOffset + mLimit;

                    }
                }
            }
        }
            
        @Override
        public void onLoaderReset(Loader<ComicBookListDto> loader) {
   
        }
    };

    private String mQuery;

    private int mOffset;

    private int mLimit;


    public static SearchFragment newInstance(String query, int limit) {
        Bundle data = new Bundle();
        data.putString("q", query);
        data.putInt("limit", limit);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null) {
            mQuery = getArguments().getString("q");
            mLimit = getArguments().getInt("limit");
        }
        mOffset = 0;

        ComicGridView comicGridView = (ComicGridView) view.findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);
        comicGridView.setAdapter(adapter);
        comicGridView.addItemDecoration(new PaddingItemDecoration(20));

        comicGridView
                .setOnScrollListener(
                        new GridRecyclerScrollListener(comicGridView.getLayoutManager()) {
                            @Override
                            public void onLoadMore() {
                                /*
                                if (!TextUtils.isEmpty(mSearchLoader.mWebCursor)) {
                                    getLoaderManager()
                                            .restartLoader(getLoaderId(), null, mSearchLoader);
                                }
                                */
                            }
                        });
        getLoaderManager().restartLoader(mQuery.hashCode(), null, mSearchLoader);
    }


    public int getViewResource() {
        return R.layout.fragment_catalog;
    }

    public int getLoaderId() {
        return 3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new CatalogAdapter(getActivity(), this);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getViewResource(), container, false);
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
}
