package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.fragments.DeleteFromCollectionFragment;
import com.skubit.comics.provider.collectionmapping.CollectionMappingSelection;
import com.skubit.dialog.ProgressActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class DeleteFromCollectionActivity extends ProgressActivity<Bundle> {

    public static Intent newInstance(String cid, String cbid) {
        Intent i = new Intent();
        i.putExtra("cid", cid);
        i.putExtra("cbid", cbid);
        i.setClassName(BuildConfig.APPLICATION_ID, DeleteFromCollectionActivity.class.getName());
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment frag = getFragmentManager().findFragmentByTag(mUIState);
        if (frag != null) {
            replaceFragment(frag, mUIState);
        } else {
            replaceFragment(new DeleteFromCollectionFragment(),
                    "DeleteFromCollection");
        }
    }

    @Override
    public void load(Bundle data, int type) {
        //TODO: move off main thread
        Bundle d = getIntent().getExtras();
        CollectionMappingSelection cs = new CollectionMappingSelection();
        cs.cid(d.getString("cid"));
        cs.cbid(d.getString("cbid"));
        cs.delete(getContentResolver());
        finish();
    }
}
