package com.skubit.comics;

import com.skubit.comics.adapters.CursorRecyclerViewAdapter;

public interface AdapterListener extends ItemClickListener {

    void resetAdapter(CursorRecyclerViewAdapter adapter);
}
