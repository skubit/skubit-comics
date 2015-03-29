package com.skubit.comics.adapters;

public class MyCollectionsOptionAdapter extends BaseOptionAdapter {

    private final String[] mItems = new String[]{"Add Comics", "Delete Collection"};

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
