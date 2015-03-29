package com.skubit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class Events {

    public static void accountChange(Context context, String accountName) {
        Intent intent = Intents.accountChangeIntent();
        intent.putExtra(Intents.ACCOUNT_NAME, accountName);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
