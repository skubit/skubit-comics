package com.skubit.comics.fragments;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.ComicData;
import com.skubit.comics.PaddingItemDecoration;
import com.skubit.comics.R;
import com.skubit.comics.SimilarView;
import com.skubit.comics.activities.ComicDetailsActivity;
import com.skubit.comics.adapters.CatalogAdapter;
import com.skubit.comics.loaders.SearchLoader;
import com.skubit.comics.loaders.SimilarLoader;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookListDto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class SimilarFragment extends Fragment implements
        ClickComicListener {

    private static final String TAG = "CatalogFragment";

    private CatalogAdapter adapter;

    private final LoaderManager.LoaderCallbacks<ComicBookListDto> mSearchLoader
            = new LoaderManager.LoaderCallbacks<ComicBookListDto>() {

        @Override
        public Loader<ComicBookListDto> onCreateLoader(int id, Bundle args) {
            return new SimilarLoader(getActivity(), mQuery, 3, 20);
        }

        @Override
        public void onLoadFinished(Loader<ComicBookListDto> loader, ComicBookListDto data) {
            if(data != null && !data.getItems().isEmpty()) {
                ArrayList<ComicBookDto> items = data.getItems();
                int maxNum = Math.min(5, data.getItems().size());

                ArrayList<ComicBookDto> newItems = new ArrayList<>();

                Random rng = new Random();
                Set<Integer> generated = new LinkedHashSet<>();
                while (generated.size() < maxNum) {
                    Integer next = rng.nextInt(maxNum);
                    generated.add(next);
                }

                for(Integer index : generated) {
                    newItems.add(items.get(index));
                }


                adapter.add(newItems);
                adapter.notifyDataSetChanged();
            } else {
                similarView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(Loader<ComicBookListDto> loader) {

        }
    };

    private String mQuery;

    private SimilarView similarView;

    public static SimilarFragment newInstance(String query) {
        Bundle data = new Bundle();
        data.putString("q", query);

        SimilarFragment fragment = new SimilarFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null) {
            mQuery = getArguments().getString("q");
        }

        similarView = (SimilarView) view.findViewById(R.id.list);
        similarView.setHasFixedSize(true);
        similarView.setAdapter(adapter);
        similarView.addItemDecoration(new PaddingItemDecoration(3));

        getLoaderManager().restartLoader(mQuery.hashCode(), null, mSearchLoader);
    }


    public int getViewResource() {
        return R.layout.fragment_similar;
    }

    public int getLoaderId() {
        return ("q" + mQuery).hashCode();
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
