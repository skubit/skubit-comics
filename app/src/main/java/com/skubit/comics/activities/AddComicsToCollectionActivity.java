package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.fragments.AddComicsToCollectionFragment;
import com.skubit.dialog.ProgressActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddComicsToCollectionActivity extends ProgressActivity<Bundle> {

    public static Intent newInstance(String cid, String name) {
        Intent i = new Intent();
        i.setClassName(BuildConfig.APPLICATION_ID, AddComicsToCollectionActivity.class.getName());
        i.putExtra("cid", cid);
        i.putExtra("name", name);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(AddComicsToCollectionFragment.newInstance(getIntent().getExtras()),
                "AddComicsToCollection");
    }

    @Override
    public void load(Bundle data, int type) {

    }
}
