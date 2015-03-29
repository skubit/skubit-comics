package com.skubit.comics.adapters;


public class MyCollectionsComicOptionAdapter extends BaseOptionAdapter {

    private final String[] mItems = new String[]{"Delete From Collection"};

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
