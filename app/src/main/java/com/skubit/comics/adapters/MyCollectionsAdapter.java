package com.skubit.comics.adapters;

import com.skubit.comics.ItemClickListener;
import com.skubit.comics.R;
import com.skubit.comics.provider.collection.CollectionCursor;
import com.skubit.comics.provider.collectionmapping.CollectionMappingSelection;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class MyCollectionsAdapter
        extends CursorRecyclerViewAdapter<MyCollectionsAdapter.MyCollectionHolder> {

    private final ItemClickListener mItemClickListener;

    public MyCollectionsAdapter(Context context, Cursor cursor,
            ItemClickListener itemClickListener) {
        super(context, cursor);
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyCollectionHolder viewHolder,
            Cursor cursor) {
        final CollectionCursor collectionCursor = new CollectionCursor(cursor);
        viewHolder.name.setText(collectionCursor.getName());
        viewHolder.position = cursor.getPosition();
        viewHolder.cid = collectionCursor.getCid();

        CollectionMappingSelection cms = new CollectionMappingSelection();
        cms.cid(collectionCursor.getCid());

        final Cursor collectionMappingCursor = cms.query(mContext.getContentResolver());
        viewHolder.count.setText(collectionMappingCursor.getCount() + " Comics¬");
        collectionMappingCursor.close();

        String coverArt = collectionCursor.getCoverart();

        if (!TextUtils.isEmpty(coverArt)) {
            Picasso.with(mContext).load(new File(coverArt)).centerCrop().resize(200, 200).into(
                    viewHolder.coverArt);//350,500
        }

    }

    @Override
    public MyCollectionHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.my_collections_item, parent, false);
        return new MyCollectionHolder(view);
    }

    public class MyCollectionHolder extends RecyclerView.ViewHolder {

        public final TextView name;

        public final ImageView coverArt;

        public int position;

        public String cid;

        public TextView count;

        public MyCollectionHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.comicCount);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(v, position, mCursor);
                }
            });

            View comicOptions = view.findViewById(R.id.collection_options);
            comicOptions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putString("cid", cid);
                    data.putString("title", name.getText().toString());
                    mItemClickListener.onClickOption(v, data);
                }
            });
        }
    }
}
