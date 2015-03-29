package com.skubit.comics.fragments;

import com.skubit.comics.AdapterListener;
import com.skubit.comics.R;
import com.skubit.comics.activities.AddComicsToCollectionActivity;
import com.skubit.comics.activities.CollectionFilterActivity;
import com.skubit.comics.activities.CreateCollectionActivity;
import com.skubit.comics.activities.DeleteCollectionActivity;
import com.skubit.comics.adapters.CursorRecyclerViewAdapter;
import com.skubit.comics.adapters.MyCollectionsOptionAdapter;
import com.skubit.comics.loaders.MyCollectionsLoader;
import com.skubit.comics.provider.collection.CollectionCursor;
import com.skubit.dialog.FloatButton;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class MyCollectionsFragment extends Fragment implements AdapterListener {

    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipe;

    private CursorRecyclerViewAdapter mAdapter;

    private RecyclerView mCollections;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_collections, null);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        FloatButton addCollection = (FloatButton) view.findViewById(R.id.add_collection);
        addCollection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(CreateCollectionActivity.newInstance());
            }
        });

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(5000, null,
                        new MyCollectionsLoader(getActivity(), MyCollectionsFragment.this));
            }
        });

        mCollections = (RecyclerView) view.findViewById(R.id.collections);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCollections.setLayoutManager(mLayoutManager);
        getLoaderManager().initLoader(5000, null, new MyCollectionsLoader(getActivity(), this));
        return view;
    }


    @Override
    public void resetAdapter(CursorRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mCollections.setAdapter(mAdapter);
        mSwipe.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position, Cursor cursor) {
        CollectionCursor c = new CollectionCursor(cursor);
        c.moveToPosition(position);
        getActivity().startActivity(CollectionFilterActivity.newInstance(c.getCid(), c.getName()));
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.none);
        /*
        CollectionCursor c = new CollectionCursor(cursor);
        c.moveToPosition(position);
        getActivity().startActivity(AddComicsToCollectionActivity.newInstance(c.getCid(), c.getName()));
        */
    }

    @Override
    public void onClickOption(View v, Bundle data) {
        final String cid = data.getString("cid");
        final String title = data.getString("title");

        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(new MyCollectionsOptionAdapter());
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(v);
        listPopupWindow.setContentWidth(400);
        listPopupWindow.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(
                            AddComicsToCollectionActivity.newInstance(cid, title));
                } else if (position == 1) {
                    startActivity(
                            DeleteCollectionActivity.newInstance(cid, title));
                }
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }
}
