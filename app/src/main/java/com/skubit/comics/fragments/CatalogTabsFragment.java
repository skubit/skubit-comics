package com.skubit.comics.fragments;

import com.astuetz.PagerSlidingTabStrip;
import com.skubit.comics.ComicFilter;
import com.skubit.comics.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CatalogTabsFragment extends Fragment {

    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    private MyPagerAdapter adapter;

    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRetainInstance(true);
        }

        adapter = new MyPagerAdapter(getChildFragmentManager());

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog_tabs, container, false);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        pager = (ViewPager) v.findViewById(R.id.catalog_pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        mPagerSlidingTabStrip .setViewPager(pager);
        return v;
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"Home", "New Releases", "Paid",
                "Free"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            ComicFilter filter = new ComicFilter();
            if(position == 0) {
                filter.isFeatured = true;
            } else if(position == 1) {
              //  filter.isFeatured = true;
            } else if(position == 2) {
                filter.isPaid = true;
            } else if(position == 3) {
                filter.isFree = true;
            }

            return CatalogFragment.newInstance(filter);
        }
    }
}
