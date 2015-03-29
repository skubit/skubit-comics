package com.skubit.comics;

import com.squareup.picasso.Picasso;

public interface PageTapListener {

    void toggleView();

    void setTotalPages(int totalPages);

    Picasso getPicasso();
}
