package com.skubit.comics.fragments;

import com.gc.materialdesign.views.ButtonFlat;
import com.skubit.comics.ComicForCollection;
import com.skubit.comics.R;
import com.skubit.comics.adapters.AddComicsToCollectionAdapter;
import com.skubit.comics.loaders.AddComicsToCollectionReadLoader;
import com.skubit.comics.loaders.AddComicsToCollectionWriteLoader;
import com.skubit.dialog.DefaultFragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddComicsToCollectionFragment extends DefaultFragment {

    private AddComicsToCollectionAdapter mAdapter;

    private String mCid;

    private final LoaderManager.LoaderCallbacks<ArrayList<ComicForCollection>> mLoader
            = new LoaderManager.LoaderCallbacks<ArrayList<ComicForCollection>>() {

        @Override
        public Loader<ArrayList<ComicForCollection>> onCreateLoader(int id, Bundle args) {
            return new AddComicsToCollectionReadLoader(getActivity(), mCid);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<ComicForCollection>> loader,
                ArrayList<ComicForCollection> data) {
            if (data != null) {
                mAdapter = new AddComicsToCollectionAdapter(
                        getActivity());
                mAdapter.setItems(data);
                mListView.setAdapter(mAdapter);
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<ComicForCollection>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<Boolean> mAddLoader
            = new LoaderManager.LoaderCallbacks<Boolean>() {

        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new AddComicsToCollectionWriteLoader(getActivity(), mCid, mAdapter.getItems());
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader,
                Boolean data) {
            if (data != null) {
                getActivity().finish();
            }
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {

        }
    };

    private ListView mListView;

    private String mName;

    private ButtonFlat mAddBtn;

    public static Fragment newInstance(Bundle args) {
        AddComicsToCollectionFragment frag = new AddComicsToCollectionFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCid = args.getString("cid");
            mName = args.getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_comics_to_collection, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);

        mAddBtn = (ButtonFlat) view.findViewById(R.id.addBtn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Pass mCid and items to loader
                getLoaderManager().initLoader(15000, null, mAddLoader);
            }
        });
        ButtonFlat cancelBtn = (ButtonFlat) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        TextView collectionsHeader = (TextView) view.findViewById(R.id.add_collection_header);
        collectionsHeader.setText("Add to " + mName);
        getLoaderManager().initLoader(5000, null, mLoader);
        return view;
    }
}
