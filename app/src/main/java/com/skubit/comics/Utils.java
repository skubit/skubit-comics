package com.skubit.comics;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

import com.github.junrar.Archive;
import com.skubit.AccountSettings;
import com.skubit.Events;
import com.skubit.Permissions;
import com.skubit.bitid.activities.AppRequestActivity;
import com.skubit.comics.activities.ComicViewerActivity;
import com.skubit.comics.activities.DownloadDialogActivity;
import com.skubit.comics.activities.MainActivity;
import com.skubit.shared.dto.ArchiveFormat;
import com.skubit.shared.dto.ComicBookDto;
import com.skubit.shared.dto.ComicBookType;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

    public static void startAuthorization(Activity activity) {
        Intent auth = AppRequestActivity.newInstance(activity, BuildConfig.APPLICATION_ID, Permissions.IAB_DEFAULT);
        activity.startActivityForResult(auth, AUTHORIZATION_CODE);
    }

    public static void doDownloadedNotification(Context context, ComicBookDto data) {
        doDownloadedNotification(context, data.getCbid(), data.getStoryTitle());
    }

    public static void downloadFailedNotification(Context context,
            String cbid, String storyTitle) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp);
        Intent resultIntent = new Intent("com.skubit.comics.MY_LOCKER");
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
        mBuilder.setContentTitle("Download failed: Click to try from locker");
        mBuilder.setContentText(storyTitle);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());

    }

    public static void endOrderNotification(Context context,
            String cbid, String storyTitle, boolean showDownloadDialog) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp);

        mBuilder.setProgress(100, 100, false);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(context.getString(R.string.end_order_message));
        mBuilder.setContentText(storyTitle);

        if(showDownloadDialog) {
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
        }

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());
    }

    public static void cancelOrderNotification(Context context,
            String cbid, String storyTitle) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp);

        mBuilder.setProgress(100, 100, false);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(context.getString(R.string.order_timeout_notif));
        mBuilder.setContentText(storyTitle);

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
                        .setContentTitle(context.getString(R.string.processing_order))
                        .setContentText(storyTitle);

        mBuilder.setProgress(0, 0, true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(cbid.hashCode(), mBuilder.build());
        return mBuilder;
    }

    public static void doDownloadedNotification(Context context, String cbid, String storyTitle) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_play_install_white_24dp)
                        .setContentTitle("Downloaded comic")
                        .setContentText("Touch to open: " + storyTitle);

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
            String storyTitle,
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
                doDownloadedNotification(context, cbid, storyTitle);
                return;
            }
        }

        request.setDestinationUri(
                Uri.fromFile(file));

        request.setDescription(storyTitle);
        request.setTitle(context.getString(R.string.downloading_comic_notif));

        downloadManager.enqueue(request);
    }

    private static boolean doesHashMatch(File file, String hash) {
        try {
            String hc = new String(BaseEncoding.base64Url().decode(hash));
            return hc.equals(Files.asByteSource(file).hash(Hashing.md5())
                    .toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void download(Context context, String uri, String md5, ComicBookDto comicBookDto,
            DownloadManager downloadManager) {
       ArchiveFormat archiveFormat = ComicBookType.ELECTRICOMIC.name().equals(comicBookDto.getComicBookType()) ?
               ArchiveFormat.ELCX : ArchiveFormat.CBZ;

        String title = ArchiveFormat.ELCX.equals(archiveFormat) ? comicBookDto.getStoryTitle().replace(" ", "_")
                : createTitle(comicBookDto);

        download(context, uri, md5, comicBookDto.getCbid(), title, archiveFormat,
                downloadManager);//TODO: PDF?
    }

    public static void download(Context context, String uri, String md5, ComicData comicData,
            DownloadManager downloadManager) {
        ArchiveFormat archiveFormat = ComicBookType.ELECTRICOMIC.name().equals(comicData.getComicBookType()) ?
                ArchiveFormat.ELCX : ArchiveFormat.CBZ;
        String title = ArchiveFormat.ELCX.equals(archiveFormat) ? comicData.getTitle().replace(" ", "_")
                : createTitle(comicData);

        download(context, uri, md5, comicData.getCbid(), title + "",
                archiveFormat, downloadManager);//TODO: PDF?
    }

    private static String createTitle(ComicData comicData) {
        return comicData.getTitle().replace(" ", "_");
    }

    private static String createTitle(ComicBookDto comicBookDto) {
        return comicBookDto.getStoryTitle() + " Vol."
                + comicBookDto.getVolume() + " #" + comicBookDto.getIssueNumber();
    }



}
