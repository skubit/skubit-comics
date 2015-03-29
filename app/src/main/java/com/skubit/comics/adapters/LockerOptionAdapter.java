package com.skubit.comics.adapters;

public class LockerOptionAdapter extends BaseOptionAdapter {

    private final String[] mItems = new String[]{"Download", "Open"};

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
