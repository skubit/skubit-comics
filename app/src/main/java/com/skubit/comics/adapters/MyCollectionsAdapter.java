package com.skubit.comics.adapters;

import com.skubit.comics.ItemClickListener;
import com.skubit.comics.R;
import com.skubit.comics.provider.collection.CollectionCursor;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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
        CollectionCursor cc = new CollectionCursor(cursor);
        viewHolder.name.setText(cc.getName());
        viewHolder.position = cursor.getPosition();
        viewHolder.cid = cc.getCid();

        String coverArt = cc.getCoverart();

        if (!TextUtils.isEmpty(coverArt)) {
            Picasso.with(mContext).load(new File(coverArt))
                    .resize(200, 300).into(viewHolder.coverArt);//350,500
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

        public MyCollectionHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
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
