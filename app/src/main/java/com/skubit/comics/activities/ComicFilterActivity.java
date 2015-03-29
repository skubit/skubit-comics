package com.skubit.comics.activities;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicGridView;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyComicsOptionAdapter;
import com.skubit.comics.loaders.MyComicsLoader;
import com.skubit.comics.provider.comic.ComicCursor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class ComicFilterActivity extends ActionBarActivity implements AdapterListener {

    private TextView mLoadingText;

    private CursorRecyclerViewAdapter mAdapter;

    private ComicGridView comicGridView;

    public static Intent newInstance(String selection, String[] params) {
        Intent i = new Intent();
        i.putExtra("selection", selection);
        i.putExtra("params", params);
        i.setClassName(BuildConfig.APPLICATION_ID, ComicFilterActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_comics);
        mLoadingText = (TextView) findViewById(R.id.no_archives);
        comicGridView = (ComicGridView) findViewById(R.id.list);
        comicGridView.setHasFixedSize(true);

        comicGridView.addItemDecoration(new PaddingItemDecoration(10));

        getLoaderManager()
                .initLoader(6000, getIntent().getExtras(), new MyComicsLoader(this, this));
    }


    @Override
    public void onClickOption(View v, Bundle data) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
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
}
