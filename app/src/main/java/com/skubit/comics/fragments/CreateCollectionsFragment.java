package com.skubit.comics.fragments;

import com.gc.materialdesign.views.ButtonFlat;
import com.skubit.comics.R;
import com.skubit.dialog.DefaultFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CreateCollectionsFragment extends DefaultFragment {

    private ButtonFlat mCreateBtn;

    private TextView mCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_create_collection, null);
        mCreateBtn = (ButtonFlat) v.findViewById(R.id.createBtn);
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("collection", mCollection.getText().toString());
                mCallbacks.load(bundle);
            }
        });
        ButtonFlat cancelBtn = (ButtonFlat) v.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mCollection = (TextView) v.findViewById(R.id.collection);
        return v;
    }
}
