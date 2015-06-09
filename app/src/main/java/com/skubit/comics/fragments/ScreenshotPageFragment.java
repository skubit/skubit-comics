package com.skubit.comics.fragments;

import com.skubit.comics.R;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ScreenshotPageFragment extends Fragment {

    private static final String COVER_ART_URL_EXTRA = "com.skubit.comics.COVER_ART_URL_EXTRA";

    private String mCoverArtUrl;

    private View mComicPageView;

    private ImageView mCoverArt;

    private int mLongestDim;

    public ScreenshotPageFragment() {
    }

    public static ScreenshotPageFragment newInstance(String coverArt) {
        ScreenshotPageFragment f = new ScreenshotPageFragment();

        Bundle args = new Bundle();
        args.putString(COVER_ART_URL_EXTRA, coverArt);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCoverArtUrl = args.getString(COVER_ART_URL_EXTRA);
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
        mComicPageView = inflater.inflate(R.layout.fragment_screenshot_page, container, false);
        mCoverArt = (ImageView) mComicPageView.findViewById(R.id.coverArt);
        Picasso.with(getActivity()).load(mCoverArtUrl + "=s" + mLongestDim + "-rw").into(mCoverArt);

        return mComicPageView;
    }
}

