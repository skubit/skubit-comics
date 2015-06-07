package com.skubit.comics.activities;


import com.skubit.comics.BuildConfig;
import com.skubit.comics.R;
import com.skubit.comics.adapters.ScreenshotViewerAdapter;
import com.skubit.shared.dto.UrlDto;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

public class ScreenshotsActivity extends Activity {

    private ViewPager mPager;

    private ScreenshotViewerAdapter mAdapter;

    public static Intent newInstance(ArrayList<UrlDto> urls, int position) {
        String[] u = new String[urls.size()];
        for(int i =0; i < urls.size(); i++) {
            u[i] = urls.get(i).getUrl();
        }

        Intent i = new Intent();
        i.putExtra("urls", u);
        i.putExtra("position", position);
        i.setClassName(BuildConfig.APPLICATION_ID, ScreenshotsActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] urls = getIntent().getStringArrayExtra("urls");
        int position = getIntent().getIntExtra("position", 0);

        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_screenshots);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(2);

        mAdapter = new ScreenshotViewerAdapter(getFragmentManager());
        mAdapter.setUrls(urls);
        mPager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.coverArt));
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(position);
        
        /*
        this.setContentView(R.layout.activity_cover_art);
        ImageView cover = (ImageView) findViewById(R.id.coverArt);

        Picasso.with(getBaseContext()).load(url)
                .into(cover);
                */

    }
}
