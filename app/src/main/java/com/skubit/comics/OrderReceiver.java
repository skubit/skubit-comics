package com.skubit.comics;

import com.skubit.comics.activities.DownloadDialogActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class OrderReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.skubit.comics.ACTION_ORDER";

    public static final String CBID = "com.skubit.comics.CBID";

    public static final String STORY_TITLE = "com.skubit.comics.STORY_TITLE";

    public static Intent newInstance(String cbid, String storyTitle) {
        Intent i = new Intent(ACTION);
        i.putExtra(CBID, cbid);
        i.putExtra(STORY_TITLE, storyTitle);
        return i;
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        String cbid = intent.getStringExtra(CBID);
        String storyTitle = intent.getStringExtra(STORY_TITLE);

    }


    private void openDownloadDialog(Context context, String cbid, String storyTitle) {
        if(!TextUtils.isEmpty(cbid) && !TextUtils.isEmpty(storyTitle)) {
            Intent resultIntent = DownloadDialogActivity.newInstance(cbid, storyTitle, false);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);
        }
    }
}
