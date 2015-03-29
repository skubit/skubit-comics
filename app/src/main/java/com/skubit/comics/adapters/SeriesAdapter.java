package com.skubit.comics.adapters;

import com.skubit.comics.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder> {

    @Override
    public SeriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SeriesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SeriesHolder extends RecyclerView.ViewHolder {

        public final TextView name;

        public int position;

        public SeriesHolder(final View view) {
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
