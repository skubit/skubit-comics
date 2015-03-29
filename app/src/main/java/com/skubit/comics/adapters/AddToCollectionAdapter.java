package com.skubit.comics.adapters;

import com.skubit.comics.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddToCollectionAdapter
        extends CursorRecyclerViewAdapter<AddToCollectionAdapter.CollectionHolder> {

    public AddToCollectionAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public CollectionHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.my_comics_item, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionHolder viewHolder,
            Cursor cursor) {

    }

    public class CollectionHolder extends RecyclerView.ViewHolder {

        public final TextView name;

        public int position;

        public CollectionHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
