package com.skubit.comics.adapters;


public class MyComicsOptionAdapter extends BaseOptionAdapter {

    private final String[] mItems = new String[]{"Add To Collection", "Delete"};

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
