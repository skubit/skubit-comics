package com.skubit.navigation;

import android.view.View;
import android.widget.AdapterView;

public interface DropDownClickEvent {

    public String onClick(AdapterView<?> adapterView,
            View view, int position, long arg3);
}
