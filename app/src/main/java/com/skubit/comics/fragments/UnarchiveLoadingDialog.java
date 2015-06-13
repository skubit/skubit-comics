package com.skubit.comics.fragments;

import com.skubit.dialog.DefaultFragment;

import android.app.Fragment;
import android.os.Bundle;


public class UnarchiveLoadingDialog extends DefaultFragment {

    public static Fragment newInstance() {
        return new UnarchiveLoadingDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
