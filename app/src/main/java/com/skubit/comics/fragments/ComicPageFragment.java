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

import com.skubit.comics.R;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public final class ComicPageFragment extends Fragment {

    private static final String COVER_ART_URL_EXTRA = "com.skubit.comics.COVER_ART_URL_EXTRA";

    private static final String PAGE_NUMBER_EXTRA = "com.skubit.comics.PAGE_NUMBER_EXTRA";

    private static final String TOTAL_PAGES_EXTRA = "com.skubit.comics.TOTAL_PAGES_EXTRA";

    private String mCoverArtUrl;

    private int mPageNum;

    private int mTotalPages;

    private View mComicPageView;

    private int mLongestDim;

    public ComicPageFragment() {
    }

    public static ComicPageFragment newInstance(String coverArt, int pageNum, int total) {
        ComicPageFragment f = new ComicPageFragment();

        Bundle args = new Bundle();
        args.putString(COVER_ART_URL_EXTRA, coverArt);
        args.putInt(PAGE_NUMBER_EXTRA, pageNum);
        args.putInt(TOTAL_PAGES_EXTRA, total);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mCoverArtUrl = args.getString(COVER_ART_URL_EXTRA);
            mPageNum = args.getInt(PAGE_NUMBER_EXTRA);
            mTotalPages = args.getInt(TOTAL_PAGES_EXTRA);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mLongestDim = (height > width ? height : width) - 50;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mComicPageView = inflater.inflate(R.layout.fragment_comic_page, null);
        ImageView coverArt = (ImageView) mComicPageView.findViewById(R.id.coverArt);
        TextView pageNumberView = (TextView) mComicPageView.findViewById(R.id.pageNum);
        pageNumberView.setText(mPageNum + "/" + mTotalPages);

        Picasso.with(getActivity()).load(new File(mCoverArtUrl))
                .resize(mLongestDim, mLongestDim).centerInside().into(coverArt);
        return mComicPageView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (OnClickListener.class.isInstance(getActivity())) {
            mComicPageView.setOnClickListener((OnClickListener) getActivity());
        }
    }
}
