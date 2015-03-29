package com.skubit.comics.adapters;

import com.skubit.comics.ComicForCollection;
import com.skubit.comics.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AddComicsToCollectionAdapter
        extends BaseAdapter {

    private final Context mContext;

    private ArrayList<ComicForCollection> mItems = new ArrayList<ComicForCollection>();

    public AddComicsToCollectionAdapter(Context context) {
        mContext = context;
    }

    public CollectionHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.my_comics_item, parent, false);
        return new CollectionHolder(view);
    }

    public ArrayList<ComicForCollection> getItems() {
        return new ArrayList(mItems);
    }

    public void setItems(ArrayList<ComicForCollection> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ComicForCollection getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CollectionHolder collectionHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.comics_to_collection_item, parent, false);
            collectionHolder = new CollectionHolder(convertView);
        } else {
            collectionHolder = (CollectionHolder) convertView.getTag();
        }

        ComicForCollection cfc = getItem(position);
        collectionHolder.name.setText(cfc.title);

        if (!TextUtils.isEmpty(cfc.coverArt)) {
            Picasso.with(mContext).load(new File(cfc.coverArt))
                    .resize(200, 300).into(collectionHolder.coverArt);
        }

        collectionHolder.position = position;

        collectionHolder.mCheck
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ComicForCollection cfc = getItem(position);
                        cfc.isChecked = isChecked;
                    }
                });

        collectionHolder.mCheck.setChecked(cfc.isChecked);

        convertView.setTag(collectionHolder);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public class CollectionHolder {

        //todo: boolean isChecked
        public final TextView name;

        public final ImageView coverArt;

        public final CheckBox mCheck;

        public int position;

        public CollectionHolder(final View view) {
            name = (TextView) view.findViewById(R.id.title);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);
            mCheck = (CheckBox) view.findViewById(R.id.checkBox);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
