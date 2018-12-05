package com.aliwh.android.quickyicha;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.aliwh.android.quickyicha.activity.MainActivity;
import com.aliwh.android.quickyicha.utils.IntentUtils;

import java.io.File;
import java.io.IOException;

/**
 * 自动更新升级服务
 *
 * @author Kevin
 */
public class UpdateService extends Service {

    private static final int DOWN_OK = 1; // 下载完成
    private static final int DOWN_ERROR = 0;
    private String down_url;
    private String app_name;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private RemoteViews contentView;
    private int notification_id = 0;
    private String filePath;
    private PendingIntent pendingIntent;
    /***
     * 更新UI
     */
    private Handler handler = new Handler() {
        @SuppressWarnings("deprecation")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_OK:
                    // 下载完成，点击发送安装广播
                    Intent it = new Intent();
                    it.setAction(Constants.INSTALL_APK);
                    it.putExtra("filePath", filePath);
                    sendBroadcast(it);
                    break;
                case DOWN_ERROR:
                    mBuilder.setContentTitle(app_name);
                    mBuilder.setContentText("下载失败");
                    mNotificationManager.notify(notification_id, mBuilder.build());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                app_name = intent.getStringExtra("app_name");
                down_url = intent.getStringExtra("down_url");
                // 创建文件
                File updateFile = new File(getExternalFilesDir("apk") + File.separator + app_name);
                if (!updateFile.exists()) {
                    try {
                        updateFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                filePath = updateFile.getAbsolutePath();
                // 创建通知
                createNotification();
                // 开始下载
                downloadUpdateFile(down_url + app_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /***
     * 创建通知栏
     */
    public void createNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, getString(R.string.app_name) + "新版本下载中....0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelID = "update";
            NotificationChannel channel = new NotificationChannel(channelID, "版本更新", NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder = new NotificationCompat.Builder(this, channelID);
        } else {
            mBuilder = new NotificationCompat.Builder(getApplicationContext());
        }
        mBuilder.setSmallIcon(R.mipmap.logo_icon);
        mBuilder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
        mBuilder.setAutoCancel(true);
        // 这个参数是通知提示闪出来的值.
        mBuilder.setTicker("下载中...");
        mBuilder.setContent(contentView);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));

        Intent installApkIntent = IntentUtils.getFileIntent(this, new File(filePath));
        pendingIntent = PendingIntent.getActivity(this, 0, installApkIntent, 0);

        mNotificationManager.notify(notification_id, mBuilder.build());
    }


    /***
     * 下载文件
     */
    public void downloadUpdateFile(String down_url)
            throws Exception {
        XutilsHttp.getInstance().downFile(down_url, filePath, new XutilsHttp.XDownLoadCallBack() {
            @Override
            public void onstart() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // TODO total总长度，current下载长度
                double i = current / (double) total * 100;
                int progress = (int) Math.ceil(i);
                Log.e("total", "total: " + total);
                Log.e("total", "current: " + current);
                contentView.setTextViewText(R.id.notificationTitle, getString(R.string.app_name) + "新版本下载中...." + progress + "%");
                contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);
                if (progress == 100) {
                    contentView.setTextViewText(R.id.notificationTitle, "下载完成,点击安装");
                    contentView.setOnClickPendingIntent(R.id.update_content, pendingIntent);
                }
                mNotificationManager.notify(notification_id, mBuilder.build());
            }

            @Override
            public void onSuccess(File result) {
                // 下载成功
                Message message = handler.obtainMessage();
                message.what = DOWN_OK;
                handler.sendMessage(message);
            }

            @Override
            public void onFail(String result) {
                Message message = handler.obtainMessage();
                message.what = DOWN_ERROR;
                handler.sendMessage(message);
            }

            @Override
            public void onFinished() {

            }
        });
    }


}