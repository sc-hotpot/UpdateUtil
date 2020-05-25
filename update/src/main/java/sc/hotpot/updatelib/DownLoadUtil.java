package sc.hotpot.updatelib;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class DownLoadUtil {


    private File saveFile;
    private long downloadId = 0;
    private Activity mContext;
    private  Activity temp;
    private static DownLoadUtil downLoadUtil;
    private static Application application;
    private CompleteReceiver item;
    ;

    public static DownLoadUtil getInstance(Activity activity) {


        if (downLoadUtil == null)
            downLoadUtil = new DownLoadUtil();
        downLoadUtil.mContext = activity;
        downLoadUtil.initBroadcastReceiver(activity);

        return downLoadUtil;
    }
    private DownLoadUtil() {
    }

    private void initBroadcastReceiver(Activity activity) {
        if (activity.equals(temp)) {
            return;
        }
        application = activity.getApplication();
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (temp != null && temp.getClass().getName().equals(activity.getClass().getName())) {
                    application.unregisterActivityLifecycleCallbacks(this);
                    temp.unregisterReceiver(item);
                }
            }
        });
        item = new CompleteReceiver();
        activity.registerReceiver(item, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        temp = activity;
    }

    public void startDownload(String url, String title, String describeStr) {

        if (downloadId != 0) {  //根据任务ID判断是否存在相同的下载任务，如果有则不重新下载
            return;
        }
        initFile();
        downloadId = downLoadApk(mContext, url, title, describeStr);
    }

    private void initFile() {
        if (saveFile == null)
            saveFile = new File(mContext.getExternalCacheDir(), "update.apk");
        if (saveFile.exists()) {    //判断文件是否存在，存在的话先删除
            saveFile.delete();
        }
    }

    private long downLoadApk(Context context, String url, String title, String describeStr) {
        // 得到系统的下载管理
        DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        // 以下两行代码可以让下载的apk文件被直接安装而不用使用Fileprovider,系统7.0或者以上才启动。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder localBuilder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(localBuilder.build());
        }
        DownloadManager.Request requestApk = new DownloadManager.Request(uri);
        // 设置在什么网络下下载
        requestApk.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 下载中和下载完后都显示通知栏
        requestApk.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        requestApk.setDestinationUri(Uri.fromFile(saveFile));
        // 表示允许MediaScanner扫描到这个文件，默认不允许。
        requestApk.allowScanningByMediaScanner();
        // 设置下载中通知栏的提示消息
        requestApk.setTitle(title);
        // 设置设置下载中通知栏提示的介绍
        requestApk.setDescription(describeStr);

        // 7.0以上的系统适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestApk.setRequiresDeviceIdle(false);
            requestApk.setRequiresCharging(false);
        }
        // 启动下载,该方法返回系统为当前下载请求分配的一个唯一的ID
        long downLoadId = manager.enqueue(requestApk);
        return downLoadId;
    }


    public void installApk() {
        downloadId = 0;
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", saveFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        mContext.startActivity(intent);
    }

    public class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // get complete download id
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            // to do here
            DownLoadUtil.this.installApk();
            DownLoadUtil.this.downloadId = 0;
        }
    }

    ;
}
