package com.skubit.comics.activities;


import com.skubit.comics.BuildConfig;
import com.skubit.comics.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CoverArtActivity extends Activity {

    public static Intent newInstance(String url) {
        Intent i = new Intent();
        i.putExtra("COVER", url);
        i.setClassName(BuildConfig.APPLICATION_ID, CoverArtActivity.class.getName());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("COVER");

        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        this.setContentView(R.layout.activity_cover_art);
        ImageView cover = (ImageView) findViewById(R.id.coverArt);

        Picasso.with(getBaseContext()).load(url + "=-rw")
                .into(cover);

    }
}
