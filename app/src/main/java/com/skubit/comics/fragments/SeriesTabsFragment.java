package com.skubit.comics.fragments;

import com.astuetz.PagerSlidingTabStrip;
import com.skubit.comics.R;
import com.skubit.comics.SeriesFilter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeriesTabsFragment extends Fragment {

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
        View v = inflater.inflate(R.layout.fragment_series_tabs, container, false);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        pager = (ViewPager) v.findViewById(R.id.series_pager);

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        mPagerSlidingTabStrip .setViewPager(pager);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  pager.setCurrentItem(0);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"A-E", "F-K",
                "L-Q", "R-V", "W-Z"};

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
            SeriesFilter filter = new SeriesFilter();
            filter.alphaBucket= position + 1;

            return SeriesFragment.newInstance(filter);
        }
    }
}
