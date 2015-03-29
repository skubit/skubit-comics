package com.skubit.dialog;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

public abstract class ProgressActivity<T> extends FragmentActivity
        implements ProgressActivityCallback<T> {

    protected View mLoading;

    protected String mUIState;

    protected View mContainer;

    @Override
    public void load(T data) {
        load(data, BaseLoader.TYPE_DEFAULT);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        mUIState = tag;
        getFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .replace(R.id.container, fragment, tag).commitAllowingStateLoss();
    }

    public void showMessage(String message) {
        if (isAlive()) {
            mUIState = "MESSAGE";
            replaceFragment(ShowMessageFragment
                            .newInstance(message, mContainer.getWidth(), mContainer.getHeight()),
                    "MESSAGE");
            hideProgress();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loading_dialog_frame);
        mLoading = findViewById(R.id.progress_bar);
        mContainer = findViewById(R.id.container);

        if (savedInstanceState != null) {
            mUIState = savedInstanceState.getString("UI_STATE");
        }
    }

    @Override
    public void sendResultsBackToCaller(int resultCode, String message) {
        if (TextUtils.isEmpty(message)) {
            setResult(resultCode);
        } else {
            setResult(resultCode, new Intent().putExtra("response", message));
        }
        finish();
    }

    @Override
    public void cancel() {
        setResult(ResultCode.USER_CANCELED);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mUIState = savedInstanceState.getString("UI_STATE");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUIState != null) {
            outState.putString("UI_STATE", mUIState);
        }
    }

    @Override
    public void hideProgress() {
        if (isAlive()) {
            mContainer.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showProgress() {
        if (isAlive()) {
            mContainer.setVisibility(View.INVISIBLE);
            mLoading.setVisibility(View.VISIBLE);
        }
    }

    public boolean isAlive() {
        return mContainer != null;
    }

}
