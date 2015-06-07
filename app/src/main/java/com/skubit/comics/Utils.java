package com.skubit.comics;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

import com.skubit.AccountSettings;
import com.skubit.Events;
import com.skubit.Permissions;
import com.skubit.android.billing.IBillingService;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.activities.DownloadDialogActivity;
import com.skubit.comics.activities.MainActivity;
import com.skubit.comics.provider.accounts.AccountsColumns;
import com.skubit.comics.provider.accounts.AccountsContentValues;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicBookDto;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Utils {

    public static final int AUTHORIZATION_CODE = 100;

    public static final int PLAY_CODE = 200;


    public static Intent getBillingServiceIntent() {
        String serviceName = BuildConfig.FLAVOR.startsWith("dev") ? Constants.IAB_TEST
                : Constants.IAB_PROD;

        Intent service = new Intent(serviceName + ".billing.IBillingService.BIND");
        service.setPackage(serviceName);
        return service;

    }

    public static String getIabPackageName() {
        return BuildConfig.FLAVOR.startsWith("dev") ? Constants.IAB_TEST
                : Constants.IAB_PROD;
    }

    public static Intent getIabIntent() {
        try {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getIabPackageName()));
        } catch (android.content.ActivityNotFoundException anfe) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="
                            + getIabPackageName()));
        }
    }

    public static boolean isIabInstalled(PackageManager pm) {
        try {
            pm.getPackageInfo(getIabPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

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

    public static void doDownloadNotification(Context context, ComicBookDto data) {
        doDownloadNotification(context, data.getCbid(), data.getStoryTitle(), data.getSummary());
    }

    public static void endOrderNotification(Context context,
            String cbid, String storyTitle, NotificationCompat.Builder mBuilder) {
        mBuilder.setProgress(100, 100, false);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle("Finished processing your order");

        Intent resultIntent = DownloadDialogActivity.newInstance(cbid, storyTitle, false);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ComicViewerActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        storyTitle.hashCode(),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());
    }

    public static NotificationCompat.Builder beginOrderNotification(Context context, String cbid,
            String storyTitle) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp)
                        .setContentTitle("Processing your order ")
                        .setContentText(storyTitle);

        mBuilder.setProgress(0, 0, true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());
        return mBuilder;
    }

    public static void doDownloadNotification(Context context, String cbid, String storyTitle,
            String summary) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp)
                        .setContentTitle(storyTitle)
                        .setContentText(summary);

        Intent resultIntent = new Intent("com.skubit.comics.MY_COMICS");
        resultIntent.setClass(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ComicViewerActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        storyTitle.hashCode(),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());
    }

    public static void download(Context context, String uri, String md5, String cbid,
            String storyTitle, String summary,
            ArchiveFormat format,
            DownloadManager downloadManager) {

        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(uri));

        File rootDir = new File(Constants.SKUBIT_ARCHIVES_DOWNLOAD,
                cbid);
        rootDir.mkdirs();

        File file = new File(rootDir, storyTitle + "." + format.name().toLowerCase());

        if (file.exists()) {
            if (doesHashMatch(file, md5)) {
                System.out.println("foo: md5  matches");
                doDownloadNotification(context, cbid, storyTitle, summary);
                return;
            }
        }

        request.setDestinationUri(
                Uri.fromFile(file));

        if (summary != null) {
            request.setDescription(summary);
        }
        request.setTitle(storyTitle);
        downloadManager.enqueue(request);
    }

    private static boolean doesHashMatch(File file, String hash) {
        try {
            String hc = new String(BaseEncoding.base64Url().decode(hash));
            return hc.equals(Files.asByteSource(file).hash(Hashing.md5())
                    .toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("foo: " + e.getLocalizedMessage());
            return false;
        }
    }

    public static void download(Context context, String uri, String md5, ComicBookDto comicBookDto,
            DownloadManager downloadManager) {
        String title = comicBookDto.getStoryTitle() + " Vol."
                + comicBookDto.getVolume() + " #" + comicBookDto.getIssueNumber();
        download(context, uri, md5, comicBookDto.getCbid(), title,
                comicBookDto.getSummary(), ArchiveFormat.CBZ, downloadManager);//TODO: PDF?
    }
}
