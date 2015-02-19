/* Copyright 2015 Skubit
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.skubit.comics.adapters;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.FontManager;
import com.skubit.comics.R;
import com.skubit.comics.provider.comicsarchive.ComicsArchiveCursor;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Adapter for displaying of imported comic books
 */
public final class MyComicsAdapter extends RecyclerView.Adapter<MyComicsAdapter.ComicViewHolder> {

    private final ComicsArchiveCursor mArchiveCursor;

    private final Context mContext;

    private final ClickComicListener mClickComicListener;

    public MyComicsAdapter(Context context,
            final ComicsArchiveCursor archiveCursor, ClickComicListener clickComicListener) {
        mContext = context;
        mArchiveCursor = archiveCursor;
        mClickComicListener = clickComicListener;

        archiveCursor.registerContentObserver(new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                archiveCursor.requery();
                notifyDataSetChanged();
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                archiveCursor.requery();
                notifyDataSetChanged();
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                archiveCursor.requery();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.my_comics_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        mArchiveCursor.moveToPosition(position);
        holder.storyTitle.setText(mArchiveCursor.getStoryTitle());
        holder.storyTitle.setTypeface(FontManager.REGULAR);
        holder.position = position;
        String coverArt = mArchiveCursor.getCoverArt();

        if (!TextUtils.isEmpty(coverArt)) {
            Picasso.with(mContext).load(new File(coverArt))
                    .resize(200, 200).centerInside().into(holder.coverArt);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mArchiveCursor.getCount();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        public final TextView storyTitle;

        public final ImageView coverArt;

        public int position;

        public ComicViewHolder(final View view) {
            super(view);
            storyTitle = (TextView) view.findViewById(R.id.title);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickComicListener.onClick(view, position);
                }
            });
        }
    }
}
