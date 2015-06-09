package com.skubit.comics.fragments;


import com.gc.materialdesign.views.ButtonFlat;
import com.skubit.comics.ComicFile;
import com.skubit.comics.R;
import com.skubit.dialog.DefaultFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;

public class DownloadDialogFragment extends DefaultFragment {

    private ButtonFlat mDownloadBtn;

    public static DownloadDialogFragment newInstance(String title,
            ArrayList<ComicFile> comicFiles) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("com.skubit.comics.COMIC_FILES", comicFiles);
        args.putString("com.skubit.comics.TITLE", title);
        DownloadDialogFragment fragment = new DownloadDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_download_sample, null);
        TextView title = (TextView) v.findViewById(R.id.title);

        final ListView mListView = (ListView) v.findViewById(R.id.listView);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelected(true);
        mListView.setClickable(true);

        final ArrayList<ComicFile> comicFiles = getArguments()
                .getParcelableArrayList("com.skubit.comics.COMIC_FILES");
        String titleStr = getArguments().getString("com.skubit.comics.TITLE");
        title.setText(titleStr);

        ArrayList<String> adapterLabels = new ArrayList<>();
        for (ComicFile comicFile : comicFiles) {
            adapterLabels.add(MessageFormat
                    .format("{0} ({1})", comicFile.format, fileSize(comicFile.size)));
        }

        final ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_single_choice, adapterLabels);
        mListView.setAdapter(mAdapter);

        if (comicFiles.size() > 0) {
            mListView.setItemChecked(0, true);
        }

        mDownloadBtn = (ButtonFlat) v.findViewById(R.id.downloadBtn);
        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mListView.getCheckedItemPosition();
                String format = comicFiles.get(position).format;
                Bundle args = new Bundle();
                args.putString("COMIC_FILE", format);
                args.putString("MD5", comicFiles.get(position).md5Hash);
                mCallbacks.load(args);
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

    private static final String[] units = new String[]{"B", "kB", "MB", "GB",};

    private static String fileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " "
                + units[digitGroups];
    }
}
