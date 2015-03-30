package com.skubit.comics;

import com.skubit.AccountSettings;
import com.skubit.Events;
import com.skubit.Permissions;
import com.skubit.android.billing.IBillingService;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.accounts.AccountsContentValues;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.LockerItemDto;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

import java.io.File;
import java.util.Date;

public class Utils {

    public static final int AUTHORIZATION_CODE = 100;

    public static void startAuthorization(Activity activity, IBillingService service) {
        try {
            Bundle bundle = service.getAuthorizationIntent(1,
                    BuildConfig.APPLICATION_ID, Permissions.IAB_DEFAULT);
            PendingIntent pendingIntent = bundle
                    .getParcelable("AUTHORIZATION_INTENT");

            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), AUTHORIZATION_CODE,
                    null, 0, 0, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    public static void createNewAccount(Activity context, Intent data) {
        String[] tokens = data.getStringExtra("response").split("[:]");
        String account = tokens[0];

        AccountsContentValues kcv = new AccountsContentValues();
        kcv.putBitid(account);
        kcv.putToken(tokens[1]);
        kcv.putDate(new Date().getTime());

        context.getContentResolver().delete(AccountsColumns.CONTENT_URI,
                AccountsColumns.BITID + "=?",
                new String[]{
                        account
                });
        context.getContentResolver().insert(AccountsColumns.CONTENT_URI, kcv.values());

        AccountSettings.get(context).saveToken(tokens[1]);
        AccountSettings.get(context).saveBitId(account);
        Events.accountChange(context, account);

    }

    public static boolean isExplicitCatalog() {
        return "devX".equals(BuildConfig.FLAVOR) || "prodX".equals(BuildConfig.FLAVOR);
    }

    public static boolean doesDownloadExist(String cbid) {
        File downloadDir = new File(Constants.SKUBIT_ARCHIVES_DOWNLOAD, cbid);
        return downloadDir.exists() && downloadDir.listFiles().length > 0 &&
                downloadDir.listFiles()[0].getName().endsWith(".cbz");
    }

    public static void download(String uri, String cbid, String storyTitle, String summary,
            DownloadManager downloadManager) {
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(uri));

        File rootDir = new File(Constants.SKUBIT_ARCHIVES_DOWNLOAD,
                cbid);
        rootDir.mkdirs();

        request.setDestinationUri(Uri.fromFile(new File(rootDir, storyTitle + ".cbz")));
        if (summary != null) {
            request.setDescription(summary);
        }
        request.setTitle(storyTitle);
        downloadManager.enqueue(request);
    }

    public static void download(String uri, LockerItemDto lockerItemDto,
            DownloadManager downloadManager) {
        download(uri, lockerItemDto.getProductId(), lockerItemDto.getTitle(),
                null, downloadManager);
    }

    public static void download(String uri, ComicBookDto comicBookDto,
            DownloadManager downloadManager) {
        download(uri, comicBookDto.getCbid(), comicBookDto.getStoryTitle(),
                comicBookDto.getSummary(), downloadManager);
    }
}
