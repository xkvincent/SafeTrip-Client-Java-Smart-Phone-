package com.crimekiller.safetrip.ws.local;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.ui.MainActivity;
import com.crimekiller.safetrip.ui.UserPageActivity;

public class NotificationService extends Service {

    private NotificationManager notificationMgr;
    private Notification notification;
    @Override
    public void onCreate() {
        super.onCreate();
        notificationMgr =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        displayNotificationMessage();
//        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
//        thr.start();
    }

    class ServiceWorker implements Runnable
    {
        public void run() {
            // do background processing here...
            // stop the service when done...
            // BackgroundService.this.stopSelf();
        }
    }

    @Override
    public void onDestroy()
    {
        //displayNotificationMessage();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void displayNotificationMessage()
    {

        PendingIntent pit = PendingIntent.getActivity(this, 0,
                                                            new Intent(this, MainActivity.class), 0);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("Notification")                        //标题
                .setContentText("Please go to Manage Friend Section")      //内容
                .setSubText("Click on view pending request")     //内容下面的一小段文字
                .setTicker("You have pending friend request")   //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.drawable.profile)            //设置小图标
                //.setLargeIcon(LargeBitmap)                     //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true);                          //设置点击后取消Notification
               // .setContentIntent(pit);                        //设置PendingIntent
        notification = mBuilder.build();
        notificationMgr.notify(1, notification);
    }
}
