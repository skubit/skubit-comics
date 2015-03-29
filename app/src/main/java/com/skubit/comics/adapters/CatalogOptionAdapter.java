package com.skubit.comics.adapters;

public class CatalogOptionAdapter extends BaseOptionAdapter {

    private final String[] mItems = new String[]{"Buy"};

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
