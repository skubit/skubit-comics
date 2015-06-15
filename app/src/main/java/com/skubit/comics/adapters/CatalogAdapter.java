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
import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.R;
import com.skubit.currencies.Bitcoin;
import com.skubit.currencies.Satoshi;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.IssueFormat;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for displaying of comic books
 */
public final class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ComicViewHolder>
        implements
        ICatalogAdapter<ComicBookDto> {

    private final Context mContext;

    private final ClickComicListener mClickComicListener;

    private ArrayList<ComicBookDto> mComicBookDtos = new ArrayList<>();

    public CatalogAdapter(Context context, ClickComicListener clickComicListener) {
        mContext = context;
        mClickComicListener = clickComicListener;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.catalog_item, parent, false);
        return new ComicViewHolder(view);
    }

    public ComicBookDto get(int position) {
        if (mComicBookDtos != null && mComicBookDtos.size() > position) {
            return mComicBookDtos.get(position);
        }
        return null;
    }

    public void add(ArrayList<ComicBookDto> comicBookDtos) {
        mComicBookDtos.addAll(comicBookDtos);
    }

    @Override
    public void onBindViewHolder(final ComicViewHolder holder, int position) {
        ComicBookDto comicBookDto = mComicBookDtos.get(position);

        if (IssueFormat.GraphicNovel.equals(comicBookDto.getIssueFormat())) {
            holder.storyTitle.setText(comicBookDto.getStoryTitle() + " GN");
        } else {
            boolean noVol = "0".equals(comicBookDto.getVolume());
            boolean noIssue = 0 == comicBookDto.getIssueNumber();

            String issue = null;
            if (noVol && noIssue) {
                issue = "";
            } else if (noVol && !noIssue) {
                issue = " #" + comicBookDto.getIssueNumber();
            } else if (!noVol && noIssue) {
                issue = " Vol " + comicBookDto.getVolume();
            } else {
                issue = " Vol " + comicBookDto.getVolume() +
                        " #"
                        + comicBookDto.getIssueNumber();
            }

            holder.storyTitle
                    .setText(comicBookDto.getStoryTitle() + issue);
        }

        holder.position = position;
        if (comicBookDto.isPurchased()) {
            holder.price.setText("Purchased");
        } else if (comicBookDto.getSatoshi() == 0 && comicBookDto.getPrice() == 0) {
            holder.price.setText("Free");
        } else {
            String currencySymbol = comicBookDto.getCurrencySymbol();
            if ("USD".equals(currencySymbol)) {
                holder.price.setText("$" + String.format("%.2f", comicBookDto.getPrice()));
            } else if ("EUR".equals(currencySymbol)) {

            } else {
                holder.price.setText(
                        new Bitcoin(new Satoshi(comicBookDto.getSatoshi())).getDisplay() + " BTC");
            }

        }

        String coverArt = comicBookDto.getCoverArtUrl() + "=s300-rw";

        if (!TextUtils.isEmpty(coverArt)) {
            Picasso.with(mContext).load(coverArt).
                    into(holder.coverArt);//350,500.resize(280, 0).
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mComicBookDtos != null ? mComicBookDtos.size() : 0;//mArchiveCursor.getCount();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        public final TextView storyTitle;

        public final ImageView coverArt;

        public final TextView price;

        public int position;

        public ComicViewHolder(final View view) {
            super(view);
            storyTitle = (TextView) view.findViewById(R.id.title);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);
            price = (TextView) view.findViewById(R.id.price);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickComicListener.onClick(view, position);
                }
            });
        }
    }
}
