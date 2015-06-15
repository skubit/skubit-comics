package com.skubit.comics.adapters;

import com.skubit.comics.ItemClickListener;
import com.skubit.comics.R;
import com.skubit.shared.dto.LockerItemDto;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LockerAdapter extends RecyclerView.Adapter<LockerAdapter.LockerItemViewHolder> {

    private final Context mContext;

    private final ItemClickListener mItemClickListener;

    private ArrayList<LockerItemDto> mLockerItemsDtos = new ArrayList<>();

    public LockerAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    @Override
    public LockerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.locker_item, parent, false);
        return new LockerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LockerItemViewHolder holder, int position) {
        LockerItemDto lockerItemDto = mLockerItemsDtos.get(position);

        holder.storyTitle.setText(lockerItemDto.getTitle()
                + " Vol." + lockerItemDto.getVolume() + " #" + lockerItemDto.getIssue());
        holder.cbid = lockerItemDto.getProductId();

        String coverArt = lockerItemDto.getCoverArt() +"=-rw";

        if (!TextUtils.isEmpty(coverArt)) {
            Picasso.with(mContext).load(coverArt)
                    .resize(200, 300).into(holder.coverArt);//350,500
        }
    }

    @Override
    public int getItemCount() {
        return mLockerItemsDtos.size();
    }

    public void clear() {
        mLockerItemsDtos.clear();
    }

    public void add(ArrayList<LockerItemDto> lockerItemsDtos) {
        mLockerItemsDtos.addAll(lockerItemsDtos);
    }

    public class LockerItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView storyTitle;

        public final ImageView coverArt;

        public String cbid;

        public LockerItemViewHolder(final View view) {
            super(view);
            storyTitle = (TextView) view.findViewById(R.id.title);
            coverArt = (ImageView) view.findViewById(R.id.coverArt);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

              //      Bundle data = new Bundle();
              //      data.putString("cbid", cbid);
              //      data.putString("title", storyTitle.getText().toString());
              //      mItemClickListener.onClickOption(v, data);
                }
            });

            View lockerOptions = view.findViewById(R.id.locker_options);
            lockerOptions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putString("cbid", cbid);
                    data.putString("title", storyTitle.getText().toString());
                    mItemClickListener.onClickOption(v, data);
                }
            });
        }
    }
}
