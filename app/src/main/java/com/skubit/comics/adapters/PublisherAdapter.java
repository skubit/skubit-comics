package com.skubit.comics.adapters;

import com.skubit.comics.ClickComicListener;
import com.skubit.comics.FontManager;
import com.skubit.comics.ICatalogAdapter;
import com.skubit.comics.R;
import com.skubit.shared.dto.PublisherDto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.Holder>
        implements ICatalogAdapter<PublisherDto> {

    private final Context mContext;

    private final ClickComicListener mClickComicListener;

    private ArrayList<PublisherDto> mDtos = new ArrayList<>();

    public PublisherAdapter(Context context,
            ClickComicListener clickComicListener) {
        mContext = context;
        mClickComicListener = clickComicListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.publisher_item, parent, false);
        return new Holder(view);
    }

    @Override
    public int getItemCount() {
        return mDtos != null ? mDtos.size() : 0;
    }

    @Override
    public void add(ArrayList<PublisherDto> dtos) {
        this.mDtos.addAll(dtos);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PublisherDto dto = mDtos.get(position);
        holder.name.setText(dto.getPublisherName());
        holder.name.setTypeface(FontManager.REGULAR);
        holder.position = position;
    }

    public PublisherDto get(int position) {
        if (mDtos != null && mDtos.size() > position) {
            return mDtos.get(position);
        }
        return null;
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
