package com.skubit.comics.activities;

import com.skubit.comics.BuildConfig;
import com.skubit.comics.ComicFile;
import com.skubit.comics.Utils;
import com.skubit.comics.fragments.DownloadDialogFragment;
import com.skubit.comics.loaders.ComicFilesLoader;
import com.skubit.comics.loaders.DownloadComicLoader;
import com.skubit.dialog.LoaderResult;
import com.skubit.dialog.ProgressActivity;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicFileClassifier;
import com.skubit.shared.dto.ComicFileDto;
import com.skubit.shared.dto.UrlDto;

import android.app.DownloadManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;

public class DownloadDialogActivity extends ProgressActivity<Bundle> {

    private String mCbid;

    private ArchiveFormat mArchiveFormat;

    private String mStoryTitle;

    private boolean mIsSample;

    private String md5;

    public static Intent newInstance(String cbid, String storyTitle, boolean isSample) {
        Intent i = new Intent();
        i.putExtra("cbid", cbid);
        i.putExtra("storyTitle", storyTitle);
        i.putExtra("isSample", isSample);
        i.setClassName(BuildConfig.APPLICATION_ID, DownloadDialogActivity.class.getName());
        return i;
    }

    private final LoaderManager.LoaderCallbacks<ArrayList<ComicFileDto>> mCallback
            = new LoaderManager.LoaderCallbacks<ArrayList<ComicFileDto>>() {

        @Override
        public Loader<ArrayList<ComicFileDto>> onCreateLoader(int id, Bundle args) {
            showProgress();
            return new ComicFilesLoader(getBaseContext(),
                    mCbid, mIsSample ? ComicFileClassifier.SAMPLE : ComicFileClassifier.FULL);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<ComicFileDto>> loader,
                ArrayList<ComicFileDto> data) {
            hideProgress();
            if (data != null) {
                ArrayList<ComicFile> comicFiles = new ArrayList<>();
                for (ComicFileDto cfd : data) {
                    ComicFile cf = new ComicFile();
                    cf.size = cfd.getSize();
                    cf.md5Hash = cfd.getMd5Hash();
                    cf.format = cfd.getFormat().name();
                    comicFiles.add(cf);
                }

                replaceFragment(DownloadDialogFragment.newInstance(mStoryTitle, comicFiles),
                        mUIState);
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<ComicFileDto>> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDownloadManager = (DownloadManager) getSystemService(
                Context.DOWNLOAD_SERVICE);
        mCbid = getIntent().getStringExtra("cbid");
        mStoryTitle = getIntent().getStringExtra("storyTitle");
        mIsSample = getIntent().getBooleanExtra("isSample", false);
        getLoaderManager()
                .initLoader((mCbid + mStoryTitle + mIsSample).hashCode(), null, mCallback);
    }

    @Override
    public void load(Bundle data, int type) {
        String format = data.getString("COMIC_FILE");
        md5 = data.getString("MD5");
        if (!TextUtils.isEmpty(format)) {
            if (ArchiveFormat.PDF.name().equals(format)) {
                mArchiveFormat = ArchiveFormat.PDF;
            } else if (ArchiveFormat.CBZ.name().equals(format)) {
                mArchiveFormat = ArchiveFormat.CBZ;
            }
            getLoaderManager().initLoader((mCbid + format + mIsSample).hashCode(), null,
                    mDownloadCallback);
        }

    }

    private DownloadManager mDownloadManager;

    private final LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>> mDownloadCallback
            = new LoaderManager.LoaderCallbacks<LoaderResult<UrlDto>>() {

        @Override
        public Loader<LoaderResult<UrlDto>> onCreateLoader(int id, Bundle args) {
            showProgress();
            return new DownloadComicLoader(getBaseContext(), mCbid, mIsSample, mArchiveFormat);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<UrlDto>> loader,
                final LoaderResult<UrlDto> data) {
            if (!TextUtils.isEmpty(data.errorMessage)) {
                Toast.makeText(getBaseContext(), data.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Utils.download(getBaseContext(), data.result.getUrl(), md5,  mCbid,
                        mStoryTitle + (mIsSample ? " - Sample" : ""),
                        mArchiveFormat,
                        mDownloadManager);
                Toast.makeText(getBaseContext(),
                        "Downloading your comic in " + mArchiveFormat.name()
                                + " format. Check notification bar...",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<UrlDto>> loader) {

        }
    };
}
