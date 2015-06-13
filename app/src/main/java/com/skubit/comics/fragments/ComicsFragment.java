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
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyComicsOptionAdapter;
import com.skubit.comics.archive.ArchiveType;
import com.skubit.comics.loaders.MyComicsLoader;
import com.skubit.comics.provider.comic.ComicCursor;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public final class ComicsFragment extends Fragment implements AdapterListener {

    private static final String TAG = "ComicsFragment";

    private TextView mLoadingText;

    private CursorRecyclerViewAdapter mAdapter;

    private ComicGridView mComicGridView;

    public ComicsFragment() {
    }

    public static ComicsFragment newInstance() {
        return new ComicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_comics, container, false);

        mLoadingText = (TextView) v.findViewById(R.id.no_archives);
        mComicGridView = (ComicGridView) v.findViewById(R.id.list);
        mComicGridView.setHasFixedSize(true);
        mComicGridView.addItemDecoration(new PaddingItemDecoration(10));

        getLoaderManager().initLoader(6000, null, new MyComicsLoader(getActivity(), this));
        return v;
    }

    @Override
    public void onClickOption(View v, Bundle data) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(new MyComicsOptionAdapter());
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setContentWidth(400);
        listPopupWindow.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {

                }
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();

    }

    @Override
    public void resetAdapter(CursorRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mComicGridView.setAdapter(adapter);

        if (adapter.getCursor().getCount() != 0) {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v, int position, Cursor cursor) {
        ComicCursor c = new ComicCursor(cursor);
        c.moveToPosition(position);
        startActivity(ComicViewerActivity
                .newInstance(c.getStoryTitle(), c.getArchiveFile(),
                        ArchiveType.fromString(c.getArchiveFormat()), c.getLastPageRead()));
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);

    }
}
