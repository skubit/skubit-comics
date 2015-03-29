package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicForCollection;
import com.skubit.comics.fragments.CreateCollectionsFragment;
import com.skubit.comics.loaders.CreateCollectionsLoader;
import com.skubit.dialog.ProgressActivity;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;


public class CreateCollectionActivity extends ProgressActivity<Bundle> {

    private final LoaderManager.LoaderCallbacks<ComicForCollection> mCallback
            = new LoaderManager.LoaderCallbacks<ComicForCollection>() {

        @Override
        public Loader<ComicForCollection> onCreateLoader(int id, Bundle args) {
            return new CreateCollectionsLoader(getBaseContext(),
                    args.getString("collection"));
        }

        @Override
        public void onLoadFinished(Loader<ComicForCollection> loader, ComicForCollection data) {
            startActivity(
                    AddComicsToCollectionActivity.newInstance(data.cid, data.title));
            finish();
        }

        @Override
        public void onLoaderReset(Loader<ComicForCollection> loader) {

        }
    };

    public static Intent newInstance() {
        Intent i = new Intent();
        i.setClassName(BuildConfig.APPLICATION_ID, CreateCollectionActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment frag = getFragmentManager().findFragmentByTag(mUIState);
        if (frag != null) {
            replaceFragment(frag, mUIState);
        } else {
            replaceFragment(new CreateCollectionsFragment(), "CreateCollections");
        }

    }

    @Override
    public void load(Bundle data, int type) {
        getLoaderManager().initLoader(8, data, mCallback);
    }
}
