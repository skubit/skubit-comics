package com.skubit.comics;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

//https://gist.githubusercontent.com/ssinss/e06f12ef66c51252563e/raw/f2f82b7eff8e359dd05f6170a3c161be2792f11f/EndlessRecyclerOnScrollListener.java
public abstract class GridRecyclerScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = GridRecyclerScrollListener.class.getSimpleName();

    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
            // True if we are still waiting for the last set of data to load.

    private boolean loading = true;
            // The minimum amount of items to have below your current scroll position before loading more.

    private int visibleThreshold = 5;

    private GridLayoutManager mLinearLayoutManager;

    public GridRecyclerScrollListener(GridLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();
}

