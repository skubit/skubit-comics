package com.skubit.comics;

import java.util.ArrayList;

public interface ICatalogAdapter<T> {

    void add(ArrayList<T> dtos);

    void notifyDataSetChanged();
}
