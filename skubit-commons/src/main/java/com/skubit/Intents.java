package com.skubit;


import android.content.Intent;
import android.content.IntentFilter;

public class Intents {

    public static final String ACCOUNT_NAME = "com.skubit.ACCOUNT_NAME";

    public static IntentFilter accountChangeFilter() {
        return new IntentFilter(ACCOUNT_NAME);
    }

    public static Intent accountChangeIntent() {
        return new Intent(ACCOUNT_NAME);
    }
}
