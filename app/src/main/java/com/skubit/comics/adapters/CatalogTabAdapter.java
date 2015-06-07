package com.skubit.comics.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class CatalogTabAdapter extends FragmentStatePagerAdapter {

    public CatalogTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {//Featured

        } else if (position == 1) {//Latest

        } else if (position == 2) {//FREE

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
