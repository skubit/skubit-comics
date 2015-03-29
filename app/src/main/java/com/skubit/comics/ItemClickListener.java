package com.skubit.comics;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

public interface ItemClickListener {

    void onClick(View v, int position, Cursor cursor);

    void onClickOption(View v, Bundle bundle);
}
