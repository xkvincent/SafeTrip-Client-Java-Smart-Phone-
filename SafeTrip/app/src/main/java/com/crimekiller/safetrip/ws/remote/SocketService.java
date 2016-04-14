package com.crimekiller.safetrip.ws.remote;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.crimekiller.safetrip.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketService extends Service {

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private ArrayList<User> friendsList;

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        socket = new Socket();
        Runnable connect = new connectSocket();
        new Thread(connect).start();
    }

    class connectSocket implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket(LocalHost, PORT);
                objInputStream = new ObjectInputStream(socket.getInputStream());
                objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                objOutputStream.writeObject("Manage Friend");
                friendsList = (ArrayList<User>) objInputStream.readObject();

            } catch (IOException e) {
                e.printStackTrace();
            } catch ( ClassNotFoundException e) {
                System.out.println("ClassNotFoundException ");
                e.printStackTrace();
            }

        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            objOutputStream.close();
            objInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
