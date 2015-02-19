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

import com.skubit.comics.ArchiveLoadedCallback;
import com.skubit.comics.ClickComicListener;
import com.skubit.comics.ComicView;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.RefreshActionProvider;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.adapters.MyComicsAdapter;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveColumns;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveCursor;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public final class MyComicsFragment extends Fragment implements
        ClickComicListener, ArchiveLoadedCallback {

    private static final String TAG = "MyComicsFragment";

    private ComicsArchiveCursor mComicsArchiveCursor;

    private TextView mLoadingText;

    public MyComicsFragment() {
    }

    public static MyComicsFragment newInstance() {
        return new MyComicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        final Cursor c = getActivity().getContentResolver()
                .query(ComicsArchiveColumns.CONTENT_URI, null, null, null, null);

        mComicsArchiveCursor = new ComicsArchiveCursor(c);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_comics, container, false);
        mLoadingText = (TextView) v.findViewById(R.id.no_archives);
        ComicView comicView = (ComicView) v.findViewById(R.id.list);
        MyComicsAdapter adapter = new MyComicsAdapter(getActivity(), mComicsArchiveCursor, this);

        comicView.setHasFixedSize(true);
        comicView.setAdapter(adapter);
        comicView.addItemDecoration(new PaddingItemDecoration(20));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_comics_menu, menu);
        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        RefreshActionProvider refreshProvider = (RefreshActionProvider) refreshItem.getActionProvider();
        refreshProvider.setLoaderManager(getLoaderManager());
        if (mComicsArchiveCursor.getCount() == 0) {
            refreshProvider.startRefresh(this);
        } else {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v, int position) {
        mComicsArchiveCursor.moveToPosition(position);
        ActivityOptions options =
                ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
        startActivity(ComicViewerActivity.newInstance(mComicsArchiveCursor.getArchiveFile()),
                options.toBundle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mComicsArchiveCursor.close();
    }

    @Override
    public void archiveLoaded(boolean foundArchives) {
        if(foundArchives) {
            mLoadingText.setVisibility(View.GONE);
        }
    }
}
