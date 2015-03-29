package com.skubit.comics.activities;


import com.skubit.comics.BuildConfig;
import com.skubit.comics.fragments.AddToCollectionFragment;
import com.skubit.dialog.ProgressActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddToCollectionActivity extends ProgressActivity<Bundle> {

    public static Intent newInstance(String cbid, String name) {
        Intent i = new Intent();
        i.setClassName(BuildConfig.APPLICATION_ID, AddToCollectionActivity.class.getName());
        i.putExtra("cbid", cbid);
        i.putExtra("name", name);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(AddToCollectionFragment.newInstance(getIntent().getExtras()),
                "AddToCollection");
    }

    @Override
    public void load(Bundle data, int type) {

    }
}
