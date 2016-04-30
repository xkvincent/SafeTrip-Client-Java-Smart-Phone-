package com.crimekiller.safetrip.ws.local;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.ui.MainActivity;
import com.crimekiller.safetrip.ui.PendingRequestActivity;
import com.crimekiller.safetrip.ui.UserPageActivity;

import java.util.ArrayList;

public class NotificationService extends Service {

    private NotificationManager notificationMgr;
    private Notification notification;
    private String username;
    private Bundle bundle;
    private ArrayList<String> PendingRequest;

    private static String GET_PENDING_REQUEST_COMMAND = "Get Pending Request";

    @Override
    public void onCreate() {
        super.onCreate();
        notificationMgr =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        PendingRequest = new ArrayList<String>();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         bundle = intent.getExtras();
         username = bundle.getString("username");
       // username = intent.getStringExtra("username");
        connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    public void connect() {

        AsyncTask<Void, ArrayList<String>, Void> checkPendingRequest =
                                                    new AsyncTask<Void, ArrayList<String>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(
                        GET_PENDING_REQUEST_COMMAND, username, null);

                socketClient.run();
                PendingRequest = socketClient.getPendingRequest();

                if( PendingRequest.size() != 0 )
                    displayNotificationMessage(username);
                return null;
            }
        };
        checkPendingRequest.execute();
    }

    private void displayNotificationMessage(String name)
    {
        Intent pendingRequestIntent = new Intent(this, PendingRequestActivity.class );
        //pendingRequestIntent.putExtra("username",name);
        pendingRequestIntent.putExtras(bundle);
        PendingIntent pit = PendingIntent.getActivity(this, 0, pendingRequestIntent,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("Notification")                        //Title
                .setContentText("Please go to Manage Friend Section")   //Content
                .setSubText("Click on view pending request")            //SubContent
                .setTicker("You have pending friend request")
                .setWhen(System.currentTimeMillis())                   //Set time
                .setSmallIcon(R.drawable.profile)                      //Set Icon
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)//set light and vibration
                .setAutoCancel(true)                          //Click on to cancel notification
                .setContentIntent(pit);                       //Pending Intent
        notification = mBuilder.build();
        notificationMgr.notify(1, notification);
    }
}
