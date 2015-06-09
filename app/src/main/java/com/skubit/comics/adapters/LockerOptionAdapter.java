package com.skubit.comics.adapters;

public class LockerOptionAdapter extends BaseOptionAdapter {

    private static final String[] mAllItems = new String[]{"Download", "Open"};

    private static final String[] mDownloadItems = new String[]{"Download"};


    private String[] mItems;

    public LockerOptionAdapter(boolean onlyDownload) {
        mItems = (onlyDownload) ? mDownloadItems : mAllItems;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getItem(int position) {
        return mItems[position];
    }

}
