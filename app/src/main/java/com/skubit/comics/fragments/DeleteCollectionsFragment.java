package com.skubit.comics.fragments;

import com.gc.materialdesign.views.ButtonFlat;
import com.skubit.comics.R;
import com.skubit.dialog.DefaultFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeleteCollectionsFragment extends DefaultFragment {

    private ButtonFlat mDeleteBtn;

    public static DeleteCollectionsFragment newInstance(Bundle args) {
        DeleteCollectionsFragment f = new DeleteCollectionsFragment();
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_delete_collection, null);

       mDeleteBtn = (ButtonFlat) v.findViewById(R.id.deleteBtn);
       mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.load(getArguments());
            }
        });
        ButtonFlat cancelBtn = (ButtonFlat) v.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }
}
