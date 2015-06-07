package com.skubit.comics.adapters;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.FontManager;
import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.R;
import com.skubit.shared.dto.SeriesDto;
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

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.Holder>
        implements ICatalogAdapter<SeriesDto> {

    private final Context mContext;

    private final ClickComicListener mClickComicListener;

    private final String mSeriesSize;

    private ArrayList<SeriesDto> mDtos = new ArrayList<>();

    public SeriesAdapter(Context context, ClickComicListener clickComicListener) {
        mContext = context;
        mClickComicListener = clickComicListener;
        mSeriesSize = context.getString(R.string.series_image_size);
    }
    
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.series_item, parent, false);
        return new Holder(view);
    }

    @Override
    public int getItemCount() {
        return mDtos.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SeriesDto dto = mDtos.get(position);
        holder.name.setText(dto.getSeriesName());
        holder.name.setTypeface(FontManager.REGULAR);

        if (!TextUtils.isEmpty(dto.getCoverArt())) {
            Picasso.with(mContext).load(dto.getCoverArt() + "=" + mSeriesSize + "-rw")
                    .into(holder.coverArt);//350,500
        }
        holder.position = position;
    }

    public SeriesDto get(int position) {
        if (mDtos != null && mDtos.size() > position) {
            return mDtos.get(position);
        }
        return null;
    }

    @Override
    public void add(ArrayList<SeriesDto> dtos) {
        mDtos.addAll(dtos);
    }

    public class Holder extends RecyclerView.ViewHolder {

        public final TextView name;

        public final ImageView coverArt;

        public int position;

        public Holder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
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
