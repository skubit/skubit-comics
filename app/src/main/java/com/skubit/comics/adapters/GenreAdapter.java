package com.skubit.comics.adapters;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.R;
import com.skubit.shared.dto.GenreDto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.Holder>
        implements ICatalogAdapter<GenreDto> {

    private final Context mContext;

    private final ClickComicListener mClickComicListener;

    private ArrayList<GenreDto> mDtos = new ArrayList<>();

    public GenreAdapter(Context context, ClickComicListener clickComicListener) {
        mContext = context;
        mClickComicListener = clickComicListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.genre_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        GenreDto genreDto = mDtos.get(position);
        holder.name.setText(genreDto.getGenreName());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mDtos.size();
    }

    public GenreDto get(int position) {
        if (mDtos != null && mDtos.size() > position) {
            return mDtos.get(position);
        }
        return null;
    }

    @Override
    public void add(ArrayList<GenreDto> dtos) {
        mDtos.addAll(dtos);
    }

    public class Holder extends RecyclerView.ViewHolder {

        public final TextView name;

        public int position;

        public Holder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickComicListener.onClick(view, position);
                }
            });
        }
    }

}
