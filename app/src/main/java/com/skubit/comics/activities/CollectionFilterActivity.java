package com.skubit.comics.activities;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicGridView;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyCollectionsComicOptionAdapter;
import com.skubit.comics.adapters.MyComicsAdapter;
import com.skubit.comics.loaders.CollectionOfComicsLoader;
import com.skubit.comics.provider.comic.ComicCursor;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class CollectionFilterActivity extends ActionBarActivity implements AdapterListener {

    private TextView mLoadingText;

    private CursorRecyclerViewAdapter mAdapter;

    private ComicGridView comicGridView;

    private String mCid;

    private final LoaderManager.LoaderCallbacks<Cursor> mCallback
            = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CollectionOfComicsLoader(CollectionFilterActivity.this, mCid);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader,
                final Cursor data) {
            mAdapter = new MyComicsAdapter(getBaseContext(), data, CollectionFilterActivity.this);
            if (mAdapter.getCursor().getCount() != 0) {
                mLoadingText.setVisibility(View.GONE);
            }
            comicGridView.setAdapter(mAdapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    public static Intent newInstance(String cid, String title) {
        Intent i = new Intent();
        i.putExtra("cid", cid);
        i.putExtra("title", title);
        i.setClassName(BuildConfig.APPLICATION_ID, CollectionFilterActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        mCid = getIntent().getStringExtra("cid");
        String title = getIntent().getStringExtra("title");
        setTitle(title);

        mLoadingText = (TextView) findViewById(R.id.no_archives);
        comicGridView = (ComicGridView) findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);

        comicGridView.addItemDecoration(new PaddingItemDecoration(10));

        getLoaderManager().initLoader(6000, null, mCallback);
    }


    @Override
    public void onClickOption(View v, Bundle data) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new MyCollectionsComicOptionAdapter());
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
        comicGridView.setAdapter(adapter);

        if (adapter.getCursor().getCount() != 0) {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v, int position, Cursor cursor) {
        ComicCursor c = new ComicCursor(cursor);
        c.moveToPosition(position);
        startActivity(ComicViewerActivity
                .newInstance(c.getStoryTitle(), c.getArchiveFile(), c.getLastPageRead()));
        overridePendingTransition(R.anim.pull_in_right, R.anim.none);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.push_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.none, R.anim.push_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
