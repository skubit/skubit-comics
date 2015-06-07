package com.skubit.comics.adapters;

import com.skubit.comics.activities.ScreenshotsActivity;
import com.skubit.shared.dto.UrlDto;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class ScreenshotAdapter2 extends BaseAdapter {

    private final Activity mContext;

    private ArrayList<UrlDto> mUrls = new ArrayList<>();

    public ScreenshotAdapter2(Activity context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    public void setUrls(ArrayList<UrlDto> urls) {
        mUrls = urls;
    }

    @Override
    public UrlDto getItem(int position) {
        return mUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setPadding(5, 5, 5, 5);

        final String u = mUrls.get(position).getUrl() + "=-rw";
        Picasso.with(mContext).load(u).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle opts = ActivityOptions.makeScaleUpAnimation(
                        imageView,
                        0, 0,
                        imageView.getWidth(), imageView.getHeight()).toBundle();
                mContext.startActivity(ScreenshotsActivity.newInstance(mUrls, position), opts);
            }
        });



        return imageView;
    }
}
