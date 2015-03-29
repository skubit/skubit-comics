package com.skubit.comics.loaders;

import com.skubit.comics.ComicForCollection;
import com.skubit.comics.provider.collectionmapping.CollectionMappingColumns;
import com.skubit.comics.provider.collectionmapping.CollectionMappingCursor;
import com.skubit.comics.provider.comic.ComicColumns;
import com.skubit.comics.provider.comic.ComicCursor;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;

public class AddComicsToCollectionReadLoader extends BaseLoader<ArrayList<ComicForCollection>> {

    private final String mCid;

    private Context mContext;

    public AddComicsToCollectionReadLoader(Context context, String cid) {
        super(context);
        mContext = context;
        mCid = cid;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ArrayList<ComicForCollection> loadInBackground() {
        Cursor collectionCursor =
                mContext.getContentResolver().query(CollectionMappingColumns.CONTENT_URI, null,
                        CollectionMappingColumns.CID + " =?",
                        new String[]{mCid}, null);
        CollectionMappingCursor cmc = new CollectionMappingCursor(collectionCursor);
        String[] comicIds = new String[collectionCursor.getCount()];
        int length = comicIds.length;
        for (int i = 0; i < length; i++) {
            cmc.moveToPosition(i);
            comicIds[i] = cmc.getCbid();
        }

        Cursor comicCursor = mContext.getContentResolver()
                .query(ComicColumns.CONTENT_URI, null, null, null, null);
        ComicCursor cc = new ComicCursor(comicCursor);
        ArrayList<ComicForCollection> collection = new ArrayList<>();
        for (int i = 0; i < cc.getCount(); i++) {
            cc.moveToPosition(i);
            ComicForCollection comicForCollection = new ComicForCollection();
            comicForCollection.title = cc.getStoryTitle();
            comicForCollection.coverArt = cc.getCoverArt();
            comicForCollection.cbid = cc.getCbid();

            if (contains(cc.getCbid(), comicIds)) {
                comicForCollection.isChecked = true;
            }
            collection.add(comicForCollection);
        }
        cc.close();
        cmc.close();
        return collection;
    }

    private boolean contains(String comicId, String[] comicIds) {
        for (String cid : comicIds) {
            if (comicId.equals(cid)) {
                return true;
            }
        }
        return false;
    }
}
