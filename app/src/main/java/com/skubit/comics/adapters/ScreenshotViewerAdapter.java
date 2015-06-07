package com.skubit.comics.adapters;

import com.skubit.comics.fragments.ScreenshotPageFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class ScreenshotViewerAdapter extends FragmentStatePagerAdapter {

    private String[] mUrls = new String[0];

    public ScreenshotViewerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setUrls(String[] urls) {
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        ScreenshotPageFragment frag = ScreenshotPageFragment
                .newInstance(mUrls[position]);
        return frag;
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
