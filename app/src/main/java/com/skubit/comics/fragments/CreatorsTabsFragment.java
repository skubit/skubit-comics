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

public class CreatorsTabsFragment extends Fragment {

    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    private MyPagerAdapter adapter;

    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_creators_tabs, container, false);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        pager = (ViewPager) v.findViewById(R.id.creators_pager);

        adapter = new MyPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        mPagerSlidingTabStrip .setViewPager(pager);

        return v;
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"A-E", "F-K",
                "L-Q", "R-W", "X-Z"};

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
            filter.alphaBucket = position + 1;

            return CreatorFragment.newInstance(filter);
        }
    }
}
