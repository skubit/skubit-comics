package com.skubit.comics.fragments;

import com.gc.materialdesign.views.ButtonFlat;
import com.skubit.dialog.DefaultFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.ListView;

public class AddToCollectionFragment extends DefaultFragment {

    private String mCid;

    private ListView mListView;

    private String mName;

    private ButtonFlat mAddBtn;

    public static Fragment newInstance(Bundle args) {
        AddToCollectionFragment frag = new AddToCollectionFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCid = args.getString("cid");
            mName = args.getString("name");
        }
    }

}
