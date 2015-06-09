package com.skubit.comics.adapters;


import com.skubit.comics.FontManager;
import com.skubit.comics.ItemClickListener;
import com.skubit.comics.R;
import com.skubit.comics.provider.comic.ComicCursor;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MyComicsAdapter extends CursorRecyclerViewAdapter<MyComicsAdapter.MyComicsHolder> {

    private final ItemClickListener mItemClickListener;

    private final Picasso mPicasso;

    public MyComicsAdapter(Context context, Cursor cursor, ItemClickListener itemClickListener) {
        super(context, cursor);
        mItemClickListener = itemClickListener;
        mPicasso = Picasso.with(mContext);
    }

    @Override
    public void onBindViewHolder(MyComicsAdapter.MyComicsHolder holder,
            Cursor cursor) {
        ComicCursor c = new ComicCursor(cursor);

        holder.storyTitle.setText(c.getStoryTitle());
        holder.storyTitle.setTypeface(FontManager.REGULAR);
        holder.position = c.getPosition();
        holder.cbid = c.getCbid();

        if(c.getIsSample() != null && c.getIsSample()) {
            holder.showSample();
        } else {
            holder.hideSample();
        }
        String coverArt = c.getCoverArt();

        if (!TextUtils.isEmpty(coverArt)) {
            mPicasso.load(new File(coverArt)).resize(280, 0)
                    .into(holder.coverArt);
        }
    }

    @Override
    public MyComicsAdapter.MyComicsHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.my_comics_item, parent, false);
        return new MyComicsHolder(view);
    }

    public class MyComicsHolder extends RecyclerView.ViewHolder {

        public final TextView storyTitle;

        public final ImageView coverArt;

        private final TextView sampleLabel;

        public int position;

        public String cbid;

        public void showSample() {
            sampleLabel.setVisibility(View.VISIBLE);
        }

        public void hideSample() {
            sampleLabel.setVisibility(View.INVISIBLE);
        }

        public MyComicsHolder(final View view) {
            super(view);
            storyTitle = (TextView) view.findViewById(R.id.title);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);
            sampleLabel = (TextView) view.findViewById(R.id.sampleLabel);
            View bottom = view.findViewById(R.id.button_bottom);
            /*
            bottom.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putBoolean("isCollectionItem", false);
                    data.putString("cbid", cbid);
                    data.putString("title", storyTitle.getText().toString());
                    mItemClickListener.onClickOption(v, data);
                }
            });

            View comicOptions = view.findViewById(R.id.my_comics_options);
            comicOptions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();

                    data.putString("cbid", cbid);
                    data.putString("title", storyTitle.getText().toString());
                    mItemClickListener.onClickOption(v, data);
                }
            });
            */
             view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(v, position, mCursor);
                }
            });
        }
    }
}
