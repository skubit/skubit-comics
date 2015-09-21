package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;

import java.io.File;


public class ElectricViewerActivity extends Activity {

    public static Intent newInstance(File archiveDir) {
        Intent i = new Intent();
        File index = index(archiveDir);
        if (index != null && index.exists()) {
            String normalizedUrl =  normalizeUrl(index.getAbsolutePath());
            i.putExtra("index", normalizedUrl);
        }

        i.setClassName(BuildConfig.APPLICATION_ID, ElectricViewerActivity.class.getName());
        return i;
    }

    private static String normalizeUrl(String file) {
        return  new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                file.substring(file.indexOf("SkubitComics"))).getAbsolutePath();
    }

    private static File index(File root) {
        File[] files = root.listFiles();
        if(files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().startsWith("index.")) {
                return file;
            } else if (file.isDirectory()) {
                File f = index(file);
                if (f != null) {
                    return f;
                }
            }
        }
        return null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_viewer);
        String indexFile = getIntent().getStringExtra("index");
        if (indexFile == null) {
            return;
        }
        final WebView webView = (WebView) this.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file://" + indexFile);
        //   webView.loadUrl(
        //          "file://" + Environment.getExternalStorageDirectory() + "/hammer/index.html");

    }
}
