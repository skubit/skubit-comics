package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.dialog.ProgressActivity;

import android.content.Intent;
import android.os.Bundle;

public class UnarchiveLoadingActivity extends ProgressActivity<Bundle> {

    public static Intent newInstance() {
        Intent i = new Intent();
        i.setClassName(BuildConfig.APPLICATION_ID, UnarchiveLoadingActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgress();
    }

    @Override
    public void load(Bundle data, int type) {

    }
}
