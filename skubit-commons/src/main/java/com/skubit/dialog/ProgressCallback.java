package com.skubit.dialog;

public interface ProgressCallback {

    void sendResultsBackToCaller(int resultCode, String message);

    void cancel();

    void showProgress();

    void hideProgress();

    void showMessage(String message);
}
