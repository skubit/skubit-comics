package com.skubit.comics.activities;


import com.skubit.comics.BuildConfig;
import com.skubit.comics.fragments.DeleteCollectionsFragment;
import com.skubit.comics.provider.collection.CollectionSelection;
import com.skubit.dialog.ProgressActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class DeleteCollectionActivity extends ProgressActivity<Bundle> {

    public static Intent newInstance(String cid, String title) {
        Intent i = new Intent();
        i.putExtra("cid", cid);
        i.putExtra("title", title);
        i.setClassName(BuildConfig.APPLICATION_ID, DeleteCollectionActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment frag = getFragmentManager().findFragmentByTag(mUIState);
        if (frag != null) {
            replaceFragment(frag, mUIState);
        } else {
            replaceFragment(DeleteCollectionsFragment.newInstance(getIntent().getExtras()), "DeleteCollection");
        }

    }

    @Override
    public void load(Bundle data, int type) {
        //TODO: move off main thread
        CollectionSelection cs = new CollectionSelection();
        cs.cid(data.getString("cid"));
        cs.delete(getContentResolver());
        finish();

    }
}
