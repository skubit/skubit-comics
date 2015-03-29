package com.skubit.comics.loaders;

import com.skubit.comics.CodeGenerator;
import com.skubit.comics.ComicForCollection;
import com.skubit.comics.provider.collection.CollectionColumns;
import com.skubit.comics.provider.collection.CollectionContentValues;

import android.content.Context;

import java.io.IOException;

public class CreateCollectionsLoader extends BaseLoader {

    private final String mName;

    public CreateCollectionsLoader(Context context, String name) {
        super(context);
        mName = name;
    }

    @Override
    protected void closeStream() throws IOException {

    }

    @Override
    public ComicForCollection loadInBackground() {
        String cid = CodeGenerator.generateCode(6);

        CollectionContentValues cv = new CollectionContentValues();
        cv.putName(mName);
        cv.putCid(cid);
        getContext().getContentResolver()
                .insert(CollectionColumns.CONTENT_URI, cv.values());

        ComicForCollection collection = new ComicForCollection();
        collection.cid = cid;
        collection.title = mName;
        return collection;
    }
}
