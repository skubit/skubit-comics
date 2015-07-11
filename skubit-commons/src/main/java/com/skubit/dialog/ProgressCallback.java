package com.skubit.dialog;

import android.os.Bundle;

public interface ProgressCallback {

    void sendResultsBackToCaller(int resultCode, String message);

    void sendResultsBackToCaller(int resultCode, String message, boolean finish);

    void sendResultsBackToCaller(int resultCode, Bundle data);

    void sendResultsBackToCaller(int resultCode, Bundle data, boolean finish);

    void cancel();

    void showProgress();

    void hideProgress();

    void showMessage(String message);

}
