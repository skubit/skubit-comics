package com.skubit.comics.loaders;

import com.skubit.comics.ComicForCollection;
import com.skubit.comics.provider.collection.CollectionContentValues;
import com.skubit.comics.provider.collection.CollectionSelection;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingContentValues;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

public class AddComicsToCollectionWriteLoader extends BaseLoader<Boolean> {

    private final String mCid;

    private final ArrayList<ComicForCollection> mComics;

    private Context mContext;

    public AddComicsToCollectionWriteLoader(Context context, String cid,
            ArrayList<ComicForCollection> comics) {
        super(context);
        mContext = context;
        mCid = cid;
        mComics = comics;
    }

    @Override
    protected void closeStream() throws IOException {

    }


    @Override
    public Boolean loadInBackground() {
        getContext().getContentResolver().delete(CollectionMappingColumns.CONTENT_URI,
                CollectionMappingColumns.CID + " =?",
                new String[]{mCid});

        boolean hasCover = false;
        for (ComicForCollection cfc : mComics) {

            if (cfc.isChecked) {
                if (!hasCover) {
                    CollectionSelection where = new CollectionSelection();
                    where.cid(mCid);

                    CollectionContentValues cv = new CollectionContentValues();
                    cv.putCoverart(cfc.coverArt);
                    cv.update(mContext.getContentResolver(), where);
                    hasCover = true;
                }
                CollectionMappingContentValues cv = new CollectionMappingContentValues();
                cv.putCid(mCid);
                cv.putCbid(cfc.cbid);
                //TODO: bulk
                cv.insert(mContext.getContentResolver());
            }
        }

        return true;
    }
}
