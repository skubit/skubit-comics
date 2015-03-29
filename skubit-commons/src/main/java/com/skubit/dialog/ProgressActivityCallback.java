package com.skubit.dialog;

public interface ProgressActivityCallback<T> extends ProgressCallback {

    void load(T data, int type);

    void load(T data);

}
